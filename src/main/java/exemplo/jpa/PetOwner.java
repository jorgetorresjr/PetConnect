package exemplo.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="TB_PETOWNER") 
@DiscriminatorValue(value = "PO")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName = "ID")
public class PetOwner extends Usuario {
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Pet> pets;
    
    @ManyToMany
    @JoinTable(
    name = "TB_FAVORITOS",
    joinColumns = @JoinColumn(name = "PETOWNER_ID"),
    inverseJoinColumns = @JoinColumn(name = "PETSITTER_ID")
    )
private Set<PetSitter> favoritos = new HashSet<>();

    public Set<Pet> getPets() {
        return pets;
    }
    

    public void addPet(Pet pet) {
        if(pets == null) {
            pets = new HashSet<>();
        }
        pets.add(pet);
    }

    public Set<PetSitter> getFavoritos() {
    return favoritos;
}

public void setFavoritos(Set<PetSitter> favoritos) {
    this.favoritos = favoritos;
}
    @Override
    public String toString() {
        return "exemplo.jpa.PetOwner[ id=" + id + " ]";
    }

}
