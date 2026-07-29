package com.aurix.platform.cambio.service;

import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class BacenJudReportGenerator {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BacenJudReportGenerator.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("ddMMyyyy");
    private static final String SEP = ";";

    public String gerar(LocalDate dataReferencia, String cnpj, List<BloqueioJudicial> bloqueios) {
        StringBuilder sb = new StringBuilder();
        sb.append("00").append(SEP).append("BACENJUD").append(SEP).append("1.0").append(SEP).append(cnpj).append(SEP).append(dataReferencia.format(DATE_FORMAT)).append("\n");
        for (BloqueioJudicial b : bloqueios) {
            sb.append("01").append(SEP).append(b.getNumeroProcesso()).append(SEP).append(b.getCpfCnpjAfetado()).append(SEP).append(b.getValor()).append(SEP).append(b.getDataBloqueio()).append("\n");
        }
        sb.append("99").append(SEP).append(bloqueios.size()).append(SEP).append("FIM").append("\n");
        return sb.toString();
    }

    public List<BloqueioJudicial> obterBloqueiosPadrao() {
        return new ArrayList<>();
    }


    public static class BloqueioJudicial {
        private String numeroProcesso;
        private String cpfCnpjAfetado;
        private String valor;
        private String dataBloqueio;

        @java.lang.SuppressWarnings("all")
        public String getNumeroProcesso() {
            return this.numeroProcesso;
        }

        @java.lang.SuppressWarnings("all")
        public String getCpfCnpjAfetado() {
            return this.cpfCnpjAfetado;
        }

        @java.lang.SuppressWarnings("all")
        public String getValor() {
            return this.valor;
        }

        @java.lang.SuppressWarnings("all")
        public String getDataBloqueio() {
            return this.dataBloqueio;
        }

        @java.lang.SuppressWarnings("all")
        public void setNumeroProcesso(final String numeroProcesso) {
            this.numeroProcesso = numeroProcesso;
        }

        @java.lang.SuppressWarnings("all")
        public void setCpfCnpjAfetado(final String cpfCnpjAfetado) {
            this.cpfCnpjAfetado = cpfCnpjAfetado;
        }

        @java.lang.SuppressWarnings("all")
        public void setValor(final String valor) {
            this.valor = valor;
        }

        @java.lang.SuppressWarnings("all")
        public void setDataBloqueio(final String dataBloqueio) {
            this.dataBloqueio = dataBloqueio;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof BacenJudReportGenerator.BloqueioJudicial)) return false;
            final BacenJudReportGenerator.BloqueioJudicial other = (BacenJudReportGenerator.BloqueioJudicial) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$numeroProcesso = this.getNumeroProcesso();
            final java.lang.Object other$numeroProcesso = other.getNumeroProcesso();
            if (this$numeroProcesso == null ? other$numeroProcesso != null : !this$numeroProcesso.equals(other$numeroProcesso)) return false;
            final java.lang.Object this$cpfCnpjAfetado = this.getCpfCnpjAfetado();
            final java.lang.Object other$cpfCnpjAfetado = other.getCpfCnpjAfetado();
            if (this$cpfCnpjAfetado == null ? other$cpfCnpjAfetado != null : !this$cpfCnpjAfetado.equals(other$cpfCnpjAfetado)) return false;
            final java.lang.Object this$valor = this.getValor();
            final java.lang.Object other$valor = other.getValor();
            if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
            final java.lang.Object this$dataBloqueio = this.getDataBloqueio();
            final java.lang.Object other$dataBloqueio = other.getDataBloqueio();
            if (this$dataBloqueio == null ? other$dataBloqueio != null : !this$dataBloqueio.equals(other$dataBloqueio)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof BacenJudReportGenerator.BloqueioJudicial;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $numeroProcesso = this.getNumeroProcesso();
            result = result * PRIME + ($numeroProcesso == null ? 43 : $numeroProcesso.hashCode());
            final java.lang.Object $cpfCnpjAfetado = this.getCpfCnpjAfetado();
            result = result * PRIME + ($cpfCnpjAfetado == null ? 43 : $cpfCnpjAfetado.hashCode());
            final java.lang.Object $valor = this.getValor();
            result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
            final java.lang.Object $dataBloqueio = this.getDataBloqueio();
            result = result * PRIME + ($dataBloqueio == null ? 43 : $dataBloqueio.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "BacenJudReportGenerator.BloqueioJudicial(numeroProcesso=" + this.getNumeroProcesso() + ", cpfCnpjAfetado=" + this.getCpfCnpjAfetado() + ", valor=" + this.getValor() + ", dataBloqueio=" + this.getDataBloqueio() + ")";
        }

        @java.lang.SuppressWarnings("all")
        public BloqueioJudicial(final String numeroProcesso, final String cpfCnpjAfetado, final String valor, final String dataBloqueio) {
            this.numeroProcesso = numeroProcesso;
            this.cpfCnpjAfetado = cpfCnpjAfetado;
            this.valor = valor;
            this.dataBloqueio = dataBloqueio;
        }
    }
}
