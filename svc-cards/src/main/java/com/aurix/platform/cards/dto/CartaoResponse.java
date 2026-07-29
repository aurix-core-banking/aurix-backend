package com.aurix.platform.cards.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartaoResponse {

    private Long id;
    private Long produtoId;
    private Long contaId;
    private String numeroCartaoMascarado;
    private String nomePortador;
    private String tipoCartao;
    private String bandeira;
    private String status;
    private BigDecimal limiteCredito;
    private BigDecimal limiteDisponivel;
    private BigDecimal limiteUtilizado;
    private Integer diaVencimentoFatura;
    private LocalDateTime dataEmissao;
    private LocalDateTime dataAtivacao;
    private LocalDateTime dataBloqueio;
    private LocalDateTime dataCancelamento;
    private AuditMetaDTO auditoria;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public Long getContaId() { return contaId; }
    public void setContaId(Long contaId) { this.contaId = contaId; }
    public String getNumeroCartaoMascarado() { return numeroCartaoMascarado; }
    public void setNumeroCartaoMascarado(String numeroCartaoMascarado) { this.numeroCartaoMascarado = numeroCartaoMascarado; }
    public String getNomePortador() { return nomePortador; }
    public void setNomePortador(String nomePortador) { this.nomePortador = nomePortador; }
    public String getTipoCartao() { return tipoCartao; }
    public void setTipoCartao(String tipoCartao) { this.tipoCartao = tipoCartao; }
    public String getBandeira() { return bandeira; }
    public void setBandeira(String bandeira) { this.bandeira = bandeira; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getLimiteCredito() { return limiteCredito; }
    public void setLimiteCredito(BigDecimal limiteCredito) { this.limiteCredito = limiteCredito; }
    public BigDecimal getLimiteDisponivel() { return limiteDisponivel; }
    public void setLimiteDisponivel(BigDecimal limiteDisponivel) { this.limiteDisponivel = limiteDisponivel; }
    public BigDecimal getLimiteUtilizado() { return limiteUtilizado; }
    public void setLimiteUtilizado(BigDecimal limiteUtilizado) { this.limiteUtilizado = limiteUtilizado; }
    public Integer getDiaVencimentoFatura() { return diaVencimentoFatura; }
    public void setDiaVencimentoFatura(Integer diaVencimentoFatura) { this.diaVencimentoFatura = diaVencimentoFatura; }
    public LocalDateTime getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(LocalDateTime dataEmissao) { this.dataEmissao = dataEmissao; }
    public LocalDateTime getDataAtivacao() { return dataAtivacao; }
    public void setDataAtivacao(LocalDateTime dataAtivacao) { this.dataAtivacao = dataAtivacao; }
    public LocalDateTime getDataBloqueio() { return dataBloqueio; }
    public void setDataBloqueio(LocalDateTime dataBloqueio) { this.dataBloqueio = dataBloqueio; }
    public LocalDateTime getDataCancelamento() { return dataCancelamento; }
    public void setDataCancelamento(LocalDateTime dataCancelamento) { this.dataCancelamento = dataCancelamento; }
    public AuditMetaDTO getAuditoria() { return auditoria; }
    public void setAuditoria(AuditMetaDTO auditoria) { this.auditoria = auditoria; }
}
