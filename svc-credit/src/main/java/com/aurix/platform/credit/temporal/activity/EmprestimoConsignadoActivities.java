package com.aurix.platform.credit.temporal.activity;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;
import java.math.BigDecimal;

@ActivityInterface
public interface EmprestimoConsignadoActivities {

    @ActivityMethod
    ResultadoAnalise analisarCredito(String clienteId, BigDecimal valorSolicitado);

    @ActivityMethod
    ResultadoAnalise validarMargem(String clienteId, BigDecimal valorSolicitado);

    @ActivityMethod
    ResultadoAnalise verificarGarantia(String emprestimoId, String tipoGarantia);

    @ActivityMethod
    ResultadoAnalise criarContrato(String clienteId, BigDecimal valor, Integer prazoMeses,
                                    BigDecimal taxaJuros, String convenioId);

    @ActivityMethod
    ResultadoAnalise gerarParcelas(String contratoId, BigDecimal valorTotal, Integer parcelas);

    @ActivityMethod
    ResultadoAnalise liquidar(String contratoId);

    @ActivityMethod
    void publicarEvento(String topico, String chave, String payload);

    record ResultadoAnalise(boolean aprovado, String motivo, String contratoId, String detalhes) {}
}
