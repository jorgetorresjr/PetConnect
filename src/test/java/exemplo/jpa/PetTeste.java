package exemplo.jpa;

import exemplo.jpa.Enums.TipoAnimal;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class PetTeste extends Teste {
    
    @Test
    public void persistirPet() {
        // Busca um PetOwner existente do dataset
        PetOwner owner = em.find(PetOwner.class, 1L);
        Assert.assertNotNull("PetOwner não encontrado no dataset", owner);
        
        // Cria um novo Pet
        Pet pet = new Pet();
        pet.setNome("Max");
        pet.setRaca("Poodle");
        pet.setIdade(3);
        pet.setTipoAnimal(TipoAnimal.CACHORRO);
        pet.setSexo("MACHO");
        pet.setTemperamento("Dócil");
        pet.setEstadoSaude("Saudável");
        pet.setCastrado(true);
        pet.setAtivo(true);
        pet.setOwner(owner);
        
        // Persiste (transação já está ativa do @Before)
        em.persist(pet);
        em.flush();
        
        Assert.assertNotNull("ID do Pet não foi gerado", pet.getId());
    }
    
    @Test
    public void consultarPet() {
        Pet pet = em.find(Pet.class, 1L);
        Assert.assertNotNull("Pet não encontrado", pet);
        Assert.assertEquals("Rex", pet.getNome());
        Assert.assertEquals(TipoAnimal.CACHORRO, pet.getTipoAnimal());
        Assert.assertNotNull("Owner não foi carregado", pet.getOwner());
    }
    
    @Test
    public void consultarPetsPorTipoAnimal() {
        //Buscar pets por tipo de animal
        String jpql = "SELECT p FROM Pet p WHERE p.tipoAnimal = :tipo ORDER BY p.nome";
        TypedQuery<Pet> query = em.createQuery(jpql, Pet.class);
        query.setParameter("tipo", TipoAnimal.CACHORRO);
        
        List<Pet> pets = query.getResultList();
        Assert.assertFalse("Lista não deve estar vazia", pets.isEmpty());
        
        for (Pet pet : pets) {
            Assert.assertEquals(TipoAnimal.CACHORRO, pet.getTipoAnimal());
        }
    }
    
}