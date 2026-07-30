package com.aurix.platform.fraud.repository;

import com.aurix.platform.fraud.entity.ScoreTransacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreTransacaoRepository extends JpaRepository<ScoreTransacao, Long> {
    List<ScoreTransacao> findByClienteId(Long clienteId);
    Optional<ScoreTransacao> findByTransacaoRef(String transacaoRef);
}
