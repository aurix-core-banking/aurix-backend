package com.aurix.platform.products.exception;

import com.aurix.platform.shared.exception.AurixException;

public class ProdutoNaoEncontradoException extends AurixException {

    public ProdutoNaoEncontradoException(Long id) {
        super("PRODUTO_NAO_ENCONTRADO", "Produto não encontrado: " + id);
    }

    public ProdutoNaoEncontradoException(String codigo) {
        super("PRODUTO_NAO_ENCONTRADO", "Produto não encontrado com código: " + codigo);
    }
}
