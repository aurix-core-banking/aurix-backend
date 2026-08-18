package com.aurix.platform.compliance.service;

import com.aurix.platform.compliance.entity.ConsentimentoLGPD;
import com.aurix.platform.compliance.entity.LgpdExclusao;
import com.aurix.platform.compliance.lgpd.entity.LgpdBaseLegal;
import com.aurix.platform.compliance.lgpd.entity.LgpdLogAcesso;
import com.aurix.platform.compliance.lgpd.repository.LgpdBaseLegalRepository;
import com.aurix.platform.compliance.lgpd.repository.LgpdLogAcessoRepository;
import com.aurix.platform.compliance.repository.ConsentimentoLGPDRepository;
import com.aurix.platform.compliance.repository.LgpdExclusaoRepository;
import com.aurix.platform.shared.cache.SharedCacheService;
import com.aurix.platform.shared.entity.Cliente;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.Transacao;
import com.aurix.platform.shared.entity.Usuario;
import com.aurix.platform.shared.event.EventPublisher;
import com.aurix.platform.shared.event.LgpdDadosExcluidosEvent;
import com.aurix.platform.shared.event.Topics;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class LgpdService {

    private static final Logger log = LoggerFactory.getLogger(LgpdService.class);

    @PersistenceContext
    private EntityManager entityManager;

    private final LgpdExclusaoRepository exclusaoRepository;
    private final SharedCacheService cacheService;
    private final EventPublisher eventPublisher;
    private final ConsentimentoLGPDRepository consentimentoRepository;
    private final LgpdBaseLegalRepository baseLegalRepository;
    private final LgpdLogAcessoRepository logAcessoRepository;

    public LgpdService(LgpdExclusaoRepository exclusaoRepository,
                       SharedCacheService cacheService,
                       EventPublisher eventPublisher,
                       ConsentimentoLGPDRepository consentimentoRepository,
                       LgpdBaseLegalRepository baseLegalRepository,
                       LgpdLogAcessoRepository logAcessoRepository) {
        this.exclusaoRepository = exclusaoRepository;
        this.cacheService = cacheService;
        this.eventPublisher = eventPublisher;
        this.consentimentoRepository = consentimentoRepository;
        this.baseLegalRepository = baseLegalRepository;
        this.logAcessoRepository = logAcessoRepository;
    }

    @Transactional
    public ConsentimentoLGPD criarConsentimento(Long clienteId, String cpfCnpj,
                                                ConsentimentoLGPD.TipoConsentimento tipo,
                                                String descricaoFinalidade,
                                                String finalidades, String dadosColetados,
                                                String compartilhamentos, String ipAddress,
                                                String userAgent) {
        ConsentimentoLGPD consentimento = new ConsentimentoLGPD();
        consentimento.setCodigoConsentimento("CONS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        consentimento.setClienteId(clienteId);
        consentimento.setCpfCnpj(cpfCnpj);
        consentimento.setTipoConsentimento(tipo);
        consentimento.setStatus(ConsentimentoLGPD.StatusConsentimento.PENDENTE);
        consentimento.setDataSolicitacao(LocalDateTime.now());
        consentimento.setDescricaoFinalidade(descricaoFinalidade);
        consentimento.setFinalidades(finalidades);
        consentimento.setDadosColetados(dadosColetados);
        consentimento.setCompartilhamentos(compartilhamentos);
        consentimento.setIpAddress(ipAddress);
        consentimento.setUserAgent(userAgent);
        consentimento.setConsentimentoEspecifico(true);
        consentimento.setConsentimentoInformado(true);
        consentimento.setConsentimentoLivre(true);
        consentimento.setConsentimentoIndubitavel(true);
        return consentimentoRepository.save(consentimento);
    }

    @Transactional
    public ConsentimentoLGPD concederConsentimento(String codigoConsentimento, LocalDateTime dataExpiracao) {
        ConsentimentoLGPD consentimento = consentimentoRepository.findByCodigoConsentimento(codigoConsentimento)
            .orElseThrow(() -> new IllegalArgumentException("Consentimento nao encontrado: " + codigoConsentimento));
        consentimento.setStatus(ConsentimentoLGPD.StatusConsentimento.CONCEDIDO);
        consentimento.setDataConsentimento(LocalDateTime.now());
        consentimento.setDataExpiracao(dataExpiracao);
        return consentimentoRepository.save(consentimento);
    }

    @Transactional
    public ConsentimentoLGPD revogarConsentimento(String codigoConsentimento) {
        ConsentimentoLGPD consentimento = consentimentoRepository.findByCodigoConsentimento(codigoConsentimento)
            .orElseThrow(() -> new IllegalArgumentException("Consentimento nao encontrado: " + codigoConsentimento));
        consentimento.setStatus(ConsentimentoLGPD.StatusConsentimento.REVOGADO);
        return consentimentoRepository.save(consentimento);
    }

    public List<ConsentimentoLGPD> listarConsentimentosPorCliente(Long clienteId) {
        return consentimentoRepository.findByClienteId(clienteId);
    }

    public List<ConsentimentoLGPD> listarConsentimentosAtivos(Long clienteId) {
        return consentimentoRepository.findConsentimentosAtivosPorCliente(clienteId, LocalDateTime.now());
    }

    @Transactional
    public LgpdBaseLegal registrarBaseLegal(LgpdBaseLegal baseLegal) {
        log.info("Registrando base legal LGPD: {}", baseLegal.getNomeBaseLegal());
        baseLegal.setCodigoBaseLegal("BL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        if (baseLegal.getDataInicioVigencia() == null) {
            baseLegal.setDataInicioVigencia(LocalDateTime.now());
        }
        LgpdBaseLegal salva = baseLegalRepository.save(baseLegal);
        log.info("Base legal registrada: codigo={}", salva.getCodigoBaseLegal());
        return salva;
    }

    @Transactional(readOnly = true)
    public List<LgpdBaseLegal> listarBasesLegais() {
        return baseLegalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<LgpdBaseLegal> listarBasesLegaisPorCliente(Long clienteId) {
        return baseLegalRepository.findByClienteId(clienteId);
    }

    @Transactional(readOnly = true)
    public List<LgpdBaseLegal> listarBasesLegaisAtivas() {
        return baseLegalRepository.findBasesLegaisAtivas(LocalDateTime.now());
    }

    @Transactional
    public Map<String, Object> exportarDados(Long clienteId) {
        log.info("Exportando dados do cliente {} (portabilidade LGPD)", clienteId);

        Cliente cliente = entityManager.find(Cliente.class, clienteId);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente nao encontrado: " + clienteId);
        }

        Map<String, Object> dadosExportados = new HashMap<>();
        dadosExportados.put("cliente", converterClienteParaMap(cliente));
        dadosExportados.put("consentimentos", consentimentoRepository.findByClienteId(clienteId));
        dadosExportados.put("basesLegais", baseLegalRepository.findByClienteId(clienteId));
        dadosExportados.put("dataExportacao", LocalDateTime.now().toString());
        dadosExportados.put("formato", "JSON");

        registrarLogAcesso(clienteId, LgpdLogAcesso.TipoAcessoLgpd.EXPORTACAO_DADOS,
            "Exportacao completa dos dados do cliente via portabilidade LGPD",
            "SISTEMA", LgpdLogAcesso.ResultadoOperacaoLgpd.SUCESSO);

        log.info("Dados exportados com sucesso para cliente {}", clienteId);
        return dadosExportados;
    }

    @Transactional
    public void anonimizarDados(Long clienteId) {
        log.info("Iniciando anonimizacao dos dados do cliente {}", clienteId);

        Cliente cliente = entityManager.find(Cliente.class, clienteId);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente nao encontrado: " + clienteId);
        }

        String cpfAnonimizado = gerarHashAnonimo(cliente.getCpf());

        cliente.setNome("ANONIMO_" + clienteId);
        cliente.setNomeRazaoSocial(null);
        cliente.setNomeFantasia(null);
        cliente.setCpf(cpfAnonimizado);
        cliente.setCnpj(null);
        cliente.setEmail("anonimo_" + clienteId + "@lgpd.pseudonimizado");
        cliente.setTelefone("00000000000");
        cliente.setDataNascimento(null);
        cliente.setEndereco("{}");
        cliente.setCidade("ANONIMIZADO");
        cliente.setEstado("XX");
        cliente.setCep("00000000");
        cliente.setStatus(Cliente.StatusCliente.EXCLUIDO);
        entityManager.merge(cliente);

        anonimizarTransacoes(clienteId);
        anonimizarContas(clienteId);
        excluirUsuario(clienteId);

        cacheService.removerCliente(clienteId.toString());

        registrarLogAcesso(clienteId, LgpdLogAcesso.TipoAcessoLgpd.ANONIMIZACAO,
            "Anonimizacao/pseudonimizacao dos dados do cliente conforme LGPD Art. 16",
            "SISTEMA", LgpdLogAcesso.ResultadoOperacaoLgpd.SUCESSO);

        log.info("Anonimizacao concluida para cliente {}", clienteId);
    }

    @Transactional
    public void registrarLogAcesso(Long clienteId, LgpdLogAcesso.TipoAcessoLgpd tipo,
                                    String descricao, String responsavel,
                                    LgpdLogAcesso.ResultadoOperacaoLgpd resultado) {
        LgpdLogAcesso logAcesso = new LgpdLogAcesso();
        logAcesso.setClienteId(clienteId);
        logAcesso.setTipoAcesso(tipo);
        logAcesso.setDescricaoOperacao(descricao);
        logAcesso.setResponsavelOperacao(responsavel);
        logAcesso.setDataOperacao(LocalDateTime.now());
        logAcesso.setResultado(resultado);
        logAcessoRepository.save(logAcesso);
    }

    @Transactional(readOnly = true)
    public List<LgpdLogAcesso> listarLogAcessos(Long clienteId) {
        return logAcessoRepository.findHistoricoPorCliente(clienteId);
    }

    @Transactional(readOnly = true)
    public List<LgpdLogAcesso> listarLogAcessosPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return logAcessoRepository.findByPeriodo(inicio, fim);
    }

    @Transactional
    public void verificarConsentimentosExpirados() {
        List<ConsentimentoLGPD> expirados = consentimentoRepository.findConsentimentosExpirados(LocalDateTime.now());
        for (ConsentimentoLGPD c : expirados) {
            c.setStatus(ConsentimentoLGPD.StatusConsentimento.EXPIRADO);
            consentimentoRepository.save(c);
            log.info("Consentimento expirado automaticamente: {}", c.getCodigoConsentimento());
        }
    }

    private Map<String, Object> converterClienteParaMap(Cliente cliente) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", cliente.getId());
        map.put("nome", cliente.getNome());
        map.put("cpf", cliente.getCpf());
        map.put("cnpj", cliente.getCnpj());
        map.put("email", cliente.getEmail());
        map.put("telefone", cliente.getTelefone());
        map.put("dataNascimento", cliente.getDataNascimento());
        map.put("endereco", cliente.getEndereco());
        map.put("cidade", cliente.getCidade());
        map.put("estado", cliente.getEstado());
        map.put("cep", cliente.getCep());
        return map;
    }

    private String gerarHashAnonimo(String valor) {
        if (valor == null) {
            return "ANONIMO";
        }
        return "ANO_" + UUID.nameUUIDFromBytes(valor.getBytes()).toString().substring(0, 8).toUpperCase();
    }

    @Transactional
    public void excluirDadosCliente(Long clienteId) {
        log.info("Iniciando exclusao LGPD para clienteId={}", clienteId);

        Cliente cliente = entityManager.find(Cliente.class, clienteId);
        if (cliente == null) {
            log.warn("Cliente nao encontrado para exclusao LGPD: clienteId={}", clienteId);
            return;
        }

        String cpfAnonimizado = anonimizarCpf(cliente.getCpf());
        String cnpjAnonimizado = cliente.getCnpj() != null ? anonimizarCnpj(cliente.getCnpj()) : null;

        anonimizarTransacoes(clienteId);
        anonimizarContas(clienteId);
        excluirUsuario(clienteId);

        anonimizarCliente(cliente, cpfAnonimizado, cnpjAnonimizado);

        cacheService.removerCliente(clienteId.toString());

        registrarExclusao(clienteId, cpfAnonimizado, cnpjAnonimizado);

        eventPublisher.publish(Topics.LGPD_DADOS_EXCLUIDOS, new LgpdDadosExcluidosEvent(clienteId));

        log.info("Exclusao LGPD concluida para clienteId={}", clienteId);
    }

    private void anonimizarTransacoes(Long clienteId) {
        List<Conta> contas = entityManager.createQuery(
                "SELECT c FROM Conta c WHERE c.cliente.id = :clienteId", Conta.class)
            .setParameter("clienteId", clienteId)
            .getResultList();

        for (Conta conta : contas) {
            entityManager.createQuery(
                    "UPDATE Transacao t SET t.descricao = 'ANONIMIZADO LGPD', " +
                    "t.dadosPix = '{}', t.dadosTed = '{}' " +
                    "WHERE t.contaOrigem.id = :contaId OR t.contaDestino.id = :contaId")
                .setParameter("contaId", conta.getId())
                .executeUpdate();
        }

        entityManager.flush();
        log.info("Transacoes anonimizadas para clienteId={}", clienteId);
    }

    private void anonimizarContas(Long clienteId) {
        entityManager.createQuery(
                "UPDATE Conta c SET c.status = 'ENCERRADA', " +
                "c.dataFechamento = :dataFechamento, " +
                "c.dadosExtras = '{}' " +
                "WHERE c.cliente.id = :clienteId")
            .setParameter("dataFechamento", LocalDateTime.now())
            .setParameter("clienteId", clienteId)
            .executeUpdate();

        entityManager.flush();
        log.info("Contas anonimizadas para clienteId={}", clienteId);
    }

    private void excluirUsuario(Long clienteId) {
        List<Usuario> usuarios = entityManager.createQuery(
                "SELECT u FROM Usuario u WHERE u.cliente.id = :clienteId", Usuario.class)
            .setParameter("clienteId", clienteId)
            .getResultList();

        for (Usuario usuario : usuarios) {
            usuario.setNome("USUARIO_EXCLUIDO_LGPD");
            usuario.setEmail("excluido_" + usuario.getId() + "@lgpd.anonimizado");
            usuario.setSenha("ANONIMIZADO");
            usuario.setAtivo(false);
            usuario.setContaBloqueada(true);
            entityManager.merge(usuario);
        }

        entityManager.flush();
        log.info("Usuarios excluidos para clienteId={}", clienteId);
    }

    private void anonimizarCliente(Cliente cliente, String cpfAnonimizado, String cnpjAnonimizado) {
        cliente.setNome("CLIENTE_EXCLUIDO_LGPD");
        cliente.setNomeRazaoSocial(null);
        cliente.setNomeFantasia(null);
        cliente.setCpf(cpfAnonimizado);
        cliente.setCnpj(cnpjAnonimizado);
        cliente.setEmail("excluido_" + cliente.getId() + "@lgpd.anonimizado");
        cliente.setTelefone("00000000000");
        cliente.setDataNascimento(null);
        cliente.setEndereco("{}");
        cliente.setCidade("ANONIMIZADO");
        cliente.setEstado("XX");
        cliente.setCep("00000000");
        cliente.setContato(null);
        cliente.setInscricaoEstadual(null);
        cliente.setInscricaoMunicipal(null);
        cliente.setStatus(Cliente.StatusCliente.EXCLUIDO);
        entityManager.merge(cliente);

        entityManager.flush();
        log.info("Cliente anonimizado: clienteId={}", cliente.getId());
    }

    private void registrarExclusao(Long clienteId, String cpfAnonimizado, String cnpjAnonimizado) {
        LgpdExclusao registro = new LgpdExclusao();
        registro.setClienteId(clienteId);
        registro.setCpfAnonimizado(cpfAnonimizado);
        registro.setCnpjAnonimizado(cnpjAnonimizado);
        registro.setDataExclusao(LocalDateTime.now());
        registro.setMotivo("Solicitacao do titular - LGPD Art. 18");
        exclusaoRepository.save(registro);
    }

    private String anonimizarCpf(String cpf) {
        if (cpf == null || cpf.length() < 11) {
            return "00000000000";
        }
        return cpf.substring(0, 3) + "******" + cpf.substring(9);
    }

    private String anonimizarCnpj(String cnpj) {
        if (cnpj == null || cnpj.length() < 14) {
            return "00000000000000";
        }
        return cnpj.substring(0, 4) + "******" + cnpj.substring(12);
    }
}