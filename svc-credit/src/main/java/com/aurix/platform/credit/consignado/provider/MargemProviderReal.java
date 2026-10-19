package com.aurix.platform.credit.consignado.provider;

import com.aurix.platform.credit.consignado.client.DataprevClient;
import com.aurix.platform.credit.consignado.client.ESocialClient;
import com.aurix.platform.credit.consignado.client.SiafiClient;
import com.aurix.platform.credit.consignado.client.SrccClient;
import com.aurix.platform.credit.consignado.entity.MargemConsignavel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("producao")
public class MargemProviderReal implements MargemProvider {

    private static final Logger log = LoggerFactory.getLogger(MargemProviderReal.class);

    private final DataprevClient dataprevClient;
    private final SiafiClient siafiClient;
    private final ESocialClient eSocialClient;
    private final SrccClient srccClient;

    public MargemProviderReal(DataprevClient dataprevClient,
                              SiafiClient siafiClient,
                              ESocialClient eSocialClient,
                              SrccClient srccClient) {
        this.dataprevClient = dataprevClient;
        this.siafiClient = siafiClient;
        this.eSocialClient = eSocialClient;
        this.srccClient = srccClient;
    }

    @Override
    public MargemConsignavel consultarMargem(Long clienteId, String cpfCnpj, String fonteMargem) {
        if (cpfCnpj == null || cpfCnpj.isBlank()) {
            throw new IllegalArgumentException("CPF/CNPJ obrigatório para consulta de margem na fonte " + fonteMargem);
        }
        BigDecimal total;
        BigDecimal utilizada;
        switch (fonteMargem) {
            case FONTE_INSS -> {
                DataprevClient.MargemInssResponse r = dataprevClient.consultarMargemInss(cpfCnpj);
                total = r.margemTotal();
                utilizada = r.margemUtilizada();
                log.info("Margem INSS obtida para cpf={}: total={}, utilizada={}", cpfCnpj, total, utilizada);
            }
            case FONTE_SIAFI -> {
                SiafiClient.MargemServidorResponse r = siafiClient.consultarMargemServidor(cpfCnpj);
                total = r.margemTotal();
                utilizada = r.margemUtilizada();
                log.info("Margem SIAPE obtida para cpf={}, orgao={}", cpfCnpj, r.orgao());
            }
            case FONTE_EMPRESA -> {
                ESocialClient.MargemEmpresaResponse r = eSocialClient.consultarMargemEmpresa(cpfCnpj);
                total = r.margemTotal();
                utilizada = r.margemUtilizada();
                log.info("Margem eSocial/empresa obtida para cpf={}, empresa={}", cpfCnpj, r.empresa());
            }
            default -> {
                SrccClient.MargemResponse r = srccClient.consultarMargem(cpfCnpj);
                total = r.margemTotal();
                utilizada = r.margemUtilizada();
                log.info("Margem SRCC obtida para cpf={}, fonte={}", cpfCnpj, r.fonte());
            }
        }
        return new MargemConsignavel(clienteId, fonteMargem, total,
            total.subtract(utilizada), utilizada, LocalDateTime.now(), "DEFAULT");
    }
}