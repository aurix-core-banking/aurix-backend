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
@Order(1)
public class SerasaProvider implements BureauProvider {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SerasaProvider.class);

    private final RestTemplate restTemplate;
    private final String url;
    private final String apiKey;

    public SerasaProvider(RestTemplate restTemplate,
                          @Value("${aurix.onboarding.bureau.serasa.url}") String url,
                          @Value("${aurix.onboarding.bureau.serasa.api-key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.apiKey = apiKey;
    }

    @Override
    public BureauService.ResultadoBureau consultar(String cpf) {
        log.debug("Consultando Serasa para CPF {}", cpf);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-Key", apiKey);
        var request = new HttpEntity<>(new SerasaRequest(cpf), headers);
        try {
            SerasaResponse response = restTemplate.exchange(url, HttpMethod.POST, request, SerasaResponse.class).getBody();
            if (response == null) {
                throw new RuntimeException("Resposta nula da Serasa");
            }
            return new BureauService.ResultadoBureau(response.score(), response.situacao(), "Serasa: " + response.mensagem());
        } catch (Exception e) {
            log.warn("Erro ao consultar Serasa: {}", e.getMessage());
            throw e;
        }
    }

    @com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
    record SerasaRequest(String cpf) {}

    @com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
    record SerasaResponse(int score, String situacao, String mensagem) {}
}
