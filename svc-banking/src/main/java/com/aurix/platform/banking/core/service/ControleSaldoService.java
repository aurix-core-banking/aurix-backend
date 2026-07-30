package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.ControleSaldoDTO;
import com.aurix.platform.banking.core.dto.MovimentoContaDTO;
import com.aurix.platform.banking.core.entity.ControleSaldo;
import com.aurix.platform.banking.core.entity.HistoricoSaldo;
import com.aurix.platform.banking.core.entity.MovimentoConta;
import com.aurix.platform.banking.core.repository.ControleSaldoRepository;
import com.aurix.platform.banking.core.repository.HistoricoSaldoRepository;
import com.aurix.platform.banking.core.repository.MovimentoContaRepository;
import com.aurix.platform.banking.core.repository.ContaRepository;
import com.aurix.platform.shared.entity.Conta;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelo controle de saldo das contas.
 */
@Service
@Transactional
public class ControleSaldoService {

    /**
     * Limite padrão para novas contas.
     */
    private static final BigDecimal LIMITE_PADRAO = BigDecimal.valueOf(5000.00);

    /**
     * Valor máximo para o código aleatório.
     */
    private static final int MAX_RANDOM_CODE = 1000000;

    /**
     * Repositório de controle de saldo.
     */
    private final ControleSaldoRepository controleSaldoRepository;

    /**
     * Repositório de movimentos de conta.
     */
    private final MovimentoContaRepository movimentoContaRepository;

    /**
     * Repositório de histórico de saldo.
     */
    private final HistoricoSaldoRepository historicoSaldoRepository;

    /**
     * Repositório de contas.
     */
    private final ContaRepository contaRepository;

    /**
     * Serviço de métricas de negócio.
     */
    private final BusinessMetricsService metricsService;

    /**
     * Gerador de números aleatórios.
     */
    private final Random random = new Random();

    /**
     * Construtor com injeção de dependências.
     *
     * @param controleSaldoRepo   Repositório de controle de saldo.
     * @param movimentoContaRepo  Repositório de movimentos de conta.
     * @param historicoSaldoRepo  Repositório de histórico de saldo.
     * @param contaRepo           Repositório de contas.
     * @param metrics             Serviço de métricas de negócio.
     */
    public ControleSaldoService(
            final ControleSaldoRepository controleSaldoRepo,
            final MovimentoContaRepository movimentoContaRepo,
            final HistoricoSaldoRepository historicoSaldoRepo,
            final ContaRepository contaRepo,
            final BusinessMetricsService metrics) {
        this.controleSaldoRepository = controleSaldoRepo;
        this.movimentoContaRepository = movimentoContaRepo;
        this.historicoSaldoRepository = historicoSaldoRepo;
        this.contaRepository = contaRepo;
        this.metricsService = metrics;
    }

    /**
     * Processa um novo movimento de conta.
     *
     * @param movimentoDTO DTO com os dados do movimento.
     * @return DTO com os dados do movimento processado.
     */
    public MovimentoContaDTO processarMovimento(final MovimentoContaDTO movimentoDTO) {
        try {
            // 1. Buscar conta
            Optional<Conta> contaOpt = contaRepository.findById(movimentoDTO.getContaId());
            if (contaOpt.isEmpty()) {
                throw new RuntimeException("Conta não encontrada");
            }

            Conta conta = contaOpt.get();

            // 2. Buscar controle de saldo atual
            Optional<ControleSaldo> controleOpt = controleSaldoRepository.findByContaId(conta.getId());
            if (controleOpt.isEmpty()) {
                // Criar controle de saldo se não existir
                ControleSaldo novoControle = criarControleSaldoInicial(conta);
                controleOpt = Optional.of(controleSaldoRepository.save(novoControle));
            }

            ControleSaldo controle = controleOpt.get();

            // 3. Validar movimento
            validarMovimento(movimentoDTO, controle);

            // 4. Criar movimento
            MovimentoConta movimento = movimentoDTO.toEntity();
            movimento.setConta(conta);
            movimento.setCodigoMovimento(gerarCodigoMovimento());
            movimento.setStatus(MovimentoConta.StatusMovimento.PENDENTE);
            movimento.setDataMovimento(LocalDateTime.now());
            movimento.setProcessamentoAutomatico(true);
            movimento.setReversivel(true);

            // 5. Calcular saldos
            calcularSaldosMovimento(movimento, controle);

            // 6. Salvar movimento
            movimentoContaRepository.save(movimento);

            // 7. Processar automaticamente se configurado
            if (movimento.getProcessamentoAutomatico()) {
                processarMovimento(movimento.getId());
                metricsService.registrarSucessoTransacao();
                metricsService.registrarVolumeFinanceiro(
                        movimento.getTipoMovimento().name(),
                        movimento.getValorMovimento());
            }

            return MovimentoContaDTO.fromEntity(movimento);

        } catch (org.springframework.orm.ObjectOptimisticLockingFailureException
                | org.hibernate.StaleObjectStateException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar movimento: " + e.getMessage());
        }
    }

    /**
     * Processa um movimento existente pelo seu ID.
     *
     * @param movimentoId ID do movimento.
     * @return DTO com os dados do movimento processado.
     */
    public MovimentoContaDTO processarMovimento(final Long movimentoId) {
        try {
            Optional<MovimentoConta> movimentoOpt = movimentoContaRepository.findById(movimentoId);
            if (movimentoOpt.isEmpty()) {
                throw new RuntimeException("Movimento não encontrado");
            }

            MovimentoConta movimento = movimentoOpt.get();

            // Verificar se pode ser processado
            if (!movimento.getStatus().equals(MovimentoConta.StatusMovimento.PENDENTE)) {
                throw new RuntimeException("Movimento não está pendente para processamento");
            }

            // Atualizar status
            movimento.setStatus(MovimentoConta.StatusMovimento.PROCESSANDO);
            movimento.setDataProcessamento(LocalDateTime.now());
            movimento = movimentoContaRepository.save(movimento);

            Optional<ControleSaldo> controleOpt = controleSaldoRepository
                    .findByContaId(movimento.getConta().getId());
            if (controleOpt.isEmpty()) {
                throw new RuntimeException("Controle de saldo não encontrado");
            }
            ControleSaldo controle = controleOpt.get();
            if (movimento.getSaldoDisponivelPosterior() == null) {
                calcularSaldosMovimento(movimento, controle);
                movimento = movimentoContaRepository.save(movimento);
            }

            try {
                processarMovimentoInterno(movimento);

                // Marcar como concluído
                movimento.setStatus(MovimentoConta.StatusMovimento.CONCLUIDO);
                movimento.setDataConclusao(LocalDateTime.now());
                movimento.setSaldoConsistente(true);
                movimentoContaRepository.save(movimento);

                // Atualizar controle de saldo
                atualizarControleSaldo(movimento);

                // Criar histórico
                criarHistoricoSaldo(movimento);

                return MovimentoContaDTO.fromEntity(movimento);

            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                if (e instanceof org.springframework.orm.ObjectOptimisticLockingFailureException) {
                    metricsService.registrarConflitoLock();
                }
                // Marcar como falhado
                movimento.setStatus(MovimentoConta.StatusMovimento.FALHADO);
                movimento.setObservacoes("Erro no processamento: "
                        + e.getMessage());
                movimentoContaRepository.save(movimento);

                throw e;
            }

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar movimento: " + e.getMessage());
        }
    }

    /**
     * Lógica interna para processamento do movimento.
     *
     * @param movimento Entidade do movimento.
     */
    private void processarMovimentoInterno(final MovimentoConta movimento) {
        // Implementar lógica específica baseada no tipo de movimento
        switch (movimento.getTipoMovimento()) {
            case DEBITO:
                processarDebito();
                break;
            case CREDITO:
                processarCredito();
                break;
            case BLOQUEIO:
                processarBloqueio();
                break;
            case DESBLOQUEIO:
                processarDesbloqueio();
                break;
            case RESERVA:
                processarReserva();
                break;
            case LIBERACAO_RESERVA:
                processarLiberacaoReserva();
                break;
            case AJUSTE_CREDITO:
                processarAjusteCredito();
                break;
            case AJUSTE_DEBITO:
                processarAjusteDebito();
                break;
            case TARIFA:
                processarTarifa();
                break;
            case JUROS:
                processarJuros();
                break;
            case RENDIMENTO:
                processarRendimento();
                break;
            case CORRECAO_MONETARIA:
                processarCorrecaoMonetaria();
                break;
            case IOF:
                processarIOF();
                break;
            case IR:
                processarIR();
                break;
            default:
                throw new RuntimeException("Tipo de movimento não suportado");
        }
    }

    private void processarDebito() {
        // Lógica específica para débito
        // Verificar saldo disponível
        // Atualizar saldos
        // Registrar movimento
    }

    private void processarCredito() {
        // Lógica específica para crédito
        // Atualizar saldos
        // Registrar movimento
    }

    private void processarBloqueio() {
        // Lógica específica para bloqueio
        // Mover valor de disponível para bloqueado
        // Registrar movimento
    }

    private void processarDesbloqueio() {
        // Lógica específica para desbloqueio
        // Mover valor de bloqueado para disponível
        // Registrar movimento
    }

    private void processarReserva() {
        // Lógica específica para reserva
        // Mover valor de disponível para pendente
        // Registrar movimento
    }

    private void processarLiberacaoReserva() {
        // Lógica específica para liberação de reserva
        // Mover valor de pendente para disponível
        // Registrar movimento
    }

    private void processarAjusteCredito() {
        // Lógica específica para ajuste de crédito
        // Aplicar ajuste
        // Registrar movimento
    }

    private void processarAjusteDebito() {
        // Lógica específica para ajuste de débito
        // Aplicar ajuste
        // Registrar movimento
    }

    private void processarTarifa() {
        // Lógica específica para tarifa
        // Aplicar tarifa
        // Registrar movimento
    }

    private void processarJuros() {
        // Lógica específica para juros
        // Aplicar juros
        // Registrar movimento
    }

    private void processarRendimento() {
        // Lógica específica para rendimento
        // Aplicar rendimento
        // Registrar movimento
    }

    private void processarCorrecaoMonetaria() {
        // Lógica específica para correção monetária
        // Aplicar correção
        // Registrar movimento
    }

    private void processarIOF() {
        // Lógica específica para IOF
        // Aplicar IOF
        // Registrar movimento
    }

    private void processarIR() {
        // Lógica específica para IR
        // Aplicar IR
        // Registrar movimento
    }

    /**
     * Valida um movimento antes do processamento.
     *
     * @param movimentoDTO DTO do movimento.
     * @param controle     Controle de saldo da conta.
     */
    private void validarMovimento(final MovimentoContaDTO movimentoDTO,
            final ControleSaldo controle) {
        // Validar se conta está bloqueada
        if (controle.getBloqueioOperacoes()) {
            throw new RuntimeException("Conta bloqueada para operações");
        }

        // Validar saldo para débitos
        if (controle.getSaldoDisponivel()
                .compareTo(movimentoDTO.getValorMovimento()) < 0) {
            throw new RuntimeException("Saldo insuficiente para a operação");
        }

        // Validar limites de crédito
        if (movimentoDTO.getTipoMovimento().equals("DEBITO")) {
            BigDecimal novoLimiteUtilizado = controle.getLimiteUtilizado()
                    .add(movimentoDTO.getValorMovimento());
            if (novoLimiteUtilizado.compareTo(controle.getLimiteCredito()) > 0) {
                throw new RuntimeException("Limite de crédito excedido");
            }
        }
    }

    /**
     * Calcula os novos saldos baseados no movimento.
     *
     * @param movimento Entidade do movimento.
     * @param controle  Controle de saldo atual.
     */
    private void calcularSaldosMovimento(final MovimentoConta movimento,
            final ControleSaldo controle) {
        // Salvar saldos anteriores
        movimento.setSaldoAnterior(controle.getSaldoTotal());
        movimento.setSaldoDisponivelAnterior(controle.getSaldoDisponivel());
        movimento.setSaldoBloqueadoAnterior(controle.getSaldoBloqueado());
        movimento.setSaldoPendenteAnterior(controle.getSaldoPendente());

        // Calcular novos saldos baseado no tipo de movimento
        BigDecimal novoSaldoDisponivel = controle.getSaldoDisponivel();
        BigDecimal novoSaldoBloqueado = controle.getSaldoBloqueado();
        BigDecimal novoSaldoPendente = controle.getSaldoPendente();

        switch (movimento.getTipoMovimento()) {
            case DEBITO:
            case AJUSTE_DEBITO:
            case TARIFA:
            case JUROS:
            case IOF:
            case IR:
                novoSaldoDisponivel = novoSaldoDisponivel
                        .subtract(movimento.getValorMovimento());
                break;
            case CREDITO:
            case AJUSTE_CREDITO:
            case RENDIMENTO:
            case CORRECAO_MONETARIA:
                novoSaldoDisponivel = novoSaldoDisponivel.add(movimento.getValorMovimento());
                break;
            case BLOQUEIO:
                novoSaldoDisponivel = novoSaldoDisponivel.subtract(movimento.getValorMovimento());
                novoSaldoBloqueado = novoSaldoBloqueado.add(movimento.getValorMovimento());
                break;
            case DESBLOQUEIO:
                novoSaldoBloqueado = novoSaldoBloqueado.subtract(movimento.getValorMovimento());
                novoSaldoDisponivel = novoSaldoDisponivel.add(movimento.getValorMovimento());
                break;
            case RESERVA:
                novoSaldoDisponivel = novoSaldoDisponivel.subtract(movimento.getValorMovimento());
                novoSaldoPendente = novoSaldoPendente.add(movimento.getValorMovimento());
                break;
            case LIBERACAO_RESERVA:
                novoSaldoPendente = novoSaldoPendente
                        .subtract(movimento.getValorMovimento());
                novoSaldoDisponivel = novoSaldoDisponivel
                        .add(movimento.getValorMovimento());
                break;
            default:
                throw new UnsupportedOperationException(
                        "Tipo de movimento não suportado: " + movimento.getTipoMovimento());
        }

        // Calcular saldo total
        BigDecimal novoSaldoTotal = novoSaldoDisponivel.add(novoSaldoBloqueado).add(novoSaldoPendente);

        // Salvar saldos posteriores
        movimento.setSaldoPosterior(novoSaldoTotal);
        movimento.setSaldoDisponivelPosterior(novoSaldoDisponivel);
        movimento.setSaldoBloqueadoPosterior(novoSaldoBloqueado);
        movimento.setSaldoPendentePosterior(novoSaldoPendente);

        // Versão do saldo agora é gerenciada pelo JPA (@Version)
    }

    /**
     * Atualiza o controle de saldo após o processamento de um movimento.
     *
     * @param movimento Entidade do movimento processado.
     */
    private void atualizarControleSaldo(final MovimentoConta movimento) {
        Optional<ControleSaldo> controleOpt = controleSaldoRepository.findByContaId(movimento.getConta().getId());
        if (controleOpt.isEmpty()) {
            throw new RuntimeException("Controle de saldo não encontrado");
        }

        ControleSaldo controle = controleOpt.get();

        // Atualizar saldos
        controle.setSaldoDisponivel(movimento.getSaldoDisponivelPosterior());
        controle.setSaldoBloqueado(movimento.getSaldoBloqueadoPosterior());
        controle.setSaldoPendente(movimento.getSaldoPendentePosterior());
        controle.setSaldoTotal(movimento.getSaldoPosterior());
        controle.setDataUltimaAtualizacao(LocalDateTime.now());
        controle.setSaldoConsistente(true);

        // Atualizar limite utilizado se for débito
        if (movimento.getTipoMovimento().equals(MovimentoConta.TipoMovimento.DEBITO)) {
            controle.setLimiteUtilizado(controle.getLimiteUtilizado().add(movimento.getValorMovimento()));
        }

        // Calcular limite disponível
        controle.setLimiteDisponivel(controle.getLimiteCredito().subtract(controle.getLimiteUtilizado()));

        controleSaldoRepository.save(controle);
    }

    /**
     * Cria um registro no histórico de saldos.
     *
     * @param movimento Entidade do movimento processado.
     */
    private void criarHistoricoSaldo(final MovimentoConta movimento) {
        HistoricoSaldo historico = new HistoricoSaldo();
        historico.setConta(movimento.getConta());
        historico.setSaldoDisponivel(movimento.getSaldoDisponivelPosterior());
        historico.setSaldoBloqueado(movimento.getSaldoBloqueadoPosterior());
        historico.setSaldoPendente(movimento.getSaldoPendentePosterior());
        historico.setSaldoTotal(movimento.getSaldoPosterior());
        historico.setDataReferencia(movimento.getDataMovimento());
        historico.setDataAtualizacao(LocalDateTime.now());
        historico.setVersaoSaldo(movimento.getVersaoSaldo());
        historico.setSaldoConsistente(true);
        historico.setTipoAtualizacao(HistoricoSaldo.TipoAtualizacao.AUTOMATICA);
        historico.setSistemaOrigem("Aurix-CORE");
        historico.setCodigoMovimento(movimento.getCodigoMovimento());

        if (movimento.getTransacao() != null) {
            historico.setCodigoTransacao(movimento.getTransacao().getCodigoTransacao());
        }

        if (movimento.getLiquidacao() != null) {
            historico.setCodigoLiquidacao(movimento.getLiquidacao().getCodigoLiquidacao());
        }

        historicoSaldoRepository.save(historico);
    }

    /**
     * Cria um controle de saldo inicial para uma nova conta.
     *
     * @param conta Entidade da conta.
     * @return Novo controle de saldo.
     */
    private ControleSaldo criarControleSaldoInicial(final Conta conta) {
        ControleSaldo controle = new ControleSaldo();
        controle.setConta(conta);
        controle.setSaldoDisponivel(conta.getSaldoAtual());
        controle.setSaldoBloqueado(BigDecimal.ZERO);
        controle.setSaldoPendente(BigDecimal.ZERO);
        controle.setSaldoTotal(conta.getSaldoAtual());
        controle.setLimiteCredito(LIMITE_PADRAO); // Limite padrão
        controle.setLimiteUtilizado(BigDecimal.ZERO);
        controle.setLimiteDisponivel(LIMITE_PADRAO);
        controle.setDataUltimaAtualizacao(LocalDateTime.now());
        controle.setSaldoConsistente(true);
        controle.setBloqueioOperacoes(false);
        return controle;
    }

    /**
     * Lista todos os movimentos de uma conta.
     *
     * @param contaId ID da conta.
     * @return Lista de DTOs dos movimentos.
     */
    public List<MovimentoContaDTO> listarMovimentosPorConta(final Long contaId) {
        List<MovimentoConta> movimentos = movimentoContaRepository.findByContaId(contaId);
        return movimentos.stream()
                .map(MovimentoContaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Lista todos os movimentos pendentes de processamento.
     *
     * @return Lista de DTOs dos movimentos pendentes.
     */
    public List<MovimentoContaDTO> listarMovimentosPendentes() {
        List<MovimentoConta> movimentos = movimentoContaRepository.findMovimentosPendentesParaProcessamento();
        return movimentos.stream()
                .map(MovimentoContaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Realiza o estorno de um movimento concluído.
     *
     * @param movimentoId ID do movimento a ser estornado.
     * @return DTO do movimento de estorno criado.
     */
    public MovimentoContaDTO estornarMovimento(final Long movimentoId) {
        Optional<MovimentoConta> movimentoOpt = movimentoContaRepository.findById(movimentoId);
        if (movimentoOpt.isEmpty()) {
            throw new RuntimeException("Movimento não encontrado");
        }

        MovimentoConta movimento = movimentoOpt.get();

        if (!movimento.getReversivel()) {
            throw new RuntimeException("Movimento não é reversível");
        }

        if (!movimento.getStatus().equals(MovimentoConta.StatusMovimento.CONCLUIDO)) {
            throw new RuntimeException("Apenas movimentos concluídos podem ser estornados");
        }

        // Criar movimento de estorno
        MovimentoConta estorno = new MovimentoConta();
        estorno.setCodigoMovimento(gerarCodigoMovimento());
        estorno.setConta(movimento.getConta());
        estorno.setTransacao(movimento.getTransacao());
        estorno.setLiquidacao(movimento.getLiquidacao());
        estorno.setTipoMovimento(movimento.getTipoMovimento());
        estorno.setStatus(MovimentoConta.StatusMovimento.ESTORNADO);
        estorno.setValorMovimento(movimento.getValorMovimento().negate());
        estorno.setDataMovimento(LocalDateTime.now());
        estorno.setProcessamentoAutomatico(true);
        estorno.setReversivel(false);
        estorno.setObservacoes("Estorno do movimento " + movimento.getCodigoMovimento());

        estorno = movimentoContaRepository.save(estorno);

        // Processar estorno
        processarMovimento(estorno.getId());

        return MovimentoContaDTO.fromEntity(estorno);
    }

    /**
     * Obtém o controle de saldo de uma conta.
     *
     * @param contaId ID da conta.
     * @return DTO com os dados do controle de saldo.
     */
    public ControleSaldoDTO obterControleSaldo(final Long contaId) {
        Optional<ControleSaldo> controleOpt = controleSaldoRepository.findByContaId(contaId);
        if (controleOpt.isEmpty()) {
            throw new RuntimeException("Controle de saldo não encontrado");
        }

        return ControleSaldoDTO.fromEntity(controleOpt.get());
    }

    /**
     * Lista as contas que possuem saldo inconsistente.
     *
     * @return Lista de DTOs dos controles de saldo inconsistentes.
     */
    public List<ControleSaldoDTO> listarContasComSaldoInconsistente() {
        List<ControleSaldo> controles = controleSaldoRepository.findBySaldoConsistente(false);
        return controles.stream()
                .map(ControleSaldoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Lista as contas que estão bloqueadas para operações.
     *
     * @return Lista de DTOs dos controles de saldo bloqueados.
     */
    public List<ControleSaldoDTO> listarContasBloqueadas() {
        List<ControleSaldo> controles = controleSaldoRepository.findByBloqueioOperacoes(true);
        return controles.stream()
                .map(ControleSaldoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private String gerarCodigoMovimento() {
        return "MOV-" + random.nextInt(MAX_RANDOM_CODE)
                + random.nextInt(MAX_RANDOM_CODE);
    }
}
