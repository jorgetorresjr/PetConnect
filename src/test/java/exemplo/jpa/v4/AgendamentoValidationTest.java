/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v4;

import exemplo.jpa.Agendamento;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
public class AgendamentoValidationTest extends Teste {

    @Test(expected = ConstraintViolationException.class)
    public void persistirAgendamentoInvalido() {
        Agendamento agendamento = null;
        try {
            agendamento = new Agendamento();
             agendamento.setDataInicio(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 10, 0))); // Data passada

            em.persist(agendamento);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach(violation -> {
                assertThat(
                        violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.Agendamento.dataInicio: A data não pode ser passada")
                                )
                );
            });
            assertEquals(1, constraintViolations.size());
            assertNull(agendamento.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarAgendamentoInvalido() {
        // Busca um Agendamento já existente no dataset pelo ID
        TypedQuery<Agendamento> query = em.createQuery("SELECT a FROM Agendamento a WHERE a.id = :id", Agendamento.class);
        query.setParameter("id", 1L); 
        Agendamento agendamento = query.getSingleResult();

          agendamento.setDataInicio(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 10, 0))); // Data passada // Data passada
        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertThat(violation.getMessage(), startsWith("A data não pode ser passada"));
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}
