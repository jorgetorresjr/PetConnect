/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elaine
 */

public class AgendamentoTeste extends Teste {

    @Test
    public void persistirAgendamento() {
        PetOwner owner = em.find(PetOwner.class, 1L);
        Servico servico = em.find(Servico.class, 1L);

        Agendamento ag = new Agendamento();
        ag.setPetOwner(owner);
        ag.setServico(servico);
        ag.setDataInicio(
                Timestamp.valueOf(LocalDateTime.now().plusDays(1)));
        ag.setHoras(2);
        ag.setConfirmado(false);

        em.persist(ag);
        em.flush();

        assertNotNull(ag.getId());
        // prova do modelo indireto
        assertNotNull(ag.getServico().getPetSitter());
    }

    @Test
    public void atualizarAgendamentoSemMerge() {
        Agendamento ag = em.find(Agendamento.class, 1L);

        ag.setHoras(3);
        ag.setConfirmado(true);

        em.flush();
        em.clear();

        Agendamento atualizado = em.find(Agendamento.class, 1L);
        assertEquals(Integer.valueOf(3), atualizado.getHoras());
        assertTrue(atualizado.getConfirmado());
    }

    @Test
    public void atualizarAgendamentoComMerge() {
        Agendamento ag = em.find(Agendamento.class, 1L);

        em.clear(); // entidade destacada

        ag.setHoras(4);

        Agendamento att = em.merge(ag);
        em.flush();
        em.clear();

        Agendamento atualizado = em.find(Agendamento.class, att.getId());
        assertEquals(Integer.valueOf(4), atualizado.getHoras());
    }

    @Test
    public void removerAgendamento() {
        Agendamento ag = em.find(Agendamento.class, 1L);
        assertNotNull(ag);

        for (Avaliacao av : ag.getAvaliacoes()) {
            em.remove(av);
        }

        em.remove(ag);
        em.flush();

        assertNull(em.find(Agendamento.class, 1L));
    }
}
