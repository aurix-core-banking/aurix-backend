package com.aurix.platform.banking.salario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ConvenioRequest {
    @NotBlank
    private String cnpj;
    @NotBlank
    private String razaoSocial;
    @NotNull
    private Long contaCorrenteId;

    public ConvenioRequest() {}
    public String getCnpj() { return cnpj; }
    public void setCnpj(String v) { this.cnpj = v; }
    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String v) { this.razaoSocial = v; }
    public Long getContaCorrenteId() { return contaCorrenteId; }
    public void setContaCorrenteId(Long v) { this.contaCorrenteId = v; }
}
