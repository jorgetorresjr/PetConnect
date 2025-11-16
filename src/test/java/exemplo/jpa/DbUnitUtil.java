/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.w3c.dom.*;
import javax.xml.parsers.*;

/**
 *
 * @author MASC
 */
public class DbUnitUtil {

    private static final String XML_FILE = "/dbunit/dataset.xml";

    public static void inserirDados(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            
            // Parse do XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream in = DbUnitUtil.class.getResourceAsStream(XML_FILE);
            Document doc = builder.parse(in);
            
            NodeList usuarios = doc.getElementsByTagName("TB_USUARIO");
            NodeList petOwners = doc.getElementsByTagName("TB_PETOWNER");
            NodeList petSitters = doc.getElementsByTagName("TB_PETSITTER");
            NodeList pets = doc.getElementsByTagName("TB_PET");
            
            // Mapear índices para IDs gerados
            java.util.Map<Integer, Usuario> usuariosMap = new java.util.HashMap<>();
            
            // Inserir Usuarios (PetOwners e PetSitters)
            for (int i = 0; i < usuarios.getLength(); i++) {
                Element usuario = (Element) usuarios.item(i);
                String discUsuario = usuario.getAttribute("disc_usuario");
                
                Usuario u;
                if ("PO".equals(discUsuario)) {
                    u = new PetOwner();
                } else if ("PS".equals(discUsuario)) {
                    PetSitter ps = new PetSitter();
                    // Buscar atributos específicos de PetSitter
                    for (int j = 0; j < petSitters.getLength(); j++) {
                        Element psElement = (Element) petSitters.item(j);
                        if (psElement.getAttribute("id_usuario").equals(String.valueOf(i + 1))) {
                            ps.setValorHora(Double.parseDouble(psElement.getAttribute("num_valor_hora")));
                            ps.setDisponibilidade(psElement.getAttribute("txt_disponibilidade"));
                            ps.setRestricoes(psElement.getAttribute("txt_restricoes"));
                            break;
                        }
                    }
                    u = ps;
                } else {
                    continue;
                }
                
                u.setCpf(usuario.getAttribute("txt_cpf"));
                u.setDataNascimento(parseDate(usuario.getAttribute("dt_nascimento")));
                u.setEmail(usuario.getAttribute("txt_email"));
                u.setLogin(usuario.getAttribute("txt_login"));
                u.setNome(usuario.getAttribute("txt_nome"));
                u.setSenha(usuario.getAttribute("txt_senha"));
                u.setEndereco(criarEndereco(
                    usuario.getAttribute("end_txt_bairro"),
                    usuario.getAttribute("end_txt_cep"),
                    usuario.getAttribute("end_txt_cidade"),
                    usuario.getAttribute("end_txt_complemento"),
                    usuario.getAttribute("end_txt_estado"),
                    usuario.getAttribute("end_txt_logradouro"),
                    Integer.parseInt(usuario.getAttribute("end_numero"))
                ));
                
                em.persist(u);
                usuariosMap.put(i + 1, u);
            }
            
            em.flush(); // Garante que IDs sejam gerados
            
            // Inserir Pets
            for (int i = 0; i < pets.getLength(); i++) {
                Element petElement = (Element) pets.item(i);
                Pet pet = new Pet();
                
                pet.setNome(petElement.getAttribute("txt_nome"));
                pet.setIdade(Integer.parseInt(petElement.getAttribute("num_idade")));
                pet.setSexo(Sexo.valueOf(petElement.getAttribute("txt_sexo")));
                pet.setRaca(petElement.getAttribute("txt_raca"));
                pet.setTipoAnimal(TipoAnimal.valueOf(petElement.getAttribute("txt_tipo_animal")));
                pet.setEstadoSaude(petElement.getAttribute("txt_estado_saude"));
                pet.setCastrado("1".equals(petElement.getAttribute("bool_castrado")));
                pet.setTemperamento(petElement.getAttribute("txt_temperamento"));
                
                // Associar ao owner
                int idUsuario = Integer.parseInt(petElement.getAttribute("id_usuario"));
                Usuario owner = usuariosMap.get(idUsuario);
                if (owner instanceof PetOwner) {
                    pet.setOwner((PetOwner) owner);
                }
                
                em.persist(pet);
            }
            
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException(ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }
    
    private static java.util.Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }
    
    private static Endereco criarEndereco(String bairro, String cep, String cidade, 
            String complemento, String estado, String logradouro, int numero) {
        Endereco e = new Endereco();
        e.setBairro(bairro);
        e.setCep(cep);
        e.setCidade(cidade);
        e.setComplemento(complemento);
        e.setEstado(estado);
        e.setLogradouro(logradouro);
        e.setNumero(numero);
        return e;
    }
}
