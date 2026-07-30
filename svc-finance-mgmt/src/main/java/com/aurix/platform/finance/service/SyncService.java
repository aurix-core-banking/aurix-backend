package com.aurix.platform.finance.service;

import com.aurix.platform.finance.entity.ContaSincronizada;
import com.aurix.platform.finance.entity.TransacaoSincronizada;
import com.aurix.platform.finance.repository.ContaSincronizadaRepository;
import com.aurix.platform.finance.repository.TransacaoSincronizadaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class SyncService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SyncService.class);
    private final ContaSincronizadaRepository contaRepo;
    private final TransacaoSincronizadaRepository transacaoRepo;

    public SyncService(ContaSincronizadaRepository contaRepo, TransacaoSincronizadaRepository transacaoRepo) {
        this.contaRepo = contaRepo;
        this.transacaoRepo = transacaoRepo;
    }

    public ContaSincronizada sincronizarConta(String contaId, String clienteId, BigDecimal saldoInicial, LocalDateTime dataCriacao) {
        Optional<ContaSincronizada> existing = contaRepo.findByContaId(contaId);
        if (existing.isPresent()) {
            ContaSincronizada c = existing.get();
            if (c.getStatus() != ContaSincronizada.StatusSync.DESINCronizado) {
                log.info("Conta {} ja sincronizada, atualizando", contaId);
                c.setSaldoInicial(saldoInicial);
                c.setDataSincronizacao(LocalDateTime.now());
                return contaRepo.save(c);
            }
            contaRepo.delete(c);
            contaRepo.flush();
        }
        ContaSincronizada conta = new ContaSincronizada();
        conta.setContaId(contaId);
        conta.setClienteId(clienteId);
        conta.setSaldoInicial(saldoInicial);
        conta.setDataCriacao(dataCriacao);
        conta.setDataSincronizacao(LocalDateTime.now());
        conta.setStatus(ContaSincronizada.StatusSync.ATIVO);
        return contaRepo.save(conta);
    }

    public void desyncConta(String contaId) {
        Optional<ContaSincronizada> existing = contaRepo.findByContaId(contaId);
        if (existing.isPresent()) {
            ContaSincronizada c = existing.get();
            c.setStatus(ContaSincronizada.StatusSync.DESINCronizado);
            contaRepo.save(c);
            log.info("Conta {} desincronizada (compensation)", contaId);
        } else {
            log.warn("Conta {} nao encontrada para desync", contaId);
        }
    }

    public TransacaoSincronizada sincronizarTransacao(String transacaoId, String contaId, BigDecimal valor, String tipo, LocalDateTime dataTransacao) {
        Optional<TransacaoSincronizada> existing = transacaoRepo.findByTransacaoId(transacaoId);
        if (existing.isPresent()) {
            TransacaoSincronizada t = existing.get();
            if (t.getStatus() != TransacaoSincronizada.StatusSync.ESTORNADA) {
                log.info("Transacao {} ja sincronizada, atualizando", transacaoId);
                t.setValor(valor);
                t.setDataSincronizacao(LocalDateTime.now());
                return transacaoRepo.save(t);
            }
            transacaoRepo.delete(t);
            transacaoRepo.flush();
        }
        TransacaoSincronizada txn = new TransacaoSincronizada();
        txn.setTransacaoId(transacaoId);
        txn.setContaId(contaId);
        txn.setValor(valor);
        txn.setTipo(tipo);
        txn.setDataTransacao(dataTransacao);
        txn.setDataSincronizacao(LocalDateTime.now());
        txn.setStatus(TransacaoSincronizada.StatusSync.REGISTRADA);
        return transacaoRepo.save(txn);
    }

    public void desyncTransacao(String transacaoId) {
        Optional<TransacaoSincronizada> existing = transacaoRepo.findByTransacaoId(transacaoId);
        if (existing.isPresent()) {
            TransacaoSincronizada t = existing.get();
            t.setStatus(TransacaoSincronizada.StatusSync.ESTORNADA);
            transacaoRepo.save(t);
            log.info("Transacao {} estornada (compensation)", transacaoId);
        } else {
            log.warn("Transacao {} nao encontrada para desync", transacaoId);
        }
    }
}
