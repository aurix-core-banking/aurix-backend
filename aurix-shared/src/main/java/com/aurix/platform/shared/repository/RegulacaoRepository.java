package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.Regulacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RegulacaoRepository extends JpaRepository<Regulacao, Long> {

    List<Regulacao> findByOrgaoRegulador(String orgaoRegulador);

    List<Regulacao> findByTipoRegulacao(Regulacao.TipoRegulacao tipoRegulacao);

    List<Regulacao> findByStatus(Regulacao.StatusRegulacao status);

    @Query("SELECT r FROM Regulacao r WHERE r.status = 'ATIVA' AND r.dataVigencia <= :dataAtual AND (r.dataVencimento IS NULL OR r.dataVencimento > :dataAtual)")
    List<Regulacao> findRegulacoesAtivas(@Param("dataAtual") LocalDateTime dataAtual);

    @Query("SELECT r FROM Regulacao r WHERE r.dataVencimento < :dataAtual")
    List<Regulacao> findRegulacoesVencidas(@Param("dataAtual") LocalDateTime dataAtual);

    @Query("SELECT r FROM Regulacao r WHERE r.dataVencimento BETWEEN :inicio AND :fim")
    List<Regulacao> findRegulacoesProximasVencimento(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT r FROM Regulacao r WHERE r.dataVigencia BETWEEN :inicio AND :fim")
    List<Regulacao> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    long countByStatus(Regulacao.StatusRegulacao status);

    long countByTipoRegulacao(Regulacao.TipoRegulacao tipoRegulacao);

    long countByOrgaoRegulador(String orgaoRegulador);
}
