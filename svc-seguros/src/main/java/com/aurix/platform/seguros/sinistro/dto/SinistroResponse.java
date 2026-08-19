package com.aurix.platform.seguros.sinistro.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record SinistroResponse(
    Long id,
    String tenantId,
    Long apoliceId,
    Long clienteId,
    Long produtoId,
    String produtoTipo,
    String descricaoEvento,
    LocalDate dataEvento,
    LocalDate dataAbertura,
    BigDecimal valorSolicitado,
    BigDecimal valorAprovado,
    String status,
    String motivoReprovacao,
    LocalDate dataAnalise,
    LocalDate dataAprovacao,
    LocalDate dataPagamento,
    List<DocumentoResponse> documentos,
    LocalDateTime dataCriacao
) {
    public record DocumentoResponse(
        Long id,
        String tipoDocumento,
        String nomeArquivo,
        String descricao,
        LocalDateTime dataUpload
    ) {}
}
