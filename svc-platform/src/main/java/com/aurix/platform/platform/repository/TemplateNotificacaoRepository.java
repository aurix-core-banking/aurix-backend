package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.TemplateNotificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TemplateNotificacaoRepository extends JpaRepository<TemplateNotificacao, Long> {
    Optional<TemplateNotificacao> findByCodigoAndAtivoTrue(String codigo);
}
