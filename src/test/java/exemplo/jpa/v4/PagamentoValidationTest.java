/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v4;

import exemplo.jpa.Pagamento;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Set;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author jorge
 */
public class PagamentoValidationTest extends Teste {

    @Test(expected = ConstraintViolationException.class)
    public void persistirPagamentoInvalido() {
        Pagamento pagamento = null;

        try {
            pagamento = new Pagamento();
            pagamento.setValor(null);
            pagamento.setData(LocalDate.now());
            pagamento.setHora(null);
            pagamento.setPetOwner(null);
            pagamento.setServico(null);
            pagamento.setTipoPagamento(null);
            pagamento.setStatus(null);

            em.persist(pagamento);
            em.flush();
        } catch (ConstraintViolationException ex) {

            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(
                        violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.Pagamento.valor: não deve ser nulo"),
                                startsWith("class exemplo.jpa.Pagamento.data: deve ser uma data futura"),
                                startsWith("class exemplo.jpa.Pagamento.hora: não deve ser nulo"),
                                startsWith("class exemplo.jpa.Pagamento.petOwner: não deve ser nulo"),
                                startsWith("class exemplo.jpa.Pagamento.servico: não deve ser nulo"),
                                startsWith("class exemplo.jpa.Pagamento.tipoPagamento: não deve ser nulo"),
                                startsWith("class exemplo.jpa.Pagamento.status: não deve ser nulo")
                        )
                );
            });

            assertEquals(7, constraintViolations.size());
            assertNull(pagamento.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarPagamentoInvalido() {

        TypedQuery<Pagamento> query =
                em.createQuery("SELECT p FROM Pagamento p WHERE p.valor=150.00", Pagamento.class);

        Pagamento pagamento = query.getSingleResult();
        pagamento.setData(LocalDate.now());

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {

            ConstraintViolation<?> violation =
                    ex.getConstraintViolations().iterator().next();

            assertEquals("deve ser uma data futura", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}