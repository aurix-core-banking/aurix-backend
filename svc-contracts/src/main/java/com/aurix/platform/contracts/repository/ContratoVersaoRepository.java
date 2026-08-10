package com.aurix.platform.contracts.repository;

import com.aurix.platform.contracts.entity.ContratoVersao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoVersaoRepository extends JpaRepository<ContratoVersao, Long> {

    List<ContratoVersao> findByContratoIdOrderByNumeroVersaoDesc(Long contratoId);
}
