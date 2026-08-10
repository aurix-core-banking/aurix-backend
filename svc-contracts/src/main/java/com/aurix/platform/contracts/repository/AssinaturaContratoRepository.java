package com.aurix.platform.contracts.repository;

import com.aurix.platform.contracts.entity.AssinaturaContrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssinaturaContratoRepository extends JpaRepository<AssinaturaContrato, Long> {

    List<AssinaturaContrato> findByContratoId(Long contratoId);

    Optional<AssinaturaContrato> findByContratoIdAndAssinanteDocumento(Long contratoId, String assinanteDocumento);

    List<AssinaturaContrato> findByContratoIdAndAssinadaTrue(Long contratoId);

    void deleteByContratoId(Long contratoId);
}
