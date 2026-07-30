package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.FilaNotificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FilaNotificacaoRepository extends JpaRepository<FilaNotificacao, Long> {
    List<FilaNotificacao> findByClienteId(Long clienteId);
    List<FilaNotificacao> findByStatus(String status);
}
