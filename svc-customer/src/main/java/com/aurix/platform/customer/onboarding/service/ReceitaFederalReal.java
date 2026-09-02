package com.aurix.platform.customer.onboarding.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Integração real com a Receita Federal para consulta de CNPJ. Ativo apenas no
 * perfil "producao" — substitui o {@link ReceitaFederalStub} (perfil
 * "dev"/"test"/"stub").
 *
 * Configuração:
 * <ul>
 *   <li>{@code aurix.onboarding.receita.url} — endpoint de consulta de CNPJ</li>
 *   <li>{@code aurix.onboarding.receita.api-key} — chave de API</li>
 *   <li>{@code aurix.onboarding.receita.timeout} — timeout (ms)</li>
 * </ul>
 */
@Component
@Profile("producao")
public class ReceitaFederalReal implements ReceitaFederalService {

    private static final Logger log = LoggerFactory.getLogger(ReceitaFederalReal.class);

    private final RestTemplate restTemplate;
    private final String url;
    private final ObjectMapper mapper = new ObjectMapper();

    public ReceitaFederalReal(
            @Value("${aurix.onboarding.receita.url:https://api.receita.serpro.gov.br/cnpj/v2/%s}") String url,
            @Value("${aurix.onboarding.receita.api-key:}") String apiKey,
            @Value("${aurix.onboarding.receita.timeout:5000}") long timeoutMs) {
        this.url = url;
        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters().removeIf(c ->
                c instanceof MappingJackson2HttpMessageConverter);
        this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) timeoutMs);
        factory.setReadTimeout((int) timeoutMs);
        this.restTemplate.setRequestFactory(factory);
        if (apiKey == null || apiKey.isBlank()) {
            log.warn("aurix.onboarding.receita.api-key não configurada — consultas de CNPJ podem falhar");
        }
    }

    @Override
    public ResultadoReceita consultarCnpj(String cnpj) {
        String digitos = cnpj != null ? cnpj.replaceAll("\\D", "") : "";
        if (digitos.length() != 14) {
            return ResultadoReceita.erro(cnpj, "CNPJ inválido");
        }
        try {
            String endpoint = String.format(this.url, digitos);
            String response = restTemplate.getForObject(endpoint, String.class);
            if (response == null || response.isBlank()) {
                return ResultadoReceita.erro(digitos, "Resposta vazia da Receita Federal");
            }

            JsonNode json = mapper.readTree(response);

            if (json.has("status") && json.get("status").asInt() == 404) {
                return ResultadoReceita.erro(digitos, "CNPJ não encontrado");
            }

            String razaoSocial = validaTexto(json, "nome", "razao_social", "nomeEmpresarial");
            String situacao = validaTexto(json, "situacao_cadastral", "situacao");

            if (razaoSocial == null || situacao == null) {
                return ResultadoReceita.erro(digitos,
                        "Resposta sem dados válidos (nome/razao_social/situacao)");
            }

            return ResultadoReceita.ok(digitos, razaoSocial, situacao);
        } catch (Exception e) {
            log.error("Erro ao consultar CNPJ {}: {}", digitos, e.getMessage());
            return ResultadoReceita.erro(digitos, "Serviço da Receita Federal indisponível");
        }
    }

    private String validaTexto(final JsonNode json, final String... campos) {
        for (String campo : campos) {
            if (json.hasNonNull(campo)) {
                String valor = json.get(campo).asText();
                if (valor != null && !valor.isBlank()) {
                    return valor;
                }
            }
        }
        return null;
    }
}