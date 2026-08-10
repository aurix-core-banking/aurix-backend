package com.aurix.platform.products.dto;

import com.aurix.platform.products.entity.VersaoProduto;

import java.time.LocalDateTime;

public record VersaoProdutoResponse(
    Long id,
    Long produtoId,
    Integer numeroVersao,
    VersaoProduto.StatusVersao status,
    String autor,
    String changelog,
    String dadosJson,
    LocalDateTime dataVersao
) {

    public static VersaoProdutoResponse de(VersaoProduto v) {
        return new VersaoProdutoResponse(
            v.getId(), v.getProdutoId(), v.getNumeroVersao(),
            v.getStatus(), v.getAutor(), v.getChangelog(),
            v.getDadosJson(), v.getDataVersao()
        );
    }
}
