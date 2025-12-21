/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v2;

import exemplo.jpa.PetOwner;
import exemplo.jpa.Teste;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author thayn
 */
public class PetOwnerTeste extends Teste {

    @Test
    public void atualizarPetOwnerSemMerge() {
        // Busca o tutor
        PetOwner petOwner = em.find(PetOwner.class, 1L);

        // Atualização normal
        petOwner.setNome("Mariana Padrao");
      
        em.flush();
        em.clear();

        // Verifica se foi atualizado
        PetOwner atualizado = em.find(PetOwner.class, 1L);
        assertEquals("Mariana Padrao", atualizado.getNome());
        PetOwner found = em.find(PetOwner.class, petOwner.getId());
         
    }

    @Test
    public void atualizarPetOwnerComMerge() {
        // Busca o tutor
        PetOwner petOwner = em.find(PetOwner.class, 2L);

        // Agora a entidade fica destacada
        em.clear();

        // Atualiza objeto destacado
        petOwner.setNome("Marina Merge");

        PetOwner att = em.merge(petOwner);

        em.flush();
        em.clear();
        // Verifica no banco
        PetOwner atualizado = em.find(PetOwner.class, att.getId());
        assertEquals("Marina Merge", atualizado.getNome());
    }

    @Test
    public void atualizarNomePetOwnerPadrao() {
        // Cria e persiste um novo tutor
        PetOwner petOwner = em.find(PetOwner.class, 3L);

        em.clear();

        // Atualização padrão
        petOwner.setNome("Paulo Atualizado");
        em.merge(petOwner);
        em.flush();
        em.clear();

        // Verifica se nome foi att
        PetOwner atualizado = em.find(PetOwner.class, petOwner.getId());
        assertEquals("Paulo Atualizado", atualizado.getNome());
    }

    @Test
    public void removerPetOwnerPadrao() {
        PetOwner petOwner = em.find(PetOwner.class, 1L);

        em.remove(petOwner);
        em.flush();

        PetOwner atualizado = em.find(PetOwner.class, 1L);
        assertNull(atualizado);
    }
}
