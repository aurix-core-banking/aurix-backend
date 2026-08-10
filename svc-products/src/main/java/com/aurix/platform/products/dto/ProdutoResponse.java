package com.aurix.platform.products.dto;

import com.aurix.platform.products.entity.Produto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProdutoResponse(
    Long id,
    String codigo,
    String nome,
    String descricao,
    Produto.TipoProduto tipoProduto,
    Produto.StatusProduto status,
    Integer numeroVersao,
    String publicoAlvo,
    String exigenciaMinima,
    String requisitos,
    LocalDate vigenciaInicio,
    LocalDate vigenciaFim,
    Boolean ativo,
    LocalDateTime dataCriacao,
    LocalDateTime dataAtualizacao
) {

    public static ProdutoResponse de(Produto p) {
        return new ProdutoResponse(
            p.getId(), p.getCodigo(), p.getNome(), p.getDescricao(),
            p.getTipoProduto(), p.getStatus(), p.getNumeroVersao(),
            p.getPublicoAlvo(), p.getExigenciaMinima(), p.getRequisitos(),
            p.getVigenciaInicio(), p.getVigenciaFim(), p.getAtivo(),
            p.getDataCriacao(), p.getDataAtualizacao()
        );
    }
}
