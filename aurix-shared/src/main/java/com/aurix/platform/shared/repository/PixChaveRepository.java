package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.PixChave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PixChaveRepository extends JpaRepository<PixChave, Long> {

    Optional<PixChave> findByChavePix(String chavePix);

    List<PixChave> findByContaId(Long contaId);

    @Query("SELECT p FROM PixChave p WHERE p.conta.id = :contaId AND p.status = 'ATIVA'")
    List<PixChave> findChavesAtivasByContaId(@Param("contaId") Long contaId);

    List<PixChave> findByTipoChave(PixChave.TipoChavePix tipoChave);

    List<PixChave> findByStatus(PixChave.StatusChavePix status);

    boolean existsByChavePix(String chavePix);

    @Query("SELECT COUNT(p) > 0 FROM PixChave p WHERE p.conta.id = :contaId AND p.status = 'ATIVA'")
    boolean existsChaveAtivaByContaId(@Param("contaId") Long contaId);

    @Query("SELECT p FROM PixChave p WHERE p.chavePix = :chavePix AND p.status = 'ATIVA'")
    Optional<PixChave> findChaveAtivaByChavePix(@Param("chavePix") String chavePix);

    long countByStatus(PixChave.StatusChavePix status);

    long countByTipoChave(PixChave.TipoChavePix tipoChave);

    long countByContaId(Long contaId);
}
