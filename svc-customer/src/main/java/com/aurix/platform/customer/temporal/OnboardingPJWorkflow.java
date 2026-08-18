package com.aurix.platform.customer.temporal;

import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;

import java.math.BigDecimal;

@WorkflowInterface
public interface OnboardingPJWorkflow {

    @WorkflowMethod
    String executar(OnboardingPJRequest request);

    @QueryMethod
    String obterStatus();

    @SignalMethod
    void confirmarAssinatura();

    record OnboardingPJRequest(
            String cnpj,
            String razaoSocial,
            String email,
            String telefone,
            String sociosJson,
            String documentosJson,
            BigDecimal faturamento
    ) {}
}
