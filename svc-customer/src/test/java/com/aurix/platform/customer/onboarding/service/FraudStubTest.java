package com.aurix.platform.customer.onboarding.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class FraudStubTest {

    private final FraudStub stub = new FraudStub();

    @Test
    void deveRetornarAprovado() {
        FraudService.ResultadoFraude result = stub.analisar("52998224725", "Maria", "maria@teste.com", "11999999999");
        assertThat(result.aprovado()).isTrue();
        assertThat(result.risco()).isZero();
        assertThat(result.codigo()).isEqualTo("APROVADO_STUB");
    }
}
