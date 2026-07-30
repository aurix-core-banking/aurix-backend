package com.aurix.platform.credit.financiamento.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import java.math.BigDecimal;
import java.time.LocalDate;

@HttpExchange("/api/rgi")
public interface CartorioRgiClient {

    record RegistroGarantiaRequest(String tipo, BigDecimal valor, String orgao) {}
    record RegistroResponse(String protocolo, String matricula, LocalDate dataRegistro) {}
    record SituacaoRegistro(String matricula, String status, String orgao) {}

    @PostExchange("/registro")
    RegistroResponse registrarGarantia(@RequestBody RegistroGarantiaRequest request);

    @GetExchange("/consulta/{matricula}")
    SituacaoRegistro consultar(@PathVariable String matricula);
}
