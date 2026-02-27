/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v4;

import exemplo.jpa.Pet;
import exemplo.jpa.PetOwner;
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
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author thayn
 */
public class PetValidationTest extends Teste {

    @Test(expected = ConstraintViolationException.class)
    public void persistirPetInvalido() {
        Pet pet = null;
        try {
            pet = new Pet();
            pet.setNome(null); // NotBlank
            pet.setTipoAnimal(null); // NotNull
            pet.setSexo("OUTRO"); // Pattern
            pet.setIdade(-5); // @PositiveOrZero
            pet.setRaca("a".repeat(51)); // Maior que 50
            pet.setTemperamento("a".repeat(101)); // Maior que 100
            pet.setEstadoSaude("a".repeat(101)); // Maior que 100
            pet.setOwner(new PetOwner());

            em.persist(pet);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach(violation -> {
                assertThat(
                        violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.Pet.nome: não deve"),
                                startsWith("class exemplo.jpa.Pet.tipoAnimal: não deve"),
                                startsWith("class exemplo.jpa.Pet.sexo: Sexo deve ser MACHO ou FEMEA"),
                                startsWith("class exemplo.jpa.Pet.idade: deve ser maior ou igual a 0"),
                                startsWith("class exemplo.jpa.Pet.raca: tamanho"),
                                startsWith("class exemplo.jpa.Pet.temperamento: tamanho"),
                                startsWith("class exemplo.jpa.Pet.estadoSaude: tamanho")
                        )
                    );
            });
                assertEquals(7, constraintViolations.size()); // Testando todas as novas anotações juntas
            assertNull(pet.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarPetInvalido() {
        TypedQuery<Pet> query = em.createQuery("SELECT p FROM Pet p WHERE p.id = :id", Pet.class);
        query.setParameter("id", 1L);
        Pet pet = query.getSingleResult();

        pet.setIdade(-2); // Testa apenas 1 campo
        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();
            assertTrue(violation.getMessage().contains("0"));
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}