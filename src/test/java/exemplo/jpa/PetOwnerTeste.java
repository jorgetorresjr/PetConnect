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
        PetOwner petOwner = em.find(PetOwner.class, 2L);
        assertNotNull(petOwner);
        assertEquals("740.707.044-00", petOwner.getCpf());
        assertEquals("sicrano", petOwner.getLogin());
        Calendar c = Calendar.getInstance();
        c.set(1973, Calendar.AUGUST, 11, 0, 0, 0);
        assertEquals(c.getTime().toString(), petOwner.getDataNascimento().toString());
        assertEquals("sicrano@gmail.com", petOwner.getEmail());
        
        Endereco endereco = petOwner.getEndereco();
        assertNotNull(endereco);
        assertEquals("Pernambuco", endereco.getEstado());
        assertEquals("50670-210", endereco.getCep());
        assertEquals("Iputinga", endereco.getBairro());
        assertEquals("Rua Ribeirão", endereco.getLogradouro());
      }
    
    
    @Test
    public void atualizarNomePetOwnerPadrao () {
        // Cria e persiste um novo tutor
        PetOwner petOwner = new PetOwner();
        petOwner.setCpf("111.222.333-44");
        petOwner.setLogin("mariadb1");
        petOwner.setNome("Maria da Silva");
        petOwner.setEmail("mariadb1@gmail.com");
        petOwner.setSenha("senha123");
        Calendar c = Calendar.getInstance();
        c.set(1990, Calendar.JANUARY, 1, 0, 0, 0);
        petOwner.setDataNascimento(c.getTime());

        Endereco endereco = new Endereco();
        endereco.setBairro("Centro");
        endereco.setCep("05090-970");
        endereco.setLogradouro("Rua Teste");
        endereco.setEstado("Pernambuco");
        endereco.setNumero(78);
        endereco.setCidade("Olinda");
        petOwner.setEndereco(endereco);

        em.persist(petOwner);
        em.flush();

        // Atualização padrão: altera o nome e faz flush/commit
        petOwner.setNome("Maria Atualizada");
        em.flush();

        // Verifica se nome foi att
        PetOwner atualizado = em.find(PetOwner.class, petOwner.getId());
        assertEquals("Maria Atualizada", atualizado.getNome());
    }
}
