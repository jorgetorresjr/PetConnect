package exemplo.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name="TB_PETOWNER") 
@DiscriminatorValue(value = "PO")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName = "ID")
public class PetOwner extends Usuario {
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Collection<Pet> pets;

    public Collection<Pet> getPets() {
        return pets;
    }

    public void addPet(Pet pet) {
        if(pets == null) {
            pets = new HashSet<>();
        }
        pets.add(pet);
    }

    @Override
    public String toString() {
        return "exemplo.jpa.PetOwner[ id=" + id + " ]";
    }

}
