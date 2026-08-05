package com.aurix.platform.customer.onboarding.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings({"PMD.UnusedFormalParameter"})
public class CoreApiClient {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CoreApiClient.class);
    private final RestTemplate restTemplate;
    @Value("${aurix.onboarding.core-api-url:http://localhost:8081/api/core}")
    private String coreApiUrl;
    @Value("${aurix.onboarding.tenant-header:X-Tenant-Id}")
    private String tenantHeader;

    public CriarClienteContaResult criarClientePFeConta(String tenantId, String cpf, String nome, String email, String telefone, String dataNascimento, String endereco, boolean contaLimitada) {
        String url = coreApiUrl + "/clientes";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (tenantId != null) {
            headers.set(tenantHeader, tenantId);
        }
        Map<String, Object> body = Map.of("cpf", cpf, "nome", nome, "email", email, "telefone", telefone != null ? telefone : "", "dataNascimento", dataNascimento != null ? dataNascimento : "", "endereco", endereco != null ? endereco : "{}");
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Object idObj = response.getBody().get("id");
                Long clienteId = idObj instanceof Number ? ((Number) idObj).longValue() : null;
                Long contaId = criarConta(tenantId, clienteId, contaLimitada);
                return new CriarClienteContaResult(clienteId, contaId, true);
            }
        } catch (Exception e) {
            log.warn("Falha ao criar cliente/conta no core: {}", e.getMessage());
        }
        return new CriarClienteContaResult(null, null, false);
    }

    public CriarClienteContaResult criarClientePJeConta(
        String tenantId, String cnpj, String razaoSocial,
        String email, String telefone, String endereco,
        boolean contaLimitada,
        BigDecimal faturamentoMensal, BigDecimal capitalSocial,
        String cnaePrincipal, String porte, String dataConstituicao) {
        String url = coreApiUrl + "/clientes";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (tenantId != null) {
            headers.set(tenantHeader, tenantId);
        }
        Map<String, Object> body = new HashMap<>(Map.of(
            "tipoPessoa", "JURIDICA",
            "cnpj", cnpj,
            "nomeRazaoSocial", razaoSocial,
            "email", email,
            "telefone", telefone != null ? telefone : "",
            "endereco", endereco != null ? endereco : "{}"
        ));
        if (faturamentoMensal != null) body.put("faturamentoMensal", faturamentoMensal);
        if (capitalSocial != null) body.put("capitalSocial", capitalSocial);
        if (cnaePrincipal != null) body.put("cnaePrincipal", cnaePrincipal);
        if (porte != null) body.put("porte", porte);
        if (dataConstituicao != null) body.put("dataConstituicao", dataConstituicao);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Object idObj = response.getBody().get("id");
                Long clienteId = idObj instanceof Number ? ((Number) idObj).longValue() : null;
                Long contaId = criarConta(tenantId, clienteId, contaLimitada);
                return new CriarClienteContaResult(clienteId, contaId, true);
            }
        } catch (Exception e) {
            log.warn("Falha ao criar cliente PJ/conta no core: {}", e.getMessage());
        }
        return new CriarClienteContaResult(null, null, false);
    }

    private Long criarConta(String tenantId, Long clienteId, boolean contaLimitada) {
        if (clienteId == null) return null;
        String url = coreApiUrl + "/contas";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (tenantId != null) headers.set(tenantHeader, tenantId);
        Map<String, Object> body = Map.of("clienteId", clienteId, "tipoConta", "CORRENTE", "saldo", BigDecimal.ZERO, "limiteCredito", BigDecimal.ZERO);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Object idObj = response.getBody().get("id");
                return idObj instanceof Number ? ((Number) idObj).longValue() : null;
            }
        } catch (Exception e) {
            log.warn("Falha ao criar conta no core: {}", e.getMessage());
        }
        return null;
    }


    public record CriarClienteContaResult(Long clienteId, Long contaId, boolean sucesso) {
    }

    @java.lang.SuppressWarnings("all")
    public CoreApiClient(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
