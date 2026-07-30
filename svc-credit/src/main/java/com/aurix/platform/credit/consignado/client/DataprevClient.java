package com.aurix.platform.credit.consignado.client;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/dataprev")
public interface DataprevClient {

    record MargemInssResponse(BigDecimal margemTotal, BigDecimal margemDisponivel, BigDecimal margemUtilizada, LocalDate dataBase) {}

    @GetExchange("/inss/margem/{cpf}")
    MargemInssResponse consultarMargemInss(@PathVariable String cpf);
}
