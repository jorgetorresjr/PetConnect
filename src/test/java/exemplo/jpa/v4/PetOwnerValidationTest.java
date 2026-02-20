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
          
            petOwner.setCpf("111.222.373-74"); // CPF inválido e não existe no dataset
            calendar.set(2028, Calendar.JANUARY, 1);
            petOwner.setDataNascimento(calendar.getTime()); // Data futura
            petOwner.setEmail("email_invalidos@"); // E-mail inválido
            petOwner.setLogin("logininvalido_unico4"); // login não existe no dataset
            petOwner.setNome("BEA"); // Nome inválido
            petOwner.setSenha("senha123"); // Senha inválida
            petOwner.addTelefone("(81)9000-0006");
            petOwner.addTelefone("(81)9000-0007");
            petOwner.addTelefone("(81)9000-0008");
            petOwner.addTelefone("(81)9000-0009");
            Endereco endereco = petOwner.getEndereco();
            endereco.setBairro("Ouro Preto");
            endereco.setCep("12345-679"); // CEP inválido
            endereco.setCidade("Recife");
            endereco.setEstado("ZR"); // Estado inválido
            endereco.setNumero(10);
            endereco.setComplemento("Apto 102");
            endereco.setLogradouro("Rua das Arvores");
            em.persist(petOwner);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.PetOwner.email: deve ser um endereço de e-mail bem formado"),
                                startsWith("class exemplo.jpa.PetOwner.endereco.estado: Estado inválido"),
                                startsWith("class exemplo.jpa.PetOwner.senha: A senha deve possuir pelo menos um caractere de: pontuação, maiúscula, minúscula e número"),
                                startsWith("class exemplo.jpa.PetOwner.telefones: tamanho deve ser entre 0 e 3"),
                                startsWith("class exemplo.jpa.PetOwner.cpf: número do registro de contribuinte individual brasileiro (CPF) inválido"),
                                startsWith("class exemplo.jpa.PetOwner.dataNascimento: deve ser uma data passada"),
                                startsWith("class exemplo.jpa.PetOwner.endereco.cep: CEP inválido. Deve estar no formado NN.NNN-NNN, onde N é número natural"),
                                startsWith("class exemplo.jpa.PetOwner.nome: Deve possuir uma única letra maiúscula, seguida por letras minúsculas")
                        )
                );
            });
            assertEquals(6, constraintViolations.size());
            assertNull(petOwner.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarPetOwnerInvalido() {
        // Busca um PetOwner já existente no dataset pelo CPF
        TypedQuery<PetOwner> query = em.createQuery("SELECT p FROM PetOwner p WHERE p.cpf = :cpf", PetOwner.class);
        query.setParameter("cpf", "159.753.486-20"); // CPF válido do dataset
        PetOwner petOwner = query.getSingleResult();

        // Atualiza com senha inválida
        petOwner.setSenha("senha1234577");
        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("A senha deve possuir pelo menos um caractere de: pontuação, maiúscula, minúscula e número", violation.getMessage());
            assertEquals(2, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}
