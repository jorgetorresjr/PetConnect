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
        petOwner.setLogin("novocomprador");
        petOwner.setNome("Novo Comprador da Silva");
        petOwner.setEmail("novocomprador@gmail.com");
        petOwner.setSenha("#1234567890#");
        Calendar c = Calendar.getInstance();
        c.set(1984, Calendar.SEPTEMBER, 24, 0, 0, 0);
        petOwner.setDataNascimento(c.getTime());

        Endereco endereco = new Endereco();
        endereco.setBairro("Várzea");
        endereco.setCep("50770-340");
        endereco.setLogradouro("Avenida Professor Moraes Rego");
        endereco.setEstado("Pernambuco");
        endereco.setNumero(40);
        endereco.setCidade("Recife");
        
        petOwner.setEndereco(endereco);
        
      
        
        em.persist(petOwner);
        em.flush();
        
        assertNotNull(petOwner.getId());
    }

    @Test
    public void consultarPetOwner() {
        // Busca o segundo PetOwner inserido pelo dataset.xml para obter seu ID
        PetOwner petOwner = em.createQuery("SELECT p FROM PetOwner p WHERE p.cpf = '740.707.044-00'", PetOwner.class).getSingleResult();
        assertNotNull(petOwner);
        Long id = petOwner.getId();
        assertNotNull(id);
        
        // Agora consulta pela chave primária (ID) como requerido
        PetOwner petOwnerConsultado = em.find(PetOwner.class, id);
        assertNotNull(petOwnerConsultado);
        assertEquals("740.707.044-00", petOwnerConsultado.getCpf());
        assertEquals("sicrano", petOwnerConsultado.getLogin());
        Calendar c = Calendar.getInstance();
        c.set(1973, Calendar.AUGUST, 11, 0, 0, 0);
        assertEquals(c.getTime().toString(), petOwnerConsultado.getDataNascimento().toString());
        assertEquals("sicrano@gmail.com", petOwnerConsultado.getEmail());
        
        Endereco endereco = petOwnerConsultado.getEndereco();
        assertNotNull(endereco);
        assertEquals("Pernambuco", endereco.getEstado());
        assertEquals("50670-210", endereco.getCep());
        assertEquals("Iputinga", endereco.getBairro());
        assertEquals("Rua Ribeirão", endereco.getLogradouro());
      }
}
