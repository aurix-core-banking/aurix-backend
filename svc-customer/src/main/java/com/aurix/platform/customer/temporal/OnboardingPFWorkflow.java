package com.aurix.platform.customer.temporal;

import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;

@WorkflowInterface
public interface OnboardingPFWorkflow {

    @WorkflowMethod
    String executar(OnboardingPFRequest request);

    @QueryMethod
    String obterStatus();

    @SignalMethod
    void receberDocumentosKyc(String documentoFrente, String documentoVerso, String selfie);

    record OnboardingPFRequest(
            String cpf,
            String nome,
            String dataNascimento,
            String email,
            String telefone,
            String ip,
            String dispositivo,
            String documentoFrente,
            String documentoVerso,
            String selfie
    ) {}
}
