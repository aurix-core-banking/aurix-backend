package com.aurix.platform.seguros.sinistro.repository;

import com.aurix.platform.seguros.sinistro.entity.Sinistro;
import com.aurix.platform.seguros.sinistro.entity.StatusSinistro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SinistroRepository extends JpaRepository<Sinistro, Long> {

    List<Sinistro> findByApoliceId(Long apoliceId);

    List<Sinistro> findByClienteId(Long clienteId);

    List<Sinistro> findByStatus(StatusSinistro status);
}
