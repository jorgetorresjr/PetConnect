package exemplo.jpa.v4;

import exemplo.jpa.Endereco;
import exemplo.jpa.PetOwner;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import static org.junit.Assert.assertTrue;

/**
 * Testes de validação para PetOwner
 */
public class PetOwnerValidationTest extends Teste {

    @Test(expected = ConstraintViolationException.class)
    public void persistirPetOwnerInvalido() {
        PetOwner petOwner = null;
        Calendar calendar = new GregorianCalendar();
        try {
            petOwner = new PetOwner();
            petOwner.setCpf("111.222.373-74"); // Inválido
            calendar.set(2028, Calendar.JANUARY, 1);
            petOwner.setDataNascimento(calendar.getTime()); // Futuro
            petOwner.setEmail("email_invalidos@"); // Inválido
            petOwner.setNome("BEA"); // Padrão Inválido
            petOwner.setSenha("senha123"); // Fraca
            petOwner.addTelefone("1"); petOwner.addTelefone("2"); petOwner.addTelefone("3"); petOwner.addTelefone("4"); // Mais de 3

            Endereco endereco = petOwner.getEndereco();
            endereco.setLogradouro(""); // NotBlank
            endereco.setBairro(""); // NotBlank
            endereco.setCidade(""); // NotBlank
            endereco.setCep("12345-679"); // Formato incorreto
            endereco.setEstado("ZR"); // Estado inválido
            endereco.setNumero(-10); // Negativo (@Positive)
            
            em.persist(petOwner);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.PetOwner.email: deve ser um endereço de e-mail"),
                                startsWith("class exemplo.jpa.PetOwner.endereco.estado: Estado inválido"),
                                startsWith("class exemplo.jpa.PetOwner.senha: A senha deve"),
                                startsWith("class exemplo.jpa.PetOwner.telefones: tamanho"),
                                startsWith("class exemplo.jpa.PetOwner.cpf: número do registro"),
                                startsWith("class exemplo.jpa.PetOwner.dataNascimento: deve ser"),
                                startsWith("class exemplo.jpa.PetOwner.endereco.cep: CEP"),
                                startsWith("class exemplo.jpa.PetOwner.nome: Deve possuir"),
                                startsWith("class exemplo.jpa.PetOwner.endereco.logradouro: não deve"),
                                startsWith("class exemplo.jpa.PetOwner.endereco.bairro: não deve"),
                                startsWith("class exemplo.jpa.PetOwner.endereco.cidade: não deve"),
                                startsWith("class exemplo.jpa.PetOwner.endereco.numero: deve ser maior que 0")
                        )
                );
            });
            assertTrue("Deveria ter encontrado as violações", constraintViolations.size() >= 5);
            assertNull(petOwner.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarPetOwnerInvalido() {
        TypedQuery<PetOwner> query = em.createQuery("SELECT p FROM PetOwner p WHERE p.cpf = :cpf", PetOwner.class);
        query.setParameter("cpf", "111.444.777-35"); 
        PetOwner petOwner = query.getSingleResult();

        petOwner.setSenha("senha1234577"); // Atualização isolada
        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();
            assertEquals("A senha deve possuir pelo menos um caractere de: pontuação, maiúscula, minúscula e número.", violation.getMessage());
            throw ex;
        }
    }
}