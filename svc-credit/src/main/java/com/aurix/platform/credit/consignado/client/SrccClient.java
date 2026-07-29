package com.aurix.platform.credit.consignado.client;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/srcc")
public interface SrccClient {

    record MargemResponse(BigDecimal margemTotal, BigDecimal margemDisponivel, BigDecimal margemUtilizada, String fonte, LocalDateTime dataReferencia) {}
    record ContratoRequest(Long clienteId, BigDecimal valorTotal, int prazoMeses, BigDecimal valorParcela, String fonteMargem) {}
    record ContratoResponse(Long id, String protocolo, String status) {}

    @GetExchange("/margem/{cpfCnpj}")
    MargemResponse consultarMargem(@PathVariable String cpfCnpj);

    @PostExchange("/contratos")
    ContratoResponse registrarContrato(@RequestBody ContratoRequest request);
}
