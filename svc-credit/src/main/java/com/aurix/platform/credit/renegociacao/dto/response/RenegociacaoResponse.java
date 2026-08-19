package com.aurix.platform.credit.renegociacao.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record RenegociacaoResponse(
    Long id,
    Long contratoOriginalId,
    Long clienteId,
    BigDecimal saldoDevedorAnterior,
    BigDecimal saldoDevedorRenegociado,
    BigDecimal taxaJurosAnterior,
    BigDecimal taxaJurosRenegociada,
    int prazoAnterior,
    int prazoRenegociado,
    BigDecimal valorParcelaAnterior,
    BigDecimal valorParcelaRenegociada,
    String sistemaAmortizacao,
    String status,
    LocalDateTime dataSolicitacao,
    LocalDateTime dataAprovacao,
    LocalDateTime dataContratacao,
    String observacoes,
    List<RenegociacaoParcelaResponse> parcelas,
    LocalDateTime dataCriacao
) {}
