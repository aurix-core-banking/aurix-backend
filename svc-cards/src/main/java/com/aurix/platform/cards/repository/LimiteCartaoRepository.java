package com.aurix.platform.cards.repository;

import com.aurix.platform.cards.entity.LimiteCartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LimiteCartaoRepository extends JpaRepository<LimiteCartao, Long> {

    Optional<LimiteCartao> findByCartaoId(Long cartaoId);
}
