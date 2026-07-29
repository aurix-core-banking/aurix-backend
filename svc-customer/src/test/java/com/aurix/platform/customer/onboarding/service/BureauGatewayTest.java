package com.aurix.platform.customer.onboarding.service;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BureauGatewayTest {

    private record StubProvider(String name, BureauService.ResultadoBureau result, boolean fail) implements BureauProvider {
        @Override
        public BureauService.ResultadoBureau consultar(String cpf) {
            if (fail) throw new RuntimeException(name + " failed");
            return result;
        }
    }

    @Test
    void deveUsarPrimeiroProvedorQuandoDisponivel() {
        BureauService.ResultadoBureau expected = new BureauService.ResultadoBureau(700, "REGULAR", "OK");
        BureauProvider p1 = new StubProvider("p1", expected, false);
        BureauProvider p2 = new StubProvider("p2", null, false);
        BureauGateway gateway = new BureauGateway(List.of(p1, p2));

        BureauService.ResultadoBureau result = gateway.consultar("52998224725");

        assertThat(result.score()).isEqualTo(700);
    }

    @Test
    void deveFazerFallbackQuandoPrimeiroFalha() {
        BureauService.ResultadoBureau expected = new BureauService.ResultadoBureau(500, "REGULAR", "fallback");
        BureauProvider p1 = new StubProvider("p1", null, true);
        BureauProvider p2 = new StubProvider("p2", expected, false);
        BureauGateway gateway = new BureauGateway(List.of(p1, p2));

        BureauService.ResultadoBureau result = gateway.consultar("52998224725");

        assertThat(result.score()).isEqualTo(500);
    }

    @Test
    void deveLancarExcecaoQuandoTodosFalham() {
        BureauProvider p1 = new StubProvider("p1", null, true);
        BureauProvider p2 = new StubProvider("p2", null, true);
        BureauGateway gateway = new BureauGateway(List.of(p1, p2));

        assertThatThrownBy(() -> gateway.consultar("52998224725"))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Todos os provedores");
    }
}
