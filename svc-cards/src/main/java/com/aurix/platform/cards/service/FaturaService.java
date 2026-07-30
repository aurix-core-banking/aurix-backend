package com.aurix.platform.cards.service;

import com.aurix.platform.cards.client.ContaCorrenteClient;
import com.aurix.platform.cards.dto.FaturaDetalhadaResponse;
import com.aurix.platform.cards.dto.FaturaResponse;
import com.aurix.platform.cards.dto.PagarFaturaRequest;
import com.aurix.platform.cards.entity.Cartao;
import com.aurix.platform.cards.entity.Fatura;
import com.aurix.platform.cards.entity.Fatura.StatusFatura;
import com.aurix.platform.cards.entity.LancamentoFatura;
import com.aurix.platform.cards.repository.CartaoRepository;
import com.aurix.platform.cards.repository.FaturaRepository;
import com.aurix.platform.cards.repository.LancamentoFaturaRepository;
import com.aurix.platform.shared.event.CartaoFaturaFechadaEvent;
import com.aurix.platform.shared.event.CartaoFaturaPagaEvent;
import com.aurix.platform.shared.event.Topics;
import com.aurix.platform.shared.tenant.TenantContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class FaturaService {

    private static final Logger log = LoggerFactory.getLogger(FaturaService.class);

    private final FaturaRepository faturaRepository;
    private final LancamentoFaturaRepository lancamentoFaturaRepository;
    private final CartaoRepository cartaoRepository;
    private final ContaCorrenteClient contaCorrenteClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public FaturaService(FaturaRepository faturaRepository,
                         LancamentoFaturaRepository lancamentoFaturaRepository,
                         CartaoRepository cartaoRepository,
                         ContaCorrenteClient contaCorrenteClient,
                         KafkaTemplate<String, Object> kafkaTemplate) {
        this.faturaRepository = faturaRepository;
        this.lancamentoFaturaRepository = lancamentoFaturaRepository;
        this.cartaoRepository = cartaoRepository;
        this.contaCorrenteClient = contaCorrenteClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public FaturaResponse fecharFatura(Long cartaoId, Integer mes, Integer ano) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
            .orElseThrow(() -> new IllegalArgumentException("Cartao nao encontrado: " + cartaoId));

        String tenantId = TenantContext.getTenantId();

        Fatura fatura = new Fatura();
        fatura.setCodigoFatura("FAT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        fatura.setCartaoId(cartaoId);
        fatura.setMesReferencia(mes);
        fatura.setAnoReferencia(ano);
        fatura.setStatus(StatusFatura.FECHADA);
        fatura.setValorTotal(BigDecimal.ZERO);
        fatura.setValorPago(BigDecimal.ZERO);
        fatura.setValorPendente(BigDecimal.ZERO);
        fatura.setValorMinimo(BigDecimal.ZERO);
        fatura.setDataVencimento(LocalDate.of(ano, mes, cartao.getDiaVencimentoFatura()).plusMonths(1));
        fatura.setDataFechamento(LocalDateTime.now());
        fatura.setDataGeracao(LocalDateTime.now());
        fatura = faturaRepository.save(fatura);

        LancamentoFatura lanc = new LancamentoFatura();
        lanc.setFaturaId(fatura.getId());
        lanc.setDescricao("Fatura " + mes + "/" + ano + " - Cartao " + cartao.getNumeroCartaoMascarado());
        lanc.setValor(cartao.getLimiteUtilizado());
        lanc.setDataLancamento(LocalDateTime.now());
        lanc.setCategoria("FATURA");
        lancamentoFaturaRepository.save(lanc);

        fatura.setValorTotal(cartao.getLimiteUtilizado());
        fatura.setValorPendente(cartao.getLimiteUtilizado());
        fatura.setValorMinimo(cartao.getLimiteUtilizado().multiply(BigDecimal.valueOf(0.1)));
        fatura = faturaRepository.save(fatura);

        kafkaTemplate.send(Topics.CARTOES_FATURA_FECHADA, String.valueOf(fatura.getId()),
                CartaoFaturaFechadaEvent.fechada(
                        fatura.getId(), fatura.getCartaoId(), fatura.getMesReferencia(),
                        fatura.getAnoReferencia(), fatura.getValorTotal(), fatura.getCartaoId()));

        log.info("Fatura fechada: id={}, cartaoId={}, mes={}, ano={}, valor={}",
            fatura.getId(), cartaoId, mes, ano, fatura.getValorTotal());
        return toResponse(fatura);
    }

    @Transactional
    public int atualizarFaturasVencidas() {
        var hoje = LocalDate.now();
        var faturasVencendo = faturaRepository.findFaturasAVencer(hoje);
        for (var fatura : faturasVencendo) {
            fatura.setStatus(StatusFatura.VENCIDA);
            faturaRepository.save(fatura);
        }
        if (!faturasVencendo.isEmpty()) {
            log.info("Faturas atualizadas para VENCIDA: {}", faturasVencendo.size());
        }
        return faturasVencendo.size();
    }

    public FaturaResponse pagarFatura(Long faturaId, PagarFaturaRequest request) {
        Fatura fatura = faturaRepository.findById(faturaId)
            .orElseThrow(() -> new IllegalArgumentException("Fatura nao encontrada: " + faturaId));

        if (fatura.getStatus() == StatusFatura.PAGA) {
            throw new IllegalStateException("Fatura ja esta paga");
        }

        String tenantId = TenantContext.getTenantId();
        BigDecimal valorPagamento = request.getValorPagamento();
        BigDecimal novoValorPago = fatura.getValorPago().add(valorPagamento);
        fatura.setValorPago(novoValorPago);
        fatura.setValorPendente(fatura.getValorTotal().subtract(novoValorPago));
        fatura.setDataPagamento(LocalDate.now());

        boolean pagamentoTotal = fatura.getValorPendente().compareTo(BigDecimal.ZERO) <= 0;

        if (pagamentoTotal) {
            fatura.setStatus(StatusFatura.PAGA);
            Cartao cartao = cartaoRepository.findById(fatura.getCartaoId())
                .orElseThrow(() -> new IllegalArgumentException("Cartao nao encontrado: " + fatura.getCartaoId()));
            cartao.setLimiteUtilizado(cartao.getLimiteUtilizado().subtract(fatura.getValorTotal()));
            cartao.setLimiteDisponivel(cartao.getLimiteCredito().subtract(cartao.getLimiteUtilizado()));
            cartaoRepository.save(cartao);
        } else {
            Cartao cartao = cartaoRepository.findById(fatura.getCartaoId())
                .orElseThrow(() -> new IllegalArgumentException("Cartao nao encontrado: " + fatura.getCartaoId()));
            BigDecimal valorRotativo = valorPagamento;
            try {
                contaCorrenteClient.creditar(cartao.getContaId(),
                    new ContaCorrenteClient.CreditoRequest(valorRotativo,
                        "Credito revolving fatura " + fatura.getCodigoFatura()));
            } catch (Exception e) {
                log.warn("Falha ao creditar revolving para fatura {}: {}", faturaId, e.getMessage());
            }
        }

        faturaRepository.save(fatura);

        kafkaTemplate.send(Topics.CARTOES_FATURA_PAGA, String.valueOf(fatura.getId()),
                CartaoFaturaPagaEvent.paga(
                        fatura.getId(), fatura.getCartaoId(), fatura.getValorPago(), fatura.getCartaoId()));

        log.info("Fatura paga: id={}, valorPago={}", fatura.getId(), fatura.getValorPago());
        return toResponse(fatura);
    }

    @Transactional(readOnly = true)
    public FaturaDetalhadaResponse consultarFatura(Long faturaId) {
        Fatura fatura = faturaRepository.findById(faturaId)
            .orElseThrow(() -> new IllegalArgumentException("Fatura nao encontrada: " + faturaId));

        List<LancamentoFatura> lancamentos = lancamentoFaturaRepository.findByFaturaId(faturaId);

        FaturaDetalhadaResponse response = new FaturaDetalhadaResponse();
        response.setId(fatura.getId());
        response.setCodigoFatura(fatura.getCodigoFatura());
        response.setCartaoId(fatura.getCartaoId());
        response.setMesReferencia(fatura.getMesReferencia());
0\r§mûü   ¦  [Ôf    _keyhttps://chat.google.com/_/scs/mss-static/_/js/k=boq-dynamite.DynamiteWebUi.pt_BR.mZ7TYLOycQg.2020.O/ck=boq-dynamite.DynamiteWebUi.j5pTWmLBqlc.L.B1.O/am=5faHON4ky0QCQ9GYhuZhjDEOAvwWGElLECPQAAAAACkHaMMMgJApBA/d=1/exm=A7fCU,ADc1Qc,AUnC5d,AVbx5d,Bawggc,BvTWuc,CmPHhe,D24Yib,D8JrFc,DlUNQb,Dt2gCf,EDEDVd,EFQ78c,EN6Flf,EXCSKc,ExeBRd,FWbgnf,FjL88d,FzWyAd,G37fEe,GnCa3c,HFL46d,HI3kIb,HMzQbf,HT8XDe,Hd4Pff,HyV36c,IiC5yd,InC9Ub,J7N9C,JH6QEd,JNoxi,JQpsj,JVePRc,K7o0rf,KXYl2d,KdO0tc,Kdrenc,KhKOW,KjiSxc,L1AAkb,LAexbf,LEikZe,LHQHXe,Lgapxc,Mb9s2d,MpJwZc,NWS6Fb,NXrYEe,O6y8ed,P2Dmtd,P6sQOc,PKeqcf,PaZbEe,PgqL1e,Ph6DJc,QzZDqf,RMhBfe,SBvVne,SljRjc,T9U3df,TELHZb,TjMdgd,U8QHR,UJmqp,UO2sTd,UOHGWe,UUJqVe,Uas9Hd,Ulmmrd,UmkMob,VGENab,Vui2Cf,WO9ee,WOh2n,Xl5qNd,Xlgikf,XnyN2e,YHtpn,YgYfYe,ZVwchc,ZwDk9d,ZwmEcd,_b,_tp,aW3pY,audrJd,byfTOb,cUFky,cr0IMb,d4us9d,dWV0nd,dXeBNb,dc5asf,e13S9d,ePHIRd,eaOP3d,er6dh,evLPce,f7XYPd,fgj8Rb,g4eCSd,gTs7K,gepemd,gumPOd,hAljs,hqV0Bd,iKbDcd,j0Phob,jdQAx,jjSbgf,jkL5Y,kjdOUc,knP6Vb,lR8EEc,lfZYrc,ljBCgd,lsjVmc,lwddkf,mSX26d,n73qwf,oIlWU,p5tslb,pMYwdd,pyllr,q2CZ6,q4ha0e,qR3pt,r1uDVb,r65Xrf,r91Udd,sCvRpf,skvfJb,t0g5vc,tSw7nc,uff4cb,uqJhG,vGs3rf,w7Iwxd,wDcr7e,wPW5Je,xFm5He,xiIt6d,xlrZDb,yWLuYe,yXJmEe,yazeCb,yb08jf,zDD95,zVtWRd/excm=LEikZe,MpJwZc,UUJqVe,_b,_tp,byfTOb,lsjVmc,n73qwf,worldmoleview/ed=1/br=1/wt=2/ujg=1/rs=AKbVGRJWr0BROZEfqszm5CaGbxWJ_sahxw/ee=A5y9vd:zD4Om;ALeOPe:DTA9dd;Arv56d:UdO36;AzI