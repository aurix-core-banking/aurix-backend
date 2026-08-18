package com.aurix.platform.compliance.pep.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "pep_clientes", schema = "aurix")
public class PepCliente extends BaseEntity {

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "cpf_cnpj", nullable = false, length = 14)
    private String cpfCnpj;

    @Column(name = "nome_completo", nullable = false, length = 255)
    private String nomeCompleto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassificacaoPep classificacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPep status = StatusPep.ATIVO;

    @Column(name = "cargo_funcao", length = 255)
    private String cargoFuncao;

    @Column(name = "orgao_instituicao", length = 255)
    private String orgaoInstituicao;

    @Column(name = "esfera_governo", length = 50)
    private String esferaGoverno;

    @Column(name = "uf_exercicio", length = 2)
    private String ufExercicio;

    @Column(name = "data_inicio_cargos")
    private LocalDateTime dataInicioCargos;

    @Column(name = "data_fim_cargos")
    private LocalDateTime dataFimCargos;

    @Column(name = "renda_declarada")
    private java.math.BigDecimal rendaDeclarada;

    @Column(name = "patrimonio_declarado")
    private java.math.BigDecimal patrimonioDeclarado;

    @Column(name = "cep_cliente", length = 8)
    private String cepCliente;

    @Column(name = "profissao", length = 100)
    private String profissao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelRiscoPep nivelRisco = NivelRiscoPep.MEDIO;

    @Column(name = "data_verificacao")
    private LocalDateTime dataVerificacao;

    @Column(name = "data_proxima_verificacao")
    private LocalDateTime dataProximaVerificacao;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSONB")
    private String historicoCargos;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSONB")
    private String vinculos_familiares;

    public enum ClassificacaoPep {
        TITULAR, CONJUGE, FILHO, DEPENDENTE, REPRESENTANTE, ADMINISTRADOR
    }

    public enum StatusPep {
        ATIVO, INATIVO, SUSPENSO, EM_VERIFICACAO
    }

    public enum NivelRiscoPep {
        BAIXO, MEDIO, ALTO, MUITO_ALTO
    }

    @SuppressWarnings("all")
    public PepCliente() {
    }

    public Long getClienteId() {
        return this.clienteId;
    }

    public String getCpfCnpj() {
        return this.cpfCnpj;
    }

    public String getNomeCompleto() {
        return this.nomeCompleto;
    }

    public ClassificacaoPep getClassificacao() {
        return this.classificacao;
    }

    public StatusPep getStatus() {
        return this.status;
    }

    public String getCargoFuncao() {
        return this.cargoFuncao;
    }

    public String getOrgaoInstituicao() {
        return this.orgaoInstituicao;
    }

    public String getEsferaGoverno() {
        return this.esferaGoverno;
    }

    public String getUfExercicio() {
        return this.ufExercicio;
    }

    public LocalDateTime getDataInicioCargos() {
        return this.dataInicioCargos;
    }

    public LocalDateTime getDataFimCargos() {
        return this.dataFimCargos;
    }

    public java.math.BigDecimal getRendaDeclarada() {
        return this.rendaDeclarada;
    }

    public java.math.BigDecimal getPatrimonioDeclarado() {
        return this.patrimonioDeclarado;
    }

    public String getCepCliente() {
        return this.cepCliente;
    }

    public String getProfissao() {
        return this.profissao;
    }

    public NivelRiscoPep getNivelRisco() {
        return this.nivelRisco;
    }

    public LocalDateTime getDataVerificacao() {
        return this.dataVerificacao;
    }

    public LocalDateTime getDataProximaVerificacao() {
        return this.dataProximaVerificacao;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public String getHistoricoCargos() {
        return this.historicoCargos;
    }

    public String getVinculos_familiares() {
        return this.vinculos_familiares;
    }

    public void setClienteId(final Long clienteId) {
        this.clienteId = clienteId;
    }

    public void setCpfCnpj(final String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public void setNomeCompleto(final String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setClassificacao(final ClassificacaoPep classificacao) {
        this.classificacao = classificacao;
    }

    public void setStatus(final StatusPep status) {
        this.status = status;
    }

    public void setCargoFuncao(final String cargoFuncao) {
        this.cargoFuncao = cargoFuncao;
    }

    public void setOrgaoInstituicao(final String orgaoInstituicao) {
        this.orgaoInstituicao = orgaoInstituicao;
    }

    public void setEsferaGoverno(final String esferaGoverno) {
        this.esferaGoverno = esferaGoverno;
    }

    public void setUfExercicio(final String ufExercicio) {
        this.ufExercicio = ufExercicio;
    }

    public void setDataInicioCargos(final LocalDateTime dataInicioCargos) {
        this.dataInicioCargos = dataInicioCargos;
    }

    public void setDataFimCargos(final LocalDateTime dataFimCargos) {
        this.dataFimCargos = dataFimCargos;
    }

    public void setRendaDeclarada(final java.math.BigDecimal rendaDeclarada) {
        this.rendaDeclarada = rendaDeclarada;
    }

    public void setPatrimonioDeclarado(final java.math.BigDecimal patrimonioDeclarado) {
        this.patrimonioDeclarado = patrimonioDeclarado;
    }

    public void setCepCliente(final String cepCliente) {
        this.cepCliente = cepCliente;
    }

    public void setProfissao(final String profissao) {
        this.profissao = profissao;
    }

    public void setNivelRisco(final NivelRiscoPep nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    public void setDataVerificacao(final LocalDateTime dataVerificacao) {
        this.dataVerificacao = dataVerificacao;
    }

    public void setDataProximaVerificacao(final LocalDateTime dataProximaVerificacao) {
        this.dataProximaVerificacao = dataProximaVerificacao;
    }

    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    public void setHistoricoCargos(final String historicoCargos) {
        this.historicoCargos = historicoCargos;
    }

    public void setVinculos_familiares(final String vinculos_familiares) {
        this.vinculos_familiares = vinculos_familiares;
    }
}
