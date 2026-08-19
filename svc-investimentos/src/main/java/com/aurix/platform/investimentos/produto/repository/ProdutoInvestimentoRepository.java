package com.aurix.platform.investimentos.produto.repository;

import com.aurix.platform.investimentos.produto.entity.ProdutoInvestimento;
import com.aurix.platform.investimentos.produto.entity.TipoProdutoInvestimento;
import com.aurix.platform.investimentos.produto.entity.TipoRenda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoInvestimentoRepository extends JpaRepository<ProdutoInvestimento, Long> {

    List<ProdutoInvestimento> findByAtivoTrue();

    List<ProdutoInvestimento> findByTipo(TipoProdutoInvestimento tipo);

    List<ProdutoInvestimento> findByTipoRenda(TipoRenda tipoRenda);

    List<ProdutoInvestimento> findByTenantId(String tenantId);
}
