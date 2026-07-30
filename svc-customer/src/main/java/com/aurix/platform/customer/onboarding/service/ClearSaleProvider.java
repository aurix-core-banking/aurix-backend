package com.aurix.platform.customer.onboarding.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Profile("producao")
public class ClearSaleProvider implements FraudService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ClearSaleProvider.class);

    private final RestTemplate restTemplate;
    private final String url;
    private final String apiKey;

    public ClearSaleProvider(RestTemplate restTemplate,
                              @Value("${aurix.onboarding.fraud.cleansale.url}") String url,
                              @Value("${aurix.onboarding.fraud.cleansale.api-key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.apiKey = apiKey;
    }

    @Override
    public ResultadoFraude analisar(String cpf, String nome, String email, String telefone) {
        log.debug("Analisando fraude via ClearSale para CPF {}", cpf);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("API-Key", apiKey);
        var request = new HttpEntity<>(new ClearSaleRequest(cpf, nome, email, telefone), headers);
        try {
            ClearSaleResponse response = restTemplate.exchange(url, HttpMethod.POST, request, ClearSaleResponse.class).getBody();
            if (response == null) {
                throw new RuntimeException("Resposta nula do ClearSale");
            }
            boolean aprovado = "APROVADO".equalsIgnoreCase(response.recomendacao());
            return new ResultadoFraude(aprovado, response.codigo(), "ClearSale: " + response.mensagem(), response.risco());
        } catch (Exception e) {
            log.warn("Erro ao consultar ClearSale, assumindo aprovado: {}", e.getMessage());
            return new ResultadoFraude(true, "FALHA_PROVEDOR", "ClearSale indisponivel, assumindo aprovado", 0);
        }
    }

    @com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
    record ClearSaleRequest(String cpf, String nome, String email, String telefone) {}

    @com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
    record ClearSaleResponse(String recomendacao, String codigo, String mensagem, int risco) {}
}
