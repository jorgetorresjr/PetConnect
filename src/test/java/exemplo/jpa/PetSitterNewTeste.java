package exemplo.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author thayn
 */
public class PetSitterNewTeste extends Teste {

    @Test
    public void atualizarPetSitterSemMerge() {
        // Busca o petsitter
        PetSitter petSitter = em.find(PetSitter.class, 5L);

        // Atualização normal
        petSitter.setNome("João Padrao");

        em.flush();
        em.clear();
        // Verifica se foi atualizado
        PetSitter atualizado = em.find(PetSitter.class, 5L);
        assertEquals("João Padrao", atualizado.getNome());
    }

    @Test
    public void atualizarPetSitterComMerge() {
        // Busca o petSitter
        PetSitter petSitter = em.find(PetSitter.class, 6L);

        // Agora a entidade fica destacada
        em.clear();

        // Atualiza objeto destacado
        petSitter.setNome("José Merge");

        PetSitter att = em.merge(petSitter);

        em.flush();
        em.clear();
        // Verifica no banco
        PetSitter atualizado = em.find(PetSitter.class, att.getId());
        assertEquals("José Merge", atualizado.getNome());
    }

    @Test
    public void atualizarNomePetSitterPadrao() {
       
        PetSitter petSitter = em.find(PetSitter.class, 6L);

        em.clear();

        // Atualização padrão
        petSitter.setNome("Pedro Atualizado");
        em.merge(petSitter);
        em.flush();
        em.clear();

        // Verifica se nome foi att
        PetSitter atualizado = em.find(PetSitter.class, petSitter.getId());
        assertEquals("Pedro Atualizado", atualizado.getNome());
    }

    @Test
    public void removerPetSitterPadrao() {
        // Cria e persiste um novo tutor
        PetSitter petSitter = em.find(PetSitter.class, 7L);

        em.remove(petSitter);
        em.flush();

        // Verifica se nome foi att
        PetSitter atualizado = em.find(PetSitter.class, 7L);
        assertNull(atualizado);
    }
}