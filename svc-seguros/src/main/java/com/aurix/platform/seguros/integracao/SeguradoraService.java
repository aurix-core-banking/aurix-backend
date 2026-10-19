package com.aurix.platform.seguros.integracao;

import java.math.BigDecimal;

/**
 * Serviço de cotação de prêmio junto à seguradora. Duas implementações:
 * {@link com.aurix.platform.seguros.integracao.SeguradoraStub} (dev/test, simulada)
 * e {@link com.aurix.platform.seguros.integracao.SeguradoraReal} (produção,
 * integração HTTP real via {@link SeguradoraClient}).
 */
public interface SeguradoraService {

    Cotacao cotar(CotacaoRequestDto request);

    RegistroApolice registrarApolice(RegistroApoliceDto request);

    record CotacaoRequestDto(
            String produto, String cobertura, BigDecimal valorSegurado,
            Integer idade, String uf, String sexo, String profissao) {
    }

    record Cotacao(BigDecimal premio, BigDecimal taxa, String seguradora, Boolean aceita, String observacao) {
    }

    record RegistroApoliceDto(
            String numeroApolice, Long produtoId, Long clienteId, String cobertura,
            BigDecimal valorSegurado, BigDecimal premio) {
    }

    record RegistroApolice(String protocolo, String status, String numeroApolice) {
    }
}
