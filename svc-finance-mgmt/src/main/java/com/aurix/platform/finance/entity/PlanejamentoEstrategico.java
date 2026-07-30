package com.aurix.platform.finance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa o planejamento estratégico
 * 
 * Gerencia objetivos estratégicos, metas e iniciativas
 */
@Entity
@Table(name = "planejamento_estrategico", schema = "aurix")
public class PlanejamentoEstrategico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo_objetivo", unique = true, nullable = false, length = 50)
    private String codigoObjetivo;
    @Column(name = "nome", nullable = false, length = 200)
    private String nome;
    @Column(name = "descricao", length = 1000)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_objetivo", nullable = false)
    private TipoObjetivo tipoObjetivo;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaObjetivo categoria;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusObjetivo status;
    @Column(name = "prioridade", nullable = false)
    private Integer prioridade;
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;
    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;
    @Column(name = "percentual_conclusao", precision = 8, scale = 4)
    private java.math.BigDecimal percentualConclusao;
    @Column(name = "responsavel", length = 100)
    private String responsavel;
    @Column(name = "equipe", length = 500)
    private String equipe;
    @Column(name = "recursos_necessarios", length = 1000)
    private String recursosNecessarios;
    @Column(name = "riscos", length = 1000)
    private String riscos;
    @Column(name = "indicadores_sucesso", length = 1000)
    private String indicadoresSucesso;
    @Column(name = "observacoes", length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;
    @Column(name = "versao", nullable = false)
    @Version
    private Long versao;


    /**
     * Tipo de objetivo estratégico
     */
    public enum TipoObjetivo {
        ESTRATEGICO,  // Estratégico
        TATICO,  // Tático
        OPERACIONAL,  // Operacional
        PROJETO,  // Projeto
        INICIATIVA,  // Iniciativa
        METAS // Metas
        ;
    }


    /**
     * Categoria do objetivo
     */
    public enum CategoriaObjetivo {
        FINANCEIRO,  // Financeiro
        COMERCIAL,  // Comercial
        OPERACIONAL,  // Operacional
        TECNOLOGIA,  // Tecnologia
        RECURSOS_HUMANOS,  // Recursos Humanos
        QUALIDADE,  // Qualidade
        CRESCIMENTO,  // Crescimento
        INOVACAO,  // Inovação
        SUSTENTABILIDADE,  // Sustentabilidade
        OUTROS // Outros
        ;
    }


    /**
     * Status do objetivo
     */
    public enum StatusObjetivo {
        PLANEJADO,  // Planejado
        EM_ANDAMENTO,  // Em andamento
        CONCLUIDO,  // Concluído
        SUSPENSO,  // Suspenso
        CANCELADO,  // Cancelado
        ATRASADO // Atrasado
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class PlanejamentoEstrategicoBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String codigoObjetivo;
        @java.lang.SuppressWarnings("all")
        private String nome;
        @java.lang.SuppressWarnings("all")
        private String descricao;
        @java.lang.SuppressWarnings("all")
        private TipoObjetivo tipoObjetivo;
        @java.lang.SuppressWarnings("all")
        private CategoriaObjetivo categoria;
        @java.lang.SuppressWarnings("all")
        private StatusObjetivo status;
        @java.lang.SuppressWarnings("all")
        private Integer prioridade;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataInicio;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataFim;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataConclusao;
        @java.lang.SuppressWarnings("all")
        private java.math.BigDecimal percentualConclusao;
        @java.lang.SuppressWarnings("all")
        private String responsavel;
        @java.lang.SuppressWarnings("all")
        private String equipe;
        @java.lang.SuppressWarnings("all")
        private String recursosNecessarios;
        @java.lang.SuppressWarnings("all")
        private String riscos;
        @java.lang.SuppressWarnings("all")
        private String indicadoresSucesso;
        @java.lang.SuppressWarnings("all")
        private String observacoes;
        @java.lang.SuppressWarnings("all")
        private String metadata;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;
        @java.lang.SuppressWarnings("all")
        private Long versao;

        @java.lang.SuppressWarnings("all")
        PlanejamentoEstrategicoBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder codigoObjetivo(final String codigoObjetivo) {
            this.codigoObjetivo = codigoObjetivo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder nome(final String nome) {
            this.nome = nome;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder descricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder tipoObjetivo(final TipoObjetivo tipoObjetivo) {
            this.tipoObjetivo = tipoObjetivo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder categoria(final CategoriaObjetivo categoria) {
            this.categoria = categoria;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder status(final StatusObjetivo status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder prioridade(final Integer prioridade) {
            this.prioridade = prioridade;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public PlanejamentoEstrategico.PlanejamentoEstrategicoBuilder dataInicio(final LocalDate dataInicio) {
            this.da                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                r.getIndicadoresSucesso();
        if (this$indicadoresSucesso == null ? other$indicadoresSucesso != null : !this$indicadoresSucesso.equals(other$indicadoresSucesso)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$metadata = this.getMetadata();
        final java.lang.Object other$metadata = other.getMetadata();
        if (this$metadata == null ? other$metadata != null : !this$metadata.equals(other$metadata)) return false;
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
        return other instanceof PlanejamentoEstrategico;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $prioridade = this.getPrioridade();
        result = result * PRIME + ($prioridade == null ? 43 : $prioridade.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $codigoObjetivo = this.getCodigoObjetivo();
        result = result * PRIME + ($codigoObjetivo == null ? 43 : $codigoObjetivo.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $tipoObjetivo = this.getTipoObjetivo();
        result = result * PRIME + ($tipoObjetivo == null ? 43 : $tipoObjetivo.hashCode());
        final java.lang.Object $categoria = this.getCategoria();
        result = result * PRIME + ($categoria == null ? 43 : $categoria.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataInicio = this.getDataInicio();
        result = result * PRIME + ($dataInicio == null ? 43 : $dataInicio.hashCode());
        final java.lang.Object $dataFim = this.getDataFim();
        result = result * PRIME + ($dataFim == null ? 43 : $dataFim.hashCode());
        final java.lang.Object $dataConclusao = this.getDataConclusao();
        result = result * PRIME + ($dataConclusao == null ? 43 : $dataConclusao.hashCode());
        final java.lang.Object $percentualConclusao = this.getPercentualConclusao();
        result = result * PRIME + ($percentualConclusao == null ? 43 : $percentualConclusao.hashCode());
        final java.lang.Object $responsavel = this.getResponsavel();
        result = result * PRIME + ($responsavel == null ? 43 : $responsavel.hashCode());
        final java.lang.Object $equipe = this.getEquipe();
        result = result * PRIME + ($equipe == null ? 43 : $equipe.hashCode());
        final java.lang.Object $recursosNecessarios = this.getRecursosNecessarios();
        result = result * PRIME + ($recursosNecessarios == null ? 43 : $recursosNecessarios.hashCode());
        final java.lang.Object $riscos = this.getRiscos();
        result = result * PRIME + ($riscos == null ? 43 : $riscos.hashCode());
        final java.lang.Object $indicadoresSucesso = this.getIndicadoresSucesso();
        result = result * PRIME + ($indicadoresSucesso == null ? 43 : $indicadoresSucesso.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $metadata = this.getMetadata();
        result = result * PRIME + ($metadata == null ? 43 : $metadata.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "PlanejamentoEstrategico(id=" + this.getId() + ", codigoObjetivo=" + this.getCodigoObjetivo() + ", nome=" + this.getNome() + ", descricao=" + this.getDescricao() + ", tipoObjetivo=" + this.getTipoObjetivo() + ", categoria=" + this.getCategoria() + ", status=" + this.getStatus() + ", prioridade=" + this.getPrioridade() + ", dataInicio=" + this.getDataInicio() + ", dataFim=" + this.getDataFim() + ", dataConclusao=" + this.getDataConclusao() + ", percentualConclusao=" + this.getPercentualConclusao() + ", responsavel=" + this.getResponsavel() + ", equipe=" + this.getEquipe() + ", recursosNecessarios=" + this.getRecursosNecessarios() + ", riscos=" + this.getRiscos() + ", indicadoresSucesso=" + this.getIndicadoresSucesso() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public PlanejamentoEstrategico() {
    }

    @java.lang.SuppressWarnings("all")
    public PlanejamentoEstrategico(final Long id, final String codigoObjetivo, final String nome, final String descricao, final TipoObjetivo tipoObjetivo, final CategoriaObjetivo categoria, final StatusObjetivo status, final Integer prioridade, final LocalDate dataInicio, final LocalDate dataFim, final LocalDate dataConclusao, final java.math.BigDecimal percentualConclusao, final String responsavel, final String equipe, final String recursosNecessarios, final String riscos, final String indicadoresSucesso, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.codigoObjetivo = codigoObjetivo;
        this.nome = nome;
        this.descricao = descricao;
        this.tipoObjetivo = tipoObjetivo;
        this.categoria = categoria;
        this.status = status;
        this.prioridade = prioridade;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataConclusao = dataConclusao;
        this.percentualConclusao = percentualConclusao;
        this.responsavel = responsavel;
        this.equipe = equipe;
        this.recursosNecessarios = recursosNecessarios;
        this.riscos = riscos;
        this.indicadoresSucesso = indicadoresSucesso;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
