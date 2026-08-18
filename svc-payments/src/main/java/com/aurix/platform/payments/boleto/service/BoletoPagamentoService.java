package com.aurix.platform.payments.boleto.service;

import com.aurix.platform.payments.boleto.repository.PagamentoBoletoRepository;
import com.aurix.platform.shared.dto.PagamentoBoletoDTO;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.PagamentoBoleto;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço para gestão de boletos registrados.
 * Inclui registro na câmara (CEPEL), baixa, devolução e protesto.
 */
@Service
@Transactional
public class BoletoPagamentoService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BoletoPagamentoService.class);

    private final PagamentoBoletoRepository pagamentoBoletoRepository;
    private final ContaRepository contaRepository;

    /**
     * Registra um novo boleto na câmara (CEPEL).
     */
    public PagamentoBoletoDTO registrarBoleto(PagamentoBoletoDTO dto) {
        log.info("Registrando boleto para conta cedente: {}", dto.getContaCedenteId());
        Conta contaCedente = contaRepository.findById(dto.getContaCedenteId())
                .orElseThrow(() -> new IllegalArgumentException("Conta cedente não encontrada: " + dto.getContaCedenteId()));

        PagamentoBoleto boleto = new PagamentoBoleto();
        boleto.setCodigoBoleto(gerarCodigoBoleto());
        boleto.setNossoNumero(gerarNossoNumero());
        boleto.setCarteira(dto.getCarteira());
        boleto.setConvenio(dto.getConvenio());
        boleto.setContaCedente(contaCedente);
        boleto.setSacadoNome(dto.getSacadoNome());
        boleto.setSacadoDocumento(dto.getSacadoDocumento());
        boleto.setSacadoEndereco(dto.getSacadoEndereco());
        boleto.setSacadoCidade(dto.getSacadoCidade());
        boleto.setSacadoUf(dto.getSacadoUf());
        boleto.setSacadoCep(dto.getSacadoCep());
        boleto.setValorOriginal(dto.getValorOriginal());
        boleto.setValorDesconto(dto.getValorDesconto() != null ? dto.getValorDesconto() : BigDecimal.ZERO);
        boleto.setValorJuros(dto.getValorJuros() != null ? dto.getValorJuros() : BigDecimal.ZERO);
        boleto.setValorMulta(dto.getValorMulta() != null ? dto.getValorMulta() : BigDecimal.ZERO);
        boleto.setDataVencimento(dto.getDataVencimento());
        boleto.setInstrucoes(dto.getInstrucoes());
        boleto.setTipoDocumento(dto.getTipoDocumento() != null ? dto.getTipoDocumento() : PagamentoBoleto.TipoDocumento.BOLETO);
        boleto.setDadosAdicionais(dto.getDadosAdicionais());
        boleto.setCodigoBarras(gerarCodigoBarras());
        boleto.setLinhaDigitavel(gerarLinhaDigitavel());
        boleto.setStatus(PagamentoBoleto.StatusBoleto.REGISTRADO);

        PagamentoBoleto salvo = pagamentoBoletoRepository.save(boleto);
        log.info("Boleto registrado com código: {}, nosso número: {}", salvo.getCodigoBoleto(), salvo.getNossoNumero());
        return converterParaDTO(salvo);
    }

    /**
     * Realiza a baixa de um boleto (quando pago ou por determinação do cedente).
     */
    public void baixarBoleto(Long id, BigDecimal valorPago) {
        log.info("Realizando baixa do boleto ID: {}", id);
        PagamentoBoleto boleto = pagamentoBoletoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Boleto não encontrado: " + id));

        if (boleto.getStatus() == PagamentoBoleto.StatusBoleto.BAIXADO
                || boleto.getStatus() == PagamentoBoleto.StatusBoleto.CANCELADO) {
            throw new IllegalStateException("Boleto já se encontra em status: " + boleto.getStatus().getDescricao());
        }

        boleto.setStatus(PagamentoBoleto.StatusBoleto.BAIXADO);
        boleto.setDataBaixa(LocalDateTime.now());
        boleto.setValorPago(valorPago);
        boleto.setDataPagamento(LocalDateTime.now());
        boleto.setCodigoRetorno("00");
        boleto.setMensagemRetorno("Boleto baixado com sucesso");
        pagamentoBoletoRepository.save(boleto);
        log.info("Boleto baixado com sucesso — código: {}", boleto.getCodigoBoleto());
    }

    /**
     * Registra protesto de boleto na câmara de compensação.
     */
    public void protestarBoleto(Long id) {
        log.info("Registrando protesto do boleto ID: {}", id);
        PagamentoBoleto boleto = pagamentoBoletoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Boleto não encontrado: " + id));

        if (boleto.getStatus() != PagamentoBoleto.StatusBoleto.REGISTRADO
                && boleto.getStatus() != PagamentoBoleto.StatusBoleto.VENCIDO) {
            throw new IllegalStateException("Boleto não pode ser protestado — status atual: " + boleto.getStatus().getDescricao());
        }

        if (boleto.getDataVencimento().isAfter(LocalDateTime.now())) {
            throw new IllegalStateException("Boleto ainda não vencido — data vencimento: " + boleto.getDataVencimento());
        }

        boleto.setStatus(PagamentoBoleto.StatusBoleto.PROTESTADO);
        boleto.setDataProtesto(LocalDateTime.now());
        boleto.setCodigoRetorno("00");
        boleto.setMensagemRetorno("Protesto registrado na câmara de compensação");
        pagamentoBoletoRepository.save(boleto);
        log.info("Boleto protestado com sucesso — código: {}", boleto.getCodigoBoleto());
    }

    /**
     * Busca boleto por ID.
     */
    @Transactional(readOnly = true)
    public PagamentoBoletoDTO buscarPorId(Long id) {
        log.info("Buscando boleto por ID: {}", id);
        PagamentoBoleto boleto = pagamentoBoletoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Boleto não encontrado: " + id));
        return converterParaDTO(boleto);
    }

    /**
     * Lista boletos por conta cedente.
     */
    @Transactional(readOnly = true)
    public List<PagamentoBoletoDTO> listarPorConta(Long contaId) {
        return pagamentoBoletoRepository.findByContaCedenteId(contaId).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista boletos vencidos não pagos.
     */
    @Transactional(readOnly = true)
    public List<PagamentoBoletoDTO> listarVencidos() {
        return pagamentoBoletoRepository.findVencidosAte(LocalDateTime.now()).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Marca boletos vencidos — chamado por scheduler.
     */
    public void marcarBoletosVencidos() {
        log.info("Verificando boletos vencidos para atualização de status");
        List<PagamentoBoleto> vencidos = pagamentoBoletoRepository.findVencidosAte(LocalDateTime.now());
        for (PagamentoBoleto boleto : vencidos) {
            if (boleto.getStatus() == PagamentoBoleto.StatusBoleto.REGISTRADO) {
                boleto.setStatus(PagamentoBoleto.StatusBoleto.VENCIDO);
                pagamentoBoletoRepository.save(boleto);
            }
        }
        log.info("Boletos vencidos marcados: {}", vencidos.size());
    }

    private String gerarCodigoBoleto() {
        return "BLT" + System.currentTimeMillis()
                + UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
    }

    private String gerarNossoNumero() {
        return String.format("%012d", System.currentTimeMillis() % 1000000000000L);
    }

    private String gerarCodigoBarras() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 44);
    }

    private String gerarLinhaDigitavel() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 47);
    }

    private PagamentoBoletoDTO converterParaDTO(PagamentoBoleto entity) {
        PagamentoBoletoDTO dto = new PagamentoBoletoDTO();
        dto.setId(entity.getId());
        dto.setCodigoBoleto(entity.getCodigoBoleto());
        dto.setNossoNumero(entity.getNossoNumero());
        dto.setCarteira(entity.getCarteira());
        dto.setConvenio(entity.getConvenio());
        dto.setContaCedenteId(entity.getContaCedente() != null ? entity.getContaCedente().getId() : null);
        dto.setContaCedenteNumero(entity.getContaCedente() != null ? entity.getContaCedente().getNumeroConta() : null);
        dto.setSacadoNome(entity.getSacadoNome());
        dto.setSacadoDocumento(entity.getSacadoDocumento());
        dto.setSacadoEndereco(entity.getSacadoEndereco());
        dto.setSacadoCidade(entity.getSacadoCidade());
        dto.setSacadoUf(entity.getSacadoUf());
        dto.setSacadoCep(entity.getSacadoCep());
        dto.setValorOriginal(entity.getValorOriginal());
        dto.setValorDesconto(entity.getValorDesconto());
        dto.setValorJuros(entity.getValorJuros());
        dto.setValorMulta(entity.getValorMulta());
        dto.setValorPago(entity.getValorPago());
        dto.setDataEmissao(entity.getDataEmissao());
        dto.setDataVencimento(entity.getDataVencimento());
        dto.setDataPagamento(entity.getDataPagamento());
        dto.setDataBaixa(entity.getDataBaixa());
        dto.setDataProtesto(entity.getDataProtesto());
        dto.setDataLimiteDesconto(entity.getDataLimiteDesconto());
        dto.setStatus(entity.getStatus());
        dto.setTipoDocumento(entity.getTipoDocumento());
        dto.setInstrucoes(entity.getInstrucoes());
        dto.setCodigoBarras(entity.getCodigoBarras());
        dto.setLinhaDigitavel(entity.getLinhaDigitavel());
        dto.setCodigoRetorno(entity.getCodigoRetorno());
        dto.setMensagemRetorno(entity.getMensagemRetorno());
        dto.setDadosAdicionais(entity.getDadosAdicionais());
        dto.setDataCriacao(entity.getDataCriacao() != null ? entity.getDataCriacao().toString() : null);
        dto.setDataAtualizacao(entity.getDataAtualizacao() != null ? entity.getDataAtualizacao().toString() : null);
        return dto;
    }

    @java.lang.SuppressWarnings("all")
    public BoletoPagamentoService(final PagamentoBoletoRepository pagamentoBoletoRepository, final ContaRepository contaRepository) {
        this.pagamentoBoletoRepository = pagamentoBoletoRepository;
        this.contaRepository = contaRepository;
    }
}
