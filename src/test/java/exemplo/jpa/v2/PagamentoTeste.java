/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v2;

import exemplo.jpa.Enums.StatusPagamento;
import exemplo.jpa.Enums.TipoPagamento;
import exemplo.jpa.Pagamento;
import exemplo.jpa.PetOwner;
import exemplo.jpa.Servico;
import exemplo.jpa.Teste;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author jorge
 */
public class PagamentoTeste extends Teste {
    @Test
    public void persistirPagamento() {
        Servico servico = em.find(Servico.class, 2L);
        PetOwner petOwner = em.find(PetOwner.class, 3L); 
        
        Pagamento pagamento = new Pagamento(); 
        pagamento.setPetOwner(petOwner);
        pagamento.setServico(servico);
        pagamento.setValor(new BigDecimal("160.00"));
        pagamento.setData(LocalDate.now());
        pagamento.setHora(LocalTime.now());
        pagamento.setTipoPagamento(TipoPagamento.BOLETO);
        pagamento.setStatus(StatusPagamento.PENDENTE);
        
        em.persist(pagamento);
        em.flush();
        
        assertNotNull(pagamento.getId());
        assertNotNull(pagamento.getServico().getPetSitter());
    }
    
    @Test
    public void atualizarPagamentoSemMerge() {
        Pagamento pagamento = em.find(Pagamento.class, 3L);
        
        pagamento.setStatus(StatusPagamento.CONFIRMADO);
        
        em.flush();
        em.clear();
        
        Pagamento pagAtualizado = em.find(Pagamento.class, 3L);
        assertEquals(StatusPagamento.CONFIRMADO, pagAtualizado.getStatus());
    }
    
    @Test
    public void atualizarPagamentoComMerge() {
        Pagamento pagamento = em.find(Pagamento.class, 2L);
        em.clear();
        
        pagamento.setValor(new BigDecimal("125.00"));
        
        Pagamento pagMerge = em.merge(pagamento);
        em.flush();
        em.clear();
        
        Pagamento pagAtualizado = em.find(Pagamento.class, pagMerge.getId());
        assertEquals(new BigDecimal("125.00"), pagAtualizado.getValor());
    }
    
    @Test
    public void consultarPagamento() {
        Pagamento pagamento = em.find(Pagamento.class, 1L);
        assertNotNull(pagamento);
        assertEquals(TipoPagamento.PIX, pagamento.getTipoPagamento());
        assertEquals(StatusPagamento.CONFIRMADO, pagamento.getStatus());
    }
    
    @Test
    public void removerPagamento() {
        Pagamento pagamento = em.find(Pagamento.class, 4L);
        assertNotNull(pagamento);
        
        em.remove(pagamento);
        em.flush();
        
        assertNull(em.find(Pagamento.class, 4L));
    }
    
    
}
