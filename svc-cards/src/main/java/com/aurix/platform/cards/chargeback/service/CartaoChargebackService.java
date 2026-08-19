package com.aurix.platform.cards.chargeback.service;

import com.aurix.platform.cards.entity.Cartao;
import com.aurix.platform.cards.entity.TransacaoCartao;
import com.aurix.platform.cards.repository.CartaoRepository;
import com.aurix.platform.cards.repository.TransacaoCartaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class CartaoChargebackService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CartaoChargebackService.class);
    private final CartaoRepository cartaoRepository;
    private final TransacaoCartaoRepository transacaoCartaoRepository;

    private static final int PRAZO_MAXIMO_DIAS = 120;
    private static final int DIAS_ESTORNO_TEMPORARIO = 5;
    private static final int DIAS_RESOLUCAO_MIN = 45;
    private static final int DIAS_RESOLUCAO_MAX = 90;

    private final Map<Long, ChargebackEntry> chargebacks = new LinkedHashMap<>();
    private static final AtomicLong COUNTER = new AtomicLong(System.currentTimeMillis());
    private static final Set<String> MOTIVOS_VALIDOS = Set.of(
            "FRAUDE", "NAO_RECONHECIDO", "PRODUTO_NAO_RECEBIDO", "VALOR_INCORRETO"
    );

    @java.lang.SuppressWarnings("all")
    public CartaoChargebackService(final CartaoRepository cartaoRepository,
                                    final TransacaoCartaoRepository transacaoCartaoRepository) {
        this.cartaoRepository = cartaoRepository;
        this.transacaoCartaoRepository = transacaoCartaoRepository;
    }

    public Map<String, Object> solicitar(Long transacaoId, Long cartaoId, String motivo, String descricao) {
        if (!MOTIVOS_VALIDOS.contains(motivo)) {
            throw new RuntimeException("Motivo inválido. Use: " + MOTIVOS_VALIDOS);
        }
        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado: " + cartaoId));

        TransacaoCartao transacao = transacaoCartaoRepository.findById(transacaoId)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada: " + transacaoId));

        if (transacao.getDataTransacao().plusDays(PRAZO_MAXIMO_DIAS).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Prazo de 120 dias excedido. Transação em: " + transacao.getDataTransacao());
        }

        for (ChargebackEntry entry : chargebacks.values()) {
            if (entry.getTransacaoId().equals(transacaoId) && entry.getStatus() != ChargebackStatus.RESOLVIDO) {
                throw new RuntimeException("Já existe chargeback pendente para esta transação");
            }
        }

        ChargebackEntry entry = new ChargebackEntry();
        entry.setChargebackId(COUNTER.incrementAndGet());
        entry.setTransacaoId(transacaoId);
        entry.setCartaoId(cartaoId);
        entry.setMotivo(motivo);
        entry.setDescricao(descricao != null ? descricao : "");
        entry.setValorTransacao(transacao.getValor());
        entry.setStatus(ChargebackStatus.ANALISE);
        entry.setDataSolicitacao(LocalDateTime.now());
        entry.setDataPrazoResolucao(LocalDateTime.now().plusDays(DIAS_RESOLUCAO_MAX));
        entry.setEstornoTemporario(LocalDateTime.now().plusDays(DIAS_ESTORNO_TEMPORARIO));
        entry.setEvidencias(new ArrayList<>());
        chargebacks.put(entry.getChargebackId(), entry);

        log.info("Chargeback solicitado: id={}, transacaoId={}, motivo={}", entry.getChargebackId(), transacaoId, motivo);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("chargebackId", entry.getChargebackId());
        resultado.put("transacaoId", transacaoId);
        resultado.put("cartaoId", cartaoId);
        resultado.put("motivo", motivo);
        resultado.put("valorTransacao", entry.getValorTransacao());
        resultado.put("status", entry.getStatus().name());
        resultado.put("dataSolicitacao", entry.getDataSolicitacao());
        resultado.put("dataPrazoResolucao", entry.getDataPrazoResolucao());
        resultado.put("dataEstornoTemporario", entry.getEstornoTemporario());
        return resultado;
    }

    public Map<String, Object> consultar(Long id) {
        ChargebackEntry entry = chargebacks.get(id);
        if (entry == null) {
            throw new RuntimeException("Chargeback não encontrado: " + id);
        }
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("chargebackId", entry.getChargebackId());
        resultado.put("transacaoId", entry.getTransacaoId());
        resultado.put("cartaoId", entry.getCartaoId());
        resultado.put("motivo", entry.getMotivo());
        resultado.put("descricao", entry.getDescricao());
        resultado.put("valorTransacao", entry.getValorTransacao());
        resultado.put("status", entry.getStatus().name());
        resultado.put("dataSolicitacao", entry.getDataSolicitacao());
        resultado.put("dataPrazoResolucao", entry.getDataPrazoResolucao());
        resultado.put("dataEstornoTemporario", entry.getEstornoTemporario());
        resultado.put("qtdEvidencias", entry.getEvidencias().size());
        resultado.put("evidencias", entry.getEvidencias());
        return resultado;
    }

    public Map<String, Object> adicionarEvidencia(Long chargebackId, String descricao, MultipartFile arquivo) {
        ChargebackEntry entry = chargebacks.get(chargebackId);
        if (entry == null) {
            throw new RuntimeException("Chargeback não encontrado: " + chargebackId);
        }
        if (entry.getStatus() == ChargebackStatus.RESOLVIDO || entry.getStatus() == ChargebackStatus.REJEITADO) {
            throw new RuntimeException("Não é possível adicionar evidência a chargeback finalizado");
        }

        Evidencia evidencia = new Evidencia();
        evidencia.setEvidenciaId(COUNTER.incrementAndGet());
        evidencia.setDescricao(descricao);
        evidencia.setDataEnvio(LocalDateTime.now());
        if (arquivo != null && !arquivo.isEmpty()) {
            evidencia.setNomeArquivo(arquivo.getOriginalFilename());
            evidencia.setTamanhoArquivo(arquivo.getSize());
        }
        entry.getEvidencias().add(evidencia);

        if (entry.getStatus() == ChargebackStatus.ANALISE) {
            entry.setStatus(ChargebackStatus.CONTESTACAO);
        }

        log.info("Evidência adicionada: chargebackId={}, evidenciaId={}", chargebackId, evidencia.getEvidenciaId());

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("chargebackId", entry.getChargebackId());
        resultado.put("evidenciaId", evidencia.getEvidenciaId());
        resultado.put("descricao", evidencia.getDescricao());
        resultado.put("status", entry.getStatus().name());
        resultado.put("qtdEvidencias", entry.getEvidencias().size());
        return resultado;
    }

    public enum ChargebackStatus {
        ANALISE, CONTESTACAO, RESOLVIDO, REJEITADO
    }

    public static class ChargebackEntry {
        private Long chargebackId;
        private Long transacaoId;
        private Long cartaoId;
        private String motivo;
        private String descricao;
        private BigDecimal valorTransacao;
        private ChargebackStatus status;
        private LocalDateTime dataSolicitacao;
        private LocalDateTime dataPrazoResolucao;
        private LocalDateTime estornoTemporario;
        private List<Evidencia> evidencias;

        public Long getChargebackId() { return chargebackId; }
        public void setChargebackId(Long chargebackId) { this.chargebackId = chargebackId; }
        public Long getTransacaoId() { return transacaoId; }
        public void setTransacaoId(Long transacaoId) { this.transacaoId = transacaoId; }
        public Long getCartaoId() { return cartaoId; }
        public void setCartaoId(Long cartaoId) { this.cartaoId = cartaoId; }
        public String getMotivo() { return motivo; }
        public void setMotivo(String motivo) { this.motivo = motivo; }
        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
        public BigDecimal getValorTransacao() { return valorTransacao; }
        public void setValorTransacao(BigDecimal valorTransacao) { this.valorTransacao = valorTransacao; }
        public ChargebackStatus getStatus() { return status; }
        public void setStatus(ChargebackStatus status) { this.status = status; }
        public LocalDateTime getDataSolicitacao() { return dataSolicitacao; }
        public void setDataSolicitacao(LocalDateTime dataSolicitacao) { this.dataSolicitacao = dataSolicitacao; }
        public LocalDateTime getDataPrazoResolucao() { return dataPrazoResolucao; }
        public void setDataPrazoResolucao(LocalDateTime dataPrazoResolucao) { this.dataPrazoResolucao = dataPrazoResolucao; }
        public LocalDateTime getEstornoTemporario() { return estornoTemporario; }
        public void setEstornoTemporario(LocalDateTime estornoTemporario) { this.estornoTemporario = estornoTemporario; }
        public List<Evidencia> getEvidencias() { return evidencias; }
        public void setEvidencias(List<Evidencia> evidencias) { this.evidencias = evidencias; }
    }

    public static class Evidencia {
        private Long evidenciaId;
        private String descricao;
        private String nomeArquivo;
        private Long tamanhoArquivo;
        private LocalDateTime dataEnvio;

        public Long getEvidenciaId() { return evidenciaId; }
        public void setEvidenciaId(Long evidenciaId) { this.evidenciaId = evidenciaId; }
        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
        public String getNomeArquivo() { return nomeArquivo; }
        public void setNomeArquivo(String nomeArquivo) { this.nomeArquivo = nomeArquivo; }
        public Long getTamanhoArquivo() { return tamanhoArquivo; }
        public void setTamanhoArquivo(Long tamanhoArquivo) { this.tamanhoArquivo = tamanhoArquivo; }
        public LocalDateTime getDataEnvio() { return dataEnvio; }
        public void setDataEnvio(LocalDateTime dataEnvio) { this.dataEnvio = dataEnvio; }
    }

    private static class AtomicLong {
        private long value;
        AtomicLong(long init) { this.value = init; }
        synchronized long incrementAndGet() { return ++value; }
    }
}
