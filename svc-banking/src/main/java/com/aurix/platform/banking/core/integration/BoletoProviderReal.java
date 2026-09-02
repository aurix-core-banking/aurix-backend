package com.aurix.platform.banking.core.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Integração real com API de boleto (Banco do Brasil). Ativo apenas no perfil
 * "producao" e marcado como {@link Primary} para resolver a disputa com o
 * {@link BoletoProviderStub} (ativo em todos os perfis). Em dev/test o stub
 * permanece como default.
 *
 * Configuração:
 * <ul>
 *   <li>{@code aurix.banking.boleto.url} — endpoint de emissão/consulta</li>
 *   <li>{@code aurix.banking.boleto.api-key} — chave de API</li>
 *   <li>{@code aurix.banking.boleto.timeout} — timeout (ms)</li>
 * </ul>
 */
@Component
@Profile("producao")
@Primary
public class BoletoProviderReal implements BoletoProvider {

    private static final Logger log = LoggerFactory.getLogger(BoletoProviderReal.class);

    private final RestTemplate restTemplate;
    private final String url;
    private final String apiKey;
    private final ObjectMapper mapper = new ObjectMapper();

    public BoletoProviderReal(
            @Value("${aurix.banking.boleto.url:https://api.bb.com.br/boleto/v1/boletos}") String url,
            @Value("${aurix.banking.boleto.api-key:}") String apiKey,
            @Value("${aurix.banking.boleto.timeout:5000}") long timeoutMs) {
        this.url = url;
        this.apiKey = apiKey;
        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters().removeIf(c ->
                c instanceof MappingJackson2HttpMessageConverter);
        this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) timeoutMs);
        factory.setReadTimeout((int) timeoutMs);
        this.restTemplate.setRequestFactory(factory);
        if (apiKey == null || apiKey.isBlank()) {
            log.warn("aurix.banking.boleto.api-key não configurada — emissão de boleto pode falhar");
        }
    }

    @Override
    public Optional<BoletoInfo> gerarBoletoReal(String beneficiarioDocumento, String beneficiarioNome,
            BigDecimal valor, LocalDate vencimento,
            String pagadorDocumento, String pagadorNome,
            String descricao) {
        try {
            String payload = mapper.writeValueAsString(new BoletoRequest(
                    beneficiarioDocumento, beneficiarioNome,
                    valor, vencimento,
                    pagadorDocumento, pagadorNome,
                    descricao));
            String response = restTemplate.postForObject(url, payload, String.class);
            if (response == null || response.isBlank()) {
                return Optional.empty();
            }
            JsonNode json = mapper.readTree(response);
            String numero = validaTexto(json, "numero", "nossoNumero", "codigoSolicitacao");
            String linha = validaTexto(json, "linhaDigitavel", "linha_digitavel");
            String codigoBarras = validaTexto(json, "codigoBarras", "codigo_barras");
            String pdfUrl = validaTexto(json, "urlPdf", "pdf_url", "url");

            if (numero == null || linha == null) {
                return Optional.empty();
            }

            return Optional.of(new BoletoInfoImpl(numero, linha, codigoBarras, pdfUrl));
        } catch (Exception e) {
            log.error("Erro ao emitir boleto: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<BoletoStatus> consultarStatus(String numeroBoleto) {
        try {
            String endpoint = url + "/" + numeroBoleto;
            String response = restTemplate.getForObject(endpoint, String.class);
            if (response == null || response.isBlank()) {
                return Optional.empty();
            }
            JsonNode json = mapper.readTree(response);
            boolean pago = json.path("pago").asBoolean(false)
                    || "PAGO".equalsIgnoreCase(json.path("situacao").asText())
                    || "LIQUIDADO".equalsIgnoreCase(json.path("situacao").asText());
            String codigoRetorno = validaTexto(json, "codigoRetorno", "codigo_retorno", "situacao");
            return Optional.of(new BoletoStatusImpl(numeroBoleto, pago, codigoRetorno));
        } catch (Exception e) {
            log.error("Erro ao consultar status do boleto {}: {}", numeroBoleto, e.getMessage());
            return Optional.empty();
        }
    }

    private String validaTexto(final JsonNode json, final String... campos) {
        for (String campo : campos) {
            if (json.hasNonNull(campo)) {
                String valor = json.get(campo).asText();
                if (valor != null && !valor.isBlank() && !"null".equals(valor)) {
                    return valor;
                }
            }
        }
        return null;
    }

    record BoletoRequest(String beneficiarioDocumento, String beneficiarioNome,
                         BigDecimal valor, LocalDate vencimento,
                         String pagadorDocumento, String pagadorNome,
                         String descricao) {
    }

    static final class BoletoInfoImpl implements BoletoInfo {
        private final String numeroBoleto;
        private final String linhaDigitavel;
        private final String codigoBarras;
        private final String pdfUrl;

        BoletoInfoImpl(String numeroBoleto, String linhaDigitavel, String codigoBarras, String pdfUrl) {
            this.numeroBoleto = numeroBoleto;
            this.linhaDigitavel = linhaDigitavel;
            this.codigoBarras = codigoBarras;
            this.pdfUrl = pdfUrl;
        }

        @Override
        public String getNumeroBoleto() {
            return numeroBoleto;
        }

        @Override
        public String getLinhaDigitavel() {
            return linhaDigitavel;
        }

        @Override
        public String getCodigoBarras() {
            return codigoBarras;
        }

        @Override
        public String getPdfUrl() {
            return pdfUrl;
        }
    }

    static final class BoletoStatusImpl implements BoletoStatus {
        private final String numeroBoleto;
        private final boolean pago;
        private final String codigoRetorno;

        BoletoStatusImpl(String numeroBoleto, boolean pago, String codigoRetorno) {
            this.numeroBoleto = numeroBoleto;
            this.pago = pago;
            this.codigoRetorno = codigoRetorno;
        }

        @Override
        public String getNumeroBoleto() {
            return numeroBoleto;
        }

        @Override
        public boolean isPago() {
            return pago;
        }

        @Override
        public String getCodigoRetorno() {
            return codigoRetorno;
        }
    }
}