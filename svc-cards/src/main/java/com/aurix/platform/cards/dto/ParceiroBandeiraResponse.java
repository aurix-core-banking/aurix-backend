package com.aurix.platform.cards.dto;

public class ParceiroBandeiraResponse {

    private Long id;
    private String nome;
    private String tipoEndpoint;
    private Boolean ativo;
    private AuditMetaDTO auditoria;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipoEndpoint() { return tipoEndpoint; }
    public void setTipoEndpoint(String tipoEndpoint) { this.tipoEndpoint = tipoEndpoint; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
    public AuditMetaDTO getAuditoria() { return auditoria; }
    public void setAuditoria(AuditMetaDTO auditoria) { this.auditoria = auditoria; }
}
