package com.aurix.platform.banking.salario.repository;

import com.aurix.platform.banking.salario.entity.ItemFolhaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemFolhaPagamentoRepository extends JpaRepository<ItemFolhaPagamento, Long> {
    List<ItemFolhaPagamento> findByFolhaId(Long folhaId);
    List<ItemFolhaPagamento> findByStatus(ItemFolhaPagamento.StatusItem status);
}
