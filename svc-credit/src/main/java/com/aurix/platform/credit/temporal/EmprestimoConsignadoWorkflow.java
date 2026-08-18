package com.aurix.platform.credit.temporal;

import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.WorkflowInterface;

import java.math.BigDecimal;

@WorkflowInterface
public interface EmprestimoConsignadoWorkflow {

    @WorkflowMethod
    String executar(EmprestimoRequest request);

    @QueryMethod
    String obterStatus();

    record EmprestimoRequest(
            String emprestimoId,
            String clienteId,
            BigDecimal valor,
            Integer prazoMeses,
            BigDecimal taxaJuros,
            String convenioId
    ) {}
}
