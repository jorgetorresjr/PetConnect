/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa.v1;

import exemplo.jpa.Endereco;
import exemplo.jpa.PetOwner;
import exemplo.jpa.Teste;
import java.util.Calendar;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author masc1
 */
public class PetOwnerTeste extends Teste {

    @Test
    public void consultarPetOwner() {
        PetOwner petOwner = em.find(PetOwner.class, 2L);
        assertNotNull(petOwner);
        assertEquals("740.707.044-00", petOwner.getCpf());
        assertEquals("testpo", petOwner.getLogin());
        Calendar c = Calendar.getInstance();
        c.set(1990, Calendar.JANUARY, 1, 0, 0, 0); // data conforme dataset.xml (1990-01-01)
        assertEquals(c.getTime().toString(), petOwner.getDataNascimento().toString());
        assertEquals("test.petowner@example.com", petOwner.getEmail());

        Endereco endereco = petOwner.getEndereco();
        assertNotNull(endereco);
        assertEquals("Estado", endereco.getEstado());
        assertEquals("00000-000", endereco.getCep());
        assertEquals("Bairro Teste", endereco.getBairro());
        assertEquals("Rua Teste", endereco.getLogradouro());
    }
}
