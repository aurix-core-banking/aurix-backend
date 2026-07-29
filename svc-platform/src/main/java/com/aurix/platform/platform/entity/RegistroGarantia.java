package com.aurix.platform.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "registros_garantia", schema = "aurix")
public class RegistroGarantia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long garantiaId;
    private String orgao; // CARTORIO/DETRAN/JUCESP
    private LocalDateTime dataRegistro;
    private String protocolo;
    private String status; // PENDENTE/REGISTRADO/REJEITADO

    public RegistroGarantia() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getGarantiaId() { return garantiaId; }
    public void setGarantiaId(Long v) { this.garantiaId = v; }
    public String getOrgao() { return orgao; }
    public void setOrgao(String v) { this.orgao = v; }
    public LocalDateTime getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDateTime v) { this.dataRegistro = v; }
    public String getProtocolo() { return protocolo; }
    public void setProtocolo(String v) { this.protocolo = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
}
