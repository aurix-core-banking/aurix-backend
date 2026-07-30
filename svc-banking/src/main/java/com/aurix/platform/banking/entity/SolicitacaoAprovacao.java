package com.aurix.platform.banking.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "solicitacoes_aprovacao")
public class SolicitacaoAprovacao extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String codigoSolicitacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id", nullable = false)
    private Workflow workflow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Funcionario solicitante;

    @Enumerated(EnumType.STRING)
    private TipoSolicitacao tipoSolicitacao;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacao status = StatusSolicitacao.PENDENTE;

    @Column(name = "valor_solicitado", precision = 15, scale = 2)
    private BigDecimal valorSolicitado;

    @Column(length = 2000)
    private String descricaoSolicitacao;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_solicitacao", columnDefinition = "JSONB")
    private String dadosSolicitacao;

    @OneToMany(mappedBy = "solicitacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Aprovacao> aprovacoes;

    @Column(name = "data_aprovacao")
    private LocalDateTime dataAprovacao;

    @Column(name = "data_rejeicao")
    private LocalDateTime dataRejeicao;

    @Column(length = 1000)
    private String motivoRejeicao;

    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "prioridade")
    private Integer prioridade = 1;

    @Column(name = "data_vencimento")
    private LocalDateTime dataVencimento;

    public enum TipoSolicitacao {
        CREDITO, TRANSACAO, CONTRATO, INVESTIMENTO, EMPRESTIMO, OUTROS
    }

    public enum StatusSolicitacao {
        PENDENTE, EM_ANALISE, APROVADA, REJEITADA, CANCELADA, VENCIDA
    }

    public SolicitacaoAprovacao() {
    }

    public SolicitacaoAprovacao(String codigoSolicitacao, Workflow workflow, Funcionario solicitante,
            TipoSolicitacao tipoSolicitacao, BigDecimal valorSolicitado) {
        this.codigoSolicitacao = codigoSolicitacao;
        this.workflow = workflow;
        this.solicitante = solicitante;
        this.tipoSolicitacao = tipoSolicitacao;
        this.valorSolicitado = valorSolicitado;
        setDataCriacao(LocalDateTime.now());
        setDataAtualizacao(LocalDateTime.now());
    }

public String getCodigoSolicitacao() {
        return codigoSolicitacao;
    }

    public void setCodigoSolicitacao(String codigoSolicitacao) {
        this.codigoSolicitacao = codigoSolicitacao;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public Funcionario getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Funcionario solicitante) {
        this.solicitante = solicitante;
    }

    public TipoSolicitacao getTipoSolicitacao() {
        return tipoSolicitacao;
    }

    public void setTipoSolicitacao(TipoSolicitacao tipoSolicitacao) {
        this.tipoSolicitacao = tipoSolicitacao;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    public BigDecimal getValorSolicitado() {
        return valorSolicitado;
    }

    public void setValorSolicitado(BigDecimal valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }

    public String getDescricaoSolicitacao() {
        return descricaoSolicitacao;
    }

    public void setDescricaoSolicitacao(String descricaoSolicitacao) {
        this.descricaoSolicitacao = descricaoSolicitacao;
    }

    public String getDadosSolicitacao() {
        return dadosSolicitacao;
    }

    public void setDadosSolicitacao(String dadosSolicitacao) {
        this.dadosSolicitacao = dadosSolicitacao;
    }

    public List<Aprovacao> getAprovacoes() {
        return aprovacoes;
    }

    public void setAprovacoes(List<Aprovacao> aprovacoes) {
        this.aprovacoes = aprovacoes;
    }

    public LocalDateTime getDataAprovacao() {
        return dataAprovacao;
    }

    public void setDataAprovacao(LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    public LocalDateTime getDataRejeicao() {
        return dataRejeicao;
    }

    public void setDataRejeicao(LocalDateTime dataRejeicao) {
        this.dataRejeicao = dataRejeicao;
    }

    public String getMotivoRejeicao() {
        return motivoRejeicao;
    }

    public void setMotivoRejeicao(String motivoRejeicao) {
        this.motivoRejeicao = motivoRejeicao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
