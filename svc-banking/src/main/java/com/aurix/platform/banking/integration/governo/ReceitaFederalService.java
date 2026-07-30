package com.aurix.platform.banking.integration.governo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReceitaFederalService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${aurix.integration.receita.url:https://www.receitaws.com.br/v1}")
    private String receitaUrl;
    
    public Map<String, Object> consultarCNPJ(String cnpj) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "Aurix-Organization/1.0");
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<Map> response = restTemplate.exchange(
            receitaUrl + "/cnpj/" + cnpj,
            HttpMethod.GET,
            entity,
            Map.class
        );
        
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        
        return null;
    }
    
    public Map<String, Object> consultarCPF(String cpf) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "Aurix-Organization/1.0");
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<Map> response = restTemplate.exchange(
            receitaUrl + "/cpf/" + cpf,
            HttpMethod.GET,
            entity,
            Map.class
        );
        
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        
        return null;
    }
    
    public boolean validarCNPJ(String cnpj) {
        Map<String, Object> dados = consultarCNPJ(cnpj);
        return dados != null && "OK".equals(dados.get("status"));
    }
    
    public boolean validarCPF(String cpf) {
        Map<String, Object> dados = consultarCPF(cpf);
        return dados != null && "OK".equals(dados.get("status"));
    }
    
    public Map<String, Object> obterDadosEmpresa(String cnpj) {
        Map<String, Object> dados = consultarCNPJ(cnpj);
        if (dados != null && "OK".equals(dados.get("status"))) {
            Map<String, Object> out = new HashMap<>();
            out.put("cnpj", dados.get("cnpj"));
            out.put("nome", dados.get("nome"));
            out.put("fantasia", dados.get("fantasia"));
            out.put("situacao", dados.get("situacao"));
            out.put("abertura", dados.get("abertura"));
            out.put("porte", dados.get("porte"));
            out.put("natureza_juridica", dados.get("natureza_juridica"));
            out.put("logradouro", dados.get("logradouro"));
            out.put("numero", dados.get("numero"));
            out.put("complemento", dados.get("complemento"));
            out.put("bairro", dados.get("bairro"));
            out.put("municipio", dados.get("municipio"));
            out.put("uf", dados.get("uf"));
            out.put("cep", dados.get("cep"));
            out.put("telefone", dados.get("telefone"));
            out.put("email", dados.get("email"));
            return out;
        }
        return null;
    }

    public Map<String, Object> obterDadosPessoa(String cpf) {
        Map<String, Object> dados = consultarCPF(cpf);
        if (dados != null && "OK".equals(dados.get("status"))) {
            Map<String, Object> out = new HashMap<>();
            out.put("cpf", dados.get("cpf"));
            out.put("nome", dados.get("nome"));
            out.put("nascimento", dados.get("nascimento"));
            out.put("situacao", dados.get("situacao"));
            out.put("logradouro", dados.get("logradouro"));
            out.put("numero", dados.get("numero"));
            out.put("complemento", dados.get("complemento"));
            out.put("bairro", dados.get("bairro"));
            out.put("municipio", dados.get("municipio"));
            out.put("uf", dados.get("uf"));
            out.put("cep", dados.get("cep"));
            return out;
        }
        return null;
    }
}
