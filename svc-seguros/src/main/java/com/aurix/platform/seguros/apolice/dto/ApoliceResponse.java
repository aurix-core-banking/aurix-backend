package com.aurix.platform.seguros.apolice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ApoliceResponse(
    Long id,
    String tenantId,
    Long clienteId,
    Long produtoId,
    String produtoTipo,
    String cobertura,
    BigDecimal valorSegurado,
    BigDecimal premio,
    BigDecimal premioMensal,
    LocalDate dataInicio,
    LocalDate dataFim,
    LocalDate dataCancelamento,
    BigDecimal valorRestituido,
    String status,
    Boolean renovacaoAutomatica,
    Integer idadeSegurado,
    String uf,
    String sexo,
    String profissao,
    String numeroApolice,
    LocalDateTime dataCriacao
) {}
