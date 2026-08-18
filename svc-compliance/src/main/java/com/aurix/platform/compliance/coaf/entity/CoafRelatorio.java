package com.aurix.platform.compliance.coaf.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "coaf_relatorios", schema = "aurix")
public class CoafRelatorio extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String codigoRelatorio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRelatorioCoaf tipoRelatorio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusRelatorioCoaf status = StatusRelatorioCoaf.GERADO;

    @Column(nullable = false)
    private LocalDateTime dataInicioPeriodo;

    @Column(nullable = false)
    private LocalDateTime dataFimPeriodo;

    @Column(nullable = false)
    private LocalDateTime dataGeracao;

    @Column
    private LocalDateTime dataEnvio;

    @Column(nullable = false)
    private Integer totalNotificacoes;

    @Column(nullable = false)
    private Integer totalTransacoesAnalisadas;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSONB")
    private String resumoEstatistico;

    @Column(columnDefinition = "TEXT")
    private String conteudoRelatorio;

    @Column(columnDefinition = "TEXT")
    private String xmlRelatorio;

    @Column(length = 100)
    private String hashRelatorio;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    public enum TipoRelatorioCoaf {
        MENSAL, TRIMESTRAL, ANUAL, ESPECIAL, AD_HOC
    }

    public enum StatusRelatorioCoaf {
        GERADO, EM_REVISAO, ENVIADO, CONFIRMADO, RETORNADO
    }

    @SuppressWarnings("all")
    public CoafRelatorio() {
    }

    public String getCodigoRelatorio() {
        return this.codigoRelatorio;
    }

    public TipoRelatorioCoaf getTipoRelatorio() {
        return this.tipoRelatorio;
    }

    public StatusRelatorioCoaf getStatus() {
        return this.status;
    }

    public LocalDateTime getDataInicioPeriodo() {
        return this.dataInicioPeriodo;
    }

    public LocalDateTime getDataFimPeriodo() {
        return this.dataFimPeriodo;
    }

    public LocalDateTime getDataGeracao() {
        return this.dataGeracao;
    }

    public LocalDateTime getDataEnvio() {
        return this.dataEnvio;
    }

    public Integer getTotalNotificacoes() {
        return this.totalNotificacoes;
    }

    public Integer getTotalTransacoesAnalisadas() {
        return this.totalTransacoesAnalisadas;
    }

    public String getResumoEstatistico() {
        return this.resumoEstatistico;
    }

    public String getConteudoRelatorio() {
        return this.conteudoRelatorio;
    }

    public String getXmlRelatorio() {
        return this.xmlRelatorio;
    }

    public String getHashRelatorio() {
        return this.hashRelatorio;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public void setCodigoRelatorio(final String codigoRelatorio) {
        this.codigoRelatorio = codigoRelatorio;
    }

    public void setTipoRelatorio(final TipoRelatorioCoaf tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

    public void setStatus(final StatusRelatorioCoaf status) {
        this.status = status;
    }

    public void setDataInicioPeriodo(final LocalDateTime dataInicioPeriodo) {
        this.dataInicioPeriodo = dataInicioPeriodo;
    }

    public void setDataFimPeriodo(final LocalDateTime dataFimPeriodo) {
        this.dataFimPeriodo = dataFimPeriodo;
    }

    public void setDataGeracao(final LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public void setDataEnvio(final LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public void setTotalNotificacoes(final Integer totalNotificacoes) {
        this.totalNotificacoes = totalNotificacoes;
    }

    public void setTotalTransacoesAnalisadas(final Integer totalTransacoesAnalisadas) {
        this.totalTransacoesAnalisadas = totalTransacoesAnalisadas;
    }

    public void setResumoEstatistico(final String resumoEstatistico) {
        this.resumoEstatistico = resumoEstatistico;
    }

    public void setConteudoRelatorio(final String conteudoRelatorio) {
        this.conteudoRelatorio = conteudoRelatorio;
    }

    public void setXmlRelatorio(final String xmlRelatorio) {
        this.xmlRelatorio = xmlRelatorio;
    }

    public void setHashRelatorio(final String hashRelatorio) {
        this.hashRelatorio = hashRelatorio;
    }

    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }
}
