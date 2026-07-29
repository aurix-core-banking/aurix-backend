package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidade Cliente do Aurix.
 * Representa um cliente do banco, podendo ser pessoa física (PF) ou jurídica (PJ).
 */
@Entity
@Table(name = "clientes", schema = "aurix", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"tenantId", "cpf"}),
    @UniqueConstraint(columnNames = {"tenantId", "cnpj"}),
    @UniqueConstraint(columnNames = {"tenantId", "email"})
})
public class Cliente extends BaseEntity {

    /**
     * Comprimento de um CPF.
     */
    private static final int CPF_LENGTH = 11;
    /**
     * Comprimento de um CNPJ.
     */
    private static final int CNPJ_LENGTH = 14;
    /**
     * Comprimento máximo do nome.
     */
    private static final int NAME_MAX_LENGTH = 255;
    /**
     * Comprimento máximo do telefone.
     */
    private static final int PHONE_MAX_LENGTH = 20;

    /**
     * Tipo de pessoa (física ou jurídica).
     */
    @NotNull(message = "Tipo de pessoa é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa", nullable = false)
    private TipoPessoa tipoPessoa;

    /**
     * CPF do cliente (11 dígitos, apenas números).
     */
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    @Column(name = "cpf", length = CPF_LENGTH)
    private String cpf;

    /**
     * Nome completo do cliente.
     */
    @Size(min = 2, max = NAME_MAX_LENGTH, message = "Nome deve ter entre 2 e 255 caracteres")
    @Column(name = "nome")
    private String nome;

    /**
     * CNPJ do cliente (14 dígitos, apenas números).
     */
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos")
    @Column(name = "cnpj", length = CNPJ_LENGTH)
    private String cnpj;

    /**
     * Razão social do cliente (pessoa jurídica).
     */
    @Column(name = "nome_razao_social", length = NAME_MAX_LENGTH)
    private String nomeRazaoSocial;

    /**
     * Nome fantasia do cliente.
     */
    @Column(name = "nome_fantasia", length = NAME_MAX_LENGTH)
    private String nomeFantasia;

    /**
     * Inscrição estadual do cliente.
     */
    @Column(name = "inscricao_estadual", length = 20)
    private String inscricaoEstadual;

    /**
     * Inscrição municipal do cliente.
     */
    @Column(name = "inscricao_municipal", length = 20)
    private String inscricaoMunicipal;

    /**
     * Faturamento mensal do cliente (pessoa jurídica).
     */
    @Column(name = "faturamento_mensal", precision = 19, scale = 4)
    private BigDecimal faturamentoMensal;

    /**
     * Capital social do cliente (pessoa jurídica).
     */
    @Column(name = "capital_social", precision = 19, scale = 4)
    private BigDecimal capitalSocial;

    /**
     * CNAE principal do cliente (pessoa jurídica).
     */
    @Column(name = "cnae_principal", length = 20)
    private String cnaePrincipal;

    /**
     * Porte da empresa (pessoa jurídica).
     */
    @Column(name = "porte", length = 20)
    private String porte;

    /**
     * Data de constituição do cliente (pessoa jurídica).
     */
    @Column(name = "data_constituicao")
    private LocalDate dataConstituicao;

    /**
     * Endereço de email de contato.
     */
    @Email(message = "Email deve ter formato válido")
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * Telefone de contato (10 ou 11 dígitos).
     */
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos")
    @Column(name = "telefone", length = PHONE_MAX_LENGTH)
    private String telefone;

    /**
     * Data de nascimento do cliente.
     */
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    /**
     * Endereço completo formatado em JSON.
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "endereco", columnDefinition = "jsonb")
    private String endereco;

    /**
     * Cidade do cliente.
     */
    @Column(name = "cidade", length = 100)
    private String cidade;

    /**
     * Estado do cliente (UF, 2 caracteres).
     */
    @Column(name = "estado", length = 2)
    private String estado;

    /**
     * CEP do cliente.
     */
    @Column(name = "cep", length = 10)
    private String cep;

    /**
     * Contato do cliente.
     */
    @Column(name = "contato", length = 100)
    private String contato;

    /**
     * Status atual do cliente no sistema.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusCliente status = StatusCliente.ATIVO;

    /**
     * Enum para tipo de pessoa.
     */
    public enum TipoPessoa {
        /**
         * Pessoa Física.
         */
        FISICA,
        /**
         * Pessoa Jurídica.
         */
        JURIDICA
    }

    /**
     * Enum para status do cliente.
     */
    public enum StatusCliente {
        /**
         * Ativo.
         */
        ATIVO("Ativo"),
        /**
         * Inativo.
         */
        INATIVO("Inativo"),
        /**
         * Bloqueado.
         */
        BLOQUEADO("Bloqueado"),
        /**
         * Suspenso.
         */
        SUSPENSO("Suspenso");
        /**
         * Descrição do status.
         */
        private final String descricao;

        StatusCliente(final String desc) {
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
     * Tipo de pessoa (física ou jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public TipoPessoa getTipoPessoa() {
        return this.tipoPessoa;
    }

    /**
     * CPF do cliente (11 dígitos, apenas números).
     */
    @java.lang.SuppressWarnings("all")
    public String getCpf() {
        return this.cpf;
    }

    /**
     * Nome completo do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    /**
     * CNPJ do cliente (14 dígitos, apenas números).
     */
    @java.lang.SuppressWarnings("all")
    public String getCnpj() {
        return this.cnpj;
    }

    /**
     * Razão social do cliente (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public String getNomeRazaoSocial() {
        return this.nomeRazaoSocial;
    }

    /**
     * Nome fantasia do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public String getNomeFantasia() {
        return this.nomeFantasia;
    }

    /**
     * Inscrição estadual do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public String getInscricaoEstadual() {
        return this.inscricaoEstadual;
    }

    /**
     * Inscrição municipal do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public String getInscricaoMunicipal() {
        return this.inscricaoMunicipal;
    }

    /**
     * Faturamento mensal do cliente (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getFaturamentoMensal() {
        return this.faturamentoMensal;
    }

    /**
     * Capital social do cliente (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getCapitalSocial() {
        return this.capitalSocial;
    }

    /**
     * CNAE principal do cliente (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public String getCnaePrincipal() {
        return this.cnaePrincipal;
    }

    /**
     * Porte da empresa (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public String getPorte() {
        return this.porte;
    }

    /**
     * Data de constituição do cliente (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public LocalDate getDataConstituicao() {
        return this.dataConstituicao;
    }

    /**
     * Endereço de email de contato.
     */
    @java.lang.SuppressWarnings("all")
    public String getEmail() {
        return this.email;
    }

    /**
     * Telefone de contato (10 ou 11 dígitos).
     */
    @java.lang.SuppressWarnings("all")
    public String getTelefone() {
        return this.telefone;
    }

    /**
     * Data de nascimento do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    /**
     * Endereço completo formatado em JSON.
     */
    @java.lang.SuppressWarnings("all")
    public String getEndereco() {
        return this.endereco;
    }

    /**
     * Cidade do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public String getCidade() {
        return this.cidade;
    }

    /**
     * Estado do cliente (UF, 2 caracteres).
     */
    @java.lang.SuppressWarnings("all")
    public String getEstado() {
        return this.estado;
    }

    /**
     * CEP do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public String getCep() {
        return this.cep;
    }

    /**
     * Contato do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public String getContato() {
        return this.contato;
    }

    /**
     * Status atual do cliente no sistema.
     */
    @java.lang.SuppressWarnings("all")
    public StatusCliente getStatus() {
        return this.status;
    }

    /**
     * Tipo de pessoa (física ou jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoPessoa(final TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    /**
     * CPF do cliente (11 dígitos, apenas números).
     */
    @java.lang.SuppressWarnings("all")
    public void setCpf(final String cpf) {
        this.cpf = cpf;
    }

    /**
     * Nome completo do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    /**
     * CNPJ do cliente (14 dígitos, apenas números).
     */
    @java.lang.SuppressWarnings("all")
    public void setCnpj(final String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * Razão social do cliente (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public void setNomeRazaoSocial(final String nomeRazaoSocial) {
        this.nomeRazaoSocial = nomeRazaoSocial;
    }

    /**
     * Nome fantasia do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public void setNomeFantasia(final String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    /**
     * Inscrição estadual do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public void setInscricaoEstadual(final String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    /**
     * Inscrição municipal do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public void setInscricaoMunicipal(final String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    /**
     * Faturamento mensal do cliente (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public void setFaturamentoMensal(final BigDecimal faturamentoMensal) {
        this.faturamentoMensal = faturamentoMensal;
    }

    /**
     * Capital social do cliente (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public void setCapitalSocial(final BigDecimal capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    /**
     * CNAE principal do cliente (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public void setCnaePrincipal(final String cnaePrincipal) {
        this.cnaePrincipal = cnaePrincipal;
    }

    /**
     * Porte da empresa (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public void setPorte(final String porte) {
        this.porte = porte;
    }

    /**
     * Data de constituição do cliente (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public void setDataConstituicao(final LocalDate dataConstituicao) {
        this.dataConstituicao = dataConstituicao;
    }

    /**
     * Endereço de email de contato.
     */
    @java.lang.SuppressWarnings("all")
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Telefone de contato (10 ou 11 dígitos).
     */
    @java.lang.SuppressWarnings("all")
    public void setTelefone(final String telefone) {
        this.telefone = telefone;
    }

    /**
     * Data de nascimento do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataNascimento(final LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * Endereço completo formatado em JSON.
     */
    @java.lang.SuppressWarnings("all")
    public void setEndereco(final String endereco) {
        this.endereco = endereco;
    }

    /**
     * Cidade do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public void setCidade(final String cidade) {
        this.cidade = cidade;
    }

    /**
     * Estado do cliente (UF, 2 caracteres).
     */
    @java.lang.SuppressWarnings("all")
    public void setEstado(final String estado) {
        this.estado = estado;
    }

    /**
     * CEP do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public void setCep(final String cep) {
        this.cep = cep;
    }

    /**
     * Contato do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public void setContato(final String contato) {
        this.contato = contato;
    }

    /**
     * Status atual do cliente no sistema.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusCliente status) {
        this.status = status;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Cliente(tipoPessoa=" + this.getTipoPessoa() + ", cpf=" + this.getCpf() + ", nome=" + this.getNome() + ", cnpj=" + this.getCnpj() + ", nomeRazaoSocial=" + this.getNomeRazaoSocial() + ", nomeFantasia=" + this.getNomeFantasia() + ", inscricaoEstadual=" + this.getInscricaoEstadual() + ", inscricaoMunicipal=" + this.getInscricaoMunicipal() + ", faturamentoMensal=" + this.getFaturamentoMensal() + ", capitalSocial=" + this.getCapitalSocial() + ", cnaePrincipal=" + this.getCnaePrincipal() + ", porte=" + this.getPorte() + ", dataConstituicao=" + this.getDataConstituicao() + ", email=" + this.getEmail() + ", telefone=" + this.getTelefone() + ", dataNascimento=" + this.getDataNascimento() + ", endereco=" + this.getEndereco() + ", cidade=" + this.getCidade() + ", estado=" + this.getEstado() + ", cep=" + this.getCep() + ", contato=" + this.getContato() + ", status=" + this.getStatus() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Cliente)) return false;
        final Cliente other = (Cliente) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$tipoPessoa = this.getTipoPessoa();
        final java.lang.Object other$tipoPessoa = other.getTipoPessoa();
        if (this$tipoPessoa == null ? other$tipoPessoa != null : !this$tipoPessoa.equals(other$tipoPessoa)) return false;
        final java.lang.Object this$cpf = this.getCpf();
        final java.lang.Object other$cpf = other.getCpf();
        if (this$cpf == null ? other$cpf != null : !this$cpf.equals(other$cpf)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$cnpj = this.getCnpj();
        final java.lang.Object other$cnpj = other.getCnpj();
        if (this$cnpj == null ? other$cnpj != null : !this$cnpj.equals(other$cnpj)) return false;
        final java.lang.Object this$nomeRazaoSocial = this.getNomeRazaoSocial();
        final java.lang.Object other$nomeRazaoSocial = other.getNomeRazaoSocial();
        if (this$nomeRazaoSocial == null ? other$nomeRazaoSocial != null : !this$nomeRazaoSocial.equals(other$nomeRazaoSocial)) return false;
        final java.lang.Object this$nomeFantasia = this.getNomeFantasia();
        final java.lang.Object other$nomeFantasia = other.getNomeFantasia();
        if (this$nomeFantasia == null ? other$nomeFantasia != null : !this$nomeFantasia.equals(other$nomeFantasia)) return false;
        final java.lang.Object this$inscricaoEstadual = this.getInscricaoEstadual();
        final java.lang.Object other$inscricaoEstadual = other.getInscricaoEstadual();
        if (this$inscricaoEstadual == null ? other$inscricaoEstadual != null : !this$inscricaoEstadual.equals(other$inscricaoEstadual)) return false;
        final java.lang.Object this$inscricaoMunicipal = this.getInscricaoMunicipal();
        final java.lang.Object other$inscricaoMunicipal = other.getInscricaoMunicipal();
        if (this$inscricaoMunicipal == null ? other$inscricaoMunicipal != null : !this$inscricaoMunicipal.equals(other$inscricaoMunicipal)) return false;
        final java.lang.Object this$email = this.getEmail();
        final java.lang.Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final java.lang.Object this$telefone = this.getTelefone();
        final java.lang.Object other$telefone = other.getTelefone();
        if (this$telefone == null ? other$telefone != null : !this$telefone.equals(other$telefone)) return false;
        final java.lang.Object this$dataNascimento = this.getDataNascimento();
        final java.lang.Object other$dataNascimento = other.getDataNascimento();
        if (this$dataNascimento == null ? other$dataNascimento != null : !this$dataNascimento.equals(other$dataNascimento)) return false;
        final java.lang.Object this$endereco = this.getEndereco();
        final java.lang.Object other$endereco = other.getEndereco();
        if (this$endereco == null ? other$endereco != null : !this$endereco.equals(other$endereco)) return false;
        final java.lang.Object this$cidade = this.getCidade();
        final java.lang.Object other$cidade = other.getCidade();
        if (this$cidade == null ? other$cidade != null : !this$cidade.equals(other$cidade)) return false;
        final java.lang.Object this$estado = this.getEstado();
        final java.lang.Object other$estado = other.getEstado();
        if (this$estado == null ? other$estado != null : !this$estado.equals(other$estado)) return false;
        final java.lang.Object this$cep = this.getCep();
        final java.lang.Object other$cep = other.getCep();
        if (this$cep == null ? other$cep != null : !this$cep.equals(other$cep)) return false;
        final java.lang.Object this$contato = this.getContato();
        final java.lang.Object other$contato = other.getContato();
        if (this$contato == null ? other$contato != null : !this$contato.equals(other$contato)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$faturamentoMensal = this.getFaturamentoMensal();
        final java.lang.Object other$faturamentoMensal = other.getFaturamentoMensal();
        if (this$faturamentoMensal == null ? other$faturamentoMensal != null : !this$faturamentoMensal.equals(other$faturamentoMensal)) return false;
        final java.lang.Object this$capitalSocial = this.getCapitalSocial();
        final java.lang.Object other$capitalSocial = other.getCapitalSocial();
        if (this$capitalSocial == null ? other$capitalSocial != null : !this$capitalSocial.equals(other$capitalSocial)) return false;
        final java.lang.Object this$cnaePrincipal = this.getCnaePrincipal();
        final java.lang.Object other$cnaePrincipal = other.getCnaePrincipal();
        if (this$cnaePrincipal == null ? other$cnaePrincipal != null : !this$cnaePrincipal.equals(other$cnaePrincipal)) return false;
        final java.lang.Object this$porte = this.getPorte();
        final java.lang.Object other$porte = other.getPorte();
        if (this$porte == null ? other$porte != null : !this$porte.equals(other$porte)) return false;
        final java.lang.Object this$dataConstituicao = this.getDataConstituicao();
        final java.lang.Object other$dataConstituicao = other.getDataConstituicao();
        if (this$dataConstituicao == null ? other$dataConstituicao != null : !this$dataConstituicao.equals(other$dataConstituicao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Cliente;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $tipoPessoa = this.getTipoPessoa();
        result = result * PRIME + ($tipoPessoa == null ? 43 : $tipoPessoa.hashCode());
        final java.lang.Object $cpf = this.getCpf();
        result = result * PRIME + ($cpf == null ? 43 : $cpf.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $cnpj = this.getCnpj();
        result = result * PRIME + ($cnpj == null ? 43 : $cnpj.hashCode());
        final java.lang.Object $nomeRazaoSocial = this.getNomeRazaoSocial();
        result = result * PRIME + ($nomeRazaoSocial == null ? 43 : $nomeRazaoSocial.hashCode());
        final java.lang.Object $nomeFantasia = this.getNomeFantasia();
        result = result * PRIME + ($nomeFantasia == null ? 43 : $nomeFantasia.hashCode());
        final java.lang.Object $inscricaoEstadual = this.getInscricaoEstadual();
        result = result * PRIME + ($inscricaoEstadual == null ? 43 : $inscricaoEstadual.hashCode());
        final java.lang.Object $inscricaoMunicipal = this.getInscricaoMunicipal();
        result = result * PRIME + ($inscricaoMunicipal == null ? 43 : $inscricaoMunicipal.hashCode());
        final java.lang.Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final java.lang.Object $telefone = this.getTelefone();
        result = result * PRIME + ($telefone == null ? 43 : $telefone.hashCode());
        final java.lang.Object $dataNascimento = this.getDataNascimento();
        result = result * PRIME + ($dataNascimento == null ? 43 : $dataNascimento.hashCode());
        final java.lang.Object $endereco = this.getEndereco();
        result = result * PRIME + ($endereco == null ? 43 : $endereco.hashCode());
        final java.lang.Object $cidade = this.getCidade();
        result = result * PRIME + ($cidade == null ? 43 : $cidade.hashCode());
        final java.lang.Object $estado = this.getEstado();
        result = result * PRIME + ($estado == null ? 43 : $estado.hashCode());
        final java.lang.Object $cep = this.getCep();
        result = result * PRIME + ($cep == null ? 43 : $cep.hashCode());
        final java.lang.Object $contato = this.getContato();
        result = result * PRIME + ($contato == null ? 43 : $contato.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $faturamentoMensal = this.getFaturamentoMensal();
        result = result * PRIME + ($faturamentoMensal == null ? 43 : $faturamentoMensal.hashCode());
        final java.lang.Object $capitalSocial = this.getCapitalSocial();
        result = result * PRIME + ($capitalSocial == null ? 43 : $capitalSocial.hashCode());
        final java.lang.Object $cnaePrincipal = this.getCnaePrincipal();
        result = result * PRIME + ($cnaePrincipal == null ? 43 : $cnaePrincipal.hashCode());
        final java.lang.Object $porte = this.getPorte();
        result = result * PRIME + ($porte == null ? 43 : $porte.hashCode());
        final java.lang.Object $dataConstituicao = this.getDataConstituicao();
        result = result * PRIME + ($dataConstituicao == null ? 43 : $dataConstituicao.hashCode());
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public Cliente() {
    }

    @java.lang.SuppressWarnings("all")
    public Cliente(final TipoPessoa tipoPessoa, final String cpf, final String nome, final String cnpj, final String nomeRazaoSocial, final String nomeFantasia, final String inscricaoEstadual, final String inscricaoMunicipal, final BigDecimal faturamentoMensal, final BigDecimal capitalSocial, final String cnaePrincipal, final String porte, final LocalDate dataConstituicao, final String email, final String telefone, final LocalDate dataNascimento, final String endereco, final String cidade, final String estado, final String cep, final String contato, final StatusCliente status) {
        this.tipoPessoa = tipoPessoa;
        this.cpf = cpf;
        this.nome = nome;
        this.cnpj = cnpj;
        this.nomeRazaoSocial = nomeRazaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.inscricaoEstadual = inscricaoEstadual;
        this.inscricaoMunicipal = inscricaoMunicipal;
        this.faturamentoMensal = faturamentoMensal;
        this.capitalSocial = capitalSocial;
        this.cnaePrincipal = cnaePrincipal;
        this.porte = porte;
        this.dataConstituicao = dataConstituicao;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.contato = contato;
        this.status = status;
    }
}
