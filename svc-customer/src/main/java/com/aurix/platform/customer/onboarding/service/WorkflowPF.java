package com.aurix.platform.customer.onboarding.service;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class WorkflowPF implements WorkflowEngine {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WorkflowPF.class);

    @Override
    public String getTipo() {
        return "PF";
    }

    @Override
    public List<String> getTransicoesValidas(String statusAtual, String novoStatus) {
        List<String> destinos = new ArrayList<>();
        switch (statusAtual) {
            case "RECEBIDA":
                destinos.add("DOCUMENTOS_PENDENTES");
                destinos.add("EM_ANALISE_KYC");
                destinos.add("REJEITADA");
                break;
            case "DOCUMENTOS_PENDENTES":
                destinos.add("EM_ANALISE_KYC");
                destinos.add("REJEITADA");
                break;
            case "EM_ANALISE_KYC":
                destinos.add("KYC_APROVADO");
                destinos.add("KYC_REJEITADO");
                break;
            case "KYC_APROVADO":
                destinos.add("APROVADA");
                destinos.add("REJEITADA");
                break;
            case "KYC_REJEITADO":
                destinos.add("EM_ANALISE_KYC");
                destinos.add("REJEITADA");
                break;
            case "REJEITADA":
                break;
            case "APROVADA":
                destinos.add("CONTA_CRIADA");
                break;
            case "CONTA_CRIADA":
                break;
            default:
                break;
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
        return "RECEBIDA";
    }

    @java.lang.SuppressWarnings("all")
    public WorkflowPF() {
    }
}
