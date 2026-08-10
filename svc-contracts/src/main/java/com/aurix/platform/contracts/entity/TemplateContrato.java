package com.aurix.platform.contracts.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "templates_contrato", schema = "aurix")
public class TemplateContrato extends BaseEntity {

    public enum StatusTemplate {
        ATIVO, INATIVO
    }

    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(nullable = false, length = 200)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contrato", nullable = false, length = 30)
    private Contrato.TipoContrato tipoContrato;

    @Column(name = "corpo_texto", nullable = false, columnDefinition = "TEXT")
    private String corpoTexto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusTemplate status = StatusTemplate.ATIVO;

    @Column(nullable = false)
    private Integer versao = 1;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Contrato.TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(Contrato.TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public String getCorpoTexto() {
        return corpoTexto;
    }

    public void setCorpoTexto(String corpoTexto) {
        this.corpoTexto = corpoTexto;
    }

    public StatusTemplate getStatus() {
        return status;
    }

    public void setStatus(StatusTemplate status) {
        this.status = status;
    }

    public Integer getVersao() {
        return versao;
    }

    public void setVersao(Integer versao) {
        this.versao = versao;
    }
}
