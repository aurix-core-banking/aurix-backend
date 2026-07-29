package com.aurix.platform.cards.dto;

import java.time.LocalDateTime;

public class AuditMetaDTO {

    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}
