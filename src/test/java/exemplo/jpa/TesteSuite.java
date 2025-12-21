/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author masc1
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    exemplo.jpa.v1.PetSitterTeste.class,
    exemplo.jpa.v1.PetOwnerTeste.class,
    exemplo.jpa.v1.PetTeste.class,
    exemplo.jpa.v2.PetTeste.class,
    exemplo.jpa.v2.PetSitterTeste.class,
    exemplo.jpa.v2.PetOwnerTeste.class,
    exemplo.jpa.v2.PerfilPetOwnerTeste.class,
    exemplo.jpa.v2.PerfilPetSitterTeste.class,
    exemplo.jpa.v2.ServicoTeste.class,
    exemplo.jpa.v2.AgendamentoTeste.class,
    exemplo.jpa.v2.NotificacaoTeste.class,
    exemplo.jpa.v2.AvaliacaoTeste.class,
    exemplo.jpa.v2.PagamentoTeste.class
})
public class TesteSuite {
    
}
