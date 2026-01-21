/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v3;

import exemplo.jpa.PetSitter;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author jorge
 */
public class PetSitterTest extends Teste{
    @Test
    public void buscarPetSitterPorNome() {
        TypedQuery<PetSitter> query = em.createQuery("SELECT ps FROM PetSitter ps WHERE ps.nome = 'Flávio'", PetSitter.class);
        
        PetSitter petSitter = query.getSingleResult();
        
        assertEquals("Flávio", petSitter.getNome());
    }
    
    @Test
    public void listarPetSitterComEmailNaoNulo () {
        TypedQuery<PetSitter> query = em.createQuery("SELECT ps FROM PetSitter ps WHERE ps.email IS NOT NULL", PetSitter.class);
        
        List<PetSitter> petSitters = query.getResultList();
        
        for(PetSitter ps : petSitters) {
            assertNotNull(ps.getEmail());
        }
    }
    
    @Test
    public void listarPetSittersComValorHoraAbaixoDe30() {
        TypedQuery<PetSitter> query = em.createQuery("SELECT ps FROM PetSitter ps WHERE ps.valorHora < 30", PetSitter.class);
        
        List<PetSitter> petSitters = query.getResultList();
        
        for(PetSitter ps : petSitters) {
            Assert.assertTrue(ps.getValorHora() < 30);
        }
        
                
    }
    
    @Test
    public void listarPetSittersPorCidade() {
        TypedQuery<PetSitter> query = em.createQuery("SELECT ps FROM PetSitter ps WHERE ps.endereco.cidade = 'Recife'", PetSitter.class);
        
        List<PetSitter> petSitters = query.getResultList();
        
        assertEquals(2, petSitters.size());
    }
}
