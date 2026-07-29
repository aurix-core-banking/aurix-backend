package com.aurix.platform.banking.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "cargos")
public class Cargo extends BaseEntity {

    @Column(nullable = false)
    private String codigoCargo;

    @Column(nullable = false)
    private String nomeCargo;

    @Column(length = 1000)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_superior_id")
    private Cargo cargoSuperior;

    @OneToMany(mappedBy = "cargoSuperior", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cargo> cargosSubordinados;

    @OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Funcionario> funcionarios;

    @Enumerated(EnumType.STRING)
    private NivelCargo nivelCargo;

    @Enumerated(EnumType.STRING)
    private StatusCargo status = StatusCargo.ATIVO;

    @Column(name = "nivel_hierarquico")
    private Integer nivelHierarquico = 1;

    @Column(name = "faixa_salarial_min", precision = 10, scale = 2)
    private BigDecimal faixaSalarialMin;

    @Column(name = "faixa_salarial_max", precision = 10, scale = 2)
    private BigDecimal faixaSalarialMax;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "permissoes_cargo", columnDefinition = "JSONB")
    private String permissoesCargo;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "alcadas_cargo", columnDefinition = "JSONB")
    private String alcadasCargo;

    public enum NivelCargo {
        OPERACIONAL, SUPERVISAO, GERENCIA, DIRETORIA, PRESIDENCIA
    }

    public enum StatusCargo {
        ATIVO, INATIVO, SUSPENSO, EM_ANALISE
    }

    public Cargo() {
    }

    public Cargo(String codigoCargo, String nomeCargo, Empresa empresa, NivelCargo nivelCargo) {
        this.codigoCargo = codigoCargo;
        this.nomeCargo = nomeCargo;
        this.empresa = empresa;
        this.nivelCargo = nivelCargo;
        setDataCriacao(LocalDateTime.now());
        setDataAtualizacao(LocalDateTime.now());
    }

public String getCodigoCargo() {
        return codigoCargo;
    }

    public void setCodigoCargo(String codigoCargo) {
        this.codigoCargo = codigoCargo;
    }

    public String getNomeCargo() {
        return nomeCargo;
    }

    public void setNomeCargo(String nomeCargo) {
        this.nomeCargo = nomeCargo;
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

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Cargo getCargoSuperior() {
        return cargoSuperior;
    }

    public void setCargoSuperior(Cargo cargoSuperior) {
        this.cargoSuperior = cargoSuperior;
    }

    public List<Cargo> getCargosSubordinados() {
        return cargosSubordinados;
    }

    public void setCargosSubordinados(List<Cargo> cargosSubordinados) {
        this.cargosSubordinados = cargosSubordinados;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public NivelCargo getNivelCargo() {
        return nivelCargo;
    }

    public void setNivelCargo(NivelCargo nivelCargo) {
        this.nivelCargo = nivelCargo;
    }

    public StatusCargo getStatus() {
        return status;
    }

    public void setStatus(StatusCargo status) {
        this.status = status;
    }

    public Integer getNivelHierarquico() {
        return nivelHierarquico;
    }

    public void setNivelHierarquico(Integer nivelHierarquico) {
        this.nivelHierarquico = nivelHierarquico;
    }

    public BigDecimal getFaixaSalarialMin() {
        return faixaSalarialMin;
    }

    public void setFaixaSalarialMin(BigDecimal faixaSalarialMin) {
        this.faixaSalarialMin = faixaSalarialMin;
    }

    public BigDecimal getFaixaSalarialMax() {
        return faixaSalarialMax;
    }

    public void setFaixaSalarialMax(BigDecimal faixaSalarialMax) {
        this.faixaSalarialMax = faixaSalarialMax;
    }

    public String getPermissoesCargo() {
        return permissoesCargo;
    }

    public void setPermissoesCargo(String permissoesCargo) {
        this.permissoesCargo = permissoesCargo;
    }

    public String getAlcadasCargo() {
        return alcadasCargo;
    }

    public void setAlcadasCargo(String alcadasCargo) {
        this.alcadasCargo = alcadasCargo;
    }
}
