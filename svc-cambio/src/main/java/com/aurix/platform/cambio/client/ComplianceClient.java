package com.aurix.platform.cambio.client;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/compliance")
public interface ComplianceClient {

    record ValidarOperacaoRequest(Long clienteId, String tipo, BigDecimal valor, String moeda, String finalidade) {}
    record ValidacaoResponse(boolean aprovada, String motivo, String protocolo) {}
    record RegistrarOperacaoRequest(Long contratoId, Long clienteId, String tipo, BigDecimal valor, String moeda) {}

    @GetExchange("/cambio/roe/{clienteId}")
    Object consultarRoe(@PathVariable Long clienteId);

    @PostExchange("/cambio/validar")
    ValidacaoResponse validarOperacao(@RequestBody ValidarOperacaoRequest request);

    @PostExchange("/cambio/registrar")
    void registrarOperacao(@RequestBody RegistrarOperacaoRequest request);
}
