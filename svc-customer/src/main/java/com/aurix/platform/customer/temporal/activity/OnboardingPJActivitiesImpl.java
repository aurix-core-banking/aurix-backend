package com.aurix.platform.customer.temporal.activity;

import com.aurix.platform.customer.integration.CoreApiClient;
import com.aurix.platform.customer.integration.ComplianceClient;
import com.aurix.platform.customer.integration.ReceitaFederalClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OnboardingPJActivitiesImpl implements OnboardingPJActivities {

    private static final Logger log = LoggerFactory.getLogger(OnboardingPJActivitiesImpl.class);

    private final ReceitaFederalClient receitaFederalClient;
    private final ComplianceClient complianceClient;
    private final CoreApiClient coreApiClient;

    public OnboardingPJActivitiesImpl(ReceitaFederalClient receitaFederalClient,
                                       ComplianceClient complianceClient,
                                       CoreApiClient coreApiClient) {
        this.receitaFederalClient = receitaFederalClient;
        this.complianceClient = complianceClient;
        this.coreApiClient = coreApiClient;
    }

    @Override
    public ResultadoConsulta consultarCnpj(String cnpj) {
        log.info("Activity: Consultando CNPJ {}", cnpj);
        try {
            boolean valido = receitaFederalClient.validarCnpj(cnpj);
            return new ResultadoConsulta(valido, valido ? "CNPJ válido" : "CNPJ inválido", null, null);
        } catch (Exception e) {
            return new ResultadoConsulta(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoConsulta validarSocios(String cnpj, String sociosJson) {
        log.info("Activity: Validando sócios do CNPJ {}", cnpj);
        try {
            boolean valido = complianceClient.validarSocios(cnpj, sociosJson);
            return new ResultadoConsulta(valido, valido ? "Sócios validados" : "Sócios inválidos", null, null);
        } catch (Exception e) {
            return new ResultadoConsulta(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoConsulta analisarDocumentos(String cnpj, String documentosJson) {
        log.info("Activity: Analisando documentos do CNPJ {}", cnpj);
        try {
            boolean valido = complianceClient.analisarDocumentos(cnpj, documentosJson);
            return new ResultadoConsulta(valido, valido ? "Documentos aprovados" : "Documentos rejeitados", null, null);
        } catch (Exception e) {
            return new ResultadoConsulta(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoConsulta verificarAml(String cnpj, String razaoSocial, BigDecimal faturamento) {
        log.info("Activity: Verificando AML para CNPJ {}", cnpj);
        try {
            boolean aprovado = complianceClient.verificarAml(cnpj, razaoSocial, faturamento);
            return new ResultadoConsulta(aprovado, aprovado ? "AML aprovado" : "AML rejeitado", null, null);
        } catch (Exception e) {
            return new ResultadoConsulta(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoConsulta verificarCompliance(String cnpj, String sociosJson) {
        log.info("Activity: Verificando compliance para CNPJ {}", cnpj);
        try {
            boolean aprovado = complianceClient.verificarCompliance(cnpj, sociosJson);
            return new ResultadoConsulta(aprovado, aprovado ? "Compliance aprovado" : "Compliance rejeitado", null, null);
        } catch (Exception e) {
            return new ResultadoConsulta(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoConsulta solicitarAssinatura(String cnpj, String razaoSocial, String sociosJson) {
        log.info("Activity: Solicitando assinatura digital para CNPJ {}", cnpj);
        try {
            boolean assinado = complianceClient.solicitarAssinatura(cnpj, razaoSocial, sociosJson);
            return new ResultadoConsulta(assinado, assinado ? "Contrato assinado" : "Aguardando assinatura", null, null);
        } catch (Exception e) {
            return new ResultadoConsulta(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoConsulta criarClienteConta(String cnpj, String razaoSocial, String email, String telefone) {
        log.info("Activity: Criando cliente PJ e conta para CNPJ {}", cnpj);
        try {
            String clienteId = coreApiClient.criarClientePJeConta(cnpj, razaoSocial, email, telefone);
            return new ResultadoConsulta(true, "Cliente e conta criados", clienteId, null);
        } catch (Exception e) {
            return new ResultadoConsulta(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public void publicarEvento(String topico, String chave, String payload) {
        log.info("Activity: Publicando evento no tópico {}", topico);
    }
}
