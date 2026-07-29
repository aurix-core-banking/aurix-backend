package com.aurix.platform.credit.credit.service;

import com.aurix.platform.credit.credit.repository.ClienteRepository;
import com.aurix.platform.credit.credit.repository.SolicitacaoCreditoRepository;
import com.aurix.platform.shared.dto.SolicitacaoCreditoDTO;
import com.aurix.platform.shared.entity.Cliente;
import com.aurix.platform.shared.entity.SolicitacaoCredito;
import com.aurix.platform.shared.event.SolicitacaoCreditoCriadaEvent;
import com.aurix.platform.shared.event.Topics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gestão de solicitações de crédito
 */
@Service
@Transactional
public class SolicitacaoCreditoService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SolicitacaoCreditoService.class);
    private final SolicitacaoCreditoRepository solicitacaoCreditoRepository;
    private final ClienteRepository clienteRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Cria uma nova solicitação de crédito
     */
    public SolicitacaoCreditoDTO criarSolicitacaoCredito(SolicitacaoCreditoDTO solicitacaoCreditoDTO) {
        log.info("Criando solicitação de crédito para cliente ID: {}", solicitacaoCreditoDTO.getClienteId());
        // Validar dados da solicitação
        validarSolicitacaoCredito(solicitacaoCreditoDTO);
        Cliente cliente = clienteRepository.findById(solicitacaoCreditoDTO.getClienteId()).orElseThrow(() -> new IllegalArgumentException("Cliente nao encontrado"));
        SolicitacaoCredito solicitacao = new SolicitacaoCredito();
        solicitacao.setCliente(cliente);
        solicitacao.setValorSolicitado(solicitacaoCreditoDTO.getValorSolicitado());
        solicitacao.setPrazoMeses(solicitacaoCreditoDTO.getPrazoMeses());
        solicitacao.setTaxaJuros(solicitacaoCreditoDTO.getTaxaJuros());
        solicitacao.setStatus(SolicitacaoCredito.StatusSolicitacao.PENDENTE);
        solicitacao.setObservacoes(solicitacaoCreditoDTO.getObservacoes());
        solicitacao.setDadosAdicionais(solicitacaoCreditoDTO.getDadosAdicionais());
        if (solicitacaoCreditoDTO.getProdutoCreditoId() != null) {
            solicitacao.setProdutoCreditoId(solicitacaoCreditoDTO.getProdutoCreditoId());
        }
        SolicitacaoCredito solicitacaoSalva = solicitacaoCreditoRepository.save(solicitacao);
        log.info("Solicitação de crédito criada com ID: {}", solicitacaoSalva.getId());

        SolicitacaoCreditoCriadaEvent event = SolicitacaoCreditoCriadaEvent.criada(
            solicitacaoSalva.getId(), solicitacaoSalva.getCliente().getId(),
            solicitacaoSalva.getValorSolicitado(), "GERAL");
        kafkaTemplate.send(Topics.CREDIT_SOLICITACAO_CRIADA,
            String.valueOf(solicitacaoSalva.getCliente().getId()), event);

        return converterParaDTO(solicitacaoSalva);
    }

    /**
     * Busca solicitação por ID
     */
    @Transactional(readOnly = true)
    public SolicitacaoCreditoDTO buscarSolicitacaoPorId(Long id) {
        log.info("Buscando solicitação de crédito por ID: {}", id);
        SolicitacaoCredito solicitacao = solicitacaoCreditoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Solicitação de crédito não encontrada"));
        return converterParaDTO(solicitacao);
    }

    /**
     * Lista solicitações por cliente
     */
    @Transactional(readOnly = true)
    public List<SolicitacaoCreditoDTO> listarSolicitacoesPorCliente(Long clienteId) {
        log.info("Listando solicitações de crédito do cliente ID: {}", clienteId);
        return solicitacaoCreditoRepository.findByClienteId(clienteId).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista solicitações por status
     */
    @Transactional(readOnly = true)
    public List<SolicitacaoCreditoDTO> listarSolicitacoesPorStatus(SolicitacaoCredito.StatusSolicitacao status) {
        log.info("Listando solicitações de crédito com status: {}", status);
        return solicitacaoCreditoRepository.findByStatus(status).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista solicitações pendentes
     */
    @Transactional(readOnly = true)
    public List<SolicitacaoCreditoDTO> listarSolicitacoesPendentes() {
        log.info("Listando solicitações de crédito pendentes");
        return solicitacaoCreditoRepository.findSolicitacoesPendentes().stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoCreditoDTO> listarSolicitacoesAprovadas() {
        return solicitacaoCreditoRepository.findSolicitacoesAprovadas().stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoCreditoDTO> listarSolicitacoesRefer() {
        return solicitacaoCreditoRepository.findSolicitacoesRefer().stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    public SolicitacaoCreditoDTO emitirOferta(Long id, BigDecimal valorAprovado, Integer prazoAprovado, BigDecimal taxaAprovada) {
        SolicitacaoCredito s = solicitacaoCreditoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        if (s.getStatus() != SolicitacaoCredito.StatusSolicitacao.APROVADA) {
            throw new IllegalStateException("Apenas solicitacoes aprovadas podem ter oferta emitida");
        }
        s.setStatus(SolicitacaoCredito.StatusSolicitacao.OFERTA_EMITIDA);
        s.setValorAprovado(valorAprovado);
        s.setPrazoAprovado(prazoAprovado);
        s.setTaxaAprovada(taxaAprovada);
        s.setDataAprovacao(LocalDateTime.now());
        return converterParaDTO(solicitacaoCreditoRepository.save(s));
    }

    public SolicitacaoCreditoDTO aceitarOferta(Long id) {
        SolicitacaoCredito s = solicitacaoCreditoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        if (s.getStatus() != SolicitacaoCredito.StatusSolicitacao.OFERTA_EMITIDA) {
            throw new IllegalStateException("Apenas ofertas emitidas podem ser aceitas");
        }
        s.setStatus(SolicitacaoCredito.StatusSolicitacao.ACEITO);
        s.setDataAceite(LocalDateTime.now());
        return converterParaDTO(solicitacaoCreditoRepository.save(s));
    }

    public SolicitacaoCreditoDTO registrarContrato(Long id, String contratoUrl) {
        SolicitacaoCredito s = solicitacaoCreditoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        if (s.getStatus() != SolicitacaoCredito.StatusSolicitacao.ACEITO) {
            throw new IllegalStateException("Apenas ofertas aceitas podem ter contrato registrado");
        }
        s.setStatus(SolicitacaoCredito.StatusSolicitacao.CONTRATO_ASSINADO);
        s.setContratoUrl(contratoUrl);
        return converterParaDTO(solicitacaoCreditoRepository.save(s));
    }

    public SolicitacaoCreditoDTO liberar(Long id) {
        SolicitacaoCredito s = solicitacaoCreditoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada"));
        if (s.getStatus() != SolicitacaoCredito.StatusSolicitacao.CONTRATO_ASSINADO) {
            throw new IllegalStateException("Apenas contratos assinados podem ser liberados");
        }
        s.setStatus(SolicitacaoCredito.StatusSolicitacao.LIBERADO);
        s.setDataLiberacao(LocalDateTime.now());
        return converterParaDTO(solicitacaoCreditoRepository.save(s));
    }

    /**
     * Aprova solicitação de crédito
     */
    public void aprovarSolicitacao(Long id, BigDecimal valorAprovado, Integer prazoAprovado, BigDecimal taxaAprovada) {
        log.info("Aprovando solicitação de crédito ID: {}", id);
        SolicitacaoCredito solicitacao = solicitacaoCreditoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Solicitação de crédito não encontrada"));
        if (solicitacao.getStatus() != SolicitacaoCredito.StatusSolicitacao.PENDENTE && solicitacao.getStatus() != SolicitacaoCredito.StatusSolicitacao.EM_ANALISE) {
            throw new IllegalStateException("Solicitação não pode ser aprovada");
        }
        solicitacao.setStatus(SolicitacaoCredito.StatusSolicitacao.APROVADA);
        solicitacao.setValorAprovado(valorAprovado);
        solicitacao.setPrazoAprovado(prazoAprovado);
        solicitacao.setTaxaAprovada(taxaAprovada);
        solicitacao.setDataAprovacao(LocalDateTime.now());
        solicitacao.setDataAnalise(LocalDateTime.now());
        solicitacaoCreditoRepository.save(solicitacao);
        log.info("Solicitação de crédito aprovada com sucesso");
    }

    /**
     * Rejeita solicitação de crédito
     */
    public void rejeitarSolicitacao(Long id, String observacoes) {
        log.info("Rejeitando solicitação de crédito ID: {}", id);
        SolicitacaoCredito solicitacao = solicitacaoCreditoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Solicitação de crédito não encontrada"));
        if (solicitacao.getStatus() != SolicitacaoCredito.StatusSolicitacao.PENDENTE && solicitacao.getStatus() != SolicitacaoCredito.StatusSolicitacao.EM_ANALISE) {
            throw new IllegalStateException("Solicitação não pode ser rejeitada");
        }
        solicitacao.setStatus(SolicitacaoCredito.StatusSolicitacao.REJEITADA);
        solicitacao.setObservacoes(observacoes);
        solicitacao.setDataAnalise(LocalDateTime.now());
        solicitacaoCreditoRepository.save(solicitacao);
        log.info("Solicitação de crédito rejeitada com sucesso");
    }

    /**
     * Cancela solicitação de crédito
     */
    public void cancelarSolicitacao(Long id) {
        log.info("Cancelando solicitação de crédito ID: {}", id);
        SolicitacaoCredito solicitacao = solicitacaoCreditoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Solicitação de crédito não encontrada"));
        if (solicitacao.getStatus() != SolicitacaoCredito.StatusSolicitacao.PENDENTE) {
            throw new IllegalStateException("Solicitação não pode ser cancelada");
        }
        solicitacao.setStatus(SolicitacaoCredito.StatusSolicitacao.CANCELADA);
        solicitacao.setDataAnalise(LocalDateTime.now());
        solicitacaoCreditoRepository.save(solicitacao);
        log.info("Solicitação de crédito cancelada com sucesso");
    }

    /**
     * Valida solicitação de crédito
     */
    private void validarSolicitacaoCredito(SolicitacaoCreditoDTO solicitacao) {
        if (solicitacao.getValorSolicitado().compareTo(BigDecimal.valueOf(1000)) < 0) {
            throw new IllegalArgumentException("Valor mínimo para crédito é R$ 1.000,00");
        }
        if (solicitacao.getValorSolicitado().compareTo(BigDecimal.valueOf(1000000)) > 0) {
            throw new IllegalArgumentException("Valor máximo para crédito é R$ 1.000.000,00");
        }
        if (solicitacao.getPrazoMeses() < 6) {
            throw new IllegalArgumentException("Prazo mínimo para crédito é 6 meses");
        }
        if (solicitacao.getPrazoMeses() > 60) {
            throw new IllegalArgumentException("Prazo máximo para crédito é 60 meses");
        }
        if (solicitacao.getTaxaJuros().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa de juros não pode ser negativa");
        }
    }

    /**
     * Converte entidade para DTO
     */
    private SolicitacaoCreditoDTO converterParaDTO(SolicitacaoCredito solicitacao) {
        SolicitacaoCreditoDTO dto = new SolicitacaoCreditoDTO();
        dto.setId(solicitacao.getId());
        Cliente cliente = solicitacao.getCliente();
        if (cliente != null) {
            dto.setClienteId(cliente.getId());
            String nomeExibicao = cliente.getTipoPessoa() == Cliente.TipoPessoa.FISICA
                ? cliente.getNome()
                : cliente.getNomeRazaoSocial();
            dto.setClienteNome(nomeExibicao);
            dto.setClienteTipoPessoa(cliente.getTipoPessoa().name());
        }
        dto.setValorSolicitado(solicitacao.getValorSolicitado());
        dto.setPrazoMeses(solicitacao.getPrazoMeses());
        dto.setTaxaJuros(solicitacao.getTaxaJuros());
        dto.setStatus(solicitacao.getStatus());
        dto.setScoreCredito(solicitacao.getScoreCredito());
        dto.setAnaliseRisco(solicitacao.getAnaliseRisco());
        dto.setDataSolicitacao(solicitacao.getDataSolicitacao());
        dto.setDataAnalise(solicitacao.getDataAnalise());
        dto.setDataAprovacao(solicitacao.getDataAprovacao());
        dto.setObservacoes(solicitacao.getObservacoes());
        dto.setValorAprovado(solicitacao.getValorAprovado());
        dto.setPrazoAprovado(solicitacao.getPrazoAprovado());
        dto.setTaxaAprovada(solicitacao.getTaxaAprovada());
        dto.setDadosAdicionais(solicitacao.getDadosAdicionais());
        dto.setProdutoCreditoId(solicitacao.getProdutoCreditoId());
        dto.setContratoUrl(solicitacao.getContratoUrl());
        dto.setDataCriacao(solicitacao.getDataCriacao() != null ? solicitacao.getDataCriacao().toString() : null);
        dto.setDataAtualizacao(solicitacao.getDataAtualizacao() != null ? solicitacao.getDataAtualizacao().toString() : null);
        return dto;
    }

    @java.lang.SuppressWarnings("all")
    public SolicitacaoCreditoService(final SolicitacaoCreditoRepository solicitacaoCreditoRepository, final ClienteRepository clienteRepository, final KafkaTemplate<String, Object> kafkaTemplate) {
        this.solicitacaoCreditoRepository = solicitacaoCreditoRepository;
        this.clienteRepository = clienteRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
}
