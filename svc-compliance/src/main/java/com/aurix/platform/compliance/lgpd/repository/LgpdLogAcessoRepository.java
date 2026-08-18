package com.aurix.platform.compliance.lgpd.repository;

import com.aurix.platform.compliance.lgpd.entity.LgpdLogAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LgpdLogAcessoRepository extends JpaRepository<LgpdLogAcesso, Long> {

    List<LgpdLogAcesso> findByClienteId(Long clienteId);

    List<LgpdLogAcesso> findByTipoAcesso(LgpdLogAcesso.TipoAcessoLgpd tipo);

    List<LgpdLogAcesso> findByResponsavelOperacao(String responsavel);

    @Query("SELECT l FROM LgpdLogAcesso l WHERE l.dataOperacao BETWEEN :inicio AND :fim ORDER BY l.dataOperacao DESC")
    List<LgpdLogAcesso> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT l FROM LgpdLogAcesso l WHERE l.clienteId = :clienteId ORDER BY l.dataOperacao DESC")
    List<LgpdLogAcesso> findHistoricoPorCliente(@Param("clienteId") Long clienteId);

    @Query("SELECT l FROM LgpdLogAcesso l WHERE l.clienteId = :clienteId AND l.tipoAcesso = :tipo ORDER BY l.dataOperacao DESC")
    List<LgpdLogAcesso> findHistoricoPorClienteETipo(@Param("clienteId") Long clienteId, @Param("tipo") LgpdLogAcesso.TipoAcessoLgpd tipo);
}
