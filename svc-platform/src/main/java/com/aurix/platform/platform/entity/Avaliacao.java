package com.aurix.platform.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "avaliacoes", schema = "aurix")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bemId;
    private LocalDate data;
    private BigDecimal valor;
    private String metodo;
    private Long avaliadorId;
    private LocalDate validadeAte;

    public Avaliacao() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getBemId() { return bemId; }
    public void setBemId(Long v) { this.bemId = v; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate v) { this.data = v; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal v) { this.valor = v; }
    public String getMetodo() { return metodo; }
    public void setMetodo(String v) { this.metodo = v; }
    public Long getAvaliadorId() { return avaliadorId; }
    public void setAvaliadorId(Long v) { this.avaliadorId = v; }
    public LocalDate getValidadeAte() { return validadeAte; }
    public void setValidadeAte(LocalDate v) { this.validadeAte = v; }
}
