package com.aurix.platform.banking.salario.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitacoes_portabilidade", schema = "aurix")
public class SolicitacaoPortabilidade extends BaseEntity {

    @NotNull
    @Column(name = "conta_salario_id", nullable = false)
    private Long contaSalarioId;

    @NotBlank
    @Column(name = "codigo_banco_destino", nullable = false, length = 3)
    private String codigoBancoDestino;

    @NotBlank
    @Column(name = "agencia_destino", nullable = false, length = 10)
    private String agenciaDestino;

    @NotBlank
    @Column(name = "conta_destino", nullable = false, length = 20)
    private String contaDestino;

    @DecimalMin("0.01")
    @DecimalMax("100.00")
    @Column(name = "valor_percentual", nullable = false, precision = 5, scale = 2)
    private BigDecimal valorPercentual = new BigDecimal("100.00");

    @NotNull
    @Column(name = "data_solicitacao", nullable = false)
    private LocalDateTime dataSolicitacao = LocalDateTime.now();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusPortabilidade status = StatusPortabilidade.PENDENTE;

    public enum StatusPortabilidade {
        PENDENTE, ATIVA, CANCELADA
    }

    public SolicitacaoPortabilidade() {}

    public SolicitacaoPortabilidade(Long contaSalarioId, String codigoBancoDestino,
                                    String agenciaDestino, String contaDestino) {
        this.contaSalarioId = contaSalarioId;
        this.codigoBancoDestino = codigoBancoDestino;
        this.agenciaDestino = agenciaDestino;
        this.contaDestino = contaDestino;
    }

    public Long getContaSalarioId() { return contaSalarioId; }
    public void setContaSalarioId(Long contaSalarioId) { this.contaSalarioId = contaSalarioId; }
    public String getCodigoBancoDestino() { return codigoBancoDestino; }
    public void setCodigoBancoDestino(String codigoBancoDestino) { this.codigoBancoDestino = codigoBancoDestino; }
    public String getAgenciaDestino() { return agenciaDestino; }
    public void setAgenciaDestino(String agenciaDestino) { this.agenciaDestino = agenciaDestino; }
    public String getContaDestino() { return contaDestino; }
    public void setContaDestino(String contaDestino) { this.contaDestino = contaDestino; }
    public BigDecimal getValorPercentual() { return valorPercentual; }
    public void setValorPercentual(BigDecimal valorPercentual) { this.valorPercentual = valorPercentual; }
    public LocalDateTime getDataSolicitacao() { return dataSolicitacao; }
    public void setDataSolicitacao(LocalDateTime dataSolicitacao) { this.dataSolicitacao = dataSolicitacao; }
    public StatusPortabilidade getStatus() { return status; }
    public void setStatus(StatusPortabilidade status) { this.status = status; }
}
