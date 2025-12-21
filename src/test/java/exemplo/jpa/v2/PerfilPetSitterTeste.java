/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v2;

import exemplo.jpa.PerfilPetSitter;
import exemplo.jpa.Teste;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author thayn
 */
public class PerfilPetSitterTeste extends Teste {
    @Test
    public void atualizarPreferenciasPetSemMerge() {
        PerfilPetSitter perfil = em.find(PerfilPetSitter.class, 5L);

        perfil.setExperiencia("5 meses");
        perfil.setCertificacoes("Hotelzinho autorizado");
        perfil.setBio("Estudante de veterinaria");
        em.flush();
        em.clear();

        PerfilPetSitter atualizado = em.find(PerfilPetSitter.class, 5L);
        assertEquals("5 meses", atualizado.getExperiencia());
        assertEquals("Hotelzinho autorizado", atualizado.getCertificacoes());
        assertEquals("Estudante de veterinaria", atualizado.getBio());
    }

    @Test
    public void atualizarPreferenciasPetComMerge() {
        PerfilPetSitter perfil = em.find(PerfilPetSitter.class, 6L);

        em.clear();
        perfil.setExperiencia("7 meses");
        perfil.setCertificacoes("Curso de cuidados caninos");
        perfil.setBio("Passeio com seu dog");
        PerfilPetSitter att = em.merge(perfil);

        em.flush();
        em.clear();

        PerfilPetSitter atualizado = em.find(PerfilPetSitter.class, att.getId());
        assertEquals("7 meses", atualizado.getExperiencia());
        assertEquals("Curso de cuidados caninos", atualizado.getCertificacoes());
        assertEquals("Passeio com seu dog", atualizado.getBio());
    }

    @Test
    public void removerPerfilPetSitterPadrao() {
        PerfilPetSitter perfil = em.find(PerfilPetSitter.class, 5L);
        em.remove(perfil);
        em.flush();

        PerfilPetSitter atualizado = em.find(PerfilPetSitter.class, 5L);
        assertNull(atualizado);
    }
}
