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
import static org.junit.Assert.*;
import org.junit.Test;

public class ServicoValidationTest extends Teste {

    @Test(expected = ConstraintViolationException.class)
    public void persistirServicoInvalido() {
        Servico servico = null;
        try {
            servico = new Servico();
            servico.setNome(null); // Nulo para testar @NotBlank
            servico.setPrecoHora(new BigDecimal("-10.00")); // preço negativo testa o @Positive

            em.persist(servico);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach(violation -> {
                assertThat(
                        violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.Servico.nome: não deve estar em branco"),
                                startsWith("class exemplo.jpa.Servico.precoHora: deve ser maior que 0")
                        )
                );
            });
            assertEquals(2, constraintViolations.size());
            assertNull(servico.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarServicoInvalido() {
        TypedQuery<Servico> query = em.createQuery("SELECT s FROM Servico s WHERE s.id = :id", Servico.class);
        query.setParameter("id", 1L);
        Servico servico = query.getSingleResult();

        servico.setNome(""); // Testa só 1 coisa na atualização
        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();
            assertThat(violation.getMessage(), CoreMatchers.anyOf(startsWith("tamanho deve ser"), startsWith("não deve estar")));
            throw ex;
        }
    }
}