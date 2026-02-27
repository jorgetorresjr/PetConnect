package exemplo.jpa.v4;

import exemplo.jpa.Endereco;
import exemplo.jpa.PetSitter;
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
 * Testes de validação para PetSitter
 */

public class PetSitterValidationTest extends Teste {

    @Test(expected = ConstraintViolationException.class)
    public void persistirPetSitterInvalido() {
        PetSitter petSitter = null;
        Calendar calendar = new GregorianCalendar();
        try {
            petSitter = new PetSitter();
            petSitter.setCpf("111.222.333-44"); // Inválido
            calendar.set(2027, Calendar.JANUARY, 1);
            petSitter.setDataNascimento(calendar.getTime()); // Futuro
            petSitter.setEmail("email_invalido@"); // Inválido
            petSitter.setLogin("logininvalido_unico3");
            petSitter.setNome("ANA"); // Padrão Inválido
            petSitter.setSenha("senha123"); // Fraca
            petSitter.addTelefone("1"); petSitter.addTelefone("2"); petSitter.addTelefone("3"); petSitter.addTelefone("4"); // Mais de 3
            
            // NOVO: Testando o @Positive do valorHora
            petSitter.setValorHora(-50.0); 

            Endereco endereco = petSitter.getEndereco();
            endereco.setLogradouro(""); // NotBlank
            endereco.setBairro(""); // NotBlank
            endereco.setCidade(""); // NotBlank
            endereco.setCep("12345-678"); // Inválido
            endereco.setEstado("ZZ"); // Estado inválido
            endereco.setNumero(-10); // Negativo (@Positive)
            endereco.setComplemento("Apto 101");
            
            em.persist(petSitter);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.PetSitter.email: deve ser um endereço de e-mail"),
                                startsWith("class exemplo.jpa.PetSitter.endereco.estado: Estado inválido"),
                                startsWith("class exemplo.jpa.PetSitter.senha: A senha deve"),
                                startsWith("class exemplo.jpa.PetSitter.telefones: tamanho deve ser"),
                                startsWith("class exemplo.jpa.PetSitter.cpf: número do registro"),
                                startsWith("class exemplo.jpa.PetSitter.dataNascimento: deve ser uma data"),
                                startsWith("class exemplo.jpa.PetSitter.endereco.cep: CEP inválido"),
                                startsWith("class exemplo.jpa.PetSitter.nome: Deve possuir uma única"),
                                startsWith("class exemplo.jpa.PetSitter.endereco.logradouro: não deve estar em branco"),
                                startsWith("class exemplo.jpa.PetSitter.endereco.bairro: não deve estar em branco"),
                                startsWith("class exemplo.jpa.PetSitter.endereco.cidade: não deve estar em branco"),
                                startsWith("class exemplo.jpa.PetSitter.endereco.numero: deve ser maior que 0"),
                                startsWith("class exemplo.jpa.PetSitter.valorHora: deve ser maior que 0")
                        )
                );
            });
            assertTrue("Deveria ter encontrado as violações", constraintViolations.size() >= 5);
            assertNull(petSitter.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarPetSitterInvalido() {
        // Busca um PetSitter já existente no dataset pelo CPF
        TypedQuery<PetSitter> query = em.createQuery("SELECT p FROM PetSitter p WHERE p.cpf = :cpf", PetSitter.class);
        query.setParameter("cpf", "153.509.460-56"); // CPF válido do dataset
        PetSitter petSitter = query.getSingleResult();

        // Atualiza apenas com senha inválida (Testando 1 coisa na atualização)
        petSitter.setSenha("senha123456");
        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();
            assertEquals("A senha deve possuir pelo menos um caractere de: pontuação, maiúscula, minúscula e número.", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}