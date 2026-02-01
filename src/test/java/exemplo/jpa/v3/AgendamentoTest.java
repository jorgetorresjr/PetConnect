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
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author jorge
 */
public class AgendamentoTest extends Teste { 
   @Test
    public void listarAgendamentosMaisRecentesPrimeiro() {
        TypedQuery<Agendamento> query = em.createQuery(
            "SELECT a FROM Agendamento a ORDER BY a.dataInicio DESC", Agendamento.class);
        
        List<Agendamento> resultados = query.getResultList();
        
        assertFalse(resultados.isEmpty());
       
        assertTrue(resultados.get(0).getDataInicio().after(resultados.get(resultados.size()-1).getDataInicio()));
    }

    @Test
    public void buscarAgendamentosPorPeriodoDeData() {
        Timestamp dataInicio = Timestamp.valueOf("2025-12-01 00:00:00");
        Timestamp dataFim = Timestamp.valueOf("2025-12-31 23:59:59");

        TypedQuery<Agendamento> query = em.createQuery(
            "SELECT a FROM Agendamento a WHERE a.dataInicio BETWEEN :inicio AND :fim", Agendamento.class);
        query.setParameter("inicio", dataInicio);
        query.setParameter("fim", dataFim);

        List<Agendamento> resultados = query.getResultList();
        assertEquals(3, resultados.size());
    }

    @Test
    public void buscarAgendamentosPorNomeDoServico() {
        TypedQuery<Agendamento> query = em.createQuery(
            "SELECT a FROM Agendamento a JOIN a.servico s WHERE s.nome = :nomeServico", Agendamento.class);
        
        query.setParameter("nomeServico", "Passeio 30min"); 

        List<Agendamento> resultados = query.getResultList();
        assertEquals(3, resultados.size());
    }

   
    @Test
    public void listarAgendamentosConfirmados() {
        TypedQuery<Agendamento> query = em.createQuery(
            "SELECT a FROM Agendamento a WHERE a.confirmado = true", Agendamento.class);
        
        List<Agendamento> resultados = query.getResultList();
        assertEquals(1, resultados.size());
    }
}