package com.aurix.platform.cards.dto;

import com.aurix.platform.cards.entity.LancamentoFatura;
import java.util.List;

public class FaturaDetalhadaResponse extends FaturaResponse {

    private List<LancamentoFatura> lancamentos;

    public List<LancamentoFatura> getLancamentos() { return lancamentos; }
    public void setLancamentos(List<LancamentoFatura> lancamentos) { this.lancamentos = lancamentos; }
}
