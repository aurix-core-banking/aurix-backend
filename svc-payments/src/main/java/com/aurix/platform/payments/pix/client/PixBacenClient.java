package com.aurix.platform.payments.pix.client;

import com.aurix.platform.payments.pix.client.dto.SpiResult;
import com.aurix.platform.payments.pix.client.dto.TransacaoSPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PixBacenClient {

    private final RestClient restClient;

    public PixBacenClient(@Value("${aurix.pix.bacen.spi-url}") String spiUrl) {
        this.restClient = RestClient.builder().baseUrl(spiUrl).build();
    }

    public SpiResult enviarPix(TransacaoSPI transacao) {
        return restClient.post()
            .uri("/api/spi-str/spi/pix")
            .body(transacao)
            .retrieve()
            .body(SpiResult.class);
    }
}
