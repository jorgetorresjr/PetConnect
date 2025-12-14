package exemplo.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;


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
    
    @ManyToMany(mappedBy = "favoritos")
    private Collection<PetOwner> favoritadoPor;

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

   
 public void addFavoritadoPor(PetOwner owner) {
        if (favoritadoPor == null) {
            favoritadoPor = new ArrayList<>();
        }
        if (!favoritadoPor.contains(owner)) {
            favoritadoPor.add(owner);
            owner.addFavorito(this);
        }
    }

    public void removeFavoritadoPor(PetOwner owner) {
        if (favoritadoPor != null) {
            favoritadoPor.remove(owner);
            owner.getFavoritos().remove(this);
        }
    }
   
    
    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PetSitter)) return false;
        PetSitter other = (PetSitter) obj;
        return id != null && id.equals(other.id);
    }
    
    
    @Override
    public String toString() {
        return "PetSitter [id=" + id +
               ", R$ " + valorHora +
               ", " + disponibilidade +
               ", " + restricoes + "]";
    }
}
