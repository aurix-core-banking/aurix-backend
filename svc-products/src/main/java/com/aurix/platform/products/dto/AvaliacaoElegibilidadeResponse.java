package com.aurix.platform.products.dto;

import java.util.List;

public record AvaliacaoElegibilidadeResponse(
    Long produtoId,
    String codigoProduto,
    String nomeProduto,
    boolean apto,
    List<ResultadoRegra> regras
) {

    public record ResultadoRegra(
        Long regraId,
        String tipoRegra,
        String descricao,
        boolean atendida,
        String valorEsperado,
        String valorInformado
    ) {
    }
}
