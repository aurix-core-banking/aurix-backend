package com.aurix.platform.banking.boleto.service;

import com.aurix.platform.banking.boleto.dto.BoletoRequest;
import com.aurix.platform.banking.boleto.dto.BoletoResponse;
import com.aurix.platform.banking.boleto.entity.BoletoRegistrado;
import com.aurix.platform.banking.boleto.entity.BoletoRegistrado.StatusBoleto;
import com.aurix.platform.banking.boleto.repository.BoletoRegistradoRepository;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class BoletoService {

    private static final Logger log = LoggerFactory.getLogger(BoletoService.class);
    private static final int TAMANHO_CODIGO_BARRAS = 44;
    private static final BigDecimal PERCENTUAL_MULTA_DEFAULT = new BigDecimal("2.00");
    private static final BigDecimal PERCENTUAL_JUROS_MES_DEFAULT = new BigDecimal("1.00");

    private final BoletoRegistradoRepository boletoRepository;
    private final ContaRepository contaRepository;

    public BoletoService(BoletoRegistradoRepository boletoRepository, ContaRepository contaRepository) {
        this.boletoRepository = boletoRepository;
        this.contaRepository = contaRepository;
    }

    public BoletoResponse registrar(BoletoRequest request) {
        log.info("Registrando boleto: contaId={}, valor={}, vencimento={}",
            request.getContaId(), request.getValor(), request.getDataVencimento());

        String tenantId = TenantContext.getTenantId();

        Conta conta = contaRepository.findByTenantIdAndId(tenantId, request.getContaId())
            .orElseThrow(() -> new IllegalArgumentException("Conta nao encontrada: " + request.getContaId()));

        if (request.getDataVencimento().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data de vencimento nao pode ser anterior a hoje");
        }

        String codigoBarras = gerarCodigoBarras();
        String linhaDigitavel = gerarLinhaDigitavel(codigoBarras);
        String nossoNumero = gerarNossoNumero();

        BoletoRegistrado boleto = new BoletoRegistrado();
        boleto.setTenantId(tenantId);
        boleto.setCodigoBarras(codigoBarras);
        boleto.setLinhaDigitavel(linhaDigitavel);
        boleto.setContaId(conta.getId());
        boleto.setContaNumero(conta.getNumeroConta());
        boleto.setValor(request.getValor());
        boleto.setDataVencimento(request.getDataVencimento());
        boleto.setBeneficiarioNome(request.getBeneficiarioNome());
        boleto.setBeneficiarioDocumento(request.getBeneficiarioDocumento());
        boleto.setPagadorNome(request.getPagadorNome());
        boleto.setPagadorDocumento(request.getPagadorDocumento());
        boleto.setDescricao(request.getDescricao());
        boleto.setNossoNumero(nossoNumero);
        boleto.setAceite(request.getAceite() != null ? request.getAceite() : false);
        boleto.setMultaPercentual(request.getMultaPercentual() != null ? request.getMultaPercentual() : PERCENTUAL_MULTA_DEFAULT);
        boleto.setJurosPercentualMes(request.getJurosPercentualMes() != null ? request.getJurosPercentualMes() : PERCENTUAL_JUROS_MES_DEFAULT);
        boleto.setQtdeDiasProtesto(request.getQtdeDiasProtesto());
        boleto.setStatus(StatusBoleto.PENDENTE);

        BoletoRegistrado salvo = boletoRepository.save(boleto);

        log.info("Boleto registrado: id={}, codigoBarras={}, nossoNumero={}",
            salvo.getId(), salvo.getCodigoBarras(), salvo.getNossoNumero());

        return converterParaResponse(salvo);
    }

    @Transactional(readOnly = true)
    public BoletoResponse buscarPorId(Long id) {
        String tenantId = TenantContext.getTenantId();
        BoletoRegistrado boleto = boletoRepository.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new IllegalArgumentException("Boleto nao encontrado: " + id));
        return converterParaResponse(boleto);
    }

    public BoletoResponse registrarPagamento(Long boletoId, Long contaPagadorId) {
        log.info("Registrando pagamento boleto: boletoId={}, contaPagadorId={}", boletoId, contaPagadorId);

        String tenantId = TenantContext.getTenantId();

        BoletoRegistrado boleto = boletoRepository.findByTenantIdAndId(tenantId, boletoId)
            .orElseThrow(() -> new IllegalArgumentException("Boleto nao encontrado: " + boletoId));

        if (boleto.getStatus() != StatusBoleto.PENDENTE && boleto.getStatus() != StatusBoleto.VENCIDO) {
            throw new IllegalArgumentException("Boleto nao esta pendente de pagamento");
        }

        Conta contaPagador = contaRepository.findByTenantIdAndId(tenantId, contaPagadorId)
            .orElseThrow(() -> new IllegalArgumentException("Conta pagadora nao encontrada: " + contaPagadorId));

        if (contaPagador.getStatus() != Conta.StatusConta.ATIVA) {
            throw new IllegalArgumentException("Conta pagadora nao esta ativa");
        }

        BigDecimal valorMulta = BigDecimal.ZERO;
        BigDecimal valorJuros = BigDecimal.ZERO;
        BigDecimal valorTotal = boleto.getValor();

        if (boleto.getStatus() == StatusBoleto.VENCIDO) {
            long diasAtraso = ChronoUnit.DAYS.between(boleto.getDataVencimento(), LocalDate.now());

            if (boleto.getMultaPercentual() != null && boleto.getMultaPercentual().compareTo(BigDecimal.ZERO) > 0) {
                valorMulta = boleto.getValor()
                    .multiply(boleto.getMultaPercentual())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            }

            if (boleto.getJurosPercentualMes() != null && boleto.getJurosPercentualMes().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal taxaDiaria = boleto.getJurosPercentualMes()
                    .divide(new BigDecimal("30"), 8, RoundingMode.HALF_UP);
                valorJuros = boleto.getValor()
                    .multiply(taxaDiaria)
                    .multiply(BigDecimal.valueOf(diasAtraso))
                    .setScale(2, RoundingMode.HALF_UP);
            }

            valorTotal = boleto.getValor().add(valorMulta).add(valorJuros);
        }

        if (contaPagador.getSaldo().compareTo(valorTotal) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para pagamento do boleto. Necessario: R$ " + valorTotal);
        }

        boleto.setStatus(StatusBoleto.PAGO);
        boleto.setDataPagamento(LocalDateTime.now());
        boleto.setValorMulta(valorMulta);
        boleto.setValorJuros(valorJuros);
        boleto.setValorTotalPago(valorTotal);
        boletoRepository.save(boleto);

        log.info("Boleto pago: id={}, valorTotal={}, multa={}, juros={}",
            boleto.getId(), valorTotal, valorMulta, valorJuros);

        return converterParaResponse(boleto);
    }

    @Transactional(readOnly = true)
    public List<BoletoResponse> listarVencidos() {
        String tenantId = TenantContext.getTenantId();
        List<BoletoRegistrado> vencidos = boletoRepository.findVencidosByTenant(tenantId, LocalDate.now());
        return vencidos.stream().map(this::converterParaResponse).collect(Collectors.toList());
    }

    public void baixarAutomaticamente(Long boletoId) {
        log.info("Baixa automatica do boleto: {}", boletoId);
        String tenantId = TenantContext.getTenantId();

        BoletoRegistrado boleto = boletoRepository.findByTenantIdAndId(tenantId, boletoId)
            .orElseThrow(() -> new IllegalArgumentException("Boleto nao encontrado: " + boletoId));

        if (boleto.getStatus() == StatusBoleto.PAGO) {
            boleto.setStatus(StatusBoleto.BAIXADO);
            boleto.setDataBaixa(LocalDateTime.now());
            boletoRepository.save(boleto);
            log.info("Boleto baixado automaticamente: id={}", boleto.getId());
        }
    }

    public void protestar(Long boletoId) {
        log.info("Protestando boleto: {}", boletoId);
        String tenantId = TenantContext.getTenantId();

        BoletoRegistrado boleto = boletoRepository.findByTenantIdAndId(tenantId, boletoId)
            .orElseThrow(() -> new IllegalArgumentException("Boleto nao encontrado: " + boletoId));

        if (boleto.getStatus() != StatusBoleto.VENCIDO) {
            throw new IllegalArgumentException("Apenas boletos vencidos podem ser protestados");
        }

        if (boleto.getQtdeDiasProtesto() == null || boleto.getQtdeDiasProtesto() <= 0) {
            throw new IllegalArgumentException("Boleto nao configurado para protesto");
        }

        long diasDesdeVencimento = ChronoUnit.DAYS.between(boleto.getDataVencimento(), LocalDate.now());
        if (diasDesdeVencimento < boleto.getQtdeDiasProtesto()) {
            throw new IllegalArgumentException("Boleto ainda nao atingiu o prazo para protesto (" + boleto.getQtdeDiasProtesto() + " dias)");
        }

        boleto.setStatus(StatusBoleto.PROTESTADO);
        boleto.setDataProtesto(LocalDateTime.now());
        boletoRepository.save(boleto);

        log.info("Boleto protestado: id={}", boleto.getId());
    }

    public void cancelar(Long boletoId) {
        log.info("Cancelando boleto: {}", boletoId);
        String tenantId = TenantContext.getTenantId();

        BoletoRegistrado boleto = boletoRepository.findByTenantIdAndId(tenantId, boletoId)
            .orElseThrow(() -> new IllegalArgumentException("Boleto nao encontrado: " + boletoId));

        if (boleto.getStatus() == StatusBoleto.PAGO || boleto.getStatus() == StatusBoleto.BAIXADO) {
            throw new IllegalArgumentException("Boleto ja pago nao pode ser cancelado");
        }

        boleto.setStatus(StatusBoleto.CANCELADO);
        boletoRepository.save(boleto);

        log.info("Boleto cancelado: id={}", boleto.getId());
    }

    private String gerarCodigoBarras() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TAMANHO_CODIGO_BARRAS; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }

    private String gerarLinhaDigitavel(String codigoBarras) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 54; i++) {
            sb.append(i < codigoBarras.length() ? codigoBarras.charAt(i) : '0');
        }
        return sb.toString();
    }

    private String gerarNossoNumero() {
        return "Nosso-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
    }

    private BoletoResponse converterParaResponse(BoletoRegistrado boleto) {
        BoletoResponse response = new BoletoResponse();
        response.setId(boleto.getId());
        response.setCodigoBarras(boleto.getCodigoBarras());
        response.setLinhaDigitavel(boleto.getLinhaDigitavel());
        response.setContaId(boleto.getContaId());
        response.setContaNumero(boleto.getContaNumero());
        response.setValor(boleto.getValor());
        response.setDataVencimento(boleto.getDataVencimento());
        response.setStatus(boleto.getStatus());
        response.setMultaPercentual(boleto.getMultaPercentual());
        response.setJurosPercentualMes(boleto.getJurosPercentualMes());
        response.setValorMulta(boleto.getValorMulta());
        response.setValorJuros(boleto.getValorJuros());
        response.setValorTotalPago(boleto.getValorTotalPago());
        response.setDataPagamento(boleto.getDataPagamento());
        response.setDataBaixa(boleto.getDataBaixa());
        response.setNossoNumero(boleto.getNossoNumero());
        response.setBeneficiarioNome(boleto.getBeneficiarioNome());
        response.setPagadorNome(boleto.getPagadorNome());
        response.setDescricao(boleto.getDescricao());
        response.setDataCriacao(boleto.getDataCriacao());
        return response;
    }
}
