package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long> {

    List<Dashboard> findByCategoria(Dashboard.CategoriaDashboard categoria);

    List<Dashboard> findByUsuarioCriacao(String usuarioCriacao);

    @Query("SELECT d FROM Dashboard d WHERE d.publico = true")
    List<Dashboard> findDashboardsPublicos();

    @Query("SELECT d FROM Dashboard d WHERE d.compartilhado = true")
    List<Dashboard> findDashboardsCompartilhados();

    List<Dashboard> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT d FROM Dashboard d WHERE d.usuarioCriacao = :usuario AND d.categoria = :categoria")
    List<Dashboard> findByUsuarioAndCategoria(@Param("usuario") String usuario, @Param("categoria") Dashboard.CategoriaDashboard categoria);

    long countByCategoria(Dashboard.CategoriaDashboard categoria);

    long countByUsuarioCriacao(String usuarioCriacao);

    long countByPublicoTrue();

    long countByCompartilhadoTrue();
}
