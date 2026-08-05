package com.aurix.platform.compliance.repository;

import com.aurix.platform.compliance.entity.AnonimizacaoDados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnonimizacaoDadosRepository extends JpaRepository<AnonimizacaoDados, Long> {

    Optional<AnonimizacaoDados> findByCodigoAnonimizacao(String codigoAnonimizacao);

    List<AnonimizacaoDados> findByClienteId(Long clienteId);

    List<AnonimizacaoDados> findByCpfCnpj(String cpfCnpj);

    List<AnonimizacaoDados> findByStatus(AnonimizacaoDados.StatusAnonimizacao status);
}
