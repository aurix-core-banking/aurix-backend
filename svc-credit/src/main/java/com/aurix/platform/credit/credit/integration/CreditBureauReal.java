package com.aurix.platform.credit.credit.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Integração real com bureau de crédito (Serasa). Ativo apenas no perfil
 * "producao" — substitui o {@link CreditBureauStub} (perfil "!producao").
 *
 * Configuração:
 * <ul>
 *   <li>{@code aurix.credit.bureau.serasa.url} — endpoint PF de score/relatório</li>
 *   <li>{@code aurix.credit.bureau.serasa.cnpj-url} — endpoint PJ de score (fallback: url PF)</li>
 *   <li>{@code aurix.credit.bureau.serasa.api-key} — chave de API</li>
 *   <li>{@code aurix.credit.bureau.serasa.timeout} — timeout (ms)</li>
 * </ul>
 */
@Component
@Profile("producao")
public class CreditBureauReal implements CreditBureauService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CreditBureauReal.class);

    private final RestTemplate restTemplate;
    private final String url;
    private final String urlCnpj;
    private final String apiKey;

    public CreditBureauReal(
            @Value("${aurix.credit.bureau.serasa.url:}") String url,
            @Value("${aurix.credit.bureau.serasa.cnpj-url:}") String urlCnpj,
            @Value("${aurix.credit.bureau.serasa.api-key:}") String apiKey,
            @Value("${aurix.credit.bureau.serasa.timeout:5000}") long timeoutMs) {
        this.url = url;
        this.urlCnpj = urlCnpj == null || urlCnpj.isBlank() ? url : urlCnpj;
        this.apiKey = apiKey;
        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters().removeIf(c -> c instanceof MappingJackson2HttpMessageConverter);
        this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) timeoutMs);
        factory.setReadTimeout((int) timeoutMs);
        this.restTemplate.setRequestFactory(factory);
    }

    @Override
    public Optional<BureauScore> consultarScore(String cpf) {
        SerasaResponse resposta = chamarSerasa(url, new SerasaRequest(cpf));
        if (resposta == null) {
            return Optional.empty();
        }
        String desc = resposta.score >= 700 ? "Baixo" : resposta.score >= 500 ? "Medio" : "Alto";
        String situacao = resposta.situacao;
        return Optional.of(new BureauScore() {
            @Override
            public String getCpf() { return cpf; }
            @Override
            public int getScore() { return resposta.score; }
            @Override
            public String getDescricaoRisco() { return desc + (situacao != null && !situacao.isBlank() ? " (" + situacao + ")" : ""); }
        });
    }

    @Override
    public Optional<BureauRelatorio> consultarRelatorio(String cpf) {
        SerasaResponse resposta = chamarSerasa(url, new SerasaRequest(cpf));
        if (resposta == null) {
            return Optional.empty();
        }
        return Optional.of(new BureauRelatorio() {
            @Override
            public String getCpf() { return cpf; }
            @Override
            public String getConteudoRelatorio() { return resposta.mensagem; }
            @Override
            public String getCodigoRetorno() { return resposta.situacao != null && !resposta.situacao.isBlank() ? "99" : "00"; }
        });
    }

    @Override
    public ScoreCNPJResult consultarScoreCNPJ(String cnpj) {
        if (cnpj == null || cnpj.replaceAll("\\D", "").length() != 14) {
            return ScoreCNPJResult.erro(cnpj, "CNPJ invalido");
        }
        ScoreCNPJResponse resposta = chamarSerasa(urlCnpj, new ScoreCNPJRequest(cnpj), ScoreCNPJResponse.class);
        if (resposta == null) {
            return ScoreCNPJResult.erro(cnpj, "Sem resposta do bureau");
        }
        return ScoreCNPJResult.ok(resposta.score(), resposta.faturamento(), resposta.risco());
    }

    private SerasaResponse chamarSerasa(String endpoint, Object body) {
        return chamarSerasa(endpoint, body, SerasaResponse.class);
    }

    private <T> T chamarSerasa(String endpoint, Object body, Class<T> tipoResposta) {
        if (endpoint == null || endpoint.isBlank()) {
            log.warn("aurix.credit.bureau.serasa.url nao configurado; consulta ao bureau ignorada");
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-Key", apiKey);
        HttpEntity<Object> request = new HttpEntity<>(body, headers);
        try {
            T resposta = restTemplate.exchange(endpoint, HttpMethod.POST, request, tipoResposta).getBody();
            if (resposta == null) {
                log.warn("Resposta nula do bureau em {}", endpoint);
            }
            return resposta;
        } catch (Exception e) {
            log.warn("Erro ao consultar bureau {}: {}", endpoint, e.getMessage());
            return null;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    record SerasaRequest(String cpf) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record SerasaResponse(int score, String situacao, String mensagem) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record ScoreCNPJRequest(String cnpj) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record ScoreCNPJResponse(int score, BigDecimal faturamento, String risco) {}
}