package com.aurix.platform.credit.consignado.client;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/salario")
public interface ContaSalarioClient {

    record ValidarVinculoRequest(Long contaId, Long clienteId) {}
    record ValidarVinculoResponse(boolean valido, String mensagem) {}

    @PostExchange("/vincular/validar")
    ValidarVinculoResponse validarVinculo(@RequestBody ValidarVinculoRequest request);

    record DebitarParcelaRequest(Long contaId, Long clienteId, BigDecimal valor, String identificador) {}

    @PostExchange("/parcelas/debitar")
    void debitarParcela(@RequestBody DebitarParcelaRequest request);
}
