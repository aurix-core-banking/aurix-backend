package com.aurix.platform.compliance.repository;

import com.aurix.platform.compliance.entity.ConsentimentoLGPD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsentimentoLGPDRepository extends JpaRepository<ConsentimentoLGPD, Long> {

    Optional<ConsentimentoLGPD> findByCodigoConsentimento(String codigoConsentimento);

    List<ConsentimentoLGPD> findByClienteId(Long clienteId);

    List<ConsentimentoLGPD> findByCpfCnpj(String cpfCnpj);

    List<ConsentimentoLGPD> findByStatus(ConsentimentoLGPD.StatusConsentimento status);

    List<ConsentimentoLGPD> findByTipoConsentimento(ConsentimentoLGPD.TipoConsentimento tipoConsentimento);

    @Query("SELECT c FROM ConsentimentoLGPD c WHERE c.clienteId = :clienteId AND c.status = 'CONCEDIDO' AND (c.dataExpiracao IS NULL OR c.dataExpiracao > :agora)")
    List<ConsentimentoLGPD> findConsentimentosAtivosPorCliente(@Param("clienteId") Long clienteId, @Param("agora") LocalDateTime agora);

    @Query("SELECT c FROM ConsentimentoLGPD c WHERE c.dataExpiracao IS NOT NULL AND c.dataExpiracao < :agora AND c.status = 'CONCEDIDO'")
    List<ConsentimentoLGPD> findConsentimentosExpirados(@Param("agora") LocalDateTime agora);
}
