package com.aurix.platform.banking.salario.dto;

import com.aurix.platform.banking.salario.entity.ItemFolhaPagamento.StatusItem;
import java.math.BigDecimal;

public class ItemFolhaResponse {
    private Long id;
    private Long folhaId;
    private Long contaSalarioId;
    private String cpfFuncionario;
    private BigDecimal valorLiquido;
    private String descontos;
    private StatusItem status;

    public ItemFolhaResponse() {}
    public Long getId() { return id; }
    public void setId(Long v) { this.id = v; }
    public Long getFolhaId() { return folhaId; }
    public void setFolhaId(Long v) { this.folhaId = v; }
    public Long getContaSalarioId() { return contaSalarioId; }
    public void setContaSalarioId(Long v) { this.contaSalarioId = v; }
    public String getCpfFuncionario() { return cpfFuncionario; }
    public void setCpfFuncionario(String v) { this.cpfFuncionario = v; }
    public BigDecimal getValorLiquido() { return valorLiquido; }
    public void setValorLiquido(BigDecimal v) { this.valorLiquido = v; }
    public String getDescontos() { return descontos; }
    public void setDescontos(String v) { this.descontos = v; }
    public StatusItem getStatus() { return status; }
    public void setStatus(StatusItem v) { this.status = v; }
}
