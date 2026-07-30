package com.aurix.platform.credit.consignado.controller;

import com.aurix.platform.credit.consignado.dto.ContratoConsignadoResponse;
import com.aurix.platform.credit.consignado.dto.CriarContratoRequest;
import com.aurix.platform.credit.consignado.dto.LiquidarRequest;
import com.aurix.platform.credit.consignado.dto.ParcelaResponse;
import com.aurix.platform.credit.consignado.dto.RenegociarRequest;
import com.aurix.platform.credit.consignado.service.ContratoConsignadoService;
import com.aurix.platform.credit.consignado.service.ParcelaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consignado/consignados")
@Tag(name = "Credito Consignado")
public class ConsignadoController {

    private final ContratoConsignadoService contratoService;
    private final ParcelaService parcelaService;

    public ConsignadoController(ContratoConsignadoService contratoService, ParcelaService parcelaService) {
        this.contratoService = contratoService;
        this.parcelaService = parcelaService;
    }

    @PostMapping("/contratos")
    public ResponseEntity<ContratoConsignadoResponse> criarContrato(@Valid @RequestBody CriarContratoRequest request) {
        var response = contratoService.criarContrato(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/contratos/{id}")
    public ResponseEntity<ContratoConsignadoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contratoService.buscarPorId(id));
    }

    @GetMapping("/contratos/cliente/{clienteId}")
    public ResponseEntity<List<ContratoConsignadoResponse>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(contratoService.listarPorCliente(clienteId));
    }

    @GetMapping("/contratos/{id}/parcelas")
    public ResponseEntity<List<ParcelaResponse>> listarParcelas(@PathVariable Long id) {
        return ResponseEntity.ok(parcelaService.listarParcelas(id));
    }

    @PostMapping("/contratos/{id}/liquidar")
    public ResponseEntity<ContratoConsignadoResponse> liquidar(@PathVariable Long id,
                                                               @RequestBody(required = false) LiquidarRequest request) {
        return ResponseEntity.ok(contratoService.liquidar(id));
    }

    @PatchMapping("/contratos/{id}/renegociar")
    public ResponseEntity<ContratoConsignadoResponse> renegociar(@PathVariable Long id,
                                                                  @Valid @RequestBody RenegociarRequest request) {
        return ResponseEntity.ok(contratoService.renegociar(id, request));
    }
}
