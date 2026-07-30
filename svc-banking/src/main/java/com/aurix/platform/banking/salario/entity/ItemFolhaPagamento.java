package com.aurix.platform.banking.salario.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_folha_pagamento", schema = "aurix")
public class ItemFolhaPagamento extends BaseEntity {

    @NotNull
    @Column(name = "folha_id", nullable = false)
    private Long folhaId;

    @Column(name = "conta_salario_id")
    private Long contaSalarioId;

    @NotBlank
    @Column(name = "cpf_funcionario", nullable = false, length = 11)
    private String cpfFuncionario;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "valor_liquido", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorLiquido;

    @Column(name = "descontos", columnDefinition = "jsonb")
    private String descontos;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusItem status = StatusItem.PENDENTE;

    public enum StatusItem {
        PENDENTE, CREDITADO, PORTADO, ERRO
    }

    public ItemFolhaPagamento() {}

    public ItemFolhaPagamento(Long folhaId, Long contaSalarioId, String cpfFuncionario,
                              BigDecimal valorLiquido) {
        this.folhaId = folhaId;
        this.contaSalarioId = contaSalarioId;
        this.cpfFuncionario = cpfFuncionario;
        this.valorLiquido = valorLiquido;
    }

    public Long getFolhaId() { return folhaId; }
    public void setFolhaId(Long folhaId) { this.folhaId = folhaId; }
    public Long getContaSalarioId() { return contaSalarioId; }
    public void setContaSalarioId(Long contaSalarioId) { this.contaSalarioId = contaSalarioId; }
    public String getCpfFuncionario() { return cpfFuncionario; }
    public void setCpfFuncionario(String cpfFuncionario) { this.cpfFuncionario = cpfFuncionario; }
    public BigDecimal getValorLiquido() { return valorLiquido; }
    public void setValorLiquido(BigDecimal valorLiquido) { this.valorLiquido = valorLiquido; }
    public String getDescontos() { return descontos; }
    public void setDescontos(String descontos) { this.descontos = descontos; }
    public StatusItem getStatus() { return status; }
    public void setStatus(StatusItem status) { this.status = status; }
}
