/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v4;

import exemplo.jpa.PerfilPetSitter;
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
public class PerfilPetSitterValidationTest  extends Teste {
        @Test(expected = ConstraintViolationException.class)
    public void persistirPerfilPetSitterInvalido() {
        PerfilPetSitter perfil = null;
        try {
            perfil = new PerfilPetSitter();
            perfil.setExperiencia(""); // obrigatório e não pode ser vazio
            perfil.setCertificacoes("a".repeat(301)); //mais de 300 caracteres
            perfil.setTipoServico(null); // obrigatório

            em.persist(perfil);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach(violation -> {
                assertThat(
                    violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                    CoreMatchers.anyOf(
                        startsWith("class exemplo.jpa.PerfilPetSitter.experiencia: Experiência é obrigatória"),
                        startsWith("class exemplo.jpa.PerfilPetSitter.certificacoes: Certificações deve ter no máximo 300 caracteres"),
                        startsWith("class exemplo.jpa.PerfilPetSitter.tipoServico: Tipo de serviço é obrigatório")
                    )
                );
            });
            assertEquals(3, constraintViolations.size());
            assertNull(perfil.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarPerfilPetSitterInvalido() {
        // Busca um PerfilPetSitter já existente no dataset pelo ID
        TypedQuery<PerfilPetSitter> query = em.createQuery("SELECT p FROM PerfilPetSitter p WHERE p.id = :id", PerfilPetSitter.class);
        query.setParameter("id", 5L);
        PerfilPetSitter perfil = query.getSingleResult();

        perfil.setExperiencia(""); // obrigatório e não pode ser vazio
        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("Experiência é obrigatória", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}
