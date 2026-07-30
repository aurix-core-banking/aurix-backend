package com.aurix.platform.banking.poupanca.client;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/bacen")
public interface BacenClient {

    @GetExchange("/indicadores/tr/{data}")
    BigDecimal buscarTrDiaria(@PathVariable String data);
}
