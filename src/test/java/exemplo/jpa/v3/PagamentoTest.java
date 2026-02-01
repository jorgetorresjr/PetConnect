/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package exemplo.jpa.v3;

import exemplo.jpa.Enums.StatusPagamento;
import exemplo.jpa.Pagamento;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author jorge
 */
public class PagamentoTest extends Teste{
    
    @Test
    public void buscarPagamentosAcimaDe100() {
        TypedQuery<Pagamento> query = em.createQuery("SELECT p FROM Pagamento p WHERE p.valor > :valor", Pagamento.class);
        query.setParameter("valor", 100);
        
        List<Pagamento> pagamentos = query.getResultList();
        
        assertEquals(2, pagamentos.size());
       
    }

    @Test
    public void contarPagamentosConcluidos() {
        
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(p) FROM Pagamento p WHERE p.status = :status", Long.class);
        
        query.setParameter("status", StatusPagamento.CONFIRMADO);
        
        Long pagamentos = query.getSingleResult();
        
        assertEquals(Long.valueOf(1), pagamentos);
    }
    
    @Test
    public void mediaPagamentosPendentesAcimaDe100() {
        TypedQuery<Double> query = em.createQuery("SELECT AVG(p.valor) FROM Pagamento p WHERE p.status = :status AND p.valor > :valor", Double.class);
        
        query.setParameter("status", StatusPagamento.PENDENTE);
        query.setParameter("valor", BigDecimal.valueOf(100));
        
        Double media = query.getSingleResult();
        
        assertEquals(135.0, media, 0.001);
        
 
    }

    @Test
    public void somarValoresDePagamentosConfirmados() {
        TypedQuery<BigDecimal> query = em.createQuery("SELECT SUM(p.valor) FROM Pagamento p WHERE p.status = :status", BigDecimal.class);
        
        query.setParameter("status", StatusPagamento.CONFIRMADO);
        
        BigDecimal pagamentos = query.getSingleResult();
        
        assertTrue(pagamentos.compareTo(BigDecimal.valueOf(80)) == 0);
                
    }
}
