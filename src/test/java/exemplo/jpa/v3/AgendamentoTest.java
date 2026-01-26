/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v3;

import exemplo.jpa.Agendamento;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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

    @Test
    public void listarAgendamentosConfirmados() {
        TypedQuery<Agendamento> query = em.createQuery("SELECT a FROM Agendamento a WHERE a.confirmado = true", Agendamento.class);
        
        List<Agendamento> resultados = query.getResultList();
        
        assertFalse(resultados.isEmpty());
    }

    @Test
    public void buscarPorPeriodoDeData() {
        Timestamp dataInicio = Timestamp.valueOf("2025-01-01 00:00:00");
        Timestamp dataFim = Timestamp.valueOf("2025-12-31 23:59:59");

        TypedQuery<Agendamento> query = em.createQuery("SELECT a FROM Agendamento a WHERE a.dataInicio BETWEEN :inicio AND :fim", Agendamento.class);
        query.setParameter("inicio", dataInicio);
        query.setParameter("fim", dataFim);

        List<Agendamento> resultados = query.getResultList();
        
        assertNotNull(resultados);
    }

    @Test
    public void listarAgendamentosPorDuração() {
        TypedQuery<Agendamento> query = em.createQuery("SELECT a FROM Agendamento a WHERE a.horas = :qtdHoras", Agendamento.class);
        query.setParameter("qtdHoras", 1);

        List<Agendamento> resultados = query.getResultList();

        assertEquals(3, resultados.size());
    }
}