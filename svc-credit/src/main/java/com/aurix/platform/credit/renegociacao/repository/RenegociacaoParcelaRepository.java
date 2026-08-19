package com.aurix.platform.credit.renegociacao.repository;

import com.aurix.platform.credit.renegociacao.entity.RenegociacaoParcela;
import com.aurix.platform.credit.renegociacao.entity.StatusParcelaRenegociacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RenegociacaoParcelaRepository extends JpaRepository<RenegociacaoParcela, Long> {

    List<RenegociacaoParcela> findByRenegociacaoId(Long renegociacaoId);

    List<RenegociacaoParcela> findByRenegociacaoIdAndStatus(Long renegociacaoId, StatusParcelaRenegociacao status);
}
