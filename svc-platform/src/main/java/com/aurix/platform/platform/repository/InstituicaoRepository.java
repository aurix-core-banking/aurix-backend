package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {

    Optional<Instituicao> findByTenantId(String tenantId);

    Optional<Instituicao> findByCnpj(String cnpj);

    boolean existsByTenantId(String tenantId);

    boolean existsByCnpj(String cnpj);
}
