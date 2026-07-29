package com.aurix.platform.credit.financiamento.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import java.math.BigDecimal;

@HttpExchange("/api/core/contas")
public interface ContaCorrenteClient {

    record DebitoRequest(BigDecimal valor, String descricao) {}
    record CreditoRequest(BigDecimal valor, String descricao) {}

    @PostExchange("/{id}/debitar")
    void debitar(@PathVariable Long id, @RequestBody DebitoRequest request);

    @PostExchange("/{id}/creditar")
    void creditar(@PathVariable Long id, @RequestBody CreditoRequest request);
}
