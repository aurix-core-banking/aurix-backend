package com.aurix.platform.credit.credit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aurix.credit.regras")
public class RegrasCreditoProperties {
    private int scoreMinAprovar = 600;
    private int scoreMaxRejeitar = 400;
    private int scoreMinRefer = 401;
    private int scoreMaxRefer = 599;

    @java.lang.SuppressWarnings("all")
    public RegrasCreditoProperties() {
    }

    @java.lang.SuppressWarnings("all")
    public int getScoreMinAprovar() {
        return this.scoreMinAprovar;
    }

    @java.lang.SuppressWarnings("all")
    public int getScoreMaxRejeitar() {
        return this.scoreMaxRejeitar;
    }

    @java.lang.SuppressWarnings("all")
    public int getScoreMinRefer() {
        return this.scoreMinRefer;
    }

    @java.lang.SuppressWarnings("all")
    public int getScoreMaxRefer() {
        return this.scoreMaxRefer;
    }

    @java.lang.SuppressWarnings("all")
    public void setScoreMinAprovar(final int scoreMinAprovar) {
        this.scoreMinAprovar = scoreMinAprovar;
    }

    @java.lang.SuppressWarnings("all")
    public void setScoreMaxRejeitar(final int scoreMaxRejeitar) {
        this.scoreMaxRejeitar = scoreMaxRejeitar;
    }

    @java.lang.SuppressWarnings("all")
    public void setScoreMinRefer(final int scoreMinRefer) {
        this.scoreMinRefer = scoreMinRefer;
    }

    @java.lang.SuppressWarnings("all")
    public void setScoreMaxRefer(final int scoreMaxRefer) {
        this.scoreMaxRefer = scoreMaxRefer;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof RegrasCreditoProperties)) return false;
        final RegrasCreditoProperties other = (RegrasCreditoProperties) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.getScoreMinAprovar() != other.getScoreMinAprovar()) return false;
        if (this.getScoreMaxRejeitar() != other.getScoreMaxRejeitar()) return false;
        if (this.getScoreMinRefer() != other.getScoreMinRefer()) return false;
        if (this.getScoreMaxRefer() != other.getScoreMaxRefer()) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof RegrasCreditoProperties;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getScoreMinAprovar();
        result = result * PRIME + this.getScoreMaxRejeitar();
        result = result * PRIME + this.getScoreMinRefer();
        result = result * PRIME + this.getScoreMaxRefer();
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "RegrasCreditoProperties(scoreMinAprovar=" + this.getScoreMinAprovar() + ", scoreMaxRejeitar=" + this.getScoreMaxRejeitar() + ", scoreMinRefer=" + this.getScoreMinRefer() + ", scoreMaxRefer=" + this.getScoreMaxRefer() + ")";
    }
}
