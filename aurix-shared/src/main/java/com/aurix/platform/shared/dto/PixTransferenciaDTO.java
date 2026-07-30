package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.PixTransferencia;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para PIX Transferência do Aurix.
 */
public class PixTransferenciaDTO {
    /**
     * Tamanho máximo da chave PIX: 77 caracteres.
     */
    private static final int MAX_KEY_SIZE = 77;
    /**
     * Tamanho máximo de nomes e descrições: 140 caracteres.
     */
    private static final int MAX_TEXT_SIZE = 140;
    /**
     * Valor mínimo de transferência: 0.01.
     */
    private static final String MIN_TRANSFER_VALUE = "0.01";
    /**
     * ID da transferência.
     */
    private Long id;
    /**
     * Código de identificação único do PIX.
     */
    private String codigoPix;
    /**
     * ID da conta de origem.
     */
    @NotNull(message = "Conta origem é obrigatória")
    private Long contaOrigemId;
    /**
     * Número da conta de origem.
     */
    private String contaOrigemNumero;
    /**
     * Chave PIX do destinatário.
     */
    @NotBlank(message = "Chave PIX destino é obrigatória")
    @Size(max = MAX_KEY_SIZE, message = "Chave PIX deve ter no máximo 77 caracteres")
    private String chavePixDestino;
    /**
     * Nome completo do destinatário.
     */
    @NotBlank(message = "Nome do destinatário é obrigatório")
    @Size(max = MAX_TEXT_SIZE, message = "Nome do destinatário deve ter no máximo 140 caracteres")
    private String nomeDestinatario;
    /**
     * Valor da transferência.
     */
    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = MIN_TRANSFER_VALUE, message = "Valor deve ser maior que zero")
    private BigDecimal valor;
    /**
     * Descrição ou mensagem enviada com o PIX.
     */
    @Size(max = MAX_TEXT_SIZE, message = "Descrição deve ter no máximo 140 caracteres")
    private String descricao;
    /**
     * Status atual da transferência.
     */
    private PixTransferencia.StatusPix status;
    /**
     * Tipo da chave utilizada pelo destinatário.
     */
    private PixTransferencia.TipoChavePix tipoChave;
    /**
     * Nome da instituição financeira de destino.
     */
    private String instituicaoDestino;
    /**
     * Número da agência de destino.
     */
    private String agenciaDestino;
    /**
     * Número da conta de destino.
     */
    private String contaDestino;
    /**
     * Data e hora da solicitação da transferência.
     */
    private LocalDateTime dataTransferencia;
    /**
     * Data e hora do processamento efetivo.
     */
    private LocalDateTime dataProcessamento;
    /**
     * Código de retorno retornado pelo BACEN/Instituição.
     */
    private String codigoRetorno;
    /**
     * Mensagem amigável de retorno.
     */
    private String mensagemRetorno;
    /**
     * Detalhes operacionais extras em formato JSON.
     */
    private String dadosAdicionais;
    /**
     * Data de criação do registro.
     */
    private String dataCriacao;
    /**
     * Data da última atualização.
     */
    private String dataAtualizacao;

    /**
     * ID da transferência.
     */
    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    /**
     * Código de identificação único do PIX.
     */
    @java.lang.SuppressWarnings("all")
    public String getCodigoPix() {
        return this.codigoPix;
    }

    /**
     * ID da conta de origem.
     */
    @java.lang.SuppressWarnings("all")
    public Long getContaOrigemId() {
        return this.contaOrigemId;
    }

    /**
     * Número da conta de origem.
     */
    @java.lang.SuppressWarnings("all")
    public String getContaOrigemNumero() {
        return this.contaOrigemNumero;
    }

    /**
     * Chave PIX do destinatário.
     */
    @java.lang.SuppressWarnings("all")
    public String getChavePixDestino() {
        return this.chavePixDestino;
    }

    /**
     * Nome completo do destinatário.
     */
    @java.lang.SuppressWarnings("all")
    public String getNomeDestinatario() {
        return this.nomeDestinatario;
    }

    /**
     * Valor da transferência.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValor() {
        return this.valor;
    }

    /**
     * Descrição ou mensagem enviada com o PIX.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Status atual da transferência.
     */
    @java.lang.SuppressWarnings("all")
    public PixTransferencia.StatusPix getStatus() {
        return this.status;
    }

    /**
     * Tipo da chave utilizada pelo destinatário.
     */
    @java.lang.SuppressWarnings("all")
    public PixTransferencia.TipoChavePix getTipoChave() {
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
     * Data e hora do processamento efetivo.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataProcessamento() {
        return this.dataProcessamento;
    }

    /**
     * Código de retorno retornado pelo BACEN/Instituição.
     */
    @java.lang.SuppressWarnings("all")
    public String getCodigoRetorno() {
        return this.codigoRetorno;
    }

    /**
     * Mensagem amigável de retorno.
     */
    @java.lang.SuppressWarnings("all")
    public String getMensagemRetorno() {
        return this.mensagemRetorno;
    }

    /**
     * Detalhes operacionais extras em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosAdicionais() {
        return this.dadosAdicionais;
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
     * ID da transferência.
     */
    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Código de identificação único do PIX.
     */
    @java.lang.SuppressWarnings("all")
    public void setCodigoPix(final String codigoPix) {
        this.codigoPix = codigoPix;
    }

    /**
     * ID da conta de origem.
     */
    @java.lang.SuppressWarnings("all")
    public void setContaOrigemId(final Long contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }

    /**
     * Número da conta de origem.
     */
    @java.lang.SuppressWarnings("all")
    public void setContaOrigemNumero(final String contaOrigemNumero) {
        this.contaOrigemNumero = contaOrigemNumero;
    }

    /**
     * Chave PIX do destinatário.
     */
    @java.lang.SuppressWarnings("all")
    public void setChavePixDestino(final String chavePixDestino) {
        this.chavePixDestino = chavePixDestino;
    }

    /**
     * Nome completo do destinatário.
     */
    @java.lang.SuppressWarnings("all")
    public void setNomeDestinatario(final String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }

    /**
     * Valor da transferência.
     */
    @java.lang.SuppressWarnings("all")
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * Descrição ou mensagem enviada com o PIX.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Status atual da transferência.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final PixTransferencia.StatusPix status) {
        this.status = status;
    }

    /**
     * Tipo da chave utilizada pelo destinatário.
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoChave(final PixTransferencia.TipoChavePix tipoChave) {
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
     * Data e hora do processamento efetivo.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataProcessamento(final LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    /**
     * Código de retorno retornado pelo BACEN/Instituição.
     */
    @java.lang.SuppressWarnings("all")
    public void setCodigoRetorno(final String codigoRetorno) {
        this.codigoRetorno = codigoRetorno;
    }

    /**
     * Mensagem amigável de retorno.
     */
    @java.lang.SuppressWarnings("all")
    public void setMensagemRetorno(final String mensagemRetorno) {
        this.mensagemRetorno = mensagemRetorno;
    }

    /**
     * Detalhes operacionais extras em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosAdicionais(final String dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
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
        if (!(o instanceof PixTransferenciaDTO)) return false;
        final PixTransferenciaDTO other = (PixTransferenciaDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$contaOrigemId = this.getContaOrigemId();
        final java.lang.Object other$contaOrigemId = other.getContaOrigemId();
        if (this$contaOrigemId == null ? other$contaOrigemId != null : !this$contaOrigemId.equals(other$contaOrigemId)) return false;
        final java.lang.Object this$codigoPix = this.getCodigoPix();
        final java.lang.Object other$codigoPix = other.getCodigoPix();
        if (this$codigoPix == null ? other$codigoPix != null : !this$codigoPix.equals(other$codigoPix)) return false;
        final java.lang.Object this$contaOrigemNumero = this.getContaOrigemNumero();
        final java.lang.Object other$contaOrigemNumero = other.getContaOrigemNumero();
        if (this$contaOrigemNumero == null ? other$contaOrigemNumero != null : !this$contaOrigemNumero.equals(other$contaOrigemNumero)) return false;
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
        return other instanceof PixTransferenciaDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $contaOrigemId = this.getContaOrigemId();
        result = result * PRIME + ($contaOrigemId == null ? 43 : $contaOrigemId.hashCode());
        final java.lang.Object $codigoPix = this.getCodigoPix();
        result = result * PRIME + ($codigoPix == null ? 43 : $codigoPix.hashCode());
        final java.lang.Object $contaOrigemNumero = this.getContaOrigemNumero();
        result = result * PRIME + ($contaOrigemNumero == null ? 43 : $contaOrigemNumero.hashCode());
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
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "PixTransferenciaDTO(id=" + this.getId() + ", codigoPix=" + this.getCodigoPix() + ", contaOrigemId=" + this.getContaOrigemId() + ", contaOrigemNumero=" + this.getContaOrigemNumero() + ", chavePixDestino=" + this.getChavePixDestino() + ", nomeDestinatario=" + this.getNomeDestinatario() + ", valor=" + this.getValor() + ", descricao=" + this.getDescricao() + ", status=" + this.getStatus() + ", tipoChave=" + this.getTipoChave() + ", instituicaoDestino=" + this.getInstituicaoDestino() + ", agenciaDestino=" + this.getAgenciaDestino() + ", contaDestino=" + this.getContaDestino() + ", dataTransferencia=" + this.getDataTransferencia() + ", dataProcessamento=" + this.getDataProcessamento() + ", codigoRetorno=" + this.getCodigoRetorno() + ", mensagemRetorno=" + this.getMensagemRetorno() + ", dadosAdicionais=" + this.getDadosAdicionais() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public PixTransferenciaDTO() {
    }

    /**
     * Creates a new {@code PixTransferenciaDTO} instance.
     *
     * @param id ID da transferência.
     * @param codigoPix Código de identificação único do PIX.
     * @param contaOrigemId ID da conta de origem.
     * @param contaOrigemNumero Número da conta de origem.
     * @param chavePixDestino Chave PIX do destinatário.
     * @param nomeDestinatario Nome completo do destinatário.
     * @param valor Valor da transferência.
     * @param descricao Descrição ou mensagem enviada com o PIX.
     * @param status Status atual da transferência.
     * @param tipoChave Tipo da chave utilizada pelo destinatário.
     * @param instituicaoDestino Nome da instituição financeira de destino.
     * @param agenciaDestino Número da agência de destino.
     * @param contaDestino Número da conta de destino.
     * @param dataTransferencia Data e hora da solicitação da transferência.
     * @param dataProcessamento Data e hora do processamento efetivo.
     * @param codigoRetorno Código de retorno retornado pelo BACEN/Instituição.
     * @param mensagemRetorno Mensagem amigável de retorno.
     * @param dadosAdicionais Detalhes operacionais extras em formato JSON.
     * @param dataCriacao Data de criação do registro.
     * @param dataAtualizacao Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public PixTransferenciaDTO(final Long id, final String codigoPix, final Long contaOrigemId, final String contaOrigemNumero, final String chavePixDestino, final String nomeDestinatario, final BigDecimal valor, final String descricao, final PixTransferencia.StatusPix status, final PixTransferencia.TipoChavePix tipoChave, final String instituicaoDestino, final String agenciaDestino, final String contaDestino, final LocalDateTime dataTransferencia, final LocalDateTime dataProcessamento, final String codigoRetorno, final String mensagemRetorno, final String dadosAdicionais, final String dataCriacao, final String dataAtualizacao) {
        this.id = id;
        this.codigoPix = codigoPix;
        this.contaOrigemId = contaOrigemId;
        this.contaOrigemNumero = contaOrigemNumero;
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
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
