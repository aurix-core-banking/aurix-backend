package com.aurix.platform.cambio.client;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/bacen")
public interface BacenClient {

    record RegistrarContratoBacenRequest(Long contratoId, BigDecimal valor, String moeda, String clienteDoc, String tipo) {}
    record TaxaBacenResponse(String moeda, BigDecimal taxaCompra, BigDecimal taxaVenda, LocalDateTime dataReferencia) {}

    @GetExchange("/cambio/taxas/{moeda}")
    TaxaBacenResponse consultarTaxa(@PathVariable String moeda);

    @PostExchange("/cambio/contratos")
    void registrarContrato(@RequestBody RegistrarContratoBacenRequest request);
}
