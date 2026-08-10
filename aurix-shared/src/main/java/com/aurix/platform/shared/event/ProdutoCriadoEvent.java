package com.aurix.platform.shared.event;

import java.time.LocalDateTime;

public class ProdutoCriadoEvent extends BaseEvent {
    private Long produtoId;
    private String codigo;
    private String nome;
    private String tipoProduto;
    private Integer numeroVersao;

    public static ProdutoCriadoEvent criado(Long produtoId, String codigo, String nome, String tipoProduto, Integer numeroVersao) {
        ProdutoCriadoEvent event = new ProdutoCriadoEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("PRODUTO_CRIADO");
        event.setSource("aurix-products");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.produtoId = produtoId;
        event.codigo = codigo;
        event.nome = nome;
        event.tipoProduto = tipoProduto;
        event.numeroVersao = numeroVersao;
        return event;
    }

    public ProdutoCriadoEvent() {
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public Integer getNumeroVersao() {
        return numeroVersao;
    }

    public void setNumeroVersao(Integer numeroVersao) {
        this.numeroVersao = numeroVersao;
    }
}
