package com.aurix.platform.banking.salario.repository;

import com.aurix.platform.banking.salario.entity.ContaSalario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContaSalarioRepository extends JpaRepository<ContaSalario, Long> {
    List<ContaSalario> findByTenantIdAndEmpresaId(String tenantId, Long empresaId);
    Optional<ContaSalario> findByTenantIdAndId(String tenantId, Long id);
    Optional<ContaSalario> findByTenantIdAndEmpresaIdAndMatriculaFuncionario(
        String tenantId, Long empresaId, String matriculaFuncionario);
    Optional<ContaSalario> findByTenantIdAndEmpresaIdAndCpfFuncionario(
        String tenantId, Long empresaId, String cpfFuncionario);
}
