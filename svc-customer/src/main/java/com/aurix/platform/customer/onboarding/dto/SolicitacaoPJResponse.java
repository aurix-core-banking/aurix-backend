package com.aurix.platform.customer.onboarding.dto;

import com.aurix.platform.customer.onboarding.entity.SolicitacaoOnboarding;
import com.aurix.platform.customer.onboarding.entity.SolicitacaoPJ;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitacaoPJResponse {
    private Long id;
    private String status;
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String email;
    private String telefone;
    private String endereco;
    private Long clienteIdCriado;
    private Long contaIdCriada;
    private String observacoesAnalista;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private List<SolicitacaoContaResponse.DocumentoResumo> documentos;
    private List<SolicitacaoContaResponse.HistoricoResumo> historico;

    @java.lang.SuppressWarnings("all")
    public SolicitacaoPJResponse() {
    }

    public static SolicitacaoPJResponse from(SolicitacaoOnboarding onboarding, SolicitacaoPJ pj) {
        return SolicitacaoPJResponse.builder()
            .id(onboarding.getId())
            .status(onboarding.getStatus() != null ? onboarding.getStatus().name() : null)
            .cnpj(pj != null ? pj.getCnpj() : null)
            .razaoSocial(pj != null ? pj.getRazaoSocial() : null)
            .nomeFantasia(pj != null ? pj.getNomeFantasia() : null)
            .email(onboarding.getEmail())
            .telefone(onboarding.getTelefone())
            .endereco(onboarding.getEndereco())
            .clienteIdCriado(onboarding.getClienteIdCriado())
            .contaIdCriada(onboarding.getContaIdCriada())
            .observacoesAnalista(onboarding.getObservacoesAnalista())
            .dataCriacao(onboarding.getDataCriacao())
            .dataAtualizacao(onboarding.getDataAtualizacao())
            .documentos(onboarding.getDocumentos() != null ? onboarding.getDocumentos().stream().map(d -> new SolicitacaoContaResponse.DocumentoResumo(d.getId(), d.getTipoDocumento(), d.getNomeArquivo(), d.getValidado())).collect(Collectors.toList()) : List.of())
            .historico(onboarding.getHistorico() != null ? onboarding.getHistorico().stream().map(h -> new SolicitacaoContaResponse.HistoricoResumo(h.getAcao(), h.getUsuarioAnalista(), h.getDataAcao())).collect(Collectors.toList()) : List.of())
            .build();
    }

    @java.lang.SuppressWarnings("all")
    public static class SolicitacaoPJResponseBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String status;
        @java.lang.SuppressWarnings("all")
        private String cnpj;
        @java.lang.SuppressWarnings("all")
        private String razaoSocial;
        @java.lang.SuppressWarnings("all")
        private String nomeFantasia;
        @java.lang.SuppressWarnings("all")
        private String email;
        @java.lang.SuppressWarnings("all")
        private String telefone;
        @java.lang.SuppressWarnings("all")
        private String endereco;
        @java.lang.SuppressWarnings("all")
        private Long clienteIdCriado;
        @java.lang.SuppressWarnings("all")
        private Long contaIdCriada;
        @java.lang.SuppressWarnings("all")
        private String observacoesAnalista;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;
        @java.lang.SuppressWarnings("all")
        private List<SolicitacaoContaResponse.DocumentoResumo> documentos;
        @java.lang.SuppressWarnings("all")
        private List<SolicitacaoContaResponse.HistoricoResumo> historico;

        @java.lang.SuppressWarnings("all")
        SolicitacaoPJResponseBuilder() {
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJResponse.SolicitacaoPJResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJResponse.SolicitacaoPJResponseBuilder status(final String status) {
            this.status = status;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJResponse.SolicitacaoPJResponseBuilder cnpj(final String cnpj) {
            this.cnpj = cnpj;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJResponse.SolicitacaoPJResponseBuilder razaoSocial(final String razaoSocial) {
            this.razaoSocial = razaoSocial;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJResponse.SolicitacaoPJResponseBuilder nomeFantasia(final String nomeFantasia) {
            this.nomeFantasia = nomeFantasia;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJResponse.SolicitacaoPJResponseBuilder email(final String email) {
            this.email = email;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJResponse.SolicitacaoPJResponseBuilder telefone(final String telefone) {
            this.telefone = telefone;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJResponse.SolicitacaoPJResponseBuilder endereco(final String endereco) {
            this.endereco = endereco;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJResponse.SolicitacaoPJResponseBuilder clienteIdCriado(final Long clienteIdCriado) {
            this.clienteIdCriado = clienteIdCriado;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJResponse.SolicitacaoPJResponseBuilder contaIdCriada(final Long contaIdCriada) {
            this.contaIdCriada = contaIdCriada;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJResponse.SolicitacaoPJResponseBuilder observacoesAnalista(final String observacoesAnalista) {
            this.observacoesAnalista = observacoesAnalista;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJResponse.SolicitacaoPJResponseBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJResponse.SolicitacaoPJResponseBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJResponse.SolicitacaoPJResponseBuilder documentos(final List<SolicitacaoContaResponse.DocumentoResumo> documentos) {
            this.documentos = documentos;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJResponse.SolicitacaoPJResponseBuilder historico(final List<SolicitacaoContaResponse.HistoricoResumo> historico) {
            this.historico = historico;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJResponse build() {
            return new SolicitacaoPJResponse(this.id, this.status, this.cnpj, this.razaoSocial, this.nomeFantasia, this.email, this.telefone, this.endereco, this.clienteIdCriado, this.contaIdCriada, this.observacoesAnalista, this.dataCriacao, this.dataAtualizacao, this.documentos, this.historico);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SolicitacaoPJResponse.SolicitacaoPJResponseBuilder(id=" + this.id + ", status=" + this.status + ", cnpj=" + this.cnpj + ", razaoSocial=" + this.razaoSocial + ", nomeFantasia=" + this.nomeFantasia + ", email=" + this.email + ", telefone=" + this.telefone + ", endereco=" + this.endereco + ", clienteIdCriado=" + this.clienteIdCriado + ", contaIdCriada=" + this.contaIdCriada + ", observacoesAnalista=" + this.observacoesAnalista + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", documentos=" + this.documentos + ", historico=" + this.historico + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static SolicitacaoPJResponse.SolicitacaoPJResponseBuilder builder() {
        return new SolicitacaoPJResponse.SolicitacaoPJResponseBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public String getCnpj() {
        return this.cnpj;
    }

    @java.lang.SuppressWarnings("all")
    public String getRazaoSocial() {
        return this.razaoSocial;
    }

    @java.lang.SuppressWarnings("all")
    public String getNomeFantasia() {
        return this.nomeFantasia;
    }

    @java.lang.SuppressWarnings("all")
    public String getEmail() {
        return this.email;
    }

    @java.lang.SuppressWarnings("all")
    public String getTelefone() {
        return this.telefone;
    }

    @java.lang.SuppressWarnings("all")
    public String getEndereco() {
        return this.endereco;
    }

    @java.lang.SuppressWarnings("all")
    public Long getClienteIdCriado() {
        return this.clienteIdCriado;
    }

    @java.lang.SuppressWarnings("all")
    public Long getContaIdCriada() {
        return this.contaIdCriada;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoesAnalista() {
        return this.observacoesAnalista;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAtualizacao() {
        return this.dataAtualizacao;
    }

    @java.lang.SuppressWarnings("all")
    public List<SolicitacaoContaResponse.DocumentoResumo> getDocumentos() {
        return this.documentos;
    }

    @java.lang.SuppressWarnings("all")
    public List<SolicitacaoContaResponse.HistoricoResumo> getHistorico() {
        return this.historico;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final String status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setCnpj(final String cnpj) {
        this.cnpj = cnpj;
    }

    @java.lang.SuppressWarnings("all")
    public void setRazaoSocial(final String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @java.lang.SuppressWarnings("all")
    public void setNomeFantasia(final String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    @java.lang.SuppressWarnings("all")
    public void setEmail(final String email) {
        this.email = email;
    }

    @java.lang.SuppressWarnings("all")
    public void setTelefone(final String telefone) {
        this.telefone = telefone;
    }

    @java.lang.SuppressWarnings("all")
    public void setEndereco(final String endereco) {
        this.endereco = endereco;
    }

    @java.lang.SuppressWarnings("all")
    public void setClienteIdCriado(final Long clienteIdCriado) {
        this.clienteIdCriado = clienteIdCriado;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaIdCriada(final Long contaIdCriada) {
        this.contaIdCriada = contaIdCriada;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoesAnalista(final String observacoesAnalista) {
        this.observacoesAnalista = observacoesAnalista;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAtualizacao(final LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDocumentos(final List<SolicitacaoContaResponse.DocumentoResumo> documentos) {
        this.documentos = documentos;
    }

    @java.lang.SuppressWarnings("all")
    public void setHistorico(final List<SolicitacaoContaResponse.HistoricoResumo> historico) {
        this.historico = historico;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof SolicitacaoPJResponse)) return false;
        final SolicitacaoPJResponse other = (SolicitacaoPJResponse) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$clienteIdCriado = this.getClienteIdCriado();
        final java.lang.Object other$clienteIdCriado = other.getClienteIdCriado();
        if (this$clienteIdCriado == null ? other$clienteIdCriado != null : !this$clienteIdCriado.equals(other$clienteIdCriado)) return false;
        final java.lang.Object this$contaIdCriada = this.getContaIdCriada();
        final java.lang.Object other$contaIdCriada = other.getContaIdCriada();
        if (this$contaIdCriada == null ? other$contaIdCriada != null : !this$contaIdCriada.equals(other$contaIdCriada)) return false;
        final java.lang.Object this$cnpj = this.getCnpj();
        final java.lang.Object other$cnpj = other.getCnpj();
        if (this$cnpj == null ? other$cnpj != null : !this$cnpj.equals(other$cnpj)) return false;
        final java.lang.Object this$razaoSocial = this.getRazaoSocial();
        final java.lang.Object other$razaoSocial = other.getRazaoSocial();
        if (this$razaoSocial == null ? other$razaoSocial != null : !this$razaoSocial.equals(other$razaoSocial)) return false;
        final java.lang.Object this$nomeFantasia = this.getNomeFantasia();
        final java.lang.Object other$nomeFantasia = other.getNomeFantasia();
        if (this$nomeFantasia == null ? other$nomeFantasia != null : !this$nomeFantasia.equals(other$nomeFantasia)) return false;
        final java.lang.Object this$email = this.getEmail();
        final java.lang.Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final java.lang.Object this$telefone = this.getTelefone();
        final java.lang.Object other$telefone = other.getTelefone();
        if (this$telefone == null ? other$telefone != null : !this$telefone.equals(other$telefone)) return false;
        final java.lang.Object this$endereco = this.getEndereco();
        final java.lang.Object other$endereco = other.getEndereco();
        if (this$endereco == null ? other$endereco != null : !this$endereco.equals(other$endereco)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$observacoesAnalista = this.getObservacoesAnalista();
        final java.lang.Object other$observacoesAnalista = other.getObservacoesAnalista();
        if (this$observacoesAnalista == null ? other$observacoesAnalista != null : !this$observacoesAnalista.equals(other$observacoesAnalista)) return false;
        final java.lang.Object this$dataCriacao = this.getDataCriacao();
        final java.lang.Object other$dataCriacao = other.getDataCriacao();
        if (this$dataCriacao == null ? other$dataCriacao != null : !this$dataCriacao.equals(other$dataCriacao)) return false;
        final java.lang.Object this$dataAtualizacao = this.getDataAtualizacao();
        final java.lang.Object other$dataAtualizacao = other.getDataAtualizacao();
        if (this$dataAtualizacao == null ? other$dataAtualizacao != null : !this$dataAtualizacao.equals(other$dataAtualizacao)) return false;
        final java.lang.Object this$documentos = this.getDocumentos();
        final java.lang.Object other$documentos = other.getDocumentos();
        if (this$documentos == null ? other$documentos != null : !this$documentos.equals(other$documentos)) return false;
        final java.lang.Object this$historico = this.getHistorico();
        final java.lang.Object other$historico = other.getHistorico();
        if (this$historico == null ? other$historico != null : !this$historico.equals(other$historico)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof SolicitacaoPJResponse;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $clienteIdCriado = this.getClienteIdCriado();
        result = result * PRIME + ($clienteIdCriado == null ? 43 : $clienteIdCriado.hashCode());
        final java.lang.Object $contaIdCriada = this.getContaIdCriada();
        result = result * PRIME + ($contaIdCriada == null ? 43 : $contaIdCriada.hashCode());
        final java.lang.Object $cnpj = this.getCnpj();
        result = result * PRIME + ($cnpj == null ? 43 : $cnpj.hashCode());
        final java.lang.Object $razaoSocial = this.getRazaoSocial();
        result = result * PRIME + ($razaoSocial == null ? 43 : $razaoSocial.hashCode());
        final java.lang.Object $nomeFantasia = this.getNomeFantasia();
        result = result * PRIME + ($nomeFantasia == null ? 43 : $nomeFantasia.hashCode());
        final java.lang.Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final java.lang.Object $telefone = this.getTelefone();
        result = result * PRIME + ($telefone == null ? 43 : $telefone.hashCode());
        final java.lang.Object $endereco = this.getEndereco();
        result = result * PRIME + ($endereco == null ? 43 : $endereco.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $observacoesAnalista = this.getObservacoesAnalista();
        result = result * PRIME + ($observacoesAnalista == null ? 43 : $observacoesAnalista.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        final java.lang.Object $documentos = this.getDocumentos();
        result = result * PRIME + ($documentos == null ? 43 : $documentos.hashCode());
        final java.lang.Object $historico = this.getHistorico();
        result = result * PRIME + ($historico == null ? 43 : $historico.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "SolicitacaoPJResponse(id=" + this.getId() + ", status=" + this.getStatus() + ", cnpj=" + this.getCnpj() + ", razaoSocial=" + this.getRazaoSocial() + ", nomeFantasia=" + this.getNomeFantasia() + ", email=" + this.getEmail() + ", telefone=" + this.getTelefone() + ", endereco=" + this.getEndereco() + ", clienteIdCriado=" + this.getClienteIdCriado() + ", contaIdCriada=" + this.getContaIdCriada() + ", observacoesAnalista=" + this.getObservacoesAnalista() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", documentos=" + this.getDocumentos() + ", historico=" + this.getHistorico() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public SolicitacaoPJResponse(final Long id, final String status, final String cnpj, final String razaoSocial, final String nomeFantasia, final String email, final String telefone, final String endereco, final Long clienteIdCriado, final Long contaIdCriada, final String observacoesAnalista, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final List<SolicitacaoContaResponse.DocumentoResumo> documentos, final List<SolicitacaoContaResponse.HistoricoResumo> historico) {
        this.id = id;
        this.status = status;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.clienteIdCriado = clienteIdCriado;
        this.contaIdCriada = contaIdCriada;
        this.observacoesAnalista = observacoesAnalista;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.documentos = documentos;
        this.historico = historico;
    }
}
