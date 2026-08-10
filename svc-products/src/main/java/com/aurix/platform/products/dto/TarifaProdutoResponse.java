package com.aurix.platform.products.dto;

import com.aurix.platform.products.entity.TarifaProduto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TarifaProdutoResponse(
    Long id,
    Long produtoId,
    String codigo,
    String descricao,
    TarifaProduto.TipoTarifa tipoTarifa,
    TarifaProduto.Periodicidade periodicidade,
    BigDecimal valorFixo,
    BigDecimal percentual,
    LocalDate vigenciaInicio,
    LocalDate vigenciaFim,
    Boolean obrigatoria
) {

    public static TarifaProdutoResponse de(TarifaProduto t) {
        return new TarifaProdutoResponse(
            t.getId(), t.getProdutoId(), t.getCodigo(), t.getDescricao(),
            t.getTipoTarifa(), t.getPeriodicidade(), t.getValorFixo(),
            t.getPercentual(), t.getVigenciaInicio(), t.getVigenciaFim(), t.getObrigatoria()
        );
    }
}
