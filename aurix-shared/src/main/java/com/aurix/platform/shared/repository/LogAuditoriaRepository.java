package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.LogAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, Long> {

    List<LogAuditoria> findByUsuarioId(Long usuarioId);

    List<LogAuditoria> findByEntidade(String entidade);

    List<LogAuditoria> findByTipoAcao(LogAuditoria.TipoAcao tipoAcao);

    List<LogAuditoria> findByCategoria(LogAuditoria.CategoriaAuditoria categoria);

    List<LogAuditoria> findByNivel(LogAuditoria.NivelAuditoria nivel);

    List<LogAuditoria> findByResultado(String resultado);

    @Query("SELECT l FROM LogAuditoria l WHERE l.dataAcao BETWEEN :inicio AND :fim")
    List<LogAuditoria> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    List<LogAuditoria> findByDataAcaoBefore(LocalDateTime data);

    @Query("SELECT l FROM LogAuditoria l WHERE l.nivel = 'CRITICO'")
    List<LogAuditoria> findLogsCriticos();

    @Query("SELECT l FROM LogAuditoria l WHERE l.resultado IN ('FALHA', 'ERRO')")
    List<LogAuditoria> findLogsFalha();

    @Query("SELECT l FROM LogAuditoria l WHERE l.resultado = 'SUCESSO'")
    List<LogAuditoria> findLogsSucesso();

    List<LogAuditoria> findByIpOrigem(String ipOrigem);

    List<LogAuditoria> findByAcaoContainingIgnoreCase(String acao);

    long countByUsuarioId(Long usuarioId);

    long countByEntidade(String entidade);

    long countByTipoAcao(LogAuditoria.TipoAcao tipoAcao);

    long countByCategoria(LogAuditoria.CategoriaAuditoria categoria);

    long countByNivel(LogAuditoria.NivelAuditoria nivel);

    long countByResultado(String resultado);

    @Query("SELECT COUNT(l) FROM LogAuditoria l WHERE l.dataAcao BETWEEN :inicio AND :fim")
    long countByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT COUNT(l) FROM LogAuditoria l WHERE l.dataAcao BETWEEN :inicio AND :fim AND l.tipoAcao = :tipo")
    long countByPeriodoAndTipoAcao(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim, @Param("tipo") LogAuditoria.TipoAcao tipo);

    @Query("SELECT COUNT(l) FROM LogAuditoria l WHERE l.dataAcao BETWEEN :inicio AND :fim AND l.categoria = :cat")
    long countByPeriodoAndCategoria(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim, @Param("cat") LogAuditoria.CategoriaAuditoria cat);

    @Query("SELECT COUNT(l) FROM LogAuditoria l WHERE l.dataAcao BETWEEN :inicio AND :fim AND l.resultado = :resultado")
    long countByPeriodoAndResultado(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim, @Param("resultado") String resultado);

    @Query("SELECT COUNT(l) FROM LogAuditoria l WHERE l.dataAcao BETWEEN :inicio AND :fim AND l.nivel = 'CRITICO'")
    long countByPeriodoAndNivelCritico(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT COUNT(l) FROM LogAuditoria l WHERE l.dataAcao BETWEEN :inicio AND :fim AND l.resultado IN ('FALHA', 'ERRO')")
    long countByPeriodoAndFalha(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
