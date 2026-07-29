package com.aurix.platform.banking.dto;

import com.aurix.platform.banking.entity.Funcionario;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FuncionarioDTO {
    private Long id;
    private String matricula;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String telefone;
    private Long empresaId;
    private Long departamentoId;
    private Long cargoId;
    private Long gestorId;
    private Funcionario.StatusFuncionario status;
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;
    private BigDecimal salarioAtual;
    private String dadosFuncionario;
    private String permissoesFuncionario;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    
    public FuncionarioDTO() {}
    
    public FuncionarioDTO(Funcionario funcionario) {
        this.id = funcionario.getId();
        this.matricula = funcionario.getMatricula();
        this.nomeCompleto = funcionario.getNomeCompleto();
        this.cpf = funcionario.getCpf();
        this.email = funcionario.getEmail();
        this.telefone = funcionario.getTelefone();
        this.empresaId = funcionario.getEmpresa().getId();
        this.departamentoId = funcionario.getDepartamento() != null ? funcionario.getDepartamento().getId() : null;
        this.cargoId = funcionario.getCargo() != null ? funcionario.getCargo().getId() : null;
        this.gestorId = funcionario.getGestor() != null ? funcionario.getGestor().getId() : null;
        this.status = funcionario.getStatus();
        this.dataAdmissao = funcionario.getDataAdmissao();
        this.dataDemissao = funcionario.getDataDemissao();
        this.salarioAtual = funcionario.getSalarioAtual();
        this.dadosFuncionario = funcionario.getDadosFuncionario();
        this.permissoesFuncionario = funcionario.getPermissoesFuncionario();
        this.dataCriacao = funcionario.getDataCriacao();
        this.dataAtualizacao = funcionario.getDataAtualizacao();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public Long getEmpresaId() { return empresaId; }
    public void setEmpresaId(Long empresaId) { this.empresaId = empresaId; }
    
    public Long getDepartamentoId() { return departamentoId; }
    public void setDepartamentoId(Long departamentoId) { this.departamentoId = departamentoId; }
    
    public Long getCargoId() { return cargoId; }
    public void setCargoId(Long cargoId) { this.cargoId = cargoId; }
    
    public Long getGestorId() { return gestorId; }
    public void setGestorId(Long gestorId) { this.gestorId = gestorId; }
    
    public Funcionario.StatusFuncionario getStatus() { return status; }
    public void setStatus(Funcionario.StatusFuncionario status) { this.status = status; }
    
    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public void setDataAdmissao(LocalDate dataAdmissao) { this.dataAdmissao = dataAdmissao; }
    
    public LocalDate getDataDemissao() { return dataDemissao; }
    public void setDataDemissao(LocalDate dataDemissao) { this.dataDemissao = dataDemissao; }
    
    public BigDecimal getSalarioAtual() { return salarioAtual; }
    public void setSalarioAtual(BigDecimal salarioAtual) { this.salarioAtual = salarioAtual; }
    
    public String getDadosFuncionario() { return dadosFuncionario; }
    public void setDadosFuncionario(String dadosFuncionario) { this.dadosFuncionario = dadosFuncionario; }
    
    public String getPermissoesFuncionario() { return permissoesFuncionario; }
    public void setPermissoesFuncionario(String permissoesFuncionario) { this.permissoesFuncionario = permissoesFuncionario; }
    
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}
