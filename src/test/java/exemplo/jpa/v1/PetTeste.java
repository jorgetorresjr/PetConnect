package exemplo.jpa.v1;

import exemplo.jpa.Enums.TipoAnimal;
import exemplo.jpa.Pet;
import exemplo.jpa.PetOwner;
import exemplo.jpa.Teste;
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

}
