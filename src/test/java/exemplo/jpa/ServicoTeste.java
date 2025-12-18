/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import java.math.BigDecimal;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
/**
 *
 * @author elaine
 */
public class ServicoTeste extends Teste {

    @Test
    public void persistirServico() {
        // PetSitter já existe no dataset (ID 5)
        PetSitter sitter = em.find(PetSitter.class, 5L);
        Assert.assertNotNull(sitter);

        Servico s = new Servico();
        s.setNome("Passeio Longo 1h");
        s.setPrecoHora(new BigDecimal("30.00"));
        s.setPetSitter(sitter);

        em.persist(s);
        em.flush();

        Assert.assertNotNull(s.getId());
    }

    @Test
    public void consultarServico() {
        // ID 1 vem do dataset
        Servico s = em.find(Servico.class, 1L);
        Assert.assertNotNull(s);
        assertEquals("Passeio com cachorro (1h)", s.getNome());
        Assert.assertEquals(new BigDecimal("30.00"), s.getPrecoHora());
    }

    @Test
    public void removerServico() {
        Servico s = em.find(Servico.class, 1L);
        Assert.assertNotNull(s);

        em.remove(s);
        em.flush();

        Assert.assertNull(em.find(Servico.class, 1L));
    }
   
    @Test
    public void atualizarServicoSemMerge() {
        Servico servico = em.find(Servico.class, 1L);

        servico.setNome("Passeio Atualizado");
        servico.setPrecoHora(new BigDecimal("35.00"));

        em.flush();
        em.clear();

        Servico atualizado = em.find(Servico.class, 1L);
        assertEquals("Passeio Atualizado", atualizado.getNome());
        assertEquals(new BigDecimal("35.00"), atualizado.getPrecoHora());
    }

    @Test
    public void atualizarServicoComMerge() {
        Servico servico = em.find(Servico.class, 1L);

        em.clear(); // entidade destacada

        servico.setPrecoHora(new BigDecimal("40.00"));

        Servico gerenciado = em.merge(servico);

        em.flush();
        em.clear();

        Servico atualizado = em.find(Servico.class, gerenciado.getId());
        assertEquals(new BigDecimal("40.00"), atualizado.getPrecoHora());
    }
}