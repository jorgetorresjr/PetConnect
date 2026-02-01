/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v3;

import exemplo.jpa.Avaliacao;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
/**
 *
 * @author elaine
 */
public class AvaliacaoTest extends Teste {

   @Test
    public void buscarAvaliacoesAcimaDaMediaGeral() {
        TypedQuery<Avaliacao> query = em.createQuery(
            "SELECT av FROM Avaliacao av WHERE av.nota > (SELECT AVG(av2.nota) FROM Avaliacao av2)", 
            Avaliacao.class);
        
        List<Avaliacao> resultados = query.getResultList();
        
        assertEquals(2, resultados.size());
    }

    @Test
    public void buscarPiorNotaRegistrada() {
        TypedQuery<Integer> query = em.createQuery(
            "SELECT MIN(av.nota) FROM Avaliacao av", Integer.class);
        
        Integer piorNota = query.getSingleResult();
        
        assertEquals(Integer.valueOf(0), piorNota);
    }

    @Test
    public void buscarAvaliacoesComNotaMaxima() {
        TypedQuery<Avaliacao> query = em.createQuery(
            "SELECT av FROM Avaliacao av WHERE av.nota = 5", Avaliacao.class);
        
        List<Avaliacao> resultados = query.getResultList();
        // Ajustado para 1 conforme seu XML atual
        assertEquals(1, resultados.size());
    }

    @Test
    public void buscarAvaliacaoPorDonoDoPet() {
        TypedQuery<Avaliacao> query = em.createQuery("SELECT av FROM Avaliacao av WHERE av.agendamento.petOwner.nome LIKE :nomeDono", 
            Avaliacao.class);
        query.setParameter("nomeDono", "%Test PetOwner%");

        List<Avaliacao> resultados = query.getResultList();
        
        assertFalse(resultados.isEmpty());
    }
}