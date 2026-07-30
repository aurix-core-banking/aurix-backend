package com.aurix.platform.banking.repository;

import com.aurix.platform.banking.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    
    Optional<Empresa> findByCodigoEmpresa(String codigoEmpresa);
    
    Optional<Empresa> findByCnpj(String cnpj);
    
    List<Empresa> findByStatus(Empresa.StatusEmpresa status);
    
    @Query("SELECT e FROM Empresa e WHERE e.nomeEmpresa ILIKE %:nome%")
    List<Empresa> findByNomeContainingIgnoreCase(@Param("nome") String nome);
    
    @Query("SELECT e FROM Empresa e WHERE e.status = 'ATIVA' ORDER BY e.nomeEmpresa")
    List<Empresa> findEmpresasAtivas();
    
    boolean existsByCodigoEmpresa(String codigoEmpresa);
    
    boolean existsByCnpj(String cnpj);
}
