package com.aurix.platform.credit.credit.integration;

import java.math.BigDecimal;
import java.util.Optional;

public interface CreditBureauService {

    Optional<BureauScore> consultarScore(String cpf);

    Optional<BureauRelatorio> consultarRelatorio(String cpf);

    ScoreCNPJResult consultarScoreCNPJ(String cnpj);

    interface BureauScore {
        String getCpf();
        int getScore();
        String getDescricaoRisco();
    }

    interface BureauRelatorio {
        String getCpf();
        String getConteudoRelatorio();
        String getCodigoRetorno();
    }

    record ScoreCNPJResult(int score, BigDecimal faturamentoEstimado,
        String risco, String mensagem) {
        public static ScoreCNPJResult ok(int score, BigDecimal faturamento, String risco) {
            return new ScoreCNPJResult(score, faturamento, risco, null);
        }
        public static ScoreCNPJResult erro(String cnpj, String erro) {
            return new ScoreCNPJResult(0, BigDecimal.ZERO, "INDEFINIDO", erro);
        }
    }
}
