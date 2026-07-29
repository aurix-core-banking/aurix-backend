package com.aurix.platform.banking.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "departamentos")
public class Departamento extends BaseEntity {

    @Column(nullable = false)
    private String codigoDepartamento;

    @Column(nullable = false)
    private String nomeDepartamento;

    @Column(length = 1000)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_pai_id")
    private Departamento departamentoPai;

    @OneToMany(mappedBy = "departamentoPai", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Departamento> subDepartamentos;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cargo> cargos;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Funcionario> funcionarios;

    @Enumerated(EnumType.STRING)
    private StatusDepartamento status = StatusDepartamento.ATIVO;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "configuracoes_departamento", columnDefinition = "JSONB")
    private String configuracoesDepartamento;

    @Column(name = "nivel_hierarquico")
    private Integer nivelHierarquico = 1;

    public enum StatusDepartamento {
        ATIVO, INATIVO, SUSPENSO, EM_ANALISE
    }

    public Departamento() {
    }

    public Departamento(String codigoDepartamento, String nomeDepartamento, Empresa empresa) {
        this.codigoDepartamento = codigoDepartamento;
        this.nomeDepartamento = nomeDepartamento;
        this.empresa = empresa;
        setDataCriacao(LocalDateTime.now());
        setDataAtualizacao(LocalDateTime.now());
    }

public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getNomeDepartamento() {
        return nomeDepartamento;
    }

    public void setNomeDepartamento(String nomeDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Departamento getDepartamentoPai() {
        return departamentoPai;
    }

    public void setDepartamentoPai(Departamento departamentoPai) {
        this.departamentoPai = departamentoPai;
    }

    public List<Departamento> getSubDepartamentos() {
        return subDepartamentos;
    }

    public void setSubDepartamentos(List<Departamento> subDepartamentos) {
        this.subDepartamentos = subDepartamentos;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public StatusDepartamento getStatus() {
        return status;
    }

    public void setStatus(StatusDepartamento status) {
        this.status = status;
    }

    public String getConfiguracoesDepartamento() {
        return configuracoesDepartamento;
    }

    public void setConfiguracoesDepartamento(String configuracoesDepartamento) {
        this.configuracoesDepartamento = configuracoesDepartamento;
    }

    public Integer getNivelHierarquico() {
        return nivelHierarquico;
    }

    public void setNivelHierarquico(Integer nivelHierarquico) {
        this.nivelHierarquico = nivelHierarquico;
    }
}
