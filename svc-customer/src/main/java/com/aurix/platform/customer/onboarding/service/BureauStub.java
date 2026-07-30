package com.aurix.platform.customer.onboarding.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import org.springframework.core.annotation.Order;

@Service
@Order(3)
@Profile({"dev", "test"})
public class BureauStub implements BureauProvider {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BureauStub.class);

    @Override
    public BureauService.ResultadoBureau consultar(String cpf) {
        log.debug("Bureau stub: consulta CPF {}", cpf);
        return new BureauService.ResultadoBureau(600, "REGULAR", "Consulta simulada - integrar Serasa/SPC/Quod");
    }
}
