package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.LiquidacaoDTO;
import com.aurix.platform.banking.core.entity.Boleto;
import com.aurix.platform.banking.core.integration.BoletoProvider;
import com.aurix.platform.banking.core.repository.BoletoRepository;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.Transacao;
import com.aurix.platform.shared.tenant.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class BoletoService {
    private final BoletoRepository boletoRepository;
    private final ContaRepository contaRepository;
    private final TransacaoService transacaoService;
    private final LiquidacaoService liquidacaoService;
    private final BoletoProvider boletoProvider;

    public Boleto emitir(Long contaIdPagador, String beneficiarioNome, String beneficiarioDocumento, String pagadorNome, String pagadorDocumento, BigDecimal valor, LocalDate dataVencimento, String descricao, boolean usarProvedorExterno) {
        String tenantId = TenantContext.getTenantId();
        String numeroBoleto;
        String linhaDigitavel;
        String codigoBarras = null;
        String pdfPath = null;
        if (usarProvedorExterno) {
            Optional<BoletoProvider.BoletoInfo> info = boletoProvider.gerarBoletoReal(beneficiarioDocumento, beneficiarioNome, valor, dataVencimento, pagadorDocumento, pagadorNome, descricao);
            if (info.isPresent()) {
                numeroBoleto = info.get().getNumeroBoleto();
                linhaDigitavel = info.get().getLinhaDigitavel();
                codigoBarras = info.get().getCodigoBarras();
                pdfPath = info.get().getPdfUrl();
            } else {
                numeroBoleto = gerarNumeroBoleto();
                linhaDigitavel = gerarLinhaDigitavel(numeroBoleto);
            }
        } else {
            numeroBoleto = gerarNumeroBoleto();
            linhaDigitavel = gerarLinhaDigitavel(numeroBoleto);
        }
        Boleto b = new Boleto();
        b.setTenantId(tenantId);
        b.setNumeroBoleto(numeroBoleto);
        b.setLinhaDigitavel(linhaDigitavel);
        b.setCodigoBarras(codigoBarras);
        b.setValor(valor);
        b.setDataVencimento(dataVencimento);
        b.setBeneficiarioNome(beneficiarioNome);
        b.setBeneficiarioDocumento(beneficiarioDocumento);
        b.setPagadorNome(pagadorNome);
        b.setPagadorDocumento(pagadorDocumento);
        b.setContaIdPagador(contaIdPagador);
        b.setStatus(Boleto.StatusBoleto.PENDENTE);
        b.setPdfPath(pdfPath);
        b.setDescricao(descricao);
        return boletoRepository.save(b);
    }

    public Boleto registrarPagamento(Long boletoId, Long contaIdPagador) {
        String tenantId = TenantContext.getTenantId();
        Boleto boleto = boletoRepository.findById(boletoId).orElseThrow(() -> new RuntimeException("Boleto não encontrado"));
        if (boleto.getStatus() != Boleto.StatusBoleto.PENDENTE && boleto.getStatus() != Boleto.StatusBoleto.VENCIDO) {
            throw new RuntimeException("Boleto não está pendente de pagamento");
        }
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, contaIdPagador).orElseThrow(() -> new RuntimeException("Conta pagadora não encontrada"));
        if (boleto.getContaIdPagador() != null && !boleto.getContaIdPagador().equals(contaIdPagador)) {
            throw new RuntimeException("Conta informada não é a conta pagadora do boleto");
        }
        com.aurix.platform.shared.dto.TransacaoDTO txDto = new com.aurix.platform.shared.dto.TransacaoDTO();
        txDto.setContaOrigemId(conta.getId());
        txDto.setContaDestinoId(null);
        txDto.setTipoTransacao(Transacao.TipoTransacao.PAGAMENTO_BOLETO);
        txDto.setValor(boleto.getValor());
        txDto.setDescricao("Pagamento boleto " + boleto.getNumeroBoleto());
        com.aurix.platform.shared.dto.TransacaoDTO txCriada = transacaoService.criar(txDto);
        LiquidacaoDTO liqDto = new LiquidacaoDTO();
        liqDto.setTransacaoId(txCriada.getId());
        liqDto.setTipoLiquidacao("PAGAMENTO_BOLETO");
        liqDto.setStatus("PENDENTE");
        liqDto.setValorLiquidacao(boleto.getValor());
        liqDto.setValorTaxa(BigDecimal.ZERO);
        liqDto.setValorTotal(boleto.getValor());
        liqDto.setProcessamentoAutomatico(true);
        liqDto.setReversivel(true);
        liquidacaoService.criarLiquidacao(liqDto);
        boleto.setStatus(Boleto.StatusBoleto.PAGO);
        boleto.setDataPagamento(LocalDateTime.now());
        boleto.setContaIdPagador(contaIdPagador);
        return boletoRepository.save(boleto);
    }

    public Optional<Boleto> buscarPorId(Long id) {
        return boletoRepository.findById(id);
    }

    public Optional<Boleto> buscarPorNumero(String numeroBoleto) {
        return boletoRepository.findByNumeroBoleto(numeroBoleto);
    }

    public List<Boleto> listarPorConta(Long contaIdPagador) {
        return boletoRepository.findByContaIdPagador(contaIdPagador);
    }

    public List<Boleto> listarPorStatus(Boleto.StatusBoleto status) {
        return boletoRepository.findByStatus(status);
    }

    private String gerarNumeroBoleto() {
        return "BOL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
    }

    private String gerarLinhaDigitavel(String numero) {
        StringBuilder sb = new StringBuilder();
        String base = numero.replaceAll("[^0-9]", "");
        for (int i = 0; i < 47; i++) {
            sb.append(base.length() > i ? base.charAt(i) : '0');
        }
        return sb.toString();
    }

    @java.lang.SuppressWarnings("all")
    public BoletoService(final BoletoRepository boletoRepository, final ContaRepository contaRepository, final TransacaoService transacaoService, final LiquidacaoService liquidacaoService, final BoletoProvider boletoProvider) {
        this.boletoRepository = boletoRepository;
        this.contaRepository = contaRepository;
        this.transacaoService = transacaoService;
        this.liquidacaoService = liquidacaoService;
        this.boletoProvider = boletoProvider;
    }
}
