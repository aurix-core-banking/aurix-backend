package com.aurix.platform.credit.financiamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bens_financiados")
public class BemFinanciado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long contratoId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoBem tipo;

    @NotNull
    @Column(nullable = false, length = 255)
    private String descricao;

    @NotNull
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valorAvaliacao;

    @Column(length = 50)
    private String chassi;

    @Column(length = 20)
    private String placa;

    @Column(length = 50)
    private String matriculaRGI;

    @Column(length = 100)
    private String registroGarantia;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    public BemFinanciado() {}

    public BemFinanciado(Long contratoId, TipoBem tipo, String descricao, BigDecimal valorAvaliacao, String chassi, String placa, String matriculaRGI, String registroGarantia) {
        this.contratoId = contratoId;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valorAvaliacao = valorAvaliacao;
        this.chassi = chassi;
        this.placa = placa;
        this.matriculaRGI = matriculaRGI;
        this.registroGarantia = registroGarantia;
    }

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getContratoId() { return contratoId; }
    public void setContratoId(Long contratoId) { this.contratoId = contratoId; }
    public TipoBem getTipo() { return tipo; }
    public void setTipo(TipoBem tipo) { this.tipo = tipo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getValorAvaliacao() { return valorAvaliacao; }
    public void setValorAvaliacao(BigDecimal valorAvaliacao) { this.valorAvaliacao = valorAvaliacao; }
    public String getChassi() { return chassi; }
    public void setChassi(String chassi) { this.chassi = chassi; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getMatriculaRGI() { return matriculaRGI; }
    public void setMatriculaRGI(String matriculaRGI) { this.matriculaRGI = matriculaRGI; }
    public String getRegistroGarantia() { return registroGarantia; }
    public void setRegistroGarantia(String registroGarantia) { this.registroGarantia = registroGarantia; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
}
