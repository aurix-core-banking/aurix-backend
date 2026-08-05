package com.aurix.platform.platform.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "templates_notificacao", schema = "aurix")
public class TemplateNotificacao extends BaseEntity {
    @Column(name = "codigo", nullable = false, unique = true, length = 100)
    private String codigo;

    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    @Column(name = "canal", nullable = false, length = 30)
    private String canal;

    @Column(name = "assunto", length = 200)
    private String assunto;

    @Column(name = "corpo", nullable = false, length = 4000)
    private String corpo;

    @Column(name = "variaveis", length = 2000)
    private String variaveis;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCanal() { return canal; }
    public void setCanal(String canal) { this.canal = canal; }
    public String getAssunto() { return assunto; }
    public void setAssunto(String assunto) { this.assunto = assunto; }
    public String getCorpo() { return corpo; }
    public void setCorpo(String corpo) { this.corpo = corpo; }
    public String getVariaveis() { return variaveis; }
    public void setVariaveis(String variaveis) { this.variaveis = variaveis; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
