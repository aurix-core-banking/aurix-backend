package com.aurix.platform.banking.integration.webhook;

import com.aurix.platform.banking.entity.Funcionario;
import com.aurix.platform.banking.entity.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Map;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class WebhookService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebhookEndpoint webhookEndpoint;

    private List<WebhookEndpoint.Endpoint> getEndpoints() {
        return webhookEndpoint.getEndpoints() != null ? webhookEndpoint.getEndpoints()
                : java.util.Collections.emptyList();
    }

    public void enviarWebhookFuncionario(Funcionario funcionario, String evento) {
        Map<String, Object> dados = Map.of(
                "funcionarioId", funcionario.getId(),
                "matricula", funcionario.getMatricula(),
                "nome", funcionario.getNomeCompleto(),
                "cpf", funcionario.getCpf(),
                "email", funcionario.getEmail(),
                "cargo", funcionario.getCargo() != null ? funcionario.getCargo().getNomeCargo() : "",
                "departamento",
                funcionario.getDepartamento() != null ? funcionario.getDepartamento().getNomeDepartamento() : "",
                "empresa", funcionario.getEmpresa().getNomeEmpresa(),
                "status", funcionario.getStatus().toString());
        Map<String, Object> payload = Map.of(
                "evento", evento,
                "timestamp", System.currentTimeMillis(),
                "dados", dados);

        getEndpoints().stream().filter(WebhookEndpoint.Endpoint::isEnabled).forEach(ep -> {
            CompletableFuture.runAsync(() -> {
                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Content-Type", "application/json");
                    headers.set("User-Agent", "Aurix-Organization/1.0");
                    if (ep.getApiKey() != null) {
                        headers.set("Authorization", "Bearer " + ep.getApiKey());
                    }
                    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
                    restTemplate.exchange(ep.getUrl() + "/webhook/funcionario", HttpMethod.POST, entity, Map.class);
                } catch (Exception e) {
                    System.err.println("Erro ao enviar webhook para " + ep.getUrl() + ": " + e.getMessage());
                }
            });
        });
    }

    public void enviarWebhookEmpresa(Empresa empresa, String evento) {
        Map<String, Object> dados = Map.of(
                "empresaId", empresa.getId(),
                "cnpj", empresa.getCnpj(),
                "nome", empresa.getNomeEmpresa(),
                "status", empresa.getStatus().toString(),
                "dataCriacao", empresa.getDataCriacao() != null ? empresa.getDataCriacao().toString() : "");
        Map<String, Object> payload = Map.of(
                "evento", evento,
                "timestamp", System.currentTimeMillis(),
                "dados", dados);

        getEndpoints().stream().filter(WebhookEndpoint.Endpoint::isEnabled).forEach(ep -> {
            CompletableFuture.runAsync(() -> {
                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Content-Type", "application/json");
                    headers.set("User-Agent", "Aurix-Organization/1.0");
                    if (ep.getApiKey() != null) {
                        headers.set("Authorization", "Bearer " + ep.getApiKey());
                    }
                    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
                    restTemplate.exchange(ep.getUrl() + "/webhook/empresa", HttpMethod.POST, entity, Map.class);
                } catch (Exception e) {
                    System.err.println("Erro ao enviar webhook para " + ep.getUrl() + ": " + e.getMessage());
                }
            });
        });
    }

    public void enviarWebhookAprovacao(Long solicitacaoId, String evento, Map<String, Object> dados) {
        Map<String, Object> payload = Map.of(
                "evento", evento,
                "timestamp", System.currentTimeMillis(),
                "solicitacaoId", solicitacaoId,
                "dados", dados);

        getEndpoints().stream().filter(WebhookEndpoint.Endpoint::isEnabled).forEach(ep -> {
            CompletableFuture.runAsync(() -> {
                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Content-Type", "application/json");
                    headers.set("User-Agent", "Aurix-Organization/1.0");
                    if (ep.getApiKey() != null) {
                        headers.set("Authorization", "Bearer " + ep.getApiKey());
                    }
                    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
                    restTemplate.exchange(ep.getUrl() + "/webhook/aprovacao", HttpMethod.POST, entity, Map.class);
                } catch (Exception e) {
                    System.err.println("Erro ao enviar webhook para " + ep.getUrl() + ": " + e.getMessage());
                }
            });
        });
    }
}
