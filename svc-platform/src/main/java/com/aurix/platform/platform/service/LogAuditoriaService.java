package com.aurix.platform.platform.service;

import com.aurix.platform.platform.dto.RelatorioComplianceDTO;
import com.aurix.platform.shared.repository.LogAuditoriaRepository;
import com.aurix.platform.shared.dto.LogAuditoriaDTO;
import com.aurix.platform.shared.entity.LogAuditoria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviço para gestão de logs de auditoria
 */
@Service
@Transactional
public class LogAuditoriaService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogAuditoriaService.class);
    private final LogAuditoriaRepository logAuditoriaRepository;

    /**
     * Cria um novo log de auditoria
     */
    public LogAuditoriaDTO criarLogAuditoria(LogAuditoriaDTO logAuditoriaDTO) {
        log.info("Criando log de auditoria: {}", logAuditoriaDTO.getAcao());
        // Criar entidade
        LogAuditoria logAuditoria = new LogAuditoria();
        logAuditoria.setAcao(logAuditoriaDTO.getAcao());
        logAuditoria.setDescricao(logAuditoriaDTO.getDescricao());
        logAuditoria.setEntidade(logAuditoriaDTO.getEntidade());
        logAuditoria.setEntidadeId(logAuditoriaDTO.getEntidadeId());
        logAuditoria.setUsuarioId(logAuditoriaDTO.getUsuarioId());
        logAuditoria.setUsuarioNome(logAuditoriaDTO.getUsuarioNome());
        logAuditoria.setIpOrigem(logAuditoriaDTO.getIpOrigem());
        logAuditoria.setUserAgent(logAuditoriaDTO.getUserAgent());
        logAuditoria.setDataAcao(logAuditoriaDTO.getDataAcao() != null ? logAuditoriaDTO.getDataAcao() : LocalDateTime.now());
        logAuditoria.setTipoAcao(logAuditoriaDTO.getTipoAcao());
        logAuditoria.setCategoria(logAuditoriaDTO.getCategoria());
        logAuditoria.setNivel(logAuditoriaDTO.getNivel() != null ? logAuditoriaDTO.getNivel() : LogAuditoria.NivelAuditoria.INFO);
        logAuditoria.setDadosAnteriores(logAuditoriaDTO.getDadosAnteriores());
        logAuditoria.setDadosNovos(logAuditoriaDTO.getDadosNovos());
        logAuditoria.setResultado(logAuditoriaDTO.getResultado());
        logAuditoria.setCodigoErro(logAuditoriaDTO.getCodigoErro());
        logAuditoria.setMensagemErro(logAuditoriaDTO.getMensagemErro());
        logAuditoria.setDadosExtras(logAuditoriaDTO.getDadosExtras());
        // Salvar
        LogAuditoria logSalvo = logAuditoriaRepository.save(logAuditoria);
        log.info("Log de auditoria criado com ID: {}", logSalvo.getId());
        return converterParaDTO(logSalvo);
    }

    /**
     * Busca log por ID
     */
    @Transactional(readOnly = true)
    public LogAuditoriaDTO buscarLogPorId(Long id) {
        log.info("Buscando log de auditoria por ID: {}", id);
        LogAuditoria logAuditoria = logAuditoriaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Log de auditoria não encontrado"));
        return converterParaDTO(logAuditoria);
    }

    /**
     * Lista logs por usuário
     */
    @Transactional(readOnly = true)
    public List<LogAuditoriaDTO> listarLogsPorUsuario(Long usuarioId) {
        log.info("Listando logs do usuário ID: {}", usuarioId);
        return logAuditoriaRepository.findByUsuarioId(usuarioId).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista logs por entidade
     */
    @Transactional(readOnly = true)
    public List<LogAuditoriaDTO> listarLogsPorEntidade(String entidade) {
        log.info("Listando logs da entidade: {}", entidade);
        return logAuditoriaRepository.findByEntidade(entidade).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista logs por tipo de ação
     */
    @Transactional(readOnly = true)
    public List<LogAuditoriaDTO> listarLogsPorTipoAcao(LogAuditoria.TipoAcao tipoAcao) {
        log.info("Listando logs do tipo de ação: {}", tipoAcao);
        return logAuditoriaRepository.findByTipoAcao(tipoAcao).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista logs por categoria
     */
    @Transactional(readOnly = true)
    public List<LogAuditoriaDTO> listarLogsPorCategoria(LogAuditoria.CategoriaAuditoria categoria) {
        log.info("Listando logs da categoria: {}", categoria);
        return logAuditoriaRepository.findByCategoria(categoria).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista logs por período
     */
    @Transactional(readOnly = true)
    public List<LogAuditoriaDTO> listarLogsPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        log.info("Listando logs do período: {} a {}", inicio, fim);
        return logAuditoriaRepository.findByPeriodo(inicio, fim).stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista logs críticos
     */
    @Transactional(readOnly = true)
    public List<LogAuditoriaDTO> listarLogsCriticos() {
        log.info("Listando logs críticos");
        return logAuditoriaRepository.findLogsCriticos().stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista logs de falha
     */
    @Transactional(readOnly = true)
    public List<LogAuditoriaDTO> listarLogsFalha() {
        log.info("Listando logs de falha");
        return logAuditoriaRepository.findLogsFalha().stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    /**
     * Lista logs de sucesso
     */
    @Transactional(readOnly = true)
    public List<LogAuditoriaDTO> listarLogsSucesso() {
        log.info("Listando logs de sucesso");
        return logAuditoriaRepository.findLogsSucesso().stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RelatorioComplianceDTO gerarRelatorioCompliance(LocalDateTime inicio, LocalDateTime fim) {
        RelatorioComplianceDTO r = new RelatorioComplianceDTO();
        r.setPeriodoInicio(inicio);
        r.setPeriodoFim(fim);
        r.setTotalRegistros(logAuditoriaRepository.countByPeriodo(inicio, fim));
        Map<String, Long> porTipo = new HashMap<>();
        for (LogAuditoria.TipoAcao t : LogAuditoria.TipoAcao.values()) {
            long c = logAuditoriaRepository.countByPeriodoAndTipoAcao(inicio, fim, t);
            if (c > 0) porTipo.put(t.name(), c);
        }
        r.setPorTipoAcao(porTipo);
        Map<String, Long> porCat = new HashMap<>();
        for (LogAuditoria.CategoriaAuditoria c : LogAuditoria.CategoriaAuditoria.values()) {
            long cnt = logAuditoriaRepository.countByPeriodoAndCategoria(inicio, fim, c);
            if (cnt > 0) porCat.put(c.name(), cnt);
        }
        r.setPorCategoria(porCat);
        Map<String, Long> porResultado = new HashMap<>();
        for (String res : List.of("SUCESSO", "FALHA", "ERRO")) {
            long cnt = logAuditoriaRepository.countByPeriodoAndResultado(inicio, fim, res);
            if (cnt > 0) porResultado.put(res, cnt);
        }
        r.setPorResultado(porResultado);
        r.setCriticos(logAuditoriaRepository.countByPeriodoAndNivelCritico(inicio, fim));
        r.setFalhas(logAuditoriaRepository.countByPeriodoAndFalha(inicio, fim));
        return r;
    }

    private LogAuditoriaDTO converterParaDTO(LogAuditoria logAuditoria) {
        LogAuditoriaDTO dto = new LogAuditoriaDTO();
        dto.setId(logAuditoria.getId());
        dto.setAcao(logAuditoria.getAcao());
        dto.setDescricao(logAuditoria.getDescricao());
        dto.setEntidade(logAuditoria.getEntidade());
        dto.setEntidadeId(logAuditoria.getEntidadeId());
        dto.setUsuarioId(logAuditoria.getUsuarioId());
        dto.setUsuarioNome(logAuditoria.getUsuarioNome());
        dto.setIpOrigem(logAuditoria.getIpOrigem());
        dto.setUserAgent(logAuditoria.getUserAgent());
        dto.setDataAcao(logAuditoria.getDataAcao());
        dto.setTipoAcao(logAuditoria.getTipoAcao());
        dto.setCategoria(logAuditoria.getCategoria());
        dto.setNivel(logAuditoria.getNivel());
        dto.setDadosAnteriores(logAuditoria.getDadosAnteriores());
        dto.setDadosNovos(logAuditoria.getDadosNovos());
        dto.setResultado(logAuditoria.getResultado());
        dto.setCodigoErro(logAuditoria.getCodigoErro());
        dto.setMensagemErro(logAuditoria.getMensagemErro());
        dto.setDadosExtras(logAuditoria.getDadosExtras());
        dto.setSucesso(logAuditoria.isSucesso());
        dto.setFalha(logAuditoria.isFalha());
        dto.setCritica(logAuditoria.isCritica());
        dto.setDataCriacao(logAuditoria.getDataCriacao() != null ? logAuditoria.getDataCriacao().toString() : null);
        dto.setDataAtualizacao(logAuditoria.getDataAtualizacao() != null ? logAuditoria.getDataAtualizacao().toString() : null);
        return dto;
    }

    @java.lang.SuppressWarnings("all")
    public LogAuditoriaService(final LogAuditoriaRepository logAuditoriaRepository) {
        this.logAuditoriaRepository = logAuditoriaRepository;
    }
}
