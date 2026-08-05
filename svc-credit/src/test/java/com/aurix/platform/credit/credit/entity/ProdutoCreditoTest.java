package com.aurix.platform.credit.credit.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProdutoCreditoTest {

    @Test
    void deveConterLimiteRotativoNoTipoCredito() {
        assertNotNull(ProdutoCredito.TipoCredito.valueOf("LIMITE_ROTATIVO"));
    }

    @Test
    void deveManterEnumsExistentes() {
        assertNotNull(ProdutoCredito.TipoCredito.valueOf("PESSOAL"));
        assertNotNull(ProdutoCredito.TipoCredito.valueOf("CONSIGNADO"));
        assertNotNull(ProdutoCredito.TipoCredito.valueOf("CDC"));
        assertNotNull(ProdutoCredito.TipoCredito.valueOf("VEICULOS"));
        assertNotNull(ProdutoCredito.TipoCredito.valueOf("IMOBILIARIO"));
        assertNotNull(ProdutoCredito.TipoCredito.valueOf("CAPITAL_GIRO"));
        assertNotNull(ProdutoCredito.TipoCredito.valueOf("OUTROS"));
    }
}
