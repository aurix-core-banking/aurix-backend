package com.aurix.platform.cards.job;

import com.aurix.platform.cards.repository.LimiteCartaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class LimiteRecuperacaoJob {

    private final LimiteCartaoRepository limiteCartaoRepository;

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void recuperarLimites() {
        var limites = limiteCartaoRepository.findAll();
        log.info("Verificando recuperacao de limites para {} registros", limites.size());

        for (var limite : limites) {
            try {
                var utilizado = limite.getLimiteUtilizado();
                if (utilizado.compareTo(java.math.BigDecimal.ZERO) > 0) {
                    var disponivel = limite.getLimiteTotal().subtract(utilizado);
                    limite.setLimiteDisponivel(disponivel);
                    limiteCartaoRepository.save(limite);
                }
            } catch (Exception e) {
                log.error("Erro ao recuperar limiteId={}", limite.getId(), e);
            }
        }
    }
}
