package com.aurix.platform.savings.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ContaPoupancaResponse {

    private Long id;
    private Long clienteId;
    private Long contaCorrenteId;
    private String numeroConta;
    private BigDecimal saldo;
    private Integer aniversarioDia;
    private LocalDate dataAbertura;
    private LocalDate ultimoAniversario;
    private String status;
    private LocalDateTime dataCriacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getContaCorrenteId() {
        return contaCorrenteId;
    }

    public void setContaCorrenteId(Long contaCorrenteId) {
        this.contaCorrenteId = contaCorrenteId;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Integer getAniversarioDia() {
        return aniversarioDia;
    }

    public void setAniversarioDia(Integer aniversarioDia) {
        this.aniversarioDia = aniversarioDia;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDate getUltimoAniversario() {
        return ultimoAniversario;
    }

    public void setUltimoAniversario(LocalDate ultimoAniversario) {
        this.ultimoAniversario = ultimoAniversario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
