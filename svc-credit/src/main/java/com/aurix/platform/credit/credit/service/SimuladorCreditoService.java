package com.aurix.platform.credit.credit.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimuladorCreditoService {
    public SimulacaoResponse simular(BigDecimal valor, Integer prazoMeses, BigDecimal taxaJurosAoMes) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0 || prazoMeses == null || prazoMeses <= 0 || taxaJurosAoMes == null || taxaJurosAoMes.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor, prazo e taxa devem ser positivos");
        }
        BigDecimal taxa = taxaJurosAoMes.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
        BigDecimal pmt = calcularParcela(valor, taxa, prazoMeses);
        List<ParcelaSimulada> parcelas = new ArrayList<>();
        BigDecimal saldoDevedor = valor;
        BigDecimal totalJuros = BigDecimal.ZERO;
        for (int i = 1; i <= prazoMeses; i++) {
            BigDecimal juros = saldoDevedor.multiply(taxa).setScale(2, RoundingMode.HALF_UP);
            BigDecimal amortizacao = pmt.subtract(juros).setScale(2, RoundingMode.HALF_UP);
            if (i == prazoMeses) {
                amortizacao = saldoDevedor;
                pmt = amortizacao.add(juros);
            }
            saldoDevedor = saldoDevedor.subtract(amortizacao).setScale(2, RoundingMode.HALF_UP);
            if (saldoDevedor.compareTo(BigDecimal.ZERO) < 0) saldoDevedor = BigDecimal.ZERO;
            totalJuros = totalJuros.add(juros);
            parcelas.add(new ParcelaSimulada(i, pmt, juros, amortizacao, saldoDevedor));
        }
        BigDecimal valorTotal = valor.add(totalJuros).setScale(2, RoundingMode.HALF_UP);
        BigDecimal cetAproximado = taxaJurosAoMes.multiply(BigDecimal.valueOf(12)).setScale(4, RoundingMode.HALF_UP);
        return new SimulacaoResponse(parcelas, valorTotal, totalJuros, cetAproximado);
    }

    private BigDecimal calcularParcela(BigDecimal pv, BigDecimal taxa, int n) {
        if (taxa.compareTo(BigDecimal.ZERO) == 0) {
            return pv.divide(BigDecimal.valueOf(n), 2, RoundingMode.HALF_UP);
        }
        BigDecimal fator = BigDecimal.ONE.add(taxa).pow(n);
        return pv.multiply(taxa).multiply(fator).divide(fator.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
    }


    public static class SimulacaoResponse {
        private final List<ParcelaSimulada> parcelas;
        private final BigDecimal valorTotal;
        private final BigDecimal totalJuros;
        private final BigDecimal cetAproximadoAnual;

        @java.lang.SuppressWarnings("all")
        public SimulacaoResponse(final List<ParcelaSimulada> parcelas, final BigDecimal valorTotal, final BigDecimal totalJuros, final BigDecimal cetAproximadoAnual) {
            this.parcelas = parcelas;
            this.valorTotal = valorTotal;
            this.totalJuros = totalJuros;
            this.cetAproximadoAnual = cetAproximadoAnual;
        }

        @java.lang.SuppressWarnings("all")
        public List<ParcelaSimulada> getParcelas() {
            return this.parcelas;
        }

        @java.lang.SuppressWarnings("all")
        public BigDecimal getValorTotal() {
            return this.valorTotal;
        }

        @java.lang.SuppressWarnings("all")
        public BigDecimal getTotalJuros() {
            return this.totalJuros;
        }

        @java.lang.SuppressWarnings("all")
        public BigDecimal getCetAproximadoAnual() {
            return this.cetAproximadoAnual;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof SimuladorCreditoService.SimulacaoResponse)) return false;
            final SimuladorCreditoService.SimulacaoResponse other = (SimuladorCreditoService.SimulacaoResponse) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$parcelas = this.getParcelas();
            final java.lang.Object other$parcelas = other.getParcelas();
            if (this$parcelas == null ? other$parcelas != null : !this$parcelas.equals(other$parcelas)) return false;
            final java.lang.Object this$valorTotal = this.getValorTotal();
            final java.lang.Object other$valorTotal = other.getValorTotal();
            if (this$valorTotal == null ? other$valorTotal != null : !this$valorTotal.equals(other$valorTotal)) return false;
            final java.lang.Object this$totalJuros = this.getTotalJuros();
            final java.lang.Object other$totalJuros = other.getTotalJuros();
            if (this$totalJuros == null ? other$totalJuros != null : !this$totalJuros.equals(other$totalJuros)) return false;
            final java.lang.Object this$cetAproximadoAnual = this.getCetAproximadoAnual();
            final java.lang.Object other$cetAproximadoAnual = other.getCetAproximadoAnual();
            if (this$cetAproximadoAnual == null ? other$cetAproximadoAnual != null : !this$cetAproximadoAnual.equals(other$cetAproximadoAnual)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof SimuladorCreditoService.SimulacaoResponse;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $parcelas = this.getParcelas();
            result = result * PRIME + ($parcelas == null ? 43 : $parcelas.hashCode());
            final java.lang.Object $valorTotal = this.getValorTotal();
            result = result * PRIME + ($valorTotal == null ? 43 : $valorTotal.hashCode());
            final java.lang.Object $totalJuros = this.getTotalJuros();
            result = result * PRIME + ($totalJuros == null ? 43 : $totalJuros.hashCode());
            final java.lang.Object $cetAproximadoAnual = this.getCetAproximadoAnual();
            result = result * PRIME + ($cetAproximadoAnual == null ? 43 : $cetAproximadoAnual.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SimuladorCreditoService.SimulacaoResponse(parcelas=" + this.getParcelas() + ", valorTotal=" + this.getValorTotal() + ", totalJuros=" + this.getTotalJuros() + ", cetAproximadoAnual=" + this.getCetAproximadoAnual() + ")";
        }
    }


    public static class ParcelaSimulada {
        private final int numero;
        private final BigDecimal valorParcela;
        private final BigDecimal juros;
        private final BigDecimal amortizacao;
        private final BigDecimal saldoDevedor;

        @java.lang.SuppressWarnings("all")
        public ParcelaSimulada(final int numero, final BigDecimal valorParcela, final BigDecimal juros, final BigDecimal amortizacao, final BigDecimal saldoDevedor) {
            this.numero = numero;
            this.valorParcela = valorParcela;
            this.juros = juros;
            this.amortizacao = amortizacao;
            this.saldoDevedor = saldoDevedor;
        }

        @java.lang.SuppressWarnings("all")
        public int getNumero() {
            return this.numero;
        }

        @java.lang.SuppressWarnings("all")
        public BigDecimal getValorParcela() {
            return this.valorParcela;
        }

        @java.lang.SuppressWarnings("all")
        public BigDecimal getJuros() {
            return this.juros;
        }

        @java.lang.SuppressWarnings("all")
        public BigDecimal getAmortizacao() {
            return this.amortizacao;
        }

        @java.lang.SuppressWarnings("all")
        public BigDecimal getSaldoDevedor() {
            return this.saldoDevedor;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof SimuladorCreditoService.ParcelaSimulada)) return false;
            final SimuladorCreditoService.ParcelaSimulada other = (SimuladorCreditoService.ParcelaSimulada) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            if (this.getNumero() != other.getNumero()) return false;
            final java.lang.Object this$valorParcela = this.getValorParcela();
            final java.lang.Object other$valorParcela = other.getValorParcela();
            if (this$valorParcela == null ? other$valorParcela != null : !this$valorParcela.equals(other$valorParcela)) return false;
            final java.lang.Object this$juros = this.getJuros();
            final java.lang.Object other$juros = other.getJuros();
            if (this$juros == null ? other$juros != null : !this$juros.equals(other$juros)) return false;
            final java.lang.Object this$amortizacao = this.getAmortizacao();
            final java.lang.Object other$amortizacao = other.getAmortizacao();
            if (this$amortizacao == null ? other$amortizacao != null : !this$amortizacao.equals(other$amortizacao)) return false;
            final java.lang.Object this$saldoDevedor = this.getSaldoDevedor();
            final java.lang.Object other$saldoDevedor = other.getSaldoDevedor();
            if (this$saldoDevedor == null ? other$saldoDevedor != null : !this$saldoDevedor.equals(other$saldoDevedor)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof SimuladorCreditoService.ParcelaSimulada;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            result = result * PRIME + this.getNumero();
            final java.lang.Object $valorParcela = this.getValorParcela();
            result = result * PRIME + ($valorParcela == null ? 43 : $valorParcela.hashCode());
            final java.lang.Object $juros = this.getJuros();
            result = result * PRIME + ($juros == null ? 43 : $juros.hashCode());
            final java.lang.Object $amortizacao = this.getAmortizacao();
            result = result * PRIME + ($amortizacao == null ? 43 : $amortizacao.hashCode());
            final java.lang.Object $saldoDevedor = this.getSaldoDevedor();
            result = result * PRIME + ($saldoDevedor == null ? 43 : $saldoDevedor.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SimuladorCreditoService.ParcelaSimulada(numero=" + this.getNumero() + ", valorParcela=" + this.getValorParcela() + ", juros=" + this.getJuros() + ", amortizacao=" + this.getAmortizacao() + ", saldoDevedor=" + this.getSaldoDevedor() + ")";
        }
    }
}
