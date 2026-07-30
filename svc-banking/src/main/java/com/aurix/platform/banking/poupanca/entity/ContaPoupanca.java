package com.aurix.platform.banking.poupanca.entity;

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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contas_poupanca")
public class ContaPoupanca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @NotNull
    @Column(name = "conta_corrente_id", nullable = false)
    private Long contaCorrenteId;

    @NotBlank
    @Column(name = "numero_conta", unique = true, nullable = false, length = 30)
    private String numeroConta;

    @NotNull
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(name = "aniversario_dia", nullable = false)
    private int aniversarioDia;

    @NotNull
    @Column(name = "data_abertura", nullable = false)
    private LocalDate dataAbertura;

    @Column(name = "ultimo_aniversario")
    private LocalDate ultimoAniversario;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusConta status = StatusConta.ATIVA;

    @NotBlank
    @Column(name = "tenant_id", nullable = false, length = 50)
    private String tenantId;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    public enum StatusConta {
        ATIVA, BLOQUEADA, ENCERRADA
    }

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = dataCriacao;
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getContaCorrenteId() { return contaCorrenteId; }
    public void setContaCorrenteId(Long contaCorrenteId) { this.contaCorrenteId = contaCorrenteId; }
    public String getNumeroConta() { return numeroConta; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }
    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
    public int getAniversarioDia() { return aniversarioDia; }
    public void setAniversarioDia(int aniversarioDia) { this.aniversarioDia = aniversarioDia; }
    public LocalDate getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDate dataAbertura) { this.dataAbertura = dataAbertura; }
    public LocalDate getUltimoAniversario() { return ultimoAniversario; }
    public void setUltimoAniversario(LocalDate ultimoAniversario) { this.ultimoAniversario = ultimoAniversario; }
    public StatusConta getStatus() { return status; }
    public void setStatus(StatusConta status) { this.status = status; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
}
