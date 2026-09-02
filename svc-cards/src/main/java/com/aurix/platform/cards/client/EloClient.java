package com.aurix.platform.cards.client;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * Cliente de integração com a Elo (bandeira).
 * <p>
 * Contrato para autorização, captura e estorno de transações em cartões Elo.
 * O proxy HTTP é gerado por {@code HttpServiceProxyFactory} (ver
 * {@code CartoesHttpConfig}).
 */
@HttpExchange("/api/elo")
public interface EloClient {

    @PostExchange("/autorizacao")
    AutorizacaoResponse autorizar(@RequestBody AutorizacaoRequest request);

    @PostExchange("/autorizacao/{id}/captura")
    CapturaResponse capturar(@PathVariable String id, @RequestBody CapturaRequest request);

    @PostExchange("/autorizacao/{id}/estorno")
    EstornoResponse estornar(@PathVariable String id, @RequestBody EstornoRequest request);

    record AutorizacaoRequest(String numeroCartao, BigDecimal valor, String moeda,
                              String estabelecimento, String nsu, String modo) {}

    record CapturaRequest(BigDecimal valor, String moeda) {}

    record EstornoRequest(BigDecimal valor, String moeda, String motivo) {}

    record AutorizacaoResponse(String autorizacao, String status, BigDecimal valorAutorizado) {}

    record CapturaResponse(String nsu, String status, BigDecimal valorCapturado) {}

    record EstornoResponse(String nsu, String status, BigDecimal valorEstornado) {}
}