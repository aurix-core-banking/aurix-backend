package com.aurix.platform.credit.temporal.activity;

import com.aurix.platform.credit.service.ContratoConsignadoService;
import com.aurix.platform.credit.service.MargemService;
import com.aurix.platform.credit.service.ParcelaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class EmprestimoConsignadoActivitiesImpl implements EmprestimoConsignadoActivities {

    private static final Logger log = LoggerFactory.getLogger(EmprestimoConsignadoActivitiesImpl.class);

    private final MargemService margemService;
    private final ContratoConsignadoService contratoService;
    private final ParcelaService parcelaService;

    public EmprestimoConsignadoActivitiesImpl(MargemService margemService,
                                               ContratoConsignadoService contratoService,
                                               ParcelaService parcelaService) {
        this.margemService = margemService;
        this.contratoService = contratoService;
        this.parcelaService = parcelaService;
    }

    @Override
    public ResultadoAnalise analisarCredito(String clienteId, BigDecimal valorSolicitado) {
        log.info("Activity: Análise de crédito para cliente {}, valor {}", clienteId, valorSolicitado);
        try {
            boolean aprovado = margemService.validarMargemDisponivel(clienteId, valorSolicitado);
            return new ResultadoAnalise(aprovado, aprovado ? "Crédito aprovado" : "Margem insuficiente", null, null);
        } catch (Exception e) {
            return new ResultadoAnalise(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoAnalise validarMargem(String clienteId, BigDecimal valorSolicitado) {
        log.info("Activity: Validando margem consignada para cliente {}", clienteId);
        try {
            var margem = margemService.consultarMargem(clienteId);
            boolean temMargem = margem.getMargemDisponivel().compareTo(valorSolicitado) >= 0;
            return new ResultadoAnalise(temMargem,
                    temMargem ? "Margem OK" : "Margem disponível: " + margem.getMargemDisponivel(),
                    null, null);
        } catch (Exception e) {
            return new ResultadoAnalise(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoAnalise verificarGarantia(String emprestimoId, String tipoGarantia) {
        log.info("Activity: Verificando garantia {} para empréstimo {}", tipoGarantia, emprestimoId);
        try {
            // Consignado não exige garantia adicional — a garantia é o desconto em folha
            return new ResultadoAnalise(true, "Garantia consignada validada", null, null);
        } catch (Exception e) {
            return new ResultadoAnalise(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoAnalise criarContrato(String clienteId, BigDecimal valor, Integer prazoMeses,
                                           BigDecimal taxaJuros, String convenioId) {
        log.info("Activity: Criando contrato consignado para cliente {}, valor {}, {} meses",
                clienteId, valor, prazoMeses);
        try {
            var contrato = contratoService.criarContrato(clienteId, valor, prazoMeses, taxaJuros, convenioId);
            return new ResultadoAnalise(true, "Contrato criado", contrato.getId().toString(), null);
        } catch (Exception e) {
            return new ResultadoAnalise(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoAnalise gerarParcelas(String contratoId, BigDecimal valorTotal, Integer parcelas) {
        log.info("Activity: Gerando {} parcelas para contrato {}", parcelas, contratoId);
        try {
            parcelaService.gerarParcelas(Long.parseLong(contratoId), parcelas);
            return new ResultadoAnalise(true, "Parcelas geradas", null, null);
        } catch (Exception e) {
            return new ResultadoAnalise(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoAnalise liquidar(String contratoId) {
        log.info("Activity: Liquidando contrato {}", contratoId);
        try {
            contratoService.liquidar(Long.parseLong(contratoId));
            return new ResultadoAnalise(true, "Contrato liquidado", null, null);
        } catch (Exception e) {
            return new ResultadoAnalise(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public void publicarEvento(String topico, String chave, String payload) {
        log.info("Activity: Publicando evento no tópico {}", topico);
    }
}
