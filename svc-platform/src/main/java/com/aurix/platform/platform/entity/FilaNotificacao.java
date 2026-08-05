package com.aurix.platform.platform.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "fila_notificacoes", schema = "aurix")
public class FilaNotificacao extends BaseEntity {
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "template_codigo", nullable = false, length = 100)
    private String templateCodigo;

    @Column(name = "canal", nullable = false, length = 30)
    private String canal;

    @Column(name = "destinatario", nullable = false, length = 200)
    private String destinatario;

    @Column(name = "assunto", length = 200)
    private String assunto;

    @Column(name = "corpo_renderizado", nullable = false, length = 4000)
    private String corpoRenderizado;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "tentativas")
    private Integer tentativas = 0;

    @Column(name = "max_tentativas")
    private Integer maxTentativas = 3;

    @Column(name = "agendada_para")
    private LocalDateTime agendadaPara;

    @Column(name = "enviada_em")
    private LocalDateTime enviadaEm;

    @Column(name = "falha_motivo", length = 500)
    private String falhaMotivo;

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getTemplateCodigo() { return templateCodigo; }
    public void setTemplateCodigo(String templateCodigo) { this.templateCodigo = templateCodigo; }
    public String getCanal() { return canal; }
    public void setCanal(String canal) { this.canal = canal; }
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public String getAssunto() { return assunto; }
    public void setAssunto(String assunto) { this.assunto = assunto; }
    public String getCorpoRenderizado() { return corpoRenderizado; }
    public void setCorpoRenderizado(String corpoRenderizado) { this.corpoRenderizado = corpoRenderizado; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getTentativas() { return tentativas; }
    public void setTentativas(Integer tentativas) { this.tentativas = tentativas; }
    public Integer getMaxTentativas() { return maxTentativas; }
    public void setMaxTentativas(Integer maxTentativas) { this.maxTentativas = maxTentativas; }
    public LocalDateTime getAgendadaPara() { return agendadaPara; }
    public void setAgendadaPara(LocalDateTime agendadaPara) { this.agendadaPara = agendadaPara; }
    public LocalDateTime getEnviadaEm() { return enviadaEm; }
    public void setEnviadaEm(LocalDateTime enviadaEm) { this.enviadaEm = enviadaEm; }
    public String getFalhaMotivo() { return falhaMotivo; }
    public void setFalhaMotivo(String falhaMotivo) { this.falhaMotivo = falhaMotivo; }
}
