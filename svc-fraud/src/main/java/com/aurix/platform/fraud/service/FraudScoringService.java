package com.aurix.platform.fraud.service;

import com.aurix.platform.fraud.entity.BloqueioPreventivo;
import com.aurix.platform.fraud.entity.OcorrenciaFraude;
import com.aurix.platform.fraud.entity.RegraFraude;
import com.aurix.platform.fraud.entity.ScoreTransacao;
import com.aurix.platform.fraud.repository.BloqueioPreventivoRepository;
import com.aurix.platform.fraud.repository.OcorrenciaFraudeRepository;
import com.aurix.platform.fraud.repository.RegraFraudeRepository;
import com.aurix.platform.fraud.repository.ScoreTransacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class FraudScoringService {
    private final RegraFraudeRepository regraRepository;
    private final ScoreTransacaoRepository scoreRepository;
    private final OcorrenciaFraudeRepository ocorrenciaRepository;
    private final BloqueioPreventivoRepository bloqueioRepository;
    private final FraudProducer fraudProducer;

    public FraudScoringService(RegraFraudeRepository regraRepository,
                               ScoreTransacaoRepository scoreRepository,
                               OcorrenciaFraudeRepository ocorrenciaRepository,
                               BloqueioPreventivoRepository bloqueioRepository,
                               FraudProducer fraudProducer) {
        this.regraRepository = regraRepository;
        this.scoreRepository = scoreRepository;
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.bloqueioRepository = bloqueioRepository;
        this.fraudProducer = fraudProducer;
    }

    @Transactional(readOnly = true)
    public List<RegraFraude> listarRegras() {
        return regraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public RegraFraude buscarRegra(Long id) {
        return regraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Regra nao encontrada: " + id));
    }

    public RegraFraude criarRegra(RegraFraude regra) {
        if (regra.getAtivo() == null) regra.setAtivo(true);
        if (regra.getPrioridade() == null) regra.setPrioridade(0);
        return regraRepository.save(regra);
    }

    public RegraFraude atualizarRegra(Long id, RegraFraude regra) {
        RegraFraude existente = buscarRegra(id);
        existente.setNome(regra.getNome());
        existente.setDescricao(regra.getDescricao());
        existente.setTipo(regra.getTipo());
        existente.setParametros(regra.getParametros());
        existente.setPontuacao(regra.getPontuacao());
        existente.setAtivo(regra.getAtivo());
        existente.setPrioridade(regra.getPrioridade());
        return regraRepository.save(existente);
    }

    public void excluirRegra(Long id) {
        RegraFraude regra = buscarRegra(id);
        regraRepository.delete(regra);
    }

    public ScoreTransacao avaliarTransacao(Long clienteId, String transacaoRef) {
        List<RegraFraude> regrasAtivas = regraRepository.findByAtivoTrueOrderByPrioridadeDesc();

        int scoreTotal = 0;
        StringBuilder regrasAcionadas = new StringBuilder();
        String risco;

        for (RegraFraude regra : regrasAtivas) {
            boolean acionada = avaliarRegra(regra, clienteId, transacaoRef);
            if (acionada) {
                scoreTotal += regra.getPontuacao();
                if (regrasAcionadas.length() > 0) regrasAcionadas.append(",");
                regrasAcionadas.append(regra.getNome());
            }
        }

        if (scoreTotal >= 80) {
            risco = "ALTO";
        } else if (scoreTotal >= 40) {
            risco = "MEDIO";
        } else {
            risco = "BAIXO";
        }

        ScoreTransacao score = new ScoreTransacao();
        score.setClienteId(clienteId);
        score.setTransacaoRef(transacaoRef);
        score.setScore(scoreTotal);
        score.setRisco(risco);
        score.setDataAvaliacao(LocalDateTime.now());
        score.setRegrasAcionadas(regrasAcionadas.toString());
        score = scoreRepository.save(score);

        fraudProducer.scoreAlterado(clienteId, transacaoRef, scoreTotal, risco);

        if ("ALTO".equals(risco)) {
            BloqueioPreventivo bloqueio = new BloqueioPreventivo();
            bloqueio.setClienteId(clienteId);
            bloqueio.setMotivo("Score alto de fraude: " + scoreTotal + " pontos");
            bloqueio.setTipo("TEMPORARIO");
            bloqueio.setDataInicio(LocalDateTime.now());
            bloqueio.setDataFim(LocalDateTime.now().plusDays(7));
            bloqueio.setAtivo(true);
            bloqueio = bloqueioRepository.save(bloqueio);

            OcorrenciaFraude ocorrencia = new OcorrenciaFraude();
            ocorrencia.setClienteId(clienteId);
            ocorrencia.setTransacaoRef(transacaoRef);
            ocorrencia.setTipo("SCORE_ALTO");
            ocorrencia.setStatus("PENDENTE");
            ocorrencia.setDataOcorrencia(LocalDateTime.now());
            ocorrencia.setDescricao("Transacao bloqueada - Score: " + scoreTotal + " - Risco: " + risco);
            ocorrencia = ocorrenciaRepository.save(ocorrencia);

            fraudProducer.transacaoBloqueada(score, bloqueio);
            fraudProducer.ocorrenciaCriada(ocorrencia);
        }

        return score;
    }

    private boolean avaliarRegra(RegraFraude regra, Long clienteId, String transacaoRef) {
        return true;
    }

    @Transactional(readOnly = true)
    public List<OcorrenciaFraude> listarOcorrencias() {
        return ocorrenciaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<OcorrenciaFraude> listarOcorrenciasPorCliente(Long clienteId) {
        return ocorrenciaRepository.findByClienteId(clienteId);
    }

    @Transactional(readOnly = true)
    public OcorrenciaFraude buscarOcorrencia(Long id) {
        return ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ocorrencia nao encontrada: " + id));
    }

    public OcorrenciaFraude atualizarStatusOcorrencia(Long id, String status) {
        OcorrenciaFraude ocorrencia = buscarOcorrencia(id);
        ocorrencia.setStatus(status);
        return ocorrenciaRepository.save(ocorrencia);
    }

    public BloqueioPreventivo criarBloqueio(BloqueioPreventivo bloqueio) {
        if (bloqueio.getAtivo() == null) bloqueio.setAtivo(true);
        if (bloqueio.getDataInicio() == null) bloqueio.setDataInicio(LocalDateTime.now());
        return bloqueioRepository.save(bloqueio);
    }

    @Transactional(readOnly = true)
    public List<BloqueioPreventivo> listarBloqueios(Long clienteId) {
        return bloqueioRepository.findByClienteId(clienteId);
    }

    public BloqueioPreventivo desativarBloqueio(Long id) {
        BloqueioPreventivo bloqueio = bloqueioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bloqueio nao encontrado: " + id));
        bloqueio.setAtivo(false);
        bloqueio.setDataFim(LocalDateTime.now());
        return bloqueioRepository.save(bloqueio);
    }
}
