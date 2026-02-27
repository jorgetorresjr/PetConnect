/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.v3;

import exemplo.jpa.Notificacao;
import exemplo.jpa.Teste;
import jakarta.persistence.TypedQuery;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
/**
 *
 * @author elaine
 */
public class NotificacaoTest extends Teste {

    @Test
    public void listarTiposDeMensagensDistintas() {
        // No XML temos 3 notificações, mas apenas 2 textos únicos
        TypedQuery<String> query = em.createQuery(
            "SELECT DISTINCT(n.mensagem) FROM Notificacao n", String.class);

        List<String> mensagensUnicas = query.getResultList();
        assertEquals(2, mensagensUnicas.size());
    }
    
    @Test
    public void buscarNotificacaoComUsuarioFetch() {
        TypedQuery<Notificacao> query = em.createQuery(
            "SELECT n FROM Notificacao n JOIN FETCH n.usuario WHERE n.id = :id", Notificacao.class);
        
        query.setParameter("id", 1L);
        Notificacao notificacao = query.getSingleResult();
        
        // Verifica se trouxe o objeto e se o usuário está preenchido
        assertNotNull(notificacao);
        assertNotNull(notificacao.getUsuario());
        assertEquals("testpo1", notificacao.getUsuario().getLogin());
    }
    
    @Test
    public void buscarNotificacoesQueContemPalavra() {
        TypedQuery<Notificacao> query = em.createQuery(
            "SELECT n FROM Notificacao n WHERE n.mensagem LIKE :termo", Notificacao.class);
        
        query.setParameter("termo", "%confirmado%"); 

        List<Notificacao> resultados = query.getResultList();
        assertEquals(1, resultados.size());
    }
    
    @Test
    public void listarNotificacoesAntigas() {
        TypedQuery<Notificacao> query = em.createQuery(
            "SELECT n FROM Notificacao n WHERE n.dataEnvio < :dataCorte", Notificacao.class);
        
        // Data de corte: 1 de Dezembro de 2025
        query.setParameter("dataCorte", java.sql.Timestamp.valueOf("2025-12-01 00:00:00"));

        List<Notificacao> resultados = query.getResultList();
        // Apenas a notificação de Novembro deve vir
        assertEquals(1, resultados.size()); 
    }
}