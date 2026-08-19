package com.aurix.platform.seguros.produto.repository;

import com.aurix.platform.seguros.produto.entity.ProdutoSeguro;
import com.aurix.platform.seguros.produto.entity.TipoSeguro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoSeguroRepository extends JpaRepository<ProdutoSeguro, Long> {

    List<ProdutoSeguro> findByAtivoTrue();

    List<ProdutoSeguro> findByTipo(TipoSeguro tipo);

    List<ProdutoSeguro> findByTenantId(String tenantId);
}
