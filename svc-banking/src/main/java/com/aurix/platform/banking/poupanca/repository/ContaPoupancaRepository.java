package com.aurix.platform.banking.poupanca.repository;

import com.aurix.platform.banking.poupanca.entity.ContaPoupanca;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContaPoupancaRepository extends JpaRepository<ContaPoupanca, Long> {

    List<ContaPoupanca> findByClienteId(Long clienteId);

    Optional<ContaPoupanca> findByNumeroConta(String numeroConta);

    @Query("SELECT c FROM ContaPoupanca c WHERE c.aniversarioDia = :dia AND c.status = 'ATIVA'")
    List<ContaPoupanca> findContasParaAniversario(@Param("dia") int dia);
}
