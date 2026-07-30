package com.aurix.platform.credit.credit.repository;

import com.aurix.platform.credit.credit.entity.ProdutoCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoCreditoRepository extends JpaRepository<ProdutoCredito, Long> {

    Optional<ProdutoCredito> findByCodigo(String codigo);

    List<ProdutoCredito> findByAtivoTrue();

    List<ProdutoCredito> findByTipoCredito(ProdutoCredito.TipoCredito tipoCredito);
}
