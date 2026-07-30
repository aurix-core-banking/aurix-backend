package com.aurix.platform.banking.controller;

import com.aurix.platform.banking.dto.EmpresaDTO;
import com.aurix.platform.banking.entity.Empresa;
import com.aurix.platform.banking.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/banking/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {
    
    @Autowired
    private EmpresaService empresaService;
    
    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> listarEmpresas() {
        List<Empresa> empresas = empresaService.listarTodasEmpresas();
        List<EmpresaDTO> empresasDTO = empresas.stream()
                .map(EmpresaDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(empresasDTO);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> buscarEmpresaPorId(@PathVariable Long id) {
        Empresa empresa = empresaService.buscarEmpresaPorId(id);
        return ResponseEntity.ok(new EmpresaDTO(empresa));
    }
    
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<EmpresaDTO> buscarEmpresaPorCodigo(@PathVariable String codigo) {
        Empresa empresa = empresaService.buscarEmpresaPorCodigo(codigo);
        return ResponseEntity.ok(new EmpresaDTO(empresa));
    }
    
    @PostMapping
    public ResponseEntity<EmpresaDTO> criarEmpresa(@RequestBody EmpresaDTO empresaDTO) {
        Empresa empresa = empresaService.criarEmpresa(empresaDTO);
        return ResponseEntity.ok(new EmpresaDTO(empresa));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDTO> atualizarEmpresa(@PathVariable Long id, @RequestBody EmpresaDTO empresaDTO) {
        Empresa empresa = empresaService.atualizarEmpresa(id, empresaDTO);
        return ResponseEntity.ok(new EmpresaDTO(empresa));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEmpresa(@PathVariable Long id) {
        empresaService.excluirEmpresa(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/ativas")
    public ResponseEntity<List<EmpresaDTO>> listarEmpresasAtivas() {
        List<Empresa> empresas = empresaService.listarEmpresasAtivas();
        List<EmpresaDTO> empresasDTO = empresas.stream()
                .map(EmpresaDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(empresasDTO);
    }
}
