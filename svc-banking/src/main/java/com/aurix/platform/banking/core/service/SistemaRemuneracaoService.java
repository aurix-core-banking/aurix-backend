package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.AplicacaoFinanceiraDTO;
import com.aurix.platform.banking.core.dto.ProdutoFinanceiroDTO;
import com.aurix.platform.banking.core.entity.AplicacaoFinanceira;
import com.aurix.platform.banking.core.entity.HistoricoRemuneracao;
import com.aurix.platform.banking.core.entity.ProdutoFinanceiro;
import com.aurix.platform.banking.core.entity.Remuneracao;
import com.aurix.platform.banking.core.repository.AplicacaoFinanceiraRepository;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.banking.core.repository.HistoricoRemuneracaoRepository;
import com.aurix.platform.banking.core.repository.ProdutoFinanceiroRepository;
import com.aurix.platform.banking.core.repository.RemuneracaoRepository;
import com.aurix.platform.shared.entity.Conta;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelo sistema de remuneração de aplicações financeiras.
 */
@Service
@Transactional
public class SistemaRemuneracaoService {

    /**
     * Logger da classe.
     */
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SistemaRemuneracaoService.class);

    /**
     * Valor máximo para o código aleatório.
     */
    private static final int MAX_RANDOM_CODE = 1000000;

    /**
     * Fator híbrido de remuneração.
     */
    private static final BigDecimal FATOR_HIBRIDO = BigDecimal.valueOf(0.5);

    /**
     * Quantidade de dias no ano.
     */
    private static final BigDecimal DIAS_ANO = BigDecimal.valueOf(365);

    /**
     * Valor de um dia.
     */
    private static final int UM_DIA = 1;

    /**
     * Repositório de produtos financeiros.
     */
    private final ProdutoFinanceiroRepository produtoFinanceiroRepository;

    /**
     * Repositório de aplicações financeiras.
     */
    private final AplicacaoFinanceiraRepository aplicacaoFinanceiraRepository;

    /**
     * Repositório de remunerações.
     */
    private final RemuneracaoRepository remuneracaoRepository;

    /**
     * Repositório de histórico de remuneração.
     */
    private final HistoricoRemuneracaoRepository historicoRemuneracaoRepository;

    /**
     * Repositório de contas.
     */
    private final ContaRepository contaRepository;

    /**
     * Gerador de números aleatórios.
     */
    private final Random random = new Random();

    /**
     * Construtor com injeção de dependências.
     *
     * @param produtoRepo     Repositório de produtos financeiros.
     * @param aplicacaoRepo   Repositório de aplicações financeiras.
     * @param remuneracaoRepo Repositório de remunerações.
     * @param historicoRepo   Repositório de histórico de remuneração.
     * @param contaRepo       Repositório de contas.
     */
    public SistemaRemuneracaoService(
            final ProdutoFinanceiroRepository produtoRepo,
            final AplicacaoFinanceiraRepository aplicacaoRepo,
            final RemuneracaoRepository remuneracaoRepo,
            final HistoricoRemuneracaoRepository historicoRepo,
            final ContaRepository contaRepo) {
        this.produtoFinanceiroRepository = produtoRepo;
        this.aplicacaoFinanceiraRepository = aplicacaoRepo;
        this.remuneracaoRepository = remuneracaoRepo;
        this.historicoRemuneracaoRepository = historicoRepo;
        this.contaRepository = contaRepo;
    }

    /**
     * Cria uma nova aplicação financeira.
     *
     * @param contaId             ID da conta investidora.
     * @param produtoFinanceiroId ID do produto financeiro.
     * @param valorAplicacao      Valor a ser aplicado.
     * @param usuarioAplicacao    Usuário que realizou a aplicação.
     * @return DTO com os dados da aplicação criada.
     */
    public AplicacaoFinanceiraDTO criarAplicacao(final Long contaId,
            final Long produtoFinanceiroId,
            final BigDecimal valorAplicacao,
            final String usuarioAplicacao) {
        try {
            // 1. Validar conta
            Optional<Conta> contaOpt = contaRepository.findById(contaId);
            if (contaOpt.isEmpty()) {
                throw new RuntimeException("Conta não encontrada");
            }

            Conta conta = contaOpt.get();

            // 2. Validar produto financeiro
            Optional<ProdutoFinanceiro> produtoOpt = produtoFinanceiroRepository
                    .findById(produtoFinanceiroId);
            if (produtoOpt.isEmpty()) {
                throw new RuntimeException("Produto financeiro não encontrado");
            }

            ProdutoFinanceiro produto = produtoOpt.get();

            // 3. Validar se produto está ativo
            if (!produto.getAtivo()) {
                throw new RuntimeException("Produto financeiro não está ativo");
            }

            // 4. Validar valor mínimo e máximo
            if (produto.getValorMinimoAplicacao() != null
                    && valorAplicacao.compareTo(
                            produto.getValorMinimoAplicacao()) < 0) {
                throw new RuntimeException(
                        "Valor da aplicação é menor que o valor mínimo permitido");
            }

            if (produto.getValorMaximoAplicacao() != null
                    && valorAplicacao.compareTo(
                            produto.getValorMaximoAplicacao()) > 0) {
                throw new RuntimeException(
                        "Valor da aplicação é maior que o valor máximo permitido");
            }

            // 5. Validar saldo da conta
            if (conta.getSaldoAtual().compareTo(valorAplicacao) < 0) {
                throw new RuntimeException("Saldo insuficiente na conta");
            }

            // 6. Criar aplicação financeira
            AplicacaoFinanceira aplicacao = new AplicacaoFinanceira();
            aplicacao.setCodigoAplicacao(gerarCodigoAplicacao());
            aplicacao.setConta(conta);
            aplicacao.setProdutoFinanceiro(produto);
            aplicacao.setStatus(AplicacaoFinanceira.StatusAplicacao.ATIVA);
            aplicacao.setValorAplicacao(valorAplicacao);
            aplicacao.setValorAtual(valorAplicacao);
            aplicacao.setTaxaRemuneracao(produto.getTaxaRemuneracao());
            aplicacao.setTaxaRemuneracaoAtual(produto.getTaxaRemuneracao());
            aplicacao.setDataAplicacao(LocalDateTime.now());
            aplicacao.setDataVencimento(calcularDataVencimento(produto));
            aplicacao.setDataProximaRemuneracao(
                    calcularProximaRemuneracao(produto));
            aplicacao.setPrazoDias(produto.getPrazoMinimoDias());
            aplicacao.setDiasDecorridos(0);
            aplicacao.setDiasRestantes(produto.getPrazoMinimoDias());
            aplicacao.setPermiteResgateAntecipado(produto.getPermiteResgateAntecipado());
            aplicacao.setTaxaResgateAntecipado(produto.getTaxaResgateAntecipado());
            aplicacao.setRenovacaoAutomatica(produto.getPermiteRenovacao());
            aplicacao.setReaplicacaoAutomatica(produto.getPermiteReaplicacao());
            aplicacao.setUsuarioAplicacao(usuarioAplicacao);
            aplicacao.setSistemaOrigem("Aurix-CORE");

            // 7. Salvar aplicação
            aplicacao = aplicacaoFinanceiraRepository.save(aplicacao);

            // 8. Atualizar saldo da conta
            conta.setSaldoAtual(conta.getSaldoAtual().subtract(valorAplicacao));
            contaRepository.save(conta);

            // 9. Criar histórico
            criarHistoricoRemuneracao(aplicacao,
                    HistoricoRemuneracao.TipoEvento.APLICACAO, valorAplicacao,
                    valorAplicacao, BigDecimal.ZERO, usuarioAplicacao);

            return AplicacaoFinanceiraDTO.fromEntity(aplicacao);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro inesperado ao criar aplicação financeira: "
                            + e.getMessage());
        }
    }

    /**
     * Realiza o resgate de uma aplicação financeira.
     *
     * @param aplicacaoId    ID da aplicação.
     * @param valorResgate   Valor a ser resgatado.
     * @param usuarioResgate Usuário que realizou o resgate.
     * @return DTO com os dados da aplicação atualizada.
     */
    public AplicacaoFinanceiraDTO resgatarAplicacao(final Long aplicacaoId,
            final BigDecimal valorResgate,
            final String usuarioResgate) {
        try {
            // 1. Buscar aplicação
            Optional<AplicacaoFinanceira> aplicacaoOpt = aplicacaoFinanceiraRepository.findById(aplicacaoId);
            if (aplicacaoOpt.isEmpty()) {
                throw new RuntimeException("Aplicação financeira não encontrada");
            }

            AplicacaoFinanceira aplicacao = aplicacaoOpt.get();

            // 2. Validar se aplicação está ativa
            if (!aplicacao.getStatus()
                    .equals(AplicacaoFinanceira.StatusAplicacao.ATIVA)) {
                throw new RuntimeException("Aplicação não está ativa");
            }

            // 3. Validar valor do resgate
            if (valorResgate.compareTo(aplicacao.getValorAtual()) > 0) {
                throw new RuntimeException(
                        "Valor do resgate é maior que o valor"
                                + " atual da aplicação");
            }

            // 4. Calcular remuneração até o momento
            BigDecimal remuneracao = calcularRemuneracaoAplicacao(aplicacao);

            // 5. Atualizar aplicação
            aplicacao.setStatus(AplicacaoFinanceira.StatusAplicacao.RESGATADA);
            aplicacao.setValorResgate(valorResgate);
            aplicacao.setValorRendimento(remuneracao);
            aplicacao.setDataResgate(LocalDateTime.now());
            aplicacao.setUsuarioResgate(usuarioResgate);

            aplicacao = aplicacaoFinanceiraRepository.save(aplicacao);

            // 7. Atualizar saldo da conta
            Conta conta = aplicacao.getConta();
            conta.setSaldoAtual(conta.getSaldoAtual().add(valorResgate));
            contaRepository.save(conta);

            // 8. Criar remuneração
            criarRemuneracao(aplicacao, remuneracao, usuarioResgate);

            // 9. Criar histórico
            criarHistoricoRemuneracao(aplicacao,
                    HistoricoRemuneracao.TipoEvento.RESGATE,
                    aplicacao.getValorAtual(), BigDecimal.ZERO,
                    remuneracao, usuarioResgate);

            return AplicacaoFinanceiraDTO.fromEntity(aplicacao);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao resgatar aplicação financeira: " + e.getMessage());
        }
    }

    public void processarRemuneracoes() {
        try {
            // Buscar aplicações que precisam de remuneração
            List<AplicacaoFinanceira> aplicacoes = aplicacaoFinanceiraRepository
                    .findAplicacoesParaRemuneracao(LocalDateTime.now());

            for (AplicacaoFinanceira aplicacao : aplicacoes) {
                processarRemuneracaoAplicacao(aplicacao);
            }

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar remunerações: "
                    + e.getMessage());
        }
    }

    /**
     * Lógica interna para processar a remuneração de uma aplicação específica.
     *
     * @param aplicacao Entidade da aplicação.
     */
    private void processarRemuneracaoAplicacao(final AplicacaoFinanceira aplicacao) {
        try {
            // Calcular remuneração
            BigDecimal remuneracao = calcularRemuneracaoAplicacao(aplicacao);

            if (remuneracao.compareTo(BigDecimal.ZERO) > 0) {
                // Atualizar valor atual da aplicação
                aplicacao.setValorAtual(aplicacao.getValorAtual()
                        .add(remuneracao));
                aplicacao.setValorRendimento(aplicacao.getValorRendimento()
                        .add(remuneracao));
                aplicacao.setDataUltimaRemuneracao(LocalDateTime.now());
                aplicacao.setDataProximaRemuneracao(
                        calcularProximaRemuneracao(
                                aplicacao.getProdutoFinanceiro()));

                aplicacaoFinanceiraRepository.save(aplicacao);

                // Criar remuneração
                criarRemuneracao(aplicacao, remuneracao, "SISTEMA");

                // Criar histórico
                criarHistoricoRemuneracao(aplicacao,
                        HistoricoRemuneracao.TipoEvento.REMUNERACAO,
                        aplicacao.getValorAtual().subtract(remuneracao),
                        aplicacao.getValorAtual(), remuneracao, "SISTEMA");
            }

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao processar remuneração da aplicação: "
                            + e.getMessage());
        }
    }

    /**
     * Calcula o valor da remuneração de uma aplicação até a data atual.
     *
     * @param aplicacao Entidade da aplicação.
     * @return Valor calculado da remuneração.
     */
    private BigDecimal calcularRemuneracaoAplicacao(
            final AplicacaoFinanceira aplicacao) {
        try {
            ProdutoFinanceiro produto = aplicacao.getProdutoFinanceiro();
            BigDecimal valorBase = aplicacao.getValorAtual();
            BigDecimal taxa = aplicacao.getTaxaRemuneracaoAtual();

            if (taxa == null || taxa.compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimal.ZERO;
            }

            // Calcular dias decorridos desde a última remuneração
            LocalDateTime dataReferencia = aplicacao
                    .getDataUltimaRemuneracao() != null
                            ? aplicacao.getDataUltimaRemuneracao()
                            : aplicacao.getDataAplicacao();

            long diasDecorridos = ChronoUnit.DAYS
                    .between(dataReferencia, LocalDateTime.now());

            if (diasDecorridos <= 0) {
                return BigDecimal.ZERO;
            }

            // Calcular remuneração baseada no tipo
            BigDecimal remuneracao = BigDecimal.ZERO;

            switch (produto.getTipoRemuneracao()) {
                case FIXA:
                    remuneracao = calcularRemuneracaoFixa(valorBase, taxa,
                            diasDecorridos);
                    break;
                case VARIAVEL:
                    remuneracao = calcularRemuneracaoVariavel(valorBase, taxa,
                            diasDecorridos);
                    break;
                case HIBRIDA:
                    remuneracao = calcularRemuneracaoHibrida(valorBase, taxa,
                            diasDecorridos);
                    break;
                case INDEXADA:
                    remuneracao = calcularRemuneracaoIndexada(valorBase, taxa,
                            diasDecorridos);
                    break;
                case PRE_FIXADA:
                    remuneracao = calcularRemuneracaoPreFixada(valorBase, taxa,
                            diasDecorridos);
                    break;
                case POS_FIXADA:
                    remuneracao = calcularRemuneracaoPosFixada(valorBase, taxa,
                            diasDecorridos);
                    break;
                default:
                    log.warn("Tipo de remuneracao nao suportado: {}. Usando taxa zero.",
                        produto.getTipoRemuneracao());
                    remuneracao = BigDecimal.ZERO;
            }

            return remuneracao.setScale(4, RoundingMode.HALF_UP);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular remuneração: "
                    + e.getMessage());
        }
    }

    /**
     * Calcula a remuneração fixa (juros simples).
     *
     * @param valorBase Valor base para cálculo.
     * @param taxa      Taxa de remuneração.
     * @param dias      Quantidade de dias.
     * @return Valor da remuneração.
     */
    private BigDecimal calcularRemuneracaoFixa(final BigDecimal valorBase,
            final BigDecimal taxa,
            final long dias) {
        // Juros simples: R = P * i * t
        return valorBase.multiply(taxa).multiply(BigDecimal.valueOf(dias))
                .divide(DIAS_ANO, 4, RoundingMode.HALF_UP);
    }

    /**
     * Calcula a remuneração variável (juros compostos).
     *
     * @param valorBase Valor base para cálculo.
     * @param taxa      Taxa de remuneração.
     * @param dias      Quantidade de dias.
     * @return Valor da remuneração.
     */
    private BigDecimal calcularRemuneracaoVariavel(final BigDecimal valorBase,
            final BigDecimal taxa,
            final long dias) {
        // Juros compostos: R = P * (1 + i)^t - P
        BigDecimal fator = BigDecimal.ONE.add(taxa).pow((int) dias);
        return valorBase.multiply(fator).subtract(valorBase);
    }

    /**
     * Calcula a remuneração híbrida (parte fixa, parte variável).
     *
     * @param valorBase Valor base para cálculo.
     * @param taxa      Taxa de remuneração.
     * @param dias      Quantidade de dias.
     * @return Valor da remuneração.
     */
    private BigDecimal calcularRemuneracaoHibrida(final BigDecimal valorBase,
            final BigDecimal taxa,
            final long dias) {
        // Combinação de fixa e variável
        BigDecimal taxaMeio = taxa.multiply(FATOR_HIBRIDO);
        BigDecimal fixa = calcularRemuneracaoFixa(valorBase, taxaMeio, dias);
        BigDecimal variavel = calcularRemuneracaoVariavel(valorBase, taxaMeio,
                dias);
        return fixa.add(variavel);
    }

    /**
     * Calcula a remuneração indexada.
     *
     * @param valorBase Valor base para cálculo.
     * @param taxa      Taxa de remuneração.
     * @param dias      Quantidade de dias.
     * @return Valor da remuneração.
     */
    private BigDecimal calcularRemuneracaoIndexada(final BigDecimal valorBase,
            final BigDecimal taxa,
            final long dias) {
        // Remuneração indexada (ex: CDI)
        return calcularRemuneracaoFixa(valorBase, taxa, dias);
    }

    /**
     * Calcula a remuneração pré-fixada.
     *
     * @param valorBase Valor base para cálculo.
     * @param taxa      Taxa de remuneração.
     * @param dias      Quantidade de dias.
     * @return Valor da remuneração.
     */
    private BigDecimal calcularRemuneracaoPreFixada(final BigDecimal valorBase,
            final BigDecimal taxa,
            final long dias) {
        // Taxa pré-fixada
        return calcularRemuneracaoFixa(valorBase, taxa, dias);
    }

    /**
     * Calcula a remuneração pós-fixada.
     *
     * @param valorBase Valor base para cálculo.
     * @param taxa      Taxa de remuneração.
     * @param dias      Quantidade de dias.
     * @return Valor da remuneração.
     */
    private BigDecimal calcularRemuneracaoPosFixada(final BigDecimal valorBase,
            final BigDecimal taxa,
            final long dias) {
        // Taxa pós-fixada
        return calcularRemuneracaoFixa(valorBase, taxa, dias);
    }

    /**
     * Calcula a data de vencimento baseada no produto.
     *
     * @param produto Entidade do produto financeiro.
     * @return Data de vencimento calculada.
     */
    private LocalDateTime calcularDataVencimento(final ProdutoFinanceiro produto) {
        if (produto.getPrazoMinimoDias() != null) {
            return LocalDateTime.now().plusDays(produto.getPrazoMinimoDias());
        }
        return null;
    }

    /**
     * Calcula a data da próxima remuneração baseada no produto.
     *
     * @param produto Entidade do produto financeiro.
     * @return Data da próxima remuneração.
     */
    private LocalDateTime calcularProximaRemuneracao(
            final ProdutoFinanceiro produto) {
        switch (produto.getPeriodicidadeRemuneracao()) {
            case DIARIA:
                return LocalDateTime.now().plusDays(1);
            case MENSAL:
                return LocalDateTime.now().plusMonths(1);
            case TRIMESTRAL:
                return LocalDateTime.now().plusMonths(3);
            case SEMESTRAL:
                return LocalDateTime.now().plusMonths(6);
            case ANUAL:
                return LocalDateTime.now().plusYears(1);
            case VENCIMENTO:
                return calcularDataVencimento(produto);
            default:
                return LocalDateTime.now().plusDays(1);
        }
    }

    /**
     * Cria um registro de remuneração calculada.
     *
     * @param aplicacao            Entidade da aplicação.
     * @param valorRemuneracao     Valor bruto da remuneração.
     * @param usuarioProcessamento Usuário ou sistema que realizou o processo.
     */
    private void criarRemuneracao(final AplicacaoFinanceira aplicacao,
            final BigDecimal valorRemuneracao,
            final String usuarioProcessamento) {
        try {
            Remuneracao remuneracao = new Remuneracao();
            remuneracao.setCodigoRemuneracao(gerarCodigoRemuneracao());
            remuneracao.setAplicacaoFinanceira(aplicacao);
            remuneracao.setConta(aplicacao.getConta());
            remuneracao.setTipoRemuneracao(Remuneracao.TipoRemuneracao.RENDIMENTO);
            remuneracao.setStatus(Remuneracao.StatusRemuneracao.CONCLUIDA);
            remuneracao.setValorRemuneracao(valorRemuneracao);
            remuneracao.setValorBase(aplicacao.getValorAtual()
                    .subtract(valorRemuneracao));
            remuneracao.setTaxaAplicada(aplicacao.getTaxaRemuneracaoAtual());
            remuneracao.setValorLiquido(valorRemuneracao);
            remuneracao.setDataRemuneracao(LocalDateTime.now());
            remuneracao.setDataProcessamento(LocalDateTime.now());
            remuneracao.setDataConclusao(LocalDateTime.now());
            remuneracao.setDiasRemuneracao(UM_DIA);
            remuneracao.setProcessamentoAutomatico(true);
            remuneracao.setReversivel(true);
            remuneracao.setUsuarioProcessamento(usuarioProcessamento);
            remuneracao.setSistemaOrigem("Aurix-CORE");

            remuneracaoRepository.save(remuneracao);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar remuneração: "
                    + e.getMessage());
        }
    }

    /**
     * Cria um registro no histórico de remuneração.
     *
     * @param aplicacao            Entidade da aplicação.
     * @param tipoEvento           Tipo do evento ocorrido.
     * @param valorAnterior        Valor da aplicação antes do evento.
     * @param valorPosterior       Valor da aplicação após o evento.
     * @param valorVariacao        Valor da variação ocorrida.
     * @param usuarioProcessamento Usuário ou sistema responsável.
     */
    private void criarHistoricoRemuneracao(
            final AplicacaoFinanceira aplicacao,
            final HistoricoRemuneracao.TipoEvento tipoEvento,
            final BigDecimal valorAnterior,
            final BigDecimal valorPosterior,
            final BigDecimal valorVariacao,
            final String usuarioProcessamento) {
        try {
            HistoricoRemuneracao historico = new HistoricoRemuneracao();
            historico.setAplicacaoFinanceira(aplicacao);
            historico.setConta(aplicacao.getConta());
            historico.setTipoEvento(tipoEvento);
            historico.setValorAnterior(valorAnterior);
            historico.setValorPosterior(valorPosterior);
            historico.setValorVariacao(valorVariacao);
            historico.setTaxaAplicada(aplicacao.getTaxaRemuneracaoAtual());
            historico.setDataEvento(LocalDateTime.now());
            historico.setDataProcessamento(LocalDateTime.now());
            historico.setDataConclusao(LocalDateTime.now());
            historico.setDiasDecorridos(aplicacao.getDiasDecorridos());
            historico.setDiasRestantes(aplicacao.getDiasRestantes());
            historico.setProcessamentoAutomatico(true);
            historico.setReversivel(true);
            historico.setUsuarioProcessamento(usuarioProcessamento);
            historico.setSistemaOrigem("Aurix-CORE");

            historicoRemuneracaoRepository.save(historico);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao criar histórico de remuneração: "
                            + e.getMessage());
        }
    }

    /**
     * Lista todas as aplicações de uma conta.
     *
     * @param contaId ID da conta.
     * @return Lista de DTOs das aplicações.
     */
    public List<AplicacaoFinanceiraDTO> listarAplicacoesPorConta(
            final Long contaId) {
        List<AplicacaoFinanceira> aplicacoes = aplicacaoFinanceiraRepository
                .findByContaId(contaId);
        return aplicacoes.stream()
                .map(AplicacaoFinanceiraDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<AplicacaoFinanceiraDTO> listarAplicacoesAtivas() {
        List<AplicacaoFinanceira> aplicacoes = aplicacaoFinanceiraRepository
                .findByStatus(AplicacaoFinanceira.StatusAplicacao.ATIVA);
        return aplicacoes.stream()
                .map(AplicacaoFinanceiraDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProdutoFinanceiroDTO> listarProdutosDisponiveis() {
        List<ProdutoFinanceiro> produtos = produtoFinanceiroRepository
                .findProdutosDisponiveisPublico(true);
        return produtos.stream()
                .map(ProdutoFinanceiroDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private String gerarCodigoAplicacao() {
        return "APL-" + random.nextInt(MAX_RANDOM_CODE);
    }

    private String gerarCodigoRemuneracao() {
        return "REM-" + random.nextInt(MAX_RANDOM_CODE);
    }
}
