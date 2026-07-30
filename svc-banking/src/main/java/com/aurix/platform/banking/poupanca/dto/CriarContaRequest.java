package com.aurix.platform.banking.poupanca.dto;

import jakarta.validation.constraints.NotNull;

public class CriarContaRequest {

    @NotNull
    private Long clienteId;

    @NotNull
    private Long contaCorrenteId;

    private int aniversarioDia;

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getContaCorrenteId() { return contaCorrenteId; }
    public void setContaCorrenteId(Long contaCorrenteId) { this.contaCorrenteId = contaCorrenteId; }
    public int getAniversarioDia() { return aniversarioDia; }
    public void setAniversarioDia(int aniversarioDia) { this.aniversarioDia = aniversarioDia; }
}
