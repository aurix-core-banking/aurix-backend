package com.aurix.platform.banking.cnab.dto;

import com.aurix.platform.banking.cnab.entity.CnabRemessa.TipoCnab;
import jakarta.validation.constraints.NotNull;

public class CnabRemessaRequest {

    @NotNull(message = "Tipo do CNAB e obrigatorio")
    private TipoCnab tipo;

    private Long[] transferenciaIds;
    private Long[] boletoIds;

    public TipoCnab getTipo() { return tipo; }
    public void setTipo(TipoCnab tipo) { this.tipo = tipo; }
    public Long[] getTransferenciaIds() { return transferenciaIds; }
    public void setTransferenciaIds(Long[] transferenciaIds) { this.transferenciaIds = transferenciaIds; }
    public Long[] getBoletoIds() { return boletoIds; }
    public void setBoletoIds(Long[] boletoIds) { this.boletoIds = boletoIds; }
}
