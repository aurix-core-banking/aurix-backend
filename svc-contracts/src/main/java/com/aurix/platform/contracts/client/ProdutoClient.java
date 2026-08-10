package com.aurix.platform.contracts.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/products")
public interface ProdutoClient {

    record ProdutoCatalogo(Long id, String codigo, String nome, String tipoProduto, String status, Boolean ativo) {
    }

    @GetExchange("/produtos/codigo/{codigo}")
    ProdutoCatalogo buscarPorCodigo(@PathVariable("codigo") String codigo);
}
