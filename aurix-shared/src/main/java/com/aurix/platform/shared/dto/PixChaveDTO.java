package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.PixChave;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * DTO para PIX Chave do Aurix.
 */
public class PixChaveDTO {
    /**
     * Tamanho máximo da chave PIX: 77 caracteres.
     */
    private static final int MAX_KEY_SIZE = 77;
    /**
     * Tamanho máximo do nome do titular: 140 caracteres.
     */
    private static final int MAX_NAME_SIZE = 140;
    /**
     * ID da chave PIX.
     */
    private Long id;
    /**
     * Valor da chave PIX (CPF, Email, Telefone, Chave Aleatória).
     */
    @NotBlank(message = "Chave PIX é obrigatória")
    @Size(max = MAX_KEY_SIZE, message = "Chave PIX deve ter no máximo 77 caracteres")
    private String chavePix;
    /**
     * ID da conta vinculada.
     */
    @NotNull(message = "Conta é obrigatória")
    private Long contaId;
    /**
     * Número da conta vinculada.
     */
    private String contaNumero;
    /**
     * Tipo da chave PIX.
     */
    @NotNull(message = "Tipo da chave é obrigatório")
    private PixChave.TipoChavePix tipoChave;
    /**
     * Nome completo do titular da conta.
     */
    @NotBlank(message = "Nome do titular é obrigatório")
    @Size(max = MAX_NAME_SIZE, message = "Nome do titular deve ter no máximo 140 caracteres")
    private String nomeTitular;
    /**
     * Status atual da chave PIX.
     */
    private PixChave.StatusChavePix status;
    /**
     * Data em que a chave foi cadastrada.
     */
    private LocalDateTime dataCadastro;
    /**
     * Data em que a chave foi inativada, se aplicável.
     */
    private LocalDateTime dataInativacao;
    /**
     * Detalhes adicionais em formato JSON.
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
     * ID da chave PIX.
     */
    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    /**
     * Valor da chave PIX (CPF, Email, Telefone, Chave Aleatória).
     */
    @java.lang.SuppressWarnings("all")
    public String getChavePix() {
        return this.chavePix;
    }

    /**
     * ID da conta vinculada.
     */
    @java.lang.SuppressWarnings("all")
    public Long getContaId() {
        return this.contaId;
    }

    /**
     * Número da conta vinculada.
     */
    @java.lang.SuppressWarnings("all")
    public String getContaNumero() {
        return this.contaNumero;
    }

    /**
     * Tipo da chave PIX.
     */
    @java.lang.SuppressWarnings("all")
    public PixChave.TipoChavePix getTipoChave() {
        return this.tipoChave;
    }

    /**
     * Nome completo do titular da conta.
     */
    @java.lang.SuppressWarnings("all")
    public String getNomeTitular() {
        return this.nomeTitular;
    }

    /**
     * Status atual da chave PIX.
     */
    @java.lang.SuppressWarnings("all")
    public PixChave.StatusChavePix getStatus() {
        return this.status;
    }

    /**
     * Data em que a chave foi cadastrada.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataCadastro() {
        return this.dataCadastro;
    }

    /**
     * Data em que a chave foi inativada, se aplicável.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataInativacao() {
        return this.dataInativacao;
    }

    /**
     * Detalhes adicionais em formato JSON.
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
     * ID da chave PIX.
     */
    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Valor da chave PIX (CPF, Email, Telefone, Chave Aleatória).
     */
    @java.lang.SuppressWarnings("all")
    public void setChavePix(final String chavePix) {
        this.chavePix = chavePix;
    }

    /**
     * ID da conta vinculada.
     */
    @java.lang.SuppressWarnings("all")
    public void setContaId(final Long contaId) {
        this.contaId = contaId;
    }

    /**
     * Número da conta vinculada.
     */
    @java.lang.SuppressWarnings("all")
    public void setContaNumero(final String contaNumero) {
        this.contaNumero = contaNumero;
    }

    /**
     * Tipo da chave PIX.
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoChave(final PixChave.TipoChavePix tipoChave) {
        this.tipoChave = tipoChave;
    }

    /**
     * Nome completo do titular da conta.
     */
    @java.lang.SuppressWarnings("all")
    public void setNomeTitular(final String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    /**
     * Status atual da chave PIX.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final PixChave.StatusChavePix status) {
        this.status = status;
    }

    /**
     * Data em que a chave foi cadastrada.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataCadastro(final LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    /**
     * Data em que a chave foi inativada, se aplicável.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataInativacao(final LocalDateTime dataInativacao) {
        this.dataInativacao = dataInativacao;
    }

    /**
     * Detalhes adicionais em formato JSON.
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
        if (!(o instanceof PixChaveDTO)) return false;
        final PixChaveDTO other = (PixChaveDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$contaId = this.getContaId();
        final java.lang.Object other$contaId = other.getContaId();
        if (this$contaId == null ? other$contaId != null : !this$contaId.equals(other$contaId)) return false;
        final java.lang.Object this$chavePix = this.getChavePix();
        final java.lang.Object other$chavePix = other.getChavePix();
        if (this$chavePix == null ? other$chavePix != null : !this$chavePix.equals(other$chavePix)) return false;
        final java.lang.Object this$contaNumero = this.getContaNumero();
        final java.lang.Object other$contaNumero = other.getContaNumero();
        if (this$contaNumero == null ? other$contaNumero != null : !this$contaNumero.equals(other$contaNumero)) return false;
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
        return other instanceof PixChaveDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $contaId = this.getContaId();
        result = result * PRIME + ($contaId == null ? 43 : $contaId.hashCode());
        final java.lang.Object $chavePix = this.getChavePix();
        result = result * PRIME + ($chavePix == null ? 43 : $chavePix.hashCode());
        final java.lang.Object $contaNumero = this.getContaNumero();
        result = result * PRIME + ($contaNumero == null ? 43 : $contaNumero.hashCode());
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
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "PixChaveDTO(id=" + this.getId() + ", chavePix=" + this.getChavePix() + ", contaId=" + this.getContaId() + ", contaNumero=" + this.getContaNumero() + ", tipoChave=" + this.getTipoChave() + ", nomeTitular=" + this.getNomeTitular() + ", status=" + this.getStatus() + ", dataCadastro=" + this.getDataCadastro() + ", dataInativacao=" + this.getDataInativacao() + ", dadosAdicionais=" + this.getDadosAdicionais() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public PixChaveDTO() {
    }

    /**
     * Creates a new {@code PixChaveDTO} instance.
     *
     * @param id ID da chave PIX.
     * @param chavePix Valor da chave PIX (CPF, Email, Telefone, Chave Aleatória).
     * @param contaId ID da conta vinculada.
     * @param contaNumero Número da conta vinculada.
     * @param tipoChave Tipo da chave PIX.
     * @param nomeTitular Nome completo do titular da conta.
     * @param status Status atual da chave PIX.
     * @param dataCadastro Data em que a chave foi cadastrada.
     * @param dataInativacao Data em que a chave foi inativada, se aplicável.
     * @param dadosAdicionais Detalhes adicionais em formato JSON.
     * @param dataCriacao Data de criação do registro.
     * @param dataAtualizacao Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public PixChaveDTO(final Long id, final String chavePix, final Long contaId, final String contaNumero, final PixChave.TipoChavePix tipoChave, final String nomeTitular, final PixChave.StatusChavePix status, final LocalDateTime dataCadastro, final LocalDateTime dataInativacao, final String dadosAdicionais, final String dataCriacao, final String dataAtualizacao) {
        this.id = id;
        this.chavePix = chavePix;
        this.contaId = contaId;
        this.contaNumero = contaNumero;
        this.tipoChave = tipoChave;
        this.nomeTitular = nomeTitular;
        this.status = status;
        this.dataCadastro = dataCadastro;
        this.dataInativacao = dataInativacao;
        this.dadosAdicionais = dadosAdicionais;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
