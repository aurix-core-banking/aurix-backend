package com.aurix.platform.banking.repository;

import com.aurix.platform.banking.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
    
    List<Cargo> findByEmpresaId(Long empresaId);
    
    List<Cargo> findByDepartamentoId(Long departamentoId);
    
    List<Cargo> findByCargoSuperiorId(Long cargoSuperiorId);
    
    List<Cargo> findByStatus(Cargo.StatusCargo status);
    
    @Query("SELECT c FROM Cargo c WHERE c.empresa.id = :empresaId AND c.status = 'ATIVO'")
    List<Cargo> findCargosAtivosByEmpresa(@Param("empresaId") Long empresaId);
    
    @Query("SELECT c FROM Cargo c WHERE c.departamento.id = :departamentoId AND c.status = 'ATIVO'")
    List<Cargo> findCargosAtivosByDepartamento(@Param("departamentoId") Long departamentoId);
    
    @Query("SELECT c FROM Cargo c WHERE c.cargoSuperior.id = :cargoSuperiorId AND c.status = 'ATIVO'")
    List<Cargo> findCargosSubordinadosAtivos(@Param("cargoSuperiorId") Long cargoSuperiorId);
    
    @Query("SELECT c FROM Cargo c WHERE c.empresa.id = :empresaId AND c.nivelCargo = :nivelCargo")
    List<Cargo> findByEmpresaIdAndNivelCargo(@Param("empresaId") Long empresaId, @Param("nivelCargo") Cargo.NivelCargo nivelCargo);
    
    @Query("SELECT c FROM Cargo c WHERE c.empresa.id = :empresaId AND c.nivelHierarquico >= :nivelMinimo")
    List<Cargo> findByEmpresaIdAndNivelHierarquicoMinimo(@Param("empresaId") Long empresaId, @Param("nivelMinimo") Integer nivelMinimo);
    
    @Query("SELECT c FROM Cargo c WHERE c.nomeCargo ILIKE %:nome%")
    List<Cargo> findByNomeContainingIgnoreCase(@Param("nome") String nome);
    
    @Query("SELECT COUNT(c) FROM Cargo c WHERE c.empresa.id = :empresaId AND c.status = 'ATIVO'")
    Long countCargosAtivosByEmpresa(@Param("empresaId") Long empresaId);
    
    @Query("SELECT COUNT(c) FROM Cargo c WHERE c.departamento.id = :departamentoId AND c.status = 'ATIVO'")
    Long countCargosAtivosByDepartamento(@Param("departamentoId") Long departamentoId);
    
    boolean existsByCodigoCargoAndEmpresaId(String codigoCargo, Long empresaId);
}
