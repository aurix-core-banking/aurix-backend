package com.aurix.platform.banking.salario.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "convenios_empresa", schema = "aurix")
public class ConvenioEmpresa extends BaseEntity {

    @NotBlank
    @Column(name = "cnpj", nullable = false, length = 14)
    private String cnpj;

    @NotBlank
    @Column(name = "razao_social", nullable = false, length = 200)
    private String razaoSocial;

    @NotNull
    @Column(name = "conta_corrente_id", nullable = false)
    private Long contaCorrenteId;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    public ConvenioEmpresa() {}

    public ConvenioEmpresa(String cnpj, String razaoSocial, Long contaCorrenteId) {
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.contaCorrenteId = contaCorrenteId;
    }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
    public Long getContaCorrenteId() { return contaCorrenteId; }
    public void setContaCorrenteId(Long contaCorrenteId) { this.contaCorrenteId = contaCorrenteId; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
