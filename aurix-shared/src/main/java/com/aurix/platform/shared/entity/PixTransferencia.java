package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade PIX Transferência do Aurix.
 * Representa uma transferência PIX.
 */
@Entity
@Table(name = "pix_transferencias", schema = "aurix")
public class PixTransferencia extends BaseEntity {
    /**
     * Comprimento do End-to-End ID do PIX.
     */
    private static final int PIX_CODE_LENGTH = 100;
    /**
     * Comprimento máximo da chave PIX.
     */
    private static final int CHAVE_MAX_LENGTH = 77;
    /**
     * Comprimento máximo de nomes e descrições.
     */
    private static final int NAME_DESC_MAX_LENGTH = 140;
    /**
     * Comprimento padrão para códigos curtos.
     */
    private static final int SHORT_CODE_LENGTH = 10;
    /**
     * Comprimento para mensagens longas.
     */
    private static final int LONG_MESSAGE_LENGTH = 500;
    /**
     * Precisão decimal para valores monetários.
     */
    private static final int DECIMAL_PRECISION = 15;
    /**
     * Escala decimal para valores monetários.
     */
    private static final int DECIMAL_SCALE = 2;
    /**
     * Comprimento para código de instituição.
     */
    private static final int INSTITUTION_CODE_LENGTH = 100;
    /**
     * Comprimento para número de agência.
     */
    private static final int AGENCY_NUMBER_LENGTH = 10;
    /**
     * Comprimento para número de conta.
     */
    private static final int ACCOUNT_NUMBER_LENGTH = 20;
    /**
     * Identificador único do PIX (End-to-End ID).
     */
    @NotBlank(message = "Código PIX é obrigatório")
    @Column(name = "codigo_pix", unique = true, nullable = false, length = PIX_CODE_LENGTH)
    private String codigoPix;
    /**
     * Conta de onde os fundos serão debitados.
     */
    @NotNull(message = "Conta origem é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_origem_id", nullable = false)
    private Conta contaOrigem;
    /**
     * Chave PIX do destinatário.
     */
    @NotBlank(message = "Chave PIX destino é obrigatória")
    @Size(max = CHAVE_MAX_LENGTH, message = "Chave PIX deve ter no máximo 77 caracteres")
    @Column(name = "chave_pix_destino", nullable = false, length = CHAVE_MAX_LENGTH)
    private String chavePixDestino;
    /**
     * Nome completo do beneficiário da transferência.
     */
    @NotBlank(message = "Nome do destinatário é obrigatório")
    @Size(max = NAME_DESC_MAX_LENGTH, message = "Nome do beneficiário deve ter no máximo 140 caracteres")
    @Column(name = "nome_destinatario", nullable = false, length = NAME_DESC_MAX_LENGTH)
    private String nomeDestinatario;
    /**
     * Valor monetário da transferência.
     */
    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    @Column(name = "valor", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE, nullable = false)
    private BigDecimal valor;
    /**
     * Descrição opcional para o extrato.
     */
    @Size(max = NAME_DESC_MAX_LENGTH, message = "Descrição deve ter no máximo 140 caracteres")
    @Column(name = "descricao", length = NAME_DESC_MAX_LENGTH)
    private String descricao;
    /**
     * Status atual do processamento da transferência.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusPix status = StatusPix.PENDENTE;
    /**
     * Tipo da chave utilizada para o destino.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_chave", nullable = false)
    private TipoChavePix tipoChave;
    /**
     * Nome da instituição financeira de destino.
     */
    @Column(name = "instituicao_destino", length = INSTITUTION_CODE_LENGTH)
    private String instituicaoDestino;
    /**
     * Número da agência de destino.
     */
    @Column(name = "agencia_destino", length = AGENCY_NUMBER_LENGTH)
    private String agenciaDestino;
    /**
     * Número da conta de destino.
     */
    @Column(name = "conta_destino", length = ACCOUNT_NUMBER_LENGTH)
    private String contaDestino;
    /**
     * Data e hora da solicitação da transferência.
     */
    @Column(name = "data_transferencia", nullable = false)
    private LocalDateTime dataTransferencia = LocalDateTime.now();
    /**
     * Data e hora da confirmação do processamento.
     */
    @Column(name = "data_processamento")
    private LocalDateTime dataProcessamento;
    /**
     * Código de retorno da clearing (ex: SPI).
     */
    @Column(name = "codigo_retorno", length = SHORT_CODE_LENGTH)
    private String codigoRetorno;
    /**
     * Mensagem descritiva do retorno.
     */
    @Column(name = "mensagem_retorno", length = LONG_MESSAGE_LENGTH)
    private String mensagemRetorno;
    /**
     * Dados técnicos suplementares do PIX (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_adicionais", columnDefinition = "jsonb")
    private String dadosAdicionais;


    /**
     * Enum para status do PIX.
     */
    public enum StatusPix {
        /**
         * Pendente.
         */
        PENDENTE("Pendente"), /**
         * Processada.
         */
        PROCESSADA("Processada"), /**
         * Cancelada.
         */
        CANCELADA("Cancelada"), /**
         * Falhada.
         */
        FALHADA("Falhada"), /**
         * Revertida.
         */
        REVERTIDA("Revertida");
        /**
         * Descrição do status.
         */
        private final String descricao;

        StatusPix(final String desc) {
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
     * Identificador único do PIX (End-to-End ID).
     */
    @java.lang.SuppressWarnings("all")
    public String getCodigoPix() {
        return this.codigoPix;
    }

    /**
     * Conta de onde os fundos serão debitados.
     */
    @java.lang.SuppressWarnings("all")
    public Conta getContaOrigem() {
        return this.contaOrigem;
    }

    /**
     * Chave PIX do destinatário.
     */
    @java.lang.SuppressWarnings("all")
    public String getChavePixDestino() {
        return this.chavePixDestino;
    }

    /**
     * Nome completo do beneficiário da transferência.
     */
    @java.lang.SuppressWarnings("all")
    public String getNomeDestinatario() {
        return this.nomeDestinatario;
    }

    /**
     * Valor monetário da transferência.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValor() {
        return this.valor;
    }

    /**
     * Descrição opcional para o extrato.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Status atual do processamento da transferência.
     */
    @java.lang.SuppressWarnings("all")
    public StatusPix getStatus() {
        return this.status;
    }

    /**
     * Tipo da chave utilizada para o destino.
     */
    @java.lang.SuppressWarnings("all")
    public TipoChavePix getTipoChave() {
        return this.tipoChave;
    }

    /**
     * Nome da instituição financeira de destino.
     */
    @java.lang.SuppressWarnings("all")
    public String getInstituicaoDestino() {
        return this.instituicaoDestino;
    }

    /**
     * Número da agência de destino.
     */
    @java.lang.SuppressWarnings("all")
    public String getAgenciaDestino() {
        return this.agenciaDestino;
    }

    /**
     * Número da conta de destino.
     */
    @java.lang.SuppressWarnings("all")
    public String getContaDestino() {
        return this.contaDestino;
    }

    /**
     * Data e hora da solicitação da transferência.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataTransferencia() {
        return this.dataTransferencia;
    }

    /**
     * Data e hora da confirmação do processamento.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataProcessamento() {
        return this.dataProcessamento;
    }

    /**
     * Código de retorno da clearing (ex: SPI).
     */
    @java.lang.SuppressWarnings("all")
    public String getCodigoRetorno() {
        return this.codigoRetorno;
    }

    /**
     * Mensagem descritiva do retorno.
     */
    @java.lang.SuppressWarnings("all")
    public String getMensagemRetorno() {
        return this.mensagemRetorno;
    }

    /**
     * Dados técnicos suplementares do PIX (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosAdicionais() {
        return this.dadosAdicionais;
    }

    /**
     * Identificador único do PIX (End-to-End ID).
     */
    @java.lang.SuppressWarnings("all")
    public void setCodigoPix(final String codigoPix) {
        this.codigoPix = codigoPix;
    }

    /**
     * Conta de onde os fundos serão debitados.
     */
    @java.lang.SuppressWarnings("all")
    public void setContaOrigem(final Conta contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    /**
     * Chave PIX do destinatário.
     */
    @java.lang.SuppressWarnings("all")
    public void setChavePixDestino(final String chavePixDestino) {
        this.chavePixDestino = chavePixDestino;
    }

    /**
     * Nome completo do beneficiário da transferência.
     */
    @java.lang.SuppressWarnings("all")
    public void setNomeDestinatario(final String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }

    /**
     * Valor monetário da transferência.
     */
    @java.lang.SuppressWarnings("all")
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * Descrição opcional para o extrato.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Status atual do processamento da transferência.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusPix status) {
        this.status = status;
    }

    /**
     * Tipo da chave utilizada para o destino.
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoChave(final TipoChavePix tipoChave) {
        this.tipoChave = tipoChave;
    }

    /**
     * Nome da instituição financeira de destino.
     */
    @java.lang.SuppressWarnings("all")
    public void setInstituicaoDestino(final String instituicaoDestino) {
        this.instituicaoDestino = instituicaoDestino;
    }

    /**
     * Número da agência de destino.
     */
    @java.lang.SuppressWarnings("all")
    public void setAgenciaDestino(final String agenciaDestino) {
        this.agenciaDestino = agenciaDestino;
    }

    /**
     * Número da conta de destino.
     */
    @java.lang.SuppressWarnings("all")
    public void setContaDestino(final String contaDestino) {
        this.contaDestino = contaDestino;
    }

    /**
     * Data e hora da solicitação da transferência.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataTransferencia(final LocalDateTime dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }

    /**
     * Data e hora da confirmação do processamento.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataProcessamento(final LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    /**
     * Código de retorno da clearing (ex: SPI).
     */
    @java.lang.SuppressWarnings("all")
    public void setCodigoRetorno(final String codigoRetorno) {
        this.codigoRetorno = codigoRetorno;
    }

    /**
     * Mensagem descritiva do retorno.
     */
    @java.lang.SuppressWarnings("all")
    public void setMensagemRetorno(final String mensagemRetorno) {
        this.mensagemRetorno = mensagemRetorno;
    }

    /**
     * Dados técnicos suplementares do PIX (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosAdicionais(final String dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "PixTransferencia(codigoPix=" + this.getCodigoPix() + ", contaOrigem=" + this.getContaOrigem() + ", chavePixDestino=" + this.getChavePixDestino() + ", nomeDestinatario=" + this.getNomeDestinatario() + ", valor=" + this.getValor() + ", descricao=" + this.getDescricao() + ", status=" + this.getStatus() + ", tipoChave=" + this.getTipoChave() + ", instituicaoDestino=" + this.getInstituicaoDestino() + ", agenciaDestino=" + this.getAgenciaDestino() + ", contaDestino=" + this.getContaDestino() + ", dataTransferencia=" + this.getDataTransferencia() + ", dataProcessamento=" + this.getDataProcessamento() + ", codigoRetorno=" + this.getCodigoRetorno() + ", mensagemRetorno=" + this.getMensagemRetorno() + ", dadosAdicionais=" + this.getDadosAdicionais() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof PixTransferencia)) return false;
        final PixTransferencia other = (PixTransferencia) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$codigoPix = this.getCodigoPix();
        final java.lang.Object other$codigoPix = other.getCodigoPix();
        if (this$codigoPix == null ? other$codigoPix != null : !this$codigoPix.equals(other$codigoPix)) return false;
        final java.lang.Object this$contaOrigem = this.getContaOrigem();
        final java.lang.Object other$contaOrigem = other.getContaOrigem();
        if (this$contaOrigem == null ? other$contaOrigem != null : !this$contaOrigem.equals(other$contaOrigem)) return false;
        final java.lang.Object this$chavePixDestino = this.getChavePixDestino();
        final java.lang.Object other$chavePixDestino = other.getChavePixDestino();
        if (this$chavePixDestino == null ? other$chavePixDestino != null : !this$chavePixDestino.equals(other$chavePixDestino)) return false;
        final java.lang.Object this$nomeDestinatario = this.getNomeDestinatario();
        final java.lang.Object other$nomeDestinatario = other.getNomeDestinatario();
        if (this$nomeDestinatario == null ? other$nomeDestinatario != null : !this$nomeDestinatario.equals(other$nomeDestinatario)) return false;
        final java.lang.Object this$valor = this.getValor();
        final java.lang.Object other$valor = other.getValor();
        if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$tipoChave = this.getTipoChave();
        final java.lang.Object other$tipoChave = other.getTipoChave();
        if (this$tipoChave == null ? other$tipoChave != null : !this$tipoChave.equals(other$tipoChave)) return false;
        final java.lang.Object this$instituicaoDestino = this.getInstituicaoDestino();
        final java.lang.Object other$instituicaoDestino = other.getInstituicaoDestino();
        if (this$instituicaoDestino == null ? other$instituicaoDestino != null : !this$instituicaoDestino.equals(other$instituicaoDestino)) return false;
        final java.lang.Object this$agenciaDestino = this.getAgenciaDestino();
        final java.lang.Object other$agenciaDestino = other.getAgenciaDestino();
        if (this$agenciaDestino == null ? other$agenciaDestino != null : !this$agenciaDestino.equals(other$agenciaDestino)) return false;
        final java.lang.Object this$contaDestino = this.getContaDestino();
        final java.lang.Object other$contaDestino = other.getContaDestino();
        if (this$contaDestino == null ? other$contaDestino != null : !this$contaDestino.equals(other$contaDestino)) return false;
        final java.lang.Object this$dataTransferencia = this.getDataTransferencia();
        final java.lang.Object other$dataTransferencia = other.getDataTransferencia();
        if (this$dataTransferencia == null ? other$dataTransferencia != null : !this$dataTransferencia.equals(other$dataTransferencia)) return false;
        final java.lang.Object this$dataProcessamento = this.getDataProcessamento();
        final java.lang.Object other$dataProcessamento = other.getDataProcessamento();
        if (this$dataProcessamento == null ? other$dataProcessamento != null : !this$dataProcessamento.equals(other$dataProcessamento)) return false;
        final java.lang.Object this$codigoRetorno = this.getCodigoRetorno();
        final java.lang.Object other$codigoRetorno = other.getCodigoRetorno();
        if (this$codigoRetorno == null ? other$codigoRetorno != null : !this$codigoRetorno.equals(other$codigoRetorno)) return false;
        final java.lang.Object this$mensagemRetorno = this.getMensagemRetorno();
        final java.lang.Object other$mensagemRetorno = other.getMensagemRetorno();
        if (this$mensagemRetorno == null ? other$mensagemRetorno != null : !this$mensagemRetorno.equals(other$mensagemRetorno)) return false;
        final java.lang.Object this$dadosAdicionais = this.getDadosAdicionais();
        final java.lang.Object other$dadosAdicionais = other.getDadosAdicionais();
        if (this$dadosAdicionais == null ? other$dadosAdicionais != null : !this$dadosAdicionais.equals(other$dadosAdicionais)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof PixTransferencia;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $codigoPix = this.getCodigoPix();
        result = result * PRIME + ($codigoPix == null ? 43 : $codigoPix.hashCode());
        final java.lang.Object $contaOrigem = this.getContaOrigem();
        result = result * PRIME + ($contaOrigem == null ? 43 : $contaOrigem.hashCode());
        final java.lang.Object $chavePixDestino = this.getChavePixDestino();
        result = result * PRIME + ($chavePixDestino == null ? 43 : $chavePixDestino.hashCode());
        final java.lang.Object $nomeDestinatario = this.getNomeDestinatario();
        result = result * PRIME + ($nomeDestinatario == null ? 43 : $nomeDestinatario.hashCode());
        final java.lang.Object $valor = this.getValor();
        result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $tipoChave = this.getTipoChave();
        result = result * PRIME + ($tipoChave == null ? 43 : $tipoChave.hashCode());
        final java.lang.Object $instituicaoDestino = this.getInstituicaoDestino();
        result = result * PRIME + ($instituicaoDestino == null ? 43 : $instituicaoDestino.hashCode());
        final java.lang.Object $agenciaDestino = this.getAgenciaDestino();
        result = result * PRIME + ($agenciaDestino == null ? 43 : $agenciaDestino.hashCode());
        final java.lang.Object $contaDestino = this.getContaDestino();
        result = result * PRIME + ($contaDestino == null ? 43 : $contaDestino.hashCode());
        final java.lang.Object $dataTransferencia = this.getDataTransferencia();
        result = result * PRIME + ($dataTransferencia == null ? 43 : $dataTransferencia.hashCode());
        final java.lang.Object $dataProcessamento = this.getDataProcessamento();
        result = result * PRIME + ($dataProcessamento == null ? 43 : $dataProcessamento.hashCode());
        final java.lang.Object $codigoRetorno = this.getCodigoRetorno();
        result = result * PRIME + ($codigoRetorno == null ? 43 : $codigoRetorno.hashCode());
        final java.lang.Object $mensagemRetorno = this.getMensagemRetorno();
        result = result * PRIME + ($mensagemRetorno == null ? 43 : $mensagemRetorno.hashCode());
        final java.lang.Object $dadosAdicionais = this.getDadosAdicionais();
        result = result * PRIME + ($dadosAdicionais == null ? 43 : $dadosAdicionais.hashCode());
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public PixTransferencia() {
    }

    /**
     * Creates a new {@code PixTransferencia} instance.
     *
     * @param codigoPix Identificador único do PIX (End-to-End ID).
     * @param contaOrigem Conta de onde os fundos serão debitados.
     * @param chavePixDestino Chave PIX do destinatário.
     * @param nomeDestinatario Nome completo do beneficiário da transferência.
     * @param valor Valor monetário da transferência.
     * @param descricao Descrição opcional para o extrato.
     * @param status Status atual do processamento da transferência.
     * @param tipoChave Tipo da chave utilizada para o destino.
     * @param instituicaoDestino Nome da instituição financeira de destino.
     * @param agenciaDestino Número da agência de destino.
     * @param contaDestino Número da conta de destino.
     * @param dataTransferencia Data e hora da solicitação da transferência.
     * @param dataProcessamento Data e hora da confirmação do processamento.
     * @param codigoRetorno Código de retorno da clearing (ex: SPI).
     * @param mensagemRetorno Mensagem descritiva do retorno.
     * @param dadosAdicionais Dados técnicos suplementares do PIX (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public PixTransferencia(final String codigoPix, final Conta contaOrigem, final String chavePixDestino, final String nomeDestinatario, final BigDecimal valor, final String descricao, final StatusPix status, final TipoChavePix tipoChave, final String instituicaoDestino, final String agenciaDestino, final String contaDestino, final LocalDateTime dataTransferencia, final LocalDateTime dataProcessamento, final String codigoRetorno, final String mensagemRetorno, final String dadosAdicionais) {
        this.codigoPix = codigoPix;
        this.contaOrigem = contaOrigem;
        this.chavePixDestino = chavePixDestino;
        this.nomeDestinatario = nomeDestinatario;
        this.valor = valor;
        this.descricao = descricao;
        this.status = status;
        this.tipoChave = tipoChave;
        this.instituicaoDestino = instituicaoDestino;
        this.agenciaDestino = agenciaDestino;
        this.contaDestino = contaDestino;
        this.dataTransferencia = dataTransferencia;
        this.dataProcessamento = dataProcessamento;
        this.codigoRetorno = codigoRetorno;
        this.mensagemRetorno = mensagemRetorno;
        this.dadosAdicionais = dadosAdicionais;
    }
}
