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
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
/**
 *
 * @author elaine
 */
public class AvaliacaoTest extends Teste {

    @Test
    public void buscarAvaliacoesComNotaMaxima() {
        TypedQuery<Avaliacao> query = em.createQuery("SELECT av FROM Avaliacao av WHERE av.nota = 5", Avaliacao.class);
        
        List<Avaliacao> resultados = query.getResultList();
        
        assertEquals(1, resultados.size());
    }

    @Test
    public void calcularMediaDasNotas() {
        TypedQuery<Double> query = em.createQuery("SELECT AVG(av.nota) FROM Avaliacao av", Double.class);
        
        Double media = query.getSingleResult();
        
        assertEquals(3.0, media, 0.1);
    }

    @Test
    public void listarAvaliacoesPorPalavraChaveNoComentario() {
        TypedQuery<Avaliacao> query = em.createQuery("SELECT av FROM Avaliacao av WHERE av.comentario LIKE :texto", Avaliacao.class);
        query.setParameter("texto", "%serviço%");

        List<Avaliacao> resultados = query.getResultList();
     
        assertEquals(2, resultados.size());
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