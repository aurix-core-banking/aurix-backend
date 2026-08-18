package com.aurix.platform.compliance.lgpd.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lgpd_log_acessos", schema = "aurix")
public class LgpdLogAcesso extends BaseEntity {

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAcessoLgpd tipoAcesso;

    @Column(nullable = false, length = 255)
    private String descricaoOperacao;

    @Column(nullable = false, length = 100)
    private String responsavelOperacao;

    @Column(length = 100)
    private String ipOrigem;

    @Column(nullable = false)
    private LocalDateTime dataOperacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultadoOperacaoLgpd resultado;

    @Column(columnDefinition = "TEXT")
    private String detalhesOperacao;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    public enum TipoAcessoLgpd {
        CONSULTA_DADOS, EXPORTACAO_DADOS, CORRECAO_DADOS,
        EXCLUSAO_DADOS, ANONIMIZACAO, REVOGACAO_CONSENTIMENTO,
        REQUISICAO_PORTABILIDADE, CADASTRO_CONSENTIMENTO,
        VISUALIZACAO_LOG, CONSULTA_BASE_LEGAL
    }

    public enum ResultadoOperacaoLgpd {
        SUCESSO, FALHA, PARCIAL, RESTRITO
    }

    @SuppressWarnings("all")
    public LgpdLogAcesso() {
    }

    public Long getClienteId() {
        return this.clienteId;
    }

    public TipoAcessoLgpd getTipoAcesso() {
        return this.tipoAcesso;
    }

    public String getDescricaoOperacao() {
        return this.descricaoOperacao;
    }

    public String getResponsavelOperacao() {
        return this.responsavelOperacao;
    }

    public String getIpOrigem() {
        return this.ipOrigem;
    }

    public LocalDateTime getDataOperacao() {
        return this.dataOperacao;
    }

    public ResultadoOperacaoLgpd getResultado() {
        return this.resultado;
    }

    public String getDetalhesOperacao() {
        return this.detalhesOperacao;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public void setClienteId(final Long clienteId) {
        this.clienteId = clienteId;
    }

    public void setTipoAcesso(final TipoAcessoLgpd tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    public void setDescricaoOperacao(final String descricaoOperacao) {
        this.descricaoOperacao = descricaoOperacao;
    }

    public void setResponsavelOperacao(final String responsavelOperacao) {
        this.responsavelOperacao = responsavelOperacao;
    }

    public void setIpOrigem(final String ipOrigem) {
        this.ipOrigem = ipOrigem;
    }

    public void setDataOperacao(final LocalDateTime dataOperacao) {
        this.dataOperacao = dataOperacao;
    }

    public void setResultado(final ResultadoOperacaoLgpd resultado) {
        this.resultado = resultado;
    }

    public void setDetalhesOperacao(final String detalhesOperacao) {
        this.detalhesOperacao = detalhesOperacao;
    }

    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }
}
