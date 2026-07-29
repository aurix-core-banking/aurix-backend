package com.aurix.platform.shared.entity;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ClientePJFieldsTest {

    @Test
    void deveArmazenarCamposFinanceirosPJ() {
        Cliente cliente = new Cliente();
        cliente.setTipoPessoa(Cliente.TipoPessoa.JURIDICA);
        cliente.setCnpj("12345678000190");
        cliente.setNomeRazaoSocial("Empresa Ltda");
        cliente.setFaturamentoMensal(BigDecimal.valueOf(500000));
        cliente.setCapitalSocial(BigDecimal.valueOf(200000));
        cliente.setCnaePrincipal("62.01-3");
        cliente.setPorte("EPP");
        cliente.setDataConstituicao(LocalDate.of(2018, 1, 1));

        assertEquals(BigDecimal.valueOf(500000), cliente.getFaturamentoMensal());
        assertEquals(BigDecimal.valueOf(200000), cliente.getCapitalSocial());
        assertEquals("62.01-3", cliente.getCnaePrincipal());
        assertEquals("EPP", cliente.getPorte());
        assertEquals(LocalDate.of(2018, 1, 1), cliente.getDataConstituicao());
    }
}
