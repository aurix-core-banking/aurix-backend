package com.aurix.platform.banking.salario.client;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import java.math.BigDecimal;

@HttpExchange("/api/core/contas")
public interface ContaCorrenteClient {

    @GetExchange("/{contaId}")
    ContaCorrenteResponse getConta(@PathVariable Long contaId);

    @PostExchange("/{contaId}/creditar")
    void creditar(@PathVariable Long contaId, @RequestBody CreditoRequest request);

    @PostExchange("/{contaId}/debitar")
    void debitar(@PathVariable Long contaId, @RequestBody DebitoRequest request);

    record CreditoRequest(
        @NotNull @DecimalMin("0.01") BigDecimal valor,
        @NotBlank String descricao
    ) {}

    record DebitoRequest(
        @NotNull @DecimalMin("0.01") BigDecimal valor,
        @NotBlank String descricao
    ) {}

    record ContaCorrenteResponse(Long id, String status) {}
}
