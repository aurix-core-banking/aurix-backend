package com.aurix.platform.cards.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "cartoes", schema = "aurix")
public class Cartao extends BaseEntity {
    @Column(nullable = false, unique = true, length = 16)
    private String numeroCartao;
    @Column(nullable = false, length = 20)
    private String numeroCartaoMascarado;
    @Column(nullable = false, length = 3)
    private String cvv;
    @Column(nullable = false)
    private LocalDate dataValidade;
    @Column(nullable = false)
    private String nomePortador;
    @Column(name = "conta_id", nullable = false)
    private Long contaId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCartao tipoCartao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BandeiraCartao bandeira;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCartao status = StatusCartao.ATIVO;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal limiteCredito = BigDecimal.ZERO;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal limiteUtilizado = BigDecimal.ZERO;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal limiteDisponivel = BigDecimal.ZERO;
    @Column
    private LocalDateTime dataEmissao;
    @Column
    private LocalDateTime dataAtivacao;
    @Column
    private LocalDateTime dataBloqueio;
    @Column(length = 500)
    private String motivoBloqueio;
    @Column
    private Boolean permiteComprasNacionais = true;
    @Column
    private Boolean permiteComprasInternacionais = false;
    @Column
    private Boolean permiteSaque = true;
    @Column
    private Boolean permiteParcelamento = true;
    @Column
    private Integer diaVencimentoFatura = 10;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "configuracoes", columnDefinition = "JSONB")
    private String configuracoes;
    @Column
    private Long produtoId;
    @Column
    private Long bandeiraParceiroId;
    @Column(length = 50)
    private String tenantId;
    @Column
    private LocalDateTime dataCancelamento;


    public enum TipoCartao {
        CREDITO, DEBITO, CREDITO_DEBITO, PRE_PAGO, MULTIFUNCIONAL;
    }


    public enum BandeiraCartao {
        VISA, MASTERCARD, ELO, AMEX, HIPERCARD, DINERS;
    }


    public enum StatusCartao {
        ATIVO, BLOQUEADO, CANCELADO, EXPIRADO, PENDENTE_ATIVACAO;
    }

@java.lang.SuppressWarnings("all")
    public String getNumeroCartao() {
        return this.numeroCartao;
    }

    @java.lang.SuppressWarnings("all")
    public String getNumeroCartaoMascarado() {
        return this.numeroCartaoMascarado;
    }

    @java.lang.SuppressWarnings("all")
    public String getCvv() {
        return this.cvv;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataValidade() {
        return this.dataValidade;
    }

    @java.lang.SuppressWarnings("all")
    public String getNomePortador() {
        return this.nomePortador;
    }

    @java.lang.SuppressWarnings("all")
    public Long getContaId() {
        return this.contaId;
    }

    @java.lang.SuppressWarnings("all")
    public TipoCartao getTipoCartao() {
        return this.tipoCartao;
    }

    @java.lang.SuppressWarnings("all")
    public BandeiraCartao getBandeira() {
        return this.bandeira;
    }

    @java.lang.SuppressWarnings("all")
    public StatusCartao getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteCredito() {
        return this.limiteCredito;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteUtilizado() {
        return this.limiteUtilizado;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteDisponivel() {
        return this.limiteDisponivel;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataEmissao() {
        return this.dataEmissao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAtivacao() {
        return this.dataAtivacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataBloqueio() {
        return this.dataBloqueio;
    }

    @java.lang.SuppressWarnings("all")
    public String getMotivoBloqueio() {
        return this.motivoBloqueio;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getPermiteComprasNacionais() {
        return this.permiteComprasNacionais;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getPermiteComprasInternacionais() {
        return this.permiteComprasInternacionais;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getPermiteSaque() {
        return this.permiteSaque;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getPermiteParcelamento() {
        return this.permiteParcelamento;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getDiaVencimentoFatura() {
        return this.diaVencimentoFatura;
    }

    @java.lang.SuppressWarnings("all")
    public String getConfiguracoes() {
        return this.configuracoes;
    }

@java.lang.SuppressWarnings("all")
    public void setNumeroCartao(final String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    @java.lang.SuppressWarnings("all")
    public void setNumeroCartaoMascarado(final String numeroCartaoMascarado) {
        this.numeroCartaoMascarado = numeroCartaoMascarado;
    }

    @java.lang.SuppressWarnings("all")
    public void setCvv(final String cvv) {
        this.cvv = cvv;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataValidade(final LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    @java.lang.SuppressWarnings("all")
    public void setNomePortador(final String nomePortador) {
        this.nomePortador = nomePortador;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaId(final Long contaId) {
        this.contaId = contaId;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoCartao(final TipoCartao tipoCartao) {
        this.tipoCartao = tipoCartao;
    }

    @java.lang.SuppressWarnings("all")
    public void setBandeira(final BandeiraCartao bandeira) {
        this.bandeira = bandeira;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusCartao status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteCredito(final BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteUtilizado(final BigDecimal limiteUtilizado) {
        this.limiteUtilizado = limiteUtilizado;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteDisponivel(final BigDecimal limiteDisponivel) {
        this.limiteDisponivel = limiteDisponivel;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataEmissao(final LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAtivacao(final LocalDateTime dataAtivacao) {
        this.dataAtivacao = dataAtivacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataBloqueio(final LocalDateTime dataBloqueio) {
        this.dataBloqueio = dataBloqueio;
    }

    @java.lang.SuppressWarnings("all")
    public void setMotivoBloqueio(final String motivoBloqueio) {
        this.motivoBloqueio = motivoBloqueio;
    }

    @java.lang.SuppressWarnings("all")
    public void setPermiteComprasNacionais(final Boolean permiteComprasNacionais) {
        this.permiteComprasNacionais = permiteComprasNacionais;
    }

    @java.lang.SuppressWarnings("all")
    public void setPermiteComprasInternacionais(final Boolean permiteComprasInternacionais) {
        this.permiteComprasInternacionais = permiteComprasInternacionais;
    }

    @java.lang.SuppressWarnings("all")
    public void setPermiteSaque(final Boolean permiteSaque) {
        this.permiteSaque = permiteSaque;
    }

    @java.lang.SuppressWarnings("all")
    public void setPermiteParcelamento(final Boolean permiteParcelamento) {
        this.permiteParcelamento = permiteParcelamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDiaVencimentoFatura(final Integer diaVencimentoFatura) {
        this.diaVencimentoFatura = diaVencimentoFatura;
    }

    @java.lang.SuppressWarnings("all")
    public void setConfiguracoes(final String configuracoes) {
        this.configuracoes = configuracoes;
    }

    @java.lang.SuppressWarnings("all")
    public Long getProdutoId() {
        return this.produtoId;
    }

    @java.lang.SuppressWarnings("all")
    public Long getBandeiraParceiroId() {
        return this.bandeiraParceiroId;
    }

    @java.lang.SuppressWarnings("all")
    public String getTenantId() {
        return this.tenantId;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataCancelamento() {
        return this.dataCancelamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setProdutoId(final Long produtoId) {
        this.produtoId = produtoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setBandeiraParceiroId(final Long bandeiraParceiroId) {
        this.bandeiraParceiroId = bandeiraParceiroId;
    }

    @java.lang.SuppressWarnings("all")
    public void setTenantId(final String tenantId) {
        this.tenantId = tenantId;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataCancelamento(final LocalDateTime dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Cartao(id=" + this.getId() + ", numeroCartao=" + this.getNumeroCartao() + ", numeroCartaoMascarado=" + this.getNumeroCartaoMascarado() + ", cvv=" + this.getCvv() + ", dataValidade=" + this.getDataValidade() + ", nomePortador=" + this.getNomePortador() + ", contaId=" + this.getContaId() + ", tipoCartao=" + this.getTipoCartao() + ", bandeira=" + this.getBandeira() + ", status=" + this.getStatus() + ", limiteCredito=" + this.getLimiteCredito() + ", limiteUtilizado=" + this.getLimiteUtilizado() + ", limiteDisponivel=" + this.getLimiteDisponivel() + ", dataEmissao=" + this.getDataEmissao() + ", dataAtivacao=" + this.getDataAtivacao() + ", dataBloqueio=" + this.getDataBloqueio() + ", motivoBloqueio=" + this.getMotivoBloqueio() + ", permiteComprasNacionais=" + this.getPermiteComprasNacionais() + ", permiteComprasInternacionais=" + this.getPermiteComprasInternacionais() + ", permiteSaque=" + this.getPermiteSaque() + ", permiteParcelamento=" + this.getPermiteParcelamento() + ", diaVencimentoFatura=" + this.getDiaVencimentoFatura() + ", configuracoes=" + this.getConfiguracoes() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public Cartao() {
    }

    @java.lang.SuppressWarnings("all")
    public Cartao(final Long id, final String numeroCartao, final String numeroCartaoMascarado, final String cvv, final LocalDate dataValidade, final String nomePortador, final Long contaId, final TipoCartao tipoCartao, final BandeiraCartao bandeira, final StatusCartao status, final BigDecimal limiteCredito, final BigDecimal limiteUtilizado, final BigDecimal limiteDisponivel, final LocalDateTime dataEmissao, final LocalDateTime dataAtivacao, final LocalDateTime dataBloqueio, final String motivoBloqueio, final Boolean permiteComprasNacionais, final Boolean permiteComprasInternacionais, final Boolean permiteSaque, final Boolean permiteParcelamento, final Integer diaVencimentoFatura, final String configuracoes) {
        this.setId(id);
        this.numeroCartao = numeroCartao;
        this.numeroCartaoMascarado = numeroCartaoMascarado;
        this.cvv = cvv;
        this.dataValidade = dataValidade;
        this.nomePortador = nomePortador;
        this.contaId = contaId;
        this.tipoCartao = tipoCartao;
        this.bandeira = bandeira;
        this.status = status;
        this.limiteCredito = limiteCredito;
        this.limiteUtilizado = limiteUtilizado;
        this.limiteDisponivel = limiteDisponivel;
        this.dataEmissao = dataEmissao;
        this.dataAtivacao = dataAtivacao;
        this.dataBloqueio = dataBloqueio;
        this.motivoBloqueio = motivoBloqueio;
        this.permiteComprasNacionais = permiteComprasNacionais;
        this.permiteComprasInternacionais = permiteComprasInternacionais;
        this.permiteSaque = permiteSaque;
        this.permiteParcelamento = permiteParcelamento;
        this.diaVencimentoFatura = diaVencimentoFatura;
        this.configuracoes = configuracoes;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Cartao)) return false;
        final Cartao other = (Cartao) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$contaId = this.getContaId();
        final java.lang.Object other$contaId = other.getContaId();
        if (this$contaId == null ? other$contaId != null : !this$contaId.equals(other$contaId)) return false;
        final java.lang.Object this$permiteComprasNacionais = this.getPermiteComprasNacionais();
        final java.lang.Object other$permiteComprasNacionais = other.getPermiteComprasNacionais();
        if (this$permiteComprasNacionais == null ? other$permiteComprasNacionais != null : !this$permiteComprasNacionais.equals(other$permiteComprasNacionais)) return false;
        final java.lang.Object this$permiteComprasInternacionais = this.getPermiteComprasInternacionais();
        final java.lang.Object other$permiteComprasInternacionais = other.getPermiteComprasInternacionais();
        if (this$permiteComprasInternacionais == null ? other$permiteComprasInternacionais != null : !this$permiteComprasInternacionais.equals(other$permiteComprasInternacionais)) return false;
        final java.lang.Object this$permiteSaque = this.getPermiteSaque();
        final java.lang.Object other$permiteSaque = other.getPermiteSaque();
        if (this$permiteSaque == null ? other$permiteSaque != null : !this$permiteSaque.equals(other$permiteSaque)) return false;
        final java.lang.Object this$permiteParcelamento = this.getPermiteParcelamento();
        final java.lang.Object other$permiteParcelamento = other.getPermiteParcelamento();
        if (this$permiteParcelamento == null ? other$permiteParcelamento != null : !this$permiteParcelamento.equals(other$permiteParcelamento)) return false;
        final java.lang.Object this$diaVencimentoFatura = this.getDiaVencimentoFatura();
        final java.lang.Object other$diaVencimentoFatura = other.getDiaVencimentoFatura();
        if (this$diaVencimentoFatura == null ? other$diaVencimentoFatura != null : !this$diaVencimentoFatura.equals(other$diaVencimentoFatura)) return false;
        final java.lang.Object this$numeroCartao = this.getNumeroCartao();
        final java.lang.Object other$numeroCartao = other.getNumeroCartao();
        if (this$numeroCartao == null ? other$numeroCartao != null : !this$numeroCartao.equals(other$numeroCartao)) return false;
        final java.lang.Object this$numeroCartaoMascarado = this.getNumeroCartaoMascarado();
        final java.lang.Object other$numeroCartaoMascarado = other.getNumeroCartaoMascarado();
        if (this$numeroCartaoMascarado == null ? other$numeroCartaoMascarado != null : !this$numeroCartaoMascarado.equals(other$numeroCartaoMascarado)) return false;
        final java.lang.Object this$cvv = this.getCvv();
        final java.lang.Object other$cvv = other.getCvv();
        if (this$cvv == null ? other$cvv != null : !this$cvv.equals(other$cvv)) return false;
        final java.lang.Object this$dataValidade = this.getDataValidade();
        final java.lang.Object other$dataValidade = other.getDataValidade();
        if (this$dataValidade == null ? other$dataValidade != null : !this$dataValidade.equals(other$dataValidade)) return false;
        final java.lang.Object this$nomePortador = this.getNomePortador();
        final java.lang.Object other$nomePortador = other.getNomePortador();
        if (this$nomePortador == null ? other$nomePortador != null : !this$nomePortador.equals(other$nomePortador)) return false;
        final java.lang.Object this$tipoCartao = this.getTipoCartao();
        final java.lang.Object other$tipoCartao = other.getTipoCartao();
        if (this$tipoCartao == null ? other$tipoCartao != null : !this$tipoCartao.equals(other$tipoCartao)) return false;
        final java.lang.Object this$bandeira = this.getBandeira();
        final java.lang.Object other$bandeira = other.getBandeira();
        if (this$bandeira == null ? other$bandeira != null : !this$bandeira.equals(other$bandeira)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$limiteCredito = this.getLimiteCredito();
        final java.lang.Object other$limiteCredito = other.getLimiteCredito();
        if (this$limiteCredito == null ? other$limiteCredito != null : !this$limiteCredito.equals(other$limiteCredito)) return false;
        final java.lang.Object this$limiteUtilizado = this.getLimiteUtilizado();
        final java.lang.Object other$limiteUtilizado = other.getLimiteUtilizado();
        if (this$limiteUtilizado == null ? other$limiteUtilizado != null : !this$limiteUtilizado.equals(other$limiteUtilizado)) return false;
        final java.lang.Object this$limiteDisponivel = this.getLimiteDisponivel();
        final java.lang.Object other$limiteDisponivel = other.getLimiteDisponivel();
        if (this$limiteDisponivel == null ? other$limiteDisponivel != null : !this$limiteDisponivel.equals(other$limiteDisponivel)) return false;
        final java.lang.Object this$dataEmissao = this.getDataEmissao();
        final java.lang.Object other$dataEmissao = other.getDataEmissao();
        if (this$dataEmissao == null ? other$dataEmissao != null : !this$dataEmissao.equals(other$dataEmissao)) return false;
        final java.lang.Object this$dataAtivacao = this.getDataAtivacao();
        final java.lang.Object other$dataAtivacao = other.getDataAtivacao();
        if (this$dataAtivacao == null ? other$dataAtivacao != null : !this$dataAtivacao.equals(other$dataAtivacao)) return false;
        final java.lang.Object this$dataBloqueio = this.getDataBloqueio();
        final java.lang.Object other$dataBloqueio = other.getDataBloqueio();
        if (this$dataBloqueio == null ? other$dataBloqueio != null : !this$dataBloqueio.equals(other$dataBloqueio)) return false;
        final java.lang.Object this$motivoBloqueio = this.getMotivoBloqueio();
        final java.lang.Object other$motivoBloqueio = other.getMotivoBloqueio();
        if (this$motivoBloqueio == null ? other$motivoBloqueio != null : !this$motivoBloqueio.equals(other$motivoBloqueio)) return false;
        final java.lang.Object this$configuracoes = this.getConfiguracoes();
        final java.lang.Object other$configuracoes = other.getConfiguracoes();
        if (this$configuracoes == null ? other$configuracoes != null : !this$configuracoes.equals(other$configuracoes)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Cartao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $contaId = this.getContaId();
        result = result * PRIME + ($contaId == null ? 43 : $contaId.hashCode());
        final java.lang.Object $permiteComprasNacionais = this.getPermiteComprasNacionais();
        result = result * PRIME + ($permiteComprasNacionais == null ? 43 : $permiteComprasNacionais.hashCode());
        final java.lang.Object $permiteComprasInternacionais = this.getPermiteComprasInternacionais();
        result = result * PRIME + ($permiteComprasInternacionais == null ? 43 : $permiteComprasInternacionais.hashCode());
        final java.lang.Object $permiteSaque = this.getPermiteSaque();
        result = result * PRIME + ($permiteSaque == null ? 43 : $permiteSaque.hashCode());
        final java.lang.Object $permiteParcelamento = this.getPermiteParcelamento();
        result = result * PRIME + ($permiteParcelamento == null ? 43 : $permiteParcelamento.hashCode());
        final java.lang.Object $diaVencimentoFatura = this.getDiaVencimentoFatura();
        result = result * PRIME + ($diaVencimentoFatura == null ? 43 : $diaVencimentoFatura.hashCode());
        final java.lang.Object $numeroCartao = this.getNumeroCartao();
        result = result * PRIME + ($numeroCartao == null ? 43 : $numeroCartao.hashCode());
        final java.lang.Object $numeroCartaoMascarado = this.getNumeroCartaoMascarado();
        result = result * PRIME + ($numeroCartaoMascarado == null ? 43 : $numeroCartaoMascarado.hashCode());
        final java.lang.Object $cvv = this.getCvv();
        result = result * PRIME + ($cvv == null ? 43 : $cvv.hashCode());
        final java.lang.Object $dataValidade = this.getDataValidade();
        result = result * PRIME + ($dataValidade == null ? 43 : $dataValidade.hashCode());
        final java.lang.Object $nomePortador = this.getNomePortador();
        result = result * PRIME + ($nomePortador == null ? 43 : $nomePortador.hashCode());
        final java.lang.Object $tipoCartao = this.getTipoCartao();
        result = result * PRIME + ($tipoCartao == null ? 43 : $tipoCartao.hashCode());
        final java.lang.Object $bandeira = this.getBandeira();
        result = result * PRIME + ($bandeira == null ? 43 : $bandeira.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $limiteCredito = this.getLimiteCredito();
        result = result * PRIME + ($limiteCredito == null ? 43 : $limiteCredito.hashCode());
        final java.lang.Object $limiteUtilizado = this.getLimiteUtilizado();
        result = result * PRIME + ($limiteUtilizado == null ? 43 : $limiteUtilizado.hashCode());
        final java.lang.Object $limiteDisponivel = this.getLimiteDisponivel();
        result = result * PRIME + ($limiteDisponivel == null ? 43 : $limiteDisponivel.hashCode());
        final java.lang.Object $dataEmissao = this.getDataEmissao();
        result = result * PRIME + ($dataEmissao == null ? 43 : $dataEmissao.hashCode());
        final java.lang.Object $dataAtivacao = this.getDataAtivacao();
        result = result * PRIME + ($dataAtivacao == null ? 43 : $dataAtivacao.hashCode());
        final java.lang.Object $dataBloqueio = this.getDataBloqueio();
        result = result * PRIME + ($dataBloqueio == null ? 43 : $dataBloqueio.hashCode());
        final java.lang.Object $motivoBloqueio = this.getMotivoBloqueio();
        result = result * PRIME + ($motivoBloqueio == null ? 43 : $motivoBloqueio.hashCode());
        final java.lang.Object $configuracoes = this.getConfiguracoes();
        result = result * PRIME + ($configuracoes == null ? 43 : $configuracoes.hashCode());
        return result;
    }
}
