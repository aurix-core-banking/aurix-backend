package com.aurix.platform.banking.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "etapas_workflow")
public class EtapaWorkflow extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id", nullable = false)
    private Workflow workflow;

    @Column(nullable = false)
    private String nomeEtapa;

    @Column(length = 1000)
    private String descricao;

    @Column(name = "ordem_etapa")
    private Integer ordemEtapa;

    @Enumerated(EnumType.STRING)
    private TipoAprovacao tipoAprovacao;

    @Enumerated(EnumType.STRING)
    private StatusEtapa status = StatusEtapa.ATIVA;

    @Column(name = "valor_minimo", precision = 15, scale = 2)
    private BigDecimal valorMinimo;

    @Column(name = "valor_maximo", precision = 15, scale = 2)
    private BigDecimal valorMaximo;

    @Column(name = "nivel_hierarquico_necessario")
    private Integer nivelHierarquicoNecessario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_aprovador_id")
    private Cargo cargoAprovador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_aprovador_id")
    private Departamento departamentoAprovador;

    @OneToMany(mappedBy = "etapaWorkflow", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Aprovacao> aprovacoes;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "regras_etapa", columnDefinition = "JSONB")
    private String regrasEtapa;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "condicoes_etapa", columnDefinition = "JSONB")
    private String condicoesEtapa;

    @Column(name = "timeout_horas")
    private Integer timeoutHoras = 24;

    public enum TipoAprovacao {
        SEQUENCIAL, PARALELA, QUALQUER_UM, TODOS
    }

    public enum StatusEtapa {
        ATIVA, INATIVA, SUSPENSA, EM_ANALISE
    }

    public EtapaWorkflow() {
    }

    public EtapaWorkflow(Workflow workflow, String nomeEtapa, Integer ordemEtapa, TipoAprovacao tipoAprovacao) {
        this.workflow = workflow;
        this.nomeEtapa = nomeEtapa;
        this.ordemEtapa = ordemEtapa;
        this.tipoAprovacao = tipoAprovacao;
        setDataCriacao(LocalDateTime.now());
        setDataAtualizacao(LocalDateTime.now());
    }

public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public String getNomeEtapa() {
        return nomeEtapa;
    }

    public void setNomeEtapa(String nomeEtapa) {
        this.nomeEtapa = nomeEtapa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getOrdemEtapa() {
        return ordemEtapa;
    }

    public void setOrdemEtapa(Integer ordemEtapa) {
        this.ordemEtapa = ordemEtapa;
    }

    public TipoAprovacao getTipoAprovacao() {
        return tipoAprovacao;
    }

    public void setTipoAprovacao(TipoAprovacao tipoAprovacao) {
        this.tipoAprovacao = tipoAprovacao;
    }

    public StatusEtapa getStatus() {
        return status;
    }

    public void setStatus(StatusEtapa status) {
        this.status = status;
    }

    public BigDecimal getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(BigDecimal valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public BigDecimal getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(BigDecimal valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public Integer getNivelHierarquicoNecessario() {
        return nivelHierarquicoNecessario;
    }

    public void setNivelHierarquicoNecessario(Integer nivelHierarquicoNecessario) {
        this.nivelHierarquicoNecessario = nivelHierarquicoNecessario;
    }

    public Cargo getCargoAprovador() {
        return cargoAprovador;
    }

    public void setCargoAprovador(Cargo cargoAprovador) {
        this.cargoAprovador = cargoAprovador;
    }

    public Departamento getDepartamentoAprovador() {
        return departamentoAprovador;
    }

    public void setDepartamentoAprovador(Departamento departamentoAprovador) {
        this.departamentoAprovador = departamentoAprovador;
    }

    public List<Aprovacao> getAprovacoes() {
        return aprovacoes;
    }

    public void setAprovacoes(List<Aprovacao> aprovacoes) {
        this.aprovacoes = aprovacoes;
    }

    public String getRegrasEtapa() {
        return regrasEtapa;
    }

    public void setRegrasEtapa(String regrasEtapa) {
        this.regrasEtapa = regrasEtapa;
    }

    public String getCondicoesEtapa() {
        return condicoesEtapa;
    }

    public void setCondicoesEtapa(String condicoesEtapa) {
        this.condicoesEtapa = condicoesEtapa;
    }

    public Integer getTimeoutHoras() {
        return timeoutHoras;
    }

    public void setTimeoutHoras(Integer timeoutHoras) {
        this.timeoutHoras = timeoutHoras;
    }
}
