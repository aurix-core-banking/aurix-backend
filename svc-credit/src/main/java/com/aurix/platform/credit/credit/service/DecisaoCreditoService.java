package com.aurix.platform.credit.credit.service;

import com.aurix.platform.credit.credit.config.RegrasCreditoProperties;
import com.aurix.platform.credit.credit.integration.CreditBureauService;
import com.aurix.platform.credit.credit.repository.SolicitacaoCreditoRepository;
import com.aurix.platform.shared.entity.Cliente;
import com.aurix.platform.shared.entity.SolicitacaoCredito;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DecisaoCreditoService {
    private final SolicitacaoCreditoRepository solicitacaoCreditoRepository;
    private final CreditBureauService bureauService;
    private final RegrasCreditoProperties regras;

    @Transactional(readOnly = true)
    public DecisaoResponse obterDecisao(Long solicitacaoId) {
        SolicitacaoCredito s = solicitacaoCreditoRepository.findById(solicitacaoId).orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        Cliente cliente = s.getCliente();
        if (cliente != null && cliente.getTipoPessoa() == Cliente.TipoPessoa.JURIDICA) {
            ResultadoDecisao result = decidirPJ(s, s.getTenantId());
            s.setScoreCredito(result.score() != null ? result.score() : 0);
            s.setStatus(SolicitacaoCredito.StatusSolicitacao.valueOf(result.status()));
            s.setAnaliseRisco("{\"decisao\":\"" + result.status() + "\",\"motivo\":\"" + result.motivo() + "\"}");
            s.setDataAnalise(LocalDateTime.now());
            solicitacaoCreditoRepository.save(s);
            return switch (result.status()) {
                case "APROVADA" -> DecisaoResponse.approve(solicitacaoId, result.score() != null ? result.score() : 0, result.motivo());
                case "REJEITADA" -> DecisaoResponse.decline(solicitacaoId, result.score() != null ? result.score() : 0, result.motivo());
                default -> DecisaoResponse.refer(solicitacaoId, result.score() != null ? result.score() : 0, result.motivo());
            };
        }
        String cpf = cliente != null ? cliente.getCpf() : null;
        if (cpf == null || cpf.isBlank()) {
            return DecisaoResponse.refer(solicitacaoId, 0, "CPF nao informado para consulta bureau");
        }
        Optional<CreditBureauService.BureauScore> scoreOpt = bureauService.consultarScore(cpf);
        int score = scoreOpt.map(CreditBureauService.BureauScore::getScore).orElse(0);
        s.setScoreCredito(score);
        s.setAnaliseRisco(scoreOpt.map(CreditBureauService.BureauScore::getDescricaoRisco).orElse("N/A"));
        if (score >= regras.getScoreMinAprovar()) {
            s.setStatus(SolicitacaoCredito.StatusSolicitacao.APROVADA);
            s.setDataAnalise(LocalDateTime.now());
            solicitacaoCreditoRepository.save(s);
            return DecisaoResponse.approve(solicitacaoId, score, "Score dentro do limite para aprovacao automatica");
        }
        if (score <= regras.getScoreMaxRejeitar()) {
            s.setStatus(SolicitacaoCredito.StatusSolicitacao.REJEITADA);
            s.setDataAnalise(LocalDateTime.now());
            solicitacaoCreditoRepository.save(s);
            return DecisaoResponse.decline(solicitacaoId, score, "Score abaixo do limite minimo");
        }
        s.setStatus(SolicitacaoCredito.StatusSolicitacao.REFER);
        s.setDataAnalise(LocalDateTime.now());
        solicitacaoCreditoRepository.save(s);
        return DecisaoResponse.refer(solicitacaoId, score, "Score na faixa de analise manual (refer)");
    }

    public ResultadoDecisao decidirPJ(SolicitacaoCredito solicitacao, String tenantId) {
        Cliente cliente = solicitacao.getCliente();
        CreditBureauService.ScoreCNPJResult scoreResult = bureauService.consultarScoreCNPJ(cliente.getCnpj());

        if (scoreResult.mensagem() != null) {
            return new ResultadoDecisao("REJEITADA", "CNPJ invalido: " + scoreResult.mensagem(), null);
        }

        if (scoreResult.score() >= 500) {
            if (cliente.getFaturamentoMensal() != null
                && cliente.getFaturamentoMensal().compareTo(
                    solicitacao.getValorSolicitado().multiply(BigDecimal.valueOf(0.3))) < 0) {
                return new ResultadoDecisao("REFER", "Faturamento insuficiente para o valor solicitado",
                    scoreResult.score());
            }
            return new ResultadoDecisao("APROVADA", "Aprovado com score " + scoreResult.score(),
                scoreResult.score());
        } else if (scoreResult.score() <= 300) {
            return new ResultadoDecisao("REJEITADA", "Score baixo: " + scoreResult.score(),
                scoreResult.score());
        } else {
            return new ResultadoDecisao("REFER", "Score intermediario: " + scoreResult.score(),
                scoreResult.score());
        }
    }


    public record DecisaoResponse(String decisao, Long solicitacaoId, int score, String motivo) {
        public static DecisaoResponse approve(Long id, int score, String motivo) {
            return new DecisaoResponse("APPROVE", id, score, motivo);
        }

        public static DecisaoResponse decline(Long id, int score, String motivo) {
            return new DecisaoResponse("DECLINE", id, score, motivo);
        }

        public static DecisaoResponse refer(Long id, int score, String motivo) {
            return new DecisaoResponse("REFER", id, score, motivo);
        }
    }

    public record ResultadoDecisao(String status, String motivo, Integer score) {
    }

    @java.lang.SuppressWarnings("all")
    public DecisaoCreditoService(final SolicitacaoCreditoRepository solicitacaoCreditoRepository, final CreditBureauService bureauService, final RegrasCreditoProperties regras) {
        this.solicitacaoCreditoRepository = solicitacaoCreditoRepository;
        this.bureauService = bureauService;
        this.regras = regras;
    }
}
