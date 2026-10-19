package com.aurix.platform.credit.consignado.provider;

import com.aurix.platform.credit.consignado.entity.MargemConsignavel;

public interface MargemProvider {

    String FONTE_INSS = "INSS";
    String FONTE_SIAFI = "SIAFI";
    String FONTE_EMPRESA = "EMPRESA";
    String FONTE_SRCC = "SRCC";

    MargemConsignavel consultarMargem(Long clienteId, String cpfCnpj, String fonteMargem);
}