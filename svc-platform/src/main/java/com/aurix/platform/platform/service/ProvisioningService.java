package com.aurix.platform.platform.service;

import com.aurix.platform.platform.entity.Instituicao;

public interface ProvisioningService {

    ProvisioningResult provisionar(Instituicao instituicao);

    record ProvisioningResult(boolean success, String message, String databaseUrl) {}
}
