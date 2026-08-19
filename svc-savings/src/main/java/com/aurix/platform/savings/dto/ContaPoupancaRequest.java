package com.aurix.platform.savings.dto;

import jakarta.validation.constraints.NotNull;

public class ContaPoupancaRequest {

    @NotNull(message = "ID do cliente e obrigatorio")
    private Long clienteId;

    private Long contaCorrenteId;
    private Integer aniversarioDia;

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

    public Integer getAniversarioDia() {
        return aniversarioDia;
    }

    public void setAniversarioDia(Integer aniversarioDia) {
        this.aniversarioDia = aniversarioDia;
    }
}
