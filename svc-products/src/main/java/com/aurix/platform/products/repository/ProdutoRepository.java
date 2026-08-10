package com.aurix.platform.products.repository;

import com.aurix.platform.products.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByCodigo(String codigo);

    List<Produto> findByTipoProduto(Produto.TipoProduto tipoProduto);

    List<Produto> findByStatus(Produto.StatusProduto status);

    List<Produto> findByAtivo(Boolean ativo);

    boolean existsByCodigo(String codigo);
}
