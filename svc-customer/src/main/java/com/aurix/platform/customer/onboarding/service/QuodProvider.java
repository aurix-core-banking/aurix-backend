package com.aurix.platform.customer.onboarding.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Order(2)
public class QuodProvider implements BureauProvider {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(QuodProvider.class);

    private final RestTemplate restTemplate;
    private final String url;
    private final String apiKey;

    public QuodProvider(RestTemplate restTemplate,
                        @Value("${aurix.onboarding.bureau.quod.url}") String url,
                        @Value("${aurix.onboarding.bureau.quod.api-key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.apiKey = apiKey;
    }

    @Override
    public BureauService.ResultadoBureau consultar(String cpf) {
        log.debug("Consultando Quod para CPF {}", cpf);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        var request = new HttpEntity<>(new QuodRequest(cpf), headers);
        try {
            QuodResponse response = restTemplate.exchange(url, HttpMethod.POST, request, QuodResponse.class).getBody();
            if (response == null) {
                throw new RuntimeException("Resposta nula do Quod");
            }
            return new BureauService.ResultadoBureau(response.score(), response.situacao(), "Quod: " + response.mensagem());
        } catch (Exception e) {
            log.warn("Erro ao consultar Quod: {}", e.getMessage());
            throw e;
        }
    }

    @com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
    record QuodRequest(String cpf) {}

    @com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
    record QuodResponse(int score, String situacao, String mensagem) {}
}
