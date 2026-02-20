/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v4;

import exemplo.jpa.Servico;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.math.BigDecimal;
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
public class ServicoValidationTest extends Teste {

    @Test(expected = ConstraintViolationException.class)
    public void persistirServicoInvalido() {
        Servico servico = null;
        try {
            servico = new Servico();
            servico.setNome(""); // nome obrigatório e tamanho mínimo
            servico.setPrecoHora(new BigDecimal("-10.00")); // preço negativo

            em.persist(servico);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach(violation -> {
                assertThat(
                        violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.Servico.nome: Nome do serviço é obrigatório"),
                                startsWith("class exemplo.jpa.Servico.nome: Nome deve ter entre 4 e 10 caracteres"),
                                startsWith("class exemplo.jpa.Servico.precoHora: Preço deve conter valor válido")
                        )
                );
            });
            assertEquals(3, constraintViolations.size());
            assertNull(servico.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarServicoInvalido() {

        TypedQuery<Servico> query = em.createQuery("SELECT s FROM Servico s WHERE s.id = :id", Servico.class);
        query.setParameter("id", 1L);
        Servico servico = query.getSingleResult();

        servico.setNome("");
        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertThat(violation.getMessage(), CoreMatchers.anyOf(
                    startsWith("Nome do serviço é obrigatório"),
                    startsWith("Nome deve ter entre 4 e 10 caracteres")
            ));
            assertEquals(2, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}
