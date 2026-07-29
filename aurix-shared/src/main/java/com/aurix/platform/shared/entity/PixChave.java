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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade PIX Chave do Aurix.
 * Representa uma chave PIX cadastrada.
 */
@Entity
@Table(name = "pix_chaves", schema = "aurix")
public class PixChave extends BaseEntity {
    /**
     * Comprimento máximo da chave PIX.
     */
    private static final int CHAVE_MAX_LENGTH = 77;
    /**
     * Comprimento máximo do nome do titular.
     */
    private static final int TITULAR_MAX_LENGTH = 140;
    /**
     * Valor textual da chave (CPF, Email, etc.).
     */
    @NotBlank(message = "Chave PIX é obrigatória")
    @Size(max = CHAVE_MAX_LENGTH, message = "Chave PIX deve ter no máximo 77 caracteres")
    @Column(name = "chave_pix", unique = true, nullable = false, length = CHAVE_MAX_LENGTH)
    private String chavePix;
    /**
     * Conta bancária para o recebimento via esta chave.
     */
    @NotNull(message = "Conta é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;
    /**
     * Tipo técnico da chave (CPF, EMAIL, ALEATORIA, etc.).
     */
    @NotNull(message = "Tipo da chave é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_chave", nullable = false)
    private TipoChavePix tipoChave;
    /**
     * Nome completo do titular da chave.
     */
    @NotBlank(message = "Nome do titular é obrigatório")
    @Size(max = TITULAR_MAX_LENGTH, message = "Nome do titular deve ter no máximo 140 caracteres")
    @Column(name = "nome_titular", nullable = false, length = TITULAR_MAX_LENGTH)
    private String nomeTitular;
    /**
     * Status de registro da chave no DICT.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusChavePix status = StatusChavePix.ATIVA;
    /**
     * Data e hora em que a chave foi registrada.
     */
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro = LocalDateTime.now();
    /**
     * Data e hora em que a chave foi desativada, se aplicável.
     */
    @Column(name = "data_inativacao")
    private LocalDateTime dataInativacao;
    /**
     * Metadados suplementares da chave (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_adicionais", columnDefinition = "jsonb")
    private String dadosAdicionais;


    /**
     * Enum para status da chave PIX.
     */
    public enum StatusChavePix {
        /**
         * Ativa.
         */
        ATIVA("Ativa"), /**
         * Inativa.
         */
        INATIVA("Inativa"), /**
         * Bloqueada.
         */
        BLOQUEADA("Bloqueada"), /**
         * Suspensa.
         */
        SUSPENSA("Suspensa");
        /**
         * Descrição do status.
         */
        private final String descricao;

        StatusChavePix(final String desc) {
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
     * Enum para tipo de chave PIX.
     */
    public enum TipoChavePix {
        /**
         * CPF.
         */
        CPF("CPF"), /**
         * CNPJ.
         */
        CNPJ("CNPJ"), /**
         * Email.
         */
        EMAIL("Email"), /**
         * Telefone.
         */
        TELEFONE("Telefone"), /**
         * Chave Aleatória.
         */
        CHAVE_ALEATORIA("Chave Aleatória");
        /**
         * Descrição do tipo.
         */
        private final String descricao;

        TipoChavePix(final String desc) {
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
     * Valor textual da chave (CPF, Email, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public String getChavePix() {
        return this.chavePix;
    }

    /**
     * Conta bancária para o recebimento via esta chave.
     */
    @java.lang.SuppressWarnings("all")
    public Conta getConta() {
        return this.conta;
    }

    /**
     * Tipo técnico da chave (CPF, EMAIL, ALEATORIA, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public TipoChavePix getTipoChave() {
        return this.tipoChave;
    }

    /**
     * Nome completo do titular da chave.
     */
    @java.lang.SuppressWarnings("all")
    public String getNomeTitular() {
        return this.nomeTitular;
    }

    /**
     * Status de registro da chave no DICT.
     */
    @java.lang.SuppressWarnings("all")
    public StatusChavePix getStatus() {
        return this.status;
    }

    /**
     * Data e hora em que a chave foi registrada.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataCadastro() {
        return this.dataCadastro;
    }

    /**
     * Data e hora em que a chave foi desativada, se aplicável.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataInativacao() {
        return this.dataInativacao;
    }

    /**
     * Metadados suplementares da chave (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosAdicionais() {
        return this.dadosAdicionais;
    }

    /**
     * Valor textual da chave (CPF, Email, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public void setChavePix(final String chavePix) {
        this.chavePix = chavePix;
    }

    /**
     * Conta bancária para o recebimento via esta chave.
     */
    @java.lang.SuppressWarnings("all")
    public void setConta(final Conta conta) {
        this.conta = conta;
    }

    /**
     * Tipo técnico da chave (CPF, EMAIL, ALEATORIA, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoChave(final TipoChavePix tipoChave) {
        this.tipoChave = tipoChave;
    }

    /**
     * Nome completo do titular da chave.
     */
    @java.lang.SuppressWarnings("all")
    public void setNomeTitular(final String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    /**
     * Status de registro da chave no DICT.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusChavePix status) {
        this.status = status;
    }

    /**
     * Data e hora em que a chave foi registrada.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataCadastro(final LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    /**
     * Data e hora em que a chave foi desativada, se aplicável.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataInativacao(final LocalDateTime dataInativacao) {
        this.dataInativacao = dataInativacao;
    }

    /**
     * Metadados suplementares da chave (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosAdicionais(final String dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "PixChave(chavePix=" + this.getChavePix() + ", conta=" + this.getConta() + ", tipoChave=" + this.getTipoChave() + ", nomeTitular=" + this.getNomeTitular() + ", status=" + this.getStatus() + ", dataCadastro=" + this.getDataCadastro() + ", dataInativacao=" + this.getDataInativacao() + ", dadosAdicionais=" + this.getDadosAdicionais() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof PixChave)) return false;
        final PixChave other = (PixChave) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$chavePix = this.getChavePix();
        final java.lang.Object other$chavePix = other.getChavePix();
        if (this$chavePix == null ? other$chavePix != null : !this$chavePix.equals(other$chavePix)) return false;
        final java.lang.Object this$conta = this.getConta();
        final java.lang.Object other$conta = other.getConta();
        if (this$conta == null ? other$conta != null : !this$conta.equals(other$conta)) return false;
        final java.lang.Object this$tipoChave = this.getTipoChave();
        final java.lang.Object other$tipoChave = other.getTipoChave();
        if (this$tipoChave == null ? other$tipoChave != null : !this$tipoChave.equals(other$tipoChave)) return false;
        final java.lang.Object this$nomeTitular = this.getNomeTitular();
        final java.lang.Object other$nomeTitular = other.getNomeTitular();
        if (this$nomeTitular == null ? other$nomeTitular != null : !this$nomeTitular.equals(other$nomeTitular)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataCadastro = this.getDataCadastro();
        final java.lang.Object other$dataCadastro = other.getDataCadastro();
        if (this$dataCadastro == null ? other$dataCadastro != null : !this$dataCadastro.equals(other$dataCadastro)) return false;
        final java.lang.Object this$dataInativacao = this.getDataInativacao();
        final java.lang.Object other$dataInativacao = other.getDataInativacao();
        if (this$dataInativacao == null ? other$dataInativacao != null : !this$dataInativacao.equals(other$dataInativacao)) return false;
        final java.lang.Object this$dadosAdicionais = this.getDadosAdicionais();
        final java.lang.Object other$dadosAdicionais = other.getDadosAdicionais();
        if (this$dadosAdicionais == null ? other$dadosAdicionais != null : !this$dadosAdicionais.equals(other$dadosAdicionais)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof PixChave;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $chavePix = this.getChavePix();
        result = result * PRIME + ($chavePix == null ? 43 : $chavePix.hashCode());
        final java.lang.Object $conta = this.getConta();
        result = result * PRIME + ($conta == null ? 43 : $conta.hashCode());
        final java.lang.Object $tipoChave = this.getTipoChave();
        result = result * PRIME + ($tipoChave == null ? 43 : $tipoChave.hashCode());
        final java.lang.Object $nomeTitular = this.getNomeTitular();
        result = result * PRIME + ($nomeTitular == null ? 43 : $nomeTitular.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataCadastro = this.getDataCadastro();
        result = result * PRIME + ($dataCadastro == null ? 43 : $dataCadastro.hashCode());
        final java.lang.Object $dataInativacao = this.getDataInativacao();
        result = result * PRIME + ($dataInativacao == null ? 43 : $dataInativacao.hashCode());
        final java.lang.Object $dadosAdicionais = this.getDadosAdicionais();
        result = result * PRIME + ($dadosAdicionais == null ? 43 : $dadosAdicionais.hashCode());
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public PixChave() {
    }

    /**
     * Creates a new {@code PixChave} instance.
     *
     * @param chavePix Valor textual da chave (CPF, Email, etc.).
     * @param conta Conta bancária para o recebimento via esta chave.
     * @param tipoChave Tipo técnico da chave (CPF, EMAIL, ALEATORIA, etc.).
     * @param nomeTitular Nome completo do titular da chave.
     * @param status Status de registro da chave no DICT.
     * @param dataCadastro Data e hora em que a chave foi registrada.
     * @param dataInativacao Data e hora em que a chave foi desativada, se aplicável.
     * @param dadosAdicionais Metadados suplementares da chave (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public PixChave(final String chavePix, final Conta conta, final TipoChavePix tipoChave, final String nomeTitular, final StatusChavePix status, final LocalDateTime dataCadastro, final LocalDateTime dataInativacao, final String dadosAdicionais) {
        this.chavePix = chavePix;
        this.conta = conta;
        this.tipoChave = tipoChave;
        this.nomeTitular = nomeTitular;
        this.status = status;
        this.dataCadastro = dataCadastro;
        this.dataInativacao = dataInativacao;
        this.dadosAdicionais = dadosAdicionais;
    }
}
