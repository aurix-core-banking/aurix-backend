package com.aurix.platform.seguros.integracao;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * Integração real com a seguradora (API de mercado) para cotação de prêmio e
 * registro de apólice. Ativa apenas no perfil "producao" — substitui o
 * {@link SeguradoraStub}.
 *
 * Configuração:
 * <ul>
 *   <li>{@code aurix.seguros.seguradora.url} — URL base da API da seguradora</li>
 *   <li>{@code aurix.seguros.seguradora.timeout} — timeout (ms)</li>
 * </ul>
 */
@Service
@Profile("producao")
public class SeguradoraReal implements SeguradoraService {

    private static final Logger log = LoggerFactory.getLogger(SeguradoraReal.class);

    private final SeguradoraClient client;

    public SeguradoraReal(
            @Value("${aurix.seguros.seguradora.url:http://localhost:8098}") String urlBase,
            @Value("${aurix.seguros.seguradora.timeout:5000}") long timeoutMs) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) timeoutMs);
        factory.setReadTimeout((int) timeoutMs);

        RestClient restClient = RestClient.builder()
                .baseUrl(urlBase)
                .requestFactory(factory)
                .build();
        this.client = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(SeguradoraClient.class);
    }

    @Override
    public Cotacao cotar(CotacaoRequestDto req) {
        log.info("Seguradora real: cotação produto={}, valorSegurado={}",
                req.produto(), req.valorSegurado());
        try {
            SeguradoraClient.CotacaoResponse resp = client.cotar(new SeguradoraClient.CotacaoRequest(
                    req.produto(), req.cobertura(), req.valorSegurado(),
                    req.idade(), req.uf(), req.sexo(), req.profissao()));
            if (resp == null || resp.premio() == null) {
                throw new IllegalStateException("Resposta de cotação vazia da seguradora");
            }
            return new Cotacao(resp.premio(), resp.taxa(), resp.seguradora(), resp.aceita(), null);
        } catch (Exception e) {
            log.error("Erro ao cotar na seguradora: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public RegistroApolice registrarApolice(RegistroApoliceDto req) {
        log.info("Seguradora real: registro apólice {}", req.numeroApolice());
        SeguradoraClient.RegistrarApoliceResponse resp = client.registrarApolice(
                new SeguradoraClient.RegistrarApoliceRequest(
                        req.numeroApolice(), req.produtoId(), req.clienteId(),
                        req.cobertura(), req.valorSegurado(), req.premio()));
        return new RegistroApolice(resp.protocolo(), resp.status(), resp.numeroApolice());
    }
}
