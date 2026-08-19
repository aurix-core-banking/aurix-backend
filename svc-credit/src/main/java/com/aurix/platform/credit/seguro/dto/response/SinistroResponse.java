package com.aurix.platform.credit.seguro.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record SinistroResponse(
    Long id,
    Long seguroId,
    String tipoSinistro,
    String descricao,
    LocalDate dataOcorrencia,
    LocalDateTime dataAbertura,
    LocalDateTime dataAnalise,
    LocalDateTime dataResolucao,
    BigDecimal valorIndenizacao,
    String status,
    String documentos,
    LocalDateTime dataCriacao
) {}
