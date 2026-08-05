package com.aurix.platform.platform.repository;

import com.aurix.platform.shared.entity.PermissaoGranular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissaoGranularRepository extends JpaRepository<PermissaoGranular, Long> {

    List<PermissaoGranular> findByRoleIdAndAtivoTrue(Long roleId);

    List<PermissaoGranular> findByRoleId(Long roleId);

    List<PermissaoGranular> findByRecursoAndAcaoAndAtivoTrue(String recurso, String acao);

    @Query("SELECT p FROM PermissaoGranular p WHERE p.roleId = :roleId AND p.recurso = :recurso AND p.acao = :acao AND p.ativo = true")
    List<PermissaoGranular> findByRoleIdAndRecursoAndAcao(@Param("roleId") Long roleId,
            @Param("recurso") String recurso, @Param("acao") String acao);

    @Query("SELECT p FROM PermissaoGranular p WHERE p.roleId IN :roleIds AND p.ativo = true")
    List<PermissaoGranular> findByRoleIds(@Param("roleIds") List<Long> roleIds);

    boolean existsByRoleIdAndRecursoAndAcaoAndEscopo(Long roleId, String recurso, String acao, String escopo);
}
