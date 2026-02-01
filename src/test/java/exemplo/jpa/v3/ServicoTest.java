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
        "SELECT s FROM Servico s WHERE s.precoHora BETWEEN :min AND :max",
        Servico.class
    );
    query.setParameter("min", BigDecimal.valueOf(50));
    query.setParameter("max", BigDecimal.valueOf(500));

    List<Servico> servicos = query.getResultList();

    Assert.assertFalse(servicos.isEmpty());

    for (Servico s : servicos) {
        Assert.assertTrue(
            s.getPrecoHora().compareTo(BigDecimal.valueOf(50)) >= 0
        );
    }
}

    @Test
public void buscarServicoPorNome() {
    TypedQuery<Servico> query = em.createQuery(
        "SELECT s FROM Servico s WHERE LOWER(s.nome) LIKE :nome",
        Servico.class
    );
    query.setParameter("nome", "%hospedagem%");

    List<Servico> resultados = query.getResultList();

    Assert.assertFalse(resultados.isEmpty());

    for (Servico s : resultados) {
        Assert.assertTrue(
            s.getNome().toLowerCase().contains("hospedagem")
        );
    }
}

    @Test
public void listarServicosComPetSitter() {
    TypedQuery<Servico> query = em.createQuery(
        "SELECT s FROM Servico s WHERE s.petSitter IS NOT NULL ORDER BY s.precoHora DESC",
        Servico.class
    );

    List<Servico> servicos = query.getResultList();

    Assert.assertFalse(servicos.isEmpty());

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