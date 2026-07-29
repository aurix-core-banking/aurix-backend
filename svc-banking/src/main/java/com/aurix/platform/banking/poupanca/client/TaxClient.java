package com.aurix.platform.banking.poupanca.client;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/tax")
public interface TaxClient {

    @PostExchange("/iof/calcular")
    IofResponse calcularIof(@RequestBody IofRequest request);

    record IofRequest(Long clienteId, BigDecimal valorResgate, LocalDate dataAplicacao, LocalDate dataResgate) {}
    record IofResponse(BigDecimal valorIof, String descricao) {}
}
