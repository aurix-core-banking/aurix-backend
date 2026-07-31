package com.aurix.platform.customer.onboarding.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Primary
public class CepReal implements CepService {

    private static final Logger log = LoggerFactory.getLogger(CepReal.class);
    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/%s/json/";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public ResultadoCep consultar(String cep) {
        String cepLimpo = cep != null ? cep.replaceAll("\\D", "") : "";
        if (cepLimpo.length() != 8) {
            return ResultadoCep.erro(cep, "CEP inválido");
        }
        try {
            String url = String.format(VIA_CEP_URL, cepLimpo);
            String response = restTemplate.getForObject(url, String.class);
            JsonNode json = mapper.readTree(response);

            if (json.has("erro") && json.get("erro").asBoolean()) {
                return ResultadoCep.erro(cepLimpo, "CEP não encontrado");
            }

            return ResultadoCep.ok(
                cepLimpo,
                json.path("logradouro").asText(),
                json.path("bairro").asText(),
                json.path("localidade").asText(),
                json.path("uf").asText()
            );
        } catch (Exception e) {
            log.error("Erro ao consultar CEP {}: {}", cepLimpo, e.getMessage());
            return ResultadoCep.erro(cepLimpo, "Serviço de CEP indisponível");
        }
    }
}