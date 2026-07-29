package com.aurix.platform.cambio.client;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/swift")
public interface SwiftClient {

    record EnviarRemessaSwiftRequest(BigDecimal valor, String moeda, String bancoDestino, String contaDestino, String codigoSwift, String finalidade) {}
    record SwiftStatusResponse(String idExterno, String statusSwift, LocalDateTime dataConfirmacao) {}

    @PostExchange("/remessas/enviar")
    SwiftStatusResponse enviarRemessa(@RequestBody EnviarRemessaSwiftRequest request);

    @GetExchange("/remessas/{id}/status")
    SwiftStatusResponse consultarStatus(@PathVariable String id);
}
