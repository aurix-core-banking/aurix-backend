package com.aurix.platform.customer.kyc.service;

import com.aurix.platform.customer.kyc.entity.DocumentoKYC;
import com.aurix.platform.customer.kyc.entity.ScoreKYC;
import com.aurix.platform.customer.kyc.entity.SolicitacaoKYC;
import com.aurix.platform.customer.kyc.repository.DocumentoKycRepository;
import com.aurix.platform.customer.kyc.repository.ScoreKycRepository;
import com.aurix.platform.customer.kyc.repository.SolicitacaoKycRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SolicitacaoKycService {
    private final SolicitacaoKycRepository solicitacaoRepository;
    private final DocumentoKycRepository documentoRepository;
    private final ScoreKycRepository scoreRepository;
    private final KycProducer kycProducer;

    public SolicitacaoKycService(SolicitacaoKycRepository solicitacaoRepository,
                                  DocumentoKycRepository documentoRepository,
                                  ScoreKycRepository scoreRepository,
                                  KycProducer kycProducer) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.documentoRepository = documentoRepository;
        this.scoreRepository = scoreRepository;
        this.kycProducer = kycProducer;
    }

    @Transactional(readOnly = true)
    public SolicitacaoKYC buscarPorId(Long id) {
        return solicitacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitacao KYC nao encontrada: " + id));
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoKYC> listarPorCliente(Long clienteId) {
        return solicitacaoRepository.findByClienteId(clienteId);
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoKYC> listarPorStatus(String status) {
        return solicitacaoRepository.findByStatus(status);
    }

    public SolicitacaoKYC criarSolicitacao(Long clienteId) {
        SolicitacaoKYC solicitacao = new SolicitacaoKYC();
        solicitacao.setClienteId(clienteId);
        solicitacao.setStatus("PENDENTE");
        solicitacao.setDataSolicitacao(LocalDateTime.now());
        return solicitacaoRepository.save(solicitacao);
    }

    public DocumentoKYC anexarDocumento(Long solicitacaoId, DocumentoKYC documento) {
        SolicitacaoKYC solicitacao = buscarPorId(solicitacaoId);
        documento.setSolicitacao(solicitacao);
        documento.setStatus("PENDENTE");
        return documentoRepository.save(documento);
    }

    public SolicitacaoKYC aprovar(Long id) {
        SolicitacaoKYC solicitacao = buscarPorId(id);
        if (!"PENDENTE".equals(solicitacao.getStatus())) {
            throw new IllegalArgumentException("Solicitacao KYC ja foi " + solicitacao.getStatus().toLowerCase());
        }
        solicitacao.setStatus("APROVADO");
        solicitacao.setDataConclusao(LocalDateTime.now());
        solicitacao.setScoreRisco(10);
        solicitacaoRepository.save(solicitacao);

        ScoreKYC score = new ScoreKYC();
        score.setClienteId(solicitacao.getClienteId());
        score.setScoreGeral(85);
        score.setScoreDocumento(90);
        score.setScoreBiometria(80);
        score.setScorePep(100);
        score.setScoreOrigemFundos(75);
        scoreRepository.save(score);

        kycProducer.kycAprovado(solicitacao);
        return solicitacao;
    }

    public SolicitacaoKYC rejeitar(Long id, String motivo) {
        SolicitacaoKYC solicitacao = buscarPorId(id);
        if (!"PENDENTE".equals(solicitacao.getStatus())) {
            throw new IllegalArgumentException("Solicitacao KYC ja foi " + solicitacao.getStatus().toLowerCase());
        }
        solicitacao.setStatus("REJEITADO");
        solicitacao.setDataConclusao(LocalDateTime.now());
        solicitacao.setObservacao(motivo);
        solicitacaoRepository.save(solicitacao);
        kycProducer.kycRejeitado(solicitacao, motivo);
        return solicitacao;
    }

    @Transactional(readOnly = true)
    public ScoreKYC consultarScore(Long clienteId) {
        return scoreRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Score nao encontrado para cliente: " + clienteId));
    }
}
