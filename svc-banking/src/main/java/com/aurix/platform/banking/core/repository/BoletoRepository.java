package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoletoRepository extends JpaRepository<Boleto, Long> {

    Optional<Boleto> findByNumeroBoleto(String numeroBoleto);

    Optional<Boleto> findByLinhaDigitavel(String linhaDigitavel);

    List<Boleto> findByContaIdPagador(Long contaIdPagador);

    List<Boleto> findByStatus(Boleto.StatusBoleto status);
}
