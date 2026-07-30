package com.aurix.platform.banking.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "empresas")
public class Empresa extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String codigoEmpresa;

    @Column(nullable = false)
    private String nomeEmpresa;

    @Column(unique = true)
    private String cnpj;

    @Column(length = 1000)
    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusEmpresa status = StatusEmpresa.ATIVA;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_empresa", columnDefinition = "JSONB")
    private String dadosEmpresa;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Departamento> departamentos;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cargo> cargos;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "configuracoes_empresa", columnDefinition = "JSONB")
    private String configuracoesEmpresa;

    public enum StatusEmpresa {
        ATIVA, INATIVA, SUSPENSA, EM_ANALISE
    }

    public Empresa() {
    }

    public Empresa(String codigoEmpresa, String nomeEmpresa, String cnpj) {
        this.codigoEmpresa = codigoEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.cnpj = cnpj;
        setDataCriacao(LocalDateTime.now());
        setDataAtualizacao(LocalDateTime.now());
    }

public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusEmpresa getStatus() {
        return status;
    }

    public void setStatus(StatusEmpresa status) {
        this.status = status;
    }

    public String getDadosEmpresa() {
        return dadosEmpresa;
    }

    public void setDadosEmpresa(String dadosEmpresa) {
        this.dadosEmpresa = dadosEmpresa;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public String getConfiguracoesEmpresa() {
        return configuracoesEmpresa;
    }

    public void setConfiguracoesEmpresa(String configuracoesEmpresa) {
        this.configuracoesEmpresa = configuracoesEmpresa;
    }
}
