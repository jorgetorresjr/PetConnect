package exemplo.jpa;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class TesteJPA {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo_06");
    private static final Logger logger = Logger.getGlobal();
    
    static {
        logger.setLevel(Level.INFO);
    }
    
    public static void main(String[] args) {
        try {
            Long id = inserirUser();
            //consultarUsuario(id);
        } finally {
            emf.close();
        }
    }
    
    private static void consultarUsuario(Long id) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            System.out.println("Consultando usuário na base...");
            User usuario = em.find(User.class, id);
            System.out.println("Imprimindo usuário (telefones serão recuperados agora)...");
            System.out.println(usuario.toString());
        } finally {
            if (em != null) {
                em.close();
            }            
        }
    }
    
    public static Long inserirUser() {
        User petOwner = criarPetOwner();
        User petSitter = criarPetSitter();
        
        EntityManager em = null;
        EntityTransaction et = null;
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            em.persist(petOwner);
            em.persist(petSitter);
            et.commit();
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                logger.log(Level.SEVERE,
                        "Cancelando transação com erro. Mensagem: {0}", ex.getMessage());
                et.rollback();
                logger.info("Transação cancelada.");
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return petOwner.getId();
    }
    
    private static PetOwner criarPetOwner() {
        PetOwner petOwner = new PetOwner();
        petOwner.setNome("Sicrano da Silva");
        petOwner.setEmail("sicrano@gmail.com");
        petOwner.setLogin("sicrano");
        petOwner.setSenha("sicrano123");
        petOwner.setCpf("534.585.764-40");
        petOwner.addTelefone("(81) 3456-2525");
        petOwner.addTelefone("(81) 9122-4528");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1981);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DAY_OF_MONTH, 25);
        petOwner.setDataNascimento(c.getTime());
        criarAddress(petOwner);
      
        
        return petOwner;
    }
    
    private static PetSitter criarPetSitter() {
        PetSitter petSitter = new PetSitter();
        petSitter.setNome("Fulano da Silva");
        petSitter.setEmail("fulano@gmail.com");
        petSitter.setLogin("fulano");
        petSitter.setSenha("teste");
        petSitter.setCpf("534.585.764-45");
        petSitter.addTelefone("(81) 3456-2525");
        petSitter.addTelefone("(81) 9122-4528");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1981);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DAY_OF_MONTH, 25);
        petSitter.setDataNascimento(c.getTime());
        criarAddress(petSitter);
        petSitter.setDisponibilidade("Vendedor Ouro");
        petSitter.setValorHora(50000.00);
        
        return petSitter;
    }
    
    public static void criarAddress(User usuario) {
        Address address = new Address();
        address.setLogradouro("Rua Iolanda Rodrigues Sobral");
        address.setBairro("Iputinga");
        address.setCidade("Recife");
        address.setEstado("Pernambuco");
        address.setCep("50690-220");
        address.setNumero(550);
        usuario.setEndereco(address);
    }
    
   
}
