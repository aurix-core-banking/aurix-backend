package com.aurix.platform.banking.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "delegacoes_poder")
public class DelegacaoPoder extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delegante_id", nullable = false)
    private Funcionario delegante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id")
    private Workflow workflow;

    @Enumerated(EnumType.STRING)
    private TipoDelegacao tipoDelegacao;

    @Enumerated(EnumType.STRING)
    private StatusDelegacao status = StatusDelegacao.ATIVA;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    @Column(length = 1000)
    private String descricao;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "permissoes_delegadas", columnDefinition = "JSONB")
    private String permissoesDelegadas;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "alcadas_delegadas", columnDefinition = "JSONB")
    private String alcadasDelegadas;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "condicoes_delegacao", columnDefinition = "JSONB")
    private String condicoesDelegacao;

    public enum TipoDelegacao {
        TEMPORARIA, PERMANENTE, ESPECIFICA, GERAL
    }

    public enum StatusDelegacao {
        ATIVA, INATIVA, SUSPENSA, VENCIDA
    }

    public DelegacaoPoder() {
    }

    public DelegacaoPoder(Funcionario delegante, Funcionario funcionario, TipoDelegacao tipoDelegacao,
            LocalDateTime dataInicio, LocalDateTime dataFim) {
        this.delegante = delegante;
        this.funcionario = funcionario;
        this.tipoDelegacao = tipoDelegacao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        setDataCriacao(LocalDateTime.now());
        setDataAtualizacao(LocalDateTime.now());
    }

public Funcionario getDelegante() {
        return delegante;
    }

    public void setDelegante(Funcionario delegante) {
        this.delegante = delegante;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public TipoDelegacao getTipoDelegacao() {
        return tipoDelegacao;
    }

    public void setTipoDelegacao(TipoDelegacao tipoDelegacao) {
        this.tipoDelegacao = tipoDelegacao;
    }

    public StatusDelegacao getStatus() {
        return status;
    }

    public void setStatus(StatusDelegacao status) {
        this.status = status;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPermissoesDelegadas() {
        return permissoesDelegadas;
    }

    public void setPermissoesDelegadas(String permissoesDelegadas) {
        this.permissoesDelegadas = permissoesDelegadas;
    }

    public String getAlcadasDelegadas() {
        return alcadasDelegadas;
    }

    public void setAlcadasDelegadas(String alcadasDelegadas) {
        this.alcadasDelegadas = alcadasDelegadas;
    }

    public String getCondicoesDelegacao() {
        return condicoesDelegacao;
    }

    public void setCondicoesDelegacao(String condicoesDelegacao) {
        this.condicoesDelegacao = condicoesDelegacao;
    }
}
