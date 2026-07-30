package com.aurix.platform.customer.onboarding.repository;

import com.aurix.platform.customer.AurixCustomerApplication;
import com.aurix.platform.customer.onboarding.entity.Participante;
import com.aurix.platform.customer.onboarding.entity.TipoParticipante;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = AurixCustomerApplication.class)
@ActiveProfiles("test")
class ParticipanteRepositoryTest {

    @Autowired
    private ParticipanteRepository repository;

    @Test
    void saveEDevePersistirParticipante() {
        Participante p = Participante.builder()
                .solicitacaoId(1L)
                .tipo(TipoParticipante.SOCIO)
                .cpf("52998224725")
                .nome("João Silva")
                .build();

        Participante saved = repository.save(p);

        assertNotNull(saved.getId());
        assertEquals(1L, saved.getSolicitacaoId());
        assertEquals("52998224725", saved.getCpf());
        assertEquals("João Silva", saved.getNome());
        assertNotNull(saved.getDataCriacao());
        assertNotNull(saved.getDataAtualizacao());
    }

    @Test
    void findBySolicitacaoIdDeveRetornarParticipantes() {
        Participante p1 = Participante.builder()
                .solicitacaoId(10L)
                .tipo(TipoParticipante.SOCIO)
                .cpf("52998224725")
                .nome("João")
                .build();
        Participante p2 = Participante.builder()
                .solicitacaoId(10L)
                .tipo(TipoParticipante.ADMINISTRADOR)
                .cpf("12345678901")
                .nome("Maria")
                .build();
        repository.save(p1);
        repository.save(p2);

        List<Participante> found = repository.findBySolicitacaoId(10L);

        assertEquals(2, found.size());
    }

    @Test
    void findBySolicitacaoIdDeveRetornarListaVaziaQuandoNaoExiste() {
        List<Participante> found = repository.findBySolicitacaoId(999L);
        assertTrue(found.isEmpty());
    }
}
