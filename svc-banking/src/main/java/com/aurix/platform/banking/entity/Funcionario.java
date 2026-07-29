package com.aurix.platform.banking.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "funcionarios")
public class Funcionario extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;

    @Column
    private String telefone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gestor_id")
    private Funcionario gestor;

    @OneToMany(mappedBy = "gestor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Funcionario> subordinados;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DelegacaoPoder> delegacoes;

    @Enumerated(EnumType.STRING)
    private StatusFuncionario status = StatusFuncionario.ATIVO;

    @Column(name = "data_admissao")
    private LocalDate dataAdmissao;

    @Column(name = "data_demissao")
    private LocalDate dataDemissao;

    @Column(name = "salario_atual", precision = 10, scale = 2)
    private BigDecimal salarioAtual;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_funcionario", columnDefinition = "JSONB")
    private String dadosFuncionario;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "permissoes_funcionario", columnDefinition = "JSONB")
    private String permissoesFuncionario;

    public enum StatusFuncionario {
        ATIVO, INATIVO, AFASTADO, FERIAS, LICENCA
    }

    public Funcionario() {
    }

    public Funcionario(String matricula, String nomeCompleto, String cpf, String email, Empresa empresa) {
        this.matricula = matricula;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.empresa = empresa;
        setDataCriacao(LocalDateTime.now());
        setDataAtualizacao(LocalDateTime.now());
    }

public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Funcionario getGestor() {
        return gestor;
    }

    public void setGestor(Funcionario gestor) {
        this.gestor = gestor;
    }

    public List<Funcionario> getSubordinados() {
        return subordinados;
    }

    public void setSubordinados(List<Funcionario> subordinados) {
        this.subordinados = subordinados;
    }

    public List<DelegacaoPoder> getDelegacoes() {
        return delegacoes;
    }

    public void setDelegacoes(List<DelegacaoPoder> delegacoes) {
        this.delegacoes = delegacoes;
    }

    public StatusFuncionario getStatus() {
        return status;
    }

    public void setStatus(StatusFuncionario status) {
        this.status = status;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public LocalDate getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(LocalDate dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    public BigDecimal getSalarioAtual() {
        return salarioAtual;
    }

    public void setSalarioAtual(BigDecimal salarioAtual) {
        this.salarioAtual = salarioAtual;
    }

    public String getDadosFuncionario() {
        return dadosFuncionario;
    }

    public void setDadosFuncionario(String dadosFuncionario) {
        this.dadosFuncionario = dadosFuncionario;
    }

    public String getPermissoesFuncionario() {
        return permissoesFuncionario;
    }

    public void setPermissoesFuncionario(String permissoesFuncionario) {
        this.permissoesFuncionario = permissoesFuncionario;
    }
}
