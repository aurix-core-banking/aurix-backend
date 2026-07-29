package com.aurix.platform.cards.repository;

import com.aurix.platform.cards.entity.ParceiroBandeira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParceiroBandeiraRepository extends JpaRepository<ParceiroBandeira, Long> {

    Optional<ParceiroBandeira> findByNome(String nome);

    List<ParceiroBandeira> findByAtivoTrue();
}
