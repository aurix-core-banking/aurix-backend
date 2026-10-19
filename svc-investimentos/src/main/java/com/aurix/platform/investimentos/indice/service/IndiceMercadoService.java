package com.aurix.platform.investimentos.indice.service;

import com.aurix.platform.investimentos.indice.entity.IndiceMercado;
import com.aurix.platform.investimentos.indice.entity.TipoIndice;
import com.aurix.platform.investimentos.indice.repository.IndiceMercadoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

@Service
@Transactional
public class IndiceMercadoService {

    private static final Logger log = LoggerFactory.getLogger(IndiceMercadoService.class);

    /**
     * Códigos das séries na API de Séries Temporais do BACEN (SGS):
     * - SELIC: série 11 (taxa básica de juros acumulada no mês, % a.a.)
     * - CDI: série 12 (taxa CDI acumulada no mês, % a.a.)
     * - IMAB: série 20891 (IBrX-100) como referência indireta; ajustado por configuração
     */
    private static final Map<TipoIndice, String> CODIGOS_SERIE_PADRAO = new EnumMap<>(Map.of(
            TipoIndice.SELIC, "11",
            TipoIndice.CDI, "12",
            TipoIndice.IMAB, "20891"
    ));

    /**
     * Item retornado pela API de séries temporais do BACEN (SGS), no formato
     * {@code [{"data":"dd/MM/yyyy","valor":"X.XX"}]}.
     */
    private record SerieTemporalBacenItem(String data, String valor) {
    }

    private final IndiceMercadoRepository indiceMercadoRepository;
    private final RestClient.Builder restClientBuilder;

    @Value("${aurix.investimentos.bacen.api.url-base:https://api.bcb.gov.br/dados/serie/bcdata.sgs}")
    private String urlBaseBacen;

    @Value("${aurix.investimentos.bacen.api.timeout:30000}")
    private long timeoutMs;

    public IndiceMercadoService(IndiceMercadoRepository indiceMercadoRepository,
                                RestClient.Builder restClientBuilder) {
        this.indiceMercadoRepository = indiceMercadoRepository;
        this.restClientBuilder = restClientBuilder;
    }

    /**
     * Busca a cotação atual do índice, atualizando do BACEN caso não exista ainda.
     */
    public IndiceMercado buscarIndiceAtual(TipoIndice tipoIndice) {
        log.info("Buscando índice atual: {}", tipoIndice);
        IndiceMercado indice = indiceMercadoRepository
                .findIndiceAtual(tipoIndice, LocalDate.now())
                .orElse(null);
        if (indice == null) {
            log.warn("Índice {} não encontrado localmente, buscando do BACEN", tipoIndice);
            indice = atualizarIndiceDoBacen(tipoIndice);
        }
        if (indice != null) {
            log.info("Índice {}: {} em {}", tipoIndice, indice.getValor(), indice.getDataReferencia());
        }
        return indice;
    }

    /**
     * Atualiza a cotação de um índice a partir da API de Séries Temporais do BACEN (SGS).
     */
    @Async
    public IndiceMercado atualizarIndiceDoBacen(TipoIndice tipoIndice) {
        log.info("Atualizando índice {} do BACEN", tipoIndice);
        try {
            BigDecimal valor = buscarValorIndiceDaApiBacen(tipoIndice);
            if (valor != null) {
                IndiceMercado indice = new IndiceMercado(
                        tipoIndice, valor, LocalDate.now(),
                        CODIGOS_SERIE_PADRAO.get(tipoIndice), "API BACEN SGS");
                return indiceMercadoRepository.save(indice);
            }
        } catch (Exception e) {
            log.error("Erro ao atualizar índice {} do BACEN: {}", tipoIndice, e.getMessage());
        }
        return indiceMercadoRepository
                .findTopByTipoIndiceOrderByDataReferenciaDesc(tipoIndice)
                .orElse(null);
    }

    /**
     * Busca histórico de cotações de um índice.
     */
    public List<IndiceMercado> buscarHistorico(TipoIndice tipoIndice) {
        return indiceMercadoRepository.findByTipoIndiceOrderByDataReferenciaDesc(tipoIndice);
    }

    /**
     * Atualização automática diária das cotações para todos os índices configurados.
     */
    @Scheduled(cron = "${aurix.investimentos.bacen.selic.intervalo-atualizacao:0 0 8 * * *}")
    public void atualizarIndicesAutomaticamente() {
        log.info("Executando atualização automática dos índices de mercado");
        for (TipoIndice tipo : TipoIndice.values()) {
            try {
                IndiceMercado atual = buscarIndiceAtual(tipo);
                if (atual == null || !atual.getDataReferencia().isEqual(LocalDate.now())) {
                    atualizarIndiceDoBacen(tipo);
                }
            } catch (Exception e) {
                log.error("Erro na atualização automática do índice {}: {}", tipo, e.getMessage());
            }
        }
    }

    /**
     * Calcula a taxa anual projetada (fração decimal anual, ex.: 0.1265 = 12,65% a.a.)
     * a partir da última cotação mensal do índice, aplicando o percentual do indexador
     * (ex.: 1.10000 = 110% do CDI). Retorna {@code null} quando o índice não está
     * disponível.
     */
    public BigDecimal buscarTaxaProjetadaAnual(TipoIndice tipoIndice, BigDecimal percentualIndexador) {
        IndiceMercado atual = buscarIndiceAtual(tipoIndice);
        if (atual == null || atual.getValor() == null) {
            return null;
        }
        BigDecimal taxaMensal = atual.getValor();
        BigDecimal fracaoAnual = taxaMensal
                .multiply(BigDecimal.valueOf(12))
                .divide(BigDecimal.valueOf(100), 10, java.math.RoundingMode.HALF_UP);
        if (percentualIndexador != null) {
            fracaoAnual = fracaoAnual.multiply(percentualIndexador);
        }
        log.info("Taxa anual projetada do índice {}: {} (percentual indexador: {})",
                tipoIndice, fracaoAnual, percentualIndexador);
        return fracaoAnual;
    }

    /**
     * Busca o valor do índice na API de Séries Temporais do BACEN (SGS). Endpoint
     * público, sem necessidade de autenticação/certificado.
     */
    private BigDecimal buscarValorIndiceDaApiBacen(TipoIndice tipoIndice) {
        String codigoSerie = CODIGOS_SERIE_PADRAO.get(tipoIndice);
        String uri = urlBaseBacen + "." + codigoSerie + "/dados/ultimos/1?formato=json";
        log.info("Buscando índice {} na API do BACEN: {}", tipoIndice, uri);
        try {
            List<SerieTemporalBacenItem> resposta = restClientBuilder.build()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .body(new org.springframework.core.ParameterizedTypeReference<List<SerieTemporalBacenItem>>() {
                    });
            if (resposta == null || resposta.isEmpty()) {
                log.warn("API do BACEN não retornou dados para o índice {} (série {})",
                        tipoIndice, codigoSerie);
                return null;
            }
            BigDecimal valor = new BigDecimal(resposta.get(0).valor());
            log.info("Valor do índice {} recebido da API do BACEN: {}", tipoIndice, valor);
            return valor;
        } catch (Exception e) {
            log.error("Erro ao buscar índice {} da API BACEN: {}", tipoIndice, e.getMessage());
            return null;
        }
    }
}