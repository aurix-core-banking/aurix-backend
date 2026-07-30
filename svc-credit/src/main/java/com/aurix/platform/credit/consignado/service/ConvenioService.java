package com.aurix.platform.credit.consignado.service;

import com.aurix.platform.credit.consignado.dto.ConvenioRequest;
import com.aurix.platform.credit.consignado.dto.ConvenioResponse;
import com.aurix.platform.credit.consignado.entity.ConvenioConsignado;
import com.aurix.platform.credit.consignado.repository.ConvenioConsignadoRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConvenioService {

    private static final Logger log = LoggerFactory.getLogger(ConvenioService.class);

    private final ConvenioConsignadoRepository repository;

    public ConvenioService(ConvenioConsignadoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ConvenioResponse> listarConvenios() {
        return repository.findByAtivoTrue().stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional
    public ConvenioResponse criarConvenio(ConvenioRequest request) {
        var entity = new ConvenioConsignado(request.getNome(), request.getTipo(),
            request.getCodigoFonte(), request.isAtivo(), "DEFAULT");
        entity = repository.save(entity);
        log.info("Convênio criado: id={}, nome={}", entity.getId(), entity.getNome());
        return toResponse(entity);
    }

    private ConvenioResponse toResponse(ConvenioConsignado e) {
        return new ConvenioResponse(e.getId(), e.getNome(), e.getTipo(),
            e.getCodigoFonte(), e.isAtivo());
    }
}
