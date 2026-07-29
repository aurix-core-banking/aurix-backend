package com.aurix.platform.banking.core.integration;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Component
public class BoletoProviderStub implements BoletoProvider {

    @Override
    public Optional<BoletoInfo> gerarBoletoReal(String beneficiarioDocumento, String beneficiarioNome,
            BigDecimal valor, LocalDate vencimento,
            String pagadorDocumento, String pagadorNome,
            String descricao) {
        String numero = "STUB-"
                + UUID.randomUUID().toString().replace("-", "").substring(0, 20).toUpperCase(Locale.ROOT);
        String linha = gerarLinhaDigitavelFake(numero);
        return Optional.of(new BoletoInfo() {
            @Override
            public String getNumeroBoleto() {
                return numero;
            }

            @Override
            public String getLinhaDigitavel() {
                return linha;
            }

            @Override
            public String getCodigoBarras() {
                return numero;
            }

            @Override
            public String getPdfUrl() {
                return null;
            }
        });
    }

    @Override
    public Optional<BoletoStatus> consultarStatus(String numeroBoleto) {
        return Optional.of(new BoletoStatus() {
            @Override
            public String getNumeroBoleto() {
                return numeroBoleto;
            }

            @Override
            public boolean isPago() {
                return false;
            }

            @Override
            public String getCodigoRetorno() {
                return "00";
            }
        });
    }

    private static String gerarLinhaDigitavelFake(String base) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 47 && sb.length() < 47; i++) {
            sb.append(base.charAt(i % base.length()));
        }
        while (sb.length() < 47)
            sb.append("0");
        return sb.substring(0, 47);
    }
}
