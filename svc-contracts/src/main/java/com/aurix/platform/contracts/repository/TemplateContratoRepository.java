package com.aurix.platform.contracts.repository;

import com.aurix.platform.contracts.entity.Contrato;
import com.aurix.platform.contracts.entity.TemplateContrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemplateContratoRepository extends JpaRepository<TemplateContrato, Long> {

    Optional<TemplateContrato> findByCodigo(String codigo);

    List<TemplateContrato> findByTipoContrato(Contrato.TipoContrato tipoContrato);

    List<TemplateContrato> findByStatus(TemplateContrato.StatusTemplate status);

    boolean existsByCodigo(String codigo);
}
