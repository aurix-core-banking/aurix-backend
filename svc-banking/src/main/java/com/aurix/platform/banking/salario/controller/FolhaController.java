package com.aurix.platform.banking.salario.controller;

import com.aurix.platform.banking.salario.dto.CreditoDiretoRequest;
import com.aurix.platform.banking.salario.dto.FolhaResponse;
import com.aurix.platform.banking.salario.dto.ItemFolhaResponse;
import com.aurix.platform.banking.salario.entity.FolhaPagamento;
import com.aurix.platform.banking.salario.entity.ItemFolhaPagamento;
import com.aurix.platform.banking.salario.repository.FolhaPagamentoRepository;
import com.aurix.platform.banking.salario.repository.ItemFolhaPagamentoRepository;
import com.aurix.platform.banking.salario.service.CnabService;
import com.aurix.platform.banking.salario.service.FolhaService;
import com.aurix.platform.shared.tenant.TenantContext;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/salario/folhas")
public class FolhaController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FolhaController.class);

    private final CnabService cnabService;
    private final FolhaService folhaService;
    private final FolhaPagamentoRepository folhaRepository;
    private final ItemFolhaPagamentoRepository itemRepository;

    public FolhaController(CnabService cnabService, FolhaService folhaService,
                           FolhaPagamentoRepository folhaRepository,
                           ItemFolhaPagamentoRepository itemRepository) {
        this.cnabService = cnabService;
        this.folhaService = folhaService;
        this.folhaRepository = folhaRepository;
        this.itemRepository = itemRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<FolhaResponse> uploadCnab(@RequestParam("arquivo") MultipartFile arquivo) throws IOException {
        log.info("Recebendo upload CNAB: {}", arquivo.getOriginalFilename());

        FolhaPagamento folha = cnabService.processarUpload(
            arquivo.getOriginalFilename(), arquivo.getInputStream());

        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaResponse(folha));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FolhaResponse> buscarPorId(@PathVariable Long id) {
        log.info("Buscando folha por ID: {}", id);

        FolhaPagamento folha = folhaRepository.findByTenantIdAndId(
            TenantContext.getTenantId(), id)
            .orElseThrow(() -> new IllegalArgumentException("Folha nao encontrada: " + id));

        return ResponseEntity.ok(converterParaResponse(folha));
    }

    @GetMapping
    public ResponseEntity<List<FolhaResponse>> listarFolhas() {
        log.info("Listando folhas");

        List<FolhaPagamento> folhas = folhaRepository.findByTenantId(
            TenantContext.getTenantId());

        return ResponseEntity.ok(folhas.stream().map(this::converterParaResponse).toList());
    }

    @GetMapping("/{id}/itens")
    public ResponseEntity<List<ItemFolhaResponse>> listarItens(@PathVariable Long id) {
        log.info("Listando itens da folha: {}", id);

        FolhaPagamento folha = folhaRepository.findByTenantIdAndId(
            TenantContext.getTenantId(), id)
            .orElseThrow(() -> new IllegalArgumentException("Folha nao encontrada: " + id));

        List<ItemFolhaPagamento> itens = itemRepository.findByFolhaId(folha.getId());

        return ResponseEntity.ok(itens.stream().map(this::converterItemParaResponse).toList());
    }

    @PostMapping("/credito-direto")
    public ResponseEntity<Void> creditoDireto(@Valid @RequestBody CreditoDiretoRequest request) {
        log.info("Credito direto para CPF: {}", request.getCpfFuncionario());

        folhaService.creditarDireto(request);

        return ResponseEntity.ok().build();
    }

    private FolhaResponse converterParaResponse(FolhaPagamento folha) {
        FolhaResponse response = new FolhaResponse();
        response.setId(folha.getId());
        response.setEmpresaId(folha.getEmpresaId());
        response.setArquivoNome(folha.getArquivoNome());
        response.setTotalFuncionarios(folha.getTotalFuncionarios());
        response.setValorTotal(folha.getValorTotal());
        response.setDataReferencia(folha.getDataReferencia());
        response.setDataProcessamento(folha.getDataProcessamento());
        response.setStatus(folha.getStatus());
        response.setDataCriacao(folha.getDataCriacao());
        return response;
    }

    private ItemFolhaResponse converterItemParaResponse(ItemFolhaPagamento item) {
        ItemFolhaResponse response = new ItemFolhaResponse();
        response.setId(item.getId());
        response.setFolhaId(item.getFolhaId());
        response.setContaSalarioId(item.getContaSalarioId());
        response.setCpfFuncionario(item.getCpfFuncionario());
        response.setValorLiquido(item.getValorLiquido());
        response.setDescontos(item.getDescontos());
        response.setStatus(item.getStatus());
        return response;
    }
}
