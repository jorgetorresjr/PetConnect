/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v3;

import exemplo.jpa.Enums.TipoAnimal;
import exemplo.jpa.Pet;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class PetTest extends Teste {

    @Test
    public void listarPetsPorTipoAnimal() {
        TypedQuery<Pet> query = em.createQuery(
            "SELECT p FROM Pet p WHERE p.tipoAnimal = :tipo",
            Pet.class
        );
        query.setParameter("tipo", TipoAnimal.CACHORRO);

        List<Pet> pets = query.getResultList();

        for (Pet p : pets) {
            Assert.assertEquals(TipoAnimal.CACHORRO, p.getTipoAnimal());
        }
    }

    @Test
    public void listarPetsAtivos() {
        TypedQuery<Pet> query = em.createQuery(
            "SELECT p FROM Pet p WHERE p.ativo = true",
            Pet.class
        );

        List<Pet> pets = query.getResultList();

        for (Pet p : pets) {
            Assert.assertTrue(p.getAtivo());
        }
    }

    @Test
    public void listarPetsCastrados() {
        TypedQuery<Pet> query = em.createQuery(
            "SELECT p FROM Pet p WHERE p.castrado = true",
            Pet.class
        );

        List<Pet> pets = query.getResultList();

        for (Pet p : pets) {
            Assert.assertTrue(p.getCastrado());
        }
    }

    @Test
    public void buscarPetsComOwner() {
        TypedQuery<Pet> query = em.createQuery(
            "SELECT p FROM Pet p WHERE p.owner IS NOT NULL",
            Pet.class
        );

        List<Pet> pets = query.getResultList();

        for (Pet p : pets) {
            Assert.assertNotNull(p.getOwner());
        }
    }
}