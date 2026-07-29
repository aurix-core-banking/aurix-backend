package com.aurix.platform.customer.onboarding.repository;

import com.aurix.platform.customer.onboarding.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Optional<Empresa> findBySolicitacaoId(Long solicitacaoId);

    Optional<Empresa> findByCnpj(String cnpj);
}
