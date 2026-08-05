package com.aurix.platform.credit.credit.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreditBureauStubTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(CreditBureauStub.class);

    @Test
    void stubFicaDisponivelForaDoPerfilDeProducao() {
        contextRunner.run(context -> assertEquals(1, context.getBeansOfType(CreditBureauStub.class).size()));
    }

    @Test
    void stubNaoFicaDisponivelNoPerfilDeProducao() {
        // Não há outra implementação de CreditBureauService além deste stub de
        // scores aleatórios — então em produção a aplicação deve falhar ao
        // subir (bean ausente) em vez de tomar decisões de crédito reais com
        // dados fictícios.
        contextRunner
                .withInitializer(ctx -> ctx.getEnvironment().setActiveProfiles("producao"))
                .run(context -> assertEquals(0, context.getBeansOfType(CreditBureauStub.class).size()));
    }
}
