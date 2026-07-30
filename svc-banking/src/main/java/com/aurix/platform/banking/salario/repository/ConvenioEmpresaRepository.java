package com.aurix.platform.banking.salario.repository;

import com.aurix.platform.banking.salario.entity.ConvenioEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConvenioEmpresaRepository extends JpaRepository<ConvenioEmpresa, Long> {
    List<ConvenioEmpresa> findByTenantId(String tenantId);
    Optional<ConvenioEmpresa> findByTenantIdAndId(String tenantId, Long id);
    Optional<ConvenioEmpresa> findByTenantIdAndCnpj(String tenantId, String cnpj);
    Optional<ConvenioEmpresa> findByTenantIdAndRazaoSocial(String tenantId, String razaoSocial);
}
