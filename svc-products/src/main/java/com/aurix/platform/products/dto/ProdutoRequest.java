package com.aurix.platform.products.dto;

import com.aurix.platform.products.entity.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ProdutoRequest(
    @NotBlank(message = "Código do produto é obrigatório")
    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    String codigo,

    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(max = 200, message = "Nome deve ter no máximo 200 caracteres")
    String nome,

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    String descricao,

    @NotNull(message = "Tipo do produto é obrigatório")
    Produto.TipoProduto tipoProduto,

    @Size(max = 200, message = "Público alvo deve ter no máximo 200 caracteres")
    String publicoAlvo,

    @Size(max = 500, message = "Exigência mínima deve ter no máximo 500 caracteres")
    String exigenciaMinima,

    String requisitos,

    LocalDate vigenciaInicio,

    LocalDate vigenciaFim
) {
}
