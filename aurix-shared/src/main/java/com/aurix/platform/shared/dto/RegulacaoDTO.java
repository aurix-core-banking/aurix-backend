package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.Regulacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * DTO para Regulacao.
 */
public class RegulacaoDTO {
    /**
     * Tamanho mínimo do nome da regulamentação.
     */
    private static final int MIN_NOME_SIZE = 2;
    /**
     * Tamanho máximo do nome da regulamentação.
     */
    private static final int MAX_NOME_SIZE = 200;
    /**
     * ID da regulamentação.
     */
    private Long id;
    /**
     * Nome oficial da regulamentação.
     */
    @NotBlank(message = "Nome da regulamentação é obrigatório")
    @Size(min = MIN_NOME_SIZE, max = MAX_NOME_SIZE, message = "Nome deve ter entre 2 e 200 caracteres")
    private String nome;
    /**
     * Descrição clara do propósito da norma.
     */
    private String descricao;
    /**
     * Órgão regulador emissor (ex: BACEN, CVM).
     */
    @NotBlank(message = "Órgão regulador é obrigatório")
    private String orgaoRegulador;
    /**
     * Número oficial da norma (ex: Resolução 4.567).
     */
    private String numeroRegulamentacao;
    /**
     * Data em que entra em vigor.
     */
    private LocalDateTime dataVigencia;
    /**
     * Data de expiração ou revisão.
     */
    private LocalDateTime dataVencimento;
    /**
     * Tipo da regulamentação.
     */
    private Regulacao.TipoRegulacao tipoRegulacao;
    /**
     * Status atual (ATIVA, REVOGADA, etc).
     */
    private Regulacao.StatusRegulacao status;
    /**
     * Valor da penalidade pecuniária em caso de descumprimento.
     */
    private Double penalidadeValor;
    /**
     * Descrição das penalidades não financeiras.
     */
    private String penalidadeDescricao;
    /**
     * Lista de requisitos técnicos/legais.
     */
    private String requisitos;
    /**
     * Indica se a norma já expirou.
     */
    private Boolean vencida;
    /**
     * Indica se a norma está em vigor atualmente.
     */
    private Boolean emVigor;
    /**
     * Data de criação do registro.
     */
    private String dataCriacao;
    /**
     * Data da última atualização.
     */
    private String dataAtualizacao;

    /**
     * ID da regulamentação.
     */
    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    /**
     * Nome oficial da regulamentação.
     */
    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    /**
     * Descrição clara do propósito da norma.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Órgão regulador emissor (ex: BACEN, CVM).
     */
    @java.lang.SuppressWarnings("all")
    public String getOrgaoRegulador() {
        return this.orgaoRegulador;
    }

    /**
     * Número oficial da norma (ex: Resolução 4.567).
     */
    @java.lang.SuppressWarnings("all")
    public String getNumeroRegulamentacao() {
        return this.numeroRegulamentacao;
    }

    /**
     * Data em que entra em vigor.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataVigencia() {
        return this.dataVigencia;
    }

    /**
     * Data de expiração ou revisão.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataVencimento() {
        return this.dataVencimento;
    }

    /**
     * Tipo da regulamentação.
     */
    @java.lang.SuppressWarnings("all")
    public Regulacao.TipoRegulacao getTipoRegulacao() {
        return this.tipoRegulacao;
    }

    /**
     * Status atual (ATIVA, REVOGADA, etc).
     */
    @java.lang.SuppressWarnings("all")
    public Regulacao.StatusRegulacao getStatus() {
        return this.status;
    }

    /**
     * Valor da penalidade pecuniária em caso de descumprimento.
     */
    @java.lang.SuppressWarnings("all")
    public Double getPenalidadeValor() {
        return this.penalidadeValor;
    }

    /**
     * Descrição das penalidades não financeiras.
     */
    @java.lang.SuppressWarnings("all")
    public String getPenalidadeDescricao() {
        return this.penalidadeDescricao;
    }

    /**
     * Lista de requisitos técnicos/legais.
     */
    @java.lang.SuppressWarnings("all")
    public String getRequisitos() {
        return this.requisitos;
    }

    /**
     * Indica se a norma já expirou.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getVencida() {
        return this.vencida;
    }

    /**
     * Indica se a norma está em vigor atualmente.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getEmVigor() {
        return this.emVigor;
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
     * ID da regulamentação.
     */
    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Nome oficial da regulamentação.
     */
    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    /**
     * Descrição clara do propósito da norma.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Órgão regulador emissor (ex: BACEN, CVM).
     */
    @java.lang.SuppressWarnings("all")
    public void setOrgaoRegulador(final String orgaoRegulador) {
        this.orgaoRegulador = orgaoRegulador;
    }

    /**
     * Número oficial da norma (ex: Resolução 4.567).
     */
    @java.lang.SuppressWarnings("all")
    public void setNumeroRegulamentacao(final String numeroRegulamentacao) {
        this.numeroRegulamentacao = numeroRegulamentacao;
    }

    /**
     * Data em que entra em vigor.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataVigencia(final LocalDateTime dataVigencia) {
        this.dataVigencia = dataVigencia;
    }

    /**
     * Data de expiração ou revisão.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataVencimento(final LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    /**
     * Tipo da regulamentação.
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoRegulacao(final Regulacao.TipoRegulacao tipoRegulacao) {
        this.tipoRegulacao = tipoRegulacao;
    }

    /**
     * Status atual (ATIVA, REVOGADA, etc).
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final Regulacao.StatusRegulacao status) {
        this.status = status;
    }

    /**
     * Valor da penalidade pecuniária em caso de descumprimento.
     */
    @java.lang.SuppressWarnings("all")
    public void setPenalidadeValor(final Double penalidadeValor) {
        this.penalidadeValor = penalidadeValor;
    }

    /**
     * Descrição das penalidades não financeiras.
     */
    @java.lang.SuppressWarnings("all")
    public void setPenalidadeDescricao(final String penalidadeDescricao) {
        this.penalidadeDescricao = penalidadeDescricao;
    }

    /**
     * Lista de requisitos técnicos/legais.
     */
    @java.lang.SuppressWarnings("all")
    public void setRequisitos(final String requisitos) {
        this.requisitos = requisitos;
    }

    /**
     * Indica se a norma já expirou.
     */
    @java.lang.SuppressWarnings("all")
    public void setVencida(final Boolean vencida) {
        this.vencida = vencida;
    }

    /**
     * Indica se a norma está em vigor atualmente.
     */
    @java.lang.SuppressWarnings("all")
    public void setEmVigor(final Boolean emVigor) {
        this.emVigor = emVigor;
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
        if (!(o instanceof RegulacaoDTO)) return false;
        final RegulacaoDTO other = (RegulacaoDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$penalidadeValor = this.getPenalidadeValor();
        final java.lang.Object other$penalidadeValor = other.getPenalidadeValor();
        if (this$penalidadeValor == null ? other$penalidadeValor != null : !this$penalidadeValor.equals(other$penalidadeValor)) return false;
        final java.lang.Object this$vencida = this.getVencida();
        final java.lang.Object other$vencida = other.getVencida();
        if (this$vencida == null ? other$vencida != null : !this$vencida.equals(other$vencida)) return false;
        final java.lang.Object this$emVigor = this.getEmVigor();
        final java.lang.Object other$emVigor = other.getEmVigor();
        if (this$emVigor == null ? other$emVigor != null : !this$emVigor.equals(other$emVigor)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$orgaoRegulador = this.getOrgaoRegulador();
        final java.lang.Object other$orgaoRegulador = other.getOrgaoRegulador();
        if (this$orgaoRegulador == null ? other$orgaoRegulador != null : !this$orgaoRegulador.equals(other$orgaoRegulador)) return false;
        final java.lang.Object this$numeroRegulamentacao = this.getNumeroRegulamentacao();
        final java.lang.Object other$numeroRegulamentacao = other.getNumeroRegulamentacao();
        if (this$numeroRegulamentacao == null ? other$numeroRegulamentacao != null : !this$numeroRegulamentacao.equals(other$numeroRegulamentacao)) return false;
        final java.lang.Object this$dataVigencia = this.getDataVigencia();
        final java.lang.Object other$dataVigencia = other.getDataVigencia();
        if (this$dataVigencia == null ? other$dataVigencia != null : !this$dataVigencia.equals(other$dataVigencia)) return false;
        final java.lang.Object this$dataVencimento = this.getDataVencimento();
        final java.lang.Object other$dataVencimento = other.getDataVencimento();
        if (this$dataVencimento == null ? other$dataVencimento != null : !this$dataVencimento.equals(other$dataVencimento)) return false;
        final java.lang.Object this$tipoRegulacao = this.getTipoRegulacao();
        final java.lang.Object other$tipoRegulacao = other.getTipoRegulacao();
        if (this$tipoRegulacao == null ? other$tipoRegulacao != null : !this$tipoRegulacao.equals(other$tipoRegulacao)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$penalidadeDescricao = this.getPenalidadeDescricao();
        final java.lang.Object other$penalidadeDescricao = other.getPenalidadeDescricao();
        if (this$penalidadeDescricao == null ? other$penalidadeDescricao != null : !this$penalidadeDescricao.equals(other$penalidadeDescricao)) return false;
        final java.lang.Object this$requisitos = this.getRequisitos();
        final java.lang.Object other$requisitos = other.getRequisitos();
        if (this$requisitos == null ? other$requisitos != null : !this$requisitos.equals(other$requisitos)) return false;
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
        return other instanceof RegulacaoDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $penalidadeValor = this.getPenalidadeValor();
        result = result * PRIME + ($penalidadeValor == null ? 43 : $penalidadeValor.hashCode());
        final java.lang.Object $vencida = this.getVencida();
        result = result * PRIME + ($vencida == null ? 43 : $vencida.hashCode());
        final java.lang.Object $emVigor = this.getEmVigor();
        result = result * PRIME + ($emVigor == null ? 43 : $emVigor.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $orgaoRegulador = this.getOrgaoRegulador();
        result = result * PRIME + ($orgaoRegulador == null ? 43 : $orgaoRegulador.hashCode());
        final java.lang.Object $numeroRegulamentacao = this.getNumeroRegulamentacao();
        result = result * PRIME + ($numeroRegulamentacao == null ? 43 : $numeroRegulamentacao.hashCode());
        final java.lang.Object $dataVigencia = this.getDataVigencia();
        result = result * PRIME + ($dataVigencia == null ? 43 : $dataVigencia.hashCode());
        final java.lang.Object $dataVencimento = this.getDataVencimento();
        result = result * PRIME + ($dataVencimento == null ? 43 : $dataVencimento.hashCode());
        final java.lang.Object $tipoRegulacao = this.getTipoRegulacao();
        result = result * PRIME + ($tipoRegulacao == null ? 43 : $tipoRegulacao.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $penalidadeDescricao = this.getPenalidadeDescricao();
        result = result * PRIME + ($penalidadeDescricao == null ? 43 : $penalidadeDescricao.hashCode());
        final java.lang.Object $requisitos = this.getRequisitos();
        result = result * PRIME + ($requisitos == null ? 43 : $requisitos.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "RegulacaoDTO(id=" + this.getId() + ", nome=" + this.getNome() + ", descricao=" + this.getDescricao() + ", orgaoRegulador=" + this.getOrgaoRegulador() + ", numeroRegulamentacao=" + this.getNumeroRegulamentacao() + ", dataVigencia=" + this.getDataVigencia() + ", dataVencimento=" + this.getDataVencimento() + ", tipoRegulacao=" + this.getTipoRegulacao() + ", status=" + this.getStatus() + ", penalidadeValor=" + this.getPenalidadeValor() + ", penalidadeDescricao=" + this.getPenalidadeDescricao() + ", requisitos=" + this.getRequisitos() + ", vencida=" + this.getVencida() + ", emVigor=" + this.getEmVigor() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public RegulacaoDTO() {
    }

    /**
     * Creates a new {@code RegulacaoDTO} instance.
     *
     * @param id ID da regulamentação.
     * @param nome Nome oficial da regulamentação.
     * @param descricao Descrição clara do propósito da norma.
     * @param orgaoRegulador Órgão regulador emissor (ex: BACEN, CVM).
     * @param numeroRegulamentacao Número oficial da norma (ex: Resolução 4.567).
     * @param dataVigencia Data em que entra em vigor.
     * @param dataVencimento Data de expiração ou revisão.
     * @param tipoRegulacao Tipo da regulamentação.
     * @param status Status atual (ATIVA, REVOGADA, etc).
     * @param penalidadeValor Valor da penalidade pecuniária em caso de descumprimento.
     * @param penalidadeDescricao Descrição das penalidades não financeiras.
     * @param requisitos Lista de requisitos técnicos/legais.
     * @param vencida Indica se a norma já expirou.
     * @param emVigor Indica se a norma está em vigor atualmente.
     * @param dataCriacao Data de criação do registro.
     * @param dataAtualizacao Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public RegulacaoDTO(final Long id, final String nome, final String descricao, final String orgaoRegulador, final String numeroRegulamentacao, final LocalDateTime dataVigencia, final LocalDateTime dataVencimento, final Regulacao.TipoRegulacao tipoRegulacao, final Regulacao.StatusRegulacao status, final Double penalidadeValor, final String penalidadeDescricao, final String requisitos, final Boolean vencida, final Boolean emVigor, final String dataCriacao, final String dataAtualizacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.orgaoRegulador = orgaoRegulador;
        this.numeroRegulamentacao = numeroRegulamentacao;
        this.dataVigencia = dataVigencia;
        this.dataVencimento = dataVencimento;
        this.tipoRegulacao = tipoRegulacao;
        this.status = status;
        this.penalidadeValor = penalidadeValor;
        this.penalidadeDescricao = penalidadeDescricao;
        this.requisitos = requisitos;
        this.vencida = vencida;
        this.emVigor = emVigor;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
