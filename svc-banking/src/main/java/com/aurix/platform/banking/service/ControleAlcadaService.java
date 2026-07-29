package com.aurix.platform.banking.service;

import com.aurix.platform.banking.dto.SolicitacaoAprovacaoDTO;
import com.aurix.platform.banking.entity.*;
import com.aurix.platform.banking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ControleAlcadaService {

    @Autowired
    private SolicitacaoAprovacaoRepository solicitacaoRepository;

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private AprovacaoRepository aprovacaoRepository;

    @Autowired
    private EtapaWorkflowRepository etapaRepository;

    public SolicitacaoAprovacao criarSolicitacaoAprovacao(SolicitacaoAprovacaoDTO solicitacaoDTO) {
        Workflow workflow = workflowRepository.findById(solicitacaoDTO.getWorkflowId())
                .orElseThrow(() -> new RuntimeException("Workflow não encontrado"));

        Funcionario solicitante = funcionarioRepository.findById(solicitacaoDTO.getSolicitanteId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        if (workflow.getStatus() != Workflow.StatusWorkflow.ATIVO) {
            throw new RuntimeException("Workflow não está ativo");
        }

        String codigoSolicitacao = gerarCodigoSolicitacao();

        SolicitacaoAprovacao solicitacao = new SolicitacaoAprovacao();
        solicitacao.setCodigoSolicitacao(codigoSolicitacao);
        solicitacao.setWorkflow(workflow);
        solicitacao.setSolicitante(solicitante);
        solicitacao.setTipoSolicitacao(solicitacaoDTO.getTipoSolicitacao());
        solicitacao.setStatus(SolicitacaoAprovacao.StatusSolicitacao.PENDENTE);
        solicitacao.setValorSolicitado(solicitacaoDTO.getValorSolicitado());
        solicitacao.setDescricaoSolicitacao(solicitacaoDTO.getDescricaoSolicitacao());
        solicitacao.setDadosSolicitacao(solicitacaoDTO.getDadosSolicitacao());
        solicitacao.setPrioridade(solicitacaoDTO.getPrioridade() != null ? solicitacaoDTO.getPrioridade() : 1);
        solicitacao.setDataVencimento(calcularDataVencimento(workflow.getTimeoutHoras()));
        solicitacao.setDataCriacao(LocalDateTime.now());
        solicitacao.setDataAtualizacao(LocalDateTime.now());

        SolicitacaoAprovacao solicitacaoSalva = solicitacaoRepository.save(solicitacao);

        // Criar aprovações para cada etapa do workflow
        criarAprovacoesParaWorkflow(solicitacaoSalva, workflow);

        return solicitacaoSalva;
    }

    public List<SolicitacaoAprovacao> listarTodasSolicitacoes() {
        return solicitacaoRepository.findAll();
    }

    public SolicitacaoAprovacao buscarSolicitacaoPorId(Long id) {
        return solicitacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
    }

    public List<SolicitacaoAprovacao> buscarSolicitacoesPorSolicitante(Long solicitanteId) {
        return solicitacaoRepository.findBySolicitanteId(solicitanteId);
    }

    public List<SolicitacaoAprovacao> buscarSolicitacoesPorAprovador(Long aprovadorId) {
        return solicitacaoRepository.findByAprovadorId(aprovadorId);
    }

    public SolicitacaoAprovacao aprovarSolicitacao(Long solicitacaoId, Long aprovadorId, String observacoes) {
        SolicitacaoAprovacao solicitacao = buscarSolicitacaoPorId(solicitacaoId);
        funcionarioRepository.findById(aprovadorId)
                .orElseThrow(() -> new RuntimeException("Aprovador não encontrado"));

        if (solicitacao.getStatus() != SolicitacaoAprovacao.StatusSolicitacao.PENDENTE) {
            throw new RuntimeException("Solicitação não está pendente");
        }

        // Buscar aprovação pendente do aprovador
        Aprovacao aprovacao = aprovacaoRepository.findBySolicitacaoIdAndAprovadorIdAndStatus(
                solicitacaoId, aprovadorId, Aprovacao.StatusAprovacao.PENDENTE)
                .orElseThrow(() -> new RuntimeException("Aprovação não encontrada ou já processada"));

        aprovacao.setStatus(Aprovacao.StatusAprovacao.APROVADA);
        aprovacao.setDataAprovacao(LocalDateTime.now());
        aprovacao.setObservacoes(observacoes);
        aprovacaoRepository.save(aprovacao);

        // Verificar se todas as aprovações necessárias foram dadas
        verificarStatusSolicitacao(solicitacao);

        return solicitacao;
    }

    public SolicitacaoAprovacao rejeitarSolicitacao(Long solicitacaoId, Long aprovadorId, String motivoRejeicao,
            String observacoes) {
        SolicitacaoAprovacao solicitacao = buscarSolicitacaoPorId(solicitacaoId);
        funcionarioRepository.findById(aprovadorId)
                .orElseThrow(() -> new RuntimeException("Aprovador não encontrado"));

        if (solicitacao.getStatus() != SolicitacaoAprovacao.StatusSolicitacao.PENDENTE) {
            throw new RuntimeException("Solicitação não está pendente");
        }

        // Buscar aprovação pendente do aprovador
        Aprovacao aprovacao = aprovacaoRepository.findBySolicitacaoIdAndAprovadorIdAndStatus(
                solicitacaoId, aprovadorId, Aprovacao.StatusAprovacao.PENDENTE)
                .orElseThrow(() -> new RuntimeException("Aprovação não encontrada ou já processada"));

        aprovacao.setStatus(Aprovacao.StatusAprovacao.REJEITADA);
        aprovacao.setDataAprovacao(LocalDateTime.now());
        aprovacao.setMotivoRejeicao(motivoRejeicao);
        aprovacao.setObservacoes(observacoes);
        aprovacaoRepository.save(aprovacao);

        // Rejeitar a solicitação
        solicitacao.setStatus(SolicitacaoAprovacao.StatusSolicitacao.REJEITADA);
        solicitacao.setDataRejeicao(LocalDateTime.now());
        solicitacao.setMotivoRejeicao(motivoRejeicao);
        solicitacao.setDataAtualizacao(LocalDateTime.now());

        return solicitacaoRepository.save(solicitacao);
    }

    public SolicitacaoAprovacao cancelarSolicitacao(Long solicitacaoId, Long solicitanteId, String motivo) {
        SolicitacaoAprovacao solicitacao = buscarSolicitacaoPorId(solicitacaoId);

        if (!solicitacao.getSolicitante().getId().equals(solicitanteId)) {
            throw new RuntimeException("Apenas o solicitante pode cancelar a solicitação");
        }

        if (solicitacao.getStatus() != SolicitacaoAprovacao.StatusSolicitacao.PENDENTE) {
            throw new RuntimeException("Apenas solicitações pendentes podem ser canceladas");
        }

        solicitacao.setStatus(SolicitacaoAprovacao.StatusSolicitacao.CANCELADA);
        solicitacao.setObservacoes(motivo);
        solicitacao.setDataAtualizacao(LocalDateTime.now());

        return solicitacaoRepository.save(solicitacao);
    }

    public Object obterDashboardAprovacoes(Long funcionarioId) {
        Map<String, Object> dashboard = new HashMap<>();

        // Solicitações pendentes do funcionário
        List<SolicitacaoAprovacao> solicitacoesPendentes = solicitacaoRepository
                .findBySolicitanteIdAndStatus(funcionarioId, SolicitacaoAprovacao.StatusSolicitacao.PENDENTE);

        // Aprovações pendentes do funcionário
        List<Aprovacao> aprovacoesPendentes = aprovacaoRepository
                .findByAprovadorIdAndStatus(funcionarioId, Aprovacao.StatusAprovacao.PENDENTE);

        dashboard.put("solicitacoesPendentes", solicitacoesPendentes.size());
        dashboard.put("aprovacoesPendentes", aprovacoesPendentes.size());
        dashboard.put("totalSolicitacoes", solicitacaoRepository.countBySolicitanteId(funcionarioId));
        dashboard.put("totalAprovacoes", aprovacaoRepository.countByAprovadorId(funcionarioId));

        return dashboard;
    }

    public List<SolicitacaoAprovacao> obterHistoricoAprovacoes(Long funcionarioId) {
        return solicitacaoRepository.findBySolicitanteIdOrderByDataCriacaoDesc(funcionarioId);
    }

    private void criarAprovacoesParaWorkflow(SolicitacaoAprovacao solicitacao, Workflow workflow) {
        List<EtapaWorkflow> etapas = workflow.getEtapas().stream()
                .sorted((e1, e2) -> e1.getOrdemEtapa().compareTo(e2.getOrdemEtapa()))
                .collect(java.util.stream.Collectors.toList());

        for (EtapaWorkflow etapa : etapas) {
            if (etapa.getStatus() == EtapaWorkflow.StatusEtapa.ATIVA) {
                // Determinar aprovadores baseado na configuração da etapa
                List<Funcionario> aprovadores = determinarAprovadores(etapa, solicitacao);

                for (Funcionario aprovador : aprovadores) {
                    Aprovacao aprovacao = new Aprovacao(solicitacao, etapa, aprovador);
                    aprovacaoRepository.save(aprovacao);
                }
            }
        }
    }

    private List<Funcionario> determinarAprovadores(EtapaWorkflow etapa, SolicitacaoAprovacao solicitacao) {
        // Lógica para determinar aprovadores baseada na configuração da etapa
        // Por enquanto, retorna uma lista vazia - implementar lógica específica
        if (etapa == null || solicitacao == null) {
            return java.util.Collections.emptyList();
        }
        return java.util.Collections.emptyList();
    }

    private void verificarStatusSolicitacao(SolicitacaoAprovacao solicitacao) {
        List<Aprovacao> aprovacoes = aprovacaoRepository.findBySolicitacaoId(solicitacao.getId());

        boolean todasAprovadas = aprovacoes.stream()
                .allMatch(a -> a.getStatus() == Aprovacao.StatusAprovacao.APROVADA);

        boolean algumaRejeitada = aprovacoes.stream()
                .anyMatch(a -> a.getStatus() == Aprovacao.StatusAprovacao.REJEITADA);

        if (algumaRejeitada) {
            solicitacao.setStatus(SolicitacaoAprovacao.StatusSolicitacao.REJEITADA);
            solicitacao.setDataRejeicao(LocalDateTime.now());
        } else if (todasAprovadas) {
            solicitacao.setStatus(SolicitacaoAprovacao.StatusSolicitacao.APROVADA);
            solicitacao.setDataAprovacao(LocalDateTime.now());
        }

        solicitacao.setDataAtualizacao(LocalDateTime.now());
        solicitacaoRepository.save(solicitacao);
    }

    private String gerarCodigoSolicitacao() {
        return "SOL-" + System.currentTimeMillis() + "-"
                + java.util.concurrent.ThreadLocalRandom.current().nextInt(1000);
    }

    private LocalDateTime calcularDataVencimento(Integer timeoutHoras) {
        return LocalDateTime.now().plusHours(timeoutHoras != null ? timeoutHoras : 24);
    }
}
