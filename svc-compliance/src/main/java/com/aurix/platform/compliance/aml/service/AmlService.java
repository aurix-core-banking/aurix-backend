package com.aurix.platform.compliance.aml.service;

import com.aurix.platform.compliance.aml.entity.AmlAlerta;
import com.aurix.platform.compliance.aml.entity.AmlInvestigacao;
import com.aurix.platform.compliance.aml.entity.AmlRegra;
import com.aurix.platform.compliance.aml.repository.AmlAlertaRepository;
import com.aurix.platform.compliance.aml.repository.AmlInvestigacaoRepository;
import com.aurix.platform.compliance.aml.repository.AmlRegraRepository;
import com.aurix.platform.shared.entity.Transacao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class AmlService {

    private static final Logger log = LoggerFactory.getLogger(AmlService.class);

    @PersistenceContext
    private EntityManager entityManager;

    private final AmlRegraRepository regraRepository;
    private final AmlAlertaRepository alertaRepository;
    private final AmlInvestigacaoRepository investigacaoRepository;

    public AmlService(AmlRegraRepository regraRepository,
                      AmlAlertaRepository alertaRepository,
                      AmlInvestigacaoRepository investigacaoRepository) {
        this.regraRepository = regraRepository;
        this.alertaRepository = alertaRepository;
        this.investigacaoRepository = investigacaoRepository;
    }

    public AmlRegra criarRegra(AmlRegra regra) {
        log.info("Criando regra AML: {}", regra.getNomeRegra());
        regra.setCodigoRegra("AML-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        AmlRegra salva = regraRepository.save(regra);
        log.info("Regra AML criada: codigo={}", salva.getCodigoRegra());
        return salva;
    }

    @Transactional(readOnly = true)
    public List<AmlRegra> listarRegras() {
        return regraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<AmlRegra> listarRegrasAtivas() {
        return regraRepository.findRegrasAtivas(LocalDateTime.now());
    }

    public AmlRegra atualizarRegra(Long id, AmlRegra regraAtualizada) {
        AmlRegra regra = regraRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Regra AML nao encontrada: " + id));
        regra.setNomeRegra(regraAtualizada.getNomeRegra());
        regra.setDescricaoRegra(regraAtualizada.getDescricaoRegra());
        regra.setTipoRegra(regraAtualizada.getTipoRegra());
        regra.setStatus(regraAtualizada.getStatus());
        regra.setPesoScore(regraAtualizada.getPesoScore());
        regra.setLimiteAtivacao(regraAtualizada.getLimiteAtivacao());
        regra.setParametrosRegra(regraAtualizada.getParametrosRegra());
        regra.setExecutarAutomaticamente(regraAtualizada.getExecutarAutomaticamente());
        regra.setNotificarAlerta(regraAtualizada.getNotificarAlerta());
        return regraRepository.save(regra);
    }

    public void toggleRegra(Long id) {
        AmlRegra regra = regraRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Regra AML nao encontrada: " + id));
        if (regra.getStatus() == AmlRegra.StatusRegraAml.ATIVA) {
            regra.setStatus(AmlRegra.StatusRegraAml.INATIVA);
        } else {
            regra.setStatus(AmlRegra.StatusRegraAml.ATIVA);
        }
        regraRepository.save(regra);
    }

    @Transactional(readOnly = true)
    public List<AmlAlerta> listarAlertas() {
        return alertaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<AmlAlerta> listarAlertasAbertos() {
        return alertaRepository.findAlertasAbertos();
    }

    @Transactional(readOnly = true)
    public List<AmlAlerta> listarAlertasPorCliente(Long clienteId) {
        return alertaRepository.findByClienteId(clienteId);
    }

    public AmlAlerta iniciarInvestigacao(Long alertaId, String investigador) {
        log.info("Iniciando investigacao para alerta: {}, investigador: {}", alertaId, investigador);

        AmlAlerta alerta = alertaRepository.findById(alertaId)
            .orElseThrow(() -> new IllegalArgumentException("Alerta AML nao encontrado: " + alertaId));

        if (alerta.getStatus() != AmlAlerta.StatusAlertaAml.DETECTADO) {
            throw new IllegalStateException("Alerta ja esta em investigacao ou finalizado");
        }

        alerta.setStatus(AmlAlerta.StatusAlertaAml.EM_INVESTIGACAO);
        alerta.setDataInvestigacao(LocalDateTime.now());
        alerta.setInvestigadorResponsavel(investigador);
        alertaRepository.save(alerta);

        AmlInvestigacao investigacao = new AmlInvestigacao();
        investigacao.setCodigoInvestigacao("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        investigacao.setAlertaId(alertaId);
        investigacao.setCodigoAlerta(alerta.getCodigoAlerta());
        investigacao.setClienteId(alerta.getClienteId());
        investigacao.setStatus(AmlInvestigacao.StatusInvestigacao.INICIADA);
        investigacao.setDataInicio(LocalDateTime.now());
        investigacao.setDataPrazo(LocalDateTime.now().plusDays(5));
        investigacao.setInvestigadorResponsavel(investigador);
        investigacao.setDescricaoInvestigacao("Investigacao iniciada para alerta: " + alerta.getCodigoAlerta());
        AmlInvestigacao salva = investigacaoRepository.save(investigacao);

        log.info("Investigacao iniciada: codigo={}, alerta={}", salva.getCodigoInvestigacao(), alerta.getCodigoAlerta());
        return alerta;
    }

    public AmlAlerta resolverAlerta(Long alertaId, AmlInvestigacao.DecisaoInvestigacao decisao,
                                     String parecer, String motivoResolucao) {
        log.info("Resolvendo alerta AML: {}, decisao: {}", alertaId, decisao);

        AmlAlerta alerta = alertaRepository.findById(alertaId)
            .orElseThrow(() -> new IllegalArgumentException("Alerta AML nao encontrado: " + alertaId));

        alerta.setStatus(AmlAlerta.StatusAlertaAml.RESOLVIDO);
        alerta.setDataResolucao(LocalDateTime.now());
        alerta.setParecerInvestigacao(parecer);
        alerta.setMotivoResolucao(motivoResolucao);
        alertaRepository.save(alerta);

        List<AmlInvestigacao> investigacoes = investigacaoRepository.findByAlertaId(alertaId);
        for (AmlInvestigacao inv : investigacoes) {
            if (inv.getStatus() == AmlInvestigacao.StatusInvestigacao.INICIADA ||
                inv.getStatus() == AmlInvestigacao.StatusInvestigacao.EM_ANDAMENTO) {
                inv.setStatus(AmlInvestigacao.StatusInvestigacao.CONCLUIDA);
                inv.setDataConclusao(LocalDateTime.now());
                inv.setParecerInvestigador(parecer);
                inv.setDecisaoFinal(decisao);
                inv.setJustificativaDecisao(motivoResolucao);
                if (decisao == AmlInvestigacao.DecisaoInvestigacao.CONFIRMADO_SUSPEITA ||
                    decisao == AmlInvestigacao.DecisaoInvestigacao.ENCAMINHADO_COAF) {
                    inv.setReportadoCoaf(true);
                    inv.setDataReporteCoaf(LocalDateTime.now());
                }
                investigacaoRepository.save(inv);
            }
        }

        log.info("Alerta AML resolvido: {}, decisao: {}", alerta.getCodigoAlerta(), decisao);
        return alerta;
    }

    public AmlAlerta arquivarAlerta(Long alertaId, String justificativa) {
        AmlAlerta alerta = alertaRepository.findById(alertaId)
            .orElseThrow(() -> new IllegalArgumentException("Alerta AML nao encontrado: " + alertaId));
        alerta.setStatus(AmlAlerta.StatusAlertaAml.ARQUIVADO);
        alerta.setDataResolucao(LocalDateTime.now());
        alerta.setMotivoResolucao("ARQUIVADO: " + justificativa);
        alertaRepository.save(alerta);
        return alerta;
    }

    @Transactional(readOnly = true)
    public List<AmlInvestigacao> listarInvestigacoes() {
        return investigacaoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<AmlInvestigacao> listarInvestigacoesAbertas() {
        return investigacaoRepository.findByStatus(AmlInvestigacao.StatusInvestigacao.INICIADA);
    }

    public List<AmlAlerta> executarRegrasAutomaticas() {
        log.info("Executando regras automaticas AML...");
        List<AmlRegra> regrasExecutaveis = regraRepository.findRegrasExecutaveis();
        List<AmlAlerta> novosAlertas = new java.util.ArrayList<>();

        for (AmlRegra regra : regrasExecutaveis) {
            try {
                List<AmlAlerta> alertasRegra = executarRegra(regra);
                novosAlertas.addAll(alertasRegra);
            } catch (Exception e) {
                log.error("Erro ao executar regra AML {}: {}", regra.getCodigoRegra(), e.getMessage());
            }
        }

        log.info("Regras AML executadas. Novos alertas: {}", novosAlertas.size());
        return novosAlertas;
    }

    private List<AmlAlerta> executarRegra(AmlRegra regra) {
        List<AmlAlerta> alertas = new java.util.ArrayList<>();

        switch (regra.getTipoRegra()) {
            case VALOR_LIMITE:
                alertas.addAll(executarRegraValorLimite(regra));
                break;
            case FREQUENCIA_DIARIA:
                alertas.addAll(executarRegraFrequenciaDiaria(regra));
                break;
            case PARAISO_FISCAL:
                alertas.addAll(executarRegraParaisoFiscal(regra));
                break;
            case PAD_DEPOSITO:
                alertas.addAll(executarRegraPadDeposito(regra));
                break;
            case STRUCTURING:
                alertas.addAll(executarRegraStructuring(regra));
                break;
            case PAIS_ALTO_RISCO:
                alertas.addAll(executarRegraPaisAltoRisco(regra));
                break;
            case MOVIMENTACAO_INCOMPATIVEL:
                alertas.addAll(executarRegraMovimentacaoIncompativel(regra));
                break;
            case LISTAS_PEP:
                alertas.addAll(executarRegraListasPep(regra));
                break;
            case ANALISE_REDES:
                alertas.addAll(executarRegraAnaliseRedes(regra));
                break;
            case ALERTA_MANUAL:
                log.debug("Regra de alerta manual: apenas registro direto");
                break;
            default:
                log.warn("Tipo de regra nao implementado: {}", regra.getTipoRegra());
        }

        return alertas;
    }

    private List<AmlAlerta> executarRegraValorLimite(AmlRegra regra) {
        List<AmlAlerta> alertas = new java.util.ArrayList<>();
        BigDecimal limite = new BigDecimal("50000.00");

        List<Transacao> transacoesGrandes = entityManager.createQuery(
                "SELECT t FROM Transacao t WHERE t.valor >= :limite AND t.dataTransacao >= :dataInicio", Transacao.class)
            .setParameter("limite", limite)
            .setParameter("dataInicio", LocalDateTime.now().minusDays(1))
            .getResultList();

        for (Transacao t : transacoesGrandes) {
            alertas.add(criarAlerta(
                t.getClienteOrigem() != null ? t.getClienteOrigem().getId() : null,
                t.getClienteOrigem() != null ? t.getClienteOrigem().getCpf() : "N/A",
                t.getClienteOrigem() != null ? t.getClienteOrigem().getNome() : "N/A",
                regra.getId(), regra.getNomeRegra(),
                AmlAlerta.TipoAlertaAml.TRANSACAO_VALOR_ALTO,
                calcularScore(70, regra.getPesoScore()),
                "Transacao com valor R$ " + t.getValor() + " acima do limite de R$ " + limite,
                t.getValor()
            ));
        }
        return alertas;
    }

    private List<AmlAlerta> executarRegraFrequenciaDiaria(AmlRegra regra) {
        List<AmlAlerta> alertas = new java.util.ArrayList<>();

        List<Object[]> clientesFrequentes = entityManager.createQuery(
                "SELECT t.clienteOrigem.id, t.clienteOrigem.cpf, t.clienteOrigem.nome, COUNT(t) " +
                "FROM Transacao t WHERE t.dataTransacao >= :dataInicio " +
                "GROUP BY t.clienteOrigem.id, t.clienteOrigem.cpf, t.clienteOrigem.nome " +
                "HAVING COUNT(t) > :limite", Object[].class)
            .setParameter("dataInicio", LocalDateTime.now().toLocalDate().atStartOfDay())
            .setParameter("limite", 10)
            .getResultList();

        for (Object[] resultado : clientesFrequentes) {
            Long clienteId = (Long) resultado[0];
            String cpf = (String) resultado[1];
            String nome = (String) resultado[2];
            Long contagem = (Long) resultado[3];

            alertas.add(criarAlerta(
                clienteId, cpf, nome,
                regra.getId(), regra.getNomeRegra(),
                AmlAlerta.TipoAlertaAml.FREQUENCIA_EXCESSIVA,
                calcularScore(60, regra.getPesoScore()),
                "Cliente realizou " + contagem + " transacoes no dia (limite: 10)",
                null
            ));
        }
        return alertas;
    }

    private List<AmlAlerta> executarRegraParaisoFiscal(AmlRegra regra) {
        List<AmlAlerta> alertas = new java.util.ArrayList<>();

        List<Transacao> transacoesInternacionais = entityManager.createQuery(
                "SELECT t FROM Transacao t WHERE t.tipo = 'TRANSFERENCIA_INTERNACIONAL' " +
                "AND t.dataTransacao >= :dataInicio", Transacao.class)
            .setParameter("dataInicio", LocalDateTime.now().minusDays(1))
            .getResultList();

        String[] paraisosFiscais = {"KY", "BM", "VG", "PA", "LU", "CH", "SG", "HK", "AE", "BZ"};

        for (Transacao t : transacoesInternacionais) {
            String paisDestino = t.getPaisDestino();
            if (paisDestino != null) {
                for (String paraiso : paraisosFiscais) {
                    if (paraiso.equalsIgnoreCase(paisDestino)) {
                        alertas.add(criarAlerta(
                            t.getClienteOrigem() != null ? t.getClienteOrigem().getId() : null,
                            t.getClienteOrigem() != null ? t.getClienteOrigem().getCpf() : "N/A",
                            t.getClienteOrigem() != null ? t.getClienteOrigem().getNome() : "N/A",
                            regra.getId(), regra.getNomeRegra(),
                            AmlAlerta.TipoAlertaAml.PARAISO_FISCAL,
                            calcularScore(80, regra.getPesoScore()),
                            "Transferencia para paraiso fiscal: " + paisDestino,
                            t.getValor()
                        ));
                        break;
                    }
                }
            }
        }
        return alertas;
    }

    private List<AmlAlerta> executarRegraPadDeposito(AmlRegra regra) {
        List<AmlAlerta> alertas = new java.util.ArrayList<>();

        List<Object[]> padDepositos = entityManager.createQuery(
                "SELECT t.clienteOrigem.id, t.clienteOrigem.cpf, t.clienteOrigem.nome, " +
                "COUNT(t), SUM(t.valor) " +
                "FROM Transacao t WHERE t.tipo = 'DEPOSITO' " +
                "AND t.dataTransacao BETWEEN :dataInicio AND :dataFim " +
                "AND t.valor < :limiteSupior AND t.valor > :limiteInferior " +
                "GROUP BY t.clienteOrigem.id, t.clienteOrigem.cpf, t.clienteOrigem.nome " +
                "HAVING COUNT(t) >= :minDepositos", Object[].class)
            .setParameter("dataInicio", LocalDateTime.now().minusDays(7).toLocalDate().atStartOfDay())
            .setParameter("dataFim", LocalDateTime.now())
            .setParameter("limiteSupior", new BigDecimal("9999.99"))
            .setParameter("limiteInferior", new BigDecimal("1000.00"))
            .setParameter("minDepositos", 3L)
            .getResultList();

        for (Object[] resultado : padDepositos) {
            Long clienteId = (Long) resultado[0];
            String cpf = (String) resultado[1];
            String nome = (String) resultado[2];
            Long contagem = (Long) resultado[3];
            BigDecimal soma = (BigDecimal) resultado[4];

            alertas.add(criarAlerta(
                clienteId, cpf, nome,
                regra.getId(), regra.getNomeRegra(),
                AmlAlerta.TipoAlertaAml.PAD_DEPOSITO,
                calcularScore(75, regra.getPesoScore()),
                "PAD detectado: " + contagem + " depositos entre R$1k-R$9.999 totalizando R$ " + soma,
                soma
            ));
        }
        return alertas;
    }

    private List<AmlAlerta> executarRegraStructuring(AmlRegra regra) {
        List<AmlAlerta> alertas = new java.util.ArrayList<>();

        List<Object[]> estruturacoes = entityManager.createQuery(
                "SELECT t.clienteOrigem.id, t.clienteOrigem.cpf, t.clienteOrigem.nome, " +
                "COUNT(t), SUM(t.valor) " +
                "FROM Transacao t WHERE t.dataTransacao >= :dataInicio " +
                "GROUP BY t.clienteOrigem.id, t.clienteOrigem.cpf, t.clienteOrigem.nome " +
                "HAVING COUNT(t) >= :minTransacoes AND SUM(t.valor) >= :valorTotal " +
                "AND COUNT(t) * 1.0 / 1.0 > :mediaTransacoes", Object[].class)
            .setParameter("dataInicio", LocalDateTime.now().minusDays(1))
            .setParameter("minTransacoes", 5)
            .setParameter("valorTotal", new BigDecimal("45000.00"))
            .setParameter("mediaTransacoes", 10.0)
            .getResultList();

        for (Object[] resultado : estruturacoes) {
            Long clienteId = (Long) resultado[0];
            String cpf = (String) resultado[1];
            String nome = (String) resultado[2];
            Long contagem = (Long) resultado[3];
            BigDecimal soma = (BigDecimal) resultado[4];

            if (contagem < 10 && soma.compareTo(new BigDecimal("50000.00")) < 0) {
                alertas.add(criarAlerta(
                    clienteId, cpf, nome,
                    regra.getId(), regra.getNomeRegra(),
                    AmlAlerta.TipoAlertaAml.STRUCTURING,
                    calcularScore(85, regra.getPesoScore()),
                    "Possivel structuring: " + contagem + " transacoes fragmentadas totalizando R$ " + soma,
                    soma
                ));
            }
        }
        return alertas;
    }

    private List<AmlAlerta> executarRegraPaisAltoRisco(AmlRegra regra) {
        List<AmlAlerta> alertas = new java.util.ArrayList<>();

        List<Transacao> transacoes = entityManager.createQuery(
                "SELECT t FROM Transacao t WHERE t.tipo = 'TRANSFERENCIA_INTERNACIONAL' " +
                "AND t.dataTransacao >= :dataInicio", Transacao.class)
            .setParameter("dataInicio", LocalDateTime.now().minusDays(1))
            .getResultList();

        String[] paisesRisco = {"IR", "KP", "SY", "CU", "VE", "MM", "BY", "RU", "CN"};

        for (Transacao t : transacoes) {
            String pais = t.getPaisDestino();
            if (pais != null) {
                for (String risco : paisesRisco) {
                    if (risco.equalsIgnoreCase(pais)) {
                        alertas.add(criarAlerta(
                            t.getClienteOrigem() != null ? t.getClienteOrigem().getId() : null,
                            t.getClienteOrigem() != null ? t.getClienteOrigem().getCpf() : "N/A",
                            t.getClienteOrigem() != null ? t.getClienteOrigem().getNome() : "N/A",
                            regra.getId(), regra.getNomeRegra(),
                            AmlAlerta.TipoAlertaAml.PAIS_ALTO_RISCO,
                            calcularScore(90, regra.getPesoScore()),
                            "Transferencia para pais de alto risco: " + pais,
                            t.getValor()
                        ));
                        break;
                    }
                }
            }
        }
        return alertas;
    }

    private List<AmlAlerta> executarRegraMovimentacaoIncompativel(AmlRegra regra) {
        List<AmlAlerta> alertas = new java.util.ArrayList<>();

        List<Object[]> movimentacoes = entityManager.createQuery(
                "SELECT t.clienteOrigem.id, t.clienteOrigem.cpf, t.clienteOrigem.nome, " +
                "SUM(t.valor) " +
                "FROM Transacao t WHERE t.dataTransacao >= :dataInicio " +
                "GROUP BY t.clienteOrigem.id, t.clienteOrigem.cpf, t.clienteOrigem.nome " +
                "HAVING SUM(t.valor) > :limiteIncompativel", Object[].class)
            .setParameter("dataInicio", LocalDateTime.now().minusDays(30))
            .setParameter("limiteIncompativel", new BigDecimal("100000.00"))
            .getResultList();

        for (Object[] resultado : movimentacoes) {
            Long clienteId = (Long) resultado[0];
            String cpf = (String) resultado[1];
            String nome = (String) resultado[2];
            BigDecimal soma = (BigDecimal) resultado[3];

            alertas.add(criarAlerta(
                clienteId, cpf, nome,
                regra.getId(), regra.getNomeRegra(),
                AmlAlerta.TipoAlertaAml.MOVIMENTACAO_INCOMPATIVEL,
                calcularScore(70, regra.getPesoScore()),
                "Movimentacao mensal de R$ " + soma + " incompativel com perfil declarado",
                soma
            ));
        }
        return alertas;
    }

    private List<AmlAlerta> executarRegraListasPep(AmlRegra regra) {
        List<AmlAlerta> alertas = new java.util.ArrayList<>();

        List<Long> clientesPep = entityManager.createQuery(
                "SELECT p.clienteId FROM PepCliente p WHERE p.status = 'ATIVO'", Long.class)
            .getResultList();

        for (Long clienteId : clientesPep) {
            boolean jaAlertado = alertaRepository.findAlertasNaoArquivadosPorCliente(clienteId).stream()
                .anyMatch(a -> a.getTipoAlerta() == AmlAlerta.TipoAlertaAml.LISTA_PEP);

            if (!jaAlertado) {
                alertas.add(criarAlerta(
                    clienteId, "N/A", "PEP",
                    regra.getId(), regra.getNomeRegra(),
                    AmlAlerta.TipoAlertaAml.LISTA_PEP,
                    calcularScore(65, regra.getPesoScore()),
                    "Cliente classificado como PEP detectado na base",
                    null
                ));
            }
        }
        return alertas;
    }

    private List<AmlAlerta> executarRegraAnaliseRedes(AmlRegra regra) {
        List<AmlAlerta> alertas = new java.util.ArrayList<>();

        List<Object[]> contasMesmoIspb = entityManager.createQuery(
                "SELECT t.clienteOrigem.id, t.clienteOrigem.cpf, t.clienteOrigem.nome, " +
                "t.ispbOrigem, COUNT(DISTINCT t.clienteDestino.id) " +
                "FROM Transacao t WHERE t.dataTransacao >= :dataInicio AND t.ispbOrigem IS NOT NULL " +
                "GROUP BY t.clienteOrigem.id, t.clienteOrigem.cpf, t.clienteOrigem.nome, t.ispbOrigem " +
                "HAVING COUNT(DISTINCT t.clienteDestino.id) > :limiteConexoes", Object[].class)
            .setParameter("dataInicio", LocalDateTime.now().minusDays(7))
            .setParameter("limiteConexoes", 20)
            .getResultList();

        for (Object[] resultado : contasMesmoIspb) {
            Long clienteId = (Long) resultado[0];
            String cpf = (String) resultado[1];
            String nome = (String) resultado[2];
            Long conexoes = (Long) resultado[4];

            alertas.add(criarAlerta(
                clienteId, cpf, nome,
                regra.getId(), regra.getNomeRegra(),
                AmlAlerta.TipoAlertaAml.ANALISE_REDE,
                calcularScore(70, regra.getPesoScore()),
                "Rede suspeita: " + conexoes + " destinatarios unicos na mesma ISPB em 7 dias",
                null
            ));
        }
        return alertas;
    }

    private AmlAlerta criarAlerta(Long clienteId, String cpfCnpj, String nomeCliente,
                                   Long regraId, String nomeRegra,
                                   AmlAlerta.TipoAlertaAml tipoAlerta,
                                   Integer scoreRisco, String descricao, BigDecimal valorTransacao) {
        AmlAlerta alerta = new AmlAlerta();
        alerta.setCodigoAlerta("ALT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        alerta.setClienteId(clienteId);
        alerta.setCpfCnpj(cpfCnpj);
        alerta.setNomeCliente(nomeCliente);
        alerta.setRegraId(regraId);
        alerta.setNomeRegra(nomeRegra);
        alerta.setTipoAlerta(tipoAlerta);
        alerta.setStatus(AmlAlerta.StatusAlertaAml.DETECTADO);
        alerta.setScoreRisco(scoreRisco);
        alerta.setDataDeteccao(LocalDateTime.now());
        alerta.setValorTransacao(valorTransacao);
        alerta.setDescricaoAlerta(descricao);
        alerta.setDetalhesDeteccao("Detectado automaticamente pela regra: " + nomeRegra);

        log.warn("Alerta AML gerado: tipo={}, score={}, cliente={}", tipoAlerta, scoreRisco, cpfCnpj);
        return alertaRepository.save(alerta);
    }

    private Integer calcularScore(int baseScore, int pesoRegra) {
        int score = baseScore + (pesoRegra / 2);
        return Math.min(Math.max(score, 0), 100);
    }

    @Transactional(readOnly = true)
    public long contarAlertasAbertos() {
        return alertaRepository.countAlertasAbertos();
    }

    @Transactional(readOnly = true)
    public long contarInvestigacoesAbertas() {
        return investigacaoRepository.countInvestigacoesAbertas();
    }

    @Transactional(readOnly = true)
    public List<AmlAlerta> listarAlertasPorScoreMinimo(Integer scoreMinimo) {
        return alertaRepository.findAlertasPorScoreMinimo(scoreMinimo);
    }
}
