package com.aurix.platform.customer.onboarding.dto;

import com.aurix.platform.customer.onboarding.entity.SolicitacaoOnboarding;
import com.aurix.platform.customer.onboarding.entity.SolicitacaoPF;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitacaoContaResponse {
    private Long id;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private String ocupacao;
    private BigDecimal rendaDeclarada;
    private String status;
    private Boolean pep;
    private Integer scoreBureau;
    private Integer riscoFraude;
    private String resultadoKyc;
    private Long clienteIdCriado;
    private Long contaIdCriada;
    private Boolean contaLimitadaAteKyc;
    private String observacoesAnalista;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private List<DocumentoResumo> documentos;
    private List<HistoricoResumo> historico;

    @java.lang.SuppressWarnings("all")
    public SolicitacaoContaResponse() {
    }

    public static SolicitacaoContaResponse from(SolicitacaoOnboarding onboarding, SolicitacaoPF pf) {
        return SolicitacaoContaResponse.builder()
            .id(onboarding.getId())
            .cpf(pf != null ? pf.getCpf() : null)
            .nome(pf != null ? pf.getNome() : null)
            .email(onboarding.getEmail())
            .telefone(onboarding.getTelefone())
            .dataNascimento(pf != null ? pf.getDataNascimento() : null)
            .ocupacao(pf != null ? pf.getOcupacao() : null)
            .rendaDeclarada(pf != null ? pf.getRendaDeclarada() : null)
            .status(onboarding.getStatus() != null ? onboarding.getStatus().name() : null)
            .pep(pf != null ? pf.getPep() : null)
            .scoreBureau(pf != null ? pf.getScoreBureau() : null)
            .riscoFraude(onboarding.getRiscoFraude())
            .resultadoKyc(pf != null ? pf.getResultadoKyc() : null)
            .clienteIdCriado(onboarding.getClienteIdCriado())
            .contaIdCriada(onboarding.getContaIdCriada())
            .contaLimitadaAteKyc(pf != null ? pf.getContaLimitadaAteKyc() : null)
            .observacoesAnalista(onboarding.getObservacoesAnalista())
            .dataCriacao(onboarding.getDataCriacao())
            .dataAtualizacao(onboarding.getDataAtualizacao())
            .documentos(onboarding.getDocumentos() != null ? onboarding.getDocumentos().stream().map(d -> new DocumentoResumo(d.getId(), d.getTipoDocumento(), d.getNomeArquivo(), d.getValidado())).collect(Collectors.toList()) : List.of())
            .historico(onboarding.getHistorico() != null ? onboarding.getHistorico().stream().map(h -> new HistoricoResumo(h.getAcao(), h.getUsuarioAnalista(), h.getDataAcao())).collect(Collectors.toList()) : List.of())
            .build();
    }


    public static class DocumentoResumo {
        private Long id;
        private String tipoDocumento;
        private String nomeArquivo;
        private Boolean validado;

        @java.lang.SuppressWarnings("all")
        public Long getId() {
            return this.id;
        }

        @java.lang.SuppressWarnings("all")
        public String getTipoDocumento() {
            return this.tipoDocumento;
        }

        @java.lang.SuppressWarnings("all")
        public String getNomeArquivo() {
            return this.nomeArquivo;
        }

        @java.lang.SuppressWarnings("all")
        public Boolean getValidado() {
            return this.validado;
        }

        @java.lang.SuppressWarnings("all")
        public void setId(final Long id) {
            this.id = id;
        }

        @java.lang.SuppressWarnings("all")
        public void setTipoDocumento(final String tipoDocumento) {
            this.tipoDocumento = tipoDocumento;
        }

        @java.lang.SuppressWarnings("all")
        public void setNomeArquivo(final String nomeArquivo) {
            this.nomeArquivo = nomeArquivo;
        }

        @java.lang.SuppressWarnings("all")
        public void setValidado(final Boolean validado) {
            this.validado = validado;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof SolicitacaoContaResponse.DocumentoResumo)) return false;
            final SolicitacaoContaResponse.DocumentoResumo other = (SolicitacaoContaResponse.DocumentoResumo) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$id = this.getId();
            final java.lang.Object other$id = other.getId();
            if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
            final java.lang.Object this$validado = this.getValidado();
            final java.lang.Object other$validado = other.getValidado();
            if (this$validado == null ? other$validado != null : !this$validado.equals(other$validado)) return false;
            final java.lang.Object this$tipoDocumento = this.getTipoDocumento();
            final java.lang.Object other$tipoDocumento = other.getTipoDocumento();
            if (this$tipoDocumento == null ? other$tipoDocumento != null : !this$tipoDocumento.equals(other$tipoDocumento)) return false;
            final java.lang.Object this$nomeArquivo = this.getNomeArquivo();
            final java.lang.Object other$nomeArquivo = other.getNomeArquivo();
            if (this$nomeArquivo == null ? other$nomeArquivo != null : !this$nomeArquivo.equals(other$nomeArquivo)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof SolicitacaoContaResponse.DocumentoResumo;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $id = this.getId();
            result = result * PRIME + ($id == null ? 43 : $id.hashCode());
            final java.lang.Object $validado = this.getValidado();
            result = result * PRIME + ($validado == null ? 43 : $validado.hashCode());
            final java.lang.Object $tipoDocumento = this.getTipoDocumento();
            result = result * PRIME + ($tipoDocumento == null ? 43 : $tipoDocumento.hashCode());
            final java.lang.Object $nomeArquivo = this.getNomeArquivo();
            result = result * PRIME + ($nomeArquivo == null ? 43 : $nomeArquivo.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SolicitacaoContaResponse.DocumentoResumo(id=" + this.getId() + ", tipoDocumento=" + this.getTipoDocumento() + ", nomeArquivo=" + this.getNomeArquivo() + ", validado=" + this.getValidado() + ")";
        }

        @java.lang.SuppressWarnings("all")
        public DocumentoResumo(final Long id, final String tipoDocumento, final String nomeArquivo, final Boolean validado) {
            this.id = id;
            this.tipoDocumento = tipoDocumento;
            this.nomeArquivo = nomeArquivo;
            this.validado = validado;
        }
    }


    public static class HistoricoResumo {
        private String acao;
        private String usuarioAnalista;
        private LocalDateTime dataAcao;

        @java.lang.SuppressWarnings("all")
        public String getAcao() {
            return this.acao;
        }

        @java.lang.SuppressWarnings("all")
        public String getUsuarioAnalista() {
            return this.usuarioAnalista;
        }

        @java.lang.SuppressWarnings("all")
        public LocalDateTime getDataAcao() {
            return this.dataAcao;
        }

        @java.lang.SuppressWarnings("all")
        public void setAcao(final String acao) {
            this.acao = acao;
        }

        @java.lang.SuppressWarnings("all")
        public void setUsuarioAnalista(final String usuarioAnalista) {
            this.usuarioAnalista = usuarioAnalista;
        }

        @java.lang.SuppressWarnings("all")
        public void setDataAcao(final LocalDateTime dataAcao) {
            this.dataAcao = dataAcao;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof SolicitacaoContaResponse.HistoricoResumo)) return false;
            final SolicitacaoContaResponse.HistoricoResumo other = (SolicitacaoContaResponse.HistoricoResumo) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$acao = this.getAcao();
            final java.lang.Object other$acao = other.getAcao();
            if (this$acao == null ? other$acao != null : !this$acao.equals(other$acao)) return false;
            final java.lang.Object this$usuarioAnalista = this.getUsuarioAnalista();
            final java.lang.Object other$usuarioAnalista = other.getUsuarioAnalista();
            if (this$usuarioAnalista == null ? other$usuarioAnalista != null : !this$usuarioAnalista.equals(other$usuarioAnalista)) return false;
            final java.lang.Object this$dataAcao = this.getDataAcao();
            final java.lang.Object other$dataAcao = other.getDataAcao();
            if (this$dataAcao == null ? other$dataAcao != null : !this$dataAcao.equals(other$dataAcao)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof SolicitacaoContaResponse.HistoricoResumo;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $acao = this.getAcao();
            result = result * PRIME + ($acao == null ? 43 : $acao.hashCode());
            final java.lang.Object $usuarioAnalista = this.getUsuarioAnalista();
            result = result * PRIME + ($usuarioAnalista == null ? 43 : $usuarioAnalista.hashCode());
            final java.lang.Object $dataAcao = this.getDataAcao();
            result = result * PRIME + ($dataAcao == null ? 43 : $dataAcao.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SolicitacaoContaResponse.HistoricoResumo(acao=" + this.getAcao() + ", usuarioAnalista=" + this.getUsuarioAnalista() + ", dataAcao=" + this.getDataAcao() + ")";
        }

        @java.lang.SuppressWarnings("all")
        public HistoricoResumo(final String acao, final String usuarioAnalista, final LocalDateTime dataAcao) {
            this.acao = acao;
            this.usuarioAnalista = usuarioAnalista;
            this.dataAcao = dataAcao;
        }
    }

    @java.lang.SuppressWarnings("all")
    SolicitacaoContaResponse(final Long id, final String cpf, final String nome, final String email, final String telefone, final LocalDate dataNascimento, final String ocupacao, final BigDecimal rendaDeclarada, final String status, final Boolean pep, final Integer scoreBureau, final Integer riscoFraude, final String resultadoKyc, final Long clienteIdCriado, final Long contaIdCriada, final Boolean contaLimitadaAteKyc, final String observacoesAnalista, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final List<DocumentoResumo> documentos, final List<HistoricoResumo> historico) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.ocupacao = ocupacao;
        this.rendaDeclarada = rendaDeclarada;
        this.status = status;
        this.pep = pep;
        this.scoreBureau = scoreBureau;
        this.riscoFraude = riscoFraude;
        this.resultadoKyc = resultadoKyc;
        this.clienteIdCriado = clienteIdCriado;
        this.contaIdCriada = contaIdCriada;
        this.contaLimitadaAteKyc = contaLimitadaAteKyc;
        this.observacoesAnalista = observacoesAnalista;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.documentos = documentos;
        this.historico = historico;
    }


    @java.lang.SuppressWarnings("all")
    public static class SolicitacaoContaResponseBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String cpf;
        @java.lang.SuppressWarnings("all")
        private String nome;
        @java.lang.SuppressWarnings("all")
        private String email;
        @java.lang.SuppressWarnings("all")
        private String telefone;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataNascimento;
        @java.lang.SuppressWarnings("all")
        private String ocupacao;
        @java.lang.SuppressWarnings("all")
        private BigDecimal rendaDeclarada;
        @java.lang.SuppressWarnings("all")
        private String status;
        @java.lang.SuppressWarnings("all")
        private Boolean pep;
        @java.lang.SuppressWarnings("all")
        private Integer scoreBureau;
        @java.lang.SuppressWarnings("all")
        private Integer riscoFraude;
        @java.lang.SuppressWarnings("all")
        private String resultadoKyc;
        @java.lang.SuppressWarnings("all")
        private Long clienteIdCriado;
        @java.lang.SuppressWarnings("all")
        private Long contaIdCriada;
        @java.lang.SuppressWarnings("all")
        private Boolean contaLimitadaAteKyc;
        @java.lang.SuppressWarnings("all")
        private String observacoesAnalista;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;
        @java.lang.SuppressWarnings("all")
        private List<DocumentoResumo> documentos;
        @java.lang.SuppressWarnings("all")
        private List<HistoricoResumo> historico;

        @java.lang.SuppressWarnings("all")
        SolicitacaoContaResponseBuilder() {
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder cpf(final String cpf) {
            this.cpf = cpf;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder nome(final String nome) {
            this.nome = nome;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder email(final String email) {
            this.email = email;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder telefone(final String telefone) {
            this.telefone = telefone;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder dataNascimento(final LocalDate dataNascimento) {
            this.dataNascimento = dataNascimento;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder ocupacao(final String ocupacao) {
            this.ocupacao = ocupacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder rendaDeclarada(final BigDecimal rendaDeclarada) {
            this.rendaDeclarada = rendaDeclarada;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder status(final String status) {
            this.status = status;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder pep(final Boolean pep) {
            this.pep = pep;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder scoreBureau(final Integer scoreBureau) {
            this.scoreBureau = scoreBureau;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder riscoFraude(final Integer riscoFraude) {
            this.riscoFraude = riscoFraude;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder resultadoKyc(final String resultadoKyc) {
            this.resultadoKyc = resultadoKyc;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder clienteIdCriado(final Long clienteIdCriado) {
            this.clienteIdCriado = clienteIdCriado;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder contaIdCriada(final Long contaIdCriada) {
            this.contaIdCriada = contaIdCriada;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder contaLimitadaAteKyc(final Boolean contaLimitadaAteKyc) {
            this.contaLimitadaAteKyc = contaLimitadaAteKyc;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder observacoesAnalista(final String observacoesAnalista) {
            this.observacoesAnalista = observacoesAnalista;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder documentos(final List<DocumentoResumo> documentos) {
            this.documentos = documentos;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse.SolicitacaoContaResponseBuilder historico(final List<HistoricoResumo> historico) {
            this.historico = historico;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoContaResponse build() {
            return new SolicitacaoContaResponse(this.id, this.cpf, this.nome, this.email, this.telefone, this.dataNascimento, this.ocupacao, this.rendaDeclarada, this.status, this.pep, this.scoreBureau, this.riscoFraude, this.resultadoKyc, this.clienteIdCriado, this.contaIdCriada, this.contaLimitadaAteKyc, this.observacoesAnalista, this.dataCriacao, this.dataAtualizacao, this.documentos, this.historico);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SolicitacaoContaResponse.SolicitacaoContaResponseBuilder(id=" + this.id + ", cpf=" + this.cpf + ", nome=" + this.nome + ", email=" + this.email + ", telefone=" + this.telefone + ", dataNascimento=" + this.dataNascimento + ", ocupacao=" + this.ocupacao + ", rendaDeclarada=" + this.rendaDeclarada + ", status=" + this.status + ", pep=" + this.pep + ", scoreBureau=" + this.scoreBureau + ", riscoFraude=" + this.riscoFraude + ", resultadoKyc=" + this.resultadoKyc + ", clienteIdCriado=" + this.clienteIdCriado + ", contaIdCriada=" + this.contaIdCriada + ", contaLimitadaAteKyc=" + this.contaLimitadaAteKyc + ", observacoesAnalista=" + this.observacoesAnalista + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", documentos=" + this.documentos + ", historico=" + this.historico + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static SolicitacaoContaResponse.SolicitacaoContaResponseBuilder builder() {
        return new SolicitacaoContaResponse.SolicitacaoContaResponseBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCpf() {
        return this.cpf;
    }

    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
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
    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    @java.lang.SuppressWarnings("all")
    public String getOcupacao() {
        return this.ocupacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getRendaDeclarada() {
        return this.rendaDeclarada;
    }

    @java.lang.SuppressWarnings("all")
    public String getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getPep() {
        return this.pep;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getScoreBureau() {
        return this.scoreBureau;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getRiscoFraude() {
        return this.riscoFraude;
    }

    @java.lang.SuppressWarnings("all")
    public String getResultadoKyc() {
        return this.resultadoKyc;
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
    public Boolean getContaLimitadaAteKyc() {
        return this.contaLimitadaAteKyc;
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
    public List<DocumentoResumo> getDocumentos() {
        return this.documentos;
    }

    @java.lang.SuppressWarnings("all")
    public List<HistoricoResumo> getHistorico() {
        return this.historico;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setCpf(final String cpf) {
        this.cpf = cpf;
    }

    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
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
    public void setDataNascimento(final LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setOcupacao(final String ocupacao) {
        this.ocupacao = ocupacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setRendaDeclarada(final BigDecimal rendaDeclarada) {
        this.rendaDeclarada = rendaDeclarada;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final String status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setPep(final Boolean pep) {
        this.pep = pep;
    }

    @java.lang.SuppressWarnings("all")
    public void setScoreBureau(final Integer scoreBureau) {
        this.scoreBureau = scoreBureau;
    }

    @java.lang.SuppressWarnings("all")
    public void setRiscoFraude(final Integer riscoFraude) {
        this.riscoFraude = riscoFraude;
    }

    @java.lang.SuppressWarnings("all")
    public void setResultadoKyc(final String resultadoKyc) {
        this.resultadoKyc = resultadoKyc;
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
    public void setContaLimitadaAteKyc(final Boolean contaLimitadaAteKyc) {
        this.contaLimitadaAteKyc = contaLimitadaAteKyc;
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
    public void setDocumentos(final List<DocumentoResumo> documentos) {
        this.documentos = documentos;
    }

    @java.lang.SuppressWarnings("all")
    public void setHistorico(final List<HistoricoResumo> historico) {
        this.historico = historico;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof SolicitacaoContaResponse)) return false;
        final SolicitacaoContaResponse other = (SolicitacaoContaResponse) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$pep = this.getPep();
        final java.lang.Object other$pep = other.getPep();
        if (this$pep == null ? other$pep != null : !this$pep.equals(other$pep)) return false;
        final java.lang.Object this$scoreBureau = this.getScoreBureau();
        final java.lang.Object other$scoreBureau = other.getScoreBureau();
        if (this$scoreBureau == null ? other$scoreBureau != null : !this$scoreBureau.equals(other$scoreBureau)) return false;
        final java.lang.Object this$riscoFraude = this.getRiscoFraude();
        final java.lang.Object other$riscoFraude = other.getRiscoFraude();
        if (this$riscoFraude == null ? other$riscoFraude != null : !this$riscoFraude.equals(other$riscoFraude)) return false;
        final java.lang.Object this$clienteIdCriado = this.getClienteIdCriado();
        final java.lang.Object other$clienteIdCriado = other.getClienteIdCriado();
        if (this$clienteIdCriado == null ? other$clienteIdCriado != null : !this$clienteIdCriado.equals(other$clienteIdCriado)) return false;
        final java.lang.Object this$contaIdCriada = this.getContaIdCriada();
        final java.lang.Object other$contaIdCriada = other.getContaIdCriada();
        if (this$contaIdCriada == null ? other$contaIdCriada != null : !this$contaIdCriada.equals(other$contaIdCriada)) return false;
        final java.lang.Object this$contaLimitadaAteKyc = this.getContaLimitadaAteKyc();
        final java.lang.Object other$contaLimitadaAteKyc = other.getContaLimitadaAteKyc();
        if (this$contaLimitadaAteKyc == null ? other$contaLimitadaAteKyc != null : !this$contaLimitadaAteKyc.equals(other$contaLimitadaAteKyc)) return false;
        final java.lang.Object this$cpf = this.getCpf();
        final java.lang.Object other$cpf = other.getCpf();
        if (this$cpf == null ? other$cpf != null : !this$cpf.equals(other$cpf)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$email = this.getEmail();
        final java.lang.Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final java.lang.Object this$telefone = this.getTelefone();
        final java.lang.Object other$telefone = other.getTelefone();
        if (this$telefone == null ? other$telefone != null : !this$telefone.equals(other$telefone)) return false;
        final java.lang.Object this$dataNascimento = this.getDataNascimento();
        final java.lang.Object other$dataNascimento = other.getDataNascimento();
        if (this$dataNascimento == null ? other$dataNascimento != null : !this$dataNascimento.equals(other$dataNascimento)) return false;
        final java.lang.Object this$ocupacao = this.getOcupacao();
        final java.lang.Object other$ocupacao = other.getOcupacao();
        if (this$ocupacao == null ? other$ocupacao != null : !this$ocupacao.equals(other$ocupacao)) return false;
        final java.lang.Object this$rendaDeclarada = this.getRendaDeclarada();
        final java.lang.Object other$rendaDeclarada = other.getRendaDeclarada();
        if (this$rendaDeclarada == null ? other$rendaDeclarada != null : !this$rendaDeclarada.equals(other$rendaDeclarada)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$resultadoKyc = this.getResultadoKyc();
        final java.lang.Object other$resultadoKyc = other.getResultadoKyc();
        if (this$resultadoKyc == null ? other$resultadoKyc != null : !this$resultadoKyc.equals(other$resultadoKyc)) return false;
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
        return other instanceof SolicitacaoContaResponse;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $pep = this.getPep();
        result = result * PRIME + ($pep == null ? 43 : $pep.hashCode());
        final java.lang.Object $scoreBureau = this.getScoreBureau();
        result = result * PRIME + ($scoreBureau == null ? 43 : $scoreBureau.hashCode());
        final java.lang.Object $riscoFraude = this.getRiscoFraude();
        result = result * PRIME + ($riscoFraude == null ? 43 : $riscoFraude.hashCode());
        final java.lang.Object $clienteIdCriado = this.getClienteIdCriado();
        result = result * PRIME + ($clienteIdCriado == null ? 43 : $clienteIdCriado.hashCode());
        final java.lang.Object $contaIdCriada = this.getContaIdCriada();
        result = result * PRIME + ($contaIdCriada == null ? 43 : $contaIdCriada.hashCode());
        final java.lang.Object $contaLimitadaAteKyc = this.getContaLimitadaAteKyc();
        result = result * PRIME + ($contaLimitadaAteKyc == null ? 43 : $contaLimitadaAteKyc.hashCode());
        final java.lang.Object $cpf = this.getCpf();
        result = result * PRIME + ($cpf == null ? 43 : $cpf.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final java.lang.Object $telefone = this.getTelefone();
        result = result * PRIME + ($telefone == null ? 43 : $telefone.hashCode());
        final java.lang.Object $dataNascimento = this.getDataNascimento();
        result = result * PRIME + ($dataNascimento == null ? 43 : $dataNascimento.hashCode());
        final java.lang.Object $ocupacao = this.getOcupacao();
        result = result * PRIME + ($ocupacao == null ? 43 : $ocupacao.hashCode());
        final java.lang.Object $rendaDeclarada = this.getRendaDeclarada();
        result = result * PRIME + ($rendaDeclarada == null ? 43 : $rendaDeclarada.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $resultadoKyc = this.getResultadoKyc();
        result = result * PRIME + ($resultadoKyc == null ? 43 : $resultadoKyc.hashCode());
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
        return "SolicitacaoContaResponse(id=" + this.getId() + ", cpf=" + this.getCpf() + ", nome=" + this.getNome() + ", email=" + this.getEmail() + ", telefone=" + this.getTelefone() + ", dataNascimento=" + this.getDataNascimento() + ", ocupacao=" + this.getOcupacao() + ", rendaDeclarada=" + this.getRendaDeclarada() + ", status=" + this.getStatus() + ", pep=" + this.getPep() + ", scoreBureau=" + this.getScoreBureau() + ", riscoFraude=" + this.getRiscoFraude() + ", resultadoKyc=" + this.getResultadoKyc() + ", clienteIdCriado=" + this.getClienteIdCriado() + ", contaIdCriada=" + this.getContaIdCriada() + ", contaLimitadaAteKyc=" + this.getContaLimitadaAteKyc() + ", observacoesAnalista=" + this.getObservacoesAnalista() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", documentos=" + this.getDocumentos() + ", historico=" + this.getHistorico() + ")";
    }
}
