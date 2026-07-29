package com.aurix.platform.payments.pix.client.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class TransacaoSPI {
    private String endToEndId;
    private String ispbOrigem;
    private String ispbDestino;
    private BigDecimal valor;
    private String chavePixDestino;
}
