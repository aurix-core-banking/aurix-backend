package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade Regulacao do Aurix.
 * Representa uma regulamentação ou norma.
 */
@Entity
@Table(name = "regulacoes", schema = "aurix")
public class Regulacao extends BaseEntity {
    /**
     * Comprimento padrão para nomes.
     */
    private static final int NAME_MAX_LENGTH = 200;
    /**
     * Comprimento padrão para campos longos.
     */
    private static final int LENGTH_LONG = 1000;
    /**
     * Nome descritivo da norma ou lei.
     */
    @NotBlank(message = "Nome da regulamentação é obrigatório")
    @Size(min = 2, max = NAME_MAX_LENGTH, message = "Nome deve ter entre 2 e 200 caracteres")
    @Column(nullable = false)
    private String nome;
    /**
     * Detalhes sobre o propósito da regulação.
     */
    @Column(length = LENGTH_LONG)
    private String descricao;
    /**
     * Nome da entidade governamental ou setorial emissora.
     */
    @NotBlank(message = "Órgão regulador é obrigatório")
    @Column(name = "orgao_regulador", nullable = false)
    private String orgaoRegulador;
    /**
     * Identificador oficial da norma (ex: Lei 12.846/13).
     */
    @Column(name = "numero_regulamentacao")
    private String numeroRegulamentacao;
    /**
     * Data a partir da qual a norma tem validade jurídica.
     */
    @Column(name = "data_vigencia", nullable = false)
    private LocalDateTime dataVigencia;
    /**
     * Data de encerramento da validade, se houver.
     */
    @Column(name = "data_vencimento")
    private LocalDateTime dataVencimento;
    /**
     * Categoria técnica da norma (LEI, DECRETO, etc.).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_regulacao", nullable = false)
    private TipoRegulacao tipoRegulacao;
    /**
     * Estado atual de vigência da regulação.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusRegulacao status = StatusRegulacao.ATIVA;
    /**
     * Valor monetário de multa em caso de infração.
     */
    @Column(name = "penalidade_valor")
    private Double penalidadeValor;
    /**
     * Descrição qualitativa das sanções previstas.
     */
    @Column(name = "penalidade_descricao")
    private String penalidadeDescricao;
    /**
     * Lista técnica de requisitos de conformidade (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "requisitos", columnDefinition = "jsonb")
    private String requisitos;

    /**
     * Verifica se a regulamentação está vencida.
     *
     * @return true se vencida, false caso contrário.
     */
    public boolean isVencida() {
        return dataVencimento != null && LocalDateTime.now().isAfter(dataVencimento);
    }

    /**
     * Verifica se a regulamentação está em vigor.
     *
     * @return true se em vigor, false caso contrário.
     */
    public boolean isEmVigor() {
        return status == StatusRegulacao.ATIVA && LocalDateTime.now().isAfter(dataVigencia) && !isVencida();
    }


    /**
     * Enum para tipo de regulamentação.
     */
    public enum TipoRegulacao {
        /**
         * Lei.
         */
        LEI("Lei"), /**
         * Decreto.
         */
        DECRETO("Decreto"), /**
         * Resolução.
         */
        RESOLUCAO("Resolução"), /**
         * Circular.
         */
        CIRCULAR("Circular"), /**
         * Instrução.
         */
        INSTRUCAO("Instrução"), /**
         * Portaria.
         */
        PORTARIA("Portaria");
        /**
         * Descrição do tipo.
         */
        private final String descricao;

        TipoRegulacao(final String desc) {
            this.descricao = desc;
        }

        /**
         * Retorna a descrição do tipo.
         * 
         * @return a descrição.
         */
        public String getDescricao() {
            return descricao;
        }
    }


    /**
     * Enum para status da regulamentação.
     */
    public enum StatusRegulacao {
        /**
         * Ativa.
         */
        ATIVA("Ativa"), /**
         * Suspensa.
         */
        SUSPENSA("Suspensa"), /**
         * Revogada.
         */
        REVOGADA("Revogada"), /**
         * Vencida.
         */
        VENCIDA("Vencida");
        /**
         * Descrição do status.
         */
        private final String descricao;

        StatusRegulacao(final String desc) {
            this.descricao = desc;
        }

        /**
         * Retorna a descrição do status.
         * 
         * @return a descrição.
         */
        public String getDescricao() {
            return descricao;
        }
    }

    /**
     * Nome descritivo da norma ou lei.
     */
    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    /**
     * Detalhes sobre o propósito da regulação.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Nome da entidade governamental ou setorial emissora.
     */
    @java.lang.SuppressWarnings("all")
    public String getOrgaoRegulador() {
        return this.orgaoRegulador;
    }

    /**
     * Identificador oficial da norma (ex: Lei 12.846/13).
     */
    @java.lang.SuppressWarnings("all")
    public String getNumeroRegulamentacao() {
        return this.numeroRegulamentacao;
    }

    /**
     * Data a partir da qual a norma tem validade jurídica.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataVigencia() {
        return this.dataVigencia;
    }

    /**
     * Data de encerramento da validade, se houver.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataVencimento() {
        return this.dataVencimento;
    }

    /**
     * Categoria técnica da norma (LEI, DECRETO, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public TipoRegulacao getTipoRegulacao() {
        return this.tipoRegulacao;
    }

    /**
     * Estado atual de vigência da regulação.
     */
    @java.lang.SuppressWarnings("all")
    public StatusRegulacao getStatus() {
        return this.status;
    }

    /**
     * Valor monetário de multa em caso de infração.
     */
    @java.lang.SuppressWarnings("all")
    public Double getPenalidadeValor() {
        return this.penalidadeValor;
    }

    /**
     * Descrição qualitativa das sanções previstas.
     */
    @java.lang.SuppressWarnings("all")
    public String getPenalidadeDescricao() {
        return this.penalidadeDescricao;
    }

    /**
     * Lista técnica de requisitos de conformidade (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getRequisitos() {
        return this.requisitos;
    }

    /**
     * Nome descritivo da norma ou lei.
     */
    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    /**
     * Detalhes sobre o propósito da regulação.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Nome da entidade governamental ou setorial emissora.
     */
    @java.lang.SuppressWarnings("all")
    public void setOrgaoRegulador(final String orgaoRegulador) {
        this.orgaoRegulador = orgaoRegulador;
    }

    /**
     * Identificador oficial da norma (ex: Lei 12.846/13).
     */
    @java.lang.SuppressWarnings("all")
    public void setNumeroRegulamentacao(final String numeroRegulamentacao) {
        this.numeroRegulamentacao = numeroRegulamentacao;
    }

    /**
     * Data a partir da qual a norma tem validade jurídica.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataVigencia(final LocalDateTime dataVigencia) {
        this.dataVigencia = dataVigencia;
    }

    /**
     * Data de encerramento da validade, se houver.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataVencimento(final LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    /**
     * Categoria técnica da norma (LEI, DECRETO, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoRegulacao(final TipoRegulacao tipoRegulacao) {
        this.tipoRegulacao = tipoRegulacao;
    }

    /**
     * Estado atual de vigência da regulação.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusRegulacao status) {
        this.status = status;
    }

    /**
     * Valor monetário de multa em caso de infração.
     */
    @java.lang.SuppressWarnings("all")
    public void setPenalidadeValor(final Double penalidadeValor) {
        this.penalidadeValor = penalidadeValor;
    }

    /**
     * Descrição qualitativa das sanções previstas.
     */
    @java.lang.SuppressWarnings("all")
    public void setPenalidadeDescricao(final String penalidadeDescricao) {
        this.penalidadeDescricao = penalidadeDescricao;
    }

    /**
     * Lista técnica de requisitos de conformidade (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setRequisitos(final String requisitos) {
        this.requisitos = requisitos;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Regulacao(nome=" + this.getNome() + ", descricao=" + this.getDescricao() + ", orgaoRegulador=" + this.getOrgaoRegulador() + ", numeroRegulamentacao=" + this.getNumeroRegulamentacao() + ", dataVigencia=" + this.getDataVigencia() + ", dataVencimento=" + this.getDataVencimento() + ", tipoRegulacao=" + this.getTipoRegulacao() + ", status=" + this.getStatus() + ", penalidadeValor=" + this.getPenalidadeValor() + ", penalidadeDescricao=" + this.getPenalidadeDescricao() + ", requisitos=" + this.getRequisitos() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Regulacao)) return false;
        final Regulacao other = (Regulacao) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$penalidadeValor = this.getPenalidadeValor();
        final java.lang.Object other$penalidadeValor = other.getPenalidadeValor();
        if (this$penalidadeValor == null ? other$penalidadeValor != null : !this$penalidadeValor.equals(other$penalidadeValor)) return false;
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
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Regulacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $penalidadeValor = this.getPenalidadeValor();
        result = result * PRIME + ($penalidadeValor == null ? 43 : $penalidadeValor.hashCode());
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
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public Regulacao() {
    }

    /**
     * Creates a new {@code Regulacao} instance.
     *
     * @param nome Nome descritivo da norma ou lei.
     * @param descricao Detalhes sobre o propósito da regulação.
     * @param orgaoRegulador Nome da entidade governamental ou setorial emissora.
     * @param numeroRegulamentacao Identificador oficial da norma (ex: Lei 12.846/13).
     * @param dataVigencia Data a partir da qual a norma tem validade jurídica.
     * @param dataVencimento Data de encerramento da validade, se houver.
     * @param tipoRegulacao Categoria técnica da norma (LEI, DECRETO, etc.).
     * @param status Estado atual de vigência da regulação.
     * @param penalidadeValor Valor monetário de multa em caso de infração.
     * @param penalidadeDescricao Descrição qualitativa das sanções previstas.
     * @param requisitos Lista técnica de requisitos de conformidade (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public Regulacao(final String nome, final String descricao, final String orgaoRegulador, final String numeroRegulamentacao, final LocalDateTime dataVigencia, final LocalDateTime dataVencimento, final TipoRegulacao tipoRegulacao, final StatusRegulacao status, final Double penalidadeValor, final String penalidadeDescricao, final String requisitos) {
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
    }
}
