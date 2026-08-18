package com.aurix.platform.payments.chargeback.repository;

import com.aurix.platform.shared.entity.ChargebackEvidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChargebackEvidenciaRepository extends JpaRepository<ChargebackEvidencia, Long> {

    List<ChargebackEvidencia> findByChargebackId(Long chargebackId);

    List<ChargebackEvidencia> findByChargebackIdAndTipoEvidencia(Long chargebackId, ChargebackEvidencia.TipoEvidencia tipoEvidencia);
}
