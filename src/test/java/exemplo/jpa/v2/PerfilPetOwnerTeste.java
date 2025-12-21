/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v2;

import exemplo.jpa.PerfilPetOwner;
import exemplo.jpa.Teste;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author thayn
 */
public class PerfilPetOwnerTeste extends Teste {

    @Test
    public void atualizarPreferenciasPetSemMerge() {
        PerfilPetOwner perfil = em.find(PerfilPetOwner.class, 1L);

        perfil.setPreferenciasPet("CACHORRO,GATO,PASSARO");
        em.flush();
        em.clear();

        PerfilPetOwner atualizado = em.find(PerfilPetOwner.class, 1L);
        assertEquals("CACHORRO,GATO,PASSARO", atualizado.getPreferenciasPet());
    }

    @Test
    public void atualizarPreferenciasPetComMerge() {
        PerfilPetOwner perfil = em.find(PerfilPetOwner.class, 2L);

        em.clear();
        perfil.setPreferenciasPet("GATO,PASSARO");
        perfil.setBio("Tutor de um gatinho e uma calopsita.");
        PerfilPetOwner att = em.merge(perfil);

        em.flush();
        em.clear();

        PerfilPetOwner atualizado = em.find(PerfilPetOwner.class, att.getId());
        assertEquals("GATO,PASSARO", atualizado.getPreferenciasPet());
        assertEquals("Tutor de um gatinho e uma calopsita.", atualizado.getBio());
    }

    @Test
    public void removerPerfilPetOwnerPadrao() {
        PerfilPetOwner perfil = em.find(PerfilPetOwner.class, 1L);
        em.remove(perfil);
        em.flush();

        PerfilPetOwner atualizado = em.find(PerfilPetOwner.class, 1L);
        assertNull(atualizado);
    }
}
