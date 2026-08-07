package com.aurix.platform.intelligence.service;

import com.aurix.platform.shared.repository.MetricaRepository;
import com.aurix.platform.shared.dto.MetricaDTO;
import com.aurix.platform.shared.entity.Metrica;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gestão de métricas
 */
@Service
@Transactional
@SuppressWarnings({"PMD.CollapsibleIfStatements"})
public class MetricaService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MetricaService.class);
    private final MetricaRepository metricaRepository;

    /**
     * Cria uma nova métrica
     */
    public MetricaDTO criarMetrica(MetricaDTO metricaDTO) {
        log.info("Criando métrica: {}", metricaDTO.getNome());
        // Validar dados da métrica
        validarMetrica(metricaDTO);
        // Criar entidade
        Metrica metrica = new Metrica();
        metrica.setNome(metricaDTO.getNome());
        metrica.setDescricao(metricaDTO.getDescricao());
        metrica.setValor(metricaDTO.getValor());
        metrica.setValorAnterior(metricaDTO.getValorAnterior());
        metrica.setDataMedicao(metricaDTO.getDataMedicao() != null ? metricaDTO.getDataMedicao() : LocalDateTime.now());
        metrica.setTipoMetrica(metricaDTO.getTipoMetrica());
        metrica.setCategoria(metricaDTO.getCategoria());
        metrica.setUnidadeMedida(metricaDTO.getUnidadeMedida());
        metrica.setTendencia(metricaDTO.getTendencia());
        metrica.setMeta(metricaDTO.getMeta());
        metrica.setLimiteInferior(metricaDTO.getLimiteInferior());
        metrica.setLimiteSuperior(metricaDTO.getLimiteSuperior());
        metrica.setDadosExtras(metricaDTO.getDadosExtras());
        // Salvar
        Metrica metricaSalva = metricaRepository.save(metrica);
        log.info("Métrica criada com ID: {}", metricaSalva.getId());
        return converterParaDTO(metricaSalva);
    }

    /**
     * Busca métrica por ID
     */
    @Transactional(readOnly = true)
    public MetricaDTO buscarMetricaPorId(Long id) {
        log.info("Buscando métrica por ID: {}", id);
        Metrica metrica = metricaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Métrica não encontrada"));
        return converterParaDTO(metrica);
    }

    /**
     * Lista todas as métricas
     */
    @Transactional(readOnly = true)
    public List<MetricaDTO> listarMetricas() {
        log.info("Listando todas as métricas");
        return metricaRepository.findAll().stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista métricas por categoria
     */
    @Transactional(readOnly = true)
    public List<MetricaDTO> listarMetricasPorCategoria(Metrica.CategoriaMetrica categoria) {
        log.info("Listando métricas da categoria: {}", categoria);
        return metricaRepository.findByCategoria(categoria).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista métricas por tipo
     */
    @Transactional(readOnly = true)
    public List<MetricaDTO> listarMetricasPorTipo(Metrica.TipoMetrica tipoMetrica) {
        log.info("Listando métricas do tipo: {}", tipoMetrica);
        return metricaRepository.findByTipoMetrica(tipoMetrica).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista métricas por período
     */
    @Transactional(readOnly = true)
    public List<MetricaDTO> listarMetricasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        log.info("Listando métricas do período: {} a {}", inicio, fim);
        return metricaRepository.findByPeriodo(inicio, fim).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista métricas que atingiram a meta
     */
    @Transactional(readOnly = true)
    public List<MetricaDTO> listarMetricasMetaAtingida() {
        log.info("Listando métricas que atingiram a meta");
        return metricaRepository.findMetricasMetaAtingida().stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista métricas fora dos limites
     */
    @Transactional(readOnly = true)
    public List<MetricaDTO> listarMetricasForaLimites() {
        log.info("Listando métricas fora dos limites");
        return metricaRepository.findMetricasForaLimites().stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Atualiza métrica
     */
    public MetricaDTO atualizarMetrica(Long id, MetricaDTO metricaDTO) {
        log.info("Atualizando métrica ID: {}", id);
        Metrica metrica = metricaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Métrica não encontrada"));
        metrica.setNome(metricaDTO.getNome());
        metrica.setDescricao(metricaDTO.getDescricao());
        metrica.setValor(metricaDTO.getValor());
        metrica.setValorAnterior(metricaDTO.getValorAnterior());
        metrica.setDataMedicao(metricaDTO.getDataMedicao());
        metrica.setTipoMetrica(metricaDTO.getTipoMetrica());
        metrica.setCategoria(metricaDTO.getCategoria());
        metrica.setUnidadeMedida(metricaDTO.getUnidadeMedida());
        metrica.setTendencia(metricaDTO.getTendencia());
        metrica.setMeta(metricaDTO.getMeta());
        metrica.setLimiteInferior(metricaDTO.getLimiteInferior());
        metrica.setLimiteSuperior(metricaDTO.getLimiteSuperior());
        metrica.setDadosExtras(metricaDTO.getDadosExtras());
        Metrica metricaAtualizada = metricaRepository.save(metrica);
        log.info("Métrica atualizada com sucesso");
        return converterParaDTO(metricaAtualizada);
    }

    /**
     * Valida métrica
     */
    private void validarMetrica(MetricaDTO metrica) {
        if (metrica.getValor() == null) {
            throw new IllegalArgumentException("Valor da métrica é obrigatório");
        }
        if (metrica.getLimiteInferior() != null && metrica.getLimiteSuperior() != null) {
            if (metrica.getLimiteInferior().compareTo(metrica.getLimiteSuperior()) > 0) {
                throw new IllegalArgumentException("Limite inferior deve ser menor que o limite superior");
            }
        }
    }

    /**
     * Converte entidade para DTO
     */
    private MetricaDTO converterParaDTO(Metrica metrica) {
        MetricaDTO dto = new MetricaDTO();
        dto.setId(metrica.getId());
        dto.setNome(metrica.getNome());
        dto.setDescricao(metrica.getDescricao());
        dto.setValor(metrica.getValor());
        dto.setValorAnterior(metrica.getValorAnterior());
        dto.setDataMedicao(metrica.getDataMedicao());
        dto.setTipoMetrica(metrica.getTipoMetrica());
        dto.setCategoria(metrica.getCategoria());
        dto.setUnidadeMedida(metrica.getUnidadeMedida());
        dto.setTendencia(metrica.getTendencia());
        dto.setMeta(metrica.getMeta());
        dto.setLimiteInferior(metrica.getLimiteInferior());
        dto.setLimiteSuperior(metrica.getLimiteSuperior());
        dto.setDadosExtras(metrica.getDadosExtras());
        dto.setVariacaoPercentual(metrica.getVariacaoPercentual());
        dto.setDentroLimites(metrica.isDentroLimites());
        dto.setMetaAtingida(metrica.isMetaAtingida());
        dto.setDataCriacao(metrica.getDataCriacao() != null ? metrica.getDataCriacao().toString() : null);
        dto.setDataAtualizacao(metrica.getDataAtualizacao() != null ? metrica.getDataAtualizacao().toString() : null);
        return dto;
    }

    @java.lang.SuppressWarnings("all")
    public MetricaService(final MetricaRepository metricaRepository) {
        this.metricaRepository = metricaRepository;
    }
}
