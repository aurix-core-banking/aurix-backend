package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.CalculoTarifaDTO;
import com.aurix.platform.banking.core.dto.LiquidacaoDTO;
import com.aurix.platform.banking.core.entity.Liquidacao;
import com.aurix.platform.banking.core.entity.LiquidacaoItem;
import com.aurix.platform.banking.core.entity.ControleSaldo;
import com.aurix.platform.banking.core.repository.LiquidacaoRepository;
import com.aurix.platform.banking.core.repository.LiquidacaoItemRepository;
import com.aurix.platform.banking.core.repository.ControleSaldoRepository;
import com.aurix.platform.banking.core.repository.TransacaoRepository;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.Transacao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelo processamento de liquidações financeiras.
 */
@Service
@Transactional
public class LiquidacaoService {

    /**
     * Valor máximo para o código aleatório.
     */
    private static final int MAX_RANDOM_CODE = 1000000;

    /**
     * Valor máximo para o código aleatório curto.
     */
    private static final int MAX_RANDOM_SHORT = 1000;

    /**
     * Alíquota do IOF para investimento.
     */
    private static final BigDecimal IOF_INVESTIMENTO = BigDecimal.valueOf(0.0038);

    /**
     * Nível de serviço padrão.
     */
    private static final int NIVEL_SERVICO_PADRAO = 1;

    /**
     * Repositório de liquidações.
     */
    private final LiquidacaoRepository liquidacaoRepository;

    /**
     * Repositório de itens de liquidação.
     */
    private final LiquidacaoItemRepository liquidacaoItemRepository;

    /**
     * Repositório de controle de saldo.
     */
    private final ControleSaldoRepository controleSaldoRepository;

    /**
     * Repositório de transações.
     */
    private final TransacaoRepository transacaoRepository;

    /**
     * Serviço de motor de tarifas.
     */
    private final MotorTarifasService motorTarifasService;

    /**
     * Gerador de números aleatórios.
     */
    private final Random random = new Random();

    /**
     * Construtor com injeção de dependências.
     *
     * @param liquidacaoRepo    Repositório de liquidações.
     * @param itemRepo          Repositório de itens de liquidação.
     * @param controleSaldoRepo Repositório de controle de saldo.
     * @param transacaoRepo     Repositório de transações.
     * @param motorTarifasServ  Serviço de motor de tarifas.
     */
    public LiquidacaoService(
            final LiquidacaoRepository liquidacaoRepo,
            final LiquidacaoItemRepository itemRepo,
            final ControleSaldoRepository controleSaldoRepo,
            final TransacaoRepository transacaoRepo,
            final MotorTarifasService motorTarifasServ) {
        this.liquidacaoRepository = liquidacaoRepo;
        this.liquidacaoItemRepository = itemRepo;
        this.controleSaldoRepository = controleSaldoRepo;
        this.transacaoRepository = transacaoRepo;
        this.motorTarifasService = motorTarifasServ;
    }

    /**
     * Cria uma nova liquidação baseada nos dados fornecidos.
     *
     * @param liquidacaoDTO DTO com os dados da liquidação.
     * @return DTO com os dados da liquidação criada.
     */
    public LiquidacaoDTO criarLiquidacao(final LiquidacaoDTO liquidacaoDTO) {
        try {
            // 1. Buscar transação
            Optional<Transacao> transacaoOpt = transacaoRepository
                    .findById(liquidacaoDTO.getTransacaoId());
            if (transacaoOpt.isEmpty()) {
                throw new RuntimeException("Transação não encontrada");
            }

            Transacao transacao = transacaoOpt.get();

            // 2. Criar liquidação
            Liquidacao liquidacao = liquidacaoDTO.toEntity();
            liquidacao.setTransacao(transacao);
            liquidacao.setCodigoLiquidacao(gerarCodigoLiquidacao());
            liquidacao.setStatus(Liquidacao.StatusLiquidacao.PENDENTE);
            liquidacao.setDataLiquidacao(LocalDateTime.now());
            liquidacao.setProcessamentoAutomatico(true);
            liquidacao.setReversivel(true);

            // 3. Calcular valores
            calcularValoresLiquidacao(liquidacao, transacao);

            // 4. Salvar liquidação
            liquidacao = liquidacaoRepository.save(liquidacao);

            // 5. Processar automaticamente se configurado
            if (liquidacao.getProcessamentoAutomatico()) {
                processarLiquidacao(liquidacao.getId());
            }

            return LiquidacaoDTO.fromEntity(liquidacao);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar liquidação: "
                    + e.getMessage());
        }
    }

    /**
     * Processa uma liquidação existente pelo seu ID.
     *
     * @param liquidacaoId ID da liquidação.
     * @return DTO com os dados da liquidação processada.
     */
    public LiquidacaoDTO processarLiquidacao(final Long liquidacaoId) {
        try {
            Optional<Liquidacao> liquidacaoOpt = liquidacaoRepository.findById(liquidacaoId);
            if (liquidacaoOpt.isEmpty()) {
                throw new RuntimeException("Liquidação não encontrada");
            }

            Liquidacao liquidacao = liquidacaoOpt.get();

            // Verificar se pode ser processada
            if (!liquidacao.getStatus()
                    .equals(Liquidacao.StatusLiquidacao.PENDENTE)) {
                throw new RuntimeException(
                        "Liquidação não está pendente para processamento");
            }

            // Atualizar status
            liquidacao.setStatus(Liquidacao.StatusLiquidacao.PROCESSANDO);
            liquidacao.setDataProcessamento(LocalDateTime.now());
            liquidacao.setTentativasLiquidacao(
                    liquidacao.getTentativasLiquidacao() + 1);
            liquidacao = liquidacaoRepository.save(liquidacao);

            try {
                // Processar baseado no tipo
                switch (liquidacao.getTipoLiquidacao()) {
                    case PIX_INSTANTANEO:
                        processarPIXInstantaneo(liquidacao);
                        break;
                    case PIX_AGENDADO:
                        processarPIXAgendado(liquidacao);
                        break;
                    case TED_IMEDIATA:
                        processarTEDImediata(liquidacao);
                        break;
                    case TED_AGENDADA:
                        processarTEDAgendada(liquidacao);
                        break;
                    case DOC_IMEDIATA:
                        processarDOCImediata(liquidacao);
                        break;
                    case DOC_AGENDADA:
                        processarDOCAgendada(liquidacao);
                        break;
                    case SAQUE_ATM:
                        processarSaqueATM(liquidacao);
                        break;
                    case DEPOSITO_ESPECIE:
                        processarDepositoEspecie(liquidacao);
                        break;
                    case DEPOSITO_CHEQUE:
                        processarDepositoCheque(liquidacao);
                        break;
                    case TRANSFERENCIA_INTERNA:
                        processarTransferenciaInterna(liquidacao);
                        break;
                    case APLICACAO_INVESTIMENTO:
                        processarAplicacaoInvestimento(liquidacao);
                        break;
                    case RESGATE_INVESTIMENTO:
                        processarResgateInvestimento(liquidacao);
                        break;
                    case PAGAMENTO_BOLETO:
                        processarPagamentoBoleto(liquidacao);
                        break;
                    case PAGAMENTO_CARTAO:
                        processarPagamentoCartao(liquidacao);
                        break;
                    default:
                        throw new RuntimeException("Tipo de liquidação não suportado");
                }

                // Marcar como liquidada
                liquidacao.setStatus(Liquidacao.StatusLiquidacao.LIQUIDADA);
                liquidacao.setDataConclusao(LocalDateTime.now());
                liquidacao = liquidacaoRepository.save(liquidacao);

                return LiquidacaoDTO.fromEntity(liquidacao);

            } catch (RuntimeException e) {
                // Marcar como falhada
                liquidacao.setStatus(Liquidacao.StatusLiquidacao.FALHADA);
                liquidacao.setErroLiquidacao("Erro no processamento: "
                        + e.getMessage());
                liquidacaoRepository.save(liquidacao);

                throw e;
            } catch (Exception e) {
                // Marcar como falhada
                liquidacao.setStatus(Liquidacao.StatusLiquidacao.FALHADA);
                liquidacao.setErroLiquidacao("Erro inesperado no processamento: "
                        + e.getMessage());
                liquidacaoRepository.save(liquidacao);

                throw new RuntimeException(e);
            }

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar liquidação: "
                    + e.getMessage());
        }
    }

    /**
     * Processa uma liquidação do tipo PIX instantâneo.
     *
     * @param liquidacao Entidade da liquidação.
     */
    private void processarPIXInstantaneo(final Liquidacao liquidacao) {
        // Implementar lógica específica do PIX instantâneo
        // 1. Validar saldo
        // 2. Bloquear valor
        // 3. Enviar para SPI
        // 4. Aguardar confirmação
        // 5. Atualizar saldos

        Transacao transacao = liquidacao.getTransacao();

        // Validar saldo disponível
        validarSaldoDisponivel(transacao.getContaOrigem(),
                liquidacao.getValorLiquidacao());

        // Criar itens de liquidação
        criarItensLiquidacao(liquidacao);

        // Atualizar saldos
        atualizarSaldos(liquidacao);

        // Gerar códigos de rastreamento
        liquidacao.setCodigoSPI(gerarCodigoSPI());
        liquidacao.setCodigoBacen(gerarCodigoBacen());
    }

    /**
     * Processa uma liquidação do tipo TED imediata.
     *
     * @param liquidacao Entidade da liquidação.
     */
    private void processarTEDImediata(final Liquidacao liquidacao) {
        // Implementar lógica específica do TED imediata
        // 1. Validar saldo
        // 2. Bloquear valor
        // 3. Enviar para STR
        // 4. Aguardar confirmação
        // 5. Atualizar saldos

        Transacao transacao = liquidacao.getTransacao();

        // Validar saldo disponível
        validarSaldoDisponivel(transacao.getContaOrigem(),
                liquidacao.getValorLiquidacao());

        // Criar itens de liquidação
        criarItensLiquidacao(liquidacao);

        // Atualizar saldos
        atualizarSaldos(liquidacao);

        // Gerar códigos de rastreamento
        liquidacao.setCodigoSTR(gerarCodigoSTR());
        liquidacao.setCodigoBacen(gerarCodigoBacen());
    }

    /**
     * Processa uma liquidação do tipo DOC imediata.
     *
     * @param liquidacao Entidade da liquidação.
     */
    private void processarDOCImediata(final Liquidacao liquidacao) {
        // Implementar lógica específica do DOC imediata
        // Similar ao TED, mas com regras específicas do DOC
        processarTEDImediata(liquidacao);
    }

    /**
     * Processa uma liquidação do tipo transferência interna.
     *
     * @param liquidacao Entidade da liquidação.
     */
    private void processarTransferenciaInterna(final Liquidacao liquidacao) {
        // Implementar lógica de transferência interna
        // 1. Validar saldo origem
        // 2. Debitar conta origem
        // 3. Creditar conta destino
        // 4. Atualizar saldos

        Transacao transacao = liquidacao.getTransacao();

        // Validar saldo disponível
        validarSaldoDisponivel(transacao.getContaOrigem(),
                liquidacao.getValorLiquidacao());

        // Criar itens de liquidação
        criarItensLiquidacao(liquidacao);

        // Atualizar saldos
        atualizarSaldos(liquidacao);
    }

    /**
     * Processa uma liquidação do tipo PIX agendado.
     *
     * @param liquidacao Entidade da liquidação.
     */
    private void processarPIXAgendado(final Liquidacao liquidacao) {
        // Implementar lógica do PIX agendado
        // Similar ao instantâneo, mas com agendamento
        processarPIXInstantaneo(liquidacao);
    }

    /**
     * Processa uma liquidação do tipo TED agendada.
     *
     * @param liquidacao Entidade da liquidação.
     */
    private void processarTEDAgendada(final Liquidacao liquidacao) {
        // Implementar lógica do TED agendada
        // Similar ao imediata, mas com agendamento
        processarTEDImediata(liquidacao);
    }

    /**
     * Processa uma liquidação do tipo DOC agendada.
     *
     * @param liquidacao Entidade da liquidação.
     */
    private void processarDOCAgendada(final Liquidacao liquidacao) {
        // Implementar lógica do DOC agendada
        // Similar ao imediata, mas com agendamento
        processarDOCImediata(liquidacao);
    }

    /**
     * Processa uma liquidação do tipo saque ATM.
     *
     * @param liquidacao Entidade da liquidação.
     */
    private void processarSaqueATM(final Liquidacao liquidacao) {
        // Implementar lógica de saque ATM
        processarTransferenciaInterna(liquidacao);
    }

    /**
     * Processa uma liquidação do tipo depósito em espécie.
     *
     * @param liquidacao Entidade da liquidação.
     */
    private void processarDepositoEspecie(final Liquidacao liquidacao) {
        // Implementar lógica de depósito em espécie
        processarTransferenciaInterna(liquidacao);
    }

    /**
     * Processa uma liquidação do tipo depósito de cheque.
     *
     * @param liquidacao Entidade da liquidação.
     */
    private void processarDepositoCheque(final Liquidacao liquidacao) {
        // Implementar lógica de depósito de cheque
        processarTransferenciaInterna(liquidacao);
    }

    /**
     * Processa uma liquidação do tipo aplicação em investimento.
     *
     * @param liquidacao Entidade da liquidação.
     */
    private void processarAplicacaoInvestimento(final Liquidacao liquidacao) {
        // Implementar lógica de aplicação em investimento
        processarTransferenciaInterna(liquidacao);
    }

    /**
     * Processa uma liquidação do tipo resgate de investimento.
     *
     * @param liquidacao Entidade da liquidação.
     */
    private void processarResgateInvestimento(final Liquidacao liquidacao) {
        // Implementar lógica de resgate de investimento
        processarTransferenciaInterna(liquidacao);
    }

    /**
     * Processa uma liquidação do tipo pagamento de boleto.
     *
     * @param liquidacao Entidade da liquidação.
     */
    private void processarPagamentoBoleto(final Liquidacao liquidacao) {
        // Implementar lógica de pagamento de boleto
        processarTransferenciaInterna(liquidacao);
    }

    /**
     * Processa uma liquidação do tipo pagamento de cartão.
     *
     * @param liquidacao Entidade da liquidação.
     */
    private void processarPagamentoCartao(final Liquidacao liquidacao) {
        // Implementar lógica de pagamento de cartão
        processarTransferenciaInterna(liquidacao);
    }

    /**
     * Valida se a conta possui saldo disponível para o valor informado.
     *
     * @param conta Entidade da conta.
     * @param valor Valor a ser validado.
     */
    private void validarSaldoDisponivel(final Conta conta, final BigDecimal valor) {
        Optional<ControleSaldo> controleOpt = controleSaldoRepository
                .findByContaId(conta.getId());
        if (controleOpt.isEmpty()) {
            throw new RuntimeException(
                    "Controle de saldo não encontrado para a conta");
        }

        ControleSaldo controle = controleOpt.get();
        if (controle.getSaldoDisponivel().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente para a operação");
        }

        if (controle.getBloqueioOperacoes()) {
            throw new RuntimeException("Conta bloqueada para operações");
        }
    }

    /**
     * Cria os itens de liquidação (débitos e créditos).
     *
     * @param liquidacao Entidade da liquidação.
     */
    private void criarItensLiquidacao(final Liquidacao liquidacao) {
        Transacao transacao = liquidacao.getTransacao();

        // Item de débito na conta origem
        if (transacao.getContaOrigem() != null) {
            LiquidacaoItem itemDebito = new LiquidacaoItem();
            itemDebito.setLiquidacao(liquidacao);
            itemDebito.setConta(transacao.getContaOrigem());
            itemDebito.setTipoMovimento(LiquidacaoItem.TipoMovimento.DEBITO);
            itemDebito.setValorMovimento(liquidacao.getValorLiquidacao());
            itemDebito.setDataMovimento(LocalDateTime.now());
            itemDebito.setDescricaoMovimento("Débito por "
                    + liquidacao.getTipoLiquidacao().name());
            itemDebito.setCodigoMovimento(gerarCodigoMovimento());
            itemDebito.setProcessado(false);
            liquidacaoItemRepository.save(itemDebito);
        }

        // Item de crédito na conta destino
        if (transacao.getContaDestino() != null) {
            LiquidacaoItem itemCredito = new LiquidacaoItem();
            itemCredito.setLiquidacao(liquidacao);
            itemCredito.setConta(transacao.getContaDestino());
            itemCredito.setTipoMovimento(LiquidacaoItem.TipoMovimento.CREDITO);
            itemCredito.setValorMovimento(liquidacao.getValorLiquidacao());
            itemCredito.setDataMovimento(LocalDateTime.now());
            itemCredito.setDescricaoMovimento("Crédito por "
                    + liquidacao.getTipoLiquidacao().name());
            itemCredito.setCodigoMovimento(gerarCodigoMovimento());
            itemCredito.setProcessado(false);
            liquidacaoItemRepository.save(itemCredito);
        }
    }

    /**
     * Atualiza os saldos das contas envolvidas na liquidação.
     *
     * @param liquidacao Entidade da liquidação.
     */
    private void atualizarSaldos(final Liquidacao liquidacao) {
        List<LiquidacaoItem> itens = liquidacaoItemRepository.findByLiquidacaoId(liquidacao.getId());

        for (LiquidacaoItem item : itens) {
            atualizarSaldoConta(item);
            item.setProcessado(true);
            liquidacaoItemRepository.save(item);
        }
    }

    /**
     * Atualiza o saldo de uma conta individualmente baseada no item de liquidação.
     *
     * @param item Item de liquidação.
     */
    private void atualizarSaldoConta(final LiquidacaoItem item) {
        Optional<ControleSaldo> controleOpt = controleSaldoRepository
                .findByContaId(item.getConta().getId());
        if (controleOpt.isEmpty()) {
            throw new RuntimeException("Controle de saldo não encontrado");
        }

        ControleSaldo controle = controleOpt.get();

        // Calcular saldo anterior
        BigDecimal saldoAnterior = controle.getSaldoDisponivel();
        item.setSaldoAnterior(saldoAnterior);

        // Atualizar saldo baseado no tipo de movimento
        BigDecimal novoSaldo;
        if (item.getTipoMovimento().equals(LiquidacaoItem.TipoMovimento.DEBITO)) {
            novoSaldo = saldoAnterior.subtract(item.getValorMovimento());
        } else {
            novoSaldo = saldoAnterior.add(item.getValorMovimento());
        }

        // Atualizar controle de saldo
        controle.setSaldoDisponivel(novoSaldo);
        controle.setSaldoTotal(novoSaldo.add(controle.getSaldoBloqueado())
                .add(controle.getSaldoPendente()));
        controle.setDataUltimaAtualizacao(LocalDateTime.now());
        controle.setVersaoSaldo(controle.getVersaoSaldo() + 1);
        controleSaldoRepository.save(controle);

        item.setSaldoPosterior(novoSaldo);
    }

    /**
     * Calcula os valores da liquidação (taxas, IOF, total).
     *
     * @param liquidacao Entidade da liquidação.
     * @param transacao  Entidade da transação original.
     */
    private void calcularValoresLiquidacao(final Liquidacao liquidacao,
            final Transacao transacao) {
        // Valor base da liquidação
        liquidacao.setValorLiquidacao(transacao.getValor());

        // Calcular taxa usando o motor de tarifas
        try {
            CalculoTarifaDTO calculo = new CalculoTarifaDTO();
            calculo.setContaId(transacao.getContaOrigem().getId());
            calculo.setTipoTarifa(liquidacao.getTipoLiquidacao().name());
            calculo.setValorTransacao(transacao.getValor());
            // Nível da conta será implementado na gestão de perfis
            calculo.setNivelServico(NIVEL_SERVICO_PADRAO);
            calculo.setDataTransacao(LocalDateTime.now());

            CalculoTarifaDTO resultado = motorTarifasService
                    .calcularTarifa(calculo);
            if (resultado.getAplicavel()) {
                liquidacao.setValorTaxa(resultado.getValorTarifa());
            }
        } catch (RuntimeException e) {
            // Em caso de erro, manter sem tarifa
            liquidacao.setValorTaxa(BigDecimal.ZERO);
            liquidacao.setValorTotal(liquidacao.getValorLiquidacao());
        } catch (Exception e) {
            // Em caso de erro, manter sem tarifa
            liquidacao.setValorTaxa(BigDecimal.ZERO);
            liquidacao.setValorTotal(liquidacao.getValorLiquidacao());
        }

        // Calcular IOF se aplicável
        liquidacao.setValorIOF(calcularIOF(liquidacao.getValorLiquidacao(),
                liquidacao.getTipoLiquidacao()));

        // Calcular valor total
        BigDecimal valorTotal = liquidacao.getValorLiquidacao()
                .add(liquidacao.getValorTaxa() != null
                        ? liquidacao.getValorTaxa()
                        : BigDecimal.ZERO)
                .add(liquidacao.getValorIOF() != null
                        ? liquidacao.getValorIOF()
                        : BigDecimal.ZERO);
        liquidacao.setValorTotal(valorTotal);
    }

    /**
     * Calcula o IOF aplicável à operação.
     *
     * @param valor Valor base para cálculo.
     * @param tipo  Tipo de liquidação.
     * @return Valor do IOF.
     */
    private BigDecimal calcularIOF(final BigDecimal valor,
            final Liquidacao.TipoLiquidacao tipo) {
        // IOF é aplicado apenas em alguns tipos de operação
        if (tipo.equals(Liquidacao.TipoLiquidacao.APLICACAO_INVESTIMENTO)
                || tipo.equals(Liquidacao.TipoLiquidacao.RESGATE_INVESTIMENTO)) {
            // IOF de 0,38% ao dia para investimentos
            return valor.multiply(IOF_INVESTIMENTO);
        }
        return BigDecimal.ZERO;
    }

    /**
     * Lista todas as liquidações pendentes de processamento.
     *
     * @return Lista de DTOs das liquidações pendentes.
     */
    public List<LiquidacaoDTO> listarLiquidacoesPendentes() {
        List<Liquidacao> liquidacoes = liquidacaoRepository.findLiquidacoesPendentesParaProcessamento();
        return liquidacoes.stream()
                .map(LiquidacaoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Realiza o estorno de uma liquidação concluída.
     *
     * @param liquidacaoId ID da liquidação a ser estornada.
     * @return DTO da liquidação de estorno criada.
     */
    public LiquidacaoDTO estornarLiquidacao(final Long liquidacaoId) {
        Optional<Liquidacao> liquidacaoOpt = liquidacaoRepository.findById(liquidacaoId);
        if (liquidacaoOpt.isEmpty()) {
            throw new RuntimeException("Liquidação não encontrada");
        }

        Liquidacao liquidacao = liquidacaoOpt.get();

        if (!liquidacao.getReversivel()) {
            throw new RuntimeException("Liquidação não é reversível");
        }

        if (!liquidacao.getStatus()
                .equals(Liquidacao.StatusLiquidacao.LIQUIDADA)) {
            throw new RuntimeException(
                    "Apenas liquidações liquidadas podem ser estornadas");
        }

        // Criar liquidação de estorno
        Liquidacao estorno = new Liquidacao();
        estorno.setCodigoLiquidacao(gerarCodigoLiquidacao());
        estorno.setTransacao(liquidacao.getTransacao());
        estorno.setTipoLiquidacao(liquidacao.getTipoLiquidacao());
        estorno.setStatus(Liquidacao.StatusLiquidacao.ESTORNADA);
        estorno.setValorLiquidacao(liquidacao.getValorLiquidacao().negate());
        estorno.setValorTaxa(liquidacao.getValorTaxa() != null
                ? liquidacao.getValorTaxa().negate()
                : BigDecimal.ZERO);
        estorno.setValorIOF(liquidacao.getValorIOF() != null
                ? liquidacao.getValorIOF().negate()
                : BigDecimal.ZERO);
        estorno.setValorTotal(liquidacao.getValorTotal().negate());
        estorno.setDataLiquidacao(LocalDateTime.now());
        estorno.setProcessamentoAutomatico(true);
        estorno.setReversivel(false);
        estorno.setObservacoes("Estorno da liquidação "
                + liquidacao.getCodigoLiquidacao());

        estorno = liquidacaoRepository.save(estorno);

        // Processar estorno
        processarLiquidacao(estorno.getId());

        return LiquidacaoDTO.fromEntity(estorno);
    }

    private String gerarCodigoLiquidacao() {
        return "LIQ-" + random.nextInt(MAX_RANDOM_CODE);
    }

    private String gerarCodigoSPI() {
        return "SPI-" + random.nextInt(MAX_RANDOM_CODE);
    }

    private String gerarCodigoSTR() {
        return "STR-" + random.nextInt(MAX_RANDOM_CODE);
    }

    private String gerarCodigoBacen() {
        return "BACEN-" + System.currentTimeMillis() + "-"
                + random.nextInt(MAX_RANDOM_SHORT);
    }

    private String gerarCodigoMovimento() {
        return "MOV-" + random.nextInt(MAX_RANDOM_CODE);
    }
}
