package com.aurix.platform.seguros.integracao;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * Contrato HTTP de integração com a seguradora para cotação de prêmio e
 * registro de apólice.
 */
@HttpExchange("/api/seguradora")
public interface SeguradoraClient {

    record CotacaoRequest(
            String produto, String cobertura, BigDecimal valorSegurado,
            Integer idade, String uf, String sexo, String profissao) {
    }

    record CotacaoResponse(BigDecimal premio, BigDecimal taxa, String seguradora, Boolean aceita) {
    }

    record RegistrarApoliceRequest(
            String numeroApolice, Long produtoId, Long clienteId, String cobertura,
            BigDecimal valorSegurado, BigDecimal premio) {
    }

    record RegistrarApoliceResponse(String protocolo, String status, String numeroApolice) {
    }

    @GetExchange("/cotacao/{produto}")
    CotacaoResponse consultarTaxa(@PathVariable String produto);

    @PostExchange("/cotacao")
    CotacaoResponse cotar(@RequestBody CotacaoRequest request);

    @PostExchange("/apolices")
    RegistrarApoliceResponse registrarApolice(@RequestBody RegistrarApoliceRequest request);
}
