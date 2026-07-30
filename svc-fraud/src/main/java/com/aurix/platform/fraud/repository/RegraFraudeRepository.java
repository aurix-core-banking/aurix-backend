package com.aurix.platform.fraud.repository;

import com.aurix.platform.fraud.entity.RegraFraude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RegraFraudeRepository extends JpaRepository<RegraFraude, Long> {
    List<RegraFraude> findByAtivoTrueOrderByPrioridadeDesc();
    List<RegraFraude> findByTipo(String tipo);
}
