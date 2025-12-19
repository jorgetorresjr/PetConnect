/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elaine
 */

public class AvaliacaoTeste extends Teste {

    @Test
    public void persistirAvaliacao() {
        Agendamento ag = em.find(Agendamento.class, 2L);
        assertNotNull(ag);

        Avaliacao av = new Avaliacao();
        av.setAgendamento(ag);
        av.setNota(5);
        av.setComentario("Serviço excelente, recomendo!");

        em.persist(av);
        em.flush();

        assertNotNull(av.getId());
    }

    @Test
    public void consultarAvaliacao() {
        Avaliacao av = em.find(Avaliacao.class, 1L);
        assertNotNull(av);
        assertEquals(Integer.valueOf(5), av.getNota());
        assertEquals("Ótimo passeio!", av.getComentario());
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
