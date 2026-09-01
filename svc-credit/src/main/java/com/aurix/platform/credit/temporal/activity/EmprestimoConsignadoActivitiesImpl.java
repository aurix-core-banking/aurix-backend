package com.aurix.platform.credit.temporal.activity;

import com.aurix.platform.credit.consignado.dto.CriarContratoRequest;
import com.aurix.platform.credit.consignado.entity.ContratoConsignado;
import com.aurix.platform.credit.consignado.repository.ContratoConsignadoRepository;
import com.aurix.platform.credit.consignado.service.ContratoConsignadoService;
import com.aurix.platform.credit.consignado.service.MargemService;
import com.aurix.platform.credit.consignado.service.ParcelaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class EmprestimoConsignadoActivitiesImpl implements EmprestimoConsignadoActivities {

    private static final Logger log = LoggerFactory.getLogger(EmprestimoConsignadoActivitiesImpl.class);

    private static final String FONTE_MARGEM_PADRAO = "DEFAULT";

    private final MargemService margemService;
    private final ContratoConsignadoService contratoService;
    private final ParcelaService parcelaService;
    private final ContratoConsignadoRepository contratoRepository;

    public EmprestimoConsignadoActivitiesImpl(MargemService margemService,
                                               ContratoConsignadoService contratoService,
                                               ParcelaService parcelaService,
                                               ContratoConsignadoRepository contratoRepository) {
        this.margemService = margemService;
        this.contratoService = contratoService;
        this.parcelaService = parcelaService;
        this.contratoRepository = contratoRepository;
    }

    @Override
    public ResultadoAnalise analisarCredito(String clienteId, BigDecimal valorSolicitado) {
        log.info("Activity: Análise de crédito para cliente {}, valor {}", clienteId, valorSolicitado);
        try {
            margemService.validarMargemDisponivel(Long.parseLong(clienteId), valorSolicitado);
            return new ResultadoAnalise(true, "Crédito aprovado", null, null);
        } catch (Exception e) {
            return new ResultadoAnalise(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoAnalise validarMargem(String clienteId, BigDecimal valorSolicitado) {
        log.info("Activity: Validando margem consignada para cliente {}", clienteId);
        try {
            var margem = margemService.consultarMargem(Long.parseLong(clienteId));
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
            CriarContratoRequest request = new CriarContratoRequest(
                    Long.parseLong(clienteId),
                    Long.parseLong(convenioId),
                    valor,
                    taxaJuros,
                    prazoMeses,
                    FONTE_MARGEM_PADRAO);
            var contrato = contratoService.criarContrato(request);
            return new ResultadoAnalise(true, "Contrato criado", contrato.getId().toString(), null);
        } catch (Exception e) {
            return new ResultadoAnalise(false, "Erro: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResultadoAnalise gerarParcelas(String contratoId, BigDecimal valorTotal, Integer parcelas) {
        log.info("Activity: Gerando {} parcelas para contrato {}", parcelas, contratoId);
        try {
            ContratoConsignado contrato = contratoRepository.findById(Long.parseLong(contratoId))
                    .orElseThrow(() -> new IllegalArgumentException("Contrato não encontrado: " + contratoId));
            parcelaService.gerarParcelas(contrato);
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
