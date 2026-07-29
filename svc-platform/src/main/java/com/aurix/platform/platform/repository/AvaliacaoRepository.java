package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.Avaliacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> findByBemIdOrderByDataDesc(Long bemId);
}
