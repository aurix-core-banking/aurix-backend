package com.aurix.platform.customer.onboarding.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
@Profile("producao")
public class UnicoProvider implements KycProviderService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UnicoProvider.class);

    private final RestTemplate restTemplate;
    private final String url;
    private final String apiKey;

    public UnicoProvider(RestTemplate restTemplate,
                         @Value("${aurix.onboarding.kyc.unico.url}") String url,
                         @Value("${aurix.onboarding.kyc.unico.api-key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.apiKey = apiKey;
    }

    @Override
    public ResultadoKyc validarDocumentos(String cpf, List<DocumentoInfo> documentos, String selfieBase64) {
        log.debug("Enviando documentos para Unico KYC, CPF {}", cpf);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-Key", apiKey);
        var request = new HttpEntity<>(new UnicoRequest(cpf, documentos, selfieBase64), headers);
        try {
            UnicoResponse response = restTemplate.exchange(url, HttpMethod.POST, request, UnicoResponse.class).getBody();
            if (response == null) {
                throw new RuntimeException("Resposta nula da Unico");
            }
            return new ResultadoKyc(response.aprovado(), response.codigo(), "Unico: " + response.mensagem());
        } catch (Exception e) {
            log.warn("Erro ao consultar Unico KYC: {}", e.getMessage());
            throw e;
        }
    }

    @com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
    record UnicoRequest(String cpf, List<DocumentoInfo> documentos, String selfieBase64) {}

    @com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
    record UnicoResponse(boolean aprovado, String codigo, String mensagem) {}
}
