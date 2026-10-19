package com.aurix.platform.credit.consignado.provider;

import com.aurix.platform.credit.consignado.entity.ContratoConsignado;

public interface AverbacaoProvider {

    AverbacaoResultado averbar(ContratoConsignado contrato);

    void baixar(ContratoConsignado contrato);

    record AverbacaoResultado(String protocolo, String status) {}
}