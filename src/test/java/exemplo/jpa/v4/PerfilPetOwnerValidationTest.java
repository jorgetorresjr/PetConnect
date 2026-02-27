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

public class PerfilPetOwnerValidationTest extends Teste {

    @Test(expected = ConstraintViolationException.class)
    public void persistirPerfilPetOwnerInvalido() {
        PerfilPetOwner perfil = null;
        try {
            perfil = new PerfilPetOwner();
            perfil.setPreferenciasPet("a".repeat(301)); // maior que 300
            perfil.setBio(""); // Adicionado para testar o @NotBlank da Bio

            em.persist(perfil);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach(violation -> {
                assertThat(
                        violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.PerfilPetOwner.preferenciasPet: tamanho"),
                                startsWith("class exemplo.jpa.PerfilPetOwner.bio: não deve")
                        )
                );
            });
            assertEquals(2, constraintViolations.size()); // Pega a Bio e o Tamanho
            assertNull(perfil.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarPerfilPetOwnerInvalido() {

        TypedQuery<PerfilPetOwner> query
                = em.createQuery(
                        "SELECT p FROM PerfilPetOwner p WHERE p.id = :id",
                        PerfilPetOwner.class
                );

        query.setParameter("id", 4L);
        PerfilPetOwner perfil = query.getSingleResult();

        perfil.setPreferenciasPet("a".repeat(301)); // maior que 300

        try {
            em.flush();

        } catch (ConstraintViolationException ex) {

            ConstraintViolation<?> violation
                    = ex.getConstraintViolations().iterator().next();

            assertEquals("tamanho deve ser entre 0 e 300",
                    violation.getMessage());

            assertEquals(1, ex.getConstraintViolations().size());

            throw ex;
        }
    }
}
