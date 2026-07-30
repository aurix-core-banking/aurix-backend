package com.aurix.platform.cards.controller;

import com.aurix.platform.cards.dto.ParceiroAdquirenteRequest;
import com.aurix.platform.cards.dto.ParceiroAdquirenteResponse;
import com.aurix.platform.cards.dto.ParceiroBandeiraRequest;
import com.aurix.platform.cards.dto.ParceiroBandeiraResponse;
import com.aurix.platform.cards.service.ParceiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards/parceiros")
@Tag(name = "Parceiros Cartao", description = "Configuracao de bandeiras e adquirentes")
public class ParceiroController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ParceiroController.class);

    private final ParceiroService parceiroService;

    public ParceiroController(ParceiroService parceiroService) {
        this.parceiroService = parceiroService;
    }

    @PostMapping("/bandeiras")
    @Operation(summary = "Criar bandeira parceira")
    public ResponseEntity<ParceiroBandeiraResponse> criarBandeira(@Valid @RequestBody ParceiroBandeiraRequest request) {
        ParceiroBandeiraResponse response = parceiroService.criarBandeira(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/adquirentes")
    @Operation(summary = "Criar adquirente parceiro")
    public ResponseEntity<ParceiroAdquirenteResponse> criarAdquirente(@Valid @RequestBody ParceiroAdquirenteRequest request) {
        ParceiroAdquirenteResponse response = parceiroService.criarAdquirente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/bandeiras")
    @Operation(summary = "Listar bandeiras parceiras")
    public ResponseEntity<List<ParceiroBandeiraResponse>> listarBandeiras() {
        return ResponseEntity.ok(parceiroService.listarBandeiras());
    }

    @GetMapping("/adquirentes")
    @Operation(summary = "Listar adquirentes parceiros")
    public ResponseEntity<List<ParceiroAdquirenteResponse>> listarAdquirentes() {
        return ResponseEntity.ok(parceiroService.listarAdquirentes());
    }
}
