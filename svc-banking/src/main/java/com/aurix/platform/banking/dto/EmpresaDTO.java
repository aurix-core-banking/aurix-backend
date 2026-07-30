package com.aurix.platform.banking.dto;

import com.aurix.platform.banking.entity.Empresa;
import java.time.LocalDateTime;

public class EmpresaDTO {
    private Long id;
    private String codigoEmpresa;
    private String nomeEmpresa;
    private String cnpj;
    private String descricao;
    private Empresa.StatusEmpresa status;
    private String dadosEmpresa;
    private String configuracoesEmpresa;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    
    public EmpresaDTO() {}
    
    public EmpresaDTO(Empresa empresa) {
        this.id = empresa.getId();
        this.codigoEmpresa = empresa.getCodigoEmpresa();
        this.nomeEmpresa = empresa.getNomeEmpresa();
        this.cnpj = empresa.getCnpj();
        this.descricao = empresa.getDescricao();
        this.status = empresa.getStatus();
        this.dadosEmpresa = empresa.getDadosEmpresa();
        this.configuracoesEmpresa = empresa.getConfiguracoesEmpresa();
        this.dataCriacao = empresa.getDataCriacao();
        this.dataAtualizacao = empresa.getDataAtualizacao();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCodigoEmpresa() { return codigoEmpresa; }
    public void setCodigoEmpresa(String codigoEmpresa) { this.codigoEmpresa = codigoEmpresa; }
    
    public String getNomeEmpresa() { return nomeEmpresa; }
    public void setNomeEmpresa(String nomeEmpresa) { this.nomeEmpresa = nomeEmpresa; }
    
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public Empresa.StatusEmpresa getStatus() { return status; }
    public void setStatus(Empresa.StatusEmpresa status) { this.status = status; }
    
    public String getDadosEmpresa() { return dadosEmpresa; }
    public void setDadosEmpresa(String dadosEmpresa) { this.dadosEmpresa = dadosEmpresa; }
    
    public String getConfiguracoesEmpresa() { return configuracoesEmpresa; }
    public void setConfiguracoesEmpresa(String configuracoesEmpresa) { this.configuracoesEmpresa = configuracoesEmpresa; }
    
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}
