package com.aurix.platform.cambio.service;

import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@SuppressWarnings({"PMD.AvoidUsingHardCodedIP"})
public class EFinanceiraReportGenerator {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EFinanceiraReportGenerator.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String SEP = "|";

    public String gerar(LocalDate dataReferencia, String cnpj, List<LinhaEFinanceira> linhas) {
        StringBuilder sb = new StringBuilder();
        sb.append("00").append(SEP).append("EFINANCEIRA").append(SEP).append("1.0").append(SEP).append(cnpj).append(SEP).append(dataReferencia.format(DATE_FORMAT)).append("\n");
        for (LinhaEFinanceira linha : linhas) {
            sb.append("01").append(SEP).append(linha.getTipo()).append(SEP).append(linha.getIdentificador()).append(SEP).append(linha.getValor()).append(SEP).append(linha.getDataOcorrencia()).append("\n");
        }
        sb.append("99").append(SEP).append(linhas.size()).append(SEP).append("FIM").append("\n");
        return sb.toString();
    }

    public List<LinhaEFinanceira> obterLinhasPadrao() {
        List<LinhaEFinanceira> linhas = new ArrayList<>();
        linhas.add(new LinhaEFinanceira("CONTA", "1.1.01.001", "0,00", LocalDate.now().toString()));
        linhas.add(new LinhaEFinanceira("MOVIMENTO", "TXN-001", "100,00", LocalDate.now().toString()));
        return linhas;
    }


    public static class LinhaEFinanceira {
        private String tipo;
        private String identificador;
        private String valor;
        private String dataOcorrencia;

        @java.lang.SuppressWarnings("all")
        public String getTipo() {
            return this.tipo;
        }

        @java.lang.SuppressWarnings("all")
        public String getIdentificador() {
            return this.identificador;
        }

        @java.lang.SuppressWarnings("all")
        public String getValor() {
            return this.valor;
        }

        @java.lang.SuppressWarnings("all")
        public String getDataOcorrencia() {
            return this.dataOcorrencia;
        }

        @java.lang.SuppressWarnings("all")
        public void setTipo(final String tipo) {
            this.tipo = tipo;
        }

        @java.lang.SuppressWarnings("all")
        public void setIdentificador(final String identificador) {
            this.identificador = identificador;
        }

        @java.lang.SuppressWarnings("all")
        public void setValor(final String valor) {
            this.valor = valor;
        }

        @java.lang.SuppressWarnings("all")
        public void setDataOcorrencia(final String dataOcorrencia) {
            this.dataOcorrencia = dataOcorrencia;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof EFinanceiraReportGenerator.LinhaEFinanceira)) return false;
            final EFinanceiraReportGenerator.LinhaEFinanceira other = (EFinanceiraReportGenerator.LinhaEFinanceira) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$tipo = this.getTipo();
            final java.lang.Object other$tipo = other.getTipo();
            if (this$tipo == null ? other$tipo != null : !this$tipo.equals(other$tipo)) return false;
            final java.lang.Object this$identificador = this.getIdentificador();
            final java.lang.Object other$identificador = other.getIdentificador();
            if (this$identificador == null ? other$identificador != null : !this$identificador.equals(other$identificador)) return false;
            final java.lang.Object this$valor = this.getValor();
            final java.lang.Object other$valor = other.getValor();
            if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
            final java.lang.Object this$dataOcorrencia = this.getDataOcorrencia();
            final java.lang.Object other$dataOcorrencia = other.getDataOcorrencia();
            if (this$dataOcorrencia == null ? other$dataOcorrencia != null : !this$dataOcorrencia.equals(other$dataOcorrencia)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof EFinanceiraReportGenerator.LinhaEFinanceira;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $tipo = this.getTipo();
            result = result * PRIME + ($tipo == null ? 43 : $tipo.hashCode());
            final java.lang.Object $identificador = this.getIdentificador();
            result = result * PRIME + ($identificador == null ? 43 : $identificador.hashCode());
            final java.lang.Object $valor = this.getValor();
            result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
            final java.lang.Object $dataOcorrencia = this.getDataOcorrencia();
            result = result * PRIME + ($dataOcorrencia == null ? 43 : $dataOcorrencia.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "EFinanceiraReportGenerator.LinhaEFinanceira(tipo=" + this.getTipo() + ", identificador=" + this.getIdentificador() + ", valor=" + this.getValor() + ", dataOcorrencia=" + this.getDataOcorrencia() + ")";
        }

        @java.lang.SuppressWarnings("all")
        public LinhaEFinanceira(final String tipo, final String identificador, final String valor, final String dataOcorrencia) {
            this.tipo = tipo;
            this.identificador = identificador;
            this.valor = valor;
            this.dataOcorrencia = dataOcorrencia;
        }
    }
}
