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