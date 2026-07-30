package com.aurix.platform.shared.exception;

import java.math.BigDecimal;

/**
 * Exceção lançada quando não há saldo suficiente para uma operação
 */
public class SaldoInsuficienteException extends AurixException {
    
    public SaldoInsuficienteException(String numeroConta, BigDecimal saldoAtual, BigDecimal valorSolicitado) {
        super("SALDO_INSUFICIENTE", 
              String.format("Saldo insuficiente na conta %s. Saldo atual: R$ %.2f, Valor solicitado: R$ %.2f", 
                           numeroConta, saldoAtual, valorSolicitado));
    }
    
    public SaldoInsuficienteException(Long contaId, BigDecimal saldoAtual, BigDecimal valorSolicitado) {
        super("SALDO_INSUFICIENTE",
              String.format("Saldo insuficiente na conta ID %d. Saldo atual: R$ %.2f, Valor solicitado: R$ %.2f",
                           contaId, saldoAtual, valorSolicitado));
    }

    /**
     * Construtor para uso após uma atualização atômica condicional (UPDATE ... WHERE saldo >= :valor)
     * que não afetou nenhuma linha — nesse caso o saldo atual não é lido pela aplicação.
     */
    public SaldoInsuficienteException(Long contaId, BigDecimal valorSolicitado) {
        super("SALDO_INSUFICIENTE",
              String.format("Saldo insuficiente na conta ID %d para débito de R$ %.2f",
                           contaId, valorSolicitado));
    }
}
