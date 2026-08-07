package com.aurix.platform.compliance.service;

import com.aurix.platform.shared.repository.RegulacaoRepository;
import com.aurix.platform.shared.dto.RegulacaoDTO;
import com.aurix.platform.shared.entity.Regulacao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gestão de regulamentações
 */
@Service
@Transactional
public class RegulacaoService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RegulacaoService.class);
    private final RegulacaoRepository regulacaoRepository;

    /**
     * Cria uma nova regulamentação
     */
    public RegulacaoDTO criarRegulacao(RegulacaoDTO regulacaoDTO) {
        log.info("Criando regulamentação: {}", regulacaoDTO.getNome());
        // Validar dados da regulamentação
        validarRegulacao(regulacaoDTO);
        // Criar entidade
        Regulacao regulacao = new Regulacao();
        regulacao.setNome(regulacaoDTO.getNome());
        regulacao.setDescricao(regulacaoDTO.getDescricao());
        regulacao.setOrgaoRegulador(regulacaoDTO.getOrgaoRegulador());
        regulacao.setNumeroRegulamentacao(regulacaoDTO.getNumeroRegulamentacao());
        regulacao.setDataVigencia(regulacaoDTO.getDataVigencia());
        regulacao.setDataVencimento(regulacaoDTO.getDataVencimento());
        regulacao.setTipoRegulacao(regulacaoDTO.getTipoRegulacao());
        regulacao.setStatus(regulacaoDTO.getStatus() != null ? regulacaoDTO.getStatus() : Regulacao.StatusRegulacao.ATIVA);
        regulacao.setPenalidadeValor(regulacaoDTO.getPenalidadeValor());
        regulacao.setPenalidadeDescricao(regulacaoDTO.getPenalidadeDescricao());
        regulacao.setRequisitos(regulacaoDTO.getRequisitos());
        // Salvar
        Regulacao regulacaoSalva = regulacaoRepository.save(regulacao);
        log.info("Regulamentação criada com ID: {}", regulacaoSalva.getId());
        return converterParaDTO(regulacaoSalva);
    }

    /**
     * Busca regulamentação por ID
     */
    @Transactional(readOnly = true)
    public RegulacaoDTO buscarRegulacaoPorId(Long id) {
        log.info("Buscando regulamentação por ID: {}", id);
        Regulacao regulacao = regulacaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Regulamentação não encontrada"));
        return converterParaDTO(regulacao);
    }

    /**
     * Lista todas as regulamentações
     */
    @Transactional(readOnly = true)
    public List<RegulacaoDTO> listarRegulacoes() {
        log.info("Listando todas as regulamentações");
        return regulacaoRepository.findAll().stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista regulamentações por órgão regulador
     */
    @Transactional(readOnly = true)
    public List<RegulacaoDTO> listarRegulacoesPorOrgao(String orgaoRegulador) {
        log.info("Listando regulamentações do órgão: {}", orgaoRegulador);
        return regulacaoRepository.findByOrgaoRegulador(orgaoRegulador).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista regulamentações por tipo
     */
    @Transactional(readOnly = true)
    public List<RegulacaoDTO> listarRegulacoesPorTipo(Regulacao.TipoRegulacao tipoRegulacao) {
        log.info("Listando regulamentações do tipo: {}", tipoRegulacao);
        return regulacaoRepository.findByTipoRegulacao(tipoRegulacao).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista regulamentações ativas
     */
    @Transactional(readOnly = true)
    public List<RegulacaoDTO> listarRegulacoesAtivas() {
        log.info("Listando regulamentações ativas");
        return regulacaoRepository.findRegulacoesAtivas(LocalDateTime.now()).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista regulamentações vencidas
     */
    @Transactional(readOnly = true)
    public List<RegulacaoDTO> listarRegulacoesVencidas() {
        log.info("Listando regulamentações vencidas");
        return regulacaoRepository.findRegulacoesVencidas(LocalDateTime.now()).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Atualiza regulamentação
     */
    public RegulacaoDTO atualizarRegulacao(Long id, RegulacaoDTO regulacaoDTO) {
        log.info("Atualizando regulamentação ID: {}", id);
        Regulacao regulacao = regulacaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Regulamentação não encontrada"));
        regulacao.setNome(regulacaoDTO.getNome());
        regulacao.setDescricao(regulacaoDTO.getDescricao());
        regulacao.setOrgaoRegulador(regulacaoDTO.getOrgaoRegulador());
        regulacao.setNumeroRegulamentacao(regulacaoDTO.getNumeroRegulamentacao());
        regulacao.setDataVigencia(regulacaoDTO.getDataVigencia());
        regulacao.setDataVencimento(regulacaoDTO.getDataVencimento());
        regulacao.setTipoRegulacao(regulacaoDTO.getTipoRegulacao());
        regulacao.setStatus(regulacaoDTO.getStatus());
        regulacao.setPenalidadeValor(regulacaoDTO.getPenalidadeValor());
        regulacao.setPenalidadeDescricao(regulacaoDTO.getPenalidadeDescricao());
        regulacao.setRequisitos(regulacaoDTO.getRequisitos());
        Regulacao regulacaoAtualizada = regulacaoRepository.save(regulacao);
        log.info("Regulamentação atualizada com sucesso");
        return converterParaDTO(regulacaoAtualizada);
    }

    /**
     * Ativa/desativa regulamentação
     */
    public void toggleStatusRegulacao(Long id) {
        log.info("Alterando status da regulamentação ID: {}", id);
        Regulacao regulacao = regulacaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Regulamentação não encontrada"));
        if (regulacao.getStatus() == Regulacao.StatusRegulacao.ATIVA) {
            regulacao.setStatus(Regulacao.StatusRegulacao.SUSPENSA);
        } else {
            regulacao.setStatus(Regulacao.StatusRegulacao.ATIVA);
        }
        regulacaoRepository.save(regulacao);
        log.info("Status da regulamentação alterado para: {}", regulacao.getStatus());
    }

    /**
     * Valida regulamentação
     */
    private void validarRegulacao(RegulacaoDTO regulacao) {
        if (regulacao.getDataVigencia() == null) {
            throw new IllegalArgumentException("Data de vigência é obrigatória");
        }
        if (regulacao.getDataVencimento() != null && regulacao.getDataVencimento().isBefore(regulacao.getDataVigencia())) {
            throw new IllegalArgumentException("Data de vencimento deve ser posterior à data de vigência");
        }
    }

    /**
     * Converte entidade para DTO
     */
    private RegulacaoDTO converterParaDTO(Regulacao regulacao) {
        RegulacaoDTO dto = new RegulacaoDTO();
        dto.setId(regulacao.getId());
        dto.setNome(regulacao.getNome());
        dto.setDescricao(regulacao.getDescricao());
        dto.setOrgaoRegulador(regulacao.getOrgaoRegulador());
        dto.setNumeroRegulamentacao(regulacao.getNumeroRegulamentacao());
        dto.setDataVigencia(regulacao.getDataVigencia());
        dto.setDataVencimento(regulacao.getDataVencimento());
        dto.setTipoRegulacao(regulacao.getTipoRegulacao());
        dto.setStatus(regulacao.getStatus());
        dto.setPenalidadeValor(regulacao.getPenalidadeValor());
        dto.setPenalidadeDescricao(regulacao.getPenalidadeDescricao());
        dto.setRequisitos(regulacao.getRequisitos());
        dto.setVencida(regulacao.isVencida());
        dto.setEmVigor(regulacao.isEmVigor());
        dto.setDataCriacao(regulacao.getDataCriacao() != null ? regulacao.getDataCriacao().toString() : null);
        dto.setDataAtualizacao(regulacao.getDataAtualizacao() != null ? regulacao.getDataAtualizacao().toString() : null);
        return dto;
    }

    @java.lang.SuppressWarnings("all")
    public RegulacaoService(final RegulacaoRepository regulacaoRepository) {
        this.regulacaoRepository = regulacaoRepository;
    }
}
