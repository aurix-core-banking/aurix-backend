package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para Cliente do Aurix.
 */
public class ClienteDTO {

    /**
     * Tamanho do CPF: 11 dígitos.
     */
    private static final int CPF_SIZE = 11;
    /**
     * Tamanho do CNPJ: 14 dígitos.
     */
    private static final int CNPJ_SIZE = 14;
    /**
     * Tamanho mínimo do nome.
     */
    private static final int MIN_NOME_SIZE = 2;
    /**
     * Tamanho máximo do nome.
     */
    private static final int MAX_NOME_SIZE = 255;
    /**
     * ID do cliente.
     */
    private Long id;
    /**
     * Tipo de pessoa (física ou jurídica).
     */
    private Cliente.TipoPessoa tipoPessoa;
    /**
     * CPF do cliente.
     */
    @Pattern(regexp = "\\d{" + CPF_SIZE + "}", message = "CPF deve conter 11 dígitos")
    private String cpf;
    /**
     * Nome completo do cliente.
     */
    @Size(min = MIN_NOME_SIZE, max = MAX_NOME_SIZE, message = "Nome deve ter entre 2 e 255 caracteres")
    private String nome;
    /**
     * CNPJ do cliente.
     */
    @Pattern(regexp = "\\d{" + CNPJ_SIZE + "}", message = "CNPJ deve conter 14 dígitos")
    private String cnpj;
    /**
     * Razão social do cliente (pessoa jurídica).
     */
    private String nomeRazaoSocial;
    /**
     * Nome fantasia do cliente.
     */
    private String nomeFantasia;
    /**
     * Inscrição estadual do cliente.
     */
    private String inscricaoEstadual;
    /**
     * Inscrição municipal do cliente.
     */
    private String inscricaoMunicipal;
    /**
     * Email de contato.
     */
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    private String email;
    /**
     * Telefone de contato.
     */
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos")
    private String telefone;
    /**
     * Data de nascimento.
     */
    private LocalDate dataNascimento;
    /**
     * Endereço (formato JSON).
     */
    private String endereco;
    /**
     * Cidade do cliente.
     */
    private String cidade;
    /**
     * Estado do cliente (UF).
     */
    private String estado;
    /**
     * CEP do cliente.
     */
    private String cep;
    /**
     * Contato do cliente.
     */
    private String contato;
    /**
     * Status atual do cliente.
     */
    private Cliente.StatusCliente status;
    /**
     * Data de criação do registro.
     */
    private String dataCriacao;
    /**
     * Data da última atualização.
     */
    private String dataAtualizacao;
    /**
     * Faturamento mensal (pessoa jurídica).
     */
    private BigDecimal faturamentoMensal;
    /**
     * Capital social (pessoa jurídica).
     */
    private BigDecimal capitalSocial;
    /**
     * CNAE principal (pessoa jurídica).
     */
    private String cnaePrincipal;
    /**
     * Porte da empresa (pessoa jurídica).
     */
    private String porte;
    /**
     * Data de constituição (pessoa jurídica).
     */
    private LocalDate dataConstituicao;

    /**
     * ID do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    /**
     * Tipo de pessoa (física ou jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public Cliente.TipoPessoa getTipoPessoa() {
        return this.tipoPessoa;
    }

    /**
     * CPF do cliente.
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
     * CNPJ do cliente.
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
     * Email de contato.
     */
    @java.lang.SuppressWarnings("all")
    public String getEmail() {
        return this.email;
    }

    /**
     * Telefone de contato.
     */
    @java.lang.SuppressWarnings("all")
    public String getTelefone() {
        return this.telefone;
    }

    /**
     * Data de nascimento.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    /**
     * Endereço (formato JSON).
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
     * Estado do cliente (UF).
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
     * Status atual do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public Cliente.StatusCliente getStatus() {
        return this.status;
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
     * Faturamento mensal (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getFaturamentoMensal() {
        return this.faturamentoMensal;
    }

    /**
     * Capital social (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getCapitalSocial() {
        return this.capitalSocial;
    }

    /**
     * CNAE principal (pessoa jurídica).
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
     * Data de constituição (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public LocalDate getDataConstituicao() {
        return this.dataConstituicao;
    }

    /**
     * ID do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Tipo de pessoa (física ou jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoPessoa(final Cliente.TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    /**
     * CPF do cliente.
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
     * CNPJ do cliente.
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
     * Email de contato.
     */
    @java.lang.SuppressWarnings("all")
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Telefone de contato.
     */
    @java.lang.SuppressWarnings("all")
    public void setTelefone(final String telefone) {
        this.telefone = telefone;
    }

    /**
     * Data de nascimento.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataNascimento(final LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * Endereço (formato JSON).
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
     * Estado do cliente (UF).
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
     * Status atual do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final Cliente.StatusCliente status) {
        this.status = status;
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

    /**
     * Faturamento mensal (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public void setFaturamentoMensal(final BigDecimal faturamentoMensal) {
        this.faturamentoMensal = faturamentoMensal;
    }

    /**
     * Capital social (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public void setCapitalSocial(final BigDecimal capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    /**
     * CNAE principal (pessoa jurídica).
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
     * Data de constituição (pessoa jurídica).
     */
    @java.lang.SuppressWarnings("all")
    public void setDataConstituicao(final LocalDate dataConstituicao) {
        this.dataConstituicao = dataConstituicao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ClienteDTO)) return false;
        final ClienteDTO other = (ClienteDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
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
        final java.lang.Object this$dataCriacao = this.getDataCriacao();
        final java.lang.Object other$dataCriacao = other.getDataCriacao();
        if (this$dataCriacao == null ? other$dataCriacao != null : !this$dataCriacao.equals(other$dataCriacao)) return false;
        final java.lang.Object this$dataAtualizacao = this.getDataAtualizacao();
        final java.lang.Object other$dataAtualizacao = other.getDataAtualizacao();
        if (this$dataAtualizacao == null ? other$dataAtualizacao != null : !this$dataAtualizacao.equals(other$dataAtualizacao)) return false;
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
        return other instanceof ClienteDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
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
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
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

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ClienteDTO(id=" + this.getId() + ", tipoPessoa=" + this.getTipoPessoa() + ", cpf=" + this.getCpf() + ", nome=" + this.getNome() + ", cnpj=" + this.getCnpj() + ", nomeRazaoSocial=" + this.getNomeRazaoSocial() + ", nomeFantasia=" + this.getNomeFantasia() + ", inscricaoEstadual=" + this.getInscricaoEstadual() + ", inscricaoMunicipal=" + this.getInscricaoMunicipal() + ", email=" + this.getEmail() + ", telefone=" + this.getTelefone() + ", dataNascimento=" + this.getDataNascimento() + ", endereco=" + this.getEndereco() + ", cidade=" + this.getCidade() + ", estado=" + this.getEstado() + ", cep=" + this.getCep() + ", contato=" + this.getContato() + ", status=" + this.getStatus() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", faturamentoMensal=" + this.getFaturamentoMensal() + ", capitalSocial=" + this.getCapitalSocial() + ", cnaePrincipal=" + this.getCnaePrincipal() + ", porte=" + this.getPorte() + ", dataConstituicao=" + this.getDataConstituicao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ClienteDTO() {
    }

    @java.lang.SuppressWarnings("all")
    public ClienteDTO(final Long id, final Cliente.TipoPessoa tipoPessoa, final String cpf, final String nome, final String cnpj, final String nomeRazaoSocial, final String nomeFantasia, final String inscricaoEstadual, final String inscricaoMunicipal, final String email, final String telefone, final LocalDate dataNascimento, final String endereco, final String cidade, final String estado, final String cep, final String contato, final Cliente.StatusCliente status, final String dataCriacao, final String dataAtualizacao, final BigDecimal faturamentoMensal, final BigDecimal capitalSocial, final String cnaePrincipal, final String porte, final LocalDate dataConstituicao) {
        this.id = id;
        this.tipoPessoa = tipoPessoa;
        this.cpf = cpf;
        this.nome = nome;
        this.cnpj = cnpj;
        this.nomeRazaoSocial = nomeRazaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.inscricaoEstadual = inscricaoEstadual;
        this.inscricaoMunicipal = inscricaoMunicipal;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.contato = contato;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.faturamentoMensal = faturamentoMensal;
        this.capitalSocial = capitalSocial;
        this.cnaePrincipal = cnaePrincipal;
        this.porte = porte;
        this.dataConstituicao = dataConstituicao;
    }
}
