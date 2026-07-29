package com.aurix.platform.cards.repository;

import com.aurix.platform.cards.entity.LancamentoFatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LancamentoFaturaRepository extends JpaRepository<LancamentoFatura, Long> {

    List<LancamentoFatura> findByFaturaId(Long faturaId);
}
