package com.aurix.platform.platform.service;

import com.aurix.platform.platform.entity.Orcamento;
import com.aurix.platform.platform.repository.OrcamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service para gestão de orçamentos
 * 
 * Gerencia todo o ciclo de vida dos orçamentos
 */
@Service
@Transactional
public class OrcamentoService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OrcamentoService.class);
    private final OrcamentoRepository orcamentoRepository;

    /**
     * Cria um novo orçamento
     */
    public Orcamento criarOrcamento(Orcamento orcamento) {
        log.info("Criando orçamento: {} - Ano: {}", orcamento.getNome(), orcamento.getAno());
        // Gerar código único se não fornecido
        if (orcamento.getCodigoOrcamento() == null) {
            orcamento.setCodigoOrcamento(gerarCodigoOrcamento(orcamento.getAno(), orcamento.getTipoOrcamento()));
        }
        // Definir status inicial
        orcamento.setStatus(Orcamento.StatusOrcamento.RASCUNHO);
        // Calcular valores iniciais
        calcularValoresOrcamento(orcamento);
        Orcamento orcamentoSalvo = orcamentoRepository.save(orcamento);
        log.info("Orçamento criado: {} - ID: {}", orcamento.getCodigoOrcamento(), orcamentoSalvo.getId());
        return orcamentoSalvo;
    }

    /**
     * Aprova um orçamento
     */
    public Orcamento aprovarOrcamento(Long id, String aprovadoPor) {
        log.info("Aprovando orçamento: {}", id);
        Orcamento orcamento = orcamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Orçamento não encontrado: " + id));
        if (orcamento.getStatus() != Orcamento.StatusOrcamento.RASCUNHO) {
            throw new RuntimeException("Orçamento não está em rascunho para aprovação");
        }
        orcamento.setStatus(Orcamento.StatusOrcamento.APROVADO);
        orcamento.setAprovadoPor(aprovadoPor);
        orcamento.setDataAprovacao(LocalDateTime.now());
        Orcamento orcamentoAprovado = orcamentoRepository.save(orcamento);
        log.info("Orçamento aprovado: {}", id);
        return orcamentoAprovado;
    }

    /**
     * Inicia execução de um orçamento
     */
    public Orcamento iniciarExecucao(Long id) {
        log.info("Iniciando execução do orçamento: {}", id);
        Orcamento orcamento = orcamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Orçamento não encontrado: " + id));
        if (orcamento.getStatus() != Orcamento.StatusOrcamento.APROVADO) {
            throw new RuntimeException("Orçamento não está aprovado para execução");
        }
        if (LocalDate.now().isBefore(orcamento.getDataInicio())) {
            throw new RuntimeException("Data de início ainda não foi atingida");
        }
        orcamento.setStatus(Orcamento.StatusOrcamento.EXECUTANDO);
        Orcamento orcamentoEmExecucao = orcamentoRepository.save(orcamento);
        log.info("Execução iniciada para orçamento: {}", id);
        return orcamentoEmExecucao;
    }

    /**
     * Atualiza valores realizados
     */
    public Orcamento atualizarValoresRealizados(Long id, BigDecimal valorRealizado) {
        log.info("Atualizando valores realizados do orçamento: {} - Valor: {}", id, valorRealizado);
        Orcamento orcamento = orcamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Orçamento não encontrado: " + id));
        if (orcamento.getStatus() != Orcamento.StatusOrcamento.EXECUTANDO) {
            throw new RuntimeException("Orçamento não está em execução");
        }
        orcamento.setValorTotalRealizado(valorRealizado);
        // Calcular variações
        calcularVariacoes(orcamento);
        Orcamento orcamentoAtualizado = orcamentoRepository.save(orcamento);
        log.info("Valores atualizados para orçamento: {}", id);
        return orcamentoAtualizado;
    }

    /**
     * Fecha um orçamento
     */
    public Orcamento fecharOrcamento(Long id) {
        log.info("Fechando orçamento: {}", id);
        Orcamento orcamento = orcamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Orçamento não encontrado: " + id));
        if (orcamento.getStatus() != Orcamento.StatusOrcamento.EXECUTANDO) {
            throw new RuntimeException("Orçamento não está em execução");
        }
        orcamento.setStatus(Orcamento.StatusOrcamento.FECHADO);
        Orcamento orcamentoFechado = orcamentoRepository.save(orcamento);
        log.info("Orçamento fechado: {}", id);
        return orcamentoFechado;
    }

    /**
     * Busca orçamentos por ano
     */
    public List<Orcamento> buscarOrcamentosPorAno(Integer ano) {
        log.info("Buscando orçamentos do ano: {}", ano);
        List<Orcamento> orcamentos = orcamentoRepository.findByAno(ano);
        log.info("Encontrados {} orçamentos para o ano {}", orcamentos.size(), ano);
        return orcamentos;
    }

    /**
     * Busca orçamentos em execução
     */
    public List<Orcamento> buscarOrcamentosEmExecucao() {
        log.info("Buscando orçamentos em execução");
        List<Orcamento> orcamentos = orcamentoRepository.findOrcamentosEmExecucao(LocalDate.now());
        log.info("Encontrados {} orçamentos em execução", orcamentos.size());
        return orcamentos;
    }

    /**
     * Busca orçamentos com variação crítica
     */
    public List<Orcamento> buscarOrcamentosComVariacaoCritica(BigDecimal limiteVariacao) {
        log.info("Buscando orçamentos com variação crítica: {}%", limiteVariacao.multiply(BigDecimal.valueOf(100)));
        List<Orcamento> orcamentos = orcamentoRepository.findOrcamentosComVariacaoAcimaLimite(limiteVariacao);
        log.info("Encontrados {} orçamentos com variação crítica", orcamentos.size());
        return orcamentos;
    }

    /**
     * Calcula resumo anual
     */
    public ResumoAnual calcularResumoAnual(Integer ano) {
        log.info("Calculando resumo anual: {}", ano);
        BigDecimal valorOrcado = orcamentoRepository.somaValorOrcadoPorAno(ano);
        BigDecimal valorRealizado = orcamentoRepository.somaValorRealizadoPorAno(ano);
        if (valorOrcado == null) valorOrcado = BigDecimal.ZERO;
        if (valorRealizado == null) valorRealizado = BigDecimal.ZERO;
        BigDecimal variacao = valorRealizado.subtract(valorOrcado);
        BigDecimal percentualVariacao = valorOrcado.compareTo(BigDecimal.ZERO) > 0 ? variacao.divide(valorOrcado, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)) : BigDecimal.ZERO;
        ResumoAnual resumo = ResumoAnual.builder().ano(ano).valorOrcado(valorOrcado).valorRealizado(valorRealizado).variacao(variacao).percentualVariacao(percentualVariacao).totalOrcamentos(orcamentoRepository.countByAno(ano)).orcamentosAprovados(orcamentoRepository.countByStatus(Orcamento.StatusOrcamento.APROVADO)).orcamentosEmExecucao(orcamentoRepository.countByStatus(Orcamento.StatusOrcamento.EXECUTANDO)).build();
        log.info("Resumo anual calculado: Orçado={}, Realizado={}, Variação={}%", valorOrcado, valorRealizado, percentualVariacao);
        return resumo;
    }

    /**
     * Gera código único para orçamento
     */
    private String gerarCodigoOrcamento(Integer ano, Orcamento.TipoOrcamento tipo) {
        return String.format("ORC-%s-%s-%d", ano, tipo.name(), System.currentTimeMillis() % 10000);
    }

    /**
     * Calcula valores do orçamento
     */
    private void calcularValoresOrcamento(Orcamento orcamento) {
        // Valores iniciais
        if (orcamento.getValorTotalOrcado() == null) {
            orcamento.setValorTotalOrcado(BigDecimal.ZERO);
        }
        if (orcamento.getValorTotalRealizado() == null) {
            orcamento.setValorTotalRealizado(BigDecimal.ZERO);
        }
        calcularVariacoes(orcamento);
    }

    /**
     * Calcula variações do orçamento
     */
    private void calcularVariacoes(Orcamento orcamento) {
        BigDecimal valorOrcado = orcamento.getValorTotalOrcado();
        BigDecimal valorRealizado = orcamento.getValorTotalRealizado();
        if (valorOrcado != null && valorRealizado != null) {
            BigDecimal variacao = valorRealizado.subtract(valorOrcado);
            orcamento.setVariacaoTotal(variacao);
            if (valorOrcado.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal percentual = variacao.divide(valorOrcado, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                orcamento.setPercentualVariacao(percentual);
            } else {
                orcamento.setPercentualVariacao(BigDecimal.ZERO);
            }
        }
    }


    /**
     * Classe para resumo anual
     */
    public static class ResumoAnual {
        private Integer ano;
        private BigDecimal valorOrcado;
        private BigDecimal valorRealizado;
        private BigDecimal variacao;
        private BigDecimal percentualVariacao;
        private Long totalOrcamentos;
        private Long orcamentosAprovados;
        private Long orcamentosEmExecucao;

        @java.lang.SuppressWarnings("all")
        ResumoAnual(final Integer ano, final BigDecimal valorOrcado, final BigDecimal valorRealizado, final BigDecimal variacao, final BigDecimal percentualVariacao, final Long totalOrcamentos, final Long orcamentosAprovados, final Long orcamentosEmExecucao) {
            this.ano = ano;
            this.valorOrcado = valorOrcado;
            this.valorRealizado = valorRealizado;
            this.variacao = variacao;
            this.percentualVariacao = percentualVariacao;
            this.totalOrcamentos = totalOrcamentos;
            this.orcamentosAprovados = orcamentosAprovados;
            this.orcamentosEmExecucao = orcamentosEmExecucao;
        }


        @java.lang.SuppressWarnings("all")
        public static class ResumoAnualBuilder {
            @java.lang.SuppressWarnings("all")
            private Integer ano;
            @java.lang.SuppressWarnings("all")
            private BigDecimal valorOrcado;
            @java.lang.SuppressWarnings("all")
            private BigDecimal valorRealizado;
            @java.lang.SuppressWarnings("all")
            private BigDecimal variacao;
            @java.lang.SuppressWarnings("all")
            private BigDecimal percentualVariacao;
            @java.lang.SuppressWarnings("all")
            private Long totalOrcamentos;
            @java.lang.SuppressWarnings("all")
            private Long orcamentosAprovados;
            @java.lang.SuppressWarnings("all")
            private Long orcamentosEmExecucao;

            @java.lang.SuppressWarnings("all")
            ResumoAnualBuilder() {
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public OrcamentoService.ResumoAnual.ResumoAnualBuilder ano(final Integer ano) {
                this.ano = ano;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public OrcamentoService.ResumoAnual.ResumoAnualBuilder valorOrcado(final BigDecimal valorOrcado) {
                this.valorOrcado = valorOrcado;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public OrcamentoService.ResumoAnual.ResumoAnualBuilder valorRealizado(final BigDecimal valorRealizado) {
                this.valorRealizado = valorRealizado;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public OrcamentoService.ResumoAnual.ResumoAnualBuilder variacao(final BigDecimal variacao) {
                this.variacao = variacao;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public OrcamentoService.ResumoAnual.ResumoAnualBuilder percentualVariacao(final BigDecimal percentualVariacao) {
                this.percentualVariacao = percentualVariacao;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public OrcamentoService.ResumoAnual.ResumoAnualBuilder totalOrcamentos(final Long totalOrcamentos) {
                this.totalOrcamentos = totalOrcamentos;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public OrcamentoService.ResumoAnual.ResumoAnualBuilder orcamentosAprovados(final Long orcamentosAprovados) {
                this.orcamentosAprovados = orcamentosAprovados;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public OrcamentoService.ResumoAnual.ResumoAnualBuilder orcamentosEmExecucao(final Long orcamentosEmExecucao) {
                this.orcamentosEmExecucao = orcamentosEmExecucao;
                return this;
            }

            @java.lang.SuppressWarnings("all")
            public OrcamentoService.ResumoAnual build() {
                return new OrcamentoService.ResumoAnual(this.ano, this.valorOrcado, this.valorRealizado, this.variacao, this.percentualVariacao, this.totalOrcamentos, this.orcamentosAprovados, this.orcamentosEmExecucao);
            }

            @java.lang.Override
            @java.lang.SuppressWarnings("all")
            public java.lang.String toString() {
                return "OrcamentoService.ResumoAnual.ResumoAnualBuilder(ano=" + this.ano + ", valorOrcado=" + this.valorOrcado + ", valorRealizado=" + this.valorRealizado + ", variacao=" + this.variacao + ", percentualVariacao=" + this.percentualVariacao + ", totalOrcamentos=" + this.totalOrcamentos + ", orcamentosAprovados=" + this.orcamentosAprovados + ", orcamentosEmExecucao=" + this.orcamentosEmExecucao + ")";
            }
        }

        @java.lang.SuppressWarnings("all")
        public static OrcamentoService.ResumoAnual.ResumoAnualBuilder builder() {
            return new OrcamentoService.ResumoAnual.ResumoAnualBuilder();
        }

        @java.lang.SuppressWarnings("all")
        public Integer getAno() {
            return this.ano;
        }

        @java.lang.SuppressWarnings("all")
        public BigDecimal getValorOrcado() {
            return this.valorOrcado;
        }

        @java.lang.SuppressWarnings("all")
        public BigDecimal getValorRealizado() {
            return this.valorRealizado;
        }

        @java.lang.SuppressWarnings("all")
        public BigDecimal getVariacao() {
            return this.variacao;
        }

        @java.lang.SuppressWarnings("all")
        public BigDecimal getPercentualVariacao() {
            return this.percentualVariacao;
        }

        @java.lang.SuppressWarnings("all")
        public Long getTotalOrcamentos() {
            return this.totalOrcamentos;
        }

        @java.lang.SuppressWarnings("all")
        public Long getOrcamentosAprovados() {
            return this.orcamentosAprovados;
        }

        @java.lang.SuppressWarnings("all")
        public Long getOrcamentosEmExecucao() {
            return this.orcamentosEmExecucao;
        }

        @java.lang.SuppressWarnings("all")
        public void setAno(final Integer ano) {
            this.ano = ano;
        }

        @java.lang.SuppressWarnings("all")
        public void setValorOrcado(final BigDecimal valorOrcado) {
            this.valorOrcado = valorOrcado;
        }

        @java.lang.SuppressWarnings("all")
        public void setValorRealizado(final BigDecimal valorRealizado) {
            this.valorRealizado = valorRealizado;
        }

        @java.lang.SuppressWarnings("all")
        public void setVariacao(final BigDecimal variacao) {
            this.variacao = variacao;
        }

        @java.lang.SuppressWarnings("all")
        public void setPercentualVariacao(final BigDecimal percentualVariacao) {
            this.percentualVariacao = percentualVariacao;
        }

        @java.lang.SuppressWarnings("all")
        public void setTotalOrcamentos(final Long totalOrcamentos) {
            this.totalOrcamentos = totalOrcamentos;
        }

        @java.lang.SuppressWarnings("all")
        public void setOrcamentosAprovados(final Long orcamentosAprovados) {
            this.orcamentosAprovados = orcamentosAprovados;
        }

        @java.lang.SuppressWarnings("all")
        public void setOrcamentosEmExecucao(final Long orcamentosEmExecucao) {
            this.orcamentosEmExecucao = orcamentosEmExecucao;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof OrcamentoService.ResumoAnual)) return false;
            final OrcamentoService.ResumoAnual other = (OrcamentoService.ResumoAnual) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$ano = this.getAno();
            final java.lang.Object other$ano = other.getAno();
            if (this$ano == null ? other$ano != null : !this$ano.equals(other$ano)) return false;
            final java.lang.Object this$totalOrcamentos = this.getTotalOrcamentos();
            final java.lang.Object other$totalOrcamentos = other.getTotalOrcamentos();
            if (this$totalOrcamentos == null ? other$totalOrcamentos != null : !this$totalOrcamentos.equals(other$totalOrcamentos)) return false;
            final java.lang.Object this$orcamentosAprovados = this.getOrcamentosAprovados();
            final java.lang.Object other$orcamentosAprovados = other.getOrcamentosAprovados();
            if (this$orcamentosAprovados == null ? other$orcamentosAprovados != null : !this$orcamentosAprovados.equals(other$orcamentosAprovados)) return false;
            final java.lang.Object this$orcamentosEmExecucao = this.getOrcamentosEmExecucao();
            final java.lang.Object other$orcamentosEmExecucao = other.getOrcamentosEmExecucao();
            if (this$orcamentosEmExecucao == null ? other$orcamentosEmExecucao != null : !this$orcamentosEmExecucao.equals(other$orcamentosEmExecucao)) return false;
            final java.lang.Object this$valorOrcado = this.getValorOrcado();
            final java.lang.Object other$valorOrcado = other.getValorOrcado();
            if (this$valorOrcado == null ? other$valorOrcado != null : !this$valorOrcado.equals(other$valorOrcado)) return false;
            final java.lang.Object this$valorRealizado = this.getValorRealizado();
            final java.lang.Object other$valorRealizado = other.getValorRealizado();
            if (this$valorRealizado == null ? other$valorRealizado != null : !this$valorRealizado.equals(other$valorRealizado)) return false;
            final java.lang.Object this$variacao = this.getVariacao();
            final java.lang.Object other$variacao = other.getVariacao();
            if (this$variacao == null ? other$variacao != null : !this$variacao.equals(other$variacao)) return false;
            final java.lang.Object this$percentualVariacao = this.getPercentualVariacao();
            final java.lang.Object other$percentualVariacao = other.getPercentualVariacao();
            if (this$percentualVariacao == null ? other$percentualVariacao != null : !this$percentualVariacao.equals(other$percentualVariacao)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof OrcamentoService.ResumoAnual;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $ano = this.getAno();
            result = result * PRIME + ($ano == null ? 43 : $ano.hashCode());
            final java.lang.Object $totalOrcamentos = this.getTotalOrcamentos();
            result = result * PRIME + ($totalOrcamentos == null ? 43 : $totalOrcamentos.hashCode());
            final java.lang.Object $orcamentosAprovados = this.getOrcamentosAprovados();
            result = result * PRIME + ($orcamentosAprovados == null ? 43 : $orcamentosAprovados.hashCode());
            final java.lang.Object $orcamentosEmExecucao = this.getOrcamentosEmExecucao();
            result = result * PRIME + ($orcamentosEmExecucao == null ? 43 : $orcamentosEmExecucao.hashCode());
            final java.lang.Object $valorOrcado = this.getValorOrcado();
            result = result * PRIME + ($valorOrcado == null ? 43 : $valorOrcado.hashCode());
            final java.lang.Object $valorRealizado = this.getValorRealizado();
            result = result * PRIME + ($valorRealizado == null ? 43 : $valorRealizado.hashCode());
            final java.lang.Object $variacao = this.getVariacao();
            result = result * PRIME + ($variacao == null ? 43 : $variacao.hashCode());
            final java.lang.Object $percentualVariacao = this.getPercentualVariacao();
            result = result * PRIME + ($percentualVariacao == null ? 43 : $percentualVariacao.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "OrcamentoService.ResumoAnual(ano=" + this.getAno() + ", valorOrcado=" + this.getValorOrcado() + ", valorRealizado=" + this.getValorRealizado() + ", variacao=" + this.getVariacao() + ", percentualVariacao=" + this.getPercentualVariacao() + ", totalOrcamentos=" + this.getTotalOrcamentos() + ", orcamentosAprovados=" + this.getOrcamentosAprovados() + ", orcamentosEmExecucao=" + this.getOrcamentosEmExecucao() + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public OrcamentoService(final OrcamentoRepository orcamentoRepository) {
        this.orcamentoRepository = orcamentoRepository;
    }
}
