package com.aurix.platform.investimentos.indice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "indices_mercado")
public class IndiceMercado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoIndice tipoIndice;

    @Column(nullable = false, precision = 12, scale = 6)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate dataReferencia;

    @Column(length = 10)
    private String codigoSerieBacen;

    @Column(length = 100)
    private String fonteDados;

    private LocalDateTime dataAtualizacaoBacen;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    public IndiceMercado() {}

    public IndiceMercado(TipoIndice tipoIndice, BigDecimal valor, LocalDate dataReferencia,
                         String codigoSerieBacen, String fonteDados) {
        this.tipoIndice = tipoIndice;
        this.valor = valor;
        this.dataReferencia = dataReferencia;
        this.codigoSerieBacen = codigoSerieBacen;
        this.fonteDados = fonteDados;
        this.dataAtualizacaoBacen = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TipoIndice getTipoIndice() { return tipoIndice; }
    public void setTipoIndice(TipoIndice tipoIndice) { this.tipoIndice = tipoIndice; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public LocalDate getDataReferencia() { return dataReferencia; }
    public void setDataReferencia(LocalDate dataReferencia) { this.dataReferencia = dataReferencia; }
    public String getCodigoSerieBacen() { return codigoSerieBacen; }
    public void setCodigoSerieBacen(String codigoSerieBacen) { this.codigoSerieBacen = codigoSerieBacen; }
    public String getFonteDados() { return fonteDados; }
    public void setFonteDados(String fonteDados) { this.fonteDados = fonteDados; }
    public LocalDateTime getDataAtualizacaoBacen() { return dataAtualizacaoBacen; }
    public void setDataAtualizacaoBacen(LocalDateTime dataAtualizacaoBacen) { this.dataAtualizacaoBacen = dataAtualizacaoBacen; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
}