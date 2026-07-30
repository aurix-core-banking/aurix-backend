package com.aurix.platform.cambio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aurix.bacen")
public class SpiStrProperties {
    private String ispb = "";
    private String environment = "homologacao";
    private CertConfig certificados = new CertConfig();
    private SpiConfig spi = new SpiConfig();
    private StrConfig str = new StrConfig();


    public static class CertConfig {
        private String keystorePath;
        private String keystorePassword;
        private String truststorePath;
        private String truststorePassword;
        private String keystoreType = "PKCS12";
        private String truststoreType = "JKS";

        @java.lang.SuppressWarnings("all")
        public CertConfig() {
        }

        @java.lang.SuppressWarnings("all")
        public String getKeystorePath() {
            return this.keystorePath;
        }

        @java.lang.SuppressWarnings("all")
        public String getKeystorePassword() {
            return this.keystorePassword;
        }

        @java.lang.SuppressWarnings("all")
        public String getTruststorePath() {
            return this.truststorePath;
        }

        @java.lang.SuppressWarnings("all")
        public String getTruststorePassword() {
            return this.truststorePassword;
        }

        @java.lang.SuppressWarnings("all")
        public String getKeystoreType() {
            return this.keystoreType;
        }

        @java.lang.SuppressWarnings("all")
        public String getTruststoreType() {
            return this.truststoreType;
        }

        @java.lang.SuppressWarnings("all")
        public void setKeystorePath(final String keystorePath) {
            this.keystorePath = keystorePath;
        }

        @java.lang.SuppressWarnings("all")
        public void setKeystorePassword(final String keystorePassword) {
            this.keystorePassword = keystorePassword;
        }

        @java.lang.SuppressWarnings("all")
        public void setTruststorePath(final String truststorePath) {
            this.truststorePath = truststorePath;
        }

        @java.lang.SuppressWarnings("all")
        public void setTruststorePassword(final String truststorePassword) {
            this.truststorePassword = truststorePassword;
        }

        @java.lang.SuppressWarnings("all")
        public void setKeystoreType(final String keystoreType) {
            this.keystoreType = keystoreType;
        }

        @java.lang.SuppressWarnings("all")
        public void setTruststoreType(final String truststoreType) {
            this.truststoreType = truststoreType;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof SpiStrProperties.CertConfig)) return false;
            final SpiStrProperties.CertConfig other = (SpiStrProperties.CertConfig) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$keystorePath = this.getKeystorePath();
            final java.lang.Object other$keystorePath = other.getKeystorePath();
            if (this$keystorePath == null ? other$keystorePath != null : !this$keystorePath.equals(other$keystorePath)) return false;
            final java.lang.Object this$keystorePassword = this.getKeystorePassword();
            final java.lang.Object other$keystorePassword = other.getKeystorePassword();
            if (this$keystorePassword == null ? other$keystorePassword != null : !this$keystorePassword.equals(other$keystorePassword)) return false;
            final java.lang.Object this$truststorePath = this.getTruststorePath();
            final java.lang.Object other$truststorePath = other.getTruststorePath();
            if (this$truststorePath == null ? other$truststorePath != null : !this$truststorePath.equals(other$truststorePath)) return false;
            final java.lang.Object this$truststorePassword = this.getTruststorePassword();
            final java.lang.Object other$truststorePassword = other.getTruststorePassword();
            if (this$truststorePassword == null ? other$truststorePassword != null : !this$truststorePassword.equals(other$truststorePassword)) return false;
            final java.lang.Object this$keystoreType = this.getKeystoreType();
            final java.lang.Object other$keystoreType = other.getKeystoreType();
            if (this$keystoreType == null ? other$keystoreType != null : !this$keystoreType.equals(other$keystoreType)) return false;
            final java.lang.Object this$truststoreType = this.getTruststoreType();
            final java.lang.Object other$truststoreType = other.getTruststoreType();
            if (this$truststoreType == null ? other$truststoreType != null : !this$truststoreType.equals(other$truststoreType)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof SpiStrProperties.CertConfig;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $keystorePath = this.getKeystorePath();
            result = result * PRIME + ($keystorePath == null ? 43 : $keystorePath.hashCode());
            final java.lang.Object $keystorePassword = this.getKeystorePassword();
            result = result * PRIME + ($keystorePassword == null ? 43 : $keystorePassword.hashCode());
            final java.lang.Object $truststorePath = this.getTruststorePath();
            result = result * PRIME + ($truststorePath == null ? 43 : $truststorePath.hashCode());
            final java.lang.Object $truststorePassword = this.getTruststorePassword();
            result = result * PRIME + ($truststorePassword == null ? 43 : $truststorePassword.hashCode());
            final java.lang.Object $keystoreType = this.getKeystoreType();
            result = result * PRIME + ($keystoreType == null ? 43 : $keystoreType.hashCode());
            final java.lang.Object $truststoreType = this.getTruststoreType();
            result = result * PRIME + ($truststoreType == null ? 43 : $truststoreType.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SpiStrProperties.CertConfig(keystorePath=" + this.getKeystorePath() + ", keystorePassword=" + this.getKeystorePassword() + ", truststorePath=" + this.getTruststorePath() + ", truststorePassword=" + this.getTruststorePassword() + ", keystoreType=" + this.getKeystoreType() + ", truststoreType=" + this.getTruststoreType() + ")";
        }
    }


    public static class SpiConfig {
        private boolean enabled = false;
        private String url = "https://spi.bcb.gov.br";
        private int connectTimeoutMs = 10000;
        private int readTimeoutMs = 30000;

        @java.lang.SuppressWarnings("all")
        public SpiConfig() {
        }

        @java.lang.SuppressWarnings("all")
        public boolean isEnabled() {
            return this.enabled;
        }

        @java.lang.SuppressWarnings("all")
        public String getUrl() {
            return this.url;
        }

        @java.lang.SuppressWarnings("all")
        public int getConnectTimeoutMs() {
            return this.connectTimeoutMs;
        }

        @java.lang.SuppressWarnings("all")
        public int getReadTimeoutMs() {
            return this.readTimeoutMs;
        }

        @java.lang.SuppressWarnings("all")
        public void setEnabled(final boolean enabled) {
            this.enabled = enabled;
        }

        @java.lang.SuppressWarnings("all")
        public void setUrl(final String url) {
            this.url = url;
        }

        @java.lang.SuppressWarnings("all")
        public void setConnectTimeoutMs(final int connectTimeoutMs) {
            this.connectTimeoutMs = connectTimeoutMs;
        }

        @java.lang.SuppressWarnings("all")
        public void setReadTimeoutMs(final int readTimeoutMs) {
            this.readTimeoutMs = readTimeoutMs;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof SpiStrProperties.SpiConfig)) return false;
            final SpiStrProperties.SpiConfig other = (SpiStrProperties.SpiConfig) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            if (this.isEnabled() != other.isEnabled()) return false;
            if (this.getConnectTimeoutMs() != other.getConnectTimeoutMs()) return false;
            if (this.getReadTimeoutMs() != other.getReadTimeoutMs()) return false;
            final java.lang.Object this$url = this.getUrl();
            final java.lang.Object other$url = other.getUrl();
            if (this$url == null ? other$url != null : !this$url.equals(other$url)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof SpiStrProperties.SpiConfig;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            result = result * PRIME + (this.isEnabled() ? 79 : 97);
            result = result * PRIME + this.getConnectTimeoutMs();
            result = result * PRIME + this.getReadTimeoutMs();
            final java.lang.Object $url = this.getUrl();
            result = result * PRIME + ($url == null ? 43 : $url.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SpiStrProperties.SpiConfig(enabled=" + this.isEnabled() + ", url=" + this.getUrl() + ", connectTimeoutMs=" + this.getConnectTimeoutMs() + ", readTimeoutMs=" + this.getReadTimeoutMs() + ")";
        }
    }


    public static class StrConfig {
        private boolean enabled = false;
        private String url = "https://str.bcb.gov.br";
        private int connectTimeoutMs = 10000;
        private int readTimeoutMs = 30000;

        @java.lang.SuppressWarnings("all")
        public StrConfig() {
        }

        @java.lang.SuppressWarnings("all")
        public boolean isEnabled() {
            return this.enabled;
        }

        @java.lang.SuppressWarnings("all")
        public String getUrl() {
            return this.url;
        }

        @java.lang.SuppressWarnings("all")
        public int getConnectTimeoutMs() {
            return this.connectTimeoutMs;
        }

        @java.lang.SuppressWarnings("all")
        public int getReadTimeoutMs() {
            return this.readTimeoutMs;
        }

        @java.lang.SuppressWarnings("all")
        public void setEnabled(final boolean enabled) {
            this.enabled = enabled;
        }

        @java.lang.SuppressWarnings("all")
        public void setUrl(final String url) {
            this.url = url;
        }

        @java.lang.SuppressWarnings("all")
        public void setConnectTimeoutMs(final int connectTimeoutMs) {
            this.connectTimeoutMs = connectTimeoutMs;
        }

        @java.lang.SuppressWarnings("all")
        public void setReadTimeoutMs(final int readTimeoutMs) {
            this.readTimeoutMs = readTimeoutMs;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof SpiStrProperties.StrConfig)) return false;
            final SpiStrProperties.StrConfig other = (SpiStrProperties.StrConfig) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            if (this.isEnabled() != other.isEnabled()) return false;
            if (this.getConnectTimeoutMs() != other.getConnectTimeoutMs()) return false;
            if (this.getReadTimeoutMs() != other.getReadTimeoutMs()) return false;
            final java.lang.Object this$url = this.getUrl();
            final java.lang.Object other$url = other.getUrl();
            if (this$url == null ? other$url != null : !this$url.equals(other$url)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof SpiStrProperties.StrConfig;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            result = result * PRIME + (this.isEnabled() ? 79 : 97);
            result = result * PRIME + this.getConnectTimeoutMs();
            result = result * PRIME + this.getReadTimeoutMs();
            final java.lang.Object $url = this.getUrl();
            result = result * PRIME + ($url == null ? 43 : $url.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SpiStrProperties.StrConfig(enabled=" + this.isEnabled() + ", url=" + this.getUrl() + ", connectTimeoutMs=" + this.getConnectTimeoutMs() + ", readTimeoutMs=" + this.getReadTimeoutMs() + ")";
        }
    }

    public boolean isHomologacao() {
        return "homologacao".equalsIgnoreCase(environment);
    }

    public boolean isProducao() {
        return "producao".equalsIgnoreCase(environment);
    }

    public boolean isCertificadoConfigurado() {
        return certificados != null && certificados.getKeystorePath() != null && !certificados.getKeystorePath().isBlank();
    }

    @java.lang.SuppressWarnings("all")
    public SpiStrProperties() {
    }

    @java.lang.SuppressWarnings("all")
    public String getIspb() {
        return this.ispb;
    }

    @java.lang.SuppressWarnings("all")
    public String getEnvironment() {
        return this.environment;
    }

    @java.lang.SuppressWarnings("all")
    public CertConfig getCertificados() {
        return this.certificados;
    }

    @java.lang.SuppressWarnings("all")
    public SpiConfig getSpi() {
        return this.spi;
    }

    @java.lang.SuppressWarnings("all")
    public StrConfig getStr() {
        return this.str;
    }

    @java.lang.SuppressWarnings("all")
    public void setIspb(final String ispb) {
        this.ispb = ispb;
    }

    @java.lang.SuppressWarnings("all")
    public void setEnvironment(final String environment) {
        this.environment = environment;
    }

    @java.lang.SuppressWarnings("all")
    public void setCertificados(final CertConfig certificados) {
        this.certificados = certificados;
    }

    @java.lang.SuppressWarnings("all")
    public void setSpi(final SpiConfig spi) {
        this.spi = spi;
    }

    @java.lang.SuppressWarnings("all")
    public void setStr(final StrConfig str) {
        this.str = str;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof SpiStrProperties)) return false;
        final SpiStrProperties other = (SpiStrProperties) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$ispb = this.getIspb();
        final java.lang.Object other$ispb = other.getIspb();
        if (this$ispb == null ? other$ispb != null : !this$ispb.equals(other$ispb)) return false;
        final java.lang.Object this$environment = this.getEnvironment();
        final java.lang.Object other$environment = other.getEnvironment();
        if (this$environment == null ? other$environment != null : !this$environment.equals(other$environment)) return false;
        final java.lang.Object this$certificados = this.getCertificados();
        final java.lang.Object other$certificados = other.getCertificados();
        if (this$certificados == null ? other$certificados != null : !this$certificados.equals(other$certificados)) return false;
        final java.lang.Object this$spi = this.getSpi();
        final java.lang.Object other$spi = other.getSpi();
        if (this$spi == null ? other$spi != null : !this$spi.equals(other$spi)) return false;
        final java.lang.Object this$str = this.getStr();
        final java.lang.Object other$str = other.getStr();
        if (this$str == null ? other$str != null : !this$str.equals(other$str)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof SpiStrProperties;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $ispb = this.getIspb();
        result = result * PRIME + ($ispb == null ? 43 : $ispb.hashCode());
        final java.lang.Object $environment = this.getEnvironment();
        result = result * PRIME + ($environment == null ? 43 : $environment.hashCode());
        final java.lang.Object $certificados = this.getCertificados();
        result = result * PRIME + ($certificados == null ? 43 : $certificados.hashCode());
        final java.lang.Object $spi = this.getSpi();
        result = result * PRIME + ($spi == null ? 43 : $spi.hashCode());
        final java.lang.Object $str = this.getStr();
        result = result * PRIME + ($str == null ? 43 : $str.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "SpiStrProperties(ispb=" + this.getIspb() + ", environment=" + this.getEnvironment() + ", certificados=" + this.getCertificados() + ", spi=" + this.getSpi() + ", str=" + this.getStr() + ")";
    }
}
