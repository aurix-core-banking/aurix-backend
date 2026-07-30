package com.aurix.platform.banking.core.integration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface BoletoProvider {

    Optional<BoletoInfo> gerarBoletoReal(String beneficiarioDocumento, String beneficiarioNome,
            BigDecimal valor, LocalDate vencimento,
            String pagadorDocumento, String pagadorNome,
            String descricao);

    Optional<BoletoStatus> consultarStatus(String numeroBoleto);

    interface BoletoInfo {
        String getNumeroBoleto();

        String getLinhaDigitavel();

        String getCodigoBarras();

        String getPdfUrl();
    }

    interface BoletoStatus {
        String getNumeroBoleto();

        boolean isPago();

        String getCodigoRetorno();
    }
}
