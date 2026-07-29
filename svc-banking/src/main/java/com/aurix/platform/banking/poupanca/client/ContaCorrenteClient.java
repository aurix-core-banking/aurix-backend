package com.aurix.platform.banking.poupanca.client;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/core/contas")
public interface ContaCorrenteClient {

    @PostExchange("/{id}/debitar")
    void debitar(@PathVariable Long id, @RequestBody DebitoRequest request);

    @PostExchange("/{id}/creditar")
    void creditar(@PathVariable Long id, @RequestBody CreditoRequest request);

    record DebitoRequest(BigDecimal valor, String descricao) {}
    record CreditoRequest(BigDecimal valor, String descricao) {}
}
