package com.aurix.platform.seguros.integracao;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Implementação simulada da integração com a SUSEP. Gera protocolo e número de
 * registro fictícios, ativa nos perfis dev/test/stub.
 */
@Service
@Profile({"dev", "test", "stub"})
public class SusepStub implements SusepService {

    private static final Logger log = LoggerFactory.getLogger(SusepStub.class);

    @Override
    public Registro registarApolice(RegistroApoliceDto apolice) {
        log.debug("Susep stub: registro apólice {}", apolice.numeroApolice());
        return new Registro(
                "SUSEP-" + apolice.numeroApolice(),
                "REGISTRADA",
                ThreadLocalRandom.current().nextLong(1000, 9999));
    }

    @Override
    public Comunicacao comunicarSinistro(Long sinistroId, BigDecimal valor, String tipo) {
        log.debug("Susep stub: comunicação de sinistro {}", sinistroId);
        return new Comunicacao(sinistroId, "SUSEP-SIN-" + sinistroId, "COMUNICADO");
    }
}
