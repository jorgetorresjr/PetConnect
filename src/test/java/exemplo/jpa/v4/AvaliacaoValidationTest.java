package exemplo.jpa.v4;

import exemplo.jpa.Avaliacao;
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

public class AvaliacaoValidationTest extends Teste {

    @Test(expected = ConstraintViolationException.class)
    public void persistirAvaliacaoInvalida() {

        Avaliacao avaliacao = null;

        try {
            avaliacao = new Avaliacao();
            avaliacao.setNota(0); // menor que 1
            avaliacao.setComentario("a".repeat(501)); // maior que 500

            em.persist(avaliacao);
            em.flush();

        } catch (ConstraintViolationException ex) {

            Set<ConstraintViolation<?>> constraintViolations
                    = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(
                        violation.getRootBeanClass() + "."
                        + violation.getPropertyPath() + ": "
                        + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.Avaliacao.nota: Nota deve ser no mínimo"),
                                startsWith("class exemplo.jpa.Avaliacao.comentario: Comentário deve ter no máximo")
                        )
                );
            });

            assertEquals(2, constraintViolations.size());
            assertNull(avaliacao.getId());

            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarAvaliacaoInvalida() {

        TypedQuery<Avaliacao> query
                = em.createQuery(
                        "SELECT a FROM Avaliacao a WHERE a.id = :id",
                        Avaliacao.class
                );

        query.setParameter("id", 1L);
        Avaliacao avaliacao = query.getSingleResult();

        avaliacao.setNota(6); // maior que 5

        try {
            em.flush();

        } catch (ConstraintViolationException ex) {

            ConstraintViolation<?> violation
                    = ex.getConstraintViolations().iterator().next();

            assertEquals("Nota deve ser no máximo 5.",
                    violation.getMessage());

            assertEquals(1, ex.getConstraintViolations().size());

            throw ex;
        }
    }
}
