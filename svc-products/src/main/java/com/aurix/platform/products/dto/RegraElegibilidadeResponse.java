package com.aurix.platform.products.dto;

import com.aurix.platform.products.entity.RegraElegibilidade;

public record RegraElegibilidadeResponse(
    Long id,
    Long produtoId,
    RegraElegibilidade.TipoRegra tipoRegra,
    RegraElegibilidade.Comparador comparador,
    java.math.BigDecimal valorNumerico,
    String valorTexto,
    String descricao,
    Boolean ativa
) {

    public static RegraElegibilidadeResponse de(RegraElegibilidade r) {
        return new RegraElegibilidadeResponse(
            r.getId(), r.getProdutoId(), r.getTipoRegra(), r.getComparador(),
            r.getValorNumerico(), r.getValorTexto(), r.getDescricao(), r.getAtiva()
        );
    }
}
