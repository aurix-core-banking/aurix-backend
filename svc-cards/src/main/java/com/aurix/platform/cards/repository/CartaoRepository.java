package com.aurix.platform.cards.repository;

import com.aurix.platform.cards.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    Optional<Cartao> findByNumeroCartao(String numeroCartao);

    List<Cartao> findByContaId(Long contaId);

    List<Cartao> findByStatus(Cartao.StatusCartao status);

    List<Cartao> findByTipoCartao(Cartao.TipoCartao tipoCartao);

    List<Cartao> findByProdutoId(Long produtoId);

    List<Cartao> findByBandeiraParceiroId(Long bandeiraParceiroId);

    List<Cartao> findByTenantId(String tenantId);
}
