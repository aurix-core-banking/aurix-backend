package com.aurix.platform.cambio.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings({"PMD.AvoidUsingHardCodedIP", "PMD.UnusedFormalParameter"})
public class CosifReportGenerator {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CosifReportGenerator.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");
    private static final String SEPARADOR = ";";

    public String gerarRelatorioCOSIF(LocalDate dataReferencia, String cnpjInstituicao, List<ContaCosif> contas) {
        StringBuilder sb = new StringBuilder();
        gerarCabecalho(sb, dataReferencia, cnpjInstituicao);
        gerarLinhasContas(sb, contas, dataReferencia);
        gerarRodape(sb, contas.size(), dataReferencia);
        return sb.toString();
    }

    private void gerarCabecalho(StringBuilder sb, LocalDate dataReferencia, String cnpjInstituicao) {
        sb.append("00").append(SEPARADOR);
        sb.append(dataReferencia.format(DATE_FORMATTER)).append(SEPARADOR);
        sb.append("COSIF").append(SEPARADOR);
        sb.append("1.0").append(SEPARADOR);
        sb.append(cnpjInstituicao).append(SEPARADOR);
        sb.append("\n");
        sb.append("01").append(SEPARADOR);
        sb.append("CONTA").append(SEPARADOR);
        sb.append("SALDO").append(SEPARADOR);
        sb.append("DATA").append(SEPARADOR);
        sb.append("NATUREZA").append("\n");
    }

    private void gerarLinhasContas(StringBuilder sb, List<ContaCosif> contas, LocalDate dataReferencia) {
        for (ContaCosif conta : contas) {
            sb.append("02").append(SEPARADOR);
            sb.append(conta.getNumeroConta()).append(SEPARADOR);
            sb.append(formatarSaldo(conta.getSaldo())).append(SEPARADOR);
            sb.append(dataReferencia.format(DATE_FORMATTER)).append(SEPARADOR);
            sb.append(conta.getNatureza()).append(SEPARADOR);
            sb.append("\n");
        }
    }

    private void gerarRodape(StringBuilder sb, int totalLinhas, LocalDate dataReferencia) {
        sb.append("99").append(SEPARADOR);
        sb.append(totalLinhas).append(SEPARADOR);
        sb.append(java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"))).append(SEPARADOR);
        sb.append("FIM").append("\n");
    }

    private String formatarSaldo(BigDecimal saldo) {
        if (saldo == null) {
            return "0,00";
        }
        return saldo.toString().replace(".", ",");
    }

    public List<ContaCosif> obterContasCosifPadrao() {
        List<ContaCosif> contas = new ArrayList<>();
        contas.add(new ContaCosif("1.1.01.001", BigDecimal.ZERO, "D"));
        contas.add(new ContaCosif("1.1.01.002", BigDecimal.ZERO, "D"));
        contas.add(new ContaCosif("1.1.02.001", BigDecimal.ZERO, "D"));
        contas.add(new ContaCosif("2.1.01.001", BigDecimal.ZERO, "C"));
        contas.add(new ContaCosif("2.1.02.001", BigDecimal.ZERO, "C"));
        contas.add(new ContaCosif("3.1.01.001", BigDecimal.ZERO, "C"));
        contas.add(new ContaCosif("4.1.01.001", BigDecimal.ZERO, "D"));
        contas.add(new ContaCosif("5.1.01.001", BigDecimal.ZERO, "D"));
        return contas;
    }


    public static class ContaCosif {
        private String numeroConta;
        private BigDecimal saldo;
        private String natureza;

        @java.lang.SuppressWarnings("all")
        public String getNumeroConta() {
            return this.numeroConta;
        }

        @java.lang.SuppressWarnings("all")
        public BigDecimal getSaldo() {
            return this.saldo;
        }

        @java.lang.SuppressWarnings("all")
        public String getNatureza() {
            return this.natureza;
        }

        @java.lang.SuppressWarnings("all")
        public void setNumeroConta(final String numeroConta) {
            this.numeroConta = numeroConta;
        }

        @java.lang.SuppressWarnings("all")
        public void setSaldo(final BigDecimal saldo) {
            this.saldo = saldo;
        }

        @java.lang.SuppressWarnings("all")
        public void setNatureza(final String natureza) {
            this.natureza = natureza;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof CosifReportGenerator.ContaCosif)) return false;
            final CosifReportGenerator.ContaCosif other = (CosifReportGenerator.ContaCosif) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$numeroConta = this.getNumeroConta();
            final java.lang.Object other$numeroConta = other.getNumeroConta();
            if (this$numeroConta == null ? other$numeroConta != null : !this$numeroConta.equals(other$numeroConta)) return false;
            final java.lang.Object this$saldo = this.getSaldo();
            final java.lang.Object other$saldo = other.getSaldo();
            if (this$saldo == null ? other$saldo != null : !this$saldo.equals(other$saldo)) return false;
            final java.lang.Object this$natureza = this.getNatureza();
            final java.lang.Object other$natureza = other.getNatureza();
            if (this$natureza == null ? other$natureza != null : !this$natureza.equals(other$natureza)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof CosifReportGenerator.ContaCosif;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $numeroConta = this.getNumeroConta();
            result = result * PRIME + ($numeroConta == null ? 43 : $numeroConta.hashCode());
            final java.lang.Object $saldo = this.getSaldo();
            result = result * PRIME + ($saldo == null ? 43 : $saldo.hashCode());
            final java.lang.Object $natureza = this.getNatureza();
            result = result * PRIME + ($natureza == null ? 43 : $natureza.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "CosifReportGenerator.ContaCosif(numeroConta=" + this.getNumeroConta() + ", saldo=" + this.getSaldo() + ", natureza=" + this.getNatureza() + ")";
        }

        @java.lang.SuppressWarnings("all")
        public ContaCosif(final String numeroConta, final BigDecimal saldo, final String natureza) {
            this.numeroConta = numeroConta;
            this.saldo = saldo;
            this.natureza = natureza;
        }
    }

    @java.lang.SuppressWarnings("all")
    public CosifReportGenerator() {
    }
}
