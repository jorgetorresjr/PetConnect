/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v3;

import exemplo.jpa.Agendamento;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author jorge
 */
public class AgendamentoTest extends Teste{
    @Test
    public void somarAgendamentosNaoConfirmados() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(a) FROM Agendamento a WHERE a.confirmado = false" , Long.class);
        
        Long naoAgendados = query.getSingleResult();
        
        assertEquals(Long.valueOf(2), naoAgendados);
    }
}
