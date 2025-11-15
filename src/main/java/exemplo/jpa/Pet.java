/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "TB_PET")
@SecondaryTable(
    name = "TB_FOTO_PET",
    pkJoinColumns = @PrimaryKeyJoinColumn(name = "ID_PET")
)
public class Pet implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TXT_NOME")
    private String nome;
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "IMG_FOTO", table = "TB_FOTO_PET", nullable = true)
    private byte[] foto;
    @Column(name = "NUM_IDADE")
    private int idade;
    @Column(name = "TXT_SEXO")
    private String sexo;
    @Column(name = "TXT_RACA")
    private String raca;
    @Column(name = "TXT_TIPO_ANIMAL")
    private String tipoAnimal;
    @Column(name = "TXT_ESTADO_SAUDE")
    private String estadoSaude;
    @Column(name = "BOOL_CASTRADO")
    private Boolean castrado;
    @Column(name = "TXT_TEMPERAMENTO")
    private String temperamento;
    @Column(name = "BOOL_ATIVO")
    private Boolean ativo = true;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private PetOwner owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getTipoAnimal() {
        return tipoAnimal;
    }

    public void setTipoAnimal(String tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }

    public String getEstadoSaude() {
        return estadoSaude;
    }

    public void setEstadoSaude(String estadoSaude) {
        this.estadoSaude = estadoSaude;
    }

    public Boolean getCastrado() {
        return castrado;
    }

    public void setCastrado(Boolean castrado) {
        this.castrado = castrado;
    }

    public String getTemperamento() {
        return temperamento;
    }

    public void setTemperamento(String temperamento) {
        this.temperamento = temperamento;
    }

    public PetOwner getOwner() {
        return owner;
    }

    public void setOwner(PetOwner owner) {
        this.owner = owner;
    }
    
    public void cadastrarPet(String nome, int idade, String sexo, String raca, 
            String tipoAnimal, String estadoSaude, Boolean castrado, 
            String temperamento, PetOwner owner) {
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.raca = raca;
        this.tipoAnimal = tipoAnimal;
        this.estadoSaude = estadoSaude;
        this.castrado = castrado;
        this.temperamento = temperamento;
        this.owner = owner;
    }
    public void editarPet(String nome, int idade, String sexo, String raca,
            String tipoAnimal, String estadoSaude, Boolean castrado, 
            String temperamento) {
    this.nome = nome;
    this.idade = idade;
    this.sexo = sexo;
    this.raca = raca;
    this.tipoAnimal = tipoAnimal;
    this.estadoSaude = estadoSaude;
    this.castrado = castrado;
    this.temperamento = temperamento;
}
    public void desabilitarPet() {
    this.ativo = false;
}

}
