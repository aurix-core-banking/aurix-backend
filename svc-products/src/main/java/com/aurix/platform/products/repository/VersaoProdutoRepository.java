package com.aurix.platform.products.repository;

import com.aurix.platform.products.entity.VersaoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersaoProdutoRepository extends JpaRepository<VersaoProduto, Long> {

    List<VersaoProduto> findByProdutoIdOrderByNumeroVersaoDesc(Long produtoId);

    boolean existsByProdutoId(Long produtoId);
}
