package com.aurix.platform.payments.dda.service;

import com.aurix.platform.payments.dda.repository.DdaAutorizacaoRepository;
import com.aurix.platform.payments.dda.repository.DdaDebitoRepository;
import com.aurix.platform.shared.dto.DdaAutorizacaoDTO;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.DdaAutorizacao;
import com.aurix.platform.shared.entity.DdaDebito;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço para gestão de DDA — Débito Direto Autorizado.
 * Gerencia autorizações prévias do titular, débitos agendados e notificações.
 */
@Service
@Transactional
public class DdaService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DdaService.class);

    private final DdaAutorizacaoRepository autorizacaoRepository;
    private final DdaDebitoRepository debitoRepository;
    private final ContaRepository contaRepository;

    /**
     * Cria uma nova autorização DDA.
     * Requer autorização prévia do titular da conta.
     */
    public DdaAutorizacaoDTO criarAutorizacao(DdaAutorizacaoDTO dto) {
        log.info("Criando autorização DDA para conta: {}", dto.getContaDebitadaId());
        Conta conta = contaRepository.findById(dto.getContaDebitadaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta debitada não encontrada: " + dto.getContaDebitadaId()));

        // Verificar se já existe autorização ativa para este beneficiário/conta
        var existente = autorizacaoRepository.findAutorizacaoAtiva(dto.getCnpjBeneficiario(), dto.getContaDebitadaId());
        if (existente.isPresent()) {
            throw new IllegalStateException("Já existe autorização DDA ativa para este beneficiário nesta conta");
        }

        DdaAutorizacao autorizacao = new DdaAutorizacao();
        autorizacao.setCodigoAutorizacao(gerarCodigoAutorizacao());
        autorizacao.setContaDebitada(conta);
        autorizacao.setDocumentoCpfCnpj(dto.getDocumentoCpfCnpj());
        autorizacao.setNomeTitular(dto.getNomeTitular());
        autorizacao.setCnpjBeneficiario(dto.getCnpjBeneficiario());
        autorizacao.setNomeBeneficiario(dto.getNomeBeneficiario());
        autorizacao.setCodigoConvenio(dto.getCodigoConvenio());
        autorizacao.setValorMaximoDebito(dto.getValorMaximoDebito());
        autorizacao.setObservacoes(dto.getObservacoes());
        autorizacao.setDadosAdicionais(dto.getDadosAdicionais());
        autorizacao.setStatus(DdaAutorizacao.StatusDda.ATIVA);

        DdaAutorizacao salva = autorizacaoRepository.save(autorizacao);
        log.info("Autorização DDA criada com código: {}", salva.getCodigoAutorizacao());
        return converterParaDTO(salva);
    }

    /**
     * Revoga uma autorização DDA existente.
     */
    public void revogarAutorizacao(Long id) {
        log.info("Revogando autorização DDA ID: {}", id);
        DdaAutorizacao autorizacao = autorizacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Autorização DDA não encontrada: " + id));

        if (autorizacao.getStatus() != DdaAutorizacao.StatusDda.ATIVA) {
            throw new IllegalStateException("Autorização não pode ser revogada — status atual: " + autorizacao.getStatus().getDescricao());
        }

        autorizacao.setStatus(DdaAutorizacao.StatusDda.REVOGADA);
        autorizacao.setDataRevogacao(LocalDateTime.now());
        autorizacaoRepository.save(autorizacao);
        log.info("Autorização DDA revogada com sucesso — código: {}", autorizacao.getCodigoAutorizacao());
    }

    /**
     * Lista débitos agendados para uma conta.
     */
    @Transactional(readOnly = true)
    public List<DdaDebito> listarDebitosAgendados(Long contaId) {
        log.info("Listando débitos DDA agendados para conta: {}", contaId);
        return debitoRepository.findByContaDebitadaId(contaId);
    }

    /**
     * Agenda um novo débito DDA com base em uma autorização válida.
     */
    public DdaDebito agendarDebito(Long autorizacaoId, DdaDebito debito) {
        log.info("Agendando débito DDA para autorização: {}", autorizacaoId);
        DdaAutorizacao autorizacao = autorizacaoRepository.findById(autorizacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Autorização DDA não encontrada: " + autorizacaoId));

        if (autorizacao.getStatus() != DdaAutorizacao.StatusDda.ATIVA) {
            throw new IllegalStateException("Autorização DDA não está ativa");
        }

        if (autorizacao.getValorMaximoDebito() != null
                && debito.getValorDebito().compareTo(autorizacao.getValorMaximoDebito()) > 0) {
            throw new IllegalStateException("Valor do débito excede o limite máximo autorizado: R$ " + autorizacao.getValorMaximoDebito());
        }

        debito.setCodigoDebito(gerarCodigoDebito());
        debito.setAutorizacao(autorizacao);
        debito.setContaDebitada(autorizacao.getContaDebitada());
        debito.setCnpjBeneficiario(autorizacao.getCnpjBeneficiario());
        debito.setNomeBeneficiario(autorizacao.getNomeBeneficiario());
        debito.setStatus(DdaDebito.StatusDebito.AGENDADO);

        DdaDebito salvo = debitoRepository.save(debito);
        log.info("Débito DDA agendado com código: {}, data vencimento: {}", salvo.getCodigoDebito(), salvo.getDataVencimento());
        return salvo;
    }

    /**
     * Notifica o titular sobre débito próximo ao vencimento.
     * Chamado por scheduler antes do débito efetivo.
     */
    public void notificarDebitosProximos() {
        log.info("Verificando débitos DDA para notificação");
        LocalDateTime amanha = LocalDateTime.now().plusDays(1);
        LocalDateTime hoje = LocalDateTime.now();
        List<DdaDebito> paraNotificar = debitoRepository.findDebitosNoPeriodo(hoje, amanha);
        for (DdaDebito debito : paraNotificar) {
            if (debito.getStatus() == DdaDebito.StatusDebito.AGENDADO) {
                debito.setStatus(DdaDebito.StatusDebito.NOTIFICADO);
                debito.setDataNotificacao(LocalDateTime.now());
                debitoRepository.save(debito);
                log.info("Notificação enviada para débito DDA código: {}", debito.getCodigoDebito());
            }
        }
    }

    /**
     * Processa débitos DDA agendados para a data atual.
     * Chamado por scheduler diário.
     */
    public void processarDebitos() {
        log.info("Processando débitos DDA para débito efetivo");
        LocalDateTime agora = LocalDateTime.now();
        List<DdaDebito> paraDebitar = debitoRepository.findDebitosParaProcessar(agora);
        for (DdaDebito debito : paraDebitar) {
            try {
                String tenantId = TenantContext.getTenantId();
                int debitado = contaRepository.debitarSaldoAtomico(
                        tenantId, debito.getContaDebitada().getId(), debito.getValorDebito());
                if (debitado == 0) {
                    debito.setStatus(DdaDebito.StatusDebito.FALHADO);
                    debito.setCodigoRetorno("99");
                    debito.setMensagemRetorno("Saldo insuficiente para débito DDA");
                    debito.setDataProcessamento(agora);
                    debitoRepository.save(debito);
                    log.warn("Débito DDA falhou por saldo insuficiente — código: {}", debito.getCodigoDebito());
                } else {
                    debito.setStatus(DdaDebito.StatusDebito.DEBITADO);
                    debito.setDataDebito(agora);
                    debito.setDataProcessamento(agora);
                    debito.setCodigoRetorno("00");
                    debito.setMensagemRetorno("Débito processado com sucesso");
                    debitoRepository.save(debito);
                    log.info("Débito DDA processado com sucesso — código: {}", debito.getCodigoDebito());
                }
            } catch (Exception e) {
                log.error("Erro ao processar débito DDA código {}: {}", debito.getCodigoDebito(), e.getMessage());
                debito.setStatus(DdaDebito.StatusDebito.FALHADO);
                debito.setCodigoRetorno("98");
                debito.setMensagemRetorno("Erro no processamento: " + e.getMessage());
                debito.setDataProcessamento(agora);
                debitoRepository.save(debito);
            }
        }
    }

    /**
     * Busca autorização por ID.
     */
    @Transactional(readOnly = true)
    public DdaAutorizacaoDTO buscarAutorizacaoPorId(Long id) {
        DdaAutorizacao autorizacao = autorizacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Autorização DDA não encontrada: " + id));
        return converterParaDTO(autorizacao);
    }

    /**
     * Lista autorizações por conta.
     */
    @Transactional(readOnly = true)
    public List<DdaAutorizacaoDTO> listarAutorizacoesPorConta(Long contaId) {
        return autorizacaoRepository.findByContaDebitadaId(contaId).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    private String gerarCodigoAutorizacao() {
        return "DDA" + System.currentTimeMillis()
                + UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
    }

    private String gerarCodigoDebito() {
        return "DDAD" + System.currentTimeMillis()
                + UUID.randomUUID().toString().replace("-", "").substring(0, 5).toUpperCase();
    }

    private DdaAutorizacaoDTO converterParaDTO(DdaAutorizacao entity) {
        DdaAutorizacaoDTO dto = new DdaAutorizacaoDTO();
        dto.setId(entity.getId());
        dto.setCodigoAutorizacao(entity.getCodigoAutorizacao());
        dto.setContaDebitadaId(entity.getContaDebitada() != null ? entity.getContaDebitada().getId() : null);
        dto.setContaDebitadaNumero(entity.getContaDebitada() != null ? entity.getContaDebitada().getNumeroConta() : null);
        dto.setDocumentoCpfCnpj(entity.getDocumentoCpfCnpj());
        dto.setNomeTitular(entity.getNomeTitular());
        dto.setCnpjBeneficiario(entity.getCnpjBeneficiario());
        dto.setNomeBeneficiario(entity.getNomeBeneficiario());
        dto.setCodigoConvenio(entity.getCodigoConvenio());
        dto.setValorMaximoDebito(entity.getValorMaximoDebito());
        dto.setStatus(entity.getStatus());
        dto.setDataAutorizacao(entity.getDataAutorizacao());
        dto.setDataRevogacao(entity.getDataRevogacao());
        dto.setObservacoes(entity.getObservacoes());
        dto.setDadosAdicionais(entity.getDadosAdicionais());
        dto.setDataCriacao(entity.getDataCriacao() != null ? entity.getDataCriacao().toString() : null);
        dto.setDataAtualizacao(entity.getDataAtualizacao() != null ? entity.getDataAtualizacao().toString() : null);
        return dto;
    }

    @java.lang.SuppressWarnings("all")
    public DdaService(final DdaAutorizacaoRepository autorizacaoRepository, final DdaDebitoRepository debitoRepository, final ContaRepository contaRepository) {
        this.autorizacaoRepository = autorizacaoRepository;
        this.debitoRepository = debitoRepository;
        this.contaRepository = contaRepository;
    }
}
