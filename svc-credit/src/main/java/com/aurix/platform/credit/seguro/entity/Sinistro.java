package com.aurix.platform.credit.seguro.entity;

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
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sinistros")
public class Sinistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long seguroId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "tipo_sinistro", length = 30)
    private TipoSinistro tipoSinistro;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @NotNull
    @Column(nullable = false, name = "data_ocorrencia")
    private LocalDate dataOcorrencia;

    @Column(nullable = false, name = "data_abertura")
    private LocalDateTime dataAbertura;

    @Column(name = "data_analise")
    private LocalDateTime dataAnalise;

    @Column(name = "data_resolucao")
    private LocalDateTime dataResolucao;

    @Column(name = "valor_indenizacao", precision = 18, scale = 2)
    private BigDecimal valorIndenizacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusSinistro status;

    @Column(columnDefinition = "JSONB")
    private String documentos;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    public Sinistro() {}

    public Sinistro(Long seguroId, TipoSinistro tipoSinistro, String descricao,
                    LocalDate dataOcorrencia, StatusSinistro status) {
        this.seguroId = seguroId;
        this.tipoSinistro = tipoSinistro;
        this.descricao = descricao;
        this.dataOcorrencia = dataOcorrencia;
        this.dataAbertura = LocalDateTime.now();
        this.status = status;
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
    public Long getSeguroId() { return seguroId; }
    public void setSeguroId(Long seguroId) { this.seguroId = seguroId; }
    public TipoSinistro getTipoSinistro() { return tipoSinistro; }
    public void setTipoSinistro(TipoSinistro tipoSinistro) { this.tipoSinistro = tipoSinistro; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getDataOcorrencia() { return dataOcorrencia; }
    public void setDataOcorrencia(LocalDate dataOcorrencia) { this.dataOcorrencia = dataOcorrencia; }
    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }
    public LocalDateTime getDataAnalise() { return dataAnalise; }
    public void setDataAnalise(LocalDateTime dataAnalise) { this.dataAnalise = dataAnalise; }
    public LocalDateTime getDataResolucao() { return dataResolucao; }
    public void setDataResolucao(LocalDateTime dataResolucao) { this.dataResolucao = dataResolucao; }
    public BigDecimal getValorIndenizacao() { return valorIndenizacao; }
    public void setValorIndenizacao(BigDecimal valorIndenizacao) { this.valorIndenizacao = valorIndenizacao; }
    public StatusSinistro getStatus() { return status; }
    public void setStatus(StatusSinistro status) { this.status = status; }
    public String getDocumentos() { return documentos; }
    public void setDocumentos(String documentos) { this.documentos = documentos; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
}
