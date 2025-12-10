/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author thayn
 */
public class PetNewTeste extends Teste {

    @Test
    public void atualizarPetSemMerge() {
        // Busca o tutor
        Pet pet = em.find(Pet.class, 2L);

        // Atualização normal
        pet.setNome("Zeca Padrao");

        em.flush();
        // Verifica se foi atualizado
        Pet atualizado = em.find(Pet.class, 2L);
        assertEquals("Zeca Padrao", atualizado.getNome());
    }

    @Test
    public void atualizarPetComMerge() {
        // Busca o tutor
        Pet pet = em.find(Pet.class, 1L);

        // Agora a entidade fica destacada
        em.clear();

        // Atualiza objeto destacado
        pet.setNome("Bentinho Merge");

        Pet att = em.merge(pet);

        em.flush();

        // Verifica no banco
        Pet atualizado = em.find(Pet.class, att.getId());
        assertEquals("Bentinho Merge", atualizado.getNome());
    }

    @Test
    public void atualizarNomePetPadrao() {
        // Cria e persiste um novo tutor
        Pet pet = em.find(Pet.class, 1L);

        em.clear();

        // Atualização padrão
        pet.setNome("Canelinha Atualizado");
        em.merge(pet);
        em.flush();
        // em.clear();

        // Verifica se nome foi att
        Pet atualizado = em.find(Pet.class, pet.getId());
        assertEquals("Canelinha Atualizado", atualizado.getNome());
    }

    @Test
    public void removerPetPadrao() {
        // Cria e persiste um novo tutor
        Pet pet = em.find(Pet.class, 1L);

        em.remove(pet);
        em.flush();

        // Verifica se nome foi att
        Pet atualizado = em.find(Pet.class, 1L);
        assertNull(atualizado);
    }
}
