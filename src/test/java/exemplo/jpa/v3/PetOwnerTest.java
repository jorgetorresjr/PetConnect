/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v3;

import exemplo.jpa.PetOwner;
import exemplo.jpa.Teste;
import exemplo.jpa.Usuario;
import jakarta.persistence.TypedQuery;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author elaine
 */
public class PetOwnerTest extends Teste {

    @Test
    public void buscarPetOwnersSemPets() {
        TypedQuery<PetOwner> query = em.createQuery(
                "SELECT p FROM PetOwner p WHERE p.pets IS EMPTY", PetOwner.class);
        List<PetOwner> resultados = query.getResultList();
        assertNotNull(resultados);
    }

    @Test
    public void buscarPetOwnerCom2OuMaisPets() {
        TypedQuery<PetOwner> query = em.createQuery(
                "SELECT p FROM PetOwner p WHERE SIZE(p.pets) > 2", PetOwner.class);
        List<PetOwner> resultados = query.getResultList();

        assertNotNull(resultados);

    }

    @Test
    public void buscarPetOwnerComUsuario() {
        TypedQuery<Object[]> query = em.createQuery(
                "SELECT p, u FROM PetOwner p INNER JOIN Usuario u ON u.id = p.id",
                Object[].class);

        List<Object[]> resultados = query.getResultList();
        assertFalse(resultados.isEmpty());

        for (Object[] row : resultados) {
            PetOwner petOwner = (PetOwner) row[0];
            Usuario usuario = (Usuario) row[1];
            assertNotNull(petOwner);
            assertNotNull(usuario);
        }
    }

    @Test
    public void buscarPetOwnerEVerificarDadosUsuario() {
        TypedQuery<Object[]> query = em.createQuery(
                "SELECT p, u FROM PetOwner p LEFT JOIN Usuario u ON u.id = p.id",
                Object[].class);

        List<Object[]> resultados = query.getResultList();

        assertNotNull(resultados);
        assertFalse(resultados.isEmpty());

        boolean existe = false;
        for (Object[] row : resultados) {
            PetOwner petOwner = (PetOwner) row[0];
            Usuario usuario = (Usuario) row[1];
            if (petOwner.getId() == 9L) {
                assertNotNull(usuario);
                assertEquals("Paulo", usuario.getNome());
                existe = true;
            }
        }
        assertEquals(true, existe);
    }
}
