package com.aurix.platform.credit.consignado.client;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/siafi")
public interface SiafiClient {

    record MargemServidorResponse(BigDecimal margemTotal, BigDecimal margemDisponivel, BigDecimal margemUtilizada, String orgao) {}

    @GetExchange("/servidor/margem/{cpf}")
    MargemServidorResponse consultarMargemServidor(@PathVariable String cpf);
}
