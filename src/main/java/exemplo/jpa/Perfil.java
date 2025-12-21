package exemplo.jpa;


import jakarta.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TB_PERFIL")
@DiscriminatorColumn(name = "DTYPE") 
public abstract class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BIO", length = 500)
    private String bio;

    @Column(name = "FOTO_URL")
    private String fotoUrl;

    @Column(name = "SOCIAL_URL")
    private String socialUrl;

    @OneToOne(mappedBy = "perfil", cascade = CascadeType.REMOVE)
    private Usuario usuario;
    
    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getSocialUrl() {
        return socialUrl;
    }

    public void setSocialUrl(String socialUrl) {
        this.socialUrl = socialUrl;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
   
}
