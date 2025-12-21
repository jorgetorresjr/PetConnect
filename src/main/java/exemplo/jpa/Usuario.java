package exemplo.jpa;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_USUARIO")
@SecondaryTable(
        name = "TB_FOTO_USUARIO",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "ID_USUARIO")
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DISC_USUARIO",
        discriminatorType = DiscriminatorType.STRING, length = 2)
public abstract class Usuario {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    @Embedded
    protected Endereco endereco;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PERFIL_ID")
    private Perfil perfil;
    
    @ElementCollection
    @CollectionTable(name = "TB_TELEFONE",
            joinColumns = @JoinColumn(name = "ID_USUARIO"))
    @Column(name = "TXT_NUM_TELEFONE")
    protected Collection<String> telefones;
    
    @Column(name = "TXT_CPF")
    protected String cpf;
    
    @Column(name = "TXT_LOGIN")
    protected String login;
    
    @Column(name = "TXT_NOME")
    protected String nome;
    
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "IMG_FOTO", table = "TB_FOTO_USUARIO", nullable = true)
    private byte[] foto;
    
    @Column(name = "TXT_EMAIL")
    protected String email;
    
    @Column(name = "TXT_SENHA")
    protected String senha;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_NASCIMENTO", nullable = true)
    protected Date dataNascimento;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<Notificacao> notificacoes;

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Collection<String> getTelefones() {
        return telefones;
    }
    
    public void setTelefones(Collection<String> telefones) {
        this.telefones = telefones;
    }

    public void addTelefone(String telefone) {
        if (telefones == null) {
            telefones = new ArrayList<>();
        }
        telefones.add(telefone);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;

        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.id);
        sb.append(", ");
        sb.append(this.nome);
        sb.append(", ");
        sb.append(this.login);
        sb.append(", ");
        sb.append(this.cpf);

        for (String telefone : this.telefones) {
            sb.append(", ");
            sb.append(telefone);
        }

        return sb.toString();
    }
}
