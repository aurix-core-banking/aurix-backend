package com.aurix.platform.seguros.apolice.repository;

import com.aurix.platform.seguros.apolice.entity.Apolice;
import com.aurix.platform.seguros.apolice.entity.StatusApolice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApoliceRepository extends JpaRepository<Apolice, Long> {

    List<Apolice> findByClienteId(Long clienteId);

    List<Apolice> findByClienteIdAndStatus(Long clienteId, StatusApolice status);

    List<Apolice> findByStatus(StatusApolice status);
}
