package com.aurix.platform.seguros.integracao;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Implementação simulada da integração com a seguradora, ativa nos perfis
 * dev/test/stub. Calcula o prêmio heuristicamente e aceita todas as cotações.
 */
@Service
@Profile({"dev", "test", "stub"})
public class SeguradoraStub implements SeguradoraService {

    private static final Logger log = LoggerFactory.getLogger(SeguradoraStub.class);

    private static final BigDecimal FATOR_IDADE_BASE = BigDecimal.valueOf(0.001);
    private static final BigDecimal FATOR_UF = BigDecimal.valueOf(0.05);
    private static final BigDecimal FATOR_SEXO_M = BigDecimal.valueOf(0.02);
    private static final BigDecimal FATOR_PROFISSAO_ALTO_RISCO = BigDecimal.valueOf(0.15);
    private static final BigDecimal TAXA_PADRAO = BigDecimal.valueOf(0.005);

    @Override
    public Cotacao cotar(CotacaoRequestDto req) {
        log.debug("Seguradora stub: cotação produto={}, valorSegurado={}", req.produto(), req.valorSegurado());

        var idade = req.idade() != null ? req.idade() : 30;
        var fatorIdade = FATOR_IDADE_BASE.multiply(BigDecimal.valueOf(idade));
        var fatorUf = "SP".equals(req.uf()) || "RJ".equals(req.uf()) ? FATOR_UF : BigDecimal.ZERO;
        var fatorSexo = "M".equals(req.sexo()) ? FATOR_SEXO_M : BigDecimal.ZERO;
        var fatorProfissao = req.profissao() != null && req.profissao().contains("ALTORISCO")
            ? FATOR_PROFISSAO_ALTO_RISCO : BigDecimal.ZERO;

        var fatorTotal = BigDecimal.ONE
            .add(fatorIdade)
            .add(fatorUf)
            .add(fatorSexo)
            .add(fatorProfissao);

        var premio = req.valorSegurado().multiply(TAXA_PADRAO).multiply(fatorTotal)
            .setScale(2, java.math.RoundingMode.HALF_UP);

        return new Cotacao(premio, TAXA_PADRAO, "SEGURADORA_STUB", true, "Cotação simulada - integrar API de mercado");
    }

    @Override
    public RegistroApolice registrarApolice(RegistroApoliceDto req) {
        log.debug("Seguradora stub: registro apólice {}", req.numeroApolice());
        return new RegistroApolice("PROTOCOL-" + req.numeroApolice(), "REGISTRADA", req.numeroApolice());
    }
}
