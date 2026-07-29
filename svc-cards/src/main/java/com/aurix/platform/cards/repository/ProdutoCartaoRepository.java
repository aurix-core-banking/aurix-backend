package com.aurix.platform.cards.repository;

import com.aurix.platform.cards.entity.ProdutoCartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoCartaoRepository extends JpaRepository<ProdutoCartao, Long> {

    List<ProdutoCartao> findByAtivoTrue();

    Optional<ProdutoCartao> findByBandeira(String bandeira);
}
