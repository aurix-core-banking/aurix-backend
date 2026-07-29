package com.aurix.platform.banking.repository;

import com.aurix.platform.banking.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    
    List<Departamento> findByEmpresaId(Long empresaId);
    
    List<Departamento> findByDepartamentoPaiId(Long departamentoPaiId);
    
    List<Departamento> findByStatus(Departamento.StatusDepartamento status);
    
    @Query("SELECT d FROM Departamento d WHERE d.empresa.id = :empresaId AND d.status = 'ATIVO'")
    List<Departamento> findDepartamentosAtivosByEmpresa(@Param("empresaId") Long empresaId);
    
    @Query("SELECT d FROM Departamento d WHERE d.departamentoPai.id = :departamentoPaiId AND d.status = 'ATIVO'")
    List<Departamento> findSubDepartamentosAtivos(@Param("departamentoPaiId") Long departamentoPaiId);
    
    @Query("SELECT d FROM Departamento d WHERE d.empresa.id = :empresaId AND d.nivelHierarquico = :nivel")
    List<Departamento> findByEmpresaIdAndNivelHierarquico(@Param("empresaId") Long empresaId, @Param("nivel") Integer nivel);
    
    @Query("SELECT d FROM Departamento d WHERE d.nomeDepartamento ILIKE %:nome%")
    List<Departamento> findByNomeContainingIgnoreCase(@Param("nome") String nome);
    
    @Query("SELECT COUNT(d) FROM Departamento d WHERE d.empresa.id = :empresaId AND d.status = 'ATIVO'")
    Long countDepartamentosAtivosByEmpresa(@Param("empresaId") Long empresaId);
    
    boolean existsByCodigoDepartamentoAndEmpresaId(String codigoDepartamento, Long empresaId);
}
