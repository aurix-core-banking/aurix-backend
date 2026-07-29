package com.aurix.platform.banking.repository;

import com.aurix.platform.banking.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    
    Optional<Funcionario> findByMatricula(String matricula);
    
    Optional<Funcionario> findByCpf(String cpf);
    
    Optional<Funcionario> findByEmail(String email);
    
    List<Funcionario> findByEmpresaId(Long empresaId);
    
    List<Funcionario> findByDepartamentoId(Long departamentoId);
    
    List<Funcionario> findByCargoId(Long cargoId);
    
    List<Funcionario> findByGestorId(Long gestorId);
    
    List<Funcionario> findByStatus(Funcionario.StatusFuncionario status);
    
    @Query("SELECT f FROM Funcionario f WHERE f.empresa.id = :empresaId AND f.status = 'ATIVO'")
    List<Funcionario> findFuncionariosAtivosByEmpresa(@Param("empresaId") Long empresaId);
    
    @Query("SELECT f FROM Funcionario f WHERE f.departamento.id = :departamentoId AND f.status = 'ATIVO'")
    List<Funcionario> findFuncionariosAtivosByDepartamento(@Param("departamentoId") Long departamentoId);
    
    @Query("SELECT f FROM Funcionario f WHERE f.cargo.id = :cargoId AND f.status = 'ATIVO'")
    List<Funcionario> findFuncionariosAtivosByCargo(@Param("cargoId") Long cargoId);
    
    @Query("SELECT f FROM Funcionario f WHERE f.gestor.id = :gestorId AND f.status = 'ATIVO'")
    List<Funcionario> findSubordinadosAtivos(@Param("gestorId") Long gestorId);
    
    @Query("SELECT f FROM Funcionario f WHERE f.nomeCompleto ILIKE %:nome%")
    List<Funcionario> findByNomeContainingIgnoreCase(@Param("nome") String nome);
    
    @Query("SELECT f FROM Funcionario f WHERE f.empresa.id = :empresaId AND f.cargo.nivelCargo = :nivelCargo")
    List<Funcionario> findByEmpresaIdAndNivelCargo(@Param("empresaId") Long empresaId, @Param("nivelCargo") String nivelCargo);
    
    @Query("SELECT f FROM Funcionario f WHERE f.empresa.id = :empresaId AND f.cargo.nivelHierarquico >= :nivelMinimo")
    List<Funcionario> findByEmpresaIdAndNivelHierarquicoMinimo(@Param("empresaId") Long empresaId, @Param("nivelMinimo") Integer nivelMinimo);
    
    boolean existsByMatricula(String matricula);
    
    boolean existsByCpf(String cpf);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT COUNT(f) FROM Funcionario f WHERE f.empresa.id = :empresaId AND f.status = 'ATIVO'")
    Long countFuncionariosAtivosByEmpresa(@Param("empresaId") Long empresaId);
    
    @Query("SELECT COUNT(f) FROM Funcionario f WHERE f.departamento.id = :departamentoId AND f.status = 'ATIVO'")
    Long countFuncionariosAtivosByDepartamento(@Param("departamentoId") Long departamentoId);
}
