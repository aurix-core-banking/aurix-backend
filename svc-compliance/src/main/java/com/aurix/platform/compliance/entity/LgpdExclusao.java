package com.aurix.platform.compliance.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "lgpd_exclusao", schema = "aurix")
public class LgpdExclusao extends BaseEntity {

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "cpf_anonimizado", length = 14)
    private String cpfAnonimizado;

    @Column(name = "cnpj_anonimizado", length = 18)
    private String cnpjAnonimizado;

    @Column(name = "data_exclusao", nullable = false)
    private LocalDateTime dataExclusao;

    @Column(name = "motivo", length = 500)
    private String motivo;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getCpfAnonimizado() {
        return cpfAnonimizado;
    }

    public void setCpfAnonimizado(String cpfAnonimizado) {
        this.cpfAnonimizado = cpfAnonimizado;
    }

    public String getCnpjAnonimizado() {
        return cnpjAnonimizado;
    }

    public void setCnpjAnonimizado(String cnpjAnonimizado) {
        this.cnpjAnonimizado = cnpjAnonimizado;
    }

    public LocalDateTime getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(LocalDateTime dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}