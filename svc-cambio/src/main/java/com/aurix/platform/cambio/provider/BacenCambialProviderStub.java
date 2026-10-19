package com.aurix.platform.cambio.provider;

import com.aurix.platform.cambio.entity.ContratoCambio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev", "test", "stub"})
public class BacenCambialProviderStub implements BacenCambialProvider {

    private static final Logger log = LoggerFactory.getLogger(BacenCambialProviderStub.class);

    @Override
    public RegistroBacenResultado registrarRdeRof(ContratoCambio contrato, String clienteDoc) {
        log.info("[stub] Registro RDE-ROF (SISBACEN) para contrato {}", contrato.getId());
        return new RegistroBacenResultado("RDE-" + contrato.getId(), "REGISTRADO");
    }
}