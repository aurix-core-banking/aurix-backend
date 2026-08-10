package com.aurix.platform.contracts.dto;

import com.aurix.platform.contracts.entity.ContratoVersao;

import java.time.LocalDateTime;

public record ContratoVersaoResponse(
    Long id,
    Long contratoId,
    Integer numeroVersao,
    String motivoAlteracao,
    String dadosJson,
    LocalDateTime dataVersao
) {

    public static ContratoVersaoResponse de(ContratoVersao v) {
        return new ContratoVersaoResponse(
            v.getId(), v.getContratoId(), v.getNumeroVersao(),
            v.getMotivoAlteracao(), v.getDadosJson(), v.getDataVersao()
        );
    }
}
