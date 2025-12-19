/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author thayn
 */
public class PetOwnerNewTeste extends Teste {

    @Test
    public void atualizarPetOwnerSemMerge() {
        // Busca o tutor
        PetOwner petOwner = em.find(PetOwner.class, 2L);

        // Atualização normal
        petOwner.setNome("Maria Padrao");

        em.flush();
        em.clear();

        // Verifica se foi atualizado
        PetOwner atualizado = em.find(PetOwner.class, 2L);
        assertEquals("Maria Padrao", atualizado.getNome());
    }

    @Test
    public void atualizarPetOwnerComMerge() {
        // Busca o tutor
        PetOwner petOwner = em.find(PetOwner.class, 1L);

        // Agora a entidade fica destacada
        em.clear();

        // Atualiza objeto destacado
        petOwner.setNome("Maria Merge");

        PetOwner att = em.merge(petOwner);

        em.flush();
        em.clear();
        // Verifica no banco
        PetOwner atualizado = em.find(PetOwner.class, att.getId());
        assertEquals("Maria Merge", atualizado.getNome());
    }

    @Test
    public void atualizarNomePetOwnerPadrao() {
        // Cria e persiste um novo tutor
        PetOwner petOwner = em.find(PetOwner.class, 1L);

        em.clear();

        // Atualização padrão
        petOwner.setNome("Maria Atualizada");
        em.merge(petOwner);
        em.flush();
        em.clear();

        // Verifica se nome foi att
        PetOwner atualizado = em.find(PetOwner.class, petOwner.getId());
        assertEquals("Maria Atualizada", atualizado.getNome());
    }

    @Test
    public void removerPetOwnerPadrao() {
        // Cria e persiste um novo tutor
        PetOwner petOwner = em.find(PetOwner.class, 1L);
        // Remove dependências
        PetOwner po = em.find(PetOwner.class, 1L);

        // Agendamentos
        po.getPets().forEach(p -> {
            // nada aqui, só garantindo pets carregados
        });

        // Agendamentos não estão mapeados no PetOwner,
        // então buscamos via navegação indireta
        em.flush();

        for (Agendamento ag : petOwner.getAgendamentos()) {
            for (Avaliacao av : ag.getAvaliacoes()) {
                em.remove(av);
            }
            em.remove(ag);
        }

        // Remove notificações via herança de Usuario
        for (Notificacao n : petOwner.getNotificacoes()) {
            em.remove(n);
        }

        em.remove(petOwner);
        em.flush();

        // Verifica se nome foi att
        PetOwner atualizado = em.find(PetOwner.class, 1L);
        assertNull(atualizado);
    }
}
