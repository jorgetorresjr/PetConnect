/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import org.junit.Assert;
import org.junit.Test;
import java.util.Date;
/**
 *
 * @author elaine
 */
public class NotificacaoTeste extends Teste {
    
    @Test
    public void persistirNotificacao() {
        // Busca um usuário existente no dataset (ID 1)
        Usuario u = em.find(Usuario.class, 1L); 
        Assert.assertNotNull(u);

        Notificacao n = new Notificacao();
        n.setUsuario(u);
        n.setMensagem("Novo agendamento confirmado");
        n.setDataEnvio(new Date());

        em.persist(n);
        em.flush(); // Força a execução do INSERT. O rollback em tearDown() desfaz.

        Assert.assertNotNull(n.getId());
    }

    @Test
    public void consultarNotificacao() {
        // ID 1 é carregado pelo dataset.xml
        Notificacao n = em.find(Notificacao.class, 1L);
        Assert.assertNotNull(n);
        Assert.assertEquals("Notificacao inicial do sistema.", n.getMensagem());
        Assert.assertNotNull(n.getUsuario());
    }
    
    @Test
    public void removerNotificacao() {
        // Remove a notificação que foi carregada pelo dataset.xml
        Notificacao n = em.find(Notificacao.class, 1L);
        Assert.assertNotNull(n);

        em.remove(n);
        em.flush();

        Assert.assertNull(em.find(Notificacao.class, 1L));
    }
}