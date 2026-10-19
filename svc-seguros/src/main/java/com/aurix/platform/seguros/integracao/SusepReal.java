package com.aurix.platform.seguros.integracao;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * Integração real com a SUSEP para registro de apólices e comunicação de
 * sinistros. Ativa apenas no perfil "producao".
 *
 * Configuração:
 * <ul>
 *   <li>{@code aurix.seguros.susep.url} — URL base da API da SUSEP</li>
 *   <li>{@code aurix.seguros.susep.timeout} — timeout (ms)</li>
 * </ul>
 */
@Service
@Profile("producao")
public class SusepReal implements SusepService {

    private static final Logger log = LoggerFactory.getLogger(SusepReal.class);

    private final SusepClient client;

    public SusepReal(
            @Value("${aurix.seguros.susep.url:http://localhost:8098}") String urlBase,
            @Value("${aurix.seguros.susep.timeout:5000}") long timeoutMs) {
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
                .createClient(SusepClient.class);
    }

    @Override
    public Registro registarApolice(RegistroApoliceDto apolice) {
        log.info("Susep real: registro apólice {}", apolice.numeroApolice());
        SusepClient.RegistrarApoliceResponse resp = client.registrarApolice(
                new SusepClient.RegistrarApoliceRequest(
                        apolice.numeroApolice(), apolice.produtoId(), apolice.clienteId(),
                        apolice.cobertura(), apolice.valorSegurado(), apolice.premio()));
        return new Registro(resp.protocolo(), resp.status(), resp.numeroRegistroSusep());
    }

    @Override
    public Comunicacao comunicarSinistro(Long sinistroId, BigDecimal valor, String tipo) {
        log.info("Susep real: comunicação de sinistro {}", sinistroId);
        SusepClient.ComunicarSinistroRequest req =
                new SusepClient.ComunicarSinistroRequest(sinistroId, valor, tipo);
        SusepClient.ComunicarSinistroResponse resp = client.comunicarSinistro(req);
        return new Comunicacao(sinistroId, resp.protocolo(), resp.status());
    }

    @HttpExchange("/api/susep")
    interface SusepClient {

        record RegistrarApoliceRequest(
                String numeroApolice, Long produtoId, Long clienteId, String cobertura,
                BigDecimal valorSegurado, BigDecimal premio) {
        }

        record RegistrarApoliceResponse(String protocolo, String status, Long numeroRegistroSusep) {
        }

        record ComunicarSinistroRequest(Long sinistroId, BigDecimal valor, String tipo) {
        }

        record ComunicarSinistroResponse(String protocolo, String status) {
        }

        @PostExchange("/apolices")
        RegistrarApoliceResponse registrarApolice(@RequestBody RegistrarApoliceRequest request);

        @PostExchange("/sinistros")
        ComunicarSinistroResponse comunicarSinistro(@RequestBody ComunicarSinistroRequest request);
    }
}
