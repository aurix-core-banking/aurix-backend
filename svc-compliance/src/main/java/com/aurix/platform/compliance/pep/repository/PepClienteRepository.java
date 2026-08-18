package com.aurix.platform.compliance.pep.repository;

import com.aurix.platform.compliance.pep.entity.PepCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PepClienteRepository extends JpaRepository<PepCliente, Long> {

    Optional<PepCliente> findByClienteId(Long clienteId);

    Optional<PepCliente> findByCpfCnpj(String cpfCnpj);

    List<PepCliente> findByStatus(PepCliente.StatusPep status);

    List<PepCliente> findByClassificacao(PepCliente.ClassificacaoPep classificacao);

    List<PepCliente> findByNivelRisco(PepCliente.NivelRiscoPep nivelRisco);

    @Query("SELECT p FROM PepCliente p WHERE p.status = 'ATIVO' ORDER BY p.nivelRisco DESC")
    List<PepCliente> findPepsAtivos();

    @Query("SELECT p FROM PepCliente p WHERE p.dataProximaVerificacao <= :agora AND p.status = 'ATIVO'")
    List<PepCliente> findPepsPendentesVerificacao(@Param("agora") LocalDateTime agora);

    @Query("SELECT p FROM PepCliente p WHERE p.nivelRisco IN ('ALTO', 'MUITO_ALTO') AND p.status = 'ATIVO'")
    List<PepCliente> findPepsAltoRisco();

    boolean existsByClienteId(Long clienteId);
}
