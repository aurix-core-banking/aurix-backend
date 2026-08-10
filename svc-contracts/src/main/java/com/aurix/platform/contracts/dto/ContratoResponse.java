package com.aurix.platform.contracts.dto;

import com.aurix.platform.contracts.entity.Contrato;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ContratoResponse(
    Long id,
    String numeroContrato,
    Long produtoId,
    String produtoCodigo,
    Long clienteId,
    String clienteDocumento,
    Contrato.TipoContrato tipoContrato,
    BigDecimal valor,
    Integer prazoMeses,
    BigDecimal valorParcela,
    BigDecimal taxaJuros,
    Contrato.StatusContrato status,
    LocalDateTime dataAssinatura,
    LocalDate dataVigenciaInicio,
    LocalDate dataVigenciaFim,
    String termosTexto,
    String assinaturaDigital,
    String dadosJson,
    LocalDateTime dataCriacao,
    LocalDateTime dataAtualizacao
) {

    public static ContratoResponse de(Contrato c) {
        return new ContratoResponse(
            c.getId(), c.getNumeroContrato(), c.getProdutoId(), c.getProdutoCodigo(),
            c.getClienteId(), c.getClienteDocumento(), c.getTipoContrato(),
            c.getValor(), c.getPrazoMeses(), c.getValorParcela(), c.getTaxaJuros(),
            c.getStatus(), c.getDataAssinatura(), c.getDataVigenciaInicio(),
            c.getDataVigenciaFim(), c.getTermosTexto(), c.getAssinaturaDigital(),
            c.getDadosJson(), c.getDataCriacao(), c.getDataAtualizacao()
        );
    }
}
