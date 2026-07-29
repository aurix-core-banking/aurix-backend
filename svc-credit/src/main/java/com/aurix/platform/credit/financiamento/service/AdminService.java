package com.aurix.platform.credit.financiamento.service;

import com.aurix.platform.credit.financiamento.dto.request.AtualizarTaxaRequest;
import com.aurix.platform.credit.financiamento.dto.response.TaxasResponse;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    private final BigDecimal taxaSacPadrao;
    private final BigDecimal taxaPricePadrao;
    private final BigDecimal taxaSacrePadrao;
    private final BigDecimal cetTaxa;

    public AdminService(@Value("${aurix.financiamento.taxa-sac:0.0099}") BigDecimal taxaSacPadrao,
                        @Value("${aurix.financiamento.taxa-price:0.0112}") BigDecimal taxaPricePadrao,
                        @Value("${aurix.financiamento.taxa-sacre:0.0105}") BigDecimal taxaSacrePadrao,
                        @Value("${aurix.financiamento.cet-taxa:0.0025}") BigDecimal cetTaxa) {
        this.taxaSacPadrao = taxaSacPadrao;
        this.taxaPricePadrao = taxaPricePadrao;
        this.taxaSacrePadrao = taxaSacrePadrao;
        this.cetTaxa = cetTaxa;
    }

    public TaxasResponse listarTaxas() {
        return new TaxasResponse(taxaSacPadrao, taxaPricePadrao, taxaSacrePadrao, cetTaxa);
    }

    public TaxasResponse atualizarTaxas(AtualizarTaxaRequest request) {
        log.info("Atualização de taxa solicitada: sistema={}, novaTaxa={}",
            request.getSistemaAmortizacao(), request.getTaxa());
        return new TaxasResponse(taxaSacPadrao, taxaPricePadrao, taxaSacrePadrao, cetTaxa);
    }
}
