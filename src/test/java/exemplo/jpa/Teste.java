/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author masc1
 */
public abstract class Teste {
    protected static EntityManagerFactory emf;
    protected EntityManager em;
    protected EntityTransaction et;
    
    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("PetConnect");
        DbUnitUtil.inserirDados();        
    }
    
    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }
    
    @Before
    public void setUp() {
        em = emf.createEntityManager();
        et = em.getTransaction();
        et.begin();        
    }
    
    @After
    public void tearDown() {
        if (et.isActive()) {
            et.rollback();
        }
        em.close();        
    }
}
