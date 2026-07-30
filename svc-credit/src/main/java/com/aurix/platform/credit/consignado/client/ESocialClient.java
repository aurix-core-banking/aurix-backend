package com.aurix.platform.credit.consignado.client;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/esocial")
public interface ESocialClient {

    record MargemEmpresaResponse(BigDecimal margemTotal, BigDecimal margemDisponivel, BigDecimal margemUtilizada, String empresa) {}

    @GetExchange("/empresa/margem/{cpf}")
    MargemEmpresaResponse consultarMargemEmpresa(@PathVariable String cpf);
}
