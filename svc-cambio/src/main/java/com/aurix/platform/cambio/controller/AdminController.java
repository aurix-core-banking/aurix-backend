package com.aurix.platform.cambio.controller;

import com.aurix.platform.cambio.dto.AtualizarLimiteRequest;
import com.aurix.platform.cambio.dto.ClienteCambioRequest;
import com.aurix.platform.cambio.dto.ClienteCambioResponse;
import com.aurix.platform.cambio.dto.LimiteCambioResponse;
import com.aurix.platform.cambio.dto.OperacaoCambioResponse;
import com.aurix.platform.cambio.entity.OperacaoCambio;
import com.aurix.platform.cambio.repository.OperacaoCambioRepository;
import com.aurix.platform.cambio.service.ClienteCambioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cambio/clientes")
@Tag(name = "Admin Cambio")
public class AdminController {

    private final ClienteCambioService clienteCambioService;
    private final OperacaoCambioRepository operacaoCambioRepository;

    public AdminController(ClienteCambioService clienteCambioService,
                           OperacaoCambioRepository operacaoCambioRepository) {
        this.clienteCambioService = clienteCambioService;
        this.operacaoCambioRepository = operacaoCambioRepository;
    }

    @GetMapping
    public ResponseEntity<List<ClienteCambioResponse>> listarClientes() {
        return ResponseEntity.ok(clienteCambioService.listarClientes());
    }

    @PostMapping
    public ResponseEntity<ClienteCambioResponse> habilitarCliente(@Valid @RequestBody ClienteCambioRequest request) {
        var response = clienteCambioService.habilitarCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/cliente/{clienteId}/limites")
    public ResponseEntity<LimiteCambioResponse> consultarLimites(@PathVariable Long clienteId) {
        return ResponseEntity.ok(clienteCambioService.consultarLimites(clienteId));
    }

    @GetMapping("/cliente/{clienteId}/operacoes")
    public ResponseEntity<List<OperacaoCambioResponse>> listarOperacoes(@PathVariable Long clienteId) {
        List<OperacaoCambio> operacoes = operacaoCambioRepository.findByClienteId(clienteId);
        var response = operacoes.stream().map(o -> new OperacaoCambioResponse(
            o.getId(), o.getContratoId(), o.getClienteId(), o.getTipo(),
            o.getValorMoedaEstrangeira(), o.getValorMoedaNacional(), o.getTaxa(),
            o.getDataOperacao(), o.getRegistroBACEN()
        )).toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/limites")
    public ResponseEntity<Void> ajustarLimites(@PathVariable Long id,
                                                @Valid @RequestBody AtualizarLimiteRequest request) {
        clienteCambioService.ajustarLimites(id, request);
        return ResponseEntity.ok().build();
    }
}
