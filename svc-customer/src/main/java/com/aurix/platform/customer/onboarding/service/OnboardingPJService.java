package com.aurix.platform.customer.onboarding.service;

import com.aurix.platform.customer.onboarding.client.CoreApiClient;
import com.aurix.platform.customer.onboarding.dto.ParticipanteRequest;
import com.aurix.platform.customer.onboarding.dto.SolicitacaoPJRequest;
import com.aurix.platform.customer.onboarding.dto.SolicitacaoPJResponse;
import com.aurix.platform.customer.onboarding.entity.*;
import com.aurix.platform.customer.onboarding.repository.*;
import com.aurix.platform.shared.tenant.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OnboardingPJService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OnboardingPJService.class);

    private final SolicitacaoOnboardingRepository solicitacaoOnboardingRepository;
    private final SolicitacaoPJRepository solicitacaoPJRepository;
    private final EmpresaRepository empresaRepository;
    private final ParticipanteRepository participanteRepository;
    private final DocumentoOnboardingRepository documentoRepository;
    private final HistoricoAprovacaoRepository historicoRepository;
    private final ReceitaFederalService receitaFederalService;
    private final CoreApiClient coreApiClient;
    private final WorkflowPJ workflowPJ;
    private final PepRepository pepRepository;

    public SolicitacaoPJResponse iniciarOnboarding(SolicitacaoPJRequest request) {
        String tenantId = TenantContext.getTenantId();
        solicitacaoPJRepository.findByCnpj(request.getCnpj()).ifPresent(existing -> {
            SolicitacaoOnboarding s = solicitacaoOnboardingRepository.findById(existing.getSolicitacaoId()).orElse(null);
            if (s != null && s.getStatus() != StatusOnboarding.REJEITADA && s.getStatus() != StatusOnboarding.CONTA_CRIADA) {
                throw new IllegalArgumentException("Ja existe solicitacao em andamento para este CNPJ");
            }
        });
        SolicitacaoOnboarding onboarding = SolicitacaoOnboarding.builder()
            .tenantId(tenantId)
            .tipoPessoa(TipoPessoa.JURIDICA)
            .status(StatusOnboarding.EM_PREENCHIMENTO)
            .email(request.getEmail())
            .telefone(request.getTelefone())
            .endereco(request.getEndereco())
            .build();
        onboarding = solicitacaoOnboardingRepository.save(onboarding);
        SolicitacaoPJ pj = SolicitacaoPJ.builder()
            .solicitacaoId(onboarding.getId())
            .cnpj(request.getCnpj())
            .razaoSocial(request.getRazaoSocial())
            .nomeFantasia(request.getNomeFantasia())
            .build();
        solicitacaoPJRepository.save(pj);
        registrarHistorico(onboarding, "EM_PREENCHIMENTO", null, null);
        return SolicitacaoPJResponse.from(onboarding, pj);
    }

    public SolicitacaoPJResponse consultarCNPJ(Long solicitacaoId) {
        String tenantId = TenantContext.getTenantId();
        SolicitacaoOnboarding onboarding = solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        SolicitacaoPJ pj = solicitacaoPJRepository.findBySolicitacaoId(solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Dados PJ nao encontrados"));
        if (!workflowPJ.transicaoValida(onboarding.getStatus().name(), "CNPJ_CONSULTADO")) {
            throw new IllegalArgumentException("Transicao invalida: " + onboarding.getStatus() + " -> CNPJ_CONSULTADO");
        }
        ReceitaFederalService.ResultadoReceita resultado = receitaFederalService.consultarCnpj(pj.getCnpj());
        if (resultado.erro() != null || !"ATIVA".equals(resultado.situacao())) {
            onboarding.setStatus(StatusOnboarding.REJEITADA);
            onboarding.setObservacoesAnalista("CNPJ invalido ou inativo: " + (resultado.erro() != null ? resultado.erro() : resultado.situacao()));
            solicitacaoOnboardingRepository.save(onboarding);
            registrarHistorico(onboarding, "REJEITADA", null, "CNPJ invalido ou inativo");
            return SolicitacaoPJResponse.from(onboarding, pj);
        }
        Empresa empresa = Empresa.builder()
            .solicitacaoId(solicitacaoId)
            .cnpj(pj.getCnpj())
            .razaoSocial(resultado.razaoSocial())
            .build();
        empresaRepository.save(empresa);
        onboarding.setStatus(StatusOnboarding.CNPJ_CONSULTADO);
        solicitacaoOnboardingRepository.save(onboarding);
        registrarHistorico(onboarding, "CNPJ_CONSULTADO", null, "CNPJ validado com sucesso");
        return SolicitacaoPJResponse.from(onboarding, pj);
    }

    public void adicionarParticipante(Long solicitacaoId, ParticipanteRequest request) {
        String tenantId = TenantContext.getTenantId();
        SolicitacaoOnboarding onboarding = solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        boolean podeTransicionar = workflowPJ.transicaoValida(onboarding.getStatus().name(), "SOCIOS_VALIDADOS");
        Participante participante = Participante.builder()
            .solicitacaoId(solicitacaoId)
            .tipo(request.getTipo())
            .cpf(request.getCpf())
            .nome(request.getNome())
            .email(request.getEmail())
            .telefone(request.getTelefone())
            .dataNascimento(request.getDataNascimento())
            .nacionalidade(request.getNacionalidade())
            .qualificacao(request.getQualificacao())
            .percentualParticipacao(request.getPercentualParticipacao())
            .build();
        participanteRepository.save(participante);
        if (podeTransicionar) {
            onboarding.setStatus(StatusOnboarding.SOCIOS_VALIDADOS);
            solicitacaoOnboardingRepository.save(onboarding);
            registrarHistorico(onboarding, "SOCIOS_VALIDADOS", null, "Participante adicionado");
        }
    }

    public void adicionarDocumento(Long solicitacaoId, String tipoDocumento, String nomeArquivo, String urlStorage) {
        String tenantId = TenantContext.getTenantId();
        SolicitacaoOnboarding onboarding = solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        DocumentoOnboarding doc = DocumentoOnboarding.builder()
            .solicitacao(onboarding)
            .tipoDocumento(tipoDocumento)
            .nomeArquivo(nomeArquivo)
            .urlStorage(urlStorage)
            .validado(false)
            .build();
        documentoRepository.save(doc);
        onboarding.getDocumentos().add(doc);
        if (workflowPJ.transicaoValida(onboarding.getStatus().name(), "DOCUMENTOS_ANALISADOS")) {
            onboarding.setStatus(StatusOnboarding.DOCUMENTOS_ANALISADOS);
            solicitacaoOnboardingRepository.save(onboarding);
            registrarHistorico(onboarding, "DOCUMENTOS_ANALISADOS", null, "Documento adicionado");
        }
    }

    public void validarDocumento(Long solicitacaoId, Long documentoId, boolean validado, String observacao) {
        String tenantId = TenantContext.getTenantId();
        SolicitacaoOnboarding onboarding = solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        DocumentoOnboarding doc = documentoRepository.findById(documentoId)
            .orElseThrow(() -> new IllegalArgumentException("Documento nao encontrado"));
        if (!doc.getSolicitacao().getId().equals(solicitacaoId)) {
            throw new IllegalArgumentException("Documento nao pertence a esta solicitacao");
        }
        doc.setValidado(validado);
        doc.setObservacaoValidacao(observacao);
        documentoRepository.save(doc);
    }

    public SolicitacaoPJResponse aprovar(Long solicitacaoId, String usuarioAnalista, String observacao) {
        String tenantId = TenantContext.getTenantId();
        SolicitacaoOnboarding onboarding = solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        SolicitacaoPJ pj = solicitacaoPJRepository.findBySolicitacaoId(solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Dados PJ nao encontrados"));
        if (onboarding.getStatus() == StatusOnboarding.CONTA_CRIADA) {
            return SolicitacaoPJResponse.from(onboarding, pj);
        }
        if (!workflowPJ.transicaoValida(onboarding.getStatus().name(), "CONTA_CRIADA")) {
            throw new IllegalArgumentException("Transicao invalida: " + onboarding.getStatus() + " -> CONTA_CRIADA");
        }
        Empresa empresa = empresaRepository.findBySolicitacaoId(solicitacaoId).orElse(null);
        onboarding.setObservacoesAnalista(observacao);
        solicitacaoOnboardingRepository.save(onboarding);
        registrarHistorico(onboarding, "APROVACAO_SOLICITADA", usuarioAnalista, observacao);
        CoreApiClient.CriarClienteContaResult result = coreApiClient.criarClientePJeConta(
            tenantId, pj.getCnpj(), pj.getRazaoSocial(),
            onboarding.getEmail(), onboarding.getTelefone(),
            onboarding.getEndereco(), true,
            pj.getFaturamentoMensal(), pj.getCapitalSocial(),
            empresa != null ? empresa.getCnaePrincipal() : null,
            pj.getPorte() != null ? pj.getPorte().name() : null,
            pj.getDataConstituicao() != null ? pj.getDataConstituicao().toString() : null);
        if (result.sucesso()) {
            onboarding.setClienteIdCriado(result.clienteId());
            onboarding.setContaIdCriada(result.contaId());
            onboarding.setStatus(StatusOnboarding.CONTA_CRIADA);
            solicitacaoOnboardingRepository.save(onboarding);
            registrarHistorico(onboarding, "CONTA_CRIADA", usuarioAnalista,
                "Cliente " + result.clienteId() + ", Conta " + result.contaId());
        }
        return SolicitacaoPJResponse.from(onboarding, pj);
    }

    public SolicitacaoPJResponse avancarStatus(Long solicitacaoId, String novoStatus, String usuario, String observacao) {
        String tenantId = TenantContext.getTenantId();
        SolicitacaoOnboarding onboarding = solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        SolicitacaoPJ pj = solicitacaoPJRepository.findBySolicitacaoId(solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Dados PJ nao encontrados"));
        if (!workflowPJ.transicaoValida(onboarding.getStatus().name(), novoStatus)) {
            throw new IllegalArgumentException("Transicao invalida: " + onboarding.getStatus() + " -> " + novoStatus);
        }
        StatusOnboarding statusDestino = StatusOnboarding.valueOf(novoStatus);
        onboarding.setStatus(statusDestino);
        onboarding.setObservacoesAnalista(observacao);
        solicitacaoOnboardingRepository.save(onboarding);
        registrarHistorico(onboarding, novoStatus, usuario, observacao);
        return SolicitacaoPJResponse.from(onboarding, pj);
    }

    public SolicitacaoPJResponse rejeitar(Long solicitacaoId, String usuarioAnalista, String observacao) {
        String tenantId = TenantContext.getTenantId();
        SolicitacaoOnboarding onboarding = solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        SolicitacaoPJ pj = solicitacaoPJRepository.findBySolicitacaoId(solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Dados PJ nao encontrados"));
        onboarding.setStatus(StatusOnboarding.REJEITADA);
        onboarding.setObservacoesAnalista(observacao);
        solicitacaoOnboardingRepository.save(onboarding);
        registrarHistorico(onboarding, "REJEITADA", usuarioAnalista, observacao);
        return SolicitacaoPJResponse.from(onboarding, pj);
    }

    public Optional<SolicitacaoPJResponse> buscarStatus(Long solicitacaoId) {
        String tenantId = TenantContext.getTenantId();
        return solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .map(onboarding -> {
                SolicitacaoPJ pj = solicitacaoPJRepository.findBySolicitacaoId(solicitacaoId).orElse(null);
                return SolicitacaoPJResponse.from(onboarding, pj);
            });
    }

    public List<SolicitacaoPJResponse> listar(List<StatusOnboarding> statusList) {
        String tenantId = TenantContext.getTenantId();
        List<SolicitacaoOnboarding> list = statusList == null || statusList.isEmpty()
            ? solicitacaoOnboardingRepository.findByTenantId(tenantId)
            : solicitacaoOnboardingRepository.findByTenantIdAndStatusIn(tenantId, statusList);
        return list.stream().map(onboarding -> {
            SolicitacaoPJ pj = solicitacaoPJRepository.findBySolicitacaoId(onboarding.getId()).orElse(null);
            return SolicitacaoPJResponse.from(onboarding, pj);
        }).toList();
    }

    public void removerParticipante(Long solicitacaoId, Long participanteId) {
        String tenantId = TenantContext.getTenantId();
        solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        participanteRepository.deleteById(participanteId);
    }

    public List<Participante> listarSocios(Long solicitacaoId) {
        String tenantId = TenantContext.getTenantId();
        solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        return participanteRepository.findBySolicitacaoId(solicitacaoId);
    }

    public SolicitacaoPJResponse aprovarAML(Long solicitacaoId) {
        String tenantId = TenantContext.getTenantId();
        SolicitacaoOnboarding onboarding = solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        SolicitacaoPJ pj = solicitacaoPJRepository.findBySolicitacaoId(solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Dados PJ nao encontrados"));
        if (onboarding.getStatus() != StatusOnboarding.DOCUMENTOS_ANALISADOS) {
            throw new IllegalArgumentException("Status invalido para aprovacao AML: " + onboarding.getStatus());
        }
        if (pj.getCnpj().startsWith("000000")) {
            onboarding.setStatus(StatusOnboarding.REJEITADA);
            onboarding.setObservacoesAnalista("CNPJ em lista de sancionados");
            solicitacaoOnboardingRepository.save(onboarding);
            registrarHistorico(onboarding, "REJEITADA", null, "CNPJ em lista de sancionados");
            return SolicitacaoPJResponse.from(onboarding, pj);
        }
        List<Participante> participantes = participanteRepository.findBySolicitacaoId(solicitacaoId);
        for (Participante p : participantes) {
            if (p.getCpf().startsWith("000000")) {
                onboarding.setStatus(StatusOnboarding.REJEITADA);
                onboarding.setObservacoesAnalista("Participante " + p.getNome() + " em lista de sancionados");
                solicitacaoOnboardingRepository.save(onboarding);
                registrarHistorico(onboarding, "REJEITADA", null, "Participante em lista de sancionados");
                return SolicitacaoPJResponse.from(onboarding, pj);
            }
        }
        int score = 50;
        if (pj.getFaturamentoMensal() != null) {
            if (pj.getFaturamentoMensal().compareTo(new BigDecimal("1000000")) > 0) score -= 30;
            else if (pj.getFaturamentoMensal().compareTo(new BigDecimal("100000")) > 0) score -= 15;
            else if (pj.getFaturamentoMensal().compareTo(new BigDecimal("10000")) > 0) score -= 5;
        }
        if (pj.getPorte() != null) {
            switch (pj.getPorte()) {
                case MEI: score += 20; break;
                case ME: score += 10; break;
                case EPP: score += 5; break;
            }
        }
        if (pj.getDataConstituicao() != null) {
            long age = ChronoUnit.YEARS.between(pj.getDataConstituicao(), LocalDate.now());
            if (age > 10) score += 20;
            else if (age > 5) score += 10;
            else if (age > 2) score += 5;
            else if (age < 1) score -= 10;
        }
        if (score < 30) {
            onboarding.setStatus(StatusOnboarding.REJEITADA);
            onboarding.setObservacoesAnalista("Pontuacao AML insuficiente: " + score);
            solicitacaoOnboardingRepository.save(onboarding);
            registrarHistorico(onboarding, "REJEITADA", null, "Pontuacao AML insuficiente: " + score);
            return SolicitacaoPJResponse.from(onboarding, pj);
        }
        onboarding.setStatus(StatusOnboarding.AML_APROVADO);
        solicitacaoOnboardingRepository.save(onboarding);
        registrarHistorico(onboarding, "AML_APROVADO", null, "Aprovado na analise AML com pontuacao " + score);
        return SolicitacaoPJResponse.from(onboarding, pj);
    }

    public SolicitacaoPJResponse aprovarCompliance(Long solicitacaoId) {
        String tenantId = TenantContext.getTenantId();
        SolicitacaoOnboarding onboarding = solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        SolicitacaoPJ pj = solicitacaoPJRepository.findBySolicitacaoId(solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Dados PJ nao encontrados"));
        if (onboarding.getStatus() != StatusOnboarding.AML_APROVADO) {
            throw new IllegalArgumentException("Status invalido para aprovacao Compliance: " + onboarding.getStatus());
        }
        List<Participante> participantes = participanteRepository.findBySolicitacaoId(solicitacaoId);
        for (Participante p : participantes) {
            if (pepRepository.existsByTenantIdAndCpf(tenantId, p.getCpf())) {
                onboarding.setStatus(StatusOnboarding.REJEITADA);
                onboarding.setObservacoesAnalista("Participante " + p.getNome() + " identificado como PEP");
                solicitacaoOnboardingRepository.save(onboarding);
                registrarHistorico(onboarding, "REJEITADA", null, "Participante identificado como PEP");
                return SolicitacaoPJResponse.from(onboarding, pj);
            }
        }
        Empresa empresa = empresaRepository.findBySolicitacaoId(solicitacaoId).orElse(null);
        String razaoSocial = empresa != null ? empresa.getRazaoSocial() : pj.getRazaoSocial();
        if (razaoSocial != null && razaoSocial.toUpperCase().contains("OFAC")) {
            onboarding.setStatus(StatusOnboarding.REJEITADA);
            onboarding.setObservacoesAnalista("Empresa em lista OFAC");
            solicitacaoOnboardingRepository.save(onboarding);
            registrarHistorico(onboarding, "REJEITADA", null, "Empresa em lista OFAC");
            return SolicitacaoPJResponse.from(onboarding, pj);
        }
        onboarding.setStatus(StatusOnboarding.COMPLIANCE_APROVADO);
        solicitacaoOnboardingRepository.save(onboarding);
        registrarHistorico(onboarding, "COMPLIANCE_APROVADO", null, "Aprovado na analise de Compliance");
        return SolicitacaoPJResponse.from(onboarding, pj);
    }

    public SolicitacaoPJResponse solicitarAssinatura(Long solicitacaoId, String tipoAssinatura) {
        String tenantId = TenantContext.getTenantId();
        SolicitacaoOnboarding onboarding = solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        SolicitacaoPJ pj = solicitacaoPJRepository.findBySolicitacaoId(solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Dados PJ nao encontrados"));
        if (onboarding.getStatus() != StatusOnboarding.COMPLIANCE_APROVADO) {
            throw new IllegalArgumentException("Status invalido para solicitacao de assinatura: " + onboarding.getStatus());
        }
        onboarding.setObservacoesAnalista("Assinatura " + tipoAssinatura + " solicitada em " + LocalDateTime.now());
        onboarding.setStatus(StatusOnboarding.EM_ASSINATURA);
        solicitacaoOnboardingRepository.save(onboarding);
        registrarHistorico(onboarding, "EM_ASSINATURA", null, "Assinatura " + tipoAssinatura + " solicitada");
        return SolicitacaoPJResponse.from(onboarding, pj);
    }

    public SolicitacaoPJResponse confirmarAssinatura(Long solicitacaoId) {
        String tenantId = TenantContext.getTenantId();
        SolicitacaoOnboarding onboarding = solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        SolicitacaoPJ pj = solicitacaoPJRepository.findBySolicitacaoId(solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Dados PJ nao encontrados"));
        if (onboarding.getStatus() != StatusOnboarding.EM_ASSINATURA) {
            throw new IllegalArgumentException("Status invalido para confirmacao de assinatura: " + onboarding.getStatus());
        }
        onboarding.setStatus(StatusOnboarding.CONTRATO_ASSINADO);
        solicitacaoOnboardingRepository.save(onboarding);
        registrarHistorico(onboarding, "CONTRATO_ASSINADO", null, "Contrato assinado");
        return SolicitacaoPJResponse.from(onboarding, pj);
    }

    private void registrarHistorico(SolicitacaoOnboarding onboarding, String acao, String usuario, String observacao) {
        HistoricoAprovacao h = HistoricoAprovacao.builder()
            .solicitacao(onboarding)
            .acao(acao)
            .usuarioAnalista(usuario)
            .observacao(observacao)
            .dataAcao(LocalDateTime.now())
            .build();
        historicoRepository.save(h);
        onboarding.getHistorico().add(h);
    }

    @java.lang.SuppressWarnings("all")
    public OnboardingPJService(final SolicitacaoOnboardingRepository solicitacaoOnboardingRepository, final SolicitacaoPJRepository solicitacaoPJRepository, final EmpresaRepository empresaRepository, final ParticipanteRepository participanteRepository, final DocumentoOnboardingRepository documentoRepository, final HistoricoAprovacaoRepository historicoRepository, final ReceitaFederalService receitaFederalService, final CoreApiClient coreApiClient, final WorkflowPJ workflowPJ, final PepRepository pepRepository) {
        this.solicitacaoOnboardingRepository = solicitacaoOnboardingRepository;
        this.solicitacaoPJRepository = solicitacaoPJRepository;
        this.empresaRepository = empresaRepository;
        this.participanteRepository = participanteRepository;
        this.documentoRepository = documentoRepository;
        this.historicoRepository = historicoRepository;
        this.receitaFederalService = receitaFederalService;
        this.coreApiClient = coreApiClient;
        this.workflowPJ = workflowPJ;
        this.pepRepository = pepRepository;
    }
}
