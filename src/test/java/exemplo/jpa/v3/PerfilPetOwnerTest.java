/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v3;

import exemplo.jpa.PerfilPetOwner;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author elaine
 */
public class PerfilPetOwnerTest extends Teste {

    @Test
    public void buscarPorPreferenciasPetGato() {
        TypedQuery<PerfilPetOwner> query = em.createQuery(
                "SELECT p FROM PerfilPetOwner p WHERE UPPER(p.preferenciasPet) LIKE :pref",
                PerfilPetOwner.class);
        query.setParameter("pref", "%GATO%");
        List<PerfilPetOwner> resultados = query.getResultList();
        assertFalse(resultados.isEmpty());
    }

    @Test
    public void contarPerfisPorPreferenciasPetGato() {
        TypedQuery<Object[]> query = em.createQuery(
                "SELECT LOWER(p.preferenciasPet), COUNT(p) "
                + "FROM PerfilPetOwner p "
                + "GROUP BY LOWER(p.preferenciasPet) "
                + "HAVING LOWER(p.preferenciasPet) = 'gato'",
                Object[].class);

        List<Object[]> resultados = query.getResultList();

        assertFalse(resultados.isEmpty());

        Object[] resultado = resultados.get(0);
        String preferencia = (String) resultado[0];
        Long quantidade = (Long) resultado[1];

        assertEquals("gato", preferencia);
        assertEquals(Long.valueOf(3), quantidade);
    }

    @Test
    public void buscarPerfisComBioXPTO() {
        TypedQuery<PerfilPetOwner> query = em.createQuery(
                "SELECT p FROM PerfilPetOwner p WHERE LOWER(p.bio) LIKE :bio",
                PerfilPetOwner.class);
        query.setParameter("bio", "%amo os animais%");
        List<PerfilPetOwner> resultados = query.getResultList();
        assertFalse(resultados.isEmpty());
        for (PerfilPetOwner perfil : resultados) {
            assertTrue(perfil.getBio().toLowerCase().contains("amo os animais"));
        }
    }

}
