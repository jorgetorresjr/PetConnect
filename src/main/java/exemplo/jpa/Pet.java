/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import exemplo.jpa.Enums.TipoAnimal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

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

    @NotBlank(message = "Nome do pet é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    @Column(name = "TXT_NOME")
    private String nome;

    @Column(name = "TXT_RACA")
    private String raca;

    @Column(name = "NUM_IDADE")
    private Integer idade;

    @NotNull(message = "Tipo de animal é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "TXT_TIPO_ANIMAL")
    private TipoAnimal tipoAnimal;

    @NotBlank(message = "Sexo é obrigatório")
    @Pattern(regexp = "MACHO|FEMEA", message = "Sexo deve ser MACHO ou FEMEA")
    @Column(name = "TXT_SEXO")
    private String sexo;

    @Column(name = "TXT_TEMPERAMENTO")
    private String temperamento;

    @Column(name = "TXT_ESTADO_SAUDE")
    private String estadoSaude;

    @Column(name = "BOOL_CASTRADO", columnDefinition = "SMALLINT DEFAULT 0")
    private Boolean castrado;

    @Column(name = "BOOL_ATIVO", columnDefinition = "SMALLINT DEFAULT 0")
    private Boolean ativo;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "IMG_FOTO", table = "TB_FOTO_PET", nullable = true)
    private byte[] foto;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private PetOwner owner;

    public Pet() {
    }

    public Pet(String nome, String raca, Integer idade, TipoAnimal tipoAnimal,
            String sexo, String temperamento, String estadoSaude,
            Boolean castrado, Boolean ativo, PetOwner owner) {
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.tipoAnimal = tipoAnimal;
        this.sexo = sexo;
        this.temperamento = temperamento;
        this.estadoSaude = estadoSaude;
        this.castrado = castrado;
        this.ativo = ativo;
        this.owner = owner;
    }

    // Getters e Setters
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

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public TipoAnimal getTipoAnimal() {
        return tipoAnimal;
    }

    public void setTipoAnimal(TipoAnimal tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTemperamento() {
        return temperamento;
    }

    public void setTemperamento(String temperamento) {
        this.temperamento = temperamento;
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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public PetOwner getOwner() {
        return owner;
    }

    public void setOwner(PetOwner owner) {
        this.owner = owner;
    }
}
