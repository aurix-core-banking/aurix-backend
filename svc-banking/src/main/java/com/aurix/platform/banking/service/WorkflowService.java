package com.aurix.platform.banking.service;

import com.aurix.platform.banking.dto.WorkflowDTO;
import com.aurix.platform.banking.entity.Empresa;
import com.aurix.platform.banking.entity.Workflow;
import com.aurix.platform.banking.repository.EmpresaRepository;
import com.aurix.platform.banking.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class WorkflowService {

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Workflow> listarTodosWorkflows() {
        return workflowRepository.findAll();
    }

    public Workflow buscarWorkflowPorId(Long id) {
        return workflowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workflow não encontrado com ID: " + id));
    }

    public List<Workflow> buscarWorkflowsPorEmpresa(Long empresaId) {
        return workflowRepository.findByEmpresaIdAndStatus(empresaId, Workflow.StatusWorkflow.ATIVO);
    }

    public List<Workflow> buscarWorkflowsPorTipo(String tipo) {
        Workflow.TipoWorkflow tipoWorkflow = Workflow.TipoWorkflow.valueOf(tipo.toUpperCase(java.util.Locale.ROOT));
        return workflowRepository.findByTipoWorkflowAndStatus(tipoWorkflow, Workflow.StatusWorkflow.ATIVO);
    }

    public Workflow criarWorkflow(WorkflowDTO workflowDTO) {
        if (workflowRepository.existsByCodigoWorkflow(workflowDTO.getCodigoWorkflow())) {
            throw new RuntimeException("Já existe um workflow com o código: " + workflowDTO.getCodigoWorkflow());
        }

        Empresa empresa = empresaRepository.findById(workflowDTO.getEmpresaId())
                .orElseThrow(
                        () -> new RuntimeException("Empresa não encontrada com ID: " + workflowDTO.getEmpresaId()));

        Workflow workflow = new Workflow();
        workflow.setCodigoWorkflow(workflowDTO.getCodigoWorkflow());
        workflow.setNomeWorkflow(workflowDTO.getNomeWorkflow());
        workflow.setDescricao(workflowDTO.getDescricao());
        workflow.setTipoWorkflow(workflowDTO.getTipoWorkflow());
        workflow.setStatus(workflowDTO.getStatus() != null ? workflowDTO.getStatus() : Workflow.StatusWorkflow.ATIVO);
        workflow.setEmpresa(empresa);
        workflow.setConfiguracoesWorkflow(workflowDTO.getConfiguracoesWorkflow());
        workflow.setRegrasWorkflow(workflowDTO.getRegrasWorkflow());
        workflow.setTimeoutHoras(workflowDTO.getTimeoutHoras() != null ? workflowDTO.getTimeoutHoras() : 24);
        workflow.setDataCriacao(LocalDateTime.now());
        workflow.setDataAtualizacao(LocalDateTime.now());

        return workflowRepository.save(workflow);
    }

    public Workflow atualizarWorkflow(Long id, WorkflowDTO workflowDTO) {
        Workflow workflow = buscarWorkflowPorId(id);

        if (!workflow.getCodigoWorkflow().equals(workflowDTO.getCodigoWorkflow()) &&
                workflowRepository.existsByCodigoWorkflow(workflowDTO.getCodigoWorkflow())) {
            throw new RuntimeException("Já existe um workflow com o código: " + workflowDTO.getCodigoWorkflow());
        }

        Empresa empresa = empresaRepository.findById(workflowDTO.getEmpresaId())
                .orElseThrow(
                        () -> new RuntimeException("Empresa não encontrada com ID: " + workflowDTO.getEmpresaId()));

        workflow.setCodigoWorkflow(workflowDTO.getCodigoWorkflow());
        workflow.setNomeWorkflow(workflowDTO.getNomeWorkflow());
        workflow.setDescricao(workflowDTO.getDescricao());
        workflow.setTipoWorkflow(workflowDTO.getTipoWorkflow());
        workflow.setStatus(workflowDTO.getStatus());
        workflow.setEmpresa(empresa);
        workflow.setConfiguracoesWorkflow(workflowDTO.getConfiguracoesWorkflow());
        workflow.setRegrasWorkflow(workflowDTO.getRegrasWorkflow());
        workflow.setTimeoutHoras(workflowDTO.getTimeoutHoras());
        workflow.setDataAtualizacao(LocalDateTime.now());

        return workflowRepository.save(workflow);
    }

    public void excluirWorkflow(Long id) {
        Workflow workflow = buscarWorkflowPorId(id);
        workflow.setStatus(Workflow.StatusWorkflow.INATIVO);
        workflow.setDataAtualizacao(LocalDateTime.now());
        workflowRepository.save(workflow);
    }

    public Workflow ativarWorkflow(Long id) {
        Workflow workflow = buscarWorkflowPorId(id);
        workflow.setStatus(Workflow.StatusWorkflow.ATIVO);
        workflow.setDataAtualizacao(LocalDateTime.now());
        return workflowRepository.save(workflow);
    }

    public Workflow desativarWorkflow(Long id) {
        Workflow workflow = buscarWorkflowPorId(id);
        workflow.setStatus(Workflow.StatusWorkflow.INATIVO);
        workflow.setDataAtualizacao(LocalDateTime.now());
        return workflowRepository.save(workflow);
    }

    private record EtapaWorkflowView(Long id, String nomeEtapa, String descricao, Integer ordemEtapa,
            String tipoAprovacao, String status) {
    }

    public List<EtapaWorkflowView> listarEtapasWorkflow(Long id) {
        Workflow workflow = buscarWorkflowPorId(id);
        return workflow.getEtapas().stream()
                .map(etapa -> new EtapaWorkflowView(
                        etapa.getId(),
                        etapa.getNomeEtapa(),
                        etapa.getDescricao(),
                        etapa.getOrdemEtapa(),
                        etapa.getTipoAprovacao().toString(),
                        etapa.getStatus().toString()))
                .collect(java.util.stream.Collectors.toList());
    }
}
