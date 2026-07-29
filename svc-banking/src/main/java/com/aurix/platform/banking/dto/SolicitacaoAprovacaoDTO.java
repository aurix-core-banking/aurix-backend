package com.aurix.platform.banking.dto;

import com.aurix.platform.banking.entity.SolicitacaoAprovacao;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SolicitacaoAprovacaoDTO {
    private Long id;
    private String codigoSolicitacao;
    private Long workflowId;
    private Long solicitanteId;
    private SolicitacaoAprovacao.TipoSolicitacao tipoSolicitacao;
    private SolicitacaoAprovacao.StatusSolicitacao status;
    private BigDecimal valorSolicitado;
    private String descricaoSolicitacao;
    private String dadosSolicitacao;
    private LocalDateTime dataAprovacao;
    private LocalDateTime dataRejeicao;
    private String motivoRejeicao;
    private String observacoes;
    private Integer prioridade;
    private LocalDateTime dataVencimento;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    
    public SolicitacaoAprovacaoDTO() {}
    
    public SolicitacaoAprovacaoDTO(SolicitacaoAprovacao solicitacao) {
        this.id = solicitacao.getId();
        this.codigoSolicitacao = solicitacao.getCodigoSolicitacao();
        this.workflowId = solicitacao.getWorkflow().getId();
        this.solicitanteId = solicitacao.getSolicitante().getId();
        this.tipoSolicitacao = solicitacao.getTipoSolicitacao();
        this.status = solicitacao.getStatus();
        this.valorSolicitado = solicitacao.getValorSolicitado();
        this.descricaoSolicitacao = solicitacao.getDescricaoSolicitacao();
        this.dadosSolicitacao = solicitacao.getDadosSolicitacao();
        this.dataAprovacao = solicitacao.getDataAprovacao();
        this.dataRejeicao = solicitacao.getDataRejeicao();
        this.motivoRejeicao = solicitacao.getMotivoRejeicao();
        this.observacoes = solicitacao.getObservacoes();
        this.prioridade = solicitacao.getPrioridade();
        this.dataVencimento = solicitacao.getDataVencimento();
        this.dataCriacao = solicitacao.getDataCriacao();
        this.dataAtualizacao = solicitacao.getDataAtualizacao();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCodigoSolicitacao() { return codigoSolicitacao; }
    public void setCodigoSolicitacao(String codigoSolicitacao) { this.codigoSolicitacao = codigoSolicitacao; }
    
    public Long getWorkflowId() { return workflowId; }
    public void setWorkflowId(Long workflowId) { this.workflowId = workflowId; }
    
    public Long getSolicitanteId() { return solicitanteId; }
    public void setSolicitanteId(Long solicitanteId) { this.solicitanteId = solicitanteId; }
    
    public SolicitacaoAprovacao.TipoSolicitacao getTipoSolicitacao() { return tipoSolicitacao; }
    public void setTipoSolicitacao(SolicitacaoAprovacao.TipoSolicitacao tipoSolicitacao) { this.tipoSolicitacao = tipoSolicitacao; }
    
    public SolicitacaoAprovacao.StatusSolicitacao getStatus() { return status; }
    public void setStatus(SolicitacaoAprovacao.StatusSolicitacao status) { this.status = status; }
    
    public BigDecimal getValorSolicitado() { return valorSolicitado; }
    public void setValorSolicitado(BigDecimal valorSolicitado) { this.valorSolicitado = valorSolicitado; }
    
    public String getDescricaoSolicitacao() { return descricaoSolicitacao; }
    public void setDescricaoSolicitacao(String descricaoSolicitacao) { this.descricaoSolicitacao = descricaoSolicitacao; }
    
    public String getDadosSolicitacao() { return dadosSolicitacao; }
    public void setDadosSolicitacao(String dadosSolicitacao) { this.dadosSolicitacao = dadosSolicitacao; }
    
    public LocalDateTime getDataAprovacao() { return dataAprovacao; }
    public void setDataAprovacao(LocalDateTime dataAprovacao) { this.dataAprovacao = dataAprovacao; }
    
    public LocalDateTime getDataRejeicao() { return dataRejeicao; }
    public void setDataRejeicao(LocalDateTime dataRejeicao) { this.dataRejeicao = dataRejeicao; }
    
    public String getMotivoRejeicao() { return motivoRejeicao; }
    public void setMotivoRejeicao(String motivoRejeicao) { this.motivoRejeicao = motivoRejeicao; }
    
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    
    public Integer getPrioridade() { return prioridade; }
    public void setPrioridade(Integer prioridade) { this.prioridade = prioridade; }
    
    public LocalDateTime getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDateTime dataVencimento) { this.dataVencimento = dataVencimento; }
    
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}
