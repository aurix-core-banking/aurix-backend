package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {

    List<Relatorio> findByCategoria(Relatorio.CategoriaRelatorio categoria);

    List<Relatorio> findByTipoRelatorio(Relatorio.TipoRelatorio tipoRelatorio);

    List<Relatorio> findByStatus(Relatorio.StatusRelatorio status);

    List<Relatorio> findByUsuarioGeracao(String usuarioGeracao);

    @Query("SELECT r FROM Relatorio r WHERE r.dataGeracao BETWEEN :inicio AND :fim")
    List<Relatorio> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT r FROM Relatorio r WHERE r.status = 'PRONTO'")
    List<Relatorio> findRelatoriosProntos();

    @Query("SELECT r FROM Relatorio r WHERE r.status = 'GERANDO'")
    List<Relatorio> findRelatoriosEmGeracao();

    @Query("SELECT r FROM Relatorio r WHERE r.status = 'FALHOU'")
    List<Relatorio> findRelatoriosFalhados();

    List<Relatorio> findByNomeContainingIgnoreCase(String nome);

    long countByStatus(Relatorio.StatusRelatorio status);

    long countByCategoria(Relatorio.CategoriaRelatorio categoria);

    long countByUsuarioGeracao(String usuarioGeracao);
}
