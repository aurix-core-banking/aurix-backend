package com.aurix.platform.seguros.sinistro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DocumentoRequest(
    @NotNull Long sinistroId,
    @NotBlank String tipoDocumento,
    @NotBlank String nomeArquivo,
    @NotBlank String caminhoArquivo,
    String descricao
) {}
