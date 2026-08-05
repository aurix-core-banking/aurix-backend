package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.ConfirmacaoRecebimento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConfirmacaoRecebimentoRepository extends JpaRepository<ConfirmacaoRecebimento, Long> {
    List<ConfirmacaoRecebimento> findByClienteId(Long clienteId);
    List<ConfirmacaoRecebimento> findByFilaNotificacaoId(Long filaNotificacaoId);
}
