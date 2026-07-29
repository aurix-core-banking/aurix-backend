package com.aurix.platform.banking.integration.social;

import com.aurix.platform.banking.entity.Funcionario;
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
public class LinkedInIntegrationService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${aurix.integration.linkedin.client-id:}")
    private String clientId;

    @Value("${aurix.integration.linkedin.client-secret:}")
    private String clientSecret;

    @Value("${aurix.integration.linkedin.access-token:}")
    private String accessToken;

    public Map<String, Object> obterPerfilLinkedIn(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://api.linkedin.com/v2/people/(id:" + email + ")",
                HttpMethod.GET,
                entity,
                Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return null;
    }

    public List<Map<String, Object>> obterExperienciasProfissionais(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://api.linkedin.com/v2/people/(id:" + email + ")/positions",
                HttpMethod.GET,
                entity,
                Map.class);

        Map<String, Object> data = (Map<String, Object>) response.getBody();
        if (response.getStatusCode().is2xxSuccessful() && data != null) {
            Object values = data.get("values");
            if (values instanceof List) {
                return (List<Map<String, Object>>) values;
            }
        }

        return Collections.emptyList();
    }

    public List<Map<String, Object>> obterEducacao(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://api.linkedin.com/v2/people/(id:" + email + ")/educations",
                HttpMethod.GET,
                entity,
                Map.class);

        Map<String, Object> body = (Map<String, Object>) response.getBody();
        if (response.getStatusCode().is2xxSuccessful() && body != null) {
            Object values = body.get("values");
            if (values instanceof List) {
                return (List<Map<String, Object>>) values;
            }
        }

        return Collections.emptyList();
    }

    public Map<String, Object> obterCompetencias(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://api.linkedin.com/v2/people/(id:" + email + ")/skills",
                HttpMethod.GET,
                entity,
                Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return null;
    }

    public void atualizarPerfilFuncionario(Funcionario funcionario) {
        Map<String, Object> perfil = Map.of(
                "funcionarioId", funcionario.getId(),
                "matricula", funcionario.getMatricula(),
                "nome", funcionario.getNomeCompleto(),
                "email", funcionario.getEmail(),
                "cargo", funcionario.getCargo() != null ? funcionario.getCargo().getNomeCargo() : null,
                "departamento",
                funcionario.getDepartamento() != null ? funcionario.getDepartamento().getNomeDepartamento() : null,
                "empresa", funcionario.getEmpresa().getNomeEmpresa());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(perfil, headers);

        restTemplate.exchange(
                "https://api.linkedin.com/v2/people/me",
                HttpMethod.PUT,
                entity,
                Map.class);
    }

    public List<Map<String, Object>> buscarCandidatos(String cargo, String localizacao) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = String.format(
                "https://api.linkedin.com/v2/people?keywords=%s&location=%s",
                cargo, localizacao);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class);

        Map<String, Object> body = (Map<String, Object>) response.getBody();
        if (response.getStatusCode().is2xxSuccessful() && body != null) {
            Object values = body.get("values");
            if (values instanceof List) {
                return (List<Map<String, Object>>) values;
            }
        }

        return Collections.emptyList();
    }
}
