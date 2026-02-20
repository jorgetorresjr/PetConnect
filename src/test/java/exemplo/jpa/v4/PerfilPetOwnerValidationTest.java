/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v4;

import exemplo.jpa.PerfilPetOwner;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Set;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 *
 * @author thayn
 */
public class PerfilPetOwnerValidationTest extends Teste {

    @Test(expected = ConstraintViolationException.class)
    public void persistirPerfilPetOwnerInvalido() {
        PerfilPetOwner perfil = null;
        try {
            perfil = new PerfilPetOwner();
            perfil.setPreferenciasPet("b".repeat(301)); //mais de 300 caracteres

            em.persist(perfil);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach(violation -> {
                assertThat(
                        violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.PerfilPetOwner.preferenciasPet: Preferências deve ter no máximo 300 caracteres")
                        )
                );
            });
            assertEquals(1, constraintViolations.size());
            assertNull(perfil.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarPerfilPetOwnerInvalido() {
        // Busca um PerfilPetSitter já existente no dataset pelo ID
        TypedQuery<PerfilPetOwner> query = em.createQuery("SELECT p FROM PerfilPetOwner p WHERE p.id = :id", PerfilPetOwner.class);
        query.setParameter("id", 4L);
        PerfilPetOwner perfil = query.getSingleResult();

        perfil.setPreferenciasPet("Lorem ipsum dolor sit amet "
                + "consectetur adipisicing elit. "
                + "Cupiditate eos fuga quas dolore "
                + "consequatur dignissimos deserunt odio id, "
                + "nostrum non? Quos tempore perspiciatis "
                + "amet quod similique assumenda ratione "
                + "quam enim.lorem ipsum dolor sit amet consectetur adipisicing elit. "
                + "Cupiditate eos fuga quas dolore consequatur dignissimos "
                + "deserunt odio id, nostrum non? "
                + "Quos tempore perspiciatis amet quod similique assumenda "
                + "ratione quam enim."); // nao pode ser maior que 300
        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("Preferências deve ter no máximo 300 caracteres", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}
