package com.aurix.platform.banking.salario.controller;

import com.aurix.platform.banking.salario.dto.PortabilidadeRequest;
import com.aurix.platform.banking.salario.dto.PortabilidadeResponse;
import com.aurix.platform.banking.salario.entity.SolicitacaoPortabilidade;
import com.aurix.platform.banking.salario.repository.SolicitacaoPortabilidadeRepository;
import com.aurix.platform.banking.salario.service.PortabilidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/salario/portabilidade")
public class PortabilidadeController {

    private final PortabilidadeService portabilidadeService;
    private final SolicitacaoPortabilidadeRepository solicitacaoRepository;

    public PortabilidadeController(PortabilidadeService portabilidadeService,
                                   SolicitacaoPortabilidadeRepository solicitacaoRepository) {
        this.portabilidadeService = portabilidadeService;
        this.solicitacaoRepository = solicitacaoRepository;
    }

    @PostMapping
    public ResponseEntity<PortabilidadeResponse> solicitar(@Valid @RequestBody PortabilidadeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(portabilidadeService.solicitar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortabilidadeResponse> buscarPorId(@PathVariable Long id) {
        SolicitacaoPortabilidade s = solicitacaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Portabilidade nao encontrada: " + id));
        return ResponseEntity.ok(converterParaResponse(s));
    }

    @GetMapping("/conta/{contaSalarioId}")
    public ResponseEntity<List<PortabilidadeResponse>> listarPorConta(@PathVariable Long contaSalarioId) {
        return ResponseEntity.ok(portabilidadeService.listarPorConta(contaSalarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        portabilidadeService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    private PortabilidadeResponse converterParaResponse(SolicitacaoPortabilidade s) {
        PortabilidadeResponse resp = new PortabilidadeResponse();
        resp.setId(s.getId());
        resp.setContaSalarioId(s.getContaSalarioId());
        resp.setCodigoBancoDestino(s.getCodigoBancoDestino());
        resp.setAgenciaDestino(s.getAgenciaDestino());
        resp.setContaDestino(s.getContaDestino());
        resp.setValorPercentual(s.getValorPercentual());
        resp.setStatus(s.getStatus());
        resp.setDataSolicitacao(s.getDataSolicitacao());
        resp.setDataCriacao(s.getDataCriacao());
        return resp;
    }
}
