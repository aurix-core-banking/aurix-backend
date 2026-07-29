package com.aurix.platform.banking.integration.governo;

import com.aurix.platform.banking.entity.Funcionario;
import com.aurix.platform.banking.entity.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.List;
import java.util.Collections;

@Service
public class ESocialIntegrationService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${aurix.integration.esocial.url:https://esocial.gov.br}")
    private String eSocialUrl;

    @Value("${aurix.integration.esocial.certificate-path:}")
    private String certificatePath;

    @Value("${aurix.integration.esocial.password:}")
    private String certificatePassword;

    public Map<String, Object> validarCNPJEmpresa(String cnpj) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "Aurix-Organization/1.0");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                eSocialUrl + "/api/validar-cnpj/" + cnpj,
                HttpMethod.GET,
                entity,
                Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return null;
    }

    public Map<String, Object> validarCPFFuncionario(String cpf) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "Aurix-Organization/1.0");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                eSocialUrl + "/api/validar-cpf/" + cpf,
                HttpMethod.GET,
                entity,
                Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return null;
    }

    public void enviarEventoS1000(Empresa empresa) {
        Map<String, Object> evento = Map.of(
                "tipoEvento", "S-1000",
                "empresa", Map.of(
                        "cnpj", empresa.getCnpj(),
                        "nomeEmpresa", empresa.getNomeEmpresa(),
                        "codigoEmpresa", empresa.getCodigoEmpresa(),
                        "status", empresa.getStatus().toString()),
                "timestamp", System.currentTimeMillis());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "Aurix-Organization/1.0");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(evento, headers);

        restTemplate.exchange(
                eSocialUrl + "/api/eventos/s1000",
                HttpMethod.POST,
                entity,
                Map.class);
    }

    public void enviarEventoS1005(Funcionario funcionario) {
        Map<String, Object> evento = Map.of(
                "tipoEvento", "S-1005",
                "funcionario", Map.of(
                        "cpf", funcionario.getCpf(),
                        "matricula", funcionario.getMatricula(),
                        "nomeCompleto", funcionario.getNomeCompleto(),
                        "cargo", funcionario.getCargo() != null ? funcionario.getCargo().getNomeCargo() : null,
                        "departamento",
                        funcionario.getDepartamento() != null ? funcionario.getDepartamento().getNomeDepartamento()
                                : null,
                        "dataAdmissao", funcionario.getDataAdmissao(),
                        "status", funcionario.getStatus().toString()),
                "timestamp", System.currentTimeMillis());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "Aurix-Organization/1.0");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(evento, headers);

        restTemplate.exchange(
                eSocialUrl + "/api/eventos/s1005",
                HttpMethod.POST,
                entity,
                Map.class);
    }

    public List<Map<String, Object>> obterCBOs() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "Aurix-Organization/1.0");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                eSocialUrl + "/api/cbos",
                HttpMethod.GET,
                entity,
                Map.class);

        Map<String, Object> body = (Map<String, Object>) response.getBody();
        if (response.getStatusCode().is2xxSuccessful() && body != null) {
            Object cbos = body.get("cbos");
            if (cbos instanceof List) {
                return (List<Map<String, Object>>) cbos;
            }
        }

        return Collections.emptyList();
    }

    public Map<String, Object> obterCBO(String codigoCBO) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "Aurix-Organization/1.0");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                eSocialUrl + "/api/cbos/" + codigoCBO,
                HttpMethod.GET,
                entity,
                Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return null;
    }
}
