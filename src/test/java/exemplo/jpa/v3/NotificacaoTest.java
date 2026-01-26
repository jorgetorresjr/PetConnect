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
import org.junit.Test;
/**
 *
 * @author elaine
 */
public class NotificacaoTest extends Teste {

    @Test
    public void buscarPorMensagemExata() {
        // 1. Igualdade de String (=)
        // No XML existem 2 notificações exatamente com este texto
        TypedQuery<Notificacao> query = em.createQuery(
            "SELECT n FROM Notificacao n WHERE n.mensagem = :msg", Notificacao.class);
        
        query.setParameter("msg", "Novo pedido de agendamento");

        List<Notificacao> resultados = query.getResultList();
        assertEquals(2, resultados.size());
    }

    @Test
    public void contarNotificacoesDoUsuario() {
        // 2. Agregação (COUNT)
        // O usuário 1 tem apenas 1 notificação ("Agendamento confirmado") no XML
        TypedQuery<Long> query = em.createQuery(
            "SELECT COUNT(n) FROM Notificacao n WHERE n.usuario.id = :id", Long.class);
        
        query.setParameter("id", 1L);

        Long total = query.getSingleResult();
        assertEquals(Long.valueOf(1), total);
    }

    @Test
    public void buscarNotificacoesQueContemPalavra() {
        // 3. Busca Parcial (LIKE)
        // Busca mensagens que tenham a palavra "confirmado". Apenas a primeira tem.
        TypedQuery<Notificacao> query = em.createQuery(
            "SELECT n FROM Notificacao n WHERE n.mensagem LIKE :termo", Notificacao.class);
        
        query.setParameter("termo", "%confirmado%");

        List<Notificacao> resultados = query.getResultList();
        assertEquals(1, resultados.size());
    }

    @Test
    public void listarNotificacoesPorNomeDoUsuario() {
        // 4. Join Implícito (Navegação)
        // Busca notificações de quem tem "PetOwner" no nome.
        // Usuários 1, 2, 3 e 4 são "PetOwner", e as notificações são dos usuários 1, 2 e 3.
        TypedQuery<Notificacao> query = em.createQuery(
            "SELECT n FROM Notificacao n WHERE n.usuario.nome LIKE :nomeUsuario", Notificacao.class);
        
        query.setParameter("nomeUsuario", "%PetOwner%");

        List<Notificacao> resultados = query.getResultList();
        // Espera-se 3 notificações (uma de cada usuário: 1, 2 e 3)
        assertEquals(3, resultados.size());
    }
}