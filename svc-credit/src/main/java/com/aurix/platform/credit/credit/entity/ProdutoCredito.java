package com.aurix.platform.credit.credit.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos_credito", schema = "aurix")
public class ProdutoCredito extends BaseEntity {
    @Column(nullable = false, unique = true, length = 50)
    private String codigo;
    @Column(nullable = false, length = 200)
    private String nome;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_credito", nullable = false, length = 30)
    private TipoCredito tipoCredito;
    @Column(name = "taxa_juros_min", precision = 5, scale = 4)
    private BigDecimal taxaJurosMin;
    @Column(name = "taxa_juros_max", precision = 5, scale = 4)
    private BigDecimal taxaJurosMax;
    @Column(name = "prazo_min_meses")
    private Integer prazoMinMeses;
    @Column(name = "prazo_max_meses")
    private Integer prazoMaxMeses;
    @Column(name = "valor_min", precision = 19, scale = 4)
    private BigDecimal valorMin;
    @Column(name = "valor_max", precision = 19, scale = 4)
    private BigDecimal valorMax;
    @Column(name = "exige_garantia", nullable = false)
    private Boolean exigeGarantia = false;
    @Column(nullable = false)
    private Boolean ativo = true;


    public enum TipoCredito {
        PESSOAL, CONSIGNADO, CDC, VEICULOS, IMOBILIARIO,         CAPITAL_GIRO,
        LIMITE_ROTATIVO,
        OUTROS;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigo() {
        return this.codigo;
    }

    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    @java.lang.SuppressWarnings("all")
    public TipoCredito getTipoCredito() {
        return this.tipoCredito;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaJurosMin() {
        return this.taxaJurosMin;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaJurosMax() {
        return this.taxaJurosMax;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getPrazoMinMeses() {
        return this.prazoMinMeses;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getPrazoMaxMeses() {
        return this.prazoMaxMeses;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorMin() {
        return this.valorMin;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorMax() {
        return this.valorMax;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getExigeGarantia() {
        return this.exigeGarantia;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getAtivo() {
        return this.ativo;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigo(final String codigo) {
        this.codigo = codigo;
    }

    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoCredito(final TipoCredito tipoCredito) {
        this.tipoCredito = tipoCredito;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaJurosMin(final BigDecimal taxaJurosMin) {
        this.taxaJurosMin = taxaJurosMin;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaJurosMax(final BigDecimal taxaJurosMax) {
        this.taxaJurosMax = taxaJurosMax;
    }

    @java.lang.SuppressWarnings("all")
    public void setPrazoMinMeses(final Integer prazoMinMeses) {
        this.prazoMinMeses = prazoMinMeses;
    }

    @java.lang.SuppressWarnings("all")
    public void setPrazoMaxMeses(final Integer prazoMaxMeses) {
        this.prazoMaxMeses = prazoMaxMeses;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorMin(final BigDecimal valorMin) {
        this.valorMin = valorMin;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorMax(final BigDecimal valorMax) {
        this.valorMax = valorMax;
    }

    @java.lang.SuppressWarnings("all")
    public void setExigeGarantia(final Boolean exigeGarantia) {
        this.exigeGarantia = exigeGarantia;
    }

    @java.lang.SuppressWarnings("all")
    public void setAtivo(final Boolean ativo) {
        this.ativo = ativo;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ProdutoCredito(codigo=" + this.getCodigo() + ", nome=" + this.getNome() + ", tipoCredito=" + this.getTipoCredito() + ", taxaJurosMin=" + this.getTaxaJurosMin() + ", taxaJurosMax=" + this.getTaxaJurosMax() + ", prazoMinMeses=" + this.getPrazoMinMeses() + ", prazoMaxMeses=" + this.getPrazoMaxMeses() + ", valorMin=" + this.getValorMin() + ", valorMax=" + this.getValorMax() + ", exigeGarantia=" + this.getExigeGarantia() + ", ativo=" + this.getAtivo() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ProdutoCredito() {
    }

    @java.lang.SuppressWarnings("all")
    public ProdutoCredito(final String codigo, final String nome, final TipoCredito tipoCredito, final BigDecimal taxaJurosMin, final BigDecimal taxaJurosMax, final Integer prazoMinMeses, final Integer prazoMaxMeses, final BigDecimal valorMin, final BigDecimal valorMax, final Boolean exigeGarantia, final Boolean ativo) {
        this.codigo = codigo;
        this.nome = nome;
        this.tipoCredito = tipoCredito;
        this.taxaJurosMin = taxaJurosMin;
        this.taxaJurosMax = taxaJurosMax;
        this.prazoMinMeses = prazoMinMeses;
        this.prazoMaxMeses = prazoMaxMeses;
        this.valorMin = valorMin;
        this.valorMax = valorMax;
        this.exigeGarantia = exigeGarantia;
        this.ativo = ativo;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ProdutoCredito)) return false;
        final ProdutoCredito other = (ProdutoCredito) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$prazoMinMeses = this.getPrazoMinMeses();
        final java.lang.Object other$prazoMinMeses = other.getPrazoMinMeses();
        if (this$prazoMinMeses == null ? other$prazoMinMeses != null : !this$prazoMinMeses.equals(other$prazoMinMeses)) return false;
        final java.lang.Object this$prazoMaxMeses = this.getPrazoMaxMeses();
        final java.lang.Object other$prazoMaxMeses = other.getPrazoMaxMeses();
        if (this$prazoMaxMeses == null ? other$prazoMaxMeses != null : !this$prazoMaxMeses.equals(other$prazoMaxMeses)) return false;
        final java.lang.Object this$exigeGarantia = this.getExigeGarantia();
        final java.lang.Object other$exigeGarantia = other.getExigeGarantia();
        if (this$exigeGarantia == null ? other$exigeGarantia != null : !this$exigeGarantia.equals(other$exigeGarantia)) return false;
        final java.lang.Object this$ativo = this.getAtivo();
        final java.lang.Object other$ativo = other.getAtivo();
        if (this$ativo == null ? other$ativo != null : !this$ativo.equals(other$ativo)) return false;
        final java.lang.Object this$codigo = this.getCodigo();
        final java.lang.Object other$codigo = other.getCodigo();
        if (this$codigo == null ? other$codigo != null : !this$codigo.equals(other$codigo)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$tipoCredito = this.getTipoCredito();
        final java.lang.Object other$tipoCredito = other.getTipoCredito();
        if (this$tipoCredito == null ? other$tipoCredito != null : !this$tipoCredito.equals(other$tipoCredito)) return false;
        final java.lang.Object this$taxaJurosMin = this.getTaxaJurosMin();
        final java.lang.Object other$taxaJurosMin = other.getTaxaJurosMin();
        if (this$taxaJurosMin == null ? other$taxaJurosMin != null : !this$taxaJurosMin.equals(other$taxaJurosMin)) return false;
        final java.lang.Object this$taxaJurosMax = this.getTaxaJurosMax();
        final java.lang.Object other$taxaJurosMax = other.getTaxaJurosMax();
        if (this$taxaJurosMax == null ? other$taxaJurosMax != null : !this$taxaJurosMax.equals(other$taxaJurosMax)) return false;
        final java.lang.Object this$valorMin = this.getValorMin();
        final java.lang.Object other$valorMin = other.getValorMin();
        if (this$valorMin == null ? other$valorMin != null : !this$valorMin.equals(other$valorMin)) return false;
        final java.lang.Object this$valorMax = this.getValorMax();
        final java.lang.Object other$valorMax = other.getValorMax();
        if (this$valorMax == null ? other$valorMax != null : !this$valorMax.equals(other$valorMax)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ProdutoCredito;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $prazoMinMeses = this.getPrazoMinMeses();
        result = result * PRIME + ($prazoMinMeses == null ? 43 : $prazoMinMeses.hashCode());
        final java.lang.Object $prazoMaxMeses = this.getPrazoMaxMeses();
        result = result * PRIME + ($prazoMaxMeses == null ? 43 : $prazoMaxMeses.hashCode());
        final java.lang.Object $exigeGarantia = this.getExigeGarantia();
        result = result * PRIME + ($exigeGarantia == null ? 43 : $exigeGarantia.hashCode());
        final java.lang.Object $ativo = this.getAtivo();
        result = result * PRIME + ($ativo == null ? 43 : $ativo.hashCode());
        final java.lang.Object $codigo = this.getCodigo();
        result = result * PRIME + ($codigo == null ? 43 : $codigo.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $tipoCredito = this.getTipoCredito();
        result = result * PRIME + ($tipoCredito == null ? 43 : $tipoCredito.hashCode());
        final java.lang.Object $taxaJurosMin = this.getTaxaJurosMin();
        result = result * PRIME + ($taxaJurosMin == null ? 43 : $taxaJurosMin.hashCode());
        final java.lang.Object $taxaJurosMax = this.getTaxaJurosMax();
        result = result * PRIME + ($taxaJurosMax == null ? 43 : $taxaJurosMax.hashCode());
        final java.lang.Object $valorMin = this.getValorMin();
        result = result * PRIME + ($valorMin == null ? 43 : $valorMin.hashCode());
        final java.lang.Object $valorMax = this.getValorMax();
        result = result * PRIME + ($valorMax == null ? 43 : $valorMax.hashCode());
        return result;
    }
}
