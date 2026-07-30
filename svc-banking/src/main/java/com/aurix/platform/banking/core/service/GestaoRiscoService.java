package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.AvaliacaoRiscoDTO;
import com.aurix.platform.banking.core.entity.AlertaRisco;
import com.aurix.platform.banking.core.entity.AvaliacaoRisco;
import com.aurix.platform.banking.core.entity.EventoRisco;
import com.aurix.platform.banking.core.entity.RegraRisco;
import com.aurix.platform.banking.core.repository.AlertaRiscoRepository;
import com.aurix.platform.banking.core.repository.AvaliacaoRiscoRepository;
import com.aurix.platform.banking.core.repository.ContaRepository;
import com.aurix.platform.banking.core.repository.EventoRiscoRepository;
import com.aurix.platform.banking.core.repository.RegraRiscoRepository;
import com.aurix.platform.banking.core.repository.TransacaoRepository;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.Transacao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela gestão e avaliação de risco.
 */
@Service
@Transactional
public class GestaoRiscoService {

    /**
     * Repositório de regras de risco.
     */
    private final RegraRiscoRepository regraRiscoRepository;

    /**
     * Repositório de avaliações de risco.
     */
    private final AvaliacaoRiscoRepository avaliacaoRiscoRepository;

    /**
     * Repositório de alertas de risco.
     */
    private final AlertaRiscoRepository alertaRiscoRepository;

    /**
     * Repositório de eventos de risco.
     */
    private final EventoRiscoRepository eventoRiscoRepository;

    /**
     * Repositório de contas.
     */
    private final ContaRepository contaRepository;

    /**
     * Repositório de transações.
     */
    private final TransacaoRepository transacaoRepository;

    /**
     * Construtor com injeção de dependências.
     *
     * @param regraRiscoRepo     Repositório de regras de risco.
     * @param avaliacaoRiscoRepo Repositório de avaliações de risco.
     * @param alertaRiscoRepo    Repositório de alertas de risco.
     * @param eventoRiscoRepo    Repositório de eventos de risco.
     * @param contaRepo          Repositório de contas.
     * @param transacaoRepo      Repositório de transações.
     */
    public GestaoRiscoService(
            final RegraRiscoRepository regraRiscoRepo,
            final AvaliacaoRiscoRepository avaliacaoRiscoRepo,
            final AlertaRiscoRepository alertaRiscoRepo,
            final EventoRiscoRepository eventoRiscoRepo,
            final ContaRepository contaRepo,
            final TransacaoRepository transacaoRepo) {
        this.regraRiscoRepository = regraRiscoRepo;
        this.avaliacaoRiscoRepository = avaliacaoRiscoRepo;
        this.alertaRiscoRepository = alertaRiscoRepo;
        this.eventoRiscoRepository = eventoRiscoRepo;
        this.contaRepository = contaRepo;
        this.transacaoRepository = transacaoRepo;
    }

    /**
     * Score máximo aleatório.
     */
    private static final int MAX_RANDOM_SCORE = 1000;

    /**
     * Score baixo.
     */
    private static final int SCORE_BAIXO = 20;

    /**
     * Score médio.
     */
    private static final int SCORE_MEDIO = 50;

    /**
     * Score alto.
     */
    private static final int SCORE_ALTO = 80;

    /**
     * Score médio alto.
     */
    private static final int SCORE_MEDIO_ALTO = 40;

    /**
     * Limite de quantidade recente.
     */
    private static final int LIMITE_QUANTIDADE_RECENTE = 20;

    /**
     * Limite suspeito recente.
     */
    private static final int LIMITE_SUSPEITO_RECENTE = 5;

    /**
     * Histórico mínimo para anomalia.
     */
    private static final int HISTORICO_MIN_ANOMALIA = 10;

    /**
     * Histórico mínimo para conta.
     */
    private static final int HISTORICO_MIN_CONTA = 5;

    /**
     * Histórico mínimo para fraude.
     */
    private static final int HISTORICO_MIN_FRAUDE = 3;

    /**
     * Minutos considerados recentes.
     */
    private static final int MINUTOS_RECENTE = 5;

    /**
     * Minutos para histórico.
     */
    private static final int MINUTOS_HISTORICO = 60;

    /**
     * Horas no dia.
     */
    private static final int HORAS_DIA = 24;

    /**
     * Fator de desvio padrão.
     */
    private static final BigDecimal FATOR_DESVIO_PADRAO = BigDecimal.valueOf(3);

    /**
     * Fator de limite diário.
     */
    private static final BigDecimal FATOR_LIMITE_DIARIO = BigDecimal.valueOf(0.8);

    /**
     * Limite para lavagem de dinheiro.
     */
    private static final BigDecimal LIMITE_LAVAGEM_DINHEIRO = BigDecimal.valueOf(10000);

    /**
     * Limite de compliance alto.
     */
    private static final BigDecimal LIMITE_COMPLIANCE_ALTO = BigDecimal.valueOf(30000);

    /**
     * Limite de compliance total.
     */
    private static final BigDecimal LIMITE_COMPLIANCE_TOTAL = BigDecimal.valueOf(100000);

    /**
     * Saldo de compliance da conta.
     */
    private static final BigDecimal SALDO_COMPLIANCE_CONTA = BigDecimal.valueOf(50000);

    /**
     * Valor suspeito.
     */
    private static final BigDecimal VALOR_SUSPEITO = BigDecimal.valueOf(1000);

    /**
     * Divisor por cem.
     */
    private static final BigDecimal DIVISOR_CEM = BigDecimal.valueOf(100);

    /**
     * Peso do score médio.
     */
    private static final int PESO_SCORE_MEDIO = 10;

    /**
     * Peso do score alto.
     */
    private static final int PESO_SCORE_ALTO = 20;

    /**
     * Peso do score crítico.
     */
    private static final int PESO_SCORE_CRITICO = 30;

    /**
     * Valor de corte médio.
     */
    private static final BigDecimal VALOR_CORTE_MEDIO = BigDecimal.valueOf(1000);

    /**
     * Valor de corte alto.
     */
    private static final BigDecimal VALOR_CORTE_ALTO = BigDecimal.valueOf(5000);

    /**
     * Valor de corte crítico.
     */
    private static final BigDecimal VALOR_CORTE_CRITICO = BigDecimal.valueOf(10000);

    /**
     * Gerador de números aleatórios.
     */
    private final Random random = new Random();

    /**
     * Avalia o risco de uma transação específica.
     *
     * @param transacaoId ID da transação.
     * @return DTO com o resultado da avaliação de risco.
     */
    public AvaliacaoRiscoDTO avaliarRiscoTransacao(final Long transacaoId) {
        try {
            // 1. Buscar transação
            Optional<Transacao> transacaoOpt = transacaoRepository
                    .findById(transacaoId);
            if (transacaoOpt.isEmpty()) {
                throw new RuntimeException("Transação não encontrada");
            }

            Transacao transacao = transacaoOpt.get();

            // 2. Buscar conta
            Optional<Conta> contaOpt = contaRepository
                    .findById(transacao.getContaOrigem().getId());
            if (contaOpt.isEmpty()) {
                throw new RuntimeException("Conta não encontrada");
            }

            Conta conta = contaOpt.get();

            // 3. Criar avaliação de risco
            AvaliacaoRisco avaliacao = new AvaliacaoRisco();
            avaliacao.setCodigoAvaliacao(gerarCodigoAvaliacao());
            avaliacao.setConta(conta);
            avaliacao.setTransacao(transacao);
            avaliacao.setTipoAvaliacao(AvaliacaoRisco.TipoAvaliacao.TRANSACAO);
            avaliacao.setStatus(AvaliacaoRisco.StatusAvaliacao.PENDENTE);
            avaliacao.setValorTransacao(transacao.getValor());
            avaliacao.setDataAvaliacao(LocalDateTime.now());
            avaliacao.setSistemaOrigem("Aurix-CORE");
            avaliacao.setCodigoTransacao(transacao.getCodigoTransacao());

            // 4. Aplicar regras de risco
            aplicarRegrasRisco(avaliacao, transacao, conta);

            // 5. Calcular score de risco
            calcularScoreRisco(avaliacao);

            // 6. Determinar nível de risco
            determinarNivelRisco(avaliacao);

            // 7. Determinar ações necessárias
            determinarAcoesNecessarias(avaliacao);

            // 8. Salvar avaliação
            avaliacao = avaliacaoRiscoRepository.save(avaliacao);

            // 9. Gerar alertas se necessário
            gerarAlertasRisco(avaliacao);

            // 10. Criar evento de risco se necessário
            criarEventoRisco(avaliacao);

            return AvaliacaoRiscoDTO.fromEntity(avaliacao);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao avaliar risco da transação: " + e.getMessage());
        }
    }

    /**
     * Avalia o risco geral de uma conta.
     *
     * @param contaId ID da conta.
     * @return DTO com o resultado da avaliação de risco.
     */
    public AvaliacaoRiscoDTO avaliarRiscoConta(final Long contaId) {
        try {
            // 1. Buscar conta
            Optional<Conta> contaOpt = contaRepository.findById(contaId);
            if (contaOpt.isEmpty()) {
                throw new RuntimeException("Conta não encontrada");
            }

            Conta conta = contaOpt.get();

            // 2. Criar avaliação de risco
            AvaliacaoRisco avaliacao = new AvaliacaoRisco();
            avaliacao.setCodigoAvaliacao(gerarCodigoAvaliacao());
            avaliacao.setConta(conta);
            avaliacao.setTipoAvaliacao(AvaliacaoRisco.TipoAvaliacao.CONTA);
            avaliacao.setStatus(AvaliacaoRisco.StatusAvaliacao.PENDENTE);
            avaliacao.setDataAvaliacao(LocalDateTime.now());
            avaliacao.setSistemaOrigem("Aurix-CORE");

            // 3. Aplicar regras de risco para conta
            aplicarRegrasRiscoConta(avaliacao, conta);

            // 4. Calcular score de risco
            calcularScoreRisco(avaliacao);

            // 5. Determinar nível de risco
            determinarNivelRisco(avaliacao);

            // 6. Determinar ações necessárias
            determinarAcoesNecessarias(avaliacao);

            // 7. Salvar avaliação
            avaliacao = avaliacaoRiscoRepository.save(avaliacao);

            // 8. Gerar alertas se necessário
            gerarAlertasRisco(avaliacao);

            // 9. Criar evento de risco se necessário
            criarEventoRisco(avaliacao);

            return AvaliacaoRiscoDTO.fromEntity(avaliacao);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao avaliar risco da conta: " + e.getMessage());
        }
    }

    /**
     * Aplica regras de risco em uma avaliação de transação.
     *
     * @param avaliacao Entidade da avaliação.
     * @param transacao Entidade da transação.
     * @param conta     Entidade da conta.
     */
    private void aplicarRegrasRisco(final AvaliacaoRisco avaliacao,
            final Transacao transacao,
            final Conta conta) {
        // Buscar regras ativas
        List<RegraRisco> regras = regraRiscoRepository
                .findRegrasVigentes(LocalDateTime.now());

        int scoreTotal = 0;
        StringBuilder regrasAplicadas = new StringBuilder();

        for (RegraRisco regra : regras) {
            if (aplicarRegra(regra, transacao, conta)) {
                scoreTotal += regra.getPesoRegra();
                regrasAplicadas.append(regra.getNomeRegra()).append(";");

                // Verificar se requer aprovação
                if (regra.getRequerAprovacao()) {
                    avaliacao.setRequerAprovacao(true);
                }

                // Verificar se requer documentação
                if (regra.getRequerDocumentacao()) {
                    avaliacao.setRequerDocumentacao(true);
                }

                // Verificar se requer biometria
                if (regra.getRequerBiometria()) {
                    avaliacao.setRequerBiometria(true);
                }

                // Verificar se requer token
                if (regra.getRequerToken()) {
                    avaliacao.setRequerToken(true);
                }

                // Verificar se requer assinatura digital
                if (regra.getRequerAssinaturaDigital()) {
                    avaliacao.setRequerAssinaturaDigital(true);
                }
            }
        }

        avaliacao.setScoreRisco(scoreTotal);
        avaliacao.setRegrasAplicadas(regrasAplicadas.toString());
    }

    /**
     * Aplica regras de risco em uma avaliação de conta.
     *
     * @param avaliacao Entidade da avaliação.
     * @param conta     Entidade da conta.
     */
    private void aplicarRegrasRiscoConta(final AvaliacaoRisco avaliacao,
            final Conta conta) {
        // Buscar regras ativas para conta
        List<RegraRisco> regras = regraRiscoRepository
                .findRegrasVigentes(LocalDateTime.now());

        int scoreTotal = 0;
        StringBuilder regrasAplicadas = new StringBuilder();

        for (RegraRisco regra : regras) {
            if (aplicarRegraConta(regra, conta)) {
                scoreTotal += regra.getPesoRegra();
                regrasAplicadas.append(regra.getNomeRegra()).append(";");

                // Verificar se requer aprovação
                if (regra.getRequerAprovacao()) {
                    avaliacao.setRequerAprovacao(true);
                }

                // Verificar se requer documentação
                if (regra.getRequerDocumentacao()) {
                    avaliacao.setRequerDocumentacao(true);
                }

                // Verificar se requer biometria
                if (regra.getRequerBiometria()) {
                    avaliacao.setRequerBiometria(true);
                }

                // Verificar se requer token
                if (regra.getRequerToken()) {
                    avaliacao.setRequerToken(true);
                }

                // Verificar se requer assinatura digital
                if (regra.getRequerAssinaturaDigital()) {
                    avaliacao.setRequerAssinaturaDigital(true);
                }
            }
        }

        avaliacao.setScoreRisco(scoreTotal);
        avaliacao.setRegrasAplicadas(regrasAplicadas.toString());
    }

    /**
     * Aplica uma regra de risco específica em uma transação.
     *
     * @param regra     Configuração da regra.
     * @param transacao Entidade da transação.
     * @param conta     Entidade da conta.
     * @return True se a regra foi violada/aplicada, false caso contrário.
     */
    private boolean aplicarRegra(final RegraRisco regra,
            final Transacao transacao,
            final Conta conta) {
        switch (regra.getTipoRegra()) {
            case VALOR:
                if (regra.getValorLimite() != null) {
                    return transacao.getValor()
                            .compareTo(regra.getValorLimite()) > 0;
                }
                return false;

            case QUANTIDADE:
                if (regra.getQuantidadeLimite() != null) {
                    LocalDateTime inicio = LocalDateTime.now()
                            .minusHours(HORAS_DIA);
                    List<Transacao> transacoesRecentes = transacaoRepository
                            .findByContaIdEPeriodo(
                                    conta.getId(), inicio, LocalDateTime.now());
                    return transacoesRecentes.size() >= regra.getQuantidadeLimite();
                }
                return false;

            case FREQUENCIA:
                if (regra.getTempoLimiteMinutos() != null
                        && regra.getQuantidadeLimite() != null) {
                    LocalDateTime inicio = LocalDateTime.now()
                            .minusMinutes(regra.getTempoLimiteMinutos());
                    List<Transacao> transacoesRecentes = transacaoRepository
                            .findByContaIdEPeriodo(
                                    conta.getId(), inicio, LocalDateTime.now());
                    return transacoesRecentes.size() >= regra.getQuantidadeLimite();
                }
                return false;

            case HORARIO:
                int horaAtual = LocalDateTime.now().getHour();
                String condicoes = regra.getCondicoes();
                if (condicoes != null && condicoes.contains("horario")) {
                    String[] partes = condicoes.split(";");
                    for (String parte : partes) {
                        if (parte.startsWith("horario=")) {
                            String[] horarios = parte.split("=")[1].split("-");
                            int inicio = Integer.parseInt(horarios[0]);
                            int fim = Integer.parseInt(horarios[1]);
                            return horaAtual < inicio || horaAtual > fim;
                        }
                    }
                }
                return false;

            case COMPORTAMENTO:
                return analisarComportamentoAnormal(conta, transacao);

            case HISTORICO:
                return analisarHistoricoTransacoes(conta, transacao);

            case FRAUDE:
                return detectarPadraoFraude(conta, transacao);

            case LAVAGEM_DINHEIRO:
                return detectarLavagemDinheiro(conta, transacao);

            case COMPLIANCE:
                return verificarCompliance(conta, transacao);

            default:
                return false;
        }
    }

    /**
     * Analisa se uma transação representa comportamento anormal.
     *
     * @param conta     Entidade da conta.
     * @param transacao Entidade da transação.
     * @return True se for anormal, false caso contrário.
     */
    private boolean analisarComportamentoAnormal(final Conta conta,
            final Transacao transacao) {
        List<Transacao> historico = transacaoRepository
                .findByContaId(conta.getId());
        if (historico.size() < HISTORICO_MIN_ANOMALIA) {
            return false;
        }

        BigDecimal valorMedio = historico.stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(historico.size()), 2,
                        RoundingMode.HALF_UP);

        BigDecimal desvioPadrao = calcularDesvioPadrao(historico, valorMedio);
        BigDecimal limiteSuperior = valorMedio.add(desvioPadrao
                .multiply(FATOR_DESVIO_PADRAO));

        return transacao.getValor().compareTo(limiteSuperior) > 0;
    }

    /**
     * Calcula o desvio padrão dos valores de transação.
     *
     * @param transacoes Lista de transações.
     * @param media      Média calculada.
     * @return Valor do desvio padrão.
     */
    private BigDecimal calcularDesvioPadrao(final List<Transacao> transacoes,
            final BigDecimal media) {
        BigDecimal somaQuadrados = transacoes.stream()
                .map(t -> t.getValor().subtract(media).pow(2))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal variancia = somaQuadrados.divide(
                BigDecimal.valueOf(transacoes.size()), 4, RoundingMode.HALF_UP);
        return BigDecimal.valueOf(Math.sqrt(variancia.doubleValue()));
    }

    /**
     * Analisa o histórico de transações em busca de riscos.
     *
     * @param conta     Entidade da conta.
     * @param transacao Entidade da transação.
     * @return True se houver risco histórico, false caso contrário.
     */
    private boolean analisarHistoricoTransacoes(final Conta conta,
            final Transacao transacao) {
        LocalDateTime ultimas24h = LocalDateTime.now().minusHours(HORAS_DIA);
        List<Transacao> recentes = transacaoRepository.findByContaIdEPeriodo(
                conta.getId(), ultimas24h, LocalDateTime.now());

        BigDecimal total24h = recentes.stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal limiteDiario = conta.getLimiteCredito()
                .multiply(FATOR_LIMITE_DIARIO);
        return total24h.add(transacao.getValor()).compareTo(limiteDiario) > 0;
    }

    /**
     * Tenta detectar padrões de fraude conhecidos.
     *
     * @param conta     Entidade da conta.
     * @param transacao Entidade da transação.
     * @return True se detectar possível fraude, false caso contrário.
     */
    private boolean detectarPadraoFraude(final Conta conta,
            final Transacao transacao) {
        List<Transacao> ultimasTransacoes = transacaoRepository
                .findByContaId(conta.getId());
        if (ultimasTransacoes.size() < HISTORICO_MIN_FRAUDE) {
            return false;
        }

        List<Transacao> ultimas3 = ultimasTransacoes.stream()
                .limit(3)
                .toList();

        boolean mesmoValor = ultimas3.stream()
                .allMatch(t -> t.getValor().equals(transacao.getValor()));

        boolean intervaloCurto = ultimas3.stream()
                .allMatch(t -> t.getDataTransacao()
                        .isAfter(LocalDateTime.now()
                                .minusMinutes(MINUTOS_RECENTE)));

        return mesmoValor && intervaloCurto;
    }

    /**
     * Tenta detectar padrões de lavagem de dinheiro (AML).
     *
     * @param conta     Entidade da conta.
     * @param transacao Entidade da transação.
     * @return True se suspeito, false caso contrário.
     */
    private boolean detectarLavagemDinheiro(final Conta conta,
            final Transacao transacao) {
        if (transacao.getValor().compareTo(LIMITE_LAVAGEM_DINHEIRO) < 0) {
            return false;
        }

        // Padrão de estruturação/lavagem exige concentração temporal (ver
        // analisarComportamentoConta, que usa a mesma janela de HORAS_DIA).
        // Antes esta contagem não filtrava por período, então qualquer conta
        // com 5 transações grandes em toda a sua história — mesmo espalhadas
        // ao longo de anos — era marcada como suspeita permanentemente.
        LocalDateTime inicio = LocalDateTime.now().minusHours(HORAS_DIA);
        List<Transacao> historico = transacaoRepository
                .findByContaId(conta.getId());
        long transacoesGrandes = historico.stream()
                .filter(t -> t.getDataTransacao() != null
                        && t.getDataTransacao().isAfter(inicio))
                .filter(t -> t.getValor()
                        .compareTo(LIMITE_LAVAGEM_DINHEIRO) > 0)
                .count();

        return transacoesGrandes >= LIMITE_SUSPEITO_RECENTE;
    }

    /**
     * Verifica requisitos de compliance regulatório.
     *
     * @param conta     Entidade da conta.
     * @param transacao Entidade da transação.
     * @return True se houver pendência de compliance, false caso contrário.
     */
    private boolean verificarCompliance(final Conta conta,
            final Transacao transacao) {
        if (transacao.getValor().compareTo(LIMITE_COMPLIANCE_ALTO) > 0) {
            return true;
        }

        List<Transacao> ultimasTransacoes = transacaoRepository
                .findByContaId(conta.getId());
        BigDecimal totalMes = ultimasTransacoes.stream()
                .filter(t -> t.getDataTransacao()
                        .isAfter(LocalDateTime.now().minusMonths(1)))
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalMes.add(transacao.getValor())
                .compareTo(LIMITE_COMPLIANCE_TOTAL) > 0;
    }

    /**
     * Aplica uma regra de risco específica em uma conta.
     *
     * @param regra Configuração da regra.
     * @param conta Entidade da conta.
     * @return True se a regra foi violada, false caso contrário.
     */
    private boolean aplicarRegraConta(final RegraRisco regra,
            final Conta conta) {
        switch (regra.getTipoRegra()) {
            case VALOR:
                if (regra.getValorLimite() != null) {
                    return conta.getSaldoAtual().compareTo(regra.getValorLimite()) > 0;
                }
                return false;

            case HISTORICO:
                List<Transacao> historico = transacaoRepository.findByContaId(conta.getId());
                if (regra.getQuantidadeLimite() != null) {
                    return historico.size() >= regra.getQuantidadeLimite();
                }
                return false;

            case COMPORTAMENTO:
                return analisarComportamentoConta(conta);

            case COMPLIANCE:
                return verificarComplianceConta(conta);

            case FRAUDE:
                return detectarFraudeConta(conta);

            default:
                return false;
        }
    }

    /**
     * Analisa o comportamento geral da conta.
     *
     * @param conta Entidade da conta.
     * @return True se for suspeito, false caso contrário.
     */
    private boolean analisarComportamentoConta(final Conta conta) {
        List<Transacao> historico = transacaoRepository
                .findByContaId(conta.getId());
        if (historico.size() < HISTORICO_MIN_CONTA) {
            return false;
        }

        long transacoesUltimas24h = historico.stream()
                .filter(t -> t.getDataTransacao()
                        .isAfter(LocalDateTime.now().minusHours(HORAS_DIA)))
                .count();

        return transacoesUltimas24h > LIMITE_QUANTIDADE_RECENTE;
    }

    /**
     * Verifica compliance geral da conta.
     *
     * @param conta Entidade da conta.
     * @return True se houver risco de compliance, false caso contrário.
     */
    private boolean verificarComplianceConta(final Conta conta) {
        return conta.getSaldoAtual().compareTo(SALDO_COMPLIANCE_CONTA) > 0;
    }

    /**
     * Tenta detectar fraudes relacionadas à conta.
     *
     * @param conta Entidade da conta.
     * @return True se suspeito, false caso contrário.
     */
    private boolean detectarFraudeConta(final Conta conta) {
        List<Transacao> historico = transacaoRepository
                .findByContaId(conta.getId());
        if (historico.size() < HISTORICO_MIN_FRAUDE) {
            return false;
        }

        long transacoesSuspeitas = historico.stream()
                .filter(t -> t.getDataTransacao().isAfter(LocalDateTime.now()
                        .minusMinutes(MINUTOS_HISTORICO)))
                .filter(t -> t.getValor().compareTo(VALOR_SUSPEITO) > 0)
                .count();

        return transacoesSuspeitas >= LIMITE_SUSPEITO_RECENTE;
    }

    /**
     * Realiza o cálculo final do score de risco da avaliação.
     *
     * @param avaliacao Entidade da avaliação.
     */
    private void calcularScoreRisco(final AvaliacaoRisco avaliacao) {
        // Implementar lógica de cálculo de score de risco
        // Por enquanto, score básico baseado no valor da transação

        if (avaliacao.getValorTransacao() != null) {
            if (avaliacao.getValorTransacao().compareTo(VALOR_CORTE_MEDIO) > 0) {
                avaliacao.setScoreRisco(avaliacao.getScoreRisco()
                        + PESO_SCORE_MEDIO);
            }
            if (avaliacao.getValorTransacao().compareTo(VALOR_CORTE_ALTO) > 0) {
                avaliacao.setScoreRisco(avaliacao.getScoreRisco()
                        + PESO_SCORE_ALTO);
            }
            if (avaliacao.getValorTransacao()
                    .compareTo(VALOR_CORTE_CRITICO) > 0) {
                avaliacao.setScoreRisco(avaliacao.getScoreRisco()
                        + PESO_SCORE_CRITICO);
            }
        }

        // Aplicar regras de score
        List<RegraRisco> regras = regraRiscoRepository
                .findRegrasVigentes(LocalDateTime.now());
        for (RegraRisco regra : regras) {
            if (regra.getScoreMinimo() != null
                    && regra.getScoreMaximo() != null
                    && avaliacao.getScoreRisco() >= regra.getScoreMinimo()
                    && avaliacao.getScoreRisco() <= regra.getScoreMaximo()) {
                avaliacao.setScoreRisco(avaliacao.getScoreRisco()
                        + regra.getPesoRegra());
            }
        }
    }

    /**
     * Determina o nível de risco baseado no score final.
     *
     * @param avaliacao Entidade da avaliação.
     */
    private void determinarNivelRisco(final AvaliacaoRisco avaliacao) {
        // Determinar nível de risco baseado no score
        if (avaliacao.getScoreRisco() <= SCORE_BAIXO) {
            avaliacao.setNivelRisco(AvaliacaoRisco.NivelRisco.BAIXO);
        } else if (avaliacao.getScoreRisco() <= SCORE_MEDIO) {
            avaliacao.setNivelRisco(AvaliacaoRisco.NivelRisco.MEDIO);
        } else if (avaliacao.getScoreRisco() <= SCORE_ALTO) {
            avaliacao.setNivelRisco(AvaliacaoRisco.NivelRisco.ALTO);
        } else {
            avaliacao.setNivelRisco(AvaliacaoRisco.NivelRisco.CRITICO);
        }

        // Calcular percentual de risco
        avaliacao.setPercentualRisco(BigDecimal.valueOf(
                avaliacao.getScoreRisco()).divide(DIVISOR_CEM, 4,
                        RoundingMode.HALF_UP));
    }

    /**
     * Determina as ações extras necessárias baseado no risco (ex: biometria).
     *
     * @param avaliacao Entidade da avaliação.
     */
    private void determinarAcoesNecessarias(final AvaliacaoRisco avaliacao) {
        // Determinar ações necessárias baseadas no nível de risco
        switch (avaliacao.getNivelRisco()) {
            case BAIXO:
                // Nenhuma ação especial necessária
                break;
            case MEDIO:
                // Pode requerer aprovação
                if (avaliacao.getScoreRisco() > SCORE_MEDIO_ALTO) {
                    avaliacao.setRequerAprovacao(true);
                }
                break;
            case ALTO:
                // Requer aprovação e documentação
                avaliacao.setRequerAprovacao(true);
                avaliacao.setRequerDocumentacao(true);
                break;
            case CRITICO:
                // Requer todas as validações
                avaliacao.setRequerAprovacao(true);
                avaliacao.setRequerDocumentacao(true);
                avaliacao.setRequerBiometria(true);
                avaliacao.setRequerToken(true);
                avaliacao.setRequerAssinaturaDigital(true);
                break;

            default:
                break;
        }
    }

    /**
     * Gera alertas de risco para o monitoramento operacional.
     *
     * @param avaliacao Entidade da avaliação.
     */
    private void gerarAlertasRisco(final AvaliacaoRisco avaliacao) {
        // Gerar alertas baseados no nível de risco
        boolean isHighRisk = avaliacao.getNivelRisco()
                .equals(AvaliacaoRisco.NivelRisco.ALTO)
                || avaliacao.getNivelRisco()
                        .equals(AvaliacaoRisco.NivelRisco.CRITICO);

        if (isHighRisk) {

            AlertaRisco alerta = new AlertaRisco();
            alerta.setCodigoAlerta(gerarCodigoAlerta());
            alerta.setAvaliacaoRisco(avaliacao);
            alerta.setConta(avaliacao.getConta());
            alerta.setTransacao(avaliacao.getTransacao());
            alerta.setTipoAlerta(AlertaRisco.TipoAlerta.FRAUDE);
            alerta.setNivelAlerta(AlertaRisco.NivelAlerta.ALTO);
            alerta.setStatus(AlertaRisco.StatusAlerta.ATIVO);
            alerta.setTitulo("Alerta de Risco - "
                    + avaliacao.getNivelRisco().name());
            alerta.setDescricao("Transação com nível de risco "
                    + avaliacao.getNivelRisco().name() + " detectada");
            alerta.setDataAlerta(LocalDateTime.now());
            alerta.setCritico(avaliacao.getNivelRisco()
                    .equals(AvaliacaoRisco.NivelRisco.CRITICO));
            alerta.setUrgente(avaliacao.getNivelRisco()
                    .equals(AvaliacaoRisco.NivelRisco.CRITICO));
            alerta.setRequerAcao(true);
            alerta.setRequerNotificacao(true);
            alerta.setRequerEscalacao(avaliacao.getNivelRisco()
                    .equals(AvaliacaoRisco.NivelRisco.CRITICO));
            alerta.setRequerAuditoria(true);
            alerta.setScoreRisco(avaliacao.getScoreRisco());
            alerta.setSistemaOrigem("Aurix-CORE");
            alerta.setCodigoTransacao(avaliacao.getCodigoTransacao());

            alertaRiscoRepository.save(alerta);
        }
    }

    /**
     * Cria um evento de risco para fins de auditoria e resposta.
     *
     * @param avaliacao Entidade da avaliação.
     */
    private void criarEventoRisco(final AvaliacaoRisco avaliacao) {
        // Criar evento de risco se necessário
        boolean isHighRisk = avaliacao.getNivelRisco()
                .equals(AvaliacaoRisco.NivelRisco.ALTO)
                || avaliacao.getNivelRisco()
                        .equals(AvaliacaoRisco.NivelRisco.CRITICO);

        if (isHighRisk) {

            EventoRisco evento = new EventoRisco();
            evento.setCodigoEvento(gerarCodigoEvento());
            evento.setConta(avaliacao.getConta());
            evento.setTransacao(avaliacao.getTransacao());
            evento.setTipoEvento(EventoRisco.TipoEvento.FRAUDE);
            evento.setCategoriaEvento(EventoRisco.CategoriaEvento.DETECTIVA);
            evento.setNivelRisco(EventoRisco.NivelRisco
                    .valueOf(avaliacao.getNivelRisco().name()));
            evento.setTitulo("Evento de Risco - "
                    + avaliacao.getNivelRisco().name());
            evento.setDescricao("Evento de risco "
                    + avaliacao.getNivelRisco().name() + " detectado");
            evento.setDataEvento(LocalDateTime.now());
            evento.setDataDetecao(LocalDateTime.now());
            evento.setValorEnvolvido(avaliacao.getValorTransacao());
            evento.setScoreRisco(avaliacao.getScoreRisco());
            evento.setCritico(avaliacao.getNivelRisco()
                    .equals(AvaliacaoRisco.NivelRisco.CRITICO));
            evento.setUrgente(avaliacao.getNivelRisco()
                    .equals(AvaliacaoRisco.NivelRisco.CRITICO));
            evento.setRequerAcao(true);
            evento.setRequerNotificacao(true);
            evento.setRequerEscalacao(avaliacao.getNivelRisco()
                    .equals(AvaliacaoRisco.NivelRisco.CRITICO));
            evento.setRequerAuditoria(true);
            evento.setSistemaOrigem("Aurix-CORE");
            evento.setCodigoTransacao(avaliacao.getCodigoTransacao());

            eventoRiscoRepository.save(evento);
        }
    }

    /**
     * Lista avaliações de risco de uma conta.
     *
     * @param contaId ID da conta.
     * @return Lista de DTOs das avaliações.
     */
    public List<AvaliacaoRiscoDTO> listarAvaliacoesPorConta(final Long contaId) {
        List<AvaliacaoRisco> avaliacoes = avaliacaoRiscoRepository.findByContaId(contaId);
        return avaliacoes.stream()
                .map(AvaliacaoRiscoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<AvaliacaoRiscoDTO> listarAvaliacoesPendentes() {
        List<AvaliacaoRisco> avaliacoes = avaliacaoRiscoRepository
                .findByStatus(AvaliacaoRisco.StatusAvaliacao.PENDENTE);
        return avaliacoes.stream()
                .map(AvaliacaoRiscoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<AvaliacaoRiscoDTO> listarAvaliacoesCriticas() {
        List<AvaliacaoRisco> avaliacoes = avaliacaoRiscoRepository
                .findByNivelRisco(AvaliacaoRisco.NivelRisco.CRITICO);
        return avaliacoes.stream()
                .map(AvaliacaoRiscoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Aprova manualmente uma avaliação de risco.
     *
     * @param avaliacaoId      ID da avaliação.
     * @param usuarioAprovador Usuário responsável pela aprovação.
     * @return DTO atualizado.
     */
    public AvaliacaoRiscoDTO aprovarAvaliacao(final Long avaliacaoId,
            final String usuarioAprovador) {
        Optional<AvaliacaoRisco> avaliacaoOpt = avaliacaoRiscoRepository.findById(avaliacaoId);
        if (avaliacaoOpt.isEmpty()) {
            throw new RuntimeException("Avaliação não encontrada");
        }

        AvaliacaoRisco avaliacao = avaliacaoOpt.get();
        avaliacao.setStatus(AvaliacaoRisco.StatusAvaliacao.APROVADA);
        avaliacao.setAprovada(true);
        avaliacao.setDataAprovacao(LocalDateTime.now());
        avaliacao.setUsuarioAprovador(usuarioAprovador);

        avaliacao = avaliacaoRiscoRepository.save(avaliacao);

        return AvaliacaoRiscoDTO.fromEntity(avaliacao);
    }

    /**
     * Rejeita manualmente uma avaliação de risco.
     *
     * @param avaliacaoId       ID da avaliação.
     * @param usuarioRejeitador Usuário responsável pela rejeição.
     * @param justificativa     Motivo da rejeição.
     * @return DTO atualizado.
     */
    public AvaliacaoRiscoDTO rejeitarAvaliacao(final Long avaliacaoId,
            final String usuarioRejeitador,
            final String justificativa) {
        Optional<AvaliacaoRisco> avaliacaoOpt = avaliacaoRiscoRepository.findById(avaliacaoId);
        if (avaliacaoOpt.isEmpty()) {
            throw new RuntimeException("Avaliação não encontrada");
        }

        AvaliacaoRisco avaliacao = avaliacaoOpt.get();
        avaliacao.setStatus(AvaliacaoRisco.StatusAvaliacao.REJEITADA);
        avaliacao.setRejeitada(true);
        avaliacao.setDataRejeicao(LocalDateTime.now());
        avaliacao.setUsuarioRejeitador(usuarioRejeitador);
        avaliacao.setJustificativa(justificativa);

        avaliacao = avaliacaoRiscoRepository.save(avaliacao);

        return AvaliacaoRiscoDTO.fromEntity(avaliacao);
    }

    private String gerarCodigoAvaliacao() {
        return "AVAL-" + System.currentTimeMillis() + "-"
                + random.nextInt(MAX_RANDOM_SCORE);
    }

    private String gerarCodigoAlerta() {
        return "ALERT-" + System.currentTimeMillis() + "-"
                + random.nextInt(MAX_RANDOM_SCORE);
    }

    private String gerarCodigoEvento() {
        return "EVENT-" + System.currentTimeMillis() + "-"
                + random.nextInt(MAX_RANDOM_SCORE);
    }
}
