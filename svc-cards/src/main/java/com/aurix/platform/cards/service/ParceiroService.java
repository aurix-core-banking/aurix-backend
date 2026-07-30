package com.aurix.platform.cards.service;

import com.aurix.platform.cards.dto.AuditMetaDTO;
import com.aurix.platform.cards.dto.ParceiroAdquirenteRequest;
import com.aurix.platform.cards.dto.ParceiroAdquirenteResponse;
import com.aurix.platform.cards.dto.ParceiroBandeiraRequest;
import com.aurix.platform.cards.dto.ParceiroBandeiraResponse;
import com.aurix.platform.cards.entity.ParceiroAdquirente;
import com.aurix.platform.cards.entity.ParceiroBandeira;
import com.aurix.platform.cards.repository.ParceiroAdquirenteRepository;
import com.aurix.platform.cards.repository.ParceiroBandeiraRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ParceiroService {

    private static final Logger log = LoggerFactory.getLogger(ParceiroService.class);

    private final ParceiroBandeiraRepository parceiroBandeiraRepository;
    private final ParceiroAdquirenteRepository parceiroAdquirenteRepository;

    public ParceiroService(ParceiroBandeiraRepository parceiroBandeiraRepository,
                           ParceiroAdquirenteRepository parceiroAdquirenteRepository) {
        this.parceiroBandeiraRepository = parceiroBandeiraRepository;
        this.parceiroAdquirenteRepository = parceiroAdquirenteRepository;
    }

    public ParceiroBandeiraResponse criarBandeira(ParceiroBandeiraRequest request) {
        ParceiroBandeira bandeira = new ParceiroBandeira();
        bandeira.setNome(request.getNome());
        bandeira.setTipoEndpoint(request.getTipoEndpoint());
        bandeira.setConfig(request.getConfig());
        bandeira.setAtivo(true);
        bandeira = parceiroBandeiraRepository.save(bandeira);
        log.info("ParceiroBandeira criado: id={}, nome={}", bandeira.getId(), bandeira.getNome());
        return toBandeiraResponse(bandeira);
    }

    public ParceiroAdquirenteResponse criarAdquirente(ParceiroAdquirenteRequest request) {
        ParceiroAdquirente adquirente = new ParceiroAdquirente();
        adquirente.setNome(request.getNome());
        adquirente.setTipoEndpoint(request.getTipoEndpoint());
        adquirente.setConfig(request.getConfig());
        adquirente.setAtivo(true);
        adquirente = parceiroAdquirenteRepository.save(adquirente);
        log.info("ParceiroAdquirente criado: id={}, nome={}", adquirente.getId(), adquirente.getNome());
        return toAdquirenteResponse(adquirente);
    }

    @Transactional(readOnly = true)
    public List<ParceiroBandeiraResponse> listarBandeiras() {
        return parceiroBandeiraRepository.findByAtivoTrue().stream()
            .map(this::toBandeiraResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ParceiroAdquirenteResponse> listarAdquirentes() {
        return parceiroAdquirenteRepository.findByAtivoTrue().stream()
            .map(this::toAdquirenteResponse).toList();
    }

    private ParceiroBandeiraResponse toBandeiraResponse(ParceiroBandeira b) {
        ParceiroBandeiraResponse r = new ParceiroBandeiraResponse();
        r.setId(b.getId());
        r.setNome(b.getNome());
        r.setTipoEndpoint(b.getTipoEndpoint());
        r.setAtivo(b.getAtivo());
        AuditMetaDTO audit = new AuditMetaDTO();
        audit.setDataCriacao(b.getDataCriacao());
        audit.setDataAtualizacao(b.getDataAtualizacao());
        r.setAuditoria(audit);
        return r;
    }

    private ParceiroAdquirenteResponse toAdquirenteResponse(ParceiroAdquirente a) {
        ParceiroAdquirenteResponse r = new ParceiroAdquirenteResponse();
        r.setId(a.getId());
        r.setNome(a.getNome());
        r.setTipoEndpoint(a.getTipoEndpoint());
        r.setAtivo(a.getAtivo());
        AuditMetaDTO audit = new AuditMetaDTO();
        audit.setDataCriacao(a.getDataCriacao());
        audit.setDataAtualizacao(a.getDataAtualizacao());
        r.setAuditoria(audit);
        return r;
    }
}
