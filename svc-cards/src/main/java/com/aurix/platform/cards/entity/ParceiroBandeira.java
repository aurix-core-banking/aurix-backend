package com.aurix.platform.cards.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "parceiros_bandeira", schema = "aurix")
public class ParceiroBandeira extends BaseEntity {
    @Column(nullable = false, length = 50)
    private String nome;
    @Column(length = 100)
    private String tipoEndpoint;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSONB")
    private String config;
    @Column(nullable = false)
    private Boolean ativo = true;

@java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    @java.lang.SuppressWarnings("all")
    public String getTipoEndpoint() {
        return this.tipoEndpoint;
    }

    @java.lang.SuppressWarnings("all")
    public String getConfig() {
        return this.config;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getAtivo() {
        return this.ativo;
    }

@java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoEndpoint(final String tipoEndpoint) {
        this.tipoEndpoint = tipoEndpoint;
    }

    @java.lang.SuppressWarnings("all")
    public void setConfig(final String config) {
        this.config = config;
    }

    @java.lang.SuppressWarnings("all")
    public void setAtivo(final Boolean ativo) {
        this.ativo = ativo;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ParceiroBandeira(id=" + this.getId() + ", nome=" + this.getNome() + ", tipoEndpoint=" + this.getTipoEndpoint() + ", config=" + this.getConfig() + ", ativo=" + this.getAtivo() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ParceiroBandeira() {
    }

    @java.lang.SuppressWarnings("all")
    public ParceiroBandeira(final Long id, final String nome, final String tipoEndpoint, final String config, final Boolean ativo) {
        this.setId(id);
        this.nome = nome;
        this.tipoEndpoint = tipoEndpoint;
        this.config = config;
        this.ativo = ativo;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ParceiroBandeira)) return false;
        final ParceiroBandeira other = (ParceiroBandeira) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$tipoEndpoint = this.getTipoEndpoint();
        final java.lang.Object other$tipoEndpoint = other.getTipoEndpoint();
        if (this$tipoEndpoint == null ? other$tipoEndpoint != null : !this$tipoEndpoint.equals(other$tipoEndpoint)) return false;
        final java.lang.Object this$config = this.getConfig();
        final java.lang.Object other$config = other.getConfig();
        if (this$config == null ? other$config != null : !this$config.equals(other$config)) return false;
        final java.lang.Object this$ativo = this.getAtivo();
        final java.lang.Object other$ativo = other.getAtivo();
        if (this$ativo == null ? other$ativo != null : !this$ativo.equals(other$ativo)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ParceiroBandeira;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $tipoEndpoint = this.getTipoEndpoint();
        result = result * PRIME + ($tipoEndpoint == null ? 43 : $tipoEndpoint.hashCode());
        final java.lang.Object $config = this.getConfig();
        result = result * PRIME + ($config == null ? 43 : $config.hashCode());
        final java.lang.Object $ativo = this.getAtivo();
        result = result * PRIME + ($ativo == null ? 43 : $ativo.hashCode());
        return result;
    }
}
