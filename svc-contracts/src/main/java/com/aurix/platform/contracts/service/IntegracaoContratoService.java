package com.aurix.platform.contracts.service;

import com.aurix.platform.contracts.client.ClienteClient;
import com.aurix.platform.contracts.client.ProdutoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IntegracaoContratoService {

    private static final Logger log = LoggerFactory.getLogger(IntegracaoContratoService.class);

    private final ClienteClient clienteClient;
    private final ProdutoClient produtoClient;

    public IntegracaoContratoService(ClienteClient clienteClient, ProdutoClient produtoClient) {
        this.clienteClient = clienteClient;
        this.produtoClient = produtoClient;
    }

    public Optional<ClienteClient.ClientePerfil> buscarCliente(Long clienteId) {
        if (clienteId == null) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(clienteClient.buscarPorId(clienteId));
        } catch (Exception e) {
            log.warn("svc-customer indisponível ao buscar cliente {}: {}", clienteId, e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<ProdutoClient.ProdutoCatalogo> buscarProduto(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(produtoClient.buscarPorCodigo(codigo));
        } catch (Exception e) {
            log.warn("svc-products indisponível ao buscar produto {}: {}", codigo, e.getMessage());
            return Optional.empty();
        }
    }
}
