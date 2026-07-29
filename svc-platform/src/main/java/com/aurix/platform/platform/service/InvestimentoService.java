package com.aurix.platform.platform.service;

import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.platform.repository.InvestimentoRepository;
import com.aurix.platform.shared.dto.InvestimentoDTO;
import com.aurix.platform.shared.entity.Investimento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gestão de investimentos
 */
@Service
@Transactional
@SuppressWarnings({"PMD.UnusedFormalParameter"})
public class InvestimentoService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InvestimentoService.class);
    private final InvestimentoRepository investimentoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Cria um novo investimento
     */
    public InvestimentoDTO criarInvestimento(InvestimentoDTO investimentoDTO) {
        log.info("Criando investimento para conta ID: {}", investimentoDTO.getContaId());
        // Validar dados do investimento
        validarInvestimento(investimentoDTO);
        // Criar entidade
        Investimento investimento = new Investimento();
        if (investimentoDTO.getContaId() != null) {
            investimento.setConta(entityManager.getReference(Conta.class, investimentoDTO.getContaId()));
        }
        investimento.setTipoInvestimento(investimentoDTO.getTipoInvestimento());
        investimento.setValorInvestido(investimentoDTO.getValorInvestido());
        investimento.setTaxaRendimento(investimentoDTO.getTaxaRendimento());
        investimento.setDataAplicacao(investimentoDTO.getDataAplicacao() != null ? investimentoDTO.getDataAplicacao() : LocalDateTime.now());
        investimento.setDataVencimento(investimentoDTO.getDataVencimento());
        investimento.setStatus(Investimento.StatusInvestimento.ATIVO);
        investimento.setRendimentoAtual(BigDecimal.ZERO);
        investimento.setDadosInvestimento(investimentoDTO.getDadosInvestimento());
        // Salvar
        Investimento investimentoSalvo = investimentoRepository.save(investimento);
        log.info("Investimento criado com ID: {}", investimentoSalvo.getId());
        return converterParaDTO(investimentoSalvo);
    }

    /**
     * Busca investimento por ID
     */
    @Transactional(readOnly = true)
    public InvestimentoDTO buscarInvestimentoPorId(Long id) {
        log.info("Buscando investimento por ID: {}", id);
        Investimento investimento = investimentoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Investimento não encontrado"));
        return converterParaDTO(investimento);
    }

    /**
     * Lista investimentos por conta
     */
    @Transactional(readOnly = true)
    public List<InvestimentoDTO> listarInvestimentosPorConta(Long contaId) {
        log.info("Listando investimentos da conta ID: {}", contaId);
        return investimentoRepository.findByContaId(contaId).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista investimentos ativos por conta
     */
    @Transactional(readOnly = true)
    public List<InvestimentoDTO> listarInvestimentosAtivosPorConta(Long contaId) {
        log.info("Listando investimentos ativos da conta ID: {}", contaId);
        return investimentoRepository.findInvestimentosAtivosByContaId(contaId).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista investimentos por tipo
     */
    @Transactional(readOnly = true)
    public List<InvestimentoDTO> listarInvestimentosPorTipo(Investimento.TipoInvestimento tipoInvestimento) {
        log.info("Listando investimentos do tipo: {}", tipoInvestimento);
        return investimentoRepository.findByTipoInvestimento(tipoInvestimento).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista investimentos por status
     */
    @Transactional(readOnly = true)
    public List<InvestimentoDTO> listarInvestimentosPorStatus(Investimento.StatusInvestimento status) {
        log.info("Listando investimentos com status: {}", status);
        return investimentoRepository.findByStatus(status).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista investimentos vencidos
     */
    @Transactional(readOnly = true)
    public List<InvestimentoDTO> listarInvestimentosVencidos() {
        log.info("Listando investimentos vencidos");
        return investimentoRepository.findInvestimentosVencidos(LocalDateTime.now()).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Resgata investimento com cálculo de impostos
     */
    public InvestimentoDTO resgatarInvestimento(Long id, boolean resgateAntecipado) {
        log.info("Resgatando investimento ID: {}, antecipado: {}", id, resgateAntecipado);
        Investimento investimento = investimentoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Investimento não encontrado"));
        if (investimento.getStatus() != Investimento.StatusInvestimento.ATIVO) {
            throw new IllegalStateException("Investimento não pode ser resgatado");
        }
        calcularRendimentoFinal(investimento);
        BigDecimal valorBruto = investimento.getValorTotal();
        BigDecimal iof = calcularIOF(investimento, resgateAntecipado);
        BigDecimal ir = calcularIR(investimento, resgateAntecipado);
        BigDecimal valorLiquido = valorBruto.subtract(iof).subtract(ir);
        investimento.setStatus(Investimento.StatusInvestimento.RESGATADO);
        investimentoRepository.save(investimento);
        log.info("Investimento resgatado: Bruto={}, IOF={}, IR={}, Líquido={}", valorBruto, iof, ir, valorLiquido);
        return converterParaDTO(investimento);
    }

    /**
     * Calcula rendimento para CDB
     */
    public BigDecimal calcularRendimentoCDB(BigDecimal valorInvestido, BigDecimal taxaAnual, int dias) {
        BigDecimal taxaDiaria = taxaAnual.divide(BigDecimal.valueOf(365), 6, RoundingMode.HALF_UP);
        BigDecimal fator = BigDecimal.ONE.add(taxaDiaria).pow(dias);
        return valorInvestido.multiply(fator).subtract(valorInvestido);
    }

    /**
     * Calcula rendimento para LCA/LCI (isento de IR)
     */
    public BigDecimal calcularRendimentoLCA_LCI(BigDecimal valorInvestido, BigDecimal taxaAnual, int dias) {
        return calcularRendimentoCDB(valorInvestido, taxaAnual, dias);
    }

    /**
     * Calcula rendimento para Tesouro Direto
     */
    public BigDecimal calcularRendimentoTesouro(Investimento investimento, BigDecimal taxaSelic) {
        long dias = ChronoUnit.DAYS.between(investimento.getDataAplicacao(), LocalDateTime.now());
        switch (investimento.getTipoInvestimento()) {
        case TESOURO_SELIC: 
            return calcularRendimentoCDB(investimento.getValorInvestido(), taxaSelic, (int) dias);
        case TESOURO_IPCA: 
            BigDecimal ipca = BigDecimal.valueOf(0.045);
            BigDecimal taxaTotal = taxaSelic.add(ipca);
            return calcularRendimentoCDB(investimento.getValorInvestido(), taxaTotal, (int) dias);
        case TESOURO_PREFIXADO: 
            return calcularRendimentoCDB(investimento.getValorInvestido(), investimento.getTaxaRendimento(), (int) dias);
        default: 
            return BigDecimal.ZERO;
        }
    }

    /**
     * Calcula IOF (Imposto sobre Operações Financeiras)
     */
    private BigDecimal calcularIOF(Investimento investimento, boolean resgateAntecipado) {
        if (!resgateAntecipado) {
            return BigDecimal.ZERO;
        }
        long dias = ChronoUnit.DAYS.between(investimento.getDataAplicacao(), LocalDateTime.now());
        if (dias >= 30) {
            return BigDecimal.ZERO;
        }
        BigDecimal aliquotaIOF = BigDecimal.valueOf(0.0038).multiply(BigDecimal.valueOf(30 - dias));
        BigDecimal valorRendimento = investimento.getRendimentoAtual();
        return valorRendimento.multiply(aliquotaIOF).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula IR (Imposto de Renda) sobre investimentos
     */
    private BigDecimal calcularIR(Investimento investimento, boolean resgateAntecipado) {
        if (investimento.getTipoInvestimento() == Investimento.TipoInvestimento.LCA || investimento.getTipoInvestimento() == Investimento.TipoInvestimento.LCI) {
            return BigDecimal.ZERO;
        }
        long dias = ChronoUnit.DAYS.between(investimento.getDataAplicacao(), LocalDateTime.now());
        BigDecimal aliquotaIR = BigDecimal.ZERO;
        if (dias <= 180) {
            aliquotaIR = BigDecimal.valueOf(0.225);
        } else if (dias <= 360) {
            aliquotaIR = BigDecimal.valueOf(0.2);
        } else if (dias <= 720) {
            aliquotaIR = BigDecimal.valueOf(0.175);
        } else {
            aliquotaIR = BigDecimal.valueOf(0.15);
        }
        BigDecimal valorRendimento = investimento.getRendimentoAtual();
        return valorRendimento.multiply(aliquotaIR).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula rendimento final do investimento
     */
    private void calcularRendimentoFinal(Investimento investimento) {
        long dias = ChronoUnit.DAYS.between(investimento.getDataAplicacao(), LocalDateTime.now());
        BigDecimal rendimento = BigDecimal.ZERO;
        switch (investimento.getTipoInvestimento()) {
        case CDB: 
            rendimento = calcularRendimentoCDB(investimento.getValorInvestido(), investimento.getTaxaRendimento(), (int) dias);
            break;
        case LCA: 
        case LCI: 
            rendimento = calcularRendimentoLCA_LCI(investimento.getValorInvestido(), investimento.getTaxaRendimento(), (int) dias);
            break;
        case TESOURO_SELIC: 
        case TESOURO_IPCA: 
        case TESOURO_PREFIXADO: 
            BigDecimal taxaSelic = BigDecimal.valueOf(0.105);
            rendimento = calcularRendimentoTesouro(investimento, taxaSelic);
            break;
        default: 
            rendimento = investimento.getValorInvestido().multiply(investimento.getTaxaRendimento()).multiply(BigDecimal.valueOf(dias)).divide(BigDecimal.valueOf(365), 4, RoundingMode.HALF_UP);
        }
        investimento.setRendimentoAtual(rendimento.setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * Atualiza rendimento do investimento automaticamente
     */
    @Transactional
    public void atualizarRendimento(Long id) {
        log.info("Atualizando rendimento do investimento ID: {}", id);
        Investimento investimento = investimentoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Investimento não encontrado"));
        if (investimento.getStatus() == Investimento.StatusInvestimento.ATIVO) {
            calcularRendimentoFinal(investimento);
            investimentoRepository.save(investimento);
            log.info("Rendimento atualizado: {}", investimento.getRendimentoAtual());
        }
    }

    /**
     * Atualiza rendimento de todos os investimentos ativos
     */
    @Transactional
    public void atualizarRendimentosAtivos() {
        log.info("Atualizando rendimentos de todos os investimentos ativos");
        List<Investimento> investimentos = investimentoRepository.findByStatus(Investimento.StatusInvestimento.ATIVO);
        for (Investimento investimento : investimentos) {
            try {
                calcularRendimentoFinal(investimento);
                investimentoRepository.save(investimento);
            } catch (Exception e) {
                log.error("Erro ao atualizar rendimento do investimento {}: {}", investimento.getId(), e.getMessage());
            }
        }
        log.info("Atualizados {} investimentos", investimentos.size());
    }

    /**
     * Simula investimento antes de aplicar
     */
    public InvestimentoDTO simularInvestimento(Investimento.TipoInvestimento tipo, BigDecimal valorInvestido, BigDecimal taxaAnual, int dias) {
        Investimento investimentoSimulado = new Investimento();
        investimentoSimulado.setTipoInvestimento(tipo);
        investimentoSimulado.setValorInvestido(valorInvestido);
        investimentoSimulado.setTaxaRendimento(taxaAnual);
        investimentoSimulado.setDataAplicacao(LocalDateTime.now());
        investimentoSimulado.setDataVencimento(LocalDateTime.now().plusDays(dias));
        BigDecimal rendimentoBruto = BigDecimal.ZERO;
        switch (tipo) {
        case CDB: 
            rendimentoBruto = calcularRendimentoCDB(valorInvestido, taxaAnual, dias);
            break;
        case LCA: 
        case LCI: 
            rendimentoBruto = calcularRendimentoLCA_LCI(valorInvestido, taxaAnual, dias);
            break;
        default: 
            rendimentoBruto = valorInvestido.multiply(taxaAnual).multiply(BigDecimal.valueOf(dias)).divide(BigDecimal.valueOf(365), 4, RoundingMode.HALF_UP);
        }
        investimentoSimulado.setRendimentoAtual(rendimentoBruto);
        BigDecimal iof = calcularIOFSimulacao(rendimentoBruto, dias);
        BigDecimal ir = calcularIRSimulacao(tipo, rendimentoBruto, dias);
        BigDecimal valorLiquido = valorInvestido.add(rendimentoBruto).subtract(iof).subtract(ir);
        InvestimentoDTO dto = converterParaDTO(investimentoSimulado);
        dto.setValorLiquido(valorLiquido);
        dto.setValorIOF(iof);
        dto.setValorIR(ir);
        return dto;
    }

    private BigDecimal calcularIOFSimulacao(BigDecimal rendimento, int dias) {
        if (dias >= 30) {
            return BigDecimal.ZERO;
        }
        BigDecimal aliquotaIOF = BigDecimal.valueOf(0.0038).multiply(BigDecimal.valueOf(30 - dias));
        return rendimento.multiply(aliquotaIOF).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularIRSimulacao(Investimento.TipoInvestimento tipo, BigDecimal rendimento, int dias) {
        if (tipo == Investimento.TipoInvestimento.LCA || tipo == Investimento.TipoInvestimento.LCI) {
            return BigDecimal.ZERO;
        }
        BigDecimal aliquotaIR = dias <= 180 ? BigDecimal.valueOf(0.225) : dias <= 360 ? BigDecimal.valueOf(0.2) : dias <= 720 ? BigDecimal.valueOf(0.175) : BigDecimal.valueOf(0.15);
        return rendimento.multiply(aliquotaIR).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula rendimento total por conta
     */
    @Transactional(readOnly = true)
    public BigDecimal calcularRendimentoTotalPorConta(Long contaId) {
        log.info("Calculando rendimento total da conta ID: {}", contaId);
        BigDecimal rendimentoTotal = investimentoRepository.somarRendimentoAtualPorConta(contaId);
        return rendimentoTotal != null ? rendimentoTotal : BigDecimal.ZERO;
    }

    /**
     * Calcula valor total investido por conta
     */
    @Transactional(readOnly = true)
    public BigDecimal calcularValorTotalInvestidoPorConta(Long contaId) {
        log.info("Calculando valor total investido da conta ID: {}", contaId);
        BigDecimal valorTotal = investimentoRepository.somarValorTotalPorConta(contaId);
        return valorTotal != null ? valorTotal : BigDecimal.ZERO;
    }

    /**
     * Valida investimento
     */
    private void validarInvestimento(InvestimentoDTO investimento) {
        if (investimento.getValorInvestido().compareTo(BigDecimal.valueOf(100)) < 0) {
            throw new IllegalArgumentException("Valor mínimo para investimento é R$ 100,00");
        }
        if (investimento.getValorInvestido().compareTo(BigDecimal.valueOf(10000000)) > 0) {
            throw new IllegalArgumentException("Valor máximo para investimento é R$ 10.000.000,00");
        }
        if (investimento.getTaxaRendimento().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa de rendimento não pode ser negativa");
        }
        if (investimento.getTaxaRendimento().compareTo(BigDecimal.valueOf(1.0)) > 0) {
            throw new IllegalArgumentException("Taxa de rendimento não pode ser maior que 100%");
        }
    }

    /**
     * Converte entidade para DTO
     */
    private InvestimentoDTO converterParaDTO(Investimento investimento) {
        InvestimentoDTO dto = new InvestimentoDTO();
        dto.setId(investimento.getId());
        dto.setContaId(investimento.getConta() != null ? investimento.getConta().getId() : null);
        dto.setContaNumero(investimento.getConta() != null ? investimento.getConta().getNumeroConta() : null);
        dto.setTipoInvestimento(investimento.getTipoInvestimento());
        dto.setValorInvestido(investimento.getValorInvestido());
        dto.setTaxaRendimento(investimento.getTaxaRendimento());
        dto.setDataAplicacao(investimento.getDataAplicacao());
        dto.setDataVencimento(investimento.getDataVencimento());
        dto.setStatus(investimento.getStatus());
        dto.setRendimentoAtual(investimento.getRendimentoAtual());
        dto.setValorTotal(investimento.getValorTotal());
        dto.setDadosInvestimento(investimento.getDadosInvestimento());
        dto.setDataCriacao(investimento.getDataCriacao() != null ? investimento.getDataCriacao().toString() : null);
        dto.setDataAtualizacao(investimento.getDataAtualizacao() != null ? investimento.getDataAtualizacao().toString() : null);
        return dto;
    }

    @java.lang.SuppressWarnings("all")
    public InvestimentoService(final InvestimentoRepository investimentoRepository) {
        this.investimentoRepository = investimentoRepository;
    }
}
