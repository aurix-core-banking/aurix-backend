package com.aurix.platform.credit.consignado.repository;

import com.aurix.platform.credit.consignado.entity.Parcela;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelaRepository extends JpaRepository<Parcela, Long> {

    List<Parcela> findByContratoId(Long contratoId);

    List<Parcela> findByStatusAndDataVencimentoBefore(String status, LocalDate data);
}
