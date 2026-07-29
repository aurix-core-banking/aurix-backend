package com.aurix.platform.payments.pix.client.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpiResult {
    private boolean sucesso;
    private String endToEndId;
    private String status;
    private String dataHoraLiquidacao;
    private String ispbDestino;
    private String mensagem;
}
