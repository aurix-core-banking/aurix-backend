package com.aurix.platform.products.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "versoes_produto", schema = "aurix")
public class VersaoProduto extends BaseEntity {

    public enum StatusVersao {
        RASCUNHO, PUBLICADA, SUBSTITUIDA
    }

    @Column(name = "produto_id", nullable = false)
    private Long produtoId;

    @Column(name = "numero_versao", nullable = false)
    private Integer numeroVersao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusVersao status = StatusVersao.RASCUNHO;

    @Column(length = 100)
    private String autor;

    @Column(length = 500)
    private String changelog;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_json", columnDefinition = "JSONB")
    private String dadosJson;

    @Column(name = "data_versao", nullable = false)
    private LocalDateTime dataVersao = LocalDateTime.now();

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getNumeroVersao() {
        return numeroVersao;
    }

    public void setNumeroVersao(Integer numeroVersao) {
        this.numeroVersao = numeroVersao;
    }

    public StatusVersao getStatus() {
        return status;
    }

    public void setStatus(StatusVersao status) {
        this.status = status;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public String getDadosJson() {
        return dadosJson;
    }

    public void setDadosJson(String dadosJson) {
        this.dadosJson = dadosJson;
    }

    public LocalDateTime getDataVersao() {
        return dataVersao;
    }

    public void setDataVersao(LocalDateTime dataVersao) {
        this.dataVersao = dataVersao;
    }
}
