package com.aurix.platform.credit.seguro.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record SeguroPrestamistaResponse(
    Long id,
    Long contratoId,
    Long clienteId,
    BigDecimal saldoDevedorInicial,
    BigDecimal taxaMensal,
    BigDecimal valorPremioMensal,
    int carenciaDias,
    List<String> coberturas,
    LocalDate dataInicio,
    LocalDate dataFim,
    LocalDate dataCancelamento,
    String motivoCancelamento,
    String status,
    LocalDateTime dataCriacao
) {}
