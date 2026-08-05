package com.aurix.platform.payments.pix.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class QrPixService {
    private static final String PREFIX_COBRANCA = "Aurix.PIX.COB.";
    private static final String PREFIX_TRANSF = "Aurix.PIX.TR.";
    private static final Pattern PATTERN_DECODE = Pattern.compile("Aurix\\.PIX\\.(COB|TR)\\.(.*)");


    public static class QrPixPayload {
        private String tipo;
        private String chave;
        private BigDecimal valor;
        private String descricao;
        private String nomeRecebedor;
        private String payloadBruto;

        @java.lang.SuppressWarnings("all")
        QrPixPayload(final String tipo, final String chave, final BigDecimal valor, final String descricao, final String nomeRecebedor, final String payloadBruto) {
            this.tipo = tipo;
            this.chave = chave;
            this.valor = valor;
            this.descricao = descricao;
            this.nomeRecebedor = nomeRecebedor;
            this.payloadBruto = payloadBruto;
        }


        @java.lang.SuppressWarnings("all")
        public static class QrPixPayloadBuilder {
            @java.lang.SuppressWarnings("all")
            private String tipo;
            @java.lang.SuppressWarnings("all")
            private String chave;
            @java.lang.SuppressWarnings("all")
            private BigDecimal valor;
            @java.lang.SuppressWarnings("all")
            private String descricao;
            @java.lang.SuppressWarnings("all")
            private String nomeRecebedor;
            @java.lang.SuppressWarnings("all")
            private String payloadBruto;

            @java.lang.SuppressWarnings("all")
            QrPixPayloadBuilder() {
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public QrPixService.QrPixPayload.QrPixPayloadBuilder tipo(final String tipo) {
                this.tipo = tipo;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public QrPixService.QrPixPayload.QrPixPayloadBuilder chave(final String chave) {
                this.chave = chave;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public QrPixService.QrPixPayload.QrPixPayloadBuilder valor(final BigDecimal valor) {
                this.valor = valor;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public QrPixService.QrPixPayload.QrPixPayloadBuilder descricao(final String descricao) {
                this.descricao = descricao;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public QrPixService.QrPixPayload.QrPixPayloadBuilder nomeRecebedor(final String nomeRecebedor) {
                this.nomeRecebedor = nomeRecebedor;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public QrPixService.QrPixPayload.QrPixPayloadBuilder payloadBruto(final String payloadBruto) {
                this.payloadBruto = payloadBruto;
                return this;
            }

            @java.lang.SuppressWarnings("all")
            public QrPixService.QrPixPayload build() {
                return new QrPixService.QrPixPayload(this.tipo, this.chave, this.valor, this.descricao, this.nomeRecebedor, this.payloadBruto);
            }

            @java.lang.Override
            @java.lang.SuppressWarnings("all")
            public java.lang.String toString() {
                return "QrPixService.QrPixPayload.QrPixPayloadBuilder(tipo=" + this.tipo + ", chave=" + this.chave + ", valor=" + this.valor + ", descricao=" + this.descricao + ", nomeRecebedor=" + this.nomeRecebedor + ", payloadBruto=" + this.payloadBruto + ")";
            }
        }

        @java.lang.SuppressWarnings("all")
        public static QrPixService.QrPixPayload.QrPixPayloadBuilder builder() {
            return new QrPixService.QrPixPayload.QrPixPayloadBuilder();
        }

        @java.lang.SuppressWarnings("all")
        public String getTipo() {
            return this.tipo;
        }

        @java.lang.SuppressWarnings("all")
        public String getChave() {
            return this.chave;
        }

        @java.lang.SuppressWarnings("all")
        public BigDecimal getValor() {
            return this.valor;
        }

        @java.lang.SuppressWarnings("all")
        public String getDescricao() {
            return this.descricao;
        }

        @java.lang.SuppressWarnings("all")
        public String getNomeRecebedor() {
            return this.nomeRecebedor;
        }

        @java.lang.SuppressWarnings("all")
        public String getPayloadBruto() {
            return this.payloadBruto;
        }

        @java.lang.SuppressWarnings("all")
        public void setTipo(final String tipo) {
            this.tipo = tipo;
        }

        @java.lang.SuppressWarnings("all")
        public void setChave(final String chave) {
            this.chave = chave;
        }

        @java.lang.SuppressWarnings("all")
        public void setValor(final BigDecimal valor) {
            this.valor = valor;
        }

        @java.lang.SuppressWarnings("all")
        public void setDescricao(final String descricao) {
            this.descricao = descricao;
        }

        @java.lang.SuppressWarnings("all")
        public void setNomeRecebedor(final String nomeRecebedor) {
            this.nomeRecebedor = nomeRecebedor;
        }

        @java.lang.SuppressWarnings("all")
        public void setPayloadBruto(final String payloadBruto) {
            this.payloadBruto = payloadBruto;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof QrPixService.QrPixPayload)) return false;
            final QrPixService.QrPixPayload other = (QrPixService.QrPixPayload) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$tipo = this.getTipo();
            final java.lang.Object other$tipo = other.getTipo();
            if (this$tipo == null ? other$tipo != null : !this$tipo.equals(other$tipo)) return false;
            final java.lang.Object this$chave = this.getChave();
            final java.lang.Object other$chave = other.getChave();
            if (this$chave == null ? other$chave != null : !this$chave.equals(other$chave)) return false;
            final java.lang.Object this$valor = this.getValor();
            final java.lang.Object other$valor = other.getValor();
            if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
            final java.lang.Object this$descricao = this.getDescricao();
            final java.lang.Object other$descricao = other.getDescricao();
            if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
            final java.lang.Object this$nomeRecebedor = this.getNomeRecebedor();
            final java.lang.Object other$nomeRecebedor = other.getNomeRecebedor();
            if (this$nomeRecebedor == null ? other$nomeRecebedor != null : !this$nomeRecebedor.equals(other$nomeRecebedor)) return false;
            final java.lang.Object this$payloadBruto = this.getPayloadBruto();
            final java.lang.Object other$payloadBruto = other.getPayloadBruto();
            if (this$payloadBruto == null ? other$payloadBruto != null : !this$payloadBruto.equals(other$payloadBruto)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof QrPixService.QrPixPayload;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $tipo = this.getTipo();
            result = result * PRIME + ($tipo == null ? 43 : $tipo.hashCode());
            final java.lang.Object $chave = this.getChave();
            result = result * PRIME + ($chave == null ? 43 : $chave.hashCode());
            final java.lang.Object $valor = this.getValor();
            result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
            final java.lang.Object $descricao = this.getDescricao();
            result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
            final java.lang.Object $nomeRecebedor = this.getNomeRecebedor();
            result = result * PRIME + ($nomeRecebedor == null ? 43 : $nomeRecebedor.hashCode());
            final java.lang.Object $payloadBruto = this.getPayloadBruto();
            result = result * PRIME + ($payloadBruto == null ? 43 : $payloadBruto.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "QrPixService.QrPixPayload(tipo=" + this.getTipo() + ", chave=" + this.getChave() + ", valor=" + this.getValor() + ", descricao=" + this.getDescricao() + ", nomeRecebedor=" + this.getNomeRecebedor() + ", payloadBruto=" + this.getPayloadBruto() + ")";
        }
    }


    public static class QrPixDecodificado {
        private String tipo;
        private String chave;
        private BigDecimal valor;
        private String descricao;
        private String nomeRecebedor;

        @java.lang.SuppressWarnings("all")
        QrPixDecodificado(final String tipo, final String chave, final BigDecimal valor, final String descricao, final String nomeRecebedor) {
            this.tipo = tipo;
            this.chave = chave;
            this.valor = valor;
            this.descricao = descricao;
            this.nomeRecebedor = nomeRecebedor;
        }


        @java.lang.SuppressWarnings("all")
        public static class QrPixDecodificadoBuilder {
            @java.lang.SuppressWarnings("all")
            private String tipo;
            @java.lang.SuppressWarnings("all")
            private String chave;
            @java.lang.SuppressWarnings("all")
            private BigDecimal valor;
            @java.lang.SuppressWarnings("all")
            private String descricao;
            @java.lang.SuppressWarnings("all")
            private String nomeRecebedor;

            @java.lang.SuppressWarnings("all")
            QrPixDecodificadoBuilder() {
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public QrPixService.QrPixDecodificado.QrPixDecodificadoBuilder tipo(final String tipo) {
                this.tipo = tipo;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public QrPixService.QrPixDecodificado.QrPixDecodificadoBuilder chave(final String chave) {
                this.chave = chave;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public QrPixService.QrPixDecodificado.QrPixDecodificadoBuilder valor(final BigDecimal valor) {
                this.valor = valor;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public QrPixService.QrPixDecodificado.QrPixDecodificadoBuilder descricao(final String descricao) {
                this.descricao = descricao;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public QrPixService.QrPixDecodificado.QrPixDecodificadoBuilder nomeRecebedor(final String nomeRecebedor) {
                this.nomeRecebedor = nomeRecebedor;
                return this;
            }

            @java.lang.SuppressWarnings("all")
            public QrPixService.QrPixDecodificado build() {
                return new QrPixService.QrPixDecodificado(this.tipo, this.chave, this.valor, this.descricao, this.nomeRecebedor);
            }

            @java.lang.Override
            @java.lang.SuppressWarnings("all")
            public java.lang.String toString() {
                return "QrPixService.QrPixDecodificado.QrPixDecodificadoBuilder(tipo=" + this.tipo + ", chave=" + this.chave + ", valor=" + this.valor + ", descricao=" + this.descricao + ", nomeRecebedor=" + this.nomeRecebedor + ")";
            }
        }

        @java.lang.SuppressWarnings("all")
        public static QrPixService.QrPixDecodificado.QrPixDecodificadoBuilder builder() {
            return new QrPixService.QrPixDecodificado.QrPixDecodificadoBuilder();
        }

        @java.lang.SuppressWarnings("all")
        public String getTipo() {
            return this.tipo;
        }

        @java.lang.SuppressWarnings("all")
        public String getChave() {
            return this.chave;
        }

        @java.lang.SuppressWarnings("all")
        public BigDecimal getValor() {
            return this.valor;
        }

        @java.lang.SuppressWarnings("all")
        public String getDescricao() {
            return this.descricao;
        }

        @java.lang.SuppressWarnings("all")
        public String getNomeRecebedor() {
            return this.nomeRecebedor;
        }

        @java.lang.SuppressWarnings("all")
        public void setTipo(final String tipo) {
            this.tipo = tipo;
        }

        @java.lang.SuppressWarnings("all")
        public void setChave(final String chave) {
            this.chave = chave;
        }

        @java.lang.SuppressWarnings("all")
        public void setValor(final BigDecimal valor) {
            this.valor = valor;
        }

        @java.lang.SuppressWarnings("all")
        public void setDescricao(final String descricao) {
            this.descricao = descricao;
        }

        @java.lang.SuppressWarnings("all")
        public void setNomeRecebedor(final String nomeRecebedor) {
            this.nomeRecebedor = nomeRecebedor;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof QrPixService.QrPixDecodificado)) return false;
            final QrPixService.QrPixDecodificado other = (QrPixService.QrPixDecodificado) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$tipo = this.getTipo();
            final java.lang.Object other$tipo = other.getTipo();
            if (this$tipo == null ? other$tipo != null : !this$tipo.equals(other$tipo)) return false;
            final java.lang.Object this$chave = this.getChave();
            final java.lang.Object other$chave = other.getChave();
            if (this$chave == null ? other$chave != null : !this$chave.equals(other$chave)) return false;
            final java.lang.Object this$valor = this.getValor();
            final java.lang.Object other$valor = other.getValor();
            if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
            final java.lang.Object this$descricao = this.getDescricao();
            final java.lang.Object other$descricao = other.getDescricao();
            if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
            final java.lang.Object this$nomeRecebedor = this.getNomeRecebedor();
            final java.lang.Object other$nomeRecebedor = other.getNomeRecebedor();
            if (this$nomeRecebedor == null ? other$nomeRecebedor != null : !this$nomeRecebedor.equals(other$nomeRecebedor)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof QrPixService.QrPixDecodificado;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $tipo = this.getTipo();
            result = result * PRIME + ($tipo == null ? 43 : $tipo.hashCode());
            final java.lang.Object $chave = this.getChave();
            result = result * PRIME + ($chave == null ? 43 : $chave.hashCode());
            final java.lang.Object $valor = this.getValor();
            result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
            final java.lang.Object $descricao = this.getDescricao();
            result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
            final java.lang.Object $nomeRecebedor = this.getNomeRecebedor();
            result = result * PRIME + ($nomeRecebedor == null ? 43 : $nomeRecebedor.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "QrPixService.QrPixDecodificado(tipo=" + this.getTipo() + ", chave=" + this.getChave() + ", valor=" + this.getValor() + ", descricao=" + this.getDescricao() + ", nomeRecebedor=" + this.getNomeRecebedor() + ")";
        }
    }

    public QrPixPayload gerarCobranca(String chaveRecebedor, BigDecimal valor, String descricao, String nomeRecebedor) {
        StringBuilder sb = new StringBuilder(PREFIX_COBRANCA);
        sb.append("CH=").append(sanitize(chaveRecebedor)).append("|");
        sb.append("V=").append(valor != null ? valor.toPlainString() : "0").append("|");
        sb.append("D=").append(sanitize(descricao)).append("|");
        sb.append("N=").append(sanitize(nomeRecebedor)).append("|");
        String payload = sb.toString();
        return QrPixPayload.builder().tipo("COBRANCA").chave(chaveRecebedor).valor(valor != null ? valor : BigDecimal.ZERO).descricao(descricao).nomeRecebedor(nomeRecebedor).payloadBruto(payload).build();
    }

    public QrPixPayload gerarTransferencia(String chaveDestino, BigDecimal valor, String descricao) {
        StringBuilder sb = new StringBuilder(PREFIX_TRANSF);
        sb.append("CH=").append(sanitize(chaveDestino)).append("|");
        sb.append("V=").append(valor != null ? valor.toPlainString() : "0").append("|");
        sb.append("D=").append(sanitize(descricao)).append("|");
        String payload = sb.toString();
        return QrPixPayload.builder().tipo("TRANSFERENCIA").chave(chaveDestino).valor(valor != null ? valor : BigDecimal.ZERO).descricao(descricao).payloadBruto(payload).build();
    }

    public QrPixDecodificado decodificar(String payload) {
        if (payload == null || payload.isBlank()) throw new IllegalArgumentException("Payload vazio");
        String trimmed = payload.trim();
        Matcher m = PATTERN_DECODE.matcher(trimmed);
        if (!m.find()) throw new IllegalArgumentException("Payload PIX nao reconhecido");
        String tipo = "COB".equals(m.group(1)) ? "COBRANCA" : "TRANSFERENCIA";
        String rest = m.group(2);
        String chave = null;
        String descricao = null;
        String nomeRecebedor = null;
        BigDecimal valor = BigDecimal.ZERO;
        for (String part : rest.split("\\|")) {
            if (part.startsWith("CH=")) chave = part.substring(3);
             else if (part.startsWith("V=")) valor = new BigDecimal(part.substring(2));
             else if (part.startsWith("D=")) descricao = part.substring(2);
             else if (part.startsWith("N=")) nomeRecebedor = part.substring(2);
        }
        return QrPixDecodificado.builder().tipo(tipo).chave(chave).valor(valor).descricao(descricao).nomeRecebedor(nomeRecebedor).build();
    }

    private static String sanitize(String s) {
        if (s == null) return "";
        return s.replace("|", "_").replace("\\", "_");
    }
}
