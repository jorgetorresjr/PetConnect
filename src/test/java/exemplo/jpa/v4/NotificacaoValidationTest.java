/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v4;

import exemplo.jpa.Notificacao;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Date;
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
public class NotificacaoValidationTest extends Teste {

    @Test(expected = ConstraintViolationException.class)
    public void persistirNotificacaoInvalida() {

        Notificacao notificacao = null;

        try {

            notificacao = new Notificacao();
            notificacao.setMensagem("Mensagem válida");

            // Data futura (inválida para @PastOrPresent)
            notificacao.setDataEnvio(
                    new Date(System.currentTimeMillis() + 100000)
            );

            notificacao.setUsuario(em.find(exemplo.jpa.Usuario.class, 1L));

            em.persist(notificacao);
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
                                startsWith("class exemplo.jpa.Notificacao.dataEnvio: deve ser uma data")
                        )
                );
            });

            assertEquals(1, constraintViolations.size());
            assertNull(notificacao.getId());

            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarNotificacaoInvalida() {

        TypedQuery<Notificacao> query
                = em.createQuery(
                        "SELECT n FROM Notificacao n WHERE n.id = :id",
                        Notificacao.class
                );

        query.setParameter("id", 1L);
        Notificacao notificacao = query.getSingleResult();

        // Define data futura (inválida)
        notificacao.setDataEnvio(
                new Date(System.currentTimeMillis() + 100000)
        );

        try {

            em.flush();

        } catch (ConstraintViolationException ex) {

            ConstraintViolation<?> violation
                    = ex.getConstraintViolations().iterator().next();

            assertEquals(
                    "deve ser uma data no passado ou no presente",
                    violation.getMessage()
            );

            assertEquals(1, ex.getConstraintViolations().size());

            throw ex;
        }
    }
}