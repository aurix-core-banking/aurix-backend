package com.aurix.platform.seguros.sinistro.controller;

import com.aurix.platform.seguros.sinistro.dto.DocumentoRequest;
import com.aurix.platform.seguros.sinistro.dto.SinistroRequest;
import com.aurix.platform.seguros.sinistro.dto.SinistroResponse;
import com.aurix.platform.seguros.sinistro.service.SinistroService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seguros/sinistros")
@Tag(name = "Sinistro")
public class SinistroController {

    private final SinistroService sinistroService;

    public SinistroController(SinistroService sinistroService) {
        this.sinistroService = sinistroService;
    }

    @PostMapping
    public ResponseEntity<SinistroResponse> abrir(@Valid @RequestBody SinistroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sinistroService.abrir(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SinistroResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(sinistroService.buscarPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<SinistroResponse>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(sinistroService.listarPorCliente(clienteId));
    }

    @PostMapping("/{id}/documentos")
    public ResponseEntity<Void> enviarDocumento(@PathVariable Long id,
                                                @Valid @RequestBody DocumentoRequest request) {
        var requestComId = new DocumentoRequest(id, request.tipoDocumento(),
            request.nomeArquivo(), request.caminhoArquivo(), request.descricao());
        sinistroService.enviarDocumento(requestComId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
