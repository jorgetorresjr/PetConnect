package exemplo.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.HashSet;

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
    private Collection<PetOwner> favoritadoPor = new HashSet<>();

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
    favoritadoPor.add(owner);
    owner.getFavoritos().add(this);
    }

    public void removeFavoritadoPor(PetOwner owner) {
    favoritadoPor.remove(owner);
    owner.getFavoritos().remove(this);
    }
    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("exemplo.jpa.PetSitter[");
        sb.append(super.toString());
        sb.append(", R$ ");
        sb.append(valorHora);
        sb.append(" por hora, ");
        sb.append(disponibilidade);
        sb.append(", ");
        sb.append(restricoes);
        sb.append("]");
        return sb.toString();
    }
}
