package com.aurix.platform.credit.credit.integration;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Stub de bureau de crédito (scores aleatórios). Desabilitado em produção
 * (perfil "producao") porque não há ainda integração real com um bureau —
 * sem este stub e sem substituto real, a aplicação falha ao subir em vez de
 * tomar decisões de crédito reais com dados fictícios.
 */
@Component
@Profile("!producao")
public class CreditBureauStub implements CreditBureauService {

    @Override
    public Optional<BureauScore> consultarScore(String cpf) {
        int score = ThreadLocalRandom.current().nextInt(200, 901);
        String desc = score >= 700 ? "Baixo" : score >= 500 ? "Medio" : "Alto";
        return Optional.of(new BureauScore() {
            @Override
            public String getCpf() { return cpf; }
            @Override
            public int getScore() { return score; }
            @Override
            public String getDescricaoRisco() { return desc; }
        });
    }

    @Override
    public Optional<BureauRelatorio> consultarRelatorio(String cpf) {
        return Optional.of(new BureauRelatorio() {
            @Override
            public String getCpf() { return cpf; }
            @Override
            public String getConteudoRelatorio() { return "Relatorio stub para " + cpf; }
            @Override
            public String getCodigoRetorno() { return "00"; }
        });
    }

    @Override
    public ScoreCNPJResult consultarScoreCNPJ(String cnpj) {
        if (cnpj == null || cnpj.replaceAll("\\D", "").length() != 14) {
            return ScoreCNPJResult.erro(cnpj, "CNPJ invalido");
        }
        return ScoreCNPJResult.ok(750, BigDecimal.valueOf(500_000), "MEDIO");
    }
}
