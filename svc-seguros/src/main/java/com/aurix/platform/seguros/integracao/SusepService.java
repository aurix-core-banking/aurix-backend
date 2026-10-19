package com.aurix.platform.seguros.integracao;

import java.math.BigDecimal;

/**
 * Serviço de comunicação regulatória com a SUSEP para registro de apólices e
 * comunicação de sinistros. A integração real é feita via API da SUSEP (perfil
 * producao); nos demais perfis usa-se a implementação simulada
 * {@link SusepStub}.
 */
public interface SusepService {

    Registro registarApolice(RegistroApoliceDto apolice);

    Comunicacao comunicarSinistro(Long sinistroId, BigDecimal valor, String tipo);

    record RegistroApoliceDto(
            String numeroApolice, Long produtoId, Long clienteId, String cobertura,
            BigDecimal valorSegurado, BigDecimal premio) {
    }

    record Registro(String protocolo, String status, Long numeroRegistroSusep) {
    }

    record Comunicacao(Long sinistroId, String protocolo, String status) {
    }
}
