package com.aurix.platform.seguros.sinistro.repository;

import com.aurix.platform.seguros.sinistro.entity.DocumentoSinistro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoSinistroRepository extends JpaRepository<DocumentoSinistro, Long> {

    List<DocumentoSinistro> findBySinistroId(Long sinistroId);
}
