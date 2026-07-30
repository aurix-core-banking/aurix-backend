package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.RegraRisco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegraRiscoRepository extends JpaRepository<RegraRisco, Long> {
    
    Optional<RegraRisco> findByCodigoRegra(String codigoRegra);
    
    List<RegraRisco> findByTipoRegra(RegraRisco.TipoRegra tipoRegra);
    
    List<RegraRisco> findByCategoriaRegra(RegraRisco.CategoriaRegra categoriaRegra);
    
    List<RegraRisco> findByNivelPrioridade(RegraRisco.NivelPrioridade nivelPrioridade);
    
    List<RegraRisco> findByAtiva(Boolean ativa);
    
    @Query("SELECT r FROM RegraRisco r WHERE r.tipoRegra = :tipoRegra AND r.ativa = true")
    List<RegraRisco> findRegrasAtivasPorTipo(@Param("tipoRegra") RegraRisco.TipoRegra tipoRegra);
    
    @Query("SELECT r FROM RegraRisco r WHERE r.categoriaRegra = :categoriaRegra AND r.ativa = true")
    List<RegraRisco> findRegrasAtivasPorCategoria(@Param("categoriaRegra") RegraRisco.CategoriaRegra categoriaRegra);
    
    @Query("SELECT r FROM RegraRisco r WHERE r.nivelPrioridade = :nivelPrioridade AND r.ativa = true")
    List<RegraRisco> findRegrasAtivasPorPrioridade(@Param("nivelPrioridade") RegraRisco.NivelPrioridade nivelPrioridade);
    
    @Query("SELECT r FROM RegraRisco r WHERE r.critica = :critica AND r.ativa = true")
    List<RegraRisco> findRegrasCriticas(@Param("critica") Boolean critica);
    
    @Query("SELECT r FROM RegraRisco r WHERE r.requerAprovacao = :requerAprovacao AND r.ativa = true")
    List<RegraRisco> findRegrasQueRequeremAprovacao(@Param("requerAprovacao") Boolean requerAprovacao);
    
    @Query("SELECT r FROM RegraRisco r WHERE r.requerNotificacao = :requerNotificacao AND r.ativa = true")
    List<RegraRisco> findRegrasQueRequeremNotificacao(@Param("requerNotificacao") Boolean requerNotificacao);
    
    @Query("SELECT r FROM RegraRisco r WHERE r.requerBloqueio = :requerBloqueio AND r.ativa = true")
    List<RegraRisco> findRegrasQueRequeremBloqueio(@Param("requerBloqueio") Boolean requerBloqueio);
    
    @Query("SELECT r FROM RegraRisco r WHERE r.requerAuditoria = :requerAuditoria AND r.ativa = true")
    List<RegraRisco> findRegrasQueRequeremAuditoria(@Param("requerAuditoria") Boolean requerAuditoria);
    
    @Query("SELECT r FROM RegraRisco r WHERE r.dataInicioVigencia <= :dataAtual AND (r.dataFimVigencia IS NULL OR r.dataFimVigencia >= :dataAtual) AND r.ativa = true")
    List<RegraRisco> findRegrasVigentes(@Param("dataAtual") LocalDateTime dataAtual);
    
    @Query("SELECT r FROM RegraRisco r WHERE r.valorLimite >= :valor AND r.ativa = true")
    List<RegraRisco> findRegrasComLimiteValorSuficiente(@Param("valor") Double valor);
    
    @Query("SELECT r FROM RegraRisco r WHERE r.percentualLimite >= :percentual AND r.ativa = true")
    List<RegraRisco> findRegrasComLimitePercentualSuficiente(@Param("percentual") Double percentual);
    
    @Query("SELECT r FROM RegraRisco r WHERE r.quantidadeLimite >= :quantidade AND r.ativa = true")
    List<RegraRisco> findRegrasComLimiteQuantidadeSuficiente(@Param("quantidade") Integer quantidade);
    
    @Query("SELECT r FROM RegraRisco r WHERE r.tempoLimiteMinutos >= :tempo AND r.ativa = true")
    List<RegraRisco> findRegrasComLimiteTempoSuficiente(@Param("tempo") Integer tempo);
    
    @Query("SELECT r FROM RegraRisco r WHERE r.scoreMinimo <= :score AND r.scoreMaximo >= :score AND r.ativa = true")
    List<RegraRisco> findRegrasPorScore(@Param("score") Integer score);
    
    @Query("SELECT r FROM RegraRisco r WHERE r.pesoRegra >= :peso AND r.ativa = true")
    List<RegraRisco> findRegrasComPesoMinimo(@Param("peso") Integer peso);
}
