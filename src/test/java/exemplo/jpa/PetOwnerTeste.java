/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa;

import java.util.Calendar;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author masc1
 */
public class PetOwnerTeste extends Teste {
    @Test
    public void persistirPetOwner() {
        PetOwner petOwner = new PetOwner();
        petOwner.setCpf("526.594.890-25");
        petOwner.setLogin("comprador1");
        petOwner.setNome("Comprador da Silva");
        petOwner.setEmail("comprador@gmail.com");
        petOwner.setSenha("#1234567890#");
        Calendar c = Calendar.getInstance();
        c.set(1984, Calendar.SEPTEMBER, 24, 0, 0, 0);
        petOwner.setDataNascimento(c.getTime());

        Address address = new Address();
        address.setBairro("Várzea");
        address.setCep("50770-340");
        address.setLogradouro("Avenida Professor Moraes Rego");
        address.setEstado("Pernambuco");
        address.setNumero(40);
        address.setCidade("Recife");
        
        petOwner.setEndereco(address);
        
      
        
        em.persist(petOwner);
        em.flush();
        
        assertNotNull(petOwner.getId());
    }

    @Test
    public void consultarPetOwner() {
        PetOwner petOwner = em.find(PetOwner.class, 2L);
        assertNotNull(petOwner);
        assertEquals("740.707.044-00", petOwner.getCpf());
        assertEquals("sicrano", petOwner.getLogin());
        Calendar c = Calendar.getInstance();
        c.set(1973, Calendar.AUGUST, 11, 0, 0, 0);
        assertEquals(c.getTime().toString(), petOwner.getDataNascimento().toString());
        assertEquals("sicrano@gmail.com", petOwner.getEmail());
        
        Address address = petOwner.getEndereco();
        assertNotNull(address);
        assertEquals("Pernambuco", address.getEstado());
        assertEquals("50670-210", address.getCep());
        assertEquals("Iputinga", address.getBairro());
        assertEquals("Rua Ribeirão", address.getLogradouro());
      }
}
