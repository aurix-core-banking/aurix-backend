package com.aurix.platform.products.repository;

import com.aurix.platform.products.entity.RegraElegibilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegraElegibilidadeRepository extends JpaRepository<RegraElegibilidade, Long> {

    List<RegraElegibilidade> findByProdutoId(Long produtoId);

    List<RegraElegibilidade> findByProdutoIdAndAtivaTrue(Long produtoId);

    void deleteByProdutoId(Long produtoId);
}
