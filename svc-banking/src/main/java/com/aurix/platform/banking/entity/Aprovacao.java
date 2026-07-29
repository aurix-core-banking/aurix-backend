package com.aurix.platform.banking.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "aprovacoes")
public class Aprovacao extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitacao_id", nullable = false)
    private SolicitacaoAprovacao solicitacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etapa_workflow_id", nullable = false)
    private EtapaWorkflow etapaWorkflow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aprovador_id", nullable = false)
    private Funcionario aprovador;

    @Enumerated(EnumType.STRING)
    private StatusAprovacao status = StatusAprovacao.PENDENTE;

    @Column(name = "data_aprovacao")
    private LocalDateTime dataAprovacao;

    @Column(length = 1000)
    private String observacoes;

    @Column(length = 1000)
    private String motivoRejeicao;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_aprovacao", columnDefinition = "JSONB")
    private String dadosAprovacao;

    public enum StatusAprovacao {
        PENDENTE, APROVADA, REJEITADA, CANCELADA, VENCIDA
    }

    public Aprovacao() {
    }

    public Aprovacao(SolicitacaoAprovacao solicitacao, EtapaWorkflow etapaWorkflow, Funcionario aprovador) {
        this.solicitacao = solicitacao;
        this.etapaWorkflow = etapaWorkflow;
        this.aprovador = aprovador;
        setDataCriacao(LocalDateTime.now());
        setDataAtualizacao(LocalDateTime.now());
    }

public SolicitacaoAprovacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(SolicitacaoAprovacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public EtapaWorkflow getEtapaWorkflow() {
        return etapaWorkflow;
    }

    public void setEtapaWorkflow(EtapaWorkflow etapaWorkflow) {
        this.etapaWorkflow = etapaWorkflow;
    }

    public Funcionario getAprovador() {
        return aprovador;
    }

    public void setAprovador(Funcionario aprovador) {
        this.aprovador = aprovador;
    }

    public StatusAprovacao getStatus() {
        return status;
    }

    public void setStatus(StatusAprovacao status) {
        this.status = status;
    }

    public LocalDateTime getDataAprovacao() {
        return dataAprovacao;
    }

    public void setDataAprovacao(LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getMotivoRejeicao() {
        return motivoRejeicao;
    }

    public void setMotivoRejeicao(String motivoRejeicao) {
        this.motivoRejeicao = motivoRejeicao;
    }

    public String getDadosAprovacao() {
        return dadosAprovacao;
    }

    public void setDadosAprovacao(String dadosAprovacao) {
        this.dadosAprovacao = dadosAprovacao;
    }
}
