package com.aurix.platform.cards.job;

import com.aurix.platform.cards.repository.CartaoRepository;
import com.aurix.platform.cards.service.FaturaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class FaturaFechamentoJob {

    private final CartaoRepository cartaoRepository;
    private final FaturaService faturaService;

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void atualizarFaturasVencidas() {
        int atualizadas = faturaService.atualizarFaturasVencidas();
        if (atualizadas > 0) {
            log.info("{} faturas marcadas como VENCIDA", atualizadas);
        }
    }

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void fecharFaturasVencidas() {
        var hoje = LocalDate.now();
        var cartoes = cartaoRepository.findAll();
        log.info("Iniciando fechamento de faturas para {} cartoes", cartoes.size());

        for (var cartao : cartoes) {
            try {
                if (cartao.getDiaVencimentoFatura() == hoje.getDayOfMonth()) {
                    var mes = hoje.getMonthValue();
                    var ano = hoje.getYear();
                    faturaService.fecharFatura(cartao.getId(), mes, ano);
                    log.info("Fatura fechada: cartaoId={}, ref={}/{}", cartao.getId(), mes, ano);
                }
            } catch (Exception e) {
                log.error("Erro ao fechar fatura do cartaoId={}", cartao.getId(), e);
            }
        }
    }
}
