package com.aurix.platform.finance.repository;

import com.aurix.platform.finance.entity.TransacaoSincronizada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TransacaoSincronizadaRepository extends JpaRepository<TransacaoSincronizada, Long> {
    Optional<TransacaoSincronizada> findByTransacaoId(String transacaoId);
    boolean existsByTransacaoId(String transacaoId);
    void deleteByTransacaoId(String transacaoId);
}
