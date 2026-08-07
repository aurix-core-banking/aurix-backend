package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.CalculoTarifaDTO;
import com.aurix.platform.banking.core.dto.TarifaDTO;
import com.aurix.platform.banking.core.entity.CobrancaTarifa;
import com.aurix.platform.banking.core.entity.ContaTarifa;
import com.aurix.platform.banking.core.entity.Tarifa;
import com.aurix.platform.banking.core.repository.CobrancaTarifaRepository;
import com.aurix.platform.banking.core.repository.ContaTarifaRepository;
import com.aurix.platform.banking.core.repository.TarifaRepository;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.shared.entity.Conta;
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
 * Serviço responsável pelo cálculo e cobrança de tarifas.
 */
@Service
@Transactional
public class MotorTarifasService {

    /**
     * Logger da classe.
     */
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MotorTarifasService.class);

    /**
     * Valor máximo para o código aleatório.
     */
    private static final int MAX_RANDOM_CODE = 1000000;

    /**
     * Divisor por cem.
     */
    private static final BigDecimal DIVISOR_CEM = BigDecimal.valueOf(100);

    /**
     * Fator de desconto premium.
     */
    private static final BigDecimal FATOR_PREMIUM = BigDecimal.valueOf(0.9);

    /**
     * Dias para vencimento da tarifa.
     */
    private static final int DIAS_VENCIMENTO_TARIFA = 30;

    /**
     * Repositório de tarifas.
     */
    private final TarifaRepository tarifaRepository;

    /**
     * Repositório de tarifas por conta.
     */
    private final ContaTarifaRepository contaTarifaRepository;

    /**
     * Repositório de cobranças de tarifa.
     */
    private final CobrancaTarifaRepository cobrancaTarifaRepository;

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
     * @param tarifaRepo      Repositório de tarifas.
     * @param contaTarifaRepo Repositório de tarifas por conta.
     * @param cobrancaRepo    Repositório de cobranças de tarifa.
     * @param contaRepo       Repositório de contas.
     */
    public MotorTarifasService(
            final TarifaRepository tarifaRepo,
            final ContaTarifaRepository contaTarifaRepo,
            final CobrancaTarifaRepository cobrancaRepo,
            final ContaRepository contaRepo) {
        this.tarifaRepository = tarifaRepo;
        this.contaTarifaRepository = contaTarifaRepo;
        this.cobrancaTarifaRepository = cobrancaRepo;
        this.contaRepository = contaRepo;
    }

    /**
     * Calcula a tarifa aplicável a uma transação.
     *
     * @param calculoRequest DTO com os dados para cálculo.
     * @return DTO com o resultado do cálculo.
     */
    public CalculoTarifaDTO calcularTarifa(final CalculoTarifaDTO calculoRequest) {
        try {
            // 1. Buscar conta
            Optional<Conta> contaOpt = contaRepository
                    .findById(calculoRequest.getContaId());
            if (contaOpt.isEmpty()) {
                calculoRequest.setAplicavel(false);
                calculoRequest.setMotivoNaoAplicavel("Conta não encontrada");
                return calculoRequest;
            }

            Conta conta = contaOpt.get();

            // 2. Verificar se há tarifa específica para a conta
            Optional<ContaTarifa> contaTarifaOpt = contaTarifaRepository
                    .findByContaIdAndTarifaIdAndAtivaTrue(conta.getId(), null);

            if (contaTarifaOpt.isPresent()) {
                return calcularTarifaEspecificaConta(calculoRequest,
                        contaTarifaOpt.get());
            }

            // 3. Buscar melhor tarifa disponível
            List<Tarifa> tarifasDisponiveis = tarifaRepository.findMelhorTarifa(
                    Tarifa.TipoTarifa.valueOf(calculoRequest.getTipoTarifa()),
                    calculoRequest.getNivelServico(),
                    calculoRequest.getDataTransacao());

            if (tarifasDisponiveis.isEmpty()) {
                calculoRequest.setAplicavel(false);
                calculoRequest.setMotivoNaoAplicavel(
                        "Nenhuma tarifa disponível para este tipo de operação");
                return calculoRequest;
            }

            // 4. Aplicar regras de negócio e calcular valor
            Tarifa melhorTarifa = tarifasDisponiveis.get(0);
            return calcularValorTarifa(calculoRequest, melhorTarifa, conta);

        } catch (Exception e) {
            calculoRequest.setAplicavel(false);
            calculoRequest.setMotivoNaoAplicavel("Erro no cálculo: "
                    + e.getMessage());
            return calculoRequest;
        }
    }

    /**
     * Calcula a tarifa baseada em regras específicas da conta.
     *
     * @param calculoRequest DTO de cálculo.
     * @param contaTarifa    Configuração de tarifa da conta.
     * @return DTO de cálculo atualizado.
     */
    private CalculoTarifaDTO calcularTarifaEspecificaConta(
            final CalculoTarifaDTO calculoRequest,
            final ContaTarifa contaTarifa) {
        Tarifa tarifa = contaTarifa.getTarifa();

        calculoRequest.setValorOriginal(contaTarifa.getValorAplicado());
        calculoRequest.setValorTarifa(contaTarifa.getValorAplicado());
        calculoRequest.setUnidadeTarifa(tarifa.getUnidadeTarifa().name());
        calculoRequest.setAplicavel(true);
        calculoRequest.setJustificativa("Tarifa específica da conta aplicada");

        // Aplicar descontos se houver
        if (contaTarifa.getPercentualDesconto().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal desconto = calculoRequest.getValorTarifa()
                    .multiply(contaTarifa.getPercentualDesconto())
                    .divide(DIVISOR_CEM, 4, RoundingMode.HALF_UP);

            calculoRequest.setValorDesconto(desconto);
            calculoRequest.setValorTarifa(calculoRequest.getValorTarifa().subtract(desconto));
            calculoRequest.setPercentualDesconto(contaTarifa.getPercentualDesconto());
        }

        return calculoRequest;
    }

    /**
     * Calcula o valor da tarifa baseada em uma configuração de tarifa.
     *
     * @param calculoRequest DTO de cálculo.
     * @param tarifa         Configuração da tarifa.
     * @param conta          Entidade da conta.
     * @return DTO de cálculo atualizado.
     */
    private CalculoTarifaDTO calcularValorTarifa(
            final CalculoTarifaDTO calculoRequest,
            final Tarifa tarifa,
            final Conta conta) {
        BigDecimal valorCalculado = BigDecimal.ZERO;

        switch (tarifa.getUnidadeTarifa()) {
            case VALOR_FIXO:
            case VALOR_POR_OPERACAO:
            case VALOR_POR_DIA:
            case VALOR_POR_MES:
            case VALOR_POR_ANO:
                valorCalculado = tarifa.getValorBase();
                break;

            case PERCENTUAL:
                valorCalculado = calculoRequest.getValorTransacao()
                        .multiply(tarifa.getPercentualBase())
                        .divide(DIVISOR_CEM, 4, RoundingMode.HALF_UP);
                break;

            case PERCENTUAL_COM_MINIMO:
                valorCalculado = calculoRequest.getValorTransacao()
                        .multiply(tarifa.getPercentualBase())
                        .divide(DIVISOR_CEM, 4, RoundingMode.HALF_UP);
                if (valorCalculado.compareTo(tarifa.getValorMinimo()) < 0) {
                    valorCalculado = tarifa.getValorMinimo();
                }
                break;

            case PERCENTUAL_COM_MAXIMO:
                valorCalculado = calculoRequest.getValorTransacao()
                        .multiply(tarifa.getPercentualBase())
                        .divide(DIVISOR_CEM, 4, RoundingMode.HALF_UP);
                if (valorCalculado.compareTo(tarifa.getValorMaximo()) > 0) {
                    valorCalculado = tarifa.getValorMaximo();
                }
                break;

            case PERCENTUAL_COM_MIN_MAX:
                valorCalculado = calculoRequest.getValorTransacao()
                        .multiply(tarifa.getPercentualBase())
                        .divide(DIVISOR_CEM, 4, RoundingMode.HALF_UP);
                if (valorCalculado.compareTo(tarifa.getValorMinimo()) < 0) {
                    valorCalculado = tarifa.getValorMinimo();
                } else if (valorCalculado.compareTo(tarifa.getValorMaximo()) > 0) {
                    valorCalculado = tarifa.getValorMaximo();
                }
                break;

            default:
                log.warn("Unidade de tarifa nao suportada: {}. Tarifa nao aplicada.",
                    tarifa.getUnidadeTarifa());
                calculoRequest.setAplicavel(false);
                calculoRequest.setMotivoNaoAplicavel(
                        "Unidade de tarifa não suportada: "
                        + tarifa.getUnidadeTarifa());
                return calculoRequest;
        }

        // Aplicar regras especiais se existirem
        valorCalculado = aplicarRegrasEspeciais(valorCalculado, conta);

        calculoRequest.setValorOriginal(valorCalculado);
        calculoRequest.setValorTarifa(valorCalculado);
        calculoRequest.setUnidadeTarifa(tarifa.getUnidadeTarifa().name());
        calculoRequest.setAplicavel(true);
        calculoRequest.setJustificativa("Tarifa calculada com base nas"
                + " regras do sistema");

        return calculoRequest;
    }

    /**
     * Aplica regras especiais de desconto ou acréscimo.
     *
     * @param valorCalculado Valor base da tarifa.
     * @param conta          Entidade da conta.
     * @return Novo valor calculado.
     */
    private BigDecimal aplicarRegrasEspeciais(final BigDecimal valorCalculado,
            final Conta conta) {
        // Aqui você pode implementar regras especiais baseadas em:
        // - Tipo de cliente (PF/PJ)
        // - Nível de relacionamento
        // - Volume de transações
        // - Período do dia/mês
        // - Configurações especiais da tarifa

        BigDecimal resultado = valorCalculado;

        String nivel = conta.getDadosExtras() != null
                && conta.getDadosExtras().contains("PREMIUM")
                        ? "PREMIUM"
                        : null;

        if ("PREMIUM".equals(nivel)) {
            resultado = valorCalculado.multiply(FATOR_PREMIUM);
        }

        return resultado;
    }

    /**
     * Realiza a cobrança de uma tarifa.
     *
     * @param calculoRequest DTO com os dados para cálculo e cobrança.
     * @return Entidade da cobrança criada.
     */
    public CobrancaTarifa cobrarTarifa(final CalculoTarifaDTO calculoRequest) {
        CalculoTarifaDTO calculo = calcularTarifa(calculoRequest);

        if (!calculo.getAplicavel()) {
            throw new RuntimeException("Tarifa não aplicável: "
                    + calculo.getMotivoNaoAplicavel());
        }

        // Criar cobrança
        CobrancaTarifa cobranca = new CobrancaTarifa();
        cobranca.setCodigoCobranca(gerarCodigoCobranca());
        cobranca.setConta(contaRepository
                .findById(calculoRequest.getContaId()).orElseThrow());
        cobranca.setValorCobrado(calculo.getValorTarifa());
        cobranca.setValorOriginal(calculo.getValorOriginal());
        cobranca.setPercentualDesconto(calculo.getPercentualDesconto());
        cobranca.setValorDesconto(calculo.getValorDesconto());
        cobranca.setStatus(CobrancaTarifa.StatusCobranca.PENDENTE);
        cobranca.setDataCobranca(LocalDateTime.now());
        cobranca.setDataVencimento(LocalDateTime.now()
                .plusDays(DIAS_VENCIMENTO_TARIFA));
        cobranca.setDetalhesCobranca(calculo.getJustificativa());
        cobranca.setRegrasAplicadas(calculo.getRegrasAplicadas());

        return cobrancaTarifaRepository.save(cobranca);
    }

    /**
     * Lista as tarifas disponíveis filtradas por tipo e nível.
     *
     * @param tipoTarifa   Tipo da tarifa.
     * @param nivelServico Nível de serviço.
     * @return Lista de DTOs das tarifas disponíveis.
     */
    public List<TarifaDTO> listarTarifasDisponiveis(final String tipoTarifa,
            final Integer nivelServico) {
        List<Tarifa> tarifas;

        if (tipoTarifa != null && nivelServico != null) {
            tarifas = tarifaRepository.findMelhorTarifa(
                    Tarifa.TipoTarifa.valueOf(tipoTarifa),
                    nivelServico,
                    LocalDateTime.now());
        } else if (tipoTarifa != null) {
            tarifas = tarifaRepository.findTarifasVigentesPorTipo(
                    Tarifa.TipoTarifa.valueOf(tipoTarifa),
                    LocalDateTime.now());
        } else {
            tarifas = tarifaRepository.findTarifasVigentes(LocalDateTime.now());
        }

        return tarifas.stream()
                .map(TarifaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Simula o valor de uma tarifa sem persistir dados.
     *
     * @param valorTransacao Valor da transação.
     * @param tipoTarifa     Tipo da tarifa.
     * @param nivelServico   Nível de serviço.
     * @return Valor simulado da tarifa.
     */
    public BigDecimal simularTarifa(final BigDecimal valorTransacao,
            final String tipoTarifa,
            final Integer nivelServico) {
        CalculoTarifaDTO calculo = new CalculoTarifaDTO();
        calculo.setValorTransacao(valorTransacao);
        calculo.setTipoTarifa(tipoTarifa);
        calculo.setNivelServico(nivelServico);
        calculo.setDataTransacao(LocalDateTime.now());

        CalculoTarifaDTO resultado = calcularTarifa(calculo);
        return resultado.getValorTarifa();
    }

    private String gerarCodigoCobranca() {
        return "COB-" + random.nextInt(MAX_RANDOM_CODE);
    }
}
