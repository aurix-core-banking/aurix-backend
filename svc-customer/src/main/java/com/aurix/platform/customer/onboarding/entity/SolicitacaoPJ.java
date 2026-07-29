package com.aurix.platform.customer.onboarding.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitacoes_pj", schema = "aurix")
public class SolicitacaoPJ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "solicitacao_id", nullable = false, unique = true)
    private Long solicitacaoId;
    @NotBlank
    @Pattern(regexp = "\\d{14}")
    @Column(name = "cnpj", nullable = false, length = 14)
    private String cnpj;
    @NotBlank
    @Column(name = "razao_social", nullable = false, length = 255)
    private String razaoSocial;
    @Column(name = "nome_fantasia", length = 255)
    private String nomeFantasia;
    @Column(name = "natureza_juridica", length = 100)
    private String naturezaJuridica;
    @Enumerated(EnumType.STRING)
    @Column(name = "porte", length = 10)
    private PorteEmpresa porte;
    @Column(name = "capital_social", precision = 15, scale = 2)
    private BigDecimal capitalSocial;
    @Column(name = "data_constituicao")
    private LocalDate dataConstituicao;
    @Column(name = "inscricao_estadual", length = 20)
    private String inscricaoEstadual;
    @Column(name = "inscricao_municipal", length = 20)
    private String inscricaoMunicipal;
    @Column(name = "faturamento_mensal", precision = 15, scale = 2)
    private BigDecimal faturamentoMensal;
    @Column(name = "numero_funcionarios")
    private Integer numeroFuncionarios;
    @Column(name = "cliente_id_criado")
    private Long clienteIdCriado;
    @Column(name = "conta_id_criada")
    private Long contaIdCriada;
    @Column(name = "observacoes_analista", length = 1000)
    private String observacoesAnalista;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;


    @java.lang.SuppressWarnings("all")
    public static class SolicitacaoPJBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private Long solicitacaoId;
        @java.lang.SuppressWarnings("all")
        private String cnpj;
        @java.lang.SuppressWarnings("all")
        private String razaoSocial;
        @java.lang.SuppressWarnings("all")
        private String nomeFantasia;
        @java.lang.SuppressWarnings("all")
        private String naturezaJuridica;
        @java.lang.SuppressWarnings("all")
        private PorteEmpresa porte;
        @java.lang.SuppressWarnings("all")
        private BigDecimal capitalSocial;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataConstituicao;
        @java.lang.SuppressWarnings("all")
        private String inscricaoEstadual;
        @java.lang.SuppressWarnings("all")
        private String inscricaoMunicipal;
        @java.lang.SuppressWarnings("all")
        private BigDecimal faturamentoMensal;
        @java.lang.SuppressWarnings("all")
        private Integer numeroFuncionarios;
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
        SolicitacaoPJBuilder() {
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder solicitacaoId(final Long solicitacaoId) {
            this.solicitacaoId = solicitacaoId;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder cnpj(final String cnpj) {
            this.cnpj = cnpj;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder razaoSocial(final String razaoSocial) {
            this.razaoSocial = razaoSocial;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder nomeFantasia(final String nomeFantasia) {
            this.nomeFantasia = nomeFantasia;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder naturezaJuridica(final String naturezaJuridica) {
            this.naturezaJuridica = naturezaJuridica;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder porte(final PorteEmpresa porte) {
            this.porte = porte;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder capitalSocial(final BigDecimal capitalSocial) {
            this.capitalSocial = capitalSocial;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder dataConstituicao(final LocalDate dataConstituicao) {
            this.dataConstituicao = dataConstituicao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder inscricaoEstadual(final String inscricaoEstadual) {
            this.inscricaoEstadual = inscricaoEstadual;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder inscricaoMunicipal(final String inscricaoMunicipal) {
            this.inscricaoMunicipal = inscricaoMunicipal;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder faturamentoMensal(final BigDecimal faturamentoMensal) {
            this.faturamentoMensal = faturamentoMensal;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder numeroFuncionarios(final Integer numeroFuncionarios) {
            this.numeroFuncionarios = numeroFuncionarios;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder clienteIdCriado(final Long clienteIdCriado) {
            this.clienteIdCriado = clienteIdCriado;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder contaIdCriada(final Long contaIdCriada) {
            this.contaIdCriada = contaIdCriada;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder observacoesAnalista(final String observacoesAnalista) {
            this.observacoesAnalista = observacoesAnalista;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ.SolicitacaoPJBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SolicitacaoPJ build() {
            return new SolicitacaoPJ(this.id, this.solicitacaoId, this.cnpj, this.razaoSocial, this.nomeFantasia, this.naturezaJuridica, this.porte, this.capitalSocial, this.dataConstituicao, this.inscricaoEstadual, this.inscricaoMunicipal, this.faturamentoMensal, this.numeroFuncionarios, this.clienteIdCriado, this.contaIdCriada, this.observacoesAnalista, this.dataCriacao, this.dataAtualizacao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SolicitacaoPJ.SolicitacaoPJBuilder(id=" + this.id + ", solicitacaoId=" + this.solicitacaoId + ", cnpj=" + this.cnpj + ", razaoSocial=" + this.razaoSocial + ", nomeFantasia=" + this.nomeFantasia + ", naturezaJuridica=" + this.naturezaJuridica + ", porte=" + this.porte + ", capitalSocial=" + this.capitalSocial + ", dataConstituicao=" + this.dataConstituicao + ", inscricaoEstadual=" + this.inscricaoEstadual + ", inscricaoMunicipal=" + this.inscricaoMunicipal + ", faturamentoMensal=" + this.faturamentoMensal + ", numeroFuncionarios=" + this.numeroFuncionarios + ", clienteIdCriado=" + this.clienteIdCriado + ", contaIdCriada=" + this.contaIdCriada + ", observacoesAnalista=" + this.observacoesAnalista + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static SolicitacaoPJ.SolicitacaoPJBuilder builder() {
        return new SolicitacaoPJ.SolicitacaoPJBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public Long getSolicitacaoId() {
        return this.solicitacaoId;
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
    public String getNaturezaJuridica() {
        return this.naturezaJuridica;
    }

    @java.lang.SuppressWarnings("all")
    public PorteEmpresa getPorte() {
        return this.porte;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getCapitalSocial() {
        return this.capitalSocial;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataConstituicao() {
        return this.dataConstituicao;
    }

    @java.lang.SuppressWarnings("all")
    public String getInscricaoEstadual() {
        return this.inscricaoEstadual;
    }

    @java.lang.SuppressWarnings("all")
    public String getInscricaoMunicipal() {
        return this.inscricaoMunicipal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getFaturamentoMensal() {
        return this.faturamentoMensal;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getNumeroFuncionarios() {
        return this.numeroFuncionarios;
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
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setSolicitacaoId(final Long solicitacaoId) {
        this.solicitacaoId = solicitacaoId;
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
    public void setNaturezaJuridica(final String naturezaJuridica) {
        this.naturezaJuridica = naturezaJuridica;
    }

    @java.lang.SuppressWarnings("all")
    public void setPorte(final PorteEmpresa porte) {
        this.porte = porte;
    }

    @java.lang.SuppressWarnings("all")
    public void setCapitalSocial(final BigDecimal capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataConstituicao(final LocalDate dataConstituicao) {
        this.dataConstituicao = dataConstituicao;
    }

    @java.lang.SuppressWarnings("all")
    public void setInscricaoEstadual(final String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    @java.lang.SuppressWarnings("all")
    public void setInscricaoMunicipal(final String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    @java.lang.SuppressWarnings("all")
    public void setFaturamentoMensal(final BigDecimal faturamentoMensal) {
        this.faturamentoMensal = faturamentoMensal;
    }

    @java.lang.SuppressWarnings("all")
    public void setNumeroFuncionarios(final Integer numeroFuncionarios) {
        this.numeroFuncionarios = numeroFuncionarios;
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
    public SolicitacaoPJ() {
    }

    @java.lang.SuppressWarnings("all")
    public SolicitacaoPJ(final Long id, final Long solicitacaoId, final String cnpj, final String razaoSocial, final String nomeFantasia, final String naturezaJuridica, final PorteEmpresa porte, final BigDecimal capitalSocial, final LocalDate dataConstituicao, final String inscricaoEstadual, final String inscricaoMunicipal, final BigDecimal faturamentoMensal, final Integer numeroFuncionarios, final Long clienteIdCriado, final Long contaIdCriada, final String observacoesAnalista, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao) {
        this.id = id;
        this.solicitacaoId = solicitacaoId;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.naturezaJuridica = naturezaJuridica;
        this.porte = porte;
        this.capitalSocial = capitalSocial;
        this.dataConstituicao = dataConstituicao;
        this.inscricaoEstadual = inscricaoEstadual;
        this.inscricaoMunicipal = inscricaoMunicipal;
        this.faturamentoMensal = faturamentoMensal;
        this.numeroFuncionarios = numeroFuncionarios;
        this.clienteIdCriado = clienteIdCriado;
        this.contaIdCriada = contaIdCriada;
        this.observacoesAnalista = observacoesAnalista;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
