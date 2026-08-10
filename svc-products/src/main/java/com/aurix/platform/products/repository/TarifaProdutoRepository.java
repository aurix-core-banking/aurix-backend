package com.aurix.platform.products.repository;

import com.aurix.platform.products.entity.TarifaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarifaProdutoRepository extends JpaRepository<TarifaProduto, Long> {

    List<TarifaProduto> findByProdutoId(Long produtoId);

    void deleteByProdutoId(Long produtoId);
}
