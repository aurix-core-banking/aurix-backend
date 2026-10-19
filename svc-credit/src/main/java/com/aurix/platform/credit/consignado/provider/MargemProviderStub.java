package com.aurix.platform.credit.consignado.provider;

import com.aurix.platform.credit.consignado.entity.MargemConsignavel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev", "test", "stub"})
public class MargemProviderStub implements MargemProvider {

    private static final Logger log = LoggerFactory.getLogger(MargemProviderStub.class);

    @Override
    public MargemConsignavel consultarMargem(Long clienteId, String cpfCnpj, String fonteMargem) {
        log.info("[stub] Consultando margem {} para clienteId={}, cpf={}", fonteMargem, clienteId, cpfCnpj);
        BigDecimal total = new BigDecimal("5000.00");
        BigDecimal utilizada = new BigDecimal("1500.00");
        return new MargemConsignavel(clienteId, fonteMargem, total,
            total.subtract(utilizada), utilizada, LocalDateTime.now(), "DEFAULT");
    }
}