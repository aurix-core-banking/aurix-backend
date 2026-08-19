package com.aurix.platform.credit.renegociacao.repository;

import com.aurix.platform.credit.renegociacao.entity.Renegociacao;
import com.aurix.platform.credit.renegociacao.entity.StatusRenegociacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RenegociacaoRepository extends JpaRepository<Renegociacao, Long> {

    List<Renegociacao> findByContratoOriginalId(Long contratoOriginalId);

    List<Renegociacao> findByClienteId(Long clienteId);

    List<Renegociacao> findByClienteIdAndStatus(Long clienteId, StatusRenegociacao status);

    List<Renegociacao> findByTenantIdAndStatus(String tenantId, StatusRenegociacao status);
}
