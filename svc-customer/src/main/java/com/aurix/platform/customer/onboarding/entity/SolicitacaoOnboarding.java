package com.aurix.platform.customer.onboarding.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "solicitacoes_onboarding", schema = "aurix")
public class SolicitacaoOnboarding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tenant_id", length = 64)
    private String tenantId;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa", nullable = false, length = 10)
    private TipoPessoa tipoPessoa;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private StatusOnboarding status;
    @Column(name = "canal", length = 20)
    private String canal;
    @Column(name = "produto", length = 50)
    private String produto;
    @Column(name = "email", length = 255)
    private String email;
    @Column(name = "telefone", length = 20)
    private String telefone;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "endereco", columnDefinition = "jsonb")
    private String endereco;
    @Column(name = "cliente_id_criado")
    private Long clienteIdCriado;
    @Column(name = "conta_id_criada")
    private Long contaIdCriada;
    @Column(name = "observacoes_analista", length = 1000)
    private String observacoesAnalista;
    @Column(name = "risco_fraude")
    private Integer riscoFraude;
    @OneToMany(mappedBy = "solicitacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentoOnboarding> documentos;
    @OneToMany(mappedBy = "solicitacao", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dataAcao DESC")
    private List<HistoricoAprovacao> historico;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;


    @java.lang.SuppressWarnings("all")
    private static StatusOnboarding $default$status() {
        return StatusOnboarding.RECEBIDA;
    }

    @java.lang.SuppressWarnings("all")
    private static TipoPessoa $default$tipoPessoa() {
        return TipoPessoa.FISICA;
    }

    @java.lang.SuppressWarnings("all")
    private static List<DocumentoOnboarding> $default$documentos() {
        return new ArrayList<>();
    }

    @java.lang.SuppressWarnings("all")
    private static List<HistoricoAprovacao> $default$historico() {
        return new ArrayList<>();
    }


    @java.lang.SuppressWarnings("all")
    public static class SolicitacaoOnboardingBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String tenantId;
        @java.lang.SuppressWarnings("all")
        private boolean tipoPessoa$set;
        @java.lang.SuppressWarnings("all")
        private TipoPessoa tipoPessoa$value;
        @java.lang.SuppressWarnings("all")
        private boolean status$set;
        @java.lang.SuppressWarnings("all")
        private StatusOnboarding status$value;
        @java.lang.SuppressWarnings("all")
        private String canal;
        @java.lang.SuppressWarnings("all")
        private String produto;
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
        private Integer riscoFraude;
        @java.lang.SuppressWarnings("all")
        private boolean documentos$set;
        @java.lang.SuppressWarnings("all")
        private List<DocumentoOnboarding> documentos$value;
        @java.lang.SuppressWarnings("all")
        private boolean historico$set;
        @java.lang.SuppressWarnings("all")
        private List<HistoricoAprovacao> historico$value;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;

        @java.lang.SuppressWarnings("all")
        SolicitacaoOnboardingBuilder() {
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding.SolicitacaoOnboardingBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding.SolicitacaoOnboardingBuilder tenantId(final String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding.SolicitacaoOnboardingBuilder tipoPessoa(final TipoPessoa tipoPessoa) {
            this.tipoPessoa$value = tipoPessoa;
            tipoPessoa$set = true;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding.SolicitacaoOnboardingBuilder status(final StatusOnboarding status) {
            this.status$value = status;
            status$set = true;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding.SolicitacaoOnboardingBuilder canal(final String canal) {
            this.canal = canal;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding.SolicitacaoOnboardingBuilder produto(final String produto) {
            this.produto = produto;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding.SolicitacaoOnboardingBuilder email(final String email) {
            this.email = email;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding.SolicitacaoOnboardingBuilder telefone(final String telefone) {
            this.telefone = telefone;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding.SolicitacaoOnboardingBuilder endereco(final String endereco) {
            this.endereco = endereco;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding.SolicitacaoOnboardingBuilder clienteIdCriado(final Long clienteIdCriado) {
            this.clienteIdCriado = clienteIdCriado;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding.SolicitacaoOnboardingBuilder contaIdCriada(final Long contaIdCriada) {
            this.contaIdCriada = contaIdCriada;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding.SolicitacaoOnboardingBuilder observacoesAnalista(final String observacoesAnalista) {
            this.observacoesAnalista = observacoesAnalista;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding.SolicitacaoOnboardingBuilder riscoFraude(final Integer riscoFraude) {
            this.riscoFraude = riscoFraude;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding.SolicitacaoOnboardingBuilder documentos(final List<DocumentoOnboarding> documentos) {
            this.documentos$value = documentos;
            documentos$set = true;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding.SolicitacaoOnboardingBuilder historico(final List<HistoricoAprovacao> historico) {
            this.historico$value = historico;
            historico$set = true;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding.SolicitacaoOnboardingBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding.SolicitacaoOnboardingBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoOnboarding build() {
            TipoPessoa tipoPessoa$value = this.tipoPessoa$value;
            if (!this.tipoPessoa$set) tipoPessoa$value = SolicitacaoOnboarding.$default$tipoPessoa();
            StatusOnboarding status$value = this.status$value;
            if (!this.status$set) status$value = SolicitacaoOnboarding.$default$status();
            List<DocumentoOnboarding> documentos$value = this.documentos$value;
            if (!this.documentos$set) documentos$value = SolicitacaoOnboarding.$default$documentos();
            List<HistoricoAprovacao> historico$value = this.historico$value;
            if (!this.historico$set) historico$value = SolicitacaoOnboarding.$default$historico();
            return new SolicitacaoOnboarding(this.id, this.tenantId, tipoPessoa$value, status$value, this.canal, this.produto, this.email, this.telefone, this.endereco, this.clienteIdCriado, this.contaIdCriada, this.observacoesAnalista, this.riscoFraude, documentos$value, historico$value, this.dataCriacao, this.dataAtualizacao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SolicitacaoOnboarding.SolicitacaoOnboardingBuilder(id=" + this.id + ", tenantId=" + this.tenantId + ", tipoPessoa$value=" + this.tipoPessoa$value + ", status$value=" + this.status$value + ", canal=" + this.canal + ", produto=" + this.produto + ", email=" + this.email + ", telefone=" + this.telefone + ", endereco=" + this.endereco + ", clienteIdCriado=" + this.clienteIdCriado + ", contaIdCriada=" + this.contaIdCriada + ", observacoesAnalista=" + this.observacoesAnalista + ", riscoFraude=" + this.riscoFraude + ", documentos$value=" + this.documentos$value + ", historico$value=" + this.historico$value + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static SolicitacaoOnboarding.SolicitacaoOnboardingBuilder builder() {
        return new SolicitacaoOnboarding.SolicitacaoOnboardingBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getTenantId() {
        return this.tenantId;
    }

    @java.lang.SuppressWarnings("all")
    public TipoPessoa getTipoPessoa() {
        return this.tipoPessoa;
    }

    @java.lang.SuppressWarnings("all")
    public StatusOnboarding getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public String getCanal() {
        return this.canal;
    }

    @java.lang.SuppressWarnings("all")
    public String getProduto() {
        return this.produto;
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
    public Integer getRiscoFraude() {
        return this.riscoFraude;
    }

    @java.lang.SuppressWarnings("all")
    public List<DocumentoOnboarding> getDocumentos() {
        return this.documentos;
    }

    @java.lang.SuppressWarnings("all")
    public List<HistoricoAprovacao> getHistorico() {
        return this.historico;
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
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setTenantId(final String tenantId) {
        this.tenantId = tenantId;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoPessoa(final TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusOnboarding status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setCanal(final String canal) {
        this.canal = canal;
    }

    @java.lang.SuppressWarnings("all")
    public void setProduto(final String produto) {
        this.produto = produto;
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
    public void setRiscoFraude(final Integer riscoFraude) {
        this.riscoFraude = riscoFraude;
    }

    @java.lang.SuppressWarnings("all")
    public void setDocumentos(final List<DocumentoOnboarding> documentos) {
        this.documentos = documentos;
    }

    @java.lang.SuppressWarnings("all")
    public void setHistorico(final List<HistoricoAprovacao> historico) {
        this.historico = historico;
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
    public SolicitacaoOnboarding() {
        this.tipoPessoa = SolicitacaoOnboarding.$default$tipoPessoa();
        this.status = SolicitacaoOnboarding.$default$status();
        this.documentos = SolicitacaoOnboarding.$default$documentos();
        this.historico = SolicitacaoOnboarding.$default$historico();
    }

    @java.lang.SuppressWarnings("all")
    public SolicitacaoOnboarding(final Long id, final String tenantId, final TipoPessoa tipoPessoa, final StatusOnboarding status, final String canal, final String produto, final String email, final String telefone, final String endereco, final Long clienteIdCriado, final Long contaIdCriada, final String observacoesAnalista, final Integer riscoFraude, final List<DocumentoOnboarding> documentos, final List<HistoricoAprovacao> historico, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao) {
        this.id = id;
        this.tenantId = tenantId;
        this.tipoPessoa = tipoPessoa;
        this.status = status;
        this.canal = canal;
        this.produto = produto;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.clienteIdCriado = clienteIdCriado;
        this.contaIdCriada = contaIdCriada;
        this.observacoesAnalista = observacoesAnalista;
        this.riscoFraude = riscoFraude;
        this.documentos = documentos;
        this.historico = historico;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
