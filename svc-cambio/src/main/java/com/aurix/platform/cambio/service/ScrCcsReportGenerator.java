package com.aurix.platform.cambio.service;

import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScrCcsReportGenerator {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ScrCcsReportGenerator.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("ddMMyyyy");
    private static final String SEP = ";";

    public String gerar(LocalDate dataReferencia, String cnpj, List<RegistroScr> registros) {
        StringBuilder sb = new StringBuilder();
        sb.append("00").append(SEP).append("SCR").append(SEP).append("1.0").append(SEP).append(cnpj).append(SEP).append(dataReferencia.format(DATE_FORMAT)).append("\n");
        for (RegistroScr r : registros) {
            sb.append("01").append(SEP).append(r.getTipoOperacao()).append(SEP).append(r.getCpfCnpjDevedor()).append(SEP).append(r.getValor()).append(SEP).append(r.getDataOperacao()).append("\n");
        }
        sb.append("99").append(SEP).append(registros.size()).append(SEP).append("FIM").append("\n");
        return sb.toString();
    }

    public List<RegistroScr> obterRegistrosPadrao() {
        List<RegistroScr> list = new ArrayList<>();
        list.add(new RegistroScr("EMPRESTIMO", "12345678901", "0,00", LocalDate.now().format(DATE_FORMAT)));
        return list;
    }


    public static class RegistroScr {
        private String tipoOperacao;
        private String cpfCnpjDevedor;
        private String valor;
        private String dataOperacao;

        @java.lang.SuppressWarnings("all")
        public String getTipoOperacao() {
            return this.tipoOperacao;
        }

        @java.lang.SuppressWarnings("all")
        public String getCpfCnpjDevedor() {
            return this.cpfCnpjDevedor;
        }

        @java.lang.SuppressWarnings("all")
        public String getValor() {
            return this.valor;
        }

        @java.lang.SuppressWarnings("all")
        public String getDataOperacao() {
            return this.dataOperacao;
        }

        @java.lang.SuppressWarnings("all")
        public void setTipoOperacao(final String tipoOperacao) {
            this.tipoOperacao = tipoOperacao;
        }

        @java.lang.SuppressWarnings("all")
        public void setCpfCnpjDevedor(final String cpfCnpjDevedor) {
            this.cpfCnpjDevedor = cpfCnpjDevedor;
        }

        @java.lang.SuppressWarnings("all")
        public void setValor(final String valor) {
            this.valor = valor;
        }

        @java.lang.SuppressWarnings("all")
        public void setDataOperacao(final String dataOperacao) {
            this.dataOperacao = dataOperacao;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof ScrCcsReportGenerator.RegistroScr)) return false;
            final ScrCcsReportGenerator.RegistroScr other = (ScrCcsReportGenerator.RegistroScr) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$tipoOperacao = this.getTipoOperacao();
            final java.lang.Object other$tipoOperacao = other.getTipoOperacao();
            if (this$tipoOperacao == null ? other$tipoOperacao != null : !this$tipoOperacao.equals(other$tipoOperacao)) return false;
            final java.lang.Object this$cpfCnpjDevedor = this.getCpfCnpjDevedor();
            final java.lang.Object other$cpfCnpjDevedor = other.getCpfCnpjDevedor();
            if (this$cpfCnpjDevedor == null ? other$cpfCnpjDevedor != null : !this$cpfCnpjDevedor.equals(other$cpfCnpjDevedor)) return false;
            final java.lang.Object this$valor = this.getValor();
            final java.lang.Object other$valor = other.getValor();
            if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
            final java.lang.Object this$dataOperacao = this.getDataOperacao();
            final java.lang.Object other$dataOperacao = other.getDataOperacao();
            if (this$dataOperacao == null ? other$dataOperacao != null : !this$dataOperacao.equals(other$dataOperacao)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof ScrCcsReportGenerator.RegistroScr;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $tipoOperacao = this.getTipoOperacao();
            result = result * PRIME + ($tipoOperacao == null ? 43 : $tipoOperacao.hashCode());
            final java.lang.Object $cpfCnpjDevedor = this.getCpfCnpjDevedor();
            result = result * PRIME + ($cpfCnpjDevedor == null ? 43 : $cpfCnpjDevedor.hashCode());
            final java.lang.Object $valor = this.getValor();
            result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
            final java.lang.Object $dataOperacao = this.getDataOperacao();
            result = result * PRIME + ($dataOperacao == null ? 43 : $dataOperacao.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "ScrCcsReportGenerator.RegistroScr(tipoOperacao=" + this.getTipoOperacao() + ", cpfCnpjDevedor=" + this.getCpfCnpjDevedor() + ", valor=" + this.getValor() + ", dataOperacao=" + this.getDataOperacao() + ")";
        }

        @java.lang.SuppressWarnings("all")
        public RegistroScr(final String tipoOperacao, final String cpfCnpjDevedor, final String valor, final String dataOperacao) {
            this.tipoOperacao = tipoOperacao;
            this.cpfCnpjDevedor = cpfCnpjDevedor;
            this.valor = valor;
            this.dataOperacao = dataOperacao;
        }
    }
}
