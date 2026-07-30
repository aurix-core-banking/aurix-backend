package com.aurix.platform.cards.repository;

import com.aurix.platform.cards.entity.ParceiroAdquirente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParceiroAdquirenteRepository extends JpaRepository<ParceiroAdquirente, Long> {

    Optional<ParceiroAdquirente> findByNome(String nome);

    List<ParceiroAdquirente> findByAtivoTrue();
}
