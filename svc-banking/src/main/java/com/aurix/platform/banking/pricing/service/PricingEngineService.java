package com.aurix.platform.banking.pricing.service;

import com.aurix.platform.banking.core.entity.Tarifa;
import com.aurix.platform.banking.core.entity.PacoteTarifas;
import com.aurix.platform.banking.pricing.entity.SimulacaoTarifas;
import com.aurix.platform.banking.core.repository.TarifaRepository;
import com.aurix.platform.banking.core.repository.PacoteTarifasRepository;
import com.aurix.platform.banking.pricing.repository.SimulacaoTarifasRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@SuppressWarnings({"PMD.UnusedFormalParameter"})
public class PricingEngineService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PricingEngineService.class);
    private final TarifaRepository tarifaRepository;
    private final PacoteTarifasRepository pacoteTarifasRepository;
    private final SimulacaoTarifasRepository simulacaoTarifasRepository;

    @Cacheable(value = "tarifas", key = "#operacao + \'_\' + #clienteId + \'_\' + #produto")
    public BigDecimal calcularTarifa(String operacao, String clienteId, String produto, BigDecimal valorOperacao, String canal) {
        log.info("Calculando tarifa: Operação={}, Cliente={}, Produto={}, Valor={}, Canal={}", operacao, clienteId, produto, valorOperacao, canal);
        try {
            Tarifa tarifaBase = buscarTarifaBase(operacao, produto);
            if (tarifaBase == null) {
                log.warn("Tarifa base não encontrada para: Operação={}, Produto={}", operacao, produto);
                return BigDecimal.ZERO;
            }
            BigDecimal tarifaCalculada = aplicarRegrasNegocio(tarifaBase, valorOperacao, canal);
            tarifaCalculada = validarLimites(tarifaCalculada, tarifaBase);
            log.info("Tarifa calculada: {} para operação: {}", tarifaCalculada, operacao);
            return tarifaCalculada;
        } catch (Exception e) {
            log.error("Erro ao calcular tarifa: {}", e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    public SimulacaoTarifas simularTarifas(String clienteId, String produto, Integer volumeOperacoes, BigDecimal valorTotalOperacoes, Integer periodoMeses) {
        log.info("Simulando tarifas: Cliente={}, Produto={}, Volume={}, Valor={}, Período={} meses", clienteId, produto, volumeOperacoes, valorTotalOperacoes, periodoMeses);
        try {
            SimulacaoTarifas simulacao = SimulacaoTarifas.builder()
                    .numeroSimulacao(gerarNumeroSimulacao())
                    .nomeSimulacao("Simulação " + produto + " - " + LocalDate.now())
                    .tipoSimulacao(SimulacaoTarifas.TipoSimulacao.COMPARATIVA)
                    .clienteId(clienteId)
                    .produto(produto)
                    .volumeOperacoes(volumeOperacoes)
                    .valorTotalOperacoes(valorTotalOperacoes)
                    .periodoSimulacao(periodoMeses)
                    .unidadePeriodo(SimulacaoTarifas.UnidadePeriodo.MESES)
                    .dataSimulacao(LocalDateTime.now())
                    .dataExpiracao(LocalDateTime.now().plusDays(30))
                    .statusSimulacao("PROCESSANDO")
                    .build();
            BigDecimal tarifaAtual = calcularTarifaAtual(clienteId, produto, volumeOperacoes, valorTotalOperacoes);
            BigDecimal tarifaOtimizada = calcularTarifaOtimizada(clienteId, produto, volumeOperacoes, valorTotalOperacoes);
            BigDecimal diferenca = tarifaOtimizada.subtract(tarifaAtual);
            BigDecimal diferencaPercentual = tarifaAtual.compareTo(BigDecimal.ZERO) > 0
                    ? diferenca.divide(tarifaAtual, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100))
                    : BigDecimal.ZERO;
            BigDecimal economiaTotal = diferenca.multiply(BigDecimal.valueOf(volumeOperacoes));
            simulacao.setTarifaAtual(tarifaAtual);
            simulacao.setTarifaSimulada(tarifaOtimizada);
            simulacao.setDiferencaValor(diferenca);
            simulacao.setDiferencaPercentual(diferencaPercentual);
            simulacao.setEconomiaTotal(economiaTotal);
            simulacao.setEconomiaPercentual(diferencaPercentual);
            simulacao.setStatusSimulacao("CONCLUIDA");
            simulacao.setRecomendacoes(gerarRecomendacoes(tarifaAtual, tarifaOtimizada, economiaTotal));
            SimulacaoTarifas simulacaoSalva = simulacaoTarifasRepository.save(simulacao);
            log.info("Simulação concluída: {} - Economia: {}", simulacaoSalva.getNumeroSimulacao(), economiaTotal);
            return simulacaoSalva;
        } catch (Exception e) {
            log.error("Erro na simulação de tarifas: {}", e.getMessage());
            return null;
        }
    }

    public PacoteTarifas criarPacotePersonalizado(String clienteId, String nomePacote, List<String> codigosTarifas, BigDecimal descontoPercentual) {
        log.info("Criando pacote personalizado: Cliente={}, Nome={}, Desconto={}%", clienteId, nomePacote, descontoPercentual);
        try {
            PacoteTarifas pacote = new PacoteTarifas();
            pacote.setCodigoPacote(gerarCodigoPacote());
            pacote.setNomePacote(nomePacote);
            pacote.setDescricao("Pacote personalizado para cliente " + clienteId);
            pacote.setTipoPacote(PacoteTarifas.TipoPacote.PERSONALIZADO);
            pacote.setNivelServico(3);
            pacote.setDataInicioVigencia(LocalDateTime.now());
            pacote.setDataFimVigencia(LocalDateTime.now().plusYears(1));
            pacote.setAtivo(true);
            pacote.setAplicavelPessoaFisica(true);
            pacote.setAplicavelPessoaJuridica(true);
            BigDecimal valorPacote = calcularValorPacote(codigosTarifas);
            BigDecimal valorComDesconto = valorPacote.multiply(BigDecimal.ONE.subtract(descontoPercentual.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)));
            pacote.setValorMensalidade(valorComDesconto);
            PacoteTarifas pacoteSalvo = pacoteTarifasRepository.save(pacote);
            log.info("Pacote personalizado criado: {} - Valor: {}", pacoteSalvo.getCodigoPacote(), valorComDesconto);
            return pacoteSalvo;
        } catch (Exception e) {
            log.error("Erro ao criar pacote personalizado: {}", e.getMessage());
            return null;
        }
    }

    private Tarifa buscarTarifaBase(String operacao, String produto) {
        return tarifaRepository.findByAtivaTrue().stream().findFirst().orElse(null);
    }

    private BigDecimal aplicarRegrasNegocio(Tarifa tarifaBase, BigDecimal valorOperacao, String canal) {
        BigDecimal tarifaCalculada = tarifaBase.getValorBase();
        if (tarifaBase.getPercentualBase() != null && tarifaBase.getPercentualBase().compareTo(BigDecimal.ZERO) > 0) {
            tarifaCalculada = valorOperacao.multiply(tarifaBase.getPercentualBase());
        }
        if ("INTERNET_BANKING".equals(canal) || "MOBILE_BANKING".equals(canal)) {
            tarifaCalculada = tarifaCalculada.multiply(BigDecimal.valueOf(0.9));
        }
        return tarifaCalculada;
    }

    private BigDecimal validarLimites(BigDecimal tarifaCalculada, Tarifa tarifaBase) {
        if (tarifaBase.getValorMinimo() != null && tarifaCalculada.compareTo(tarifaBase.getValorMinimo()) < 0) {
            tarifaCalculada = tarifaBase.getValorMinimo();
        }
        if (tarifaBase.getValorMaximo() != null && tarifaCalculada.compareTo(tarifaBase.getValorMaximo()) > 0) {
            tarifaCalculada = tarifaBase.getValorMaximo();
        }
        return tarifaCalculada;
    }

    private BigDecimal calcularTarifaAtual(String clienteId, String produto, Integer volumeOperacoes, BigDecimal valorTotalOperacoes) {
        BigDecimal tarifaBase = BigDecimal.valueOf(10.0);
        return tarifaBase.multiply(BigDecimal.valueOf(volumeOperacoes));
    }

    private BigDecimal calcularTarifaOtimizada(String clienteId, String produto, Integer volumeOperacoes, BigDecimal valorTotalOperacoes) {
        BigDecimal tarifaBase = BigDecimal.valueOf(10.0);
        BigDecimal desconto = BigDecimal.valueOf(0.15);
        return tarifaBase.multiply(BigDecimal.valueOf(volumeOperacoes)).multiply(BigDecimal.ONE.subtract(desconto));
    }

    private String gerarRecomendacoes(BigDecimal tarifaAtual, BigDecimal tarifaOtimizada, BigDecimal economiaTotal) {
        if (economiaTotal.compareTo(BigDecimal.ZERO) > 0) {
            return String.format("Recomendamos a migração para o novo pacote. "
                    + "Economia estimada: R$ %.2f por mês (%.2f%% de redução).", economiaTotal,
                    tarifaAtual.compareTo(BigDecimal.ZERO) > 0
                            ? economiaTotal.divide(tarifaAtual, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100))
                            : BigDecimal.ZERO);
        } else {
            return "O pacote atual já está otimizado para o seu perfil de uso.";
        }
    }

    private BigDecimal calcularValorPacote(List<String> codigosTarifas) {
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (String codigo : codigosTarifas) {
            Optional<Tarifa> tarifa = tarifaRepository.findByCodigoTarifa(codigo);
            if (tarifa.isPresent()) {
                valorTotal = valorTotal.add(tarifa.get().getValorBase());
            }
        }
        return valorTotal;
    }

    private String gerarNumeroSimulacao() {
        return "SIM-" + System.currentTimeMillis() + "-" + (int) (Math.random() * 1000);
    }

    private String gerarCodigoPacote() {
        return "PAC-" + System.currentTimeMillis() + "-" + (int) (Math.random() * 1000);
    }

    @java.lang.SuppressWarnings("all")
    public PricingEngineService(final TarifaRepository tarifaRepository, final PacoteTarifasRepository pacoteTarifasRepository, final SimulacaoTarifasRepository simulacaoTarifasRepository) {
        this.tarifaRepository = tarifaRepository;
        this.pacoteTarifasRepository = pacoteTarifasRepository;
        this.simulacaoTarifasRepository = simulacaoTarifasRepository;
    }
}
