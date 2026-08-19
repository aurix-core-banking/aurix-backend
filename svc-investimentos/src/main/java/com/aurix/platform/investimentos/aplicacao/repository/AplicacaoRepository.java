package com.aurix.platform.investimentos.aplicacao.repository;

import com.aurix.platform.investimentos.aplicacao.entity.Aplicacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AplicacaoRepository extends JpaRepository<Aplicacao, Long> {

    List<Aplicacao> findByClienteId(Long clienteId);

    List<Aplicacao> findByClienteIdAndStatus(Long clienteId, com.aurix.platform.investimentos.aplicacao.entity.StatusAplicacao status);
}
