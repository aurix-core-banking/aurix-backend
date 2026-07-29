package com.aurix.platform.credit.financiamento.job;

import com.aurix.platform.credit.financiamento.entity.StatusContrato;
import com.aurix.platform.credit.financiamento.entity.StatusParcela;
import com.aurix.platform.credit.financiamento.repository.ContratoFinanciamentoRepository;
import com.aurix.platform.credit.financiamento.repository.ParcelaFinanciamentoRepository;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("processamentoParcelasFinanciamentoJob")
public class ProcessamentoParcelasJob {

    private static final Logger log = LoggerFactory.getLogger(ProcessamentoParcelasJob.class);

    private static final long DIAS_INADIMPLENCIA = 90;

    private final ContratoFinanciamentoRepository contratoRepository;
    private final ParcelaFinanciamentoRepository parcelaRepository;

    public ProcessamentoParcelasJob(ContratoFinanciamentoRepository contratoRepository,
                                    ParcelaFinanciamentoRepository parcelaRepository) {
        this.contratoRepository = contratoRepository;
        this.parcelaRepository = parcelaRepository;
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void processarParcelasVencidas() {
        log.info("Iniciando processamento de parcelas vencidas...");

        var contratos = contratoRepository.findByTenantIdAndStatus("DEFAULT", StatusContrato.ATIVO);
        for (var contrato : contratos) {
            var parcelas = parcelaRepository.findByContratoIdAndStatus(contrato.getId(), StatusParcela.PENDENTE);
            boolean temInadimplencia = false;

            for (var parcela : parcelas) {
                if (!parcela.getDataVencimento().isAfter(LocalDate.now())) {
                    parcela.setStatus(StatusParcela.ATRASADA);
                    parcelaRepository.save(parcela);
                    log.warn("Parcela {} do contrato {} em atraso", parcela.getNumero(), contrato.getId());

                    var diasAtraso = parcela.getDataVencimento().until(LocalDate.now()).getDays();
                    if (diasAtraso >= DIAS_INADIMPLENCIA) {
                        temInadimplencia = true;
                    }
                }
            }

            if (temInadimplencia) {
                contrato.setStatus(StatusContrato.INADIMPLENTE);
                contratoRepository.save(contrato);
                log.warn("Contrato {} marcado como INADIMPLENTE", contrato.getId());
            }
        }

        log.info("Processamento de parcelas vencidas concluído.");
    }
}
