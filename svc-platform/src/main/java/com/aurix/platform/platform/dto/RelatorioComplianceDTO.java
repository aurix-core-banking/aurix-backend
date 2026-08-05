package com.aurix.platform.platform.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class RelatorioComplianceDTO {
    private LocalDateTime periodoInicio;
    private LocalDateTime periodoFim;
    private long totalRegistros;
    private Map<String, Long> porTipoAcao;
    private Map<String, Long> porCategoria;
    private Map<String, Long> porResultado;
    private long criticos;
    private long falhas;

    @java.lang.SuppressWarnings("all")
    public RelatorioComplianceDTO() {
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getPeriodoInicio() {
        return this.periodoInicio;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getPeriodoFim() {
        return this.periodoFim;
    }

    @java.lang.SuppressWarnings("all")
    public long getTotalRegistros() {
        return this.totalRegistros;
    }

    @java.lang.SuppressWarnings("all")
    public Map<String, Long> getPorTipoAcao() {
        return this.porTipoAcao;
    }

    @java.lang.SuppressWarnings("all")
    public Map<String, Long> getPorCategoria() {
        return this.porCategoria;
    }

    @java.lang.SuppressWarnings("all")
    public Map<String, Long> getPorResultado() {
        return this.porResultado;
    }

    @java.lang.SuppressWarnings("all")
    public long getCriticos() {
        return this.criticos;
    }

    @java.lang.SuppressWarnings("all")
    public long getFalhas() {
        return this.falhas;
    }

    @java.lang.SuppressWarnings("all")
    public void setPeriodoInicio(final LocalDateTime periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    @java.lang.SuppressWarnings("all")
    public void setPeriodoFim(final LocalDateTime periodoFim) {
        this.periodoFim = periodoFim;
    }

    @java.lang.SuppressWarnings("all")
    public void setTotalRegistros(final long totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    @java.lang.SuppressWarnings("all")
    public void setPorTipoAcao(final Map<String, Long> porTipoAcao) {
        this.porTipoAcao = porTipoAcao;
    }

    @java.lang.SuppressWarnings("all")
    public void setPorCategoria(final Map<String, Long> porCategoria) {
        this.porCategoria = porCategoria;
    }

    @java.lang.SuppressWarnings("all")
    public void setPorResultado(final Map<String, Long> porResultado) {
        this.porResultado = porResultado;
    }

    @java.lang.SuppressWarnings("all")
    public void setCriticos(final long criticos) {
        this.criticos = criticos;
    }

    @java.lang.SuppressWarnings("all")
    public void setFalhas(final long falhas) {
        this.falhas = falhas;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof RelatorioComplianceDTO)) return false;
        final RelatorioComplianceDTO other = (RelatorioComplianceDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.getTotalRegistros() != other.getTotalRegistros()) return false;
        if (this.getCriticos() != other.getCriticos()) return false;
        if (this.getFalhas() != other.getFalhas()) return false;
        final java.lang.Object this$periodoInicio = this.getPeriodoInicio();
        final java.lang.Object other$periodoInicio = other.getPeriodoInicio();
        if (this$periodoInicio == null ? other$periodoInicio != null : !this$periodoInicio.equals(other$periodoInicio)) return false;
        final java.lang.Object this$periodoFim = this.getPeriodoFim();
        final java.lang.Object other$periodoFim = other.getPeriodoFim();
        if (this$periodoFim == null ? other$periodoFim != null : !this$periodoFim.equals(other$periodoFim)) return false;
        final java.lang.Object this$porTipoAcao = this.getPorTipoAcao();
        final java.lang.Object other$porTipoAcao = other.getPorTipoAcao();
        if (this$porTipoAcao == null ? other$porTipoAcao != null : !this$porTipoAcao.equals(other$porTipoAcao)) return false;
        final java.lang.Object this$porCategoria = this.getPorCategoria();
        final java.lang.Object other$porCategoria = other.getPorCategoria();
        if (this$porCategoria == null ? other$porCategoria != null : !this$porCategoria.equals(other$porCategoria)) return false;
        final java.lang.Object this$porResultado = this.getPorResultado();
        final java.lang.Object other$porResultado = other.getPorResultado();
        if (this$porResultado == null ? other$porResultado != null : !this$porResultado.equals(other$porResultado)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof RelatorioComplianceDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $totalRegistros = this.getTotalRegistros();
        result = result * PRIME + (int) ($totalRegistros >>> 32 ^ $totalRegistros);
        final long $criticos = this.getCriticos();
        result = result * PRIME + (int) ($criticos >>> 32 ^ $criticos);
        final long $falhas = this.getFalhas();
        result = result * PRIME + (int) ($falhas >>> 32 ^ $falhas);
        final java.lang.Object $periodoInicio = this.getPeriodoInicio();
        result = result * PRIME + ($periodoInicio == null ? 43 : $periodoInicio.hashCode());
        final java.lang.Object $periodoFim = this.getPeriodoFim();
        result = result * PRIME + ($periodoFim == null ? 43 : $periodoFim.hashCode());
        final java.lang.Object $porTipoAcao = this.getPorTipoAcao();
        result = result * PRIME + ($porTipoAcao == null ? 43 : $porTipoAcao.hashCode());
        final java.lang.Object $porCategoria = this.getPorCategoria();
        result = result * PRIME + ($porCategoria == null ? 43 : $porCategoria.hashCode());
        final java.lang.Object $porResultado = this.getPorResultado();
        result = result * PRIME + ($porResultado == null ? 43 : $porResultado.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "RelatorioComplianceDTO(periodoInicio=" + this.getPeriodoInicio() + ", periodoFim=" + this.getPeriodoFim() + ", totalRegistros=" + this.getTotalRegistros() + ", porTipoAcao=" + this.getPorTipoAcao() + ", porCategoria=" + this.getPorCategoria() + ", porResultado=" + this.getPorResultado() + ", criticos=" + this.getCriticos() + ", falhas=" + this.getFalhas() + ")";
    }
}
