package com.aurix.platform.compliance.repository;

import com.aurix.platform.compliance.entity.PortabilidadeDados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PortabilidadeDadosRepository extends JpaRepository<PortabilidadeDados, Long> {

    Optional<PortabilidadeDados> findByCodigoPortabilidade(String codigoPortabilidade);

    List<PortabilidadeDados> findByClienteId(Long clienteId);

    List<PortabilidadeDados> findByCpfCnpj(String cpfCnpj);

    List<PortabilidadeDados> findByStatus(PortabilidadeDados.StatusPortabilidade status);

    @Query("SELECT p FROM PortabilidadeDados p WHERE p.status = 'DISPONIVEL' AND p.dataExpiracao < :agora")
    List<PortabilidadeDados> findPortabilidadesExpiradas(@Param("agora") LocalDateTime agora);
}
