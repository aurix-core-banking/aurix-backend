package com.aurix.platform.banking.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "workflows")
public class Workflow extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String codigoWorkflow;

    @Column(nullable = false)
    private String nomeWorkflow;

    @Column(length = 1000)
    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoWorkflow tipoWorkflow;

    @Enumerated(EnumType.STRING)
    private StatusWorkflow status = StatusWorkflow.ATIVO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EtapaWorkflow> etapas;

    @OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SolicitacaoAprovacao> solicitacoes;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "configuracoes_workflow", columnDefinition = "JSONB")
    private String configuracoesWorkflow;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "regras_workflow", columnDefinition = "JSONB")
    private String regrasWorkflow;

    @Column(name = "timeout_horas")
    private Integer timeoutHoras = 24;

    public enum TipoWorkflow {
        APROVACAO_CREDITO, APROVACAO_TRANSACAO, APROVACAO_CONTRATO,
        APROVACAO_INVESTIMENTO, APROVACAO_EMPRESTIMO, APROVACAO_GERAL
    }

    public enum StatusWorkflow {
        ATIVO, INATIVO, SUSPENSO, EM_ANALISE
    }

    public Workflow() {
    }

    public Workflow(String codigoWorkflow, String nomeWorkflow, TipoWorkflow tipoWorkflow, Empresa empresa) {
        this.codigoWorkflow = codigoWorkflow;
        this.nomeWorkflow = nomeWorkflow;
        this.tipoWorkflow = tipoWorkflow;
        this.empresa = empresa;
        setDataCriacao(LocalDateTime.now());
        setDataAtualizacao(LocalDateTime.now());
    }

public String getCodigoWorkflow() {
        return codigoWorkflow;
    }

    public void setCodigoWorkflow(String codigoWorkflow) {
        this.codigoWorkflow = codigoWorkflow;
    }

    public String getNomeWorkflow() {
        return nomeWorkflow;
    }

    public void setNomeWorkflow(String nomeWorkflow) {
        this.nomeWorkflow = nomeWorkflow;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoWorkflow getTipoWorkflow() {
        return tipoWorkflow;
    }

    public void setTipoWorkflow(TipoWorkflow tipoWorkflow) {
        this.tipoWorkflow = tipoWorkflow;
    }

    public StatusWorkflow getStatus() {
        return status;
    }

    public void setStatus(StatusWorkflow status) {
        this.status = status;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<EtapaWorkflow> getEtapas() {
        return etapas;
    }

    public void setEtapas(List<EtapaWorkflow> etapas) {
        this.etapas = etapas;
    }

    public List<SolicitacaoAprovacao> getSolicitacoes() {
        return solicitacoes;
    }

    public void setSolicitacoes(List<SolicitacaoAprovacao> solicitacoes) {
        this.solicitacoes = solicitacoes;
    }

    public String getConfiguracoesWorkflow() {
        return configuracoesWorkflow;
    }

    public void setConfiguracoesWorkflow(String configuracoesWorkflow) {
        this.configuracoesWorkflow = configuracoesWorkflow;
    }

    public String getRegrasWorkflow() {
        return regrasWorkflow;
    }

    public void setRegrasWorkflow(String regrasWorkflow) {
        this.regrasWorkflow = regrasWorkflow;
    }

    public Integer getTimeoutHoras() {
        return timeoutHoras;
    }

    public void setTimeoutHoras(Integer timeoutHoras) {
        this.timeoutHoras = timeoutHoras;
    }
}
