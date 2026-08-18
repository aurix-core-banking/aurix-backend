package com.aurix.platform.customer.temporal;

import com.aurix.platform.customer.temporal.activity.OnboardingPFActivities;
import com.aurix.platform.customer.temporal.activity.OnboardingPFActivities.ResultadoConsulta;
import io.temporal/activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.*;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.Map;

public class OnboardingPFWorkflowImpl implements OnboardingPFWorkflow {

    private static final Logger log = Workflow.getLogger(OnboardingPFWorkflowImpl.class);

    private final OnboardingPFActivities activities = Workflow.newActivityStub(
            OnboardingPFActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofMinutes(5))
                    .setRetryOptions(RetryOptions.newBuilder()
                            .setMaximumAttempts(3)
                            .setInitialInterval(Duration.ofSeconds(2))
                            .setMaximumInterval(Duration.ofMinutes(1))
                            .build())
                    .build());

    private String statusAtual = "RECEBIDA";

    @Override
    public String executar(OnboardingPFRequest request) {
        log.info("Iniciando workflow Onboarding PF para CPF {}", request.cpf());
        Workflow.upsertSearchAttributes(Map.of("clienteId", request.cpf()));

        // 1. Consulta Receita Federal
        Workflow.sleep(Duration.ofSeconds(1)); // debounce
        ResultadoConsulta rf = activities.consultarReceitaFederal(request.cpf(), request.nome(), request.dataNascimento());
        if (!rf.aprovado()) {
            return finalizar("REJEITADA", "RF: " + rf.motivo());
        }
        statusAtual = "RF_VALIDADO";

        // 2. Bureau de crédito
        ResultadoConsulta bureau = activities.consultarBureauCredito(request.cpf());
        statusAtual = "BUREAU_CONSULTADO";

        // 3. Verificação PEP (paralelo com fraude)
        ResultadoConsulta pep = activities.verificarPep(request.cpf(), request.nome());
        if (!pep.aprovado()) {
            return finalizar("EM_ANALISE", "PEP: " + pep.motivo());
        }

        // 4. Análise de fraude
        ResultadoConsulta fraude = activities.analisarFraude(
                request.cpf(), request.nome(), request.ip(), request.dispositivo());
        if (!fraude.aprovado()) {
            return finalizar("REJEITADA", "Fraude: " + fraude.motivo());
        }
        statusAtual = "ANALISE_APROVADA";

        // 5. Aguarda documentos KYC (pode levar dias — Temporal mantém o state)
        if (request.documentoFrente() != null) {
            ResultadoConsulta kyc = activities.validarKyc(
                    request.cpf(), request.documentoFrente(), request.documentoVerso(), request.selfie());
            if (!kyc.aprovado()) {
                return finalizar("KYC_REJEITADO", kyc.motivo());
            }
            statusAtual = "KYC_APROVADO";
        } else {
            // Aguarda upload de documentos via signal
            String[] docs = Workflow.getSignalChannel("documentos-kyc").receive();
            ResultadoConsulta kyc = activities.validarKyc(request.cpf(), docs[0], docs[1], docs[2]);
            if (!kyc.aprovado()) {
                return finalizar("KYC_REJEITADO", kyc.motivo());
            }
            statusAtual = "KYC_APROVADO";
        }

        // 6. Criação de cliente e conta
        ResultadoConsulta conta = activities.criarClienteConta(
                request.cpf(), request.nome(), request.email(), request.telefone());
        if (!conta.aprovado()) {
            return finalizar("ERRO", "Conta: " + conta.motivo());
        }
        statusAtual = "CONTA_CRIADA";

        // 7. Evento final
        activities.publicarEvento("customer.cliente.criado.v1", request.cpf(),
                "{\"clienteId\":\"" + conta.score() + "\",\"tipo\":\"PF\"}");

        return finalizar("CONCLUIDA", "Cliente " + conta.score() + " criado");
    }

    @Override
    public String obterStatus() {
        return statusAtual;
    }

    private String finalizar(String novoStatus, String motivo) {
        statusAtual = novoStatus;
        log.info("Workflow Onboarding PF finalizado: status={}, motivo={}", novoStatus, motivo);
        return motivo;
    }
}
