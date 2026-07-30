package com.aurix.platform.credit.financiamento.client;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import java.math.BigDecimal;

@HttpExchange("/api/bacen")
public interface BacenClient {

    @GetExchange("/taxas/tr")
    BigDecimal consultarTR();

    @GetExchange("/taxas/selic")
    BigDecimal consultarSelic();
}
