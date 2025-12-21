/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v2;

import exemplo.jpa.Endereco;
import exemplo.jpa.PetOwner;
import exemplo.jpa.PetSitter;
import exemplo.jpa.Teste;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author thayn
 */
public class PetOwnerTeste extends Teste {

    @Test
    public void atualizarPetOwnerSemMerge() {
        // Busca o tutor
        PetOwner petOwner = em.find(PetOwner.class, 1L);

        // Atualização normal
        petOwner.setNome("Mariana Padrao");

        em.flush();
        em.clear();

        // Verifica se foi atualizado
        PetOwner atualizado = em.find(PetOwner.class, 1L);
        assertEquals("Mariana Padrao", atualizado.getNome());
    }

    @Test
    public void atualizarPetOwnerComMerge() {
        // Busca o tutor
        PetOwner petOwner = em.find(PetOwner.class, 2L);

        // Agora a entidade fica destacada
        em.clear();

        // Atualiza objeto destacado
        petOwner.setNome("Marina Merge");
        petOwner.addTelefone("123456");

        PetOwner att = em.merge(petOwner);

        em.flush();
        em.clear();
        // Verifica no banco
        PetOwner atualizado = em.find(PetOwner.class, att.getId());
        assertEquals("Marina Merge", atualizado.getNome());
        assertEquals(2, atualizado.getTelefones().size());
        assertTrue(atualizado.getTelefones().contains("123456"));
    }

    @Test
    public void atualizarNomePetOwnerPadrao() {
        // Cria e persiste um novo tutor
        PetOwner petOwner = em.find(PetOwner.class, 3L);

        em.clear();

        // Atualização padrão
        petOwner.setNome("Paulo Atualizado");
        em.merge(petOwner);
        em.flush();
        em.clear();

        // Verifica se nome foi att
        PetOwner atualizado = em.find(PetOwner.class, petOwner.getId());
        assertEquals("Paulo Atualizado", atualizado.getNome());
    }

    @Test
    public void atualizarEnderecoPetOwnerComMerge() {

        PetOwner petOwner = em.find(PetOwner.class, 2L);
        assertNotNull(petOwner);

        em.clear();

        Endereco novo = new Endereco();
        novo.setCidade("Recife");
        novo.setBairro("Iputinga");
        novo.setLogradouro("Rua Ribeirão");
        novo.setCep("50670-210");
        novo.setEstado("PE");
        novo.setComplemento("Apto 303");
        novo.setNumero(11);

        petOwner.setEndereco(novo);

        PetOwner merged = em.merge(petOwner);
        em.flush();
        em.clear();

        PetOwner atualizado = em.find(PetOwner.class, merged.getId());
        assertNotNull(atualizado.getEndereco());
        assertEquals("Recife", atualizado.getEndereco().getCidade());
        assertEquals("Iputinga", atualizado.getEndereco().getBairro());
        assertEquals("Rua Ribeirão", atualizado.getEndereco().getLogradouro());
        assertEquals(Integer.valueOf(11), atualizado.getEndereco().getNumero());
    }

    @Test
    public void petOwnerFavoritaPetSitter() {
        PetOwner owner = em.find(PetOwner.class, 1L);
        PetSitter sitter = em.find(PetSitter.class, 5L);

        owner.getFavoritos().add(sitter);

        em.flush();
        em.clear();

        PetOwner pOwner = em.find(PetOwner.class, 1L);
        PetSitter pSitter = em.find(PetSitter.class, 5L);

        assertTrue(pOwner.getFavoritos().contains(pSitter));
        assertTrue(pSitter.getFavoritadoPor().contains(pOwner));
    }

    @Test
    public void removerPetOwnerPadrão() {
        PetOwner petOwner = em.find(PetOwner.class, 1L);

        em.remove(petOwner);
        em.flush();

        // Verifica se nome foi att
        PetOwner atualizado = em.find(PetOwner.class, 1L);
        assertNull(atualizado);
    }
}
