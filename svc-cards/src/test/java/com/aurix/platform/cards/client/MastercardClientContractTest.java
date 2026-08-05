package com.aurix.platform.cards.client;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

class MastercardClientContractTest {

    @Test
    void clientCanBeCreated() {
        var restClient = RestClient.builder().baseUrl("http://localhost:8080").build();
        var client = HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(restClient))
            .build()
            .createClient(MastercardClient.class);
    }
}
