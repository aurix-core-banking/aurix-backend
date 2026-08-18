package com.aurix.platform.payments.chargeback.service;

import com.aurix.platform.payments.chargeback.repository.ChargebackRepository;
import com.aurix.platform.payments.chargeback.repository.ChargebackEvidenciaRepository;
import com.aurix.platform.shared.dto.ChargebackDTO;
import com.aurix.platform.shared.entity.Chargeback;
import com.aurix.platform.shared.entity.ChargebackEvidencia;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.repository.ContaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço para gestão de chargebacks (estornos).
 * Processo: abertura → análise → contestação → resolução.
 * Prazo limite: até 120 dias da data da transação original.
 */
@Service
@Transactional
public class ChargebackService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ChargebackService.class);
    private static final int PRAZO_MAXIMO_DIAS = 120;

    private final ChargebackRepository chargebackRepository;
    private final ChargebackEvidenciaRepository evidenciaRepository;
    private final ContaRepository contaRepository;

    /**
     * Solicita um novo chargeback.
     * Valida o prazo de 120 dias a partir da data da transação original.
     */
    public ChargebackDTO solicitarChargeback(ChargebackDTO dto) {
        log.info("Solicitando chargeback para conta: {}, tipo origem: {}", dto.getContaId(), dto.getTipoOrigem());

        if (contaRepository.findById(dto.getContaId()).isEmpty()) {
            throw new IllegalArgumentException("Conta não encontrada: " + dto.getContaId());
        }

        LocalDateTime dataTransacao = dto.getDataTransacaoOrigem() != null
                ? dto.getDataTransacaoOrigem()
                : LocalDateTime.now();

        if (dataTransacao.isBefore(LocalDateTime.now().minusDays(PRAZO_MAXIMO_DIAS))) {
            throw new IllegalStateException("Prazo para chargeback expirado — transação anterior a 120 dias");
        }

        Chargeback chargeback = new Chargeback();
        chargeback.setCodigoChargeback(gerarCodigoChargeback());
        chargeback.setContaId(dto.getContaId());
        chargeback.setTransacaoOrigemId(dto.getTransacaoOrigemId());
        chargeback.setTipoOrigem(dto.getTipoOrigem());
        chargeback.setDocumentoOrigem(dto.getDocumentoOrigem());
        chargeback.setValorOriginal(dto.getValorOriginal());
        chargeback.setValorChargeback(dto.getValorChargeback());
        chargeback.setMotivo(dto.getMotivo());
        chargeback.setDescricaoMotivo(dto.getDescricaoMotivo());
        chargeback.setDataTransacaoOrigem(dataTransacao);
        chargeback.setDadosAdicionais(dto.getDadosAdicionais());
        chargeback.setStatus(Chargeback.StatusChargeback.ABERTO);
        chargeback.setPrazoLimite(LocalDateTime.now().plusDays(PRAZO_MAXIMO_DIAS));

        Chargeback salvo = chargebackRepository.save(chargeback);
        log.info("Chargeback aberto com código: {}", salvo.getCodigoChargeback());
        return converterParaDTO(salvo);
    }

    /**
     * Busca chargeback por ID.
     */
    @Transactional(readOnly = true)
    public ChargebackDTO buscarPorId(Long id) {
        log.info("Buscando chargeback por ID: {}", id);
        Chargeback chargeback = chargebackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chargeback não encontrado: " + id));
        return converterParaDTO(chargeback);
    }

    /**
     * Adiciona evidência a um chargeback.
     */
    public ChargebackEvidencia adicionarEvidencia(Long chargebackId, ChargebackEvidencia evidencia) {
        log.info("Adicionando evidência ao chargeback ID: {}", chargebackId);
        Chargeback chargeback = chargebackRepository.findById(chargebackId)
                .orElseThrow(() -> new IllegalArgumentException("Chargeback não encontrado: " + chargebackId));

        if (chargeback.getStatus() == Chargeback.StatusChargeback.DEFERIDO
                || chargeback.getStatus() == Chargeback.StatusChargeback.INDEFERIDO
                || chargeback.getStatus() == Chargeback.StatusChargeback.CANCELADO) {
            throw new IllegalStateException("Não é possível adicionar evidências a chargeback com status: " + chargeback.getStatus().getDescricao());
        }

        evidencia.setChargeback(chargeback);
        evidencia.setDataUpload(LocalDateTime.now());
        ChargebackEvidencia salva = evidenciaRepository.save(evidencia);
        log.info("Evidência adicionada com sucesso — tipo: {}", salva.getTipoEvidencia());
        return salva;
    }

    /**
     * Move chargeback para status "Em Análise".
     */
    public void iniciarAnalise(Long id) {
        log.info("Iniciando análise do chargeback ID: {}", id);
        Chargeback chargeback = chargebackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chargeback não encontrado: " + id));

        if (chargeback.getStatus() != Chargeback.StatusChargeback.ABERTO) {
            throw new IllegalStateException("Chargeback não pode ser analisado — status atual: " + chargeback.getStatus().getDescricao());
        }

        chargeback.setStatus(Chargeback.StatusChargeback.EM_ANALISE);
        chargeback.setDataAnalise(LocalDateTime.now());
        chargebackRepository.save(chargeback);
        log.info("Análise do chargeback iniciada — código: {}", chargeback.getCodigoChargeback());
    }

    /**
     * Move chargeback para status "Em Contestação".
     */
    public void contestar(Long id) {
        log.info("Iniciando contestação do chargeback ID: {}", id);
        Chargeback chargeback = chargebackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chargeback não encontrado: " + id));

        if (chargeback.getStatus() != Chargeback.StatusChargeback.EM_ANALISE) {
            throw new IllegalStateException("Chargeback não pode ser contestado — status atual: " + chargeback.getStatus().getDescricao());
        }

        chargeback.setStatus(Chargeback.StatusChargeback.EM_CONTESTACAO);
        chargeback.setDataContestacao(LocalDateTime.now());
        chargebackRepository.save(chargeback);
        log.info("Contestação do chargeback iniciada — código: {}", chargeback.getCodigoChargeback());
    }

    /**
     * Resolve o chargeback com resultado final.
     */
    public void resolver(Long id, Chargeback.ResultadoChargeback resultado, String justificativa) {
        log.info("Resolvendo chargeback ID: {} com resultado: {}", id, resultado);
        Chargeback chargeback = chargebackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chargeback não encontrado: " + id));

        if (chargeback.getStatus() == Chargeback.StatusChargeback.DEFERIDO
                || chargeback.getStatus() == Chargeback.StatusChargeback.INDEFERIDO
                || chargeback.getStatus() == Chargeback.StatusChargeback.CANCELADO) {
            throw new IllegalStateException("Chargeback já foi resolvido — status: " + chargeback.getStatus().getDescricao());
        }

        switch (resultado) {
            case DEFERIDO:
                chargeback.setStatus(Chargeback.StatusChargeback.DEFERIDO);
                break;
            case INDEFERIDO:
                chargeback.setStatus(Chargeback.StatusChargeback.INDEFERIDO);
                break;
            case PARCIAL:
                chargeback.setStatus(Chargeback.StatusChargeback.PARCIAL);
                break;
            default:
                throw new IllegalArgumentException("Resultado inválido: " + resultado);
        }

        chargeback.setResultado(resultado);
        chargeback.setJustificativaResolucao(justificativa);
        chargeback.setDataResolucao(LocalDateTime.now());
        chargebackRepository.save(chargeback);
        log.info("Chargeback resolvido — código: {}, resultado: {}", chargeback.getCodigoChargeback(), resultado);
    }

    /**
     * Lista chargebacks por conta.
     */
    @Transactional(readOnly = true)
    public List<ChargebackDTO> listarPorConta(Long contaId) {
        return chargebackRepository.findByContaId(contaId).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista chargebacks por status.
     */
    @Transactional(readOnly = true)
    public List<ChargebackDTO> listarPorStatus(Chargeback.StatusChargeback status) {
        return chargebackRepository.findByStatus(status).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Marca chargebacks expirados — chamado por scheduler.
     */
    public void marcarExpirados() {
        log.info("Verificando chargebacks expirados");
        List<Chargeback> expirados = chargebackRepository.findExpirados(LocalDateTime.now());
        for (Chargeback chargeback : expirados) {
            chargeback.setStatus(Chargeback.StatusChargeback.INDEFERIDO);
            chargeback.setResultado(Chargeback.ResultadoChargeback.INDEFERIDO);
            chargeback.setJustificativaResolucao("Prazo de 120 dias expirado");
            chargeback.setDataResolucao(LocalDateTime.now());
            chargebackRepository.save(chargeback);
        }
        log.info("Chargebacks expirados marcados: {}", expirados.size());
    }

    private String gerarCodigoChargeback() {
        return "CHB" + System.currentTimeMillis()
                + UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
    }

    private ChargebackDTO converterParaDTO(Chargeback entity) {
        ChargebackDTO dto = new ChargebackDTO();
        dto.setId(entity.getId());
        dto.setCodigoChargeback(entity.getCodigoChargeback());
        dto.setContaId(entity.getConta() != null ? entity.getConta().getId() : null);
        dto.setContaNumero(entity.getConta() != null ? entity.getConta().getNumeroConta() : null);
        dto.setTransacaoOrigemId(entity.getTransacaoOrigemId());
        dto.setTipoOrigem(entity.getTipoOrigem());
        dto.setDocumentoOrigem(entity.getDocumentoOrigem());
        dto.setValorOriginal(entity.getValorOriginal());
        dto.setValorChargeback(entity.getValorChargeback());
        dto.setMotivo(entity.getMotivo());
        dto.setDescricaoMotivo(entity.getDescricaoMotivo());
        dto.setStatus(entity.getStatus());
        dto.setDataTransacaoOrigem(entity.getDataTransacaoOrigem());
        dto.setDataSolicitacao(entity.getDataSolicitacao());
        dto.setDataAnalise(entity.getDataAnalise());
        dto.setDataContestacao(entity.getDataContestacao());
        dto.setDataResolucao(entity.getDataResolucao());
        dto.setResultado(entity.getResultado());
        dto.setJustificativaResolucao(entity.getJustificativaResolucao());
        dto.setPrazoLimite(entity.getPrazoLimite());
        dto.setDadosAdicionais(entity.getDadosAdicionais());
        dto.setDataCriacao(entity.getDataCriacao() != null ? entity.getDataCriacao().toString() : null);
        dto.setDataAtualizacao(entity.getDataAtualizacao() != null ? entity.getDataAtualizacao().toString() : null);
        return dto;
    }

    @java.lang.SuppressWarnings("all")
    public ChargebackService(final ChargebackRepository chargebackRepository, final ChargebackEvidenciaRepository evidenciaRepository, final ContaRepository contaRepository) {
        this.chargebackRepository = chargebackRepository;
        this.evidenciaRepository = evidenciaRepository;
        this.contaRepository = contaRepository;
    }
}
