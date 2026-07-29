package com.aurix.platform.intelligence.repository;

import com.aurix.platform.shared.entity.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para Relatorio
 */
@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
    
    /**
     * Busca relatórios por categoria
     */
    List<Relatorio> findByCategoria(Relatorio.CategoriaRelatorio categoria);
    
    /**
     * Busca relatórios por tipo
     */
    List<Relatorio> findByTipoRelatorio(Relatorio.TipoRelatorio tipoRelatorio);
    
    /**
     * Busca relatórios por status
     */
    List<Relatorio> findByStatus(Relatorio.StatusRelatorio status);
    
    /**
     * Busca relatórios por usuário
     */
    List<Relatorio> findByUsuarioGeracao(String usuarioGeracao);
    
    /**
     * Busca relatórios por período
     */
    @Query("SELECT r FROM Relatorio r WHERE r.dataGeracao BETWEEN :inicio AND :fim")
    List<Relatorio> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca relatórios prontos
     */
    @Query("SELECT r FROM Relatorio r WHERE r.status = 'PRONTO'")
    List<Relatorio> findRelatoriosProntos();
    
    /**
     * Busca relatórios em geração
     */
    @Query("SELECT r FROM Relatorio r WHERE r.status = 'GERANDO'")
    List<Relatorio> findRelatoriosEmGeracao();
    
    /**
     * Busca relatórios falhados
     */
    @Query("SELECT r FROM Relatorio r WHERE r.status = 'FALHOU'")
    List<Relatorio> findRelatoriosFalhados();
    
    /**
     * Busca relatórios por nome
     */
    List<Relatorio> findByNomeContainingIgnoreCase(String nome);
    
    /**
     * Conta relatórios por status
     */
    long countByStatus(Relatorio.StatusRelatorio status);
    
    /**
     * Conta relatórios por categoria
     */
    long countByCategoria(Relatorio.CategoriaRelatorio categoria);
    
    /**
     * Conta relatórios por usuário
     */
    long countByUsuarioGeracao(String usuarioGeracao);
}

