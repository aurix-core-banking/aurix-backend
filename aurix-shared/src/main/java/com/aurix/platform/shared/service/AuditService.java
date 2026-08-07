package com.aurix.platform.shared.service;

import com.aurix.platform.shared.entity.LogAuditoria;
import com.aurix.platform.shared.repository.LogAuditoriaRepository;

public class AuditService {

    private final LogAuditoriaRepository logAuditoriaRepository;

    public AuditService(LogAuditoriaRepository logAuditoriaRepository) {
        this.logAuditoriaRepository = logAuditoriaRepository;
    }

    public void registrar(String acao, LogAuditoria.TipoAcao tipoAcao,
                          LogAuditoria.CategoriaAuditoria categoria,
                          String entidade, Long entidadeId) {
        registrar(acao, tipoAcao, categoria, entidade, entidadeId, null, null, null, null, null);
    }

    public void registrar(String acao, LogAuditoria.TipoAcao tipoAcao,
                          LogAuditoria.CategoriaAuditoria categoria,
                          String entidade, Long entidadeId,
                          Long usuarioId, String usuarioNome,
                          String ipOrigem, String resultado, String dadosExtras) {
        LogAuditoria log = new LogAuditoria();
        log.setAcao(acao);
        log.setTipoAcao(tipoAcao);
        log.setCategoria(categoria);
        log.setEntidade(entidade);
        log.setEntidadeId(entidadeId);
        log.setUsuarioId(usuarioId);
        log.setUsuarioNome(usuarioNome);
        log.setIpOrigem(ipOrigem);
        log.setResultado(resultado);
        log.setDadosExtras(dadosExtras);
        logAuditoriaRepository.save(log);
    }
}
