package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.Conformidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConformidadeRepository extends JpaRepository<Conformidade, Long> {

    List<Conformidade> findByRegulacaoId(Long regulacaoId);

    List<Conformidade> findByEntidadeIdAndTipoEntidade(Long entidadeId, String tipoEntidade);

    List<Conformidade> findByStatus(Conformidade.StatusConformidade status);

    @Query("SELECT c FROM Conformidade c WHERE c.dataProximaVerificacao < :dataAtual")
    List<Conformidade> findConformidadesVencidas(@Param("dataAtual") LocalDateTime dataAtual);

    @Query("SELECT c FROM Conformidade c WHERE c.dataProximaVerificacao BETWEEN :inicio AND :fim")
    List<Conformidade> findConformidadesProximasVencimento(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT c FROM Conformidade c WHERE c.dataVerificacao BETWEEN :inicio AND :fim")
    List<Conformidade> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT c FROM Conformidade c WHERE c.status IN ('NAO_CONFORME', 'NAO_CONFORME_CRITICO')")
    List<Conformidade> findConformidadesNaoConformes();

    @Query("SELECT c FROM Conformidade c WHERE c.status = 'NAO_CONFORME_CRITICO'")
    List<Conformidade> findConformidadesCriticas();

    long countByStatus(Conformidade.StatusConformidade status);

    long countByEntidadeIdAndTipoEntidade(Long entidadeId, String tipoEntidade);

    long countByRegulacaoId(Long regulacaoId);
}
