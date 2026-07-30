package com.aurix.platform.finance.service;

import com.aurix.platform.finance.entity.ContaPagar;
import com.aurix.platform.finance.repository.ContaPagarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Service para gestão de contas a pagar
 * 
 * Gerencia todo o ciclo de vida das contas a pagar
 */
@Service
@Transactional
public class ContaPagarService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ContaPagarService.class);
    private final ContaPagarRepository contaPagarRepository;

    /**
     * Cria uma nova conta a pagar
     */
    public ContaPagar criarContaPagar(ContaPagar contaPagar) {
        log.info("Criando conta a pagar: {} - Fornecedor: {}", contaPagar.getNumeroDocumento(), contaPagar.getFornecedor().getId());
        // Calcular valor total
        calcularValores(contaPagar);
        // Definir status inicial
        contaPagar.setStatus(ContaPagar.StatusConta.PENDENTE);
        // Definir data de criação
        contaPagar.setDataEmissao(LocalDate.now());
        ContaPagar contaSalva = contaPagarRepository.save(contaPagar);
        log.info("Conta a pagar criada: {} - ID: {}", contaPagar.getNumeroDocumento(), contaSalva.getId());
        return contaSalva;
    }

    /**
     * Aprova uma conta a pagar
     */
    public ContaPagar aprovarContaPagar(Long id, String usuarioAprovacao) {
        log.info("Aprovando conta a pagar: {}", id);
        ContaPagar conta = contaPagarRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta a pagar não encontrada: " + id));
        if (conta.getStatus() != ContaPagar.StatusConta.PENDENTE) {
            throw new RuntimeException("Conta não está pendente para aprovação");
        }
        conta.setStatus(ContaPagar.StatusConta.APROVADA);
        conta.setUsuarioAprovacao(usuarioAprovacao);
        conta.setDataAprovacao(java.time.LocalDateTime.now());
        ContaPagar contaAprovada = contaPagarRepository.save(conta);
        log.info("Conta a pagar aprovada: {}", id);
        return contaAprovada;
    }

    /**
     * Registra o pagamento de uma conta
     */
    public ContaPagar pagarConta(Long id, BigDecimal valorPago, LocalDate dataPagamento) {
        log.info("Registrando pagamento da conta: {} - Valor: {}", id, valorPago);
        ContaPagar conta = contaPagarRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta a pagar não encontrada: " + id));
        if (conta.getStatus() != ContaPagar.StatusConta.APROVADA) {
            throw new RuntimeException("Conta não está aprovada para pagamento");
        }
        // Calcular juros e multa se houver atraso
        if (dataPagamento.isAfter(conta.getDataVencimento())) {
            calcularJurosMulta(conta, dataPagamento);
        }
        conta.setValorPago(valorPago);
        conta.setDataPagamento(dataPagamento);
        conta.setStatus(ContaPagar.StatusConta.PAGA);
        // Recalcular valor total
        calcularValores(conta);
        ContaPagar contaPaga = contaPagarRepository.save(conta);
        log.info("Pagamento registrado: {} - Valor pago: {}", id, valorPago);
        return contaPaga;
    }

    /**
     * Cancela uma conta a pagar
     */
    public ContaPagar cancelarConta(Long id, String motivo) {
        log.info("Cancelando conta a pagar: {} - Motivo: {}", id, motivo);
        ContaPagar conta = contaPagarRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta a pagar não encontrada: " + id));
        if (conta.getStatus() == ContaPagar.StatusConta.PAGA) {
            throw new RuntimeException("Não é possível cancelar conta já paga");
        }
        conta.setStatus(ContaPagar.StatusConta.CANCELADA);
        conta.setObservacoes(conta.getObservacoes() + "\nCancelada: " + motivo);
        ContaPagar contaCancelada = contaPagarRepository.save(conta);
        log.info("Conta cancelada: {}", id);
        return contaCancelada;
    }

    /**
     * Busca contas vencidas
     */
    public List<ContaPagar> buscarContasVencidas() {
        log.info("Buscando contas vencidas");
        LocalDate hoje = LocalDate.now();
        List<ContaPagar> contasVencidas = contaPagarRepository.findContasVencidas(hoje);
        log.info("Encontradas {} contas vencidas", contasVencidas.size());
        return contasVencidas;
    }

    /**
     * Busca contas próximas do vencimento
     */
    public List<ContaPagar> buscarContasProximasVencimento(int dias) {
        log.info("Buscando contas próximas do vencimento em {} dias", dias);
        LocalDate hoje = LocalDate.now();
        LocalDate dataLimite = hoje.plusDays(dias);
        List<ContaPagar> contasProximas = contaPagarRepository.findContasProximasVencimento(hoje, dataLimite);
        log.info("Encontradas {} contas próximas do vencimento", contasProximas.size());
        return contasProximas;
    }

    /**
     * Calcula totais por status
     */
    public BigDecimal calcularTotalPorStatus(ContaPagar.StatusConta status) {
        BigDecimal total = contaPagarRepository.somaValorPorStatus(status);
        return total != null ? total : BigDecimal.ZERO;
    }

    /**
     * Calcula totais por período
     */
    public BigDecimal calcularTotalPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        BigDecimal total = contaPagarRepository.somaValorPorPeriodo(dataInicio, dataFim);
        return total != null ? total : BigDecimal.ZERO;
    }

    /**
     * Calcula valores da conta (total, juros, multa, desconto)
     */
    private void calcularValores(ContaPagar conta) {
        BigDecimal valorOriginal = conta.getValorOriginal();
        BigDecimal juros = conta.getValorJuros() != null ? conta.getValorJuros() : BigDecimal.ZERO;
        BigDecimal multa = conta.getValorMulta() != null ? conta.getValorMulta() : BigDecimal.ZERO;
        BigDecimal desconto = conta.getValorDesconto() != null ? conta.getValorDesconto() : BigDecimal.ZERO;
        BigDecimal valorTotal = valorOriginal.add(juros).add(multa).subtract(desconto);
        conta.setValorTotal(valorTotal.setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * Calcula juros e multa por atraso
     */
    private void calcularJurosMulta(ContaPagar conta, LocalDate dataPagamento) {
        long diasAtraso = ChronoUnit.DAYS.between(conta.getDataVencimento(), dataPagamento);
        if (diasAtraso > 0) {
            // Calcular multa (2% sobre valor original)
            BigDecimal multa = conta.getValorOriginal().multiply(BigDecimal.valueOf(0.02));
            conta.setValorMulta(multa);
            // Calcular juros (1% ao mês sobre valor original)
            BigDecimal taxaJurosDiaria = BigDecimal.valueOf(0.01).divide(BigDecimal.valueOf(30), 6, RoundingMode.HALF_UP);
            BigDecimal juros = conta.getValorOriginal().multiply(taxaJurosDiaria).multiply(BigDecimal.valueOf(diasAtraso));
            conta.setValorJuros(juros);
            log.info("Calculados juros e multa para conta {}: Multa={}, Juros={}, Dias atraso={}", conta.getId(), multa, juros, diasAtraso);
        }
    }

    @java.lang.SuppressWarnings("all")
    public ContaPagarService(final ContaPagarRepository contaPagarRepository) {
        this.contaPagarRepository = contaPagarRepository;
    }
}
