package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.core.dto.CalculoTarifaDTO;
import com.aurix.platform.banking.core.dto.TarifaDTO;
import com.aurix.platform.banking.core.entity.CobrancaTarifa;
import com.aurix.platform.banking.core.service.MotorTarifasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/motor-tarifas")
@CrossOrigin(origins = "*")
public class MotorTarifasController {

    @Autowired
    private MotorTarifasService motorTarifasService;

    @PostMapping("/calcular")
    public ResponseEntity<CalculoTarifaDTO> calcularTarifa(@RequestBody CalculoTarifaDTO calculoRequest) {
        try {
            CalculoTarifaDTO resultado = motorTarifasService.calcularTarifa(calculoRequest);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            calculoRequest.setAplicavel(false);
            calculoRequest.setMotivoNaoAplicavel("Erro no cálculo: " + e.getMessage());
            return ResponseEntity.badRequest().body(calculoRequest);
        } catch (Exception e) {
            calculoRequest.setAplicavel(false);
            calculoRequest.setMotivoNaoAplicavel("Erro inesperado: " + e.getMessage());
            return ResponseEntity.badRequest().body(calculoRequest);
        }
    }

    @PostMapping("/cobrar")
    public ResponseEntity<CobrancaTarifa> cobrarTarifa(@RequestBody CalculoTarifaDTO calculoRequest) {
        try {
            CobrancaTarifa cobranca = motorTarifasService.cobrarTarifa(calculoRequest);
            return ResponseEntity.ok(cobranca);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/tarifas-disponiveis")
    public ResponseEntity<List<TarifaDTO>> listarTarifasDisponiveis(
            @RequestParam(required = false) String tipoTarifa,
            @RequestParam(required = false) Integer nivelServico) {
        try {
            List<TarifaDTO> tarifas = motorTarifasService.listarTarifasDisponiveis(tipoTarifa, nivelServico);
            return ResponseEntity.ok(tarifas);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/simular")
    public ResponseEntity<Map<String, Object>> simularTarifa(
            @RequestParam BigDecimal valorTransacao,
            @RequestParam String tipoTarifa,
            @RequestParam Integer nivelServico) {
        try {
            BigDecimal valorTarifa = motorTarifasService.simularTarifa(valorTransacao, tipoTarifa, nivelServico);
            return ResponseEntity.ok(Map.of(
                    "valorTransacao", valorTransacao,
                    "tipoTarifa", tipoTarifa,
                    "nivelServico", nivelServico,
                    "valorTarifa", valorTarifa,
                    "sucesso", true));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "erro", e.getMessage(),
                    "sucesso", false));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "erro", "Erro inesperado: " + e.getMessage(),
                    "sucesso", false));
        }
    }

    @GetMapping("/tipos-tarifa")
    public ResponseEntity<Map<String, Object>> listarTiposTarifa() {
        try {
            Map<String, Object> tipos = Map.of(
                    "tipos", List.of(
                            "TRANSFERENCIA_PIX",
                            "TRANSFERENCIA_TED",
                            "TRANSFERENCIA_DOC",
                            "SAQUE_ATM",
                            "SAQUE_AGENCIA",
                            "DEPOSITO",
                            "CONSULTA_SALDO",
                            "EXTRATO",
                            "CARTAO_CREDITO",
                            "CARTAO_DEBITO",
                            "INVESTIMENTO",
                            "EMPRESTIMO",
                            "FINANCIAMENTO",
                            "SEGURO",
                            "OUTROS"),
                    "categorias", List.of(
                            "TRANSACIONAL",
                            "MANUTENCAO",
                            "CREDITO",
                            "INVESTIMENTO",
                            "SEGURO",
                            "TRIBUTARIA",
                            "OUTROS"),
                    "unidades", List.of(
                            "VALOR_FIXO",
                            "PERCENTUAL",
                            "PERCENTUAL_COM_MINIMO",
                            "PERCENTUAL_COM_MAXIMO",
                            "PERCENTUAL_COM_MIN_MAX",
                            "VALOR_POR_OPERACAO",
                            "VALOR_POR_DIA",
                            "VALOR_POR_MES",
                            "VALOR_POR_ANO"));
            return ResponseEntity.ok(tipos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/niveis-servico")
    public ResponseEntity<Map<String, Object>> listarNiveisServico() {
        try {
            Map<String, Object> niveis = Map.of(
                    "niveis", List.of(1, 2, 3, 4, 5),
                    "descricoes", Map.of(
                            1, "Básico",
                            2, "Intermediário",
                            3, "Premium",
                            4, "Executivo",
                            5, "Empresarial"));
            return ResponseEntity.ok(niveis);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
