/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v4;

import exemplo.jpa.Avaliacao;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Set;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author thayn
 */
public class AvaliacaoValidationTest extends Teste {

    @Test(expected = ConstraintViolationException.class)
    public void persistirAvaliacaoInvalida() {
        Avaliacao avaliacao = null;
        try {
            avaliacao = new Avaliacao();
            avaliacao.setNota(0); // inválido: menor que 1
            avaliacao.setComentario("a".repeat(501)); // inválido: mais de 500 caracteres
          

            em.persist(avaliacao);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach(violation -> {
                assertThat(
                        violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.Avaliacao.nota: Nota deve ser no mínimo 1"),
                                startsWith("class exemplo.jpa.Avaliacao.comentario: Comentário deve ter no máximo 500 caracteres")
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
        // Busca uma Avaliacao já existente no dataset pelo ID
        TypedQuery<Avaliacao> query = em.createQuery("SELECT a FROM Avaliacao a WHERE a.id = :id", Avaliacao.class);
        query.setParameter("id", 1L);
        Avaliacao avaliacao = query.getSingleResult();

        avaliacao.setNota(6); // inválido: maior que 5
        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertThat(violation.getMessage(), startsWith("Nota deve ser no máximo 5"));
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}
