package com.aurix.platform.banking.salario.dto;

import java.time.LocalDateTime;

public class ConvenioResponse {
    private Long id;
    private String cnpj;
    private String razaoSocial;
    private Long contaCorrenteId;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public ConvenioResponse() {}
    public Long getId() { return id; }
    public void setId(Long v) { this.id = v; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String v) { this.cnpj = v; }
    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String v) { this.razaoSocial = v; }
    public Long getContaCorrenteId() { return contaCorrenteId; }
    public void setContaCorrenteId(Long v) { this.contaCorrenteId = v; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean v) { this.ativo = v; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime v) { this.dataCriacao = v; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime v) { this.dataAtualizacao = v; }
}
