package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.Conformidade;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO para Conformidade.
 */
public class ConformidadeDTO {
    /**
     * ID da conformidade.
     */
    private Long id;
    /**
     * ID da regulamentação associada.
     */
    @NotNull(message = "Regulamentação é obrigatória")
    private Long regulacaoId;
    /**
     * Nome da regulamentação associada.
     */
    private String regulacaoNome;
    /**
     * ID da entidade relacionada.
     */
    private Long entidadeId;
    /**
     * Tipo da entidade relacionada.
     */
    private String tipoEntidade;
    /**
     * Status atual da conformidade.
     */
    private Conformidade.StatusConformidade status;
    /**
     * Data da verificação.
     */
    private LocalDateTime dataVerificacao;
    /**
     * Data da próxima verificação prevista.
     */
    private LocalDateTime dataProximaVerificacao;
    /**
     * Observações adicionais.
     */
    private String observacoes;
    /**
     * Evidências documentais.
     */
    private String evidencias;
    /**
     * Descrição das não conformidades.
     */
    private String naoConformidades;
    /**
     * Plano de ação corretiva.
     */
    private String planoAcao;
    /**
     * Indica se a verificação está vencida.
     */
    private Boolean vencida;
    /**
     * Indica se a entidade está conforme.
     */
    private Boolean conforme;
    /**
     * Indica se possui não conformidades registradas.
     */
    private Boolean hasNaoConformidades;
    /**
     * Data de criação do registro.
     */
    private String dataCriacao;
    /**
     * Data da última atualização.
     */
    private String dataAtualizacao;

    /**
     * ID da conformidade.
     */
    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    /**
     * ID da regulamentação associada.
     */
    @java.lang.SuppressWarnings("all")
    public Long getRegulacaoId() {
        return this.regulacaoId;
    }

    /**
     * Nome da regulamentação associada.
     */
    @java.lang.SuppressWarnings("all")
    public String getRegulacaoNome() {
        return this.regulacaoNome;
    }

    /**
     * ID da entidade relacionada.
     */
    @java.lang.SuppressWarnings("all")
    public Long getEntidadeId() {
        return this.entidadeId;
    }

    /**
     * Tipo da entidade relacionada.
     */
    @java.lang.SuppressWarnings("all")
    public String getTipoEntidade() {
        return this.tipoEntidade;
    }

    /**
     * Status atual da conformidade.
     */
    @java.lang.SuppressWarnings("all")
    public Conformidade.StatusConformidade getStatus() {
        return this.status;
    }

    /**
     * Data da verificação.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataVerificacao() {
        return this.dataVerificacao;
    }

    /**
     * Data da próxima verificação prevista.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataProximaVerificacao() {
        return this.dataProximaVerificacao;
    }

    /**
     * Observações adicionais.
     */
    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    /**
     * Evidências documentais.
     */
    @java.lang.SuppressWarnings("all")
    public String getEvidencias() {
        return this.evidencias;
    }

    /**
     * Descrição das não conformidades.
     */
    @java.lang.SuppressWarnings("all")
    public String getNaoConformidades() {
        return this.naoConformidades;
    }

    /**
     * Plano de ação corretiva.
     */
    @java.lang.SuppressWarnings("all")
    public String getPlanoAcao() {
        return this.planoAcao;
    }

    /**
     * Indica se a verificação está vencida.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getVencida() {
        return this.vencida;
    }

    /**
     * Indica se a entidade está conforme.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getConforme() {
        return this.conforme;
    }

    /**
     * Indica se possui não conformidades registradas.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getHasNaoConformidades() {
        return this.hasNaoConformidades;
    }

    /**
     * Data de criação do registro.
     */
    @java.lang.SuppressWarnings("all")
    public String getDataCriacao() {
        return this.dataCriacao;
    }

    /**
     * Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public String getDataAtualizacao() {
        return this.dataAtualizacao;
    }

    /**
     * ID da conformidade.
     */
    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * ID da regulamentação associada.
     */
    @java.lang.SuppressWarnings("all")
    public void setRegulacaoId(final Long regulacaoId) {
        this.regulacaoId = regulacaoId;
    }

    /**
     * Nome da regulamentação associada.
     */
    @java.lang.SuppressWarnings("all")
    public void setRegulacaoNome(final String regulacaoNome) {
        this.regulacaoNome = regulacaoNome;
    }

    /**
     * ID da entidade relacionada.
     */
    @java.lang.SuppressWarnings("all")
    public void setEntidadeId(final Long entidadeId) {
        this.entidadeId = entidadeId;
    }

    /**
     * Tipo da entidade relacionada.
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoEntidade(final String tipoEntidade) {
        this.tipoEntidade = tipoEntidade;
    }

    /**
     * Status atual da conformidade.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final Conformidade.StatusConformidade status) {
        this.status = status;
    }

    /**
     * Data da verificação.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataVerificacao(final LocalDateTime dataVerificacao) {
        this.dataVerificacao = dataVerificacao;
    }

    /**
     * Data da próxima verificação prevista.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataProximaVerificacao(final LocalDateTime dataProximaVerificacao) {
        this.dataProximaVerificacao = dataProximaVerificacao;
    }

    /**
     * Observações adicionais.
     */
    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    /**
     * Evidências documentais.
     */
    @java.lang.SuppressWarnings("all")
    public void setEvidencias(final String evidencias) {
        this.evidencias = evidencias;
    }

    /**
     * Descrição das não conformidades.
     */
    @java.lang.SuppressWarnings("all")
    public void setNaoConformidades(final String naoConformidades) {
        this.naoConformidades = naoConformidades;
    }

    /**
     * Plano de ação corretiva.
     */
    @java.lang.SuppressWarnings("all")
    public void setPlanoAcao(final String planoAcao) {
        this.planoAcao = planoAcao;
    }

    /**
     * Indica se a verificação está vencida.
     */
    @java.lang.SuppressWarnings("all")
    public void setVencida(final Boolean vencida) {
        this.vencida = vencida;
    }

    /**
     * Indica se a entidade está conforme.
     */
    @java.lang.SuppressWarnings("all")
    public void setConforme(final Boolean conforme) {
        this.conforme = conforme;
    }

    /**
     * Indica se possui não conformidades registradas.
     */
    @java.lang.SuppressWarnings("all")
    public void setHasNaoConformidades(final Boolean hasNaoConformidades) {
        this.hasNaoConformidades = hasNaoConformidades;
    }

    /**
     * Data de criação do registro.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    /**
     * Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataAtualizacao(final String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ConformidadeDTO)) return false;
        final ConformidadeDTO other = (ConformidadeDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$regulacaoId = this.getRegulacaoId();
        final java.lang.Object other$regulacaoId = other.getRegulacaoId();
        if (this$regulacaoId == null ? other$regulacaoId != null : !this$regulacaoId.equals(other$regulacaoId)) return false;
        final java.lang.Object this$entidadeId = this.getEntidadeId();
        final java.lang.Object other$entidadeId = other.getEntidadeId();
        if (this$entidadeId == null ? other$entidadeId != null : !this$entidadeId.equals(other$entidadeId)) return false;
        final java.lang.Object this$vencida = this.getVencida();
        final java.lang.Object other$vencida = other.getVencida();
        if (this$vencida == null ? other$vencida != null : !this$vencida.equals(other$vencida)) return false;
        final java.lang.Object this$conforme = this.getConforme();
        final java.lang.Object other$conforme = other.getConforme();
        if (this$conforme == null ? other$conforme != null : !this$conforme.equals(other$conforme)) return false;
        final java.lang.Object this$hasNaoConformidades = this.getHasNaoConformidades();
        final java.lang.Object other$hasNaoConformidades = other.getHasNaoConformidades();
        if (this$hasNaoConformidades == null ? other$hasNaoConformidades != null : !this$hasNaoConformidades.equals(other$hasNaoConformidades)) return false;
        final java.lang.Object this$regulacaoNome = this.getRegulacaoNome();
        final java.lang.Object other$regulacaoNome = other.getRegulacaoNome();
        if (this$regulacaoNome == null ? other$regulacaoNome != null : !this$regulacaoNome.equals(other$regulacaoNome)) return false;
        final java.lang.Object this$tipoEntidade = this.getTipoEntidade();
        final java.lang.Object other$tipoEntidade = other.getTipoEntidade();
        if (this$tipoEntidade == null ? other$tipoEntidade != null : !this$tipoEntidade.equals(other$tipoEntidade)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataVerificacao = this.getDataVerificacao();
        final java.lang.Object other$dataVerificacao = other.getDataVerificacao();
        if (this$dataVerificacao == null ? other$dataVerificacao != null : !this$dataVerificacao.equals(other$dataVerificacao)) return false;
        final java.lang.Object this$dataProximaVerificacao = this.getDataProximaVerificacao();
        final java.lang.Object other$dataProximaVerificacao = other.getDataProximaVerificacao();
        if (this$dataProximaVerificacao == null ? other$dataProximaVerificacao != null : !this$dataProximaVerificacao.equals(other$dataProximaVerificacao)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$evidencias = this.getEvidencias();
        final java.lang.Object other$evidencias = other.getEvidencias();
        if (this$evidencias == null ? other$evidencias != null : !this$evidencias.equals(other$evidencias)) return false;
        final java.lang.Object this$naoConformidades = this.getNaoConformidades();
        final java.lang.Object other$naoConformidades = other.getNaoConformidades();
        if (this$naoConformidades == null ? other$naoConformidades != null : !this$naoConformidades.equals(other$naoConformidades)) return false;
        final java.lang.Object this$planoAcao = this.getPlanoAcao();
        final java.lang.Object other$planoAcao = other.getPlanoAcao();
        if (this$planoAcao == null ? other$planoAcao != null : !this$planoAcao.equals(other$planoAcao)) return false;
        final java.lang.Object this$dataCriacao = this.getDataCriacao();
        final java.lang.Object other$dataCriacao = other.getDataCriacao();
        if (this$dataCriacao == null ? other$dataCriacao != null : !this$dataCriacao.equals(other$dataCriacao)) return false;
        final java.lang.Object this$dataAtualizacao = this.getDataAtualizacao();
        final java.lang.Object other$dataAtualizacao = other.getDataAtualizacao();
        if (this$dataAtualizacao == null ? other$dataAtualizacao != null : !this$dataAtualizacao.equals(other$dataAtualizacao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ConformidadeDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $regulacaoId = this.getRegulacaoId();
        result = result * PRIME + ($regulacaoId == null ? 43 : $regulacaoId.hashCode());
        final java.lang.Object $entidadeId = this.getEntidadeId();
        result = result * PRIME + ($entidadeId == null ? 43 : $entidadeId.hashCode());
        final java.lang.Object $vencida = this.getVencida();
        result = result * PRIME + ($vencida == null ? 43 : $vencida.hashCode());
        final java.lang.Object $conforme = this.getConforme();
        result = result * PRIME + ($conforme == null ? 43 : $conforme.hashCode());
        final java.lang.Object $hasNaoConformidades = this.getHasNaoConformidades();
        result = result * PRIME + ($hasNaoConformidades == null ? 43 : $hasNaoConformidades.hashCode());
        final java.lang.Object $regulacaoNome = this.getRegulacaoNome();
        result = result * PRIME + ($regulacaoNome == null ? 43 : $regulacaoNome.hashCode());
        final java.lang.Object $tipoEntidade = this.getTipoEntidade();
        result = result * PRIME + ($tipoEntidade == null ? 43 : $tipoEntidade.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataVerificacao = this.getDataVerificacao();
        result = result * PRIME + ($dataVerificacao == null ? 43 : $dataVerificacao.hashCode());
        final java.lang.Object $dataProximaVerificacao = this.getDataProximaVerificacao();
        result = result * PRIME + ($dataProximaVerificacao == null ? 43 : $dataProximaVerificacao.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $evidencias = this.getEvidencias();
        result = result * PRIME + ($evidencias == null ? 43 : $evidencias.hashCode());
        final java.lang.Object $naoConformidades = this.getNaoConformidades();
        result = result * PRIME + ($naoConformidades == null ? 43 : $naoConformidades.hashCode());
        final java.lang.Object $planoAcao = this.getPlanoAcao();
        result = result * PRIME + ($planoAcao == null ? 43 : $planoAcao.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ConformidadeDTO(id=" + this.getId() + ", regulacaoId=" + this.getRegulacaoId() + ", regulacaoNome=" + this.getRegulacaoNome() + ", entidadeId=" + this.getEntidadeId() + ", tipoEntidade=" + this.getTipoEntidade() + ", status=" + this.getStatus() + ", dataVerificacao=" + this.getDataVerificacao() + ", dataProximaVerificacao=" + this.getDataProximaVerificacao() + ", observacoes=" + this.getObservacoes() + ", evidencias=" + this.getEvidencias() + ", naoConformidades=" + this.getNaoConformidades() + ", planoAcao=" + this.getPlanoAcao() + ", vencida=" + this.getVencida() + ", conforme=" + this.getConforme() + ", hasNaoConformidades=" + this.getHasNaoConformidades() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ConformidadeDTO() {
    }

    /**
     * Creates a new {@code ConformidadeDTO} instance.
     *
     * @param id ID da conformidade.
     * @param regulacaoId ID da regulamentação associada.
     * @param regulacaoNome Nome da regulamentação associada.
     * @param entidadeId ID da entidade relacionada.
     * @param tipoEntidade Tipo da entidade relacionada.
     * @param status Status atual da conformidade.
     * @param dataVerificacao Data da verificação.
     * @param dataProximaVerificacao Data da próxima verificação prevista.
     * @param observacoes Observações adicionais.
     * @param evidencias Evidências documentais.
     * @param naoConformidades Descrição das não conformidades.
     * @param planoAcao Plano de ação corretiva.
     * @param vencida Indica se a verificação está vencida.
     * @param conforme Indica se a entidade está conforme.
     * @param hasNaoConformidades Indica se possui não conformidades registradas.
     * @param dataCriacao Data de criação do registro.
     * @param dataAtualizacao Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public ConformidadeDTO(final Long id, final Long regulacaoId, final String regulacaoNome, final Long entidadeId, final String tipoEntidade, final Conformidade.StatusConformidade status, final LocalDateTime dataVerificacao, final LocalDateTime dataProximaVerificacao, final String observacoes, final String evidencias, final String naoConformidades, final String planoAcao, final Boolean vencida, final Boolean conforme, final Boolean hasNaoConformidades, final String dataCriacao, final String dataAtualizacao) {
        this.id = id;
        this.regulacaoId = regulacaoId;
        this.regulacaoNome = regulacaoNome;
        this.entidadeId = entidadeId;
        this.tipoEntidade = tipoEntidade;
        this.status = status;
        this.dataVerificacao = dataVerificacao;
        this.dataProximaVerificacao = dataProximaVerificacao;
        this.observacoes = observacoes;
        this.evidencias = evidencias;
        this.naoConformidades = naoConformidades;
        this.planoAcao = planoAcao;
        this.vencida = vencida;
        this.conforme = conforme;
        this.hasNaoConformidades = hasNaoConformidades;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
