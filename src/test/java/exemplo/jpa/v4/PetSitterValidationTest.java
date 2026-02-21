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

            petSitter.setCpf("111.222.333-44"); // CPF inválido e não existe no dataset
            calendar.set(2027, Calendar.JANUARY, 1);
            petSitter.setDataNascimento(calendar.getTime()); // Data futura
            petSitter.setEmail("email_invalido@"); // E-mail inválido
            petSitter.setLogin("logininvalido_unico3"); // login não existe no dataset
            petSitter.setNome("ANA"); // Nome inválido
            petSitter.setSenha("senha123"); // Senha inválida
            petSitter.addTelefone("(81)9000-0001");
            petSitter.addTelefone("(81)9000-0002");
            petSitter.addTelefone("(81)9000-0003");
            petSitter.addTelefone("(81)9000-0004");
            Endereco endereco = petSitter.getEndereco();
            endereco.setBairro("Centro");
            endereco.setCep("12345-678"); // CEP inválido
            endereco.setCidade("Recife");
            endereco.setEstado("ZZ"); // Estado inválido
            endereco.setNumero(10);
            endereco.setComplemento("Apto 101");
            endereco.setLogradouro("Rua das Flores");
            em.persist(petSitter);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.PetSitter.email: deve ser um endereço de e-mail bem formado"),
                                startsWith("class exemplo.jpa.PetSitter.endereco.estado: Estado inválido"),
                                startsWith("class exemplo.jpa.PetSitter.senha: A senha deve possuir pelo menos um caractere de: pontuação, maiúscula, minúscula e número"),
                                startsWith("class exemplo.jpa.PetSitter.telefones: tamanho deve ser entre 0 e 3"),
                                startsWith("class exemplo.jpa.PetSitter.cpf: número do registro de contribuinte individual brasileiro (CPF) inválido"),
                                startsWith("class exemplo.jpa.PetSitter.dataNascimento: deve ser uma data passada"),
                                startsWith("class exemplo.jpa.PetSitter.endereco.cep: CEP inválido. Deve estar no formado NN.NNN-NNN, onde N é número natural"),
                                startsWith("class exemplo.jpa.PetSitter.nome: Deve possuir uma única letra maiúscula, seguida por letras minúsculas")
                        )
                );
            });
            assertEquals(6, constraintViolations.size());
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

        // Atualiza com senha inválida
        petSitter.setSenha("senha123456");
        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("A senha deve possuir pelo menos um caractere de: pontuação, maiúscula, minúscula e número.", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}
