package com.aurix.platform.platform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditoriaConfig {

    @Value("${aurix.audit.retencao-anos:5}")
    private int retencaoAnos;

    @Value("${aurix.audit.log-imutavel:true}")
    private boolean logImutavel;

    public int getRetencaoAnos() {
        return retencaoAnos;
    }

    public boolean isLogImutavel() {
        return logImutavel;
    }
}
