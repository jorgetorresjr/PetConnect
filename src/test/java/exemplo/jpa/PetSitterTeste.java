<<<<<<< HEAD
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
public class PetSitterTeste extends Teste {

    @Test
    public void persistirVendedor() {
        PetSitter vendedor = new PetSitter();
        vendedor.setCpf("248.008.000-56");
        vendedor.setLogin("vendedor1");
        vendedor.setNome("Vendedor da Silva");
        vendedor.setEmail("vendedor2000@gmail.com");
        vendedor.setSenha("#987654321#");
        Calendar c = Calendar.getInstance();
        c.set(1991, Calendar.OCTOBER, 12, 0, 0, 0);
        vendedor.setDataNascimento(c.getTime());
        vendedor.setDisponibilidade("Top");
        vendedor.setValorHora(Double.valueOf(1500000));

        Endereco endereco = new Endereco();
        endereco.setBairro("Ipsep");
        endereco.setCep("50770-680");
        endereco.setLogradouro("Avenida das Garças");
        endereco.setEstado("Pernambuco");
        endereco.setNumero(400);
        endereco.setCidade("Recife");
        vendedor.setEndereco(endereco);

        em.persist(vendedor);
        em.flush();

        assertNotNull(vendedor.getId());
    }

    @Test
    public void consultarVendedor() {
        PetSitter vendedor = em.find(PetSitter.class, 5L);
        assertNotNull(vendedor);
        assertEquals("484.854.847-03", vendedor.getCpf());
        assertEquals("v1silva", vendedor.getLogin());
        Calendar c = Calendar.getInstance();
        c.set(1995, Calendar.NOVEMBER, 23, 0, 0, 0);
        assertEquals(c.getTime().toString(), vendedor.getDataNascimento().toString());
        assertEquals("vendedor1@gmail.com", vendedor.getEmail());
        assertEquals(Double.valueOf("10500.50"), vendedor.getValorHora());
        assertEquals("EXPERIENTE", vendedor.getDisponibilidade());

        Endereco endereco = vendedor.getEndereco();
        assertNotNull(endereco);
        assertEquals("Pernambuco", endereco.getEstado());
        assertEquals("50670-210", endereco.getCep());
        assertEquals("Iputinga", endereco.getBairro());
        assertEquals("Rua Ribeirão", endereco.getLogradouro());
    }

}
=======
package exemplo.jpa;

import org.junit.Assert;
import org.junit.Test;

public class PetSitterTeste extends Teste {
    
    @Test
    public void persistirPetSitter() {
        PetSitter petSitter = new PetSitter();
        petSitter.setCpf("248.008.000-56");
        petSitter.setDataNascimento(java.sql.Date.valueOf("1991-10-12"));
        petSitter.setEmail("novovendedor@gmail.com");
        petSitter.setLogin("novovendedor");
        petSitter.setNome("Novo Vendedor da Silva");
        petSitter.setSenha("#987654321#");
        
        Endereco endereco = new Endereco();
        endereco.setBairro("Ipsep");
        endereco.setCep("50770-680");
        endereco.setCidade("Recife");
        endereco.setEstado("Pernambuco");
        endereco.setLogradouro("Avenida das Garças");
        endereco.setNumero(400);
        petSitter.setEndereco(endereco);
        
        petSitter.setValorHora(10.1);
        petSitter.setDisponibilidade("Top");
        
        em.persist(petSitter);
        em.flush();
        
        Assert.assertNotNull(petSitter.getId());
    }
    
    @Test
    public void consultarPetSitter() {
        PetSitter petSitter = em.find(PetSitter.class, 5L);
        Assert.assertNotNull(petSitter);
        Assert.assertEquals("Cuidador da Silva", petSitter.getNome());
        Assert.assertEquals("cuidador1@gmail.com", petSitter.getEmail());
        Assert.assertEquals(50.0f, petSitter.getValorHora(), 0.01);
        Assert.assertEquals("Segunda a Sexta", petSitter.getDisponibilidade());
    }
}
>>>>>>> fix_tests
