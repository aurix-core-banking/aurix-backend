package com.aurix.platform.banking.salario.controller;

import com.aurix.platform.banking.salario.dto.ContaSalarioRequest;
import com.aurix.platform.banking.salario.dto.ContaSalarioResponse;
import com.aurix.platform.banking.salario.service.ContaSalarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/salario/contas")
public class ContaSalarioController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ContaSalarioController.class);
    private final ContaSalarioService contaSalarioService;

    public ContaSalarioController(ContaSalarioService contaSalarioService) {
        this.contaSalarioService = contaSalarioService;
    }

    @PostMapping
    public ResponseEntity<ContaSalarioResponse> criarConta(@Valid @RequestBody ContaSalarioRequest request) {
        log.info("Criando conta salario para matricula: {}", request.getMatriculaFuncionario());
        ContaSalarioResponse response = contaSalarioService.criarConta(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaSalarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contaSalarioService.buscarPorId(id));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<ContaSalarioResponse>> listarPorEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(contaSalarioService.listarPorEmpresa(empresaId));
    }

    @PatchMapping("/{id}/bloquear")
    public ResponseEntity<Void> bloquearConta(@PathVariable Long id) {
        contaSalarioService.bloquearConta(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/rescindir")
    public ResponseEntity<Void> rescindirConta(@PathVariable Long id) {
        contaSalarioService.rescindirConta(id);
        return ResponseEntity.noContent().build();
    }
}
