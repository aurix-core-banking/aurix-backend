package com.aurix.platform.credit.temporal;

import com.aurix.platform.credit.temporal.activity.EmprestimoConsignadoActivities;
import com.aurix.platform.credit.temporal.activity.EmprestimoConsignadoActivities.ResultadoAnalise;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.*;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;

public class EmprestimoConsignadoWorkflowImpl implements EmprestimoConsignadoWorkflow {

    private static final Logger log = Workflow.getLogger(EmprestimoConsignadoWorkflowImpl.class);

    private final EmprestimoConsignadoActivities activities = Workflow.newActivityStub(
            EmprestimoConsignadoActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofMinutes(15))
                    .setRetryOptions(RetryOptions.newBuilder()
                            .setMaximumAttempts(3)
                            .setInitialInterval(Duration.ofSeconds(2))
                            .build())
                    .build());

    private String statusAtual = "PROPOSTA";

    @Override
    public String executar(EmprestimoRequest request) {
        log.info("Iniciando workflow Empréstimo Consignado: cliente={}, valor={}",
                request.clienteId(), request.valor());
        Workflow.upsertSearchAttributes(Map.of("clienteId", request.clienteId()));

        // 1. Análise de crédito
        ResultadoAnalise credito = activities.analisarCredito(request.clienteId(), request.valor());
        if (!credito.aprovado()) {
            return finalizar("REJEITADO", "Crédito: " + credito.motivo());
        }
        statusAtual = "CREDITO_APROVADO";

        // 2. Validação de margem consignada
        ResultadoAnalise margem = activities.validarMargem(request.clienteId(), request.valor());
        if (!margem.aprovado()) {
            return finalizar("REJEITADO", "Margem: " + margem.motivo());
        }
        statusAtual = "MARGEM_VALIDADA";

        // 3. Verificação de garantia (consignado = desconto em folha)
        ResultadoAnalise garantia = activities.verificarGarantia(request.emprestimoId(), "CONSIGNADO");
        if (!garantia.aprovado()) {
            return finalizar("REJEITADO", "Garantia: " + garantia.motivo());
        }
        statusAtual = "GARANTIA_VALIDADA";

        // 4. Criação do contrato
        ResultadoAnalise contrato = activities.criarContrato(
                request.clienteId(), request.valor(), request.prazoMeses(),
                request.taxaJuros(), request.convenioId());
        if (!contrato.aprovado()) {
            return finalizar("ERRO", "Contrato: " + contrato.motivo());
        }
        statusAtual = "CONTRATO_CRIADO";

        // 5. Geração de parcelas
        ResultadoAnalise parcelas = activities.gerarParcelas(
                contrato.contratoId(), request.valor(), request.prazoMeses());
        if (!parcelas.aprovado()) {
            return finalizar("ERRO", "Parcelas: " + parcelas.motivo());
        }
        statusAtual = "PARCELAS_GERADAS";

        // 6. Publica evento de contrato assinado
        activities.publicarEvento("core.emprestimo.concedido.v1", request.clienteId(),
                "{\"emprestimoId\":\"" + contrato.contratoId() +
                "\",\"valor\":\"" + request.valor() +
                "\",\"prazoMeses\":" + request.prazoMeses() + "}");

        statusAtual = "ATIVO";
        log.info("Workflow Empréstimo Consignado concluído: contrato={}", contrato.contratoId());
        return "Contrato " + contrato.contratoId() + " ativo";
    }

    @Override
    public String obterStatus() {
        return statusAtual;
    }

    private String finalizar(String novoStatus, String motivo) {
        statusAtual = novoStatus;
        log.info("Workflow Empréstimo finalizado: status={}, motivo={}", novoStatus, motivo);
        return motivo;
    }
}
