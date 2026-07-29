package com.aurix.platform.banking.integration.rh;

import com.aurix.platform.banking.entity.Funcionario;
import com.aurix.platform.banking.dto.FuncionarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Service
public class RHIntegrationService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${aurix.integration.rh.url:http://localhost:8087}")
    private String rhServiceUrl;

    @Value("${aurix.integration.rh.api-key:}")
    private String rhApiKey;

    public List<FuncionarioDTO> sincronizarFuncionarios() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + rhApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                rhServiceUrl + "/api/funcionarios/sincronizar",
                HttpMethod.GET,
                entity,
                Map.class);

        Map<String, Object> body = (Map<String, Object>) response.getBody();
        if (response.getStatusCode().is2xxSuccessful() && body != null) {
            Object funcionarios = body.get("funcionarios");
            if (funcionarios instanceof List) {
                return (List<FuncionarioDTO>) funcionarios;
            }
        }

        throw new RuntimeException("Erro ao sincronizar funcionários do RH");
    }

    public FuncionarioDTO buscarFuncionarioPorMatriculaRH(String matricula) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + rhApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<FuncionarioDTO> response = restTemplate.exchange(
                rhServiceUrl + "/api/funcionarios/matricula/" + matricula,
                HttpMethod.GET,
                entity,
                FuncionarioDTO.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return null;
    }

    public Map<String, Object> obterDadosFolhaPagamento(Long funcionarioId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + rhApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                rhServiceUrl + "/api/folha-pagamento/funcionario/" + funcionarioId,
                HttpMethod.GET,
                entity,
                Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return null;
    }

    public List<Map<String, Object>> obterEstruturaOrganizacionalRH() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + rhApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                rhServiceUrl + "/api/estrutura-organizacional",
                HttpMethod.GET,
                entity,
                Map.class);

        Map<String, Object> body = (Map<String, Object>) response.getBody();
        if (response.getStatusCode().is2xxSuccessful() && body != null) {
            Object estrutura = body.get("estrutura");
            if (estrutura instanceof List) {
                return (List<Map<String, Object>>) estrutura;
            }
        }

        return java.util.Collections.emptyList();
    }

    public void notificarMudancaFuncionario(Funcionario funcionario, String tipoMudanca) {
        Map<String, Object> payload = Map.of(
                "funcionarioId", funcionario.getId(),
                "matricula", funcionario.getMatricula(),
                "tipoMudanca", tipoMudanca,
                "dados", Map.of(
                        "nome", funcionario.getNomeCompleto(),
                        "cargo", funcionario.getCargo() != null ? funcionario.getCargo().getNomeCargo() : null,
                        "departamento",
                        funcionario.getDepartamento() != null ? funcionario.getDepartamento().getNomeDepartamento()
                                : null,
                        "status", funcionario.getStatus().toString()));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + rhApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        restTemplate.exchange(
                rhServiceUrl + "/api/webhooks/funcionario-mudanca",
                HttpMethod.POST,
                entity,
                Map.class);
    }
}
