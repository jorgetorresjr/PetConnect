/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v3;

import exemplo.jpa.Enums.TipoAnimal;
import exemplo.jpa.Pet;
import exemplo.jpa.PetOwner;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class PetTest extends Teste {

    @Test
    public void listarPetsPorTipoAnimal() {
        TypedQuery<Pet> query = em.createQuery(
                "SELECT p FROM Pet p WHERE p.tipoAnimal IN :tipos ORDER BY p.nome",
                Pet.class
        );
        query.setParameter("tipos", List.of(TipoAnimal.CACHORRO));

        List<Pet> pets = query.getResultList();

        Assert.assertFalse(pets.isEmpty());

        for (Pet p : pets) {
            Assert.assertEquals(TipoAnimal.CACHORRO, p.getTipoAnimal());
        }
    }

    @Test
    public void listarPetsCastrados() {
        TypedQuery<Pet> query = em.createQuery(
                "SELECT p FROM Pet p WHERE NOT p.castrado = false",
                Pet.class
        );

        List<Pet> pets = query.getResultList();

        Assert.assertFalse(pets.isEmpty());

        for (Pet p : pets) {
            Assert.assertTrue(p.getCastrado());
        }
    }

    @Test
    public void buscarPetsComOwner() {
        TypedQuery<Pet> query = em.createQuery(
                "SELECT p FROM Pet p JOIN p.owner o ORDER BY o.nome",
                Pet.class
        );

        List<Pet> pets = query.getResultList();

        Assert.assertFalse(pets.isEmpty());

        for (Pet p : pets) {
            Assert.assertNotNull(p.getOwner());
        }
    }

    @Test
    public void buscarDonosComPetEspecifico() {
        Pet pet = em.find(Pet.class, 4L);
        TypedQuery<PetOwner> query = em.createQuery(
                "SELECT o FROM PetOwner o WHERE :pet MEMBER OF o.pets", PetOwner.class);
        query.setParameter("pet", pet);

        List<PetOwner> owners = query.getResultList();
        Assert.assertFalse(owners.isEmpty());
        for (PetOwner owner : owners) {
            Assert.assertTrue(owner.getPets().contains(pet));
        }
    }

}
