package com.aurix.platform.customer.temporal;

import com.aurix.platform.customer.temporal.activity.OnboardingPJActivities;
import com.aurix.platform.customer.temporal.activity.OnboardingPJActivities.ResultadoConsulta;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.*;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;

public class OnboardingPJWorkflowImpl implements OnboardingPJWorkflow {

    private static final Logger log = Workflow.getLogger(OnboardingPJWorkflowImpl.class);

    private final OnboardingPJActivities activities = Workflow.newActivityStub(
            OnboardingPJActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofMinutes(10))
                    .setRetryOptions(RetryOptions.newBuilder()
                            .setMaximumAttempts(3)
                            .setInitialInterval(Duration.ofSeconds(2))
                            .build())
                    .build());

    private String statusAtual = "EM_PREENCHIMENTO";

    @Override
    public String executar(OnboardingPJRequest request) {
        log.info("Iniciando workflow Onboarding PJ para CNPJ {}", request.cnpj());
        Workflow.upsertSearchAttributes(Map.of("clienteId", request.cnpj()));

        // 1. Consulta CNPJ na Receita Federal
        ResultadoConsulta cnpj = activities.consultarCnpj(request.cnpj());
        if (!cnpj.aprovado()) {
            return finalizar("REJEITADA", "CNPJ: " + cnpj.motivo());
        }
        statusAtual = "CNPJ_CONSULTADO";

        // 2. Validação de sócios
        ResultadoConsulta socios = activities.validarSocios(request.cnpj(), request.sociosJson());
        if (!socios.aprovado()) {
            return finalizar("REJEITADA", "Sócios: " + socios.motivo());
        }
        statusAtual = "SOCIOS_VALIDADOS";

        // 3. Análise de documentos
        ResultadoConsulta docs = activities.analisarDocumentos(request.cnpj(), request.documentosJson());
        if (!docs.aprovado()) {
            return finalizar("REJEITADA", "Documentos: " + docs.motivo());
        }
        statusAtual = "DOCUMENTOS_ANALISADOS";

        // 4. Verificação AML
        ResultadoConsulta aml = activities.verificarAml(
                request.cnpj(), request.razaoSocial(), request.faturamento());
        if (!aml.aprovado()) {
            return finalizar("REJEITADA", "AML: " + aml.motivo());
        }
        statusAtual = "AML_APROVADO";

        // 5. Compliance (PEP + OFAC)
        ResultadoConsulta compliance = activities.verificarCompliance(request.cnpj(), request.sociosJson());
        if (!compliance.aprovado()) {
            return finalizar("REJEITADA", "Compliance: " + compliance.motivo());
        }
        statusAtual = "COMPLIANCE_APROVADO";

        // 6. Assinatura digital do contrato
        ResultadoConsulta assinatura = activities.solicitarAssinatura(
                request.cnpj(), request.razaoSocial(), request.sociosJson());
        if (!assinatura.aprovado()) {
            // Aguarda signal de confirmação de assinatura
            Workflow.getSignalChannel("assinatura-confirmada").receive();
            statusAtual = "CONTRATO_ASSINADO";
        } else {
            statusAtual = "EM_ASSINATURA";
        }

        // 7. Criação de cliente e conta
        ResultadoConsulta conta = activities.criarClienteConta(
                request.cnpj(), request.razaoSocial(), request.email(), request.telefone());
        if (!conta.aprovado()) {
            return finalizar("ERRO", "Conta: " + conta.motivo());
        }
        statusAtual = "CONTA_CRIADA";

        // 8. Evento final
        activities.publicarEvento("customer.cliente.criado.v1", request.cnpj(),
                "{\"clienteId\":\"" + conta.score() + "\",\"tipo\":\"PJ\"}");

        return finalizar("CONCLUIDA", "Cliente " + conta.score() + " criado");
    }

    @Override
    public String obterStatus() {
        return statusAtual;
    }

    private String finalizar(String novoStatus, String motivo) {
        statusAtual = novoStatus;
        log.info("Workflow Onboarding PJ finalizado: status={}, motivo={}", novoStatus, motivo);
        return motivo;
    }
}
