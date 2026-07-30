package com.aurix.platform.cambio.service;

import com.aurix.platform.cambio.client.SpiStrApiClient;
import com.aurix.platform.cambio.config.SpiStrProperties;
import com.aurix.platform.cambio.entity.TransacaoSPI;
import com.aurix.platform.cambio.entity.TransacaoSTR;
import com.aurix.platform.cambio.repository.TransacaoSPIRepository;
import com.aurix.platform.cambio.repository.TransacaoSTRRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SpiStrIntegrationService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SpiStrIntegrationService.class);
    private final TransacaoSPIRepository spiRepository;
    private final TransacaoSTRRepository strRepository;
    private final SpiStrApiClient spiStrApiClient;
    private final SpiStrProperties properties;

    public TransacaoSPI criarTransacaoSPI(String endToEndId, String ispbOrigem, String ispbDestino, String contaOrigem, String contaDestino, BigDecimal valor, String descricao) {
        TransacaoSPI transacao = new TransacaoSPI();
        transacao.setEndToEndId(endToEndId);
        transacao.setIspbOrigem(ispbOrigem);
        transacao.setIspbDestino(ispbDestino);
        transacao.setContaOrigem(contaOrigem);
        transacao.setContaDestino(contaDestino);
        transacao.setValor(valor);
        transacao.setDescricao(descricao);
        transacao.setStatus(TransacaoSPI.StatusSPI.PENDENTE);
        transacao.setDataCriacao(LocalDateTime.now());
        return spiRepository.save(transacao);
    }

    public SpiResult enviarPixSPI(String endToEndId, String ispbOrigem, String ispbDestino, String contaOrigem, String contaDestino, BigDecimal valor, String descricao) {
        TransacaoSPI transacao = criarTransacaoSPI(endToEndId, ispbOrigem, ispbDestino, contaOrigem, contaDestino, valor, descricao);
        transacao.setStatus(TransacaoSPI.StatusSPI.ENVIANDO);
        transacao.setDataEnvio(LocalDateTime.now());
        transacao.setTentativasEnvio(transacao.getTentativasEnvio() + 1);
        spiRepository.save(transacao);
        try {
            SpiResult resultado = spiStrApiClient.enviarPixSpi(transacao);
            if (resultado.isSucesso()) {
                transacao.setStatus(TransacaoSPI.StatusSPI.LIQUIDADA);
                transacao.setDataLiquidacao(resultado.getDataProcessamento() != null ? resultado.getDataProcessamento() : LocalDateTime.now());
            } else {
                transacao.setStatus(TransacaoSPI.StatusSPI.REJEITADA);
            }
            transacao.setCodigoRetorno(resultado.getCodigoRetorno());
            transacao.setMensagemRetorno(resultado.getMensagem());
            spiRepository.save(transacao);
            return resultado;
        } catch (Exception e) {
            log.error("Erro ao enviar para SPI: {}", e.getMessage());
            transacao.setStatus(TransacaoSPI.StatusSPI.ERRO);
            transacao.setMensagemRetorno("Erro: " + e.getMessage());
            spiRepository.save(transacao);
            throw new RuntimeException("Erro ao enviar transação SPI: " + e.getMessage());
        }
    }

    public TransacaoSTR criarTransacaoSTR(String numeroControle, String ispbOrigem, String ispbDestino, String contaOrigem, String contaDestino, BigDecimal valor, TransacaoSTR.TipoSTR tipoSTR, LocalDateTime dataAgendamento) {
        TransacaoSTR transacao = new TransacaoSTR();
        transacao.setNumeroControle(numeroControle);
        transacao.setIspbOrigem(ispbOrigem);
        transacao.setIspbDestino(ispbDestino);
        transacao.setContaOrigem(contaOrigem);
        transacao.setContaDestino(contaDestino);
        transacao.setValor(valor);
        transacao.setTipoSTR(tipoSTR);
        transacao.setStatus(dataAgendamento != null && dataAgendamento.isAfter(LocalDateTime.now()) ? TransacaoSTR.StatusSTR.AGENDADA : TransacaoSTR.StatusSTR.PENDENTE);
        transacao.setDataCriacao(LocalDateTime.now());
        transacao.setDataAgendamento(dataAgendamento);
        return strRepository.save(transacao);
    }

    public StrResult enviarTEDSTR(String idTransacao, String ispbOrigem, String ispbDestino, String contaOrigem, String contaDestino, BigDecimal valor, LocalDateTime dataAgendamento) {
        String numeroControle = "TED-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        TransacaoSTR transacao = criarTransacaoSTR(numeroControle, ispbOrigem, ispbDestino, contaOrigem, contaDestino, valor, TransacaoSTR.TipoSTR.TED, dataAgendamento);
        transacao.setStatus(TransacaoSTR.StatusSTR.ENVIANDO);
        transacao.setDataEnvio(LocalDateTime.now());
        transacao.setTentativasEnvio(transacao.getTentativasEnvio() + 1);
        strRepository.save(transacao);
        try {
            StrResult resultado = spiStrApiClient.enviarTedStr(transacao);
            if (resultado.isSucesso()) {
                transacao.setStatus(TransacaoSTR.StatusSTR.LIQUIDADA);
                transacao.setDataLiquidacao(resultado.getDataLiquidacao());
            } else {
                transacao.setStatus(TransacaoSTR.StatusSTR.REJEITADA);
            }
            transacao.setCodigoRetorno(resultado.getCodigoRetorno());
            transacao.setMensagemRetorno(resultado.getMensagem());
            strRepository.save(transacao);
            return resultado;
        } catch (Exception e) {
            log.error("Erro ao enviar para STR: {}", e.getMessage());
            transacao.setStatus(TransacaoSTR.StatusSTR.ERRO);
            transacao.setMensagemRetorno("Erro: " + e.getMessage());
            strRepository.save(transacao);
            throw new RuntimeException("Erro ao enviar transação STR: " + e.getMessage());
        }
    }

    public StrResult enviarDOCSTR(String idTransacao, String ispbOrigem, String ispbDestino, String contaOrigem, String contaDestino, BigDecimal valor, LocalDateTime dataAgendamento) {
        String numeroControle = "DOC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        TransacaoSTR transacao = criarTransacaoSTR(numeroControle, ispbOrigem, ispbDestino, contaOrigem, contaDestino, valor, TransacaoSTR.TipoSTR.DOC, dataAgendamento);
        transacao.setStatus(TransacaoSTR.StatusSTR.ENVIANDO);
        transacao.setDataEnvio(LocalDateTime.now());
        transacao.setTentativasEnvio(transacao.getTentativasEnvio() + 1);
        strRepository.save(transacao);
        try {
            StrResult resultado = spiStrApiClient.enviarDocStr(transacao);
            if (resultado.isSucesso()) {
                transacao.setStatus(TransacaoSTR.StatusSTR.LIQUIDADA);
                transacao.setDataLiquidacao(resultado.getDataLiquidacao());
            } else {
                transacao.setStatus(TransacaoSTR.StatusSTR.REJEITADA);
            }
            transacao.setCodigoRetorno(resultado.getCodigoRetorno());
            transacao.setMensagemRetorno(resultado.getMensagem());
            strRepository.save(transacao);
            return resultado;
        } catch (Exception e) {
            log.error("Erro ao enviar DOC para STR: {}", e.getMessage());
            transacao.setStatus(TransacaoSTR.StatusSTR.ERRO);
            transacao.setMensagemRetorno("Erro: " + e.getMessage());
            strRepository.save(transacao);
            throw new RuntimeException("Erro ao enviar transação DOC STR: " + e.getMessage());
        }
    }

    public TransacaoSPI consultarStatusSPI(String endToEndId) {
        return spiRepository.findByEndToEndId(endToEndId).orElseThrow(() -> new RuntimeException("Transação SPI não encontrada"));
    }

    public TransacaoSTR consultarStatusSTR(String numeroControle) {
        return strRepository.findByNumeroControle(numeroControle).orElseThrow(() -> new RuntimeException("Transação STR não encontrada"));
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void processarTransacoesPendentesSPI() {
        if (!properties.getSpi().isEnabled()) {
            return;
        }
        List<TransacaoSPI> pendentes = spiRepository.findTransacoesParaEnvio();
        for (TransacaoSPI transacao : pendentes) {
            try {
                enviarPixSPI(transacao.getEndToEndId(), transacao.getIspbOrigem(), transacao.getIspbDestino(), transacao.getContaOrigem(), transacao.getContaDestino(), transacao.getValor(), transacao.getDescricao());
            } catch (Exception e) {
                log.error("Erro ao processar transação SPI pendente {}: {}", transacao.getEndToEndId(), e.getMessage());
            }
        }
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void processarTransacoesPendentesSTR() {
        if (!properties.getStr().isEnabled()) {
            return;
        }
        List<TransacaoSTR> pendentes = strRepository.findTransacoesParaEnvio(LocalDateTime.now());
        for (TransacaoSTR transacao : pendentes) {
            try {
                enviarTEDSTR(transacao.getNumeroControle(), transacao.getIspbOrigem(), transacao.getIspbDestino(), transacao.getContaOrigem(), transacao.getContaDestino(), transacao.getValor(), transacao.getDataAgendamento());
            } catch (Exception e) {
                log.error("Erro ao processar transação STR pendente {}: {}", transacao.getNumeroControle(), e.getMessage());
            }
        }
    }


    public static class SpiResult {
        private boolean sucesso;
        private String codigoRetorno;
        private String mensagem;
        private String idTransacao;
        private LocalDateTime dataProcessamento;

        @java.lang.SuppressWarnings("all")
        SpiResult(final boolean sucesso, final String codigoRetorno, final String mensagem, final String idTransacao, final LocalDateTime dataProcessamento) {
            this.sucesso = sucesso;
            this.codigoRetorno = codigoRetorno;
            this.mensagem = mensagem;
            this.idTransacao = idTransacao;
            this.dataProcessamento = dataProcessamento;
        }


        @java.lang.SuppressWarnings("all")
        public static class SpiResultBuilder {
            @java.lang.SuppressWarnings("all")
            private boolean sucesso;
            @java.lang.SuppressWarnings("all")
            private String codigoRetorno;
            @java.lang.SuppressWarnings("all")
            private String mensagem;
            @java.lang.SuppressWarnings("all")
            private String idTransacao;
            @java.lang.SuppressWarnings("all")
            private LocalDateTime dataProcessamento;

            @java.lang.SuppressWarnings("all")
            SpiResultBuilder() {
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public SpiStrIntegrationService.SpiResult.SpiResultBuilder sucesso(final boolean sucesso) {
                this.sucesso = sucesso;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public SpiStrIntegrationService.SpiResult.SpiResultBuilder codigoRetorno(final String codigoRetorno) {
                this.codigoRetorno = codigoRetorno;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public SpiStrIntegrationService.SpiResult.SpiResultBuilder mensagem(final String mensagem) {
                this.mensagem = mensagem;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public SpiStrIntegrationService.SpiResult.SpiResultBuilder idTransacao(final String idTransacao) {
                this.idTransacao = idTransacao;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public SpiStrIntegrationService.SpiResult.SpiResultBuilder dataProcessamento(final LocalDateTime dataProcessamento) {
                this.dataProcessamento = dataProcessamento;
                return this;
            }

            @java.lang.SuppressWarnings("all")
            public SpiStrIntegrationService.SpiResult build() {
                return new SpiStrIntegrationService.SpiResult(this.sucesso, this.codigoRetorno, this.mensagem, this.idTransacao, this.dataProcessamento);
            }

            @java.lang.Override
            @java.lang.SuppressWarnings("all")
            public java.lang.String toString() {
                return "SpiStrIntegrationService.SpiResult.SpiResultBuilder(sucesso=" + this.sucesso + ", codigoRetorno=" + this.codigoRetorno + ", mensagem=" + this.mensagem + ", idTransacao=" + this.idTransacao + ", dataProcessamento=" + this.dataProcessamento + ")";
            }
        }

        @java.lang.SuppressWarnings("all")
        public static SpiStrIntegrationService.SpiResult.SpiResultBuilder builder() {
            return new SpiStrIntegrationService.SpiResult.SpiResultBuilder();
        }

        @java.lang.SuppressWarnings("all")
        public boolean isSucesso() {
            return this.sucesso;
        }

        @java.lang.SuppressWarnings("all")
        public String getCodigoRetorno() {
            return this.codigoRetorno;
        }

        @java.lang.SuppressWarnings("all")
        public String getMensagem() {
            return this.mensagem;
        }

        @java.lang.SuppressWarnings("all")
        public String getIdTransacao() {
            return this.idTransacao;
        }

        @java.lang.SuppressWarnings("all")
        public LocalDateTime getDataProcessamento() {
            return this.dataProcessamento;
        }

        @java.lang.SuppressWarnings("all")
        public void setSucesso(final boolean sucesso) {
            this.sucesso = sucesso;
        }

        @java.lang.SuppressWarnings("all")
        public void setCodigoRetorno(final String codigoRetorno) {
            this.codigoRetorno = codigoRetorno;
        }

        @java.lang.SuppressWarnings("all")
        public void setMensagem(final String mensagem) {
            this.mensagem = mensagem;
        }

        @java.lang.SuppressWarnings("all")
        public void setIdTransacao(final String idTransacao) {
            this.idTransacao = idTransacao;
        }

        @java.lang.SuppressWarnings("all")
        public void setDataProcessamento(final LocalDateTime dataProcessamento) {
            this.dataProcessamento = dataProcessamento;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof SpiStrIntegrationService.SpiResult)) return false;
            final SpiStrIntegrationService.SpiResult other = (SpiStrIntegrationService.SpiResult) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            if (this.isSucesso() != other.isSucesso()) return false;
            final java.lang.Object this$codigoRetorno = this.getCodigoRetorno();
            final java.lang.Object other$codigoRetorno = other.getCodigoRetorno();
            if (this$codigoRetorno == null ? other$codigoRetorno != null : !this$codigoRetorno.equals(other$codigoRetorno)) return false;
            final java.lang.Object this$mensagem = this.getMensagem();
            final java.lang.Object other$mensagem = other.getMensagem();
            if (this$mensagem == null ? other$mensagem != null : !this$mensagem.equals(other$mensagem)) return false;
            final java.lang.Object this$idTransacao = this.getIdTransacao();
            final java.lang.Object other$idTransacao = other.getIdTransacao();
            if (this$idTransacao == null ? other$idTransacao != null : !this$idTransacao.equals(other$idTransacao)) return false;
            final java.lang.Object this$dataProcessamento = this.getDataProcessamento();
            final java.lang.Object other$dataProcessamento = other.getDataProcessamento();
            if (this$dataProcessamento == null ? other$dataProcessamento != null : !this$dataProcessamento.equals(other$dataProcessamento)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof SpiStrIntegrationService.SpiResult;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            result = result * PRIME + (this.isSucesso() ? 79 : 97);
            final java.lang.Object $codigoRetorno = this.getCodigoRetorno();
            result = result * PRIME + ($codigoRetorno == null ? 43 : $codigoRetorno.hashCode());
            final java.lang.Object $mensagem = this.getMensagem();
            result = result * PRIME + ($mensagem == null ? 43 : $mensagem.hashCode());
            final java.lang.Object $idTransacao = this.getIdTransacao();
            result = result * PRIME + ($idTransacao == null ? 43 : $idTransacao.hashCode());
            final java.lang.Object $dataProcessamento = this.getDataProcessamento();
            result = result * PRIME + ($dataProcessamento == null ? 43 : $dataProcessamento.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SpiStrIntegrationService.SpiResult(sucesso=" + this.isSucesso() + ", codigoRetorno=" + this.getCodigoRetorno() + ", mensagem=" + this.getMensagem() + ", idTransacao=" + this.getIdTransacao() + ", dataProcessamento=" + this.getDataProcessamento() + ")";
        }
    }


    public static class StrResult {
        private boolean sucesso;
        private String codigoRetorno;
        private String mensagem;
        private String numeroControle;
        private LocalDateTime dataLiquidacao;

        @java.lang.SuppressWarnings("all")
        StrResult(final boolean sucesso, final String codigoRetorno, final String mensagem, final String numeroControle, final LocalDateTime dataLiquidacao) {
            this.sucesso = sucesso;
            this.codigoRetorno = codigoRetorno;
            this.mensagem = mensagem;
            this.numeroControle = numeroControle;
            this.dataLiquidacao = dataLiquidacao;
        }


        @java.lang.SuppressWarnings("all")
        public static class StrResultBuilder {
            @java.lang.SuppressWarnings("all")
            private boolean sucesso;
            @java.lang.SuppressWarnings("all")
            private String codigoRetorno;
            @java.lang.SuppressWarnings("all")
            private String mensagem;
            @java.lang.SuppressWarnings("all")
            private String numeroControle;
            @java.lang.SuppressWarnings("all")
            private LocalDateTime dataLiquidacao;

            @java.lang.SuppressWarnings("all")
            StrResultBuilder() {
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public SpiStrIntegrationService.StrResult.StrResultBuilder sucesso(final boolean sucesso) {
                this.sucesso = sucesso;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public SpiStrIntegrationService.StrResult.StrResultBuilder codigoRetorno(final String codigoRetorno) {
                this.codigoRetorno = codigoRetorno;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public SpiStrIntegrationService.StrResult.StrResultBuilder mensagem(final String mensagem) {
                this.mensagem = mensagem;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public SpiStrIntegrationService.StrResult.StrResultBuilder numeroControle(final String numeroControle) {
                this.numeroControle = numeroControle;
                return this;
            }

            /**
             * @return {@code this}.
             */
            @java.lang.SuppressWarnings("all")
            public SpiStrIntegrationService.StrResult.StrResultBuilder dataLiquidacao(final LocalDateTime dataLiquidacao) {
                this.dataLiquidacao = dataLiquidacao;
                return this;
            }

            @java.lang.SuppressWarnings("all")
            public SpiStrIntegrationService.StrResult build() {
                return new SpiStrIntegrationService.StrResult(this.sucesso, this.codigoRetorno, this.mensagem, this.numeroControle, this.dataLiquidacao);
            }

            @java.lang.Override
            @java.lang.SuppressWarnings("all")
            public java.lang.String toString() {
                return "SpiStrIntegrationService.StrResult.StrResultBuilder(sucesso=" + this.sucesso + ", codigoRetorno=" + this.codigoRetorno + ", mensagem=" + this.mensagem + ", numeroControle=" + this.numeroControle + ", dataLiquidacao=" + this.dataLiquidacao + ")";
            }
        }

        @java.lang.SuppressWarnings("all")
        public static SpiStrIntegrationService.StrResult.StrResultBuilder builder() {
            return new SpiStrIntegrationService.StrResult.StrResultBuilder();
        }

        @java.lang.SuppressWarnings("all")
        public boolean isSucesso() {
            return this.sucesso;
        }

        @java.lang.SuppressWarnings("all")
        public String getCodigoRetorno() {
            return this.codigoRetorno;
        }

        @java.lang.SuppressWarnings("all")
        public String getMensagem() {
            return this.mensagem;
        }

        @java.lang.SuppressWarnings("all")
        public String getNumeroControle() {
            return this.numeroControle;
        }

        @java.lang.SuppressWarnings("all")
        public LocalDateTime getDataLiquidacao() {
            return this.dataLiquidacao;
        }

        @java.lang.SuppressWarnings("all")
        public void setSucesso(final boolean sucesso) {
            this.sucesso = sucesso;
        }

        @java.lang.SuppressWarnings("all")
        public void setCodigoRetorno(final String codigoRetorno) {
            this.codigoRetorno = codigoRetorno;
        }

        @java.lang.SuppressWarnings("all")
        public void setMensagem(final String mensagem) {
            this.mensagem = mensagem;
        }

        @java.lang.SuppressWarnings("all")
        public void setNumeroControle(final String numeroControle) {
            this.numeroControle = numeroControle;
        }

        @java.lang.SuppressWarnings("all")
        public void setDataLiquidacao(final LocalDateTime dataLiquidacao) {
            this.dataLiquidacao = dataLiquidacao;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof SpiStrIntegrationService.StrResult)) return false;
            final SpiStrIntegrationService.StrResult other = (SpiStrIntegrationService.StrResult) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            if (this.isSucesso() != other.isSucesso()) return false;
            final java.lang.Object this$codigoRetorno = this.getCodigoRetorno();
            final java.lang.Object other$codigoRetorno = other.getCodigoRetorno();
            if (this$codigoRetorno == null ? other$codigoRetorno != null : !this$codigoRetorno.equals(other$codigoRetorno)) return false;
            final java.lang.Object this$mensagem = this.getMensagem();
            final java.lang.Object other$mensagem = other.getMensagem();
            if (this$mensagem == null ? other$mensagem != null : !this$mensagem.equals(other$mensagem)) return false;
            final java.lang.Object this$numeroControle = this.getNumeroControle();
            final java.lang.Object other$numeroControle = other.getNumeroControle();
            if (this$numeroControle == null ? other$numeroControle != null : !this$numeroControle.equals(other$numeroControle)) return false;
            final java.lang.Object this$dataLiquidacao = this.getDataLiquidacao();
            final java.lang.Object other$dataLiquidacao = other.getDataLiquidacao();
            if (this$dataLiquidacao == null ? other$dataLiquidacao != null : !this$dataLiquidacao.equals(other$dataLiquidacao)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof SpiStrIntegrationService.StrResult;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            result = result * PRIME + (this.isSucesso() ? 79 : 97);
            final java.lang.Object $codigoRetorno = this.getCodigoRetorno();
            result = result * PRIME + ($codigoRetorno == null ? 43 : $codigoRetorno.hashCode());
            final java.lang.Object $mensagem = this.getMensagem();
            result = result * PRIME + ($mensagem == null ? 43 : $mensagem.hashCode());
            final java.lang.Object $numeroControle = this.getNumeroControle();
            result = result * PRIME + ($numeroControle == null ? 43 : $numeroControle.hashCode());
            final java.lang.Object $dataLiquidacao = this.getDataLiquidacao();
            result = result * PRIME + ($dataLiquidacao == null ? 43 : $dataLiquidacao.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SpiStrIntegrationService.StrResult(sucesso=" + this.isSucesso() + ", codigoRetorno=" + this.getCodigoRetorno() + ", mensagem=" + this.getMensagem() + ", numeroControle=" + this.getNumeroControle() + ", dataLiquidacao=" + this.getDataLiquidacao() + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public SpiStrIntegrationService(final TransacaoSPIRepository spiRepository, final TransacaoSTRRepository strRepository, final SpiStrApiClient spiStrApiClient, final SpiStrProperties properties) {
        this.spiRepository = spiRepository;
        this.strRepository = strRepository;
        this.spiStrApiClient = spiStrApiClient;
        this.properties = properties;
    }
}
