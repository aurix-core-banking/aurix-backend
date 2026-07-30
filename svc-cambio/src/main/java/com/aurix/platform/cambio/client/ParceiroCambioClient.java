package com.aurix.platform.cambio.client;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/parceiro-cambio")
public interface ParceiroCambioClient {

    record CotacaoParceiroResponse(String moeda, BigDecimal taxaCompra, BigDecimal taxaVenda, LocalDateTime dataCotacao) {}

    @GetExchange("/cotacoes/{moeda}")
    CotacaoParceiroResponse consultarCotacao(@PathVariable String moeda);

    @PostExchange("/contratos")
    void registrarContratoParceiro(@RequestBody Object request);
}
