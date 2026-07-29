package com.aurix.platform.customer.onboarding.service;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class WorkflowPJ implements WorkflowEngine {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WorkflowPJ.class);

    @Override
    public String getTipo() {
        return "PJ";
    }

    @Override
    public List<String> getTransicoesValidas(String statusAtual, String novoStatus) {
        List<String> destinos = new ArrayList<>();
        switch (statusAtual) {
            case "EM_PREENCHIMENTO":
                destinos.add("CNPJ_CONSULTADO");
                break;
            case "CNPJ_CONSULTADO":
                destinos.add("SOCIOS_VALIDADOS");
                break;
            case "SOCIOS_VALIDADOS":
                destinos.add("DOCUMENTOS_ANALISADOS");
                break;
            case "DOCUMENTOS_ANALISADOS":
                destinos.add("AML_APROVADO");
                break;
            case "AML_APROVADO":
                destinos.add("COMPLIANCE_APROVADO");
                break;
            case "COMPLIANCE_APROVADO":
                destinos.add("EM_ASSINATURA");
                break;
            case "EM_ASSINATURA":
                destinos.add("CONTRATO_ASSINADO");
                break;
            case "CONTRATO_ASSINADO":
                destinos.add("CONTA_CRIADA");
                break;
            case "CONTA_CRIADA":
            case "REJEITADA":
                break;
            default:
                break;
        }
        if (!statusAtual.equals("REJEITADA") && !statusAtual.equals("CONTA_CRIADA")) {
            destinos.add("REJEITADA");
        }
        if (novoStatus == null) {
            return destinos;
        }
        return destinos.stream().filter(d -> d.equals(novoStatus)).toList();
    }

    @Override
    public boolean transicaoValida(String statusAtual, String novoStatus) {
        return !getTransicoesValidas(statusAtual, novoStatus).isEmpty();
    }

    @Override
    public String getStatusInicial() {
        return "EM_PREENCHIMENTO";
    }

    @java.lang.SuppressWarnings("all")
    public WorkflowPJ() {
    }
}
