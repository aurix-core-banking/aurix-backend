package com.aurix.platform.payments.pix.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.time.LocalDateTime;

@HttpExchange("/pix/v2")
public interface BacenPixClient {

    record ConsultarChaveResponse(String chave, String tipo, String instituicao, String nomeTitular, String documento, LocalDateTime dataCriacao) {}
    record RegistrarSpbRequest(String chave, String tipoConta, String ispb, String agencia, String conta, String documento) {}
    record SpbResponse(String id, String status, String protocolo) {}
    record DevolverPixRequest(String endToEndId, String valor, String natureza, String codigo) {}
    record DevolucaoResponse(String id, String status, String motivo) {}

    @GetExchange("/chaves/{chave}")
    ConsultarChaveResponse consultarChave(@PathVariable String chave);

    @PostExchange("/spb/registrar")
    SpbResponse registrarSpb(@RequestBody RegistrarSpbRequest request);

    @PostExchange("/spb/devolver")
    DevolucaoResponse devolverPix(@RequestBody DevolverPixRequest request);
}
