/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v3;

import exemplo.jpa.PetSitter;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author jorge
 */
public class PetSitterTest extends Teste{
    @Test
    public void buscarPetSitterPorNome() {
        TypedQuery<PetSitter> query = em.createQuery("SELECT ps FROM PetSitter ps WHERE ps.nome = 'Flavio'", PetSitter.class);
        
        PetSitter petSitter = query.getSingleResult();
        
        assertEquals("Flavio", petSitter.getNome());
    }
    
    @Test
    public void selecionarPetSitterComMaiorValorHora () {
        TypedQuery<Double> query = em.createQuery("SELECT MAX(ps.valorHora) FROM PetSitter ps", Double.class);
        
        Double valorHora = query.getSingleResult();
        
        assertEquals(Double.valueOf(50), valorHora);
        
    }
    
    @Test
    public void somarValoresHoraAcimaDe30() {
        TypedQuery<Double> query = em.createQuery("SELECT SUM(ps.valorHora) FROM PetSitter ps WHERE ps.valorHora > 30", Double.class);
        
        Double somaValoresHora = query.getSingleResult();
        
        assertEquals(Double.valueOf(120), somaValoresHora);
        
                
    }
    
    @Test
    public void contarPetSittersComCidadeRecife() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(ps) FROM PetSitter ps WHERE ps.endereco.cidade = 'Recife'", Long.class);
        
        Long quantidade = query.getSingleResult();
        
        assertEquals(Long.valueOf(2), quantidade);
    }
}
