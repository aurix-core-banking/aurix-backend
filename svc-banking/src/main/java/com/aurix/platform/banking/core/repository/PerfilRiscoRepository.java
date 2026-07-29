package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.PerfilRisco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PerfilRiscoRepository extends JpaRepository<PerfilRisco, Long> {
    
    Optional<PerfilRisco> findByCodigoPerfil(String codigoPerfil);
    
    List<PerfilRisco> findByNivelRisco(PerfilRisco.NivelRisco nivelRisco);
    
    List<PerfilRisco> findByAtivo(Boolean ativo);
    
    @Query("SELECT p FROM PerfilRisco p WHERE p.nivelRisco = :nivelRisco AND p.ativo = true")
    List<PerfilRisco> findPerfisAtivosPorNivel(@Param("nivelRisco") PerfilRisco.NivelRisco nivelRisco);
    
    @Query("SELECT p FROM PerfilRisco p WHERE p.requerAprovacao = :requerAprovacao AND p.ativo = true")
    List<PerfilRisco> findPerfisQueRequeremAprovacao(@Param("requerAprovacao") Boolean requerAprovacao);
    
    @Query("SELECT p FROM PerfilRisco p WHERE p.requerDocumentacao = :requerDocumentacao AND p.ativo = true")
    List<PerfilRisco> findPerfisQueRequeremDocumentacao(@Param("requerDocumentacao") Boolean requerDocumentacao);
    
    @Query("SELECT p FROM PerfilRisco p WHERE p.requerBiometria = :requerBiometria AND p.ativo = true")
    List<PerfilRisco> findPerfisQueRequeremBiometria(@Param("requerBiometria") Boolean requerBiometria);
    
    @Query("SELECT p FROM PerfilRisco p WHERE p.requerToken = :requerToken AND p.ativo = true")
    List<PerfilRisco> findPerfisQueRequeremToken(@Param("requerToken") Boolean requerToken);
    
    @Query("SELECT p FROM PerfilRisco p WHERE p.requerAssinaturaDigital = :requerAssinaturaDigital AND p.ativo = true")
    List<PerfilRisco> findPerfisQueRequeremAssinaturaDigital(@Param("requerAssinaturaDigital") Boolean requerAssinaturaDigital);
    
    @Query("SELECT p FROM PerfilRisco p WHERE p.dataInicioVigencia <= :dataAtual AND (p.dataFimVigencia IS NULL OR p.dataFimVigencia >= :dataAtual) AND p.ativo = true")
    List<PerfilRisco> findPerfisVigentes(@Param("dataAtual") LocalDateTime dataAtual);
    
    @Query("SELECT p FROM PerfilRisco p WHERE p.valorLimiteDiario >= :valor AND p.ativo = true")
    List<PerfilRisco> findPerfisComLimiteDiarioSuficiente(@Param("valor") Double valor);
    
    @Query("SELECT p FROM PerfilRisco p WHERE p.valorLimiteMensal >= :valor AND p.ativo = true")
    List<PerfilRisco> findPerfisComLimiteMensalSuficiente(@Param("valor") Double valor);
    
    @Query("SELECT p FROM PerfilRisco p WHERE p.valorLimiteAnual >= :valor AND p.ativo = true")
    List<PerfilRisco> findPerfisComLimiteAnualSuficiente(@Param("valor") Double valor);
    
    @Query("SELECT p FROM PerfilRisco p WHERE p.quantidadeTransacoesDiarias >= :quantidade AND p.ativo = true")
    List<PerfilRisco> findPerfisComLimiteTransacoesDiariasSuficiente(@Param("quantidade") Integer quantidade);
    
    @Query("SELECT p FROM PerfilRisco p WHERE p.quantidadeTransacoesMensais >= :quantidade AND p.ativo = true")
    List<PerfilRisco> findPerfisComLimiteTransacoesMensaisSuficiente(@Param("quantidade") Integer quantidade);
    
    @Query("SELECT p FROM PerfilRisco p WHERE p.quantidadeTransacoesAnuais >= :quantidade AND p.ativo = true")
    List<PerfilRisco> findPerfisComLimiteTransacoesAnuaisSuficiente(@Param("quantidade") Integer quantidade);
}
