package com.aurix.platform.banking.controller;

import com.aurix.platform.banking.dto.WorkflowDTO;
import com.aurix.platform.banking.entity.Workflow;
import com.aurix.platform.banking.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/banking/workflows")
@CrossOrigin(origins = "*")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @GetMapping
    public ResponseEntity<List<WorkflowDTO>> listarWorkflows() {
        List<Workflow> workflows = workflowService.listarTodosWorkflows();
        List<WorkflowDTO> workflowsDTO = workflows.stream()
                .map(WorkflowDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(workflowsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowDTO> buscarWorkflowPorId(@PathVariable Long id) {
        Workflow workflow = workflowService.buscarWorkflowPorId(id);
        return ResponseEntity.ok(new WorkflowDTO(workflow));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<WorkflowDTO>> buscarWorkflowsPorEmpresa(@PathVariable Long empresaId) {
        List<Workflow> workflows = workflowService.buscarWorkflowsPorEmpresa(empresaId);
        List<WorkflowDTO> workflowsDTO = workflows.stream()
                .map(WorkflowDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(workflowsDTO);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<WorkflowDTO>> buscarWorkflowsPorTipo(@PathVariable String tipo) {
        List<Workflow> workflows = workflowService.buscarWorkflowsPorTipo(tipo);
        List<WorkflowDTO> workflowsDTO = workflows.stream()
                .map(WorkflowDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(workflowsDTO);
    }

    @PostMapping
    public ResponseEntity<WorkflowDTO> criarWorkflow(@RequestBody WorkflowDTO workflowDTO) {
        Workflow workflow = workflowService.criarWorkflow(workflowDTO);
        return ResponseEntity.ok(new WorkflowDTO(workflow));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowDTO> atualizarWorkflow(@PathVariable Long id, @RequestBody WorkflowDTO workflowDTO) {
        Workflow workflow = workflowService.atualizarWorkflow(id, workflowDTO);
        return ResponseEntity.ok(new WorkflowDTO(workflow));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirWorkflow(@PathVariable Long id) {
        workflowService.excluirWorkflow(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/ativar")
    public ResponseEntity<WorkflowDTO> ativarWorkflow(@PathVariable Long id) {
        Workflow workflow = workflowService.ativarWorkflow(id);
        return ResponseEntity.ok(new WorkflowDTO(workflow));
    }

    @PostMapping("/{id}/desativar")
    public ResponseEntity<WorkflowDTO> desativarWorkflow(@PathVariable Long id) {
        Workflow workflow = workflowService.desativarWorkflow(id);
        return ResponseEntity.ok(new WorkflowDTO(workflow));
    }

    @GetMapping("/{id}/etapas")
    public ResponseEntity<List<?>> listarEtapasWorkflow(@PathVariable Long id) {
        return ResponseEntity.ok(workflowService.listarEtapasWorkflow(id));
    }
}
