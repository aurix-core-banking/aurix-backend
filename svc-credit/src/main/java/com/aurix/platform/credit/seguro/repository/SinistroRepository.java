package com.aurix.platform.credit.seguro.repository;

import com.aurix.platform.credit.seguro.entity.Sinistro;
import com.aurix.platform.credit.seguro.entity.StatusSinistro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SinistroRepository extends JpaRepository<Sinistro, Long> {

    List<Sinistro> findBySeguroId(Long seguroId);

    List<Sinistro> findBySeguroIdAndStatus(Long seguroId, StatusSinistro status);
}
