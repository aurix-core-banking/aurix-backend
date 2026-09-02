package com.aurix.platform.cards.client;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * Cliente de integração com a Stone (adquirente).
 * <p>
 * Contrato para captura de transações de cartão. O proxy HTTP é gerado por
 * {@code HttpServiceProxyFactory} (ver {@code CartoesHttpConfig}).
 */
@HttpExchange("/api/stone")
public interface StoneClient {

    @PostExchange("/transacoes/captura")
    CapturaResponse capturar(@RequestBody CapturaRequest request);

    @PostExchange("/transacoes/{id}/estorno")
    EstornoResponse estornar(@PathVariable String id, @RequestBody EstornoRequest request);

    record CapturaRequest(String numeroCartao, BigDecimal valor, String moeda,
                          String estabelecimento, String nsu, String bandeira) {}

    record EstornoRequest(BigDecimal valor, String motivo) {}

    record CapturaResponse(String nsu, String status, BigDecimal valorCapturado) {}

    record EstornoResponse(String nsu, String status, BigDecimal valorEstornado) {}
}