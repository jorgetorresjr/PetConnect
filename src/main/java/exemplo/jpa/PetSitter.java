package exemplo.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "TB_PETSITTER")
@DiscriminatorValue(value = "PS")
@PrimaryKeyJoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
public class PetSitter extends Usuario {

    @Column(name = "NUM_VALOR_HORA")
    private Double valorHora;
    @Column(name = "TXT_DISPONIBILIDADE")
    private String disponibilidade;
    @Column(name = "TXT_RESTRICOES")
    private String restricoes;

    @ManyToMany(mappedBy = "favoritos", cascade = CascadeType.REMOVE)
    private Collection<PetOwner> favoritadoPor;

    @OneToMany(mappedBy = "petSitter", cascade = CascadeType.REMOVE)
    private List<Servico> servicos;

    public Double getValorHora() {
        return valorHora;
    }

    public void setValorHora(Double valorVendas) {
        this.valorHora = valorVendas;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public String getRestricoes() {
        return restricoes;
    }

    public void setRestricoes(String restricoes) {
        this.restricoes = restricoes;
    }

    public Collection<PetOwner> getFavoritadoPor() {
        return favoritadoPor;
    }

    public void setFavoritadoPor(Collection<PetOwner> favoritadoPor) {
        this.favoritadoPor = favoritadoPor;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PetSitter)) {
            return false;
        }
        PetSitter other = (PetSitter) obj;
        return id != null && id.equals(other.id);
    }

    @Override
    public String toString() {
        return "PetSitter [id=" + id
                + ", R$ " + valorHora
                + ", " + disponibilidade
                + ", " + restricoes + "]";
    }
}
