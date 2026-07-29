package com.aurix.platform.cambio.repository;

import com.aurix.platform.cambio.entity.Cotacao;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CotacaoRepository extends JpaRepository<Cotacao, Long> {

    Optional<Cotacao> findFirstByMoedaOrderByDataCotacaoDesc(String moeda);

    List<Cotacao> findByMoedaAndDataCotacaoAfter(String moeda, LocalDateTime data);
}
