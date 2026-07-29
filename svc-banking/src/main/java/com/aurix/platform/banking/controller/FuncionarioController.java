package com.aurix.platform.banking.controller;

import com.aurix.platform.banking.dto.FuncionarioDTO;
import com.aurix.platform.banking.entity.Funcionario;
import com.aurix.platform.banking.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/banking/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {
    
    @Autowired
    private FuncionarioService funcionarioService;
    
    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> listarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.listarTodosFuncionarios();
        List<FuncionarioDTO> funcionariosDTO = funcionarios.stream()
                .map(FuncionarioDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(funcionariosDTO);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> buscarFuncionarioPorId(@PathVariable Long id) {
        Funcionario funcionario = funcionarioService.buscarFuncionarioPorId(id);
        return ResponseEntity.ok(new FuncionarioDTO(funcionario));
    }
    
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<FuncionarioDTO> buscarFuncionarioPorMatricula(@PathVariable String matricula) {
        Funcionario funcionario = funcionarioService.buscarFuncionarioPorMatricula(matricula);
        return ResponseEntity.ok(new FuncionarioDTO(funcionario));
    }
    
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<FuncionarioDTO>> buscarFuncionariosPorEmpresa(@PathVariable Long empresaId) {
        List<Funcionario> funcionarios = funcionarioService.buscarFuncionariosPorEmpresa(empresaId);
        List<FuncionarioDTO> funcionariosDTO = funcionarios.stream()
                .map(FuncionarioDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(funcionariosDTO);
    }
    
    @GetMapping("/departamento/{departamentoId}")
    public ResponseEntity<List<FuncionarioDTO>> buscarFuncionariosPorDepartamento(@PathVariable Long departamentoId) {
        List<Funcionario> funcionarios = funcionarioService.buscarFuncionariosPorDepartamento(departamentoId);
        List<FuncionarioDTO> funcionariosDTO = funcionarios.stream()
                .map(FuncionarioDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(funcionariosDTO);
    }
    
    @GetMapping("/cargo/{cargoId}")
    public ResponseEntity<List<FuncionarioDTO>> buscarFuncionariosPorCargo(@PathVariable Long cargoId) {
        List<Funcionario> funcionarios = funcionarioService.buscarFuncionariosPorCargo(cargoId);
        List<FuncionarioDTO> funcionariosDTO = funcionarios.stream()
                .map(FuncionarioDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(funcionariosDTO);
    }
    
    @GetMapping("/gestor/{gestorId}")
    public ResponseEntity<List<FuncionarioDTO>> buscarSubordinados(@PathVariable Long gestorId) {
        List<Funcionario> funcionarios = funcionarioService.buscarSubordinados(gestorId);
        List<FuncionarioDTO> funcionariosDTO = funcionarios.stream()
                .map(FuncionarioDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(funcionariosDTO);
    }
    
    @PostMapping
    public ResponseEntity<FuncionarioDTO> criarFuncionario(@RequestBody FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = funcionarioService.criarFuncionario(funcionarioDTO);
        return ResponseEntity.ok(new FuncionarioDTO(funcionario));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> atualizarFuncionario(@PathVariable Long id, @RequestBody FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = funcionarioService.atualizarFuncionario(id, funcionarioDTO);
        return ResponseEntity.ok(new FuncionarioDTO(funcionario));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirFuncionario(@PathVariable Long id) {
        funcionarioService.excluirFuncionario(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/ativar")
    public ResponseEntity<FuncionarioDTO> ativarFuncionario(@PathVariable Long id) {
        Funcionario funcionario = funcionarioService.ativarFuncionario(id);
        return ResponseEntity.ok(new FuncionarioDTO(funcionario));
    }
    
    @PostMapping("/{id}/desativar")
    public ResponseEntity<FuncionarioDTO> desativarFuncionario(@PathVariable Long id) {
        Funcionario funcionario = funcionarioService.desativarFuncionario(id);
        return ResponseEntity.ok(new FuncionarioDTO(funcionario));
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<FuncionarioDTO>> buscarFuncionarios(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long empresaId,
            @RequestParam(required = false) Long departamentoId,
            @RequestParam(required = false) String status) {
        List<Funcionario> funcionarios = funcionarioService.buscarFuncionarios(nome, empresaId, departamentoId, status);
        List<FuncionarioDTO> funcionariosDTO = funcionarios.stream()
                .map(FuncionarioDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(funcionariosDTO);
    }
}
