package com.aurix.platform.finance.repository;

import com.aurix.platform.finance.entity.ContaSincronizada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ContaSincronizadaRepository extends JpaRepository<ContaSincronizada, Long> {
    Optional<ContaSincronizada> findByContaId(String contaId);
    boolean existsByContaId(String contaId);
    void deleteByContaId(String contaId);
}
