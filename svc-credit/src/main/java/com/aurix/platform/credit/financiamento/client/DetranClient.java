package com.aurix.platform.credit.financiamento.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import java.math.BigDecimal;

@HttpExchange("/api/detran")
public interface DetranClient {

    record DetranGarantiaRequest(String placa, String chassi, BigDecimal valor, String credor) {}
    record DetranResponse(String protocolo, String registro, String status) {}
    record DadosVeiculo(String placa, String chassi, String marca, String modelo, int ano, String situacao) {}

    @PostExchange("/garantias")
    DetranResponse registrarGarantia(@RequestBody DetranGarantiaRequest request);

    @GetExchange("/veiculos/{placa}")
    DadosVeiculo consultarVeiculo(@PathVariable String placa);
}
