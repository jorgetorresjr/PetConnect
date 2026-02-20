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
            pet.setNome(""); // nome obrigatório e com tamanho mínimo
            pet.setTipoAnimal(null);// tipoAnimal obrigatório
            pet.setSexo("OUTRO"); // sexo deve ser MACHO ou FEMEA
            pet.setOwner(new PetOwner()); // deve ter um petowner valido

            em.persist(pet);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach(violation -> {
                assertThat(
                        violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.Pet.nome: Nome do pet é obrigatório"),
                                startsWith("class exemplo.jpa.Pet.nome: Nome deve ter entre 2 e 50 caracteres"),
                                startsWith("class exemplo.jpa.Pet.tipoAnimal: Tipo de animal é obrigatório"),
                                startsWith("class exemplo.jpa.Pet.sexo: Sexo deve ser MACHO ou FEMEA")
                        )
                );
            });
            assertEquals(4, constraintViolations.size());
            assertNull(pet.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarPetInvalido() {
        // Busca um Pet já existente no dataset pelo ID
        TypedQuery<Pet> query = em.createQuery("SELECT p FROM Pet p WHERE p.id = :id", Pet.class);
        query.setParameter("id", 1L);
        Pet pet = query.getSingleResult();

        pet.setNome(""); // nome obrigatório e tamanho mínimo
        pet.setTipoAnimal(null);// tipoAnimal obrigatório
        pet.setSexo("OUTRO"); // sexo deve ser MACHO ou FEMEA
   
        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertThat(violation.getMessage(), CoreMatchers.anyOf(
                    startsWith("Nome do pet é obrigatório"),
                    startsWith("Nome deve ter entre 2 e 50 caracteres"),
                    startsWith("Tipo de animal é obrigatório"),
                    startsWith("Sexo deve ser MACHO ou FEMEA")
            ));
            assertEquals(4, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}
