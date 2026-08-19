package com.aurix.platform.contracts.assinatura.repository;

import com.aurix.platform.contracts.assinatura.entity.AssinaturaDigital;
import com.aurix.platform.contracts.assinatura.entity.StatusAssinaturaDigital;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssinaturaDigitalRepository extends JpaRepository<AssinaturaDigital, Long> {

    List<AssinaturaDigital> findByContratoId(Long contratoId);

    Optional<AssinaturaDigital> findByContratoIdAndClienteDocumento(Long contratoId, String clienteDocumento);

    Optional<AssinaturaDigital> findByContratoIdAndStatus(Long contratoId, StatusAssinaturaDigital status);
}
