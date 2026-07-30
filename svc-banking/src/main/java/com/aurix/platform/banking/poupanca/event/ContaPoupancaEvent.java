package com.aurix.platform.banking.poupanca.event;

import java.time.LocalDate;

public record ContaPoupancaEvent(
    Long id,
    Long clienteId,
    String numeroConta,
    LocalDate dataAbertura,
    String tenantId
) {}
