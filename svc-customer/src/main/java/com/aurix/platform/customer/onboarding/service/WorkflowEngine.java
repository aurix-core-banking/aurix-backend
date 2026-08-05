package com.aurix.platform.customer.onboarding.service;

import java.util.List;

public interface WorkflowEngine {
    String getTipo();
    List<String> getTransicoesValidas(String statusAtual, String novoStatus);
    boolean transicaoValida(String statusAtual, String novoStatus);
    String getStatusInicial();
}
