package com.aurix.platform.fraud.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "regras_fraude", schema = "aurix")
public class RegraFraude extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(name = "parametros", length = 2000)
    private String parametros;

    @Column(nullable = false)
    private Integer pontuacao;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(nullable = false)
    private Integer prioridade = 0;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getParametros() { return parametros; }
    public void setParametros(String parametros) { this.parametros = parametros; }
    public Integer getPontuacao() { return pontuacao; }
    public void setPontuacao(Integer pontuacao) { this.pontuacao = pontuacao; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
    public Integer getPrioridade() { return prioridade; }
    public void setPrioridade(Integer prioridade) { this.prioridade = prioridade; }
}
