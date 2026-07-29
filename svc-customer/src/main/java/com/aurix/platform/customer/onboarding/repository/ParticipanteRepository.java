package com.aurix.platform.customer.onboarding.repository;

import com.aurix.platform.customer.onboarding.entity.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {

    List<Participante> findBySolicitacaoId(Long solicitacaoId);
}
