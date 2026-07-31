package com.aurix.platform.compliance.repository;

import com.aurix.platform.compliance.entity.LgpdExclusao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LgpdExclusaoRepository extends JpaRepository<LgpdExclusao, Long> {

    Optional<LgpdExclusao> findByClienteId(Long clienteId);

    boolean existsByClienteId(Long clienteId);
}