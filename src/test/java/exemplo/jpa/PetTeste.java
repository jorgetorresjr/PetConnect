package exemplo.jpa;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe de teste para a entidade Pet
 * 
 * @author masc1
 */
public class PetTeste extends Teste {

    @Test
    public void persistirPet() {
        // Primeiro cria um PetOwner para associar ao Pet
        PetOwner owner = new PetOwner();
        owner.setCpf("111.222.333-44");
        owner.setLogin("ownertest");
        owner.setNome("Owner Test");
        owner.setEmail("ownertest@gmail.com");
        owner.setSenha("senha123");
        
        Endereco endereco = new Endereco();
        endereco.setBairro("Centro");
        endereco.setCep("50000-000");
        endereco.setLogradouro("Rua Teste");
        endereco.setEstado("Pernambuco");
        endereco.setNumero(100);
        endereco.setCidade("Recife");
        owner.setEndereco(endereco);
        
        em.persist(owner);
        em.flush();
    
        Pet pet = new Pet();
        pet.setNome("Rex");
        pet.setIdade(3);
        pet.setSexo(Sexo.MACHO);
        pet.setRaca("Labrador");
        pet.setTipoAnimal(TipoAnimal.CACHORRO);
        pet.setEstadoSaude("Saudável");
        pet.setCastrado(true);
        pet.setTemperamento("Dócil");
        pet.setOwner(owner);
        
        em.persist(pet);
        em.flush();
        
        assertNotNull(pet.getId());
        assertNotNull(pet.getOwner());
    }

    @Test
    public void consultarPet() {
        // Busca o Pet "Rex" com raça "Labrador" e 5 anos (do dataset.xml)
        Pet pet = em.createQuery("SELECT p FROM Pet p WHERE p.nome = 'Rex' AND p.raca = 'Labrador' AND p.idade = 5", Pet.class)
                    .getSingleResult();
        assertNotNull(pet);
        Long id = pet.getId();
        assertNotNull(id);
        
        // Consulta pela chave primária
        Pet petConsultado = em.find(Pet.class, id);
        assertNotNull(petConsultado);
        assertEquals("Rex", petConsultado.getNome());
        assertEquals(Sexo.MACHO, petConsultado.getSexo());
        assertEquals(TipoAnimal.CACHORRO, petConsultado.getTipoAnimal());
        assertEquals("Labrador", petConsultado.getRaca());
        assertEquals(5, petConsultado.getIdade());
        assertNotNull(petConsultado.getOwner());
    }
}
