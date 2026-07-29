package com.aurix.platform.cards.client;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/ml/fraud")
public interface MlFraudClient {

    @PostExchange("/avaliar")
    FraudResponse avaliar(@RequestBody FraudRequest request);

    record FraudRequest(Long cartaoId, BigDecimal valor, String estabelecimento, String modo) {}
    record FraudResponse(String resultado, Double score, String recomendacao) {}
}
