package com.aurix.platform.cambio.dto;

import java.math.BigDecimal;

public class LimiteCambioResponse {

    private Long clienteId;
    private BigDecimal limiteRemessaMensal;
    private BigDecimal limiteRemessaAnual;
    private BigDecimal totalRemessasMes;
    private BigDecimal totalRemessasAno;
    private BigDecimal saldoDisponivelMensal;
    private BigDecimal saldoDisponivelAnual;

    public LimiteCambioResponse() {}

    public LimiteCambioResponse(Long clienteId, BigDecimal limiteRemessaMensal, BigDecimal limiteRemessaAnual, BigDecimal totalRemessasMes, BigDecimal totalRemessasAno, BigDecimal saldoDisponivelMensal, BigDecimal saldoDisponivelAnual) {
        this.clienteId = clienteId;
        this.limiteRemessaMensal = limiteRemessaMensal;
        this.limiteRemessaAnual = limiteRemessaAnual;
        this.totalRemessasMes = totalRemessasMes;
        this.totalRemessasAno = totalRemessasAno;
        this.saldoDisponivelMensal = saldoDisponivelMensal;
        this.saldoDisponivelAnual = saldoDisponivelAnual;
    }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public BigDecimal getLimiteRemessaMensal() { return limiteRemessaMensal; }
    public void setLimiteRemessaMensal(BigDecimal limiteRemessaMensal) { this.limiteRemessaMensal = limiteRemessaMensal; }
    public BigDecimal getLimiteRemessaAnual() { return limiteRemessaAnual; }
    public void setLimiteRemessaAnual(BigDecimal limiteRemessaAnual) { this.limiteRemessaAnual = limiteRemessaAnual; }
    public BigDecimal getTotalRemessasMes() { return totalRemessasMes; }
    public void setTotalRemessasMes(BigDecimal totalRemessasMes) { this.totalRemessasMes = totalRemessasMes; }
    public BigDecimal getTotalRemessasAno() { return totalRemessasAno; }
    public void setTotalRemessasAno(BigDecimal totalRemessasAno) { this.totalRemessasAno = totalRemessasAno; }
    public BigDecimal getSaldoDisponivelMensal() { return saldoDisponivelMensal; }
    public void setSaldoDisponivelMensal(BigDecimal saldoDisponivelMensal) { this.saldoDisponivelMensal = saldoDisponivelMensal; }
    public BigDecimal getSaldoDisponivelAnual() { return saldoDisponivelAnual; }
    public void setSaldoDisponivelAnual(BigDecimal saldoDisponivelAnual) { this.saldoDisponivelAnual = saldoDisponivelAnual; }
}
