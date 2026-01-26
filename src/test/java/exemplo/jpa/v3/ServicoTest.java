/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v3;

import exemplo.jpa.Servico;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ServicoTest extends Teste {

    @Test
    public void listarServicosComPrecoHoraMaiorQue50() {
        TypedQuery<Servico> query = em.createQuery(
            "SELECT s FROM Servico s WHERE s.precoHora > :valor",
            Servico.class
        );
        query.setParameter("valor", BigDecimal.valueOf(50));

        List<Servico> servicos = query.getResultList();

        for (Servico s : servicos) {
            Assert.assertTrue(
                s.getPrecoHora().compareTo(BigDecimal.valueOf(50)) > 0
            );
        }
    }

    @Test
    public void buscarServicoPorNome() {
        TypedQuery<Servico> query = em.createQuery(
            "SELECT s FROM Servico s WHERE s.nome = :nome",
            Servico.class
        );
        query.setParameter("nome", "Banho");

        Servico servico = query.getSingleResult();

        Assert.assertEquals("Banho", servico.getNome());
    }

    @Test
    public void listarServicosComPetSitter() {
        TypedQuery<Servico> query = em.createQuery(
            "SELECT s FROM Servico s WHERE s.petSitter IS NOT NULL",
            Servico.class
        );

        List<Servico> servicos = query.getResultList();

        for (Servico s : servicos) {
            Assert.assertNotNull(s.getPetSitter());
        }
    }

    @Test
    public void listarServicosComAgendamentos() {
        TypedQuery<Servico> query = em.createQuery(
            "SELECT DISTINCT s FROM Servico s JOIN s.agendamentos a",
            Servico.class
        );

        List<Servico> servicos = query.getResultList();

        Assert.assertFalse(servicos.isEmpty());
    }
}