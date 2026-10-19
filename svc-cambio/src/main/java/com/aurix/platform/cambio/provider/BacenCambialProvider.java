package com.aurix.platform.cambio.provider;

import com.aurix.platform.cambio.entity.ContratoCambio;

public interface BacenCambialProvider {

    RegistroBacenResultado registrarRdeRof(ContratoCambio contrato, String clienteDoc);

    record RegistroBacenResultado(String protocolo, String status) {}
}