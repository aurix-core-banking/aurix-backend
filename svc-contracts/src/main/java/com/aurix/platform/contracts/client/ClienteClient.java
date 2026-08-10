package com.aurix.platform.contracts.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/clientes")
public interface ClienteClient {

    record ClientePerfil(Long id, String nome, String documento, String tipoPessoa, String segmento) {
    }

    @GetExchange("/{id}")
    ClientePerfil buscarPorId(@PathVariable("id") Long id);
}
