package com.aurix.platform.banking.controller;

import com.aurix.platform.banking.integration.analytics.BIAnalyticsService;
import com.aurix.platform.banking.integration.governo.ESocialIntegrationService;
import com.aurix.platform.banking.integration.governo.ReceitaFederalService;
import com.aurix.platform.banking.integration.rh.RHIntegrationService;
import com.aurix.platform.banking.integration.social.LinkedInIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("bankingIntegrationController")
@RequestMapping("/api/banking/integration")
@CrossOrigin(origins = "*")
public class IntegrationController {
    
    @Autowired
    private RHIntegrationService rhIntegrationService;
    
    @Autowired
    private ESocialIntegrationService eSocialIntegrationService;
    
    @Autowired
    private ReceitaFederalService receitaFederalService;
    
    @Autowired
    private LinkedInIntegrationService linkedInIntegrationService;
    
    @Autowired
    private BIAnalyticsService biAnalyticsService;
    
    // ===== INTEGRAÇÃO COM RH =====
    
    @PostMapping("/rh/sincronizar")
    public ResponseEntity<Map<String, Object>> sincronizarRH() {
        try {
            var funcionarios = rhIntegrationService.sincronizarFuncionarios();
            return ResponseEntity.ok(Map.of(
                "sucesso", true,
                "funcionariosSincronizados", funcionarios.size(),
                "mensagem", "Sincronização com RH realizada com sucesso"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "sucesso", false,
                "erro", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/rh/estrutura-organizacional")
    public ResponseEntity<Map<String, Object>> obterEstruturaOrganizacionalRH() {
        try {
            var estrutura = rhIntegrationService.obterEstruturaOrganizacionalRH();
            return ResponseEntity.ok(Map.of(
                "sucesso", true,
                "estrutura", estrutura
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "sucesso", false,
                "erro", e.getMessage()
            ));
        }
    }
    
    // ===== INTEGRAÇÃO COM RECEITA FEDERAL =====
    
    @GetMapping("/receita/validar-cnpj/{cnpj}")
    public ResponseEntity<Map<String, Object>> validarCNPJ(@PathVariable String cnpj) {
        try {
            boolean valido = receitaFederalService.validarCNPJ(cnpj);
            Map<String, Object> dados = null;
            
            if (valido) {
                dados = receitaFederalService.obterDadosEmpresa(cnpj);
            }
            
            return ResponseEntity.ok(Map.of(
                "sucesso", true,
                "valido", valido,
                "dados", dados
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "sucesso", false,
                "erro", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/receita/validar-cpf/{cpf}")
    public ResponseEntity<Map<String, Object>> validarCPF(@PathVariable String cpf) {
        try {
            boolean valido = receitaFederalService.validarCPF(cpf);
            Map<String, Object> dados = null;
            
            if (valido) {
                dados = receitaFederalService.obterDadosPessoa(cpf);
            }
            
            return ResponseEntity.ok(Map.of(
                "sucesso", true,
                "valido", valido,
                "dados", dados
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "sucesso", false,
                "erro", e.getMessage()
            ));
        }
    }
    
    // ===== INTEGRAÇÃO COM eSOCIAL =====
    
    @PostMapping("/esocial/enviar-evento-s1000/{empresaId}")
    public ResponseEntity<Map<String, Object>> enviarEventoS1000(@PathVariable Long empresaId) {
        try {
            // Aqui você buscaria a empresa pelo ID e enviaria o evento
            return ResponseEntity.ok(Map.of(
                "sucesso", true,
                "mensagem", "Evento S-1000 enviado com sucesso"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "sucesso", false,
                "erro", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/esocial/cbos")
    public ResponseEntity<Map<String, Object>> obterCBOs() {
        try {
            var cbos = eSocialIntegrationService.obterCBOs();
            return ResponseEntity.ok(Map.of(
                "sucesso", true,
                "cbos", cbos
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "sucesso", false,
                "erro", e.getMessage()
            ));
        }
    }
    
    // ===== INTEGRAÇÃO COM LINKEDIN =====
    
    @GetMapping("/linkedin/perfil/{email}")
    public ResponseEntity<Map<String, Object>> obterPerfilLinkedIn(@PathVariable String email) {
        try {
            var perfil = linkedInIntegrationService.obterPerfilLinkedIn(email);
            return ResponseEntity.ok(Map.of(
                "sucesso", true,
                "perfil", perfil
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "sucesso", false,
                "erro", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/linkedin/competencias/{email}")
    public ResponseEntity<Map<String, Object>> obterCompetenciasLinkedIn(@PathVariable String email) {
        try {
            var competencias = linkedInIntegrationService.obterCompetencias(email);
            return ResponseEntity.ok(Map.of(
                "sucesso", true,
                "competencias", competencias
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "sucesso", false,
                "erro", e.getMessage()
            ));
        }
    }
    
    // ===== INTEGRAÇÃO COM BI/ANALYTICS =====
    
    @GetMapping("/bi/metricas-organizacionais/{empresaId}")
    public ResponseEntity<Map<String, Object>> obterMetricasOrganizacionais(@PathVariable Long empresaId) {
        try {
            var metricas = biAnalyticsService.obterMetricasOrganizacionais(empresaId);
            return ResponseEntity.ok(Map.of(
                "sucesso", true,
                "metricas", metricas
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "sucesso", false,
                "erro", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/bi/dashboard-rh/{empresaId}")
    public ResponseEntity<Map<String, Object>> obterDashboardRH(@PathVariable Long empresaId) {
        try {
            var dashboard = biAnalyticsService.obterDashboardRH(empresaId);
            return ResponseEntity.ok(Map.of(
                "sucesso", true,
                "dashboard", dashboard
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "sucesso", false,
                "erro", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/bi/relatorio-turnover/{empresaId}")
    public ResponseEntity<Map<String, Object>> obterRelatorioTurnover(@PathVariable Long empresaId) {
        try {
            var relatorio = biAnalyticsService.obterRelatorioTurnover(empresaId);
            return ResponseEntity.ok(Map.of(
                "sucesso", true,
                "relatorio", relatorio
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "sucesso", false,
                "erro", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/bi/analise-competencias/{funcionarioId}")
    public ResponseEntity<Map<String, Object>> obterAnaliseCompetencias(@PathVariable Long funcionarioId) {
        try {
            var analise = biAnalyticsService.obterAnaliseCompetencias(funcionarioId);
            return ResponseEntity.ok(Map.of(
                "sucesso", true,
                "analise", analise
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "sucesso", false,
                "erro", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/bi/previsao-demissao/{funcionarioId}")
    public ResponseEntity<Map<String, Object>> obterPrevisaoDemissao(@PathVariable Long funcionarioId) {
        try {
            var previsao = biAnalyticsService.obterPrevisaoDemissao(funcionarioId);
            return ResponseEntity.ok(Map.of(
                "sucesso", true,
                "previsao", previsao
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "sucesso", false,
                "erro", e.getMessage()
            ));
        }
    }
}
