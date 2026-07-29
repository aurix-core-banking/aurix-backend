package com.aurix.platform.cambio.service;

import com.aurix.platform.cambio.entity.TaxaSelic;
import com.aurix.platform.cambio.repository.TaxaSelicRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service principal para integração com BACEN
 * 
 * Gerencia taxa SELIC, relatórios e compliance
 */
@Service
@Transactional
public class BacenIntegrationService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BacenIntegrationService.class);
    private final TaxaSelicRepository taxaSelicRepository;
    private final WebClient.Builder webClientBuilder;
    @Value("${aurix.bacen.api.url-base}")
    private String urlBaseBacen;
    @Value("${aurix.bacen.selic.codigo-serie}")
    private String codigoSerieSelic;
    @Value("${aurix.bacen.api.timeout}")
    private long timeoutMs;

    /**
     * Item retornado pela API de séries temporais do BACEN (SGS), no formato
     * {@code [{"data":"dd/MM/yyyy","valor":"X.XX"}]}.
     */
    private record SerieTemporalBacenItem(String data, String valor) {
    }

    /**
     * Busca taxa SELIC atual
     */
    @Cacheable(value = "taxa-selic", key = "\'atual\'")
    public TaxaSelic buscarTaxaSelicAtual() {
        log.info("Buscando taxa SELIC atual");
        TaxaSelic taxaAtual = taxaSelicRepository.findTaxaAtual(LocalDate.now()).orElse(null);
        if (taxaAtual == null) {
            log.warn("Taxa SELIC atual não encontrada, buscando do BACEN");
            taxaAtual = atualizarTaxaSelicDoBacen();
        }
        log.info("Taxa SELIC atual: {} - Data: {}", taxaAtual != null ? taxaAtual.getValorTaxa() : "N/A", taxaAtual != null ? taxaAtual.getDataReferencia() : "N/A");
        return taxaAtual;
    }

    /**
     * Atualiza taxa SELIC do BACEN
     */
    @Async
    public TaxaSelic atualizarTaxaSelicDoBacen() {
        log.info("Atualizando taxa SELIC do BACEN");
        try {
            // Simular chamada para API do BACEN
            // Em produção, seria uma chamada real para https://api.bcb.gov.br/dados/serie/bcdata.sgs
            BigDecimal taxaBacen = buscarTaxaSelicDaApiBacen();
            if (taxaBacen != null) {
                TaxaSelic taxaSelic = TaxaSelic.builder().dataReferencia(LocalDate.now()).valorTaxa(taxaBacen).valorTaxaAnualizada(taxaBacen.multiply(BigDecimal.valueOf(12))).codigoSerieBacen(codigoSerieSelic).tipoTaxa(TaxaSelic.TipoTaxa.SELIC_OVER).fonteDados("API BACEN").dataAtualizacaoBacen(LocalDateTime.now()).build();
                TaxaSelic taxaSalva = taxaSelicRepository.save(taxaSelic);
                log.info("Taxa SELIC atualizada: {} - Data: {}", taxaSalva.getValorTaxa(), taxaSalva.getDataReferencia());
                return taxaSalva;
            }
        } catch (Exception e) {
            log.error("Erro ao atualizar taxa SELIC do BACEN: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Busca histórico de taxas SELIC
     */
    public List<TaxaSelic> buscarHistoricoTaxaSelic(LocalDate dataInicio, LocalDate dataFim) {
        log.info("Buscando histórico de taxa SELIC: {} a {}", dataInicio, dataFim);
        List<TaxaSelic> historico = taxaSelicRepository.findByPeriodo(dataInicio, dataFim);
        log.info("Encontradas {} taxas no período", historico.size());
        return historico;
    }

    /**
     * Calcula spread bancário
     */
    public BigDecimal calcularSpreadBancario(BigDecimal taxaCaptacao, BigDecimal taxaAplicacao) {
        log.info("Calculando spread bancário: Captação={}, Aplicação={}", taxaCaptacao, taxaAplicacao);
        BigDecimal spread = taxaAplicacao.subtract(taxaCaptacao);
        log.info("Spread calculado: {}", spread);
        return spread;
    }

    /**
     * Calcula taxa de aplicação baseada no spread
     */
    public BigDecimal calcularTaxaAplicacao(BigDecimal taxaSelic, BigDecimal spreadDesejado) {
        log.info("Calculando taxa de aplicação: SELIC={}, Spread={}", taxaSelic, spreadDesejado);
        BigDecimal taxaAplicacao = taxaSelic.add(spreadDesejado);
        log.info("Taxa de aplicação calculada: {}", taxaAplicacao);
        return taxaAplicacao;
    }

    /**
     * Calcula competitividade da taxa
     */
    public BigDecimal calcularCompetitividade(BigDecimal taxaNossa, BigDecimal taxaConcorrencia) {
        log.info("Calculando competitividade: Nossa={}, Concorrência={}", taxaNossa, taxaConcorrencia);
        if (taxaConcorrencia.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal competitividade = taxaNossa.divide(taxaConcorrencia, 4, BigDecimal.ROUND_HALF_UP);
            log.info("Competitividade calculada: {}", competitividade);
            return competitividade;
        }
        return BigDecimal.ZERO;
    }

    /**
     * Atualização automática da taxa SELIC
     */
    @Scheduled(cron = "${aurix.bacen.selic.intervalo-atualizacao:0 */30 * * * *}")
    public void atualizarTaxaSelicAutomaticamente() {
        log.info("Executando atualização automática da taxa SELIC");
        try {
            TaxaSelic taxaAtual = buscarTaxaSelicAtual();
            if (taxaAtual == null || taxaAtual.getDataReferencia().isBefore(LocalDate.now())) {
                atualizarTaxaSelicDoBacen();
            }
        } catch (Exception e) {
            log.error("Erro na atualização automática da taxa SELIC: {}", e.getMessage());
        }
    }

    /**
     * Busca a taxa SELIC mais recente na API de Séries Temporais do BACEN
     * (SGS), série configurada em {@code aurix.bacen.selic.codigo-serie}.
     * Endpoint público, sem necessidade de autenticação/certificado.
     */
    private BigDecimal buscarTaxaSelicDaApiBacen() {
        String uri = urlBaseBacen + "." + codigoSerieSelic + "/dados/ultimos/1?formato=json";
        log.info("Buscando taxa SELIC na API do BACEN: {}", uri);
        try {
            List<SerieTemporalBacenItem> resposta = webClientBuilder.build()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .bodyToFlux(SerieTemporalBacenItem.class)
                    .collectList()
                    .block(Duration.ofMillis(timeoutMs));
            if (resposta == null || resposta.isEmpty()) {
                log.warn("API do BACEN não retornou dados para a série {}", codigoSerieSelic);
                return null;
            }
            BigDecimal taxaBacen = new BigDecimal(resposta.get(0).valor());
            log.info("Taxa SELIC recebida da API do BACEN: {}", taxaBacen);
            return taxaBacen;
        } catch (Exception e) {
            log.error("Erro ao buscar taxa SELIC da API BACEN: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Valida se a taxa está dentro dos limites aceitáveis
     */
    public boolean validarTaxaSelic(BigDecimal taxa) {
        log.info("Validando taxa SELIC: {}", taxa);
        // Taxa SELIC normalmente fica entre 0.1% e 2% ao mês
        boolean valida = taxa.compareTo(BigDecimal.valueOf(0.001)) >= 0 && taxa.compareTo(BigDecimal.valueOf(0.02)) <= 0;
        log.info("Taxa SELIC válida: {}", valida);
        return valida;
    }

    /**
     * Calcula tendência da taxa SELIC
     */
    public String calcularTendenciaTaxaSelic() {
        log.info("Calculando tendência da taxa SELIC");
        List<TaxaSelic> ultimasTaxas = taxaSelicRepository.findTendenciaTaxa();
        if (ultimasTaxas.size() >= 2) {
            TaxaSelic taxaAtual = ultimasTaxas.get(0);
            TaxaSelic taxaAnterior = ultimasTaxas.get(1);
            BigDecimal variacao = taxaAtual.getValorTaxa().subtract(taxaAnterior.getValorTaxa());
            String tendencia;
            if (variacao.compareTo(BigDecimal.ZERO) > 0) {
                tendencia = "ALTA";
            } else if (variacao.compareTo(BigDecimal.ZERO) < 0) {
                tendencia = "BAIXA";
            } else {
                tendencia = "ESTAVEL";
            }
            log.info("Tendência da taxa SELIC: {} (Variação: {})", tendencia, variacao);
            return tendencia;
        }
        return "INDEFINIDA";
    }

    @java.lang.SuppressWarnings("all")
    public BacenIntegrationService(final TaxaSelicRepository taxaSelicRepository, final WebClient.Builder webClientBuilder) {
        this.taxaSelicRepository = taxaSelicRepository;
        this.webClientBuilder = webClientBuilder;
    }
}
