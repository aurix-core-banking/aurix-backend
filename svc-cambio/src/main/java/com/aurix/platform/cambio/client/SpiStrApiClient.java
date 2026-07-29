package com.aurix.platform.cambio.client;

import com.aurix.platform.cambio.entity.TransacaoSPI;
import com.aurix.platform.cambio.entity.TransacaoSTR;
import com.aurix.platform.cambio.service.SpiStrIntegrationService;

public interface SpiStrApiClient {

    SpiStrIntegrationService.SpiResult enviarPixSpi(TransacaoSPI transacao);

    SpiStrIntegrationService.StrResult enviarTedStr(TransacaoSTR transacao);

    SpiStrIntegrationService.StrResult enviarDocStr(TransacaoSTR transacao);
}
