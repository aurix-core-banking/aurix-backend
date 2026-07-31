package com.aurix.platform.compliance.service;

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
import java.util.List;

@Service
public class LgpdService {

    private static final Logger log = LoggerFactory.getLogger(LgpdService.class);

    @PersistenceContext
    private EntityManager entityManager;

    private final LgpdExclusaoRepository exclusaoRepository;
    private final SharedCacheService cacheService;
    private final EventPublisher eventPublisher;

    public LgpdService(LgpdExclusaoRepository exclusaoRepository,
                       SharedCacheService cacheService,
                       EventPublisher eventPublisher) {
        this.exclusaoRepository = exclusaoRepository;
        this.cacheService = cacheService;
        this.eventPublisher = eventPublisher;
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