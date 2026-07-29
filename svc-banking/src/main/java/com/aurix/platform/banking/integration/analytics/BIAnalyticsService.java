package com.aurix.platform.banking.integration.analytics;

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
public class BIAnalyticsService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${aurix.integration.bi.url:http://localhost:8101}")
    private String biServiceUrl;

    @Value("${aurix.integration.bi.api-key:}")
    private String biApiKey;

    public Map<String, Object> obterMetricasOrganizacionais(Long empresaId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + biApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                biServiceUrl + "/api/metricas/organizacionais/" + empresaId,
                HttpMethod.GET,
                entity,
                Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return null;
    }

    public Map<String, Object> obterDashboardRH(Long empresaId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + biApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                biServiceUrl + "/api/dashboard/rh/" + empresaId,
                HttpMethod.GET,
                entity,
                Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return null;
    }

    public List<Map<String, Object>> obterRelatorioTurnover(Long empresaId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + biApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                biServiceUrl + "/api/relatorios/turnover/" + empresaId,
                HttpMethod.GET,
                entity,
                Map.class);

        Map<String, Object> body = (Map<String, Object>) response.getBody();
        if (response.getStatusCode().is2xxSuccessful() && body != null) {
            Object dados = body.get("dados");
            if (dados instanceof List) {
                return (List<Map<String, Object>>) dados;
            }
        }

        return Collections.emptyList();
    }

    public Map<String, Object> obterAnaliseCompetencias(Long funcionarioId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + biApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                biServiceUrl + "/api/analise/competencias/" + funcionarioId,
                HttpMethod.GET,
                entity,
                Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return null;
    }

    public void enviarDadosFuncionario(Funcionario funcionario) {
        Map<String, Object> dados = Map.of(
                "funcionarioId", funcionario.getId(),
                "matricula", funcionario.getMatricula(),
                "nome", funcionario.getNomeCompleto(),
                "cargo", funcionario.getCargo() != null ? funcionario.getCargo().getNomeCargo() : null,
                "departamento",
                funcionario.getDepartamento() != null ? funcionario.getDepartamento().getNomeDepartamento() : null,
                "empresa", funcionario.getEmpresa().getNomeEmpresa(),
                "dataAdmissao", funcionario.getDataAdmissao(),
                "salario", funcionario.getSalarioAtual(),
                "status", funcionario.getStatus().toString());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + biApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(dados, headers);

        restTemplate.exchange(
                biServiceUrl + "/api/dados/funcionario",
                HttpMethod.POST,
                entity,
                Map.class);
    }

    public void enviarDadosEmpresa(Empresa empresa) {
        Map<String, Object> dados = Map.of(
                "empresaId", empresa.getId(),
                "cnpj", empresa.getCnpj(),
                "nome", empresa.getNomeEmpresa(),
                "status", empresa.getStatus().toString(),
                "dataCriacao", empresa.getDataCriacao());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + biApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(dados, headers);

        restTemplate.exchange(
                biServiceUrl + "/api/dados/empresa",
                HttpMethod.POST,
                entity,
                Map.class);
    }

    public Map<String, Object> obterPrevisaoDemissao(Long funcionarioId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + biApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                biServiceUrl + "/api/predicao/demissao/" + funcionarioId,
                HttpMethod.GET,
                entity,
                Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return null;
    }

    public List<Map<String, Object>> obterRecomendacoesCargos(Long funcionarioId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + biApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                biServiceUrl + "/api/recomendacoes/cargos/" + funcionarioId,
                HttpMethod.GET,
                entity,
                Map.class);

        Map<String, Object> body = (Map<String, Object>) response.getBody();
        if (response.getStatusCode().is2xxSuccessful() && body != null) {
            Object recomendacoes = body.get("recomendacoes");
            if (recomendacoes instanceof List) {
                return (List<Map<String, Object>>) recomendacoes;
            }
        }

        return Collections.emptyList();
    }
}
