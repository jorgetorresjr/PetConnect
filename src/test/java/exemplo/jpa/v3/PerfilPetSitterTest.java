/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v3;

import exemplo.jpa.PerfilPetSitter;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author elaine
 */
public class PerfilPetSitterTest extends Teste {

    @Test
    public void buscarPetSittersComCertificacao() {
        TypedQuery<PerfilPetSitter> query = em.createQuery(
                "SELECT p FROM PerfilPetSitter p WHERE p.certificacoes IS NOT NULL AND p.certificacoes <> ' '",
                PerfilPetSitter.class);
        List<PerfilPetSitter> resultados = query.getResultList();
        assertFalse(resultados.isEmpty());
        for (PerfilPetSitter perfil : resultados) {
            assertNotNull(perfil.getCertificacoes());
            assertFalse(perfil.getCertificacoes().isEmpty());
        }
    }

    @Test
    public void buscarPerfisComExperienciaMin2Anos() {
        TypedQuery<PerfilPetSitter> query = em.createQuery(
                "SELECT p FROM PerfilPetSitter p WHERE SUBSTRING(p.experiencia, 1, 1) = '2'",
                PerfilPetSitter.class);

        List<PerfilPetSitter> resultados = query.getResultList();

        assertFalse(resultados.isEmpty());
    }

    @Test
    public void confirmarDadosPetSitterId10() {
        TypedQuery<PerfilPetSitter> query = em.createQuery(
                "SELECT p FROM PerfilPetSitter p JOIN Perfil base ON p.id = base.id WHERE p.id = :id",
                PerfilPetSitter.class);
        query.setParameter("id", 10L);

        PerfilPetSitter petSitter = query.getSingleResult();

        assertNotNull(petSitter);
        assertEquals(Long.valueOf(10), petSitter.getId());
        assertEquals("3 anos", petSitter.getExperiencia());
        assertEquals("Basico", petSitter.getCertificacoes());
        assertEquals("PASSEIO", petSitter.getTipoServico().toString());
    }
    
    @Test
    public void buscarPetSittersComCertificacaoBasico() {
        TypedQuery<PerfilPetSitter> query = em.createQuery(
                "SELECT p FROM PerfilPetSitter p WHERE LENGTH(p.certificacoes) >= 3",
                PerfilPetSitter.class
        );
       
        List<PerfilPetSitter> resultados = query.getResultList();

        assertFalse(resultados.isEmpty());
        for (PerfilPetSitter perfil : resultados) {
            assertTrue(perfil.getCertificacoes() != null && 
                    perfil.getCertificacoes().length() >= 3);
        }
    }

}
