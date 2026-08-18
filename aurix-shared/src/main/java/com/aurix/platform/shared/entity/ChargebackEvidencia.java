package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Entidade Chargeback Evidência do Aurix.
 * Armazena evidências documentais anexas a um processo de chargeback.
 */
@Entity
@Table(name = "chargeback_evidencias", schema = "aurix")
public class ChargebackEvidencia extends BaseEntity {
    private static final int TIPO_LENGTH = 30;
    private static final int DESC_LENGTH = 255;
    private static final int URL_LENGTH = 500;

    @NotNull(message = "Chargeback é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chargeback_id", nullable = false)
    private Chargeback chargeback;

    @NotBlank(message = "Tipo de evidência é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_evidencia", nullable = false, length = TIPO_LENGTH)
    private TipoEvidencia tipoEvidencia;

    @Size(max = DESC_LENGTH, message = "Descrição deve ter no máximo 255 caracteres")
    @Column(name = "descricao", length = DESC_LENGTH)
    private String descricao;

    @Size(max = URL_LENGTH, message = "URL do documento deve ter no máximo 500 caracteres")
    @Column(name = "url_documento", length = URL_LENGTH)
    private String urlDocumento;

    @Column(name = "conteudo_texto", columnDefinition = "TEXT")
    private String conteudoTexto;

    @Column(name = "data_upload", nullable = false)
    private LocalDateTime dataUpload = LocalDateTime.now();

    /**
     * Tipo de evidência documental.
     */
    public enum TipoEvidencia {
        COMPROVANTE("Comprovante de pagamento"),
        CORRESPONDENCIA("Correspondência bancária"),
        PRINT_TELA("Print de tela"),
        NOTA_FISCAL("Nota fiscal"),
        CONTRATO("Contrato"),
        OUTROS("Outros");

        private final String descricao;

        TipoEvidencia(final String desc) {
            this.descricao = desc;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    @java.lang.SuppressWarnings("all")
    public Chargeback getChargeback() {
        return this.chargeback;
    }

    @java.lang.SuppressWarnings("all")
    public TipoEvidencia getTipoEvidencia() {
        return this.tipoEvidencia;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public String getUrlDocumento() {
        return this.urlDocumento;
    }

    @java.lang.SuppressWarnings("all")
    public String getConteudoTexto() {
        return this.conteudoTexto;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataUpload() {
        return this.dataUpload;
    }

    @java.lang.SuppressWarnings("all")
    public void setChargeback(final Chargeback chargeback) {
        this.chargeback = chargeback;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoEvidencia(final TipoEvidencia tipoEvidencia) {
        this.tipoEvidencia = tipoEvidencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setUrlDocumento(final String urlDocumento) {
        this.urlDocumento = urlDocumento;
    }

    @java.lang.SuppressWarnings("all")
    public void setConteudoTexto(final String conteudoTexto) {
        this.conteudoTexto = conteudoTexto;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataUpload(final LocalDateTime dataUpload) {
        this.dataUpload = dataUpload;
    }
}
