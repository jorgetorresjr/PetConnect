/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v2;

import exemplo.jpa.Agendamento;
import exemplo.jpa.Avaliacao;
import exemplo.jpa.PetOwner;
import exemplo.jpa.Servico;
import exemplo.jpa.Teste;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elaine
 */
public class AvaliacaoTeste extends Teste {

    @Test
    public void persistirAvaliacao() {
        PetOwner petOwner = em.find(PetOwner.class, 1L);
        Servico servico = em.find(Servico.class, 1L);
        assertNotNull(petOwner);
        assertNotNull(servico);

        Agendamento novoAg = new Agendamento();
        novoAg.setPetOwner(petOwner);
        novoAg.setServico(servico);
        novoAg.setHoras(1);
        novoAg.setConfirmado(false);
        novoAg.setDataInicio(
                Timestamp.valueOf(LocalDateTime.now().plusDays(1))
        );
        em.persist(novoAg);
        em.flush();

        Avaliacao av = new Avaliacao();
        av.setAgendamento(novoAg);
        av.setNota(5);
        av.setComentario("Serviço excelente, recomendo!");

        em.persist(av);
        em.flush();
        em.clear();

        assertNotNull(av.getId());
    }

    @Test
    public void consultarAvaliacao() {
        Avaliacao av = em.find(Avaliacao.class, 2L);
        assertNotNull(av);
        assertEquals(Integer.valueOf(0), av.getNota());
        assertEquals("Pior serviço", av.getComentario());
    }

    @Test
    public void atualizarAvaliacaoSemMerge() {
        Avaliacao ag = em.find(Avaliacao.class, 2L);

        ag.setNota(3);
        ag.setComentario("Bom!");

        em.flush();
        em.clear();

        Avaliacao atualizado = em.find(Avaliacao.class, 2L);
        assertEquals(Integer.valueOf(3), atualizado.getNota());
        assertEquals("Bom!", atualizado.getComentario());
    }

    @Test
    public void atualizarAvaliacaoComMerge() {
        Avaliacao av = em.find(Avaliacao.class, 3L);

        em.clear(); // entidade destacada

        av.setNota(4);

        Avaliacao att = em.merge(av);
        em.flush();
        em.clear();

        Avaliacao atualizado = em.find(Avaliacao.class, att.getId());
        assertEquals(Integer.valueOf(4), atualizado.getNota());
    }

    @Test
    public void removerAvaliacao() {
        Avaliacao av = em.find(Avaliacao.class, 1L);
        assertNotNull(av);

        em.remove(av);
        em.flush();

        assertNull(em.find(Avaliacao.class, 1L));
    }
}
