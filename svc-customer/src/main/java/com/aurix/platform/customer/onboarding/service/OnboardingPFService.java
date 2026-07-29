package com.aurix.platform.customer.onboarding.service;

import com.aurix.platform.customer.onboarding.client.CoreApiClient;
import com.aurix.platform.customer.onboarding.dto.SolicitacaoContaRequest;
import com.aurix.platform.customer.onboarding.dto.SolicitacaoContaResponse;
import com.aurix.platform.customer.onboarding.entity.*;
import com.aurix.platform.customer.onboarding.repository.*;
import com.aurix.platform.shared.tenant.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OnboardingPFService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OnboardingPFService.class);
    private final SolicitacaoOnboardingRepository solicitacaoOnboardingRepository;
    private final SolicitacaoPFRepository solicitacaoPFRepository;
    private final DocumentoOnboardingRepository documentoRepository;
    private final HistoricoAprovacaoRepository historicoRepository;
    private final PepRepository pepRepository;
    private final KycProviderService kycProviderService;
    private final BureauService bureauService;
    private final FraudService fraudService;
    private final CoreApiClient coreApiClient;
    private final WorkflowEngine workflowPF;

    public SolicitacaoContaResponse solicitarAberturaConta(SolicitacaoContaRequest request) {
        String tenantId = TenantContext.getTenantId();
        List<SolicitacaoPF> existing = solicitacaoPFRepository.findByTenantIdAndCpf(tenantId, request.getCpf());
        for (SolicitacaoPF pf : existing) {
            SolicitacaoOnboarding s = solicitacaoOnboardingRepository.findById(pf.getSolicitacaoId()).orElse(null);
            if (s != null && s.getStatus() != StatusOnboarding.REJEITADA && s.getStatus() != StatusOnboarding.CONTA_CRIADA) {
                throw new IllegalArgumentException("Ja existe solicitacao em andamento para este CPF");
            }
        }
        Boolean pep = pepRepository.existsByTenantIdAndCpf(tenantId, request.getCpf()) ? true : null;
        BureauService.ResultadoBureau bureau = bureauService.consultar(request.getCpf());
        FraudService.ResultadoFraude fraude = fraudService.analisar(
            request.getCpf(), request.getNome(), request.getEmail(), request.getTelefone());
        TipoPessoa tipoPessoa = request.getTipoPessoa() != null ? request.getTipoPessoa() : TipoPessoa.FISICA;
        StatusOnboarding statusInicial = fraude.risco() > 70 ? StatusOnboarding.EM_ANALISE_KYC : StatusOnboarding.RECEBIDA;
        SolicitacaoOnboarding onboarding = SolicitacaoOnboarding.builder()
            .tenantId(tenantId)
            .tipoPessoa(tipoPessoa)
            .status(statusInicial)
            .riscoFraude(fraude.risco())
            .email(request.getEmail())
            .telefone(request.getTelefone())
            .endereco(request.getEndereco())
            .build();
        onboarding = solicitacaoOnboardingRepository.save(onboarding);
        SolicitacaoPF pf = SolicitacaoPF.builder()
            .solicitacaoId(onboarding.getId())
            .tenantId(tenantId)
            .cpf(request.getCpf())
            .nome(request.getNome())
            .dataNascimento(request.getDataNascimento())
            .ocupacao(request.getOcupacao())
            .rendaDeclarada(request.getRendaDeclarada())
            .pep(pep)
            .scoreBureau(bureau != null ? bureau.score() : null)
            .contaLimitadaAteKyc(true)
            .build();
        solicitacaoPFRepository.save(pf);
        registrarHistorico(onboarding, "SOLICITACAO_RECEBIDA", null, null);
        return SolicitacaoContaResponse.from(onboarding, pf);
    }

    public Optional<SolicitacaoContaResponse> buscarStatus(Long solicitacaoId) {
        String tenantId = TenantContext.getTenantId();
        return solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .map(onboarding -> {
                SolicitacaoPF pf = solicitacaoPFRepository.findByTenantIdAndSolicitacaoId(tenantId, solicitacaoId).orElse(null);
                return SolicitacaoContaResponse.from(onboarding, pf);
            });
    }

    public List<SolicitacaoContaResponse> listarParaBackOffice(List<StatusOnboarding> statusList) {
        String tenantId = TenantContext.getTenantId();
        List<SolicitacaoOnboarding> list = statusList == null || statusList.isEmpty()
            ? solicitacaoOnboardingRepository.findByTenantId(tenantId)
            : solicitacaoOnboardingRepository.findByTenantIdAndStatusIn(tenantId, statusList);
        return list.stream().map(onboarding -> {
            SolicitacaoPF pf = solicitacaoPFRepository.findByTenantIdAndSolicitacaoId(tenantId, onboarding.getId()).orElse(null);
            return SolicitacaoContaResponse.from(onboarding, pf);
        }).toList();
    }

    public SolicitacaoContaResponse aprovar(Long solicitacaoId, String usuarioAnalista, String observacao) {
        String tenantId = TenantContext.getTenantId();
        SolicitacaoOnboarding onboarding = solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        SolicitacaoPF pf = solicitacaoPFRepository.findByTenantIdAndSolicitacaoId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Dados PF nao encontrados"));
        if (onboarding.getStatus() == StatusOnboarding.CONTA_CRIADA) {
            return SolicitacaoContaResponse.from(onboarding, pf);
        }
        validarTransicao(onboarding, StatusOnboarding.APROVADA);
        onboarding.setStatus(StatusOnboarding.APROVADA);
        onboarding.setObservacoesAnalista(observacao);
        solicitacaoOnboardingRepository.save(onboarding);
        registrarHistorico(onboarding, "APROVADA", usuarioAnalista, observacao);
        CoreApiClient.CriarClienteContaResult result = coreApiClient.criarClientePFeConta(
            tenantId, pf.getCpf(), pf.getNome(), onboarding.getEmail(),
            onboarding.getTelefone(),
            pf.getDataNascimento() != null ? pf.getDataNascimento().toString() : null,
            onboarding.getEndereco(),
            Boolean.TRUE.equals(pf.getContaLimitadaAteKyc()));
        if (result.sucesso()) {
            onboarding.setClienteIdCriado(result.clienteId());
            onboarding.setContaIdCriada(result.contaId());
            validarTransicao(onboarding, StatusOnboarding.CONTA_CRIADA);
            onboarding.setStatus(StatusOnboarding.CONTA_CRIADA);
            solicitacaoOnboardingRepository.save(onboarding);
            registrarHistorico(onboarding, "CONTA_CRIADA", usuarioAnalista,
                "Cliente " + result.clienteId() + ", Conta " + result.contaId());
        }
        return SolicitacaoContaResponse.from(onboarding, pf);
    }

    public SolicitacaoContaResponse rejeitar(Long solicitacaoId, String usuarioAnalista, String observacao) {
        String tenantId = TenantContext.getTenantId();
        SolicitacaoOnboarding onboarding = solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        SolicitacaoPF pf = solicitacaoPFRepository.findByTenantIdAndSolicitacaoId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Dados PF nao encontrados"));
        validarTransicao(onboarding, StatusOnboarding.REJEITADA);
        onboarding.setStatus(StatusOnboarding.REJEITADA);
        onboarding.setObservacoesAnalista(observacao);
        solicitacaoOnboardingRepository.save(onboarding);
        registrarHistorico(onboarding, "REJEITADA", usuarioAnalista, observacao);
        return SolicitacaoContaResponse.from(onboarding, pf);
    }

    public SolicitacaoContaResponse enviarParaKyc(Long solicitacaoId, List<KycProviderService.DocumentoInfo> documentos, String selfieBase64) {
        String tenantId = TenantContext.getTenantId();
        SolicitacaoOnboarding onboarding = solicitacaoOnboardingRepository.findByTenantIdAndId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        SolicitacaoPF pf = solicitacaoPFRepository.findByTenantIdAndSolicitacaoId(tenantId, solicitacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Dados PF nao encontrados"));
        validarTransicao(onboarding, StatusOnboarding.EM_ANALISE_KYC);
        onboarding.setStatus(StatusOnboarding.EM_ANALISE_KYC);
        solicitacaoOnboardingRepository.save(onboarding);
        KycProviderService.ResultadoKyc kyc = kycProviderService.validarDocumentos(pf.getCpf(), documentos, selfieBase64);
        pf.setResultadoKyc(kyc.codigoResultado());
        solicitacaoPFRepository.save(pf);
        StatusOnboarding resultadoKyc = kyc.aprovado() ? StatusOnboarding.KYC_APROVADO : StatusOnboarding.KYC_REJEITADO;
        validarTransicao(onboarding, resultadoKyc);
        onboarding.setStatus(resultadoKyc);
        solicitacaoOnboardingRepository.save(onboarding);
        registrarHistorico(onboarding, "KYC_" + (kyc.aprovado() ? "APROVADO" : "REJEITADO"), null, kyc.mensagem());
        return SolicitacaoContaResponse.from(onboarding, pf);
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
        if (onboarding.getStatus() == StatusOnboarding.RECEBIDA) {
            validarTransicao(onboarding, StatusOnboarding.DOCUMENTOS_PENDENTES);
            onboarding.setStatus(StatusOnboarding.DOCUMENTOS_PENDENTES);
            solicitacaoOnboardingRepository.save(onboarding);
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

    public boolean consultarPep(String cpf) {
        String tenantId = TenantContext.getTenantId();
        return pepRepository.existsByTenantIdAndCpf(tenantId, cpf);
    }

    public Pep registrarPep(String cpf, String nome, String cargoOuVinculo) {
        String tenantId = TenantContext.getTenantId();
        Pep pep = Pep.builder().tenantId(tenantId).cpf(cpf).nome(nome).cargoOuVinculo(cargoOuVinculo).ativo(true).build();
        return pepRepository.save(pep);
    }

    private void validarTransicao(SolicitacaoOnboarding onboarding, StatusOnboarding novoStatus) {
        if (!workflowPF.transicaoValida(onboarding.getStatus().name(), novoStatus.name())) {
            throw new IllegalStateException(
                "Transicao invalida de " + onboarding.getStatus().name() + " para " + novoStatus.name());
        }
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
    public OnboardingPFService(final SolicitacaoOnboardingRepository solicitacaoOnboardingRepository, final SolicitacaoPFRepository solicitacaoPFRepository, final DocumentoOnboardingRepository documentoRepository, final HistoricoAprovacaoRepository historicoRepository, final PepRepository pepRepository, final KycProviderService kycProviderService, final BureauService bureauService, final FraudService fraudService, final CoreApiClient coreApiClient, final WorkflowEngine workflowPF) {
        this.solicitacaoOnboardingRepository = solicitacaoOnboardingRepository;
        this.solicitacaoPFRepository = solicitacaoPFRepository;
        this.documentoRepository = documentoRepository;
        this.historicoRepository = historicoRepository;
        this.pepRepository = pepRepository;
        this.kycProviderService = kycProviderService;
        this.bureauService = bureauService;
        this.fraudService = fraudService;
        this.coreApiClient = coreApiClient;
        this.workflowPF = workflowPF;
    }
}
