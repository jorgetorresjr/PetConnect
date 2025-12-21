package exemplo.jpa.v1;

import exemplo.jpa.PetSitter;
import exemplo.jpa.Teste;
import org.junit.Assert;
import org.junit.Test;

public class PetSitterTeste extends Teste {
    
   
    
    @Test
    public void consultarPetSitter() {
        PetSitter petSitter = em.find(PetSitter.class, 5L);
        Assert.assertNotNull(petSitter);
        Assert.assertEquals("Test PetSitter", petSitter.getNome());
        Assert.assertEquals("test.petsitter@example.com", petSitter.getEmail());
        Assert.assertEquals(35.0f, petSitter.getValorHora(), 0.01);
        Assert.assertEquals("Seg-Sex", petSitter.getDisponibilidade());
    }
}


 