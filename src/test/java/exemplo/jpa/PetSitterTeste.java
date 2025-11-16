/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa;

import java.util.Calendar;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author masc1
 */
public class PetSitterTeste extends Teste {

    @Test
    public void persistirVendedor() {
        PetSitter vendedor = new PetSitter();
        vendedor.setCpf("248.008.000-56");
        vendedor.setLogin("novovendedor");
        vendedor.setNome("Novo Vendedor da Silva");
        vendedor.setEmail("novovendedor@gmail.com");
        vendedor.setSenha("#987654321#");
        Calendar c = Calendar.getInstance();
        c.set(1991, Calendar.OCTOBER, 12, 0, 0, 0);
        vendedor.setDataNascimento(c.getTime());
        vendedor.setDisponibilidade("Top");
        vendedor.setValorHora(Double.valueOf(1500000));

        Endereco endereco = new Endereco();
        endereco.setBairro("Ipsep");
        endereco.setCep("50770-680");
        endereco.setLogradouro("Avenida das Garças");
        endereco.setEstado("Pernambuco");
        endereco.setNumero(400);
        endereco.setCidade("Recife");
        vendedor.setEndereco(endereco);

        em.persist(vendedor);
        em.flush();

        assertNotNull(vendedor.getId());
    }

    @Test
    public void consultarPetSitter() {
        // Busca o primeiro PetSitter inserido pelo dataset.xml para obter seu ID
        PetSitter vendedor = em.createQuery("SELECT v FROM PetSitter v WHERE v.cpf = '484.854.847-03'", PetSitter.class).getSingleResult();
        assertNotNull(vendedor);
        Long id = vendedor.getId();
        assertNotNull(id);
        
        // Agora consulta pela chave primária (ID) como requerido
        PetSitter vendedorConsultado = em.find(PetSitter.class, id);
        assertNotNull(vendedorConsultado);
        assertEquals("484.854.847-03", vendedorConsultado.getCpf());
        assertEquals("v1silva", vendedorConsultado.getLogin());
        Calendar c = Calendar.getInstance();
        c.set(1995, Calendar.NOVEMBER, 23, 0, 0, 0);
        assertEquals(c.getTime().toString(), vendedorConsultado.getDataNascimento().toString());
        assertEquals("vendedor1@gmail.com", vendedorConsultado.getEmail());
        assertEquals(Double.valueOf("10500.50"), vendedorConsultado.getValorHora());
        assertEquals("EXPERIENTE", vendedorConsultado.getDisponibilidade());

        Endereco endereco = vendedorConsultado.getEndereco();
        assertNotNull(endereco);
        assertEquals("Pernambuco", endereco.getEstado());
        assertEquals("50670-210", endereco.getCep());
        assertEquals("Iputinga", endereco.getBairro());
        assertEquals("Rua Ribeirão", endereco.getLogradouro());
    }

}
