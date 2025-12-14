package exemplo.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="TB_PETOWNER") 
@DiscriminatorValue(value = "PO")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName = "ID")
public class PetOwner extends Usuario {
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Collection<Pet> pets;
    
    @ManyToMany
    @JoinTable(
    name = "TB_FAVORITOS",
    joinColumns = @JoinColumn(name = "PETOWNER_ID"),
    inverseJoinColumns = @JoinColumn(name = "PETSITTER_ID")
    )
    private Collection<PetSitter> favoritos;

    public Collection<Pet> getPets() {
        return pets;
    }
    
    public Collection<PetSitter> getFavoritos() {
        return favoritos;
    }
    

 public void addPet(Pet pet) {
        if (pets == null) {
            pets = new java.util.ArrayList<>();
        }
        pets.add(pet);
        pet.setOwner(this);
    }

 public void addFavorito(PetSitter sitter) {
        if (favoritos == null) {
            favoritos = new ArrayList<>();
        }

        if (!favoritos.contains(sitter)) {
            favoritos.add(sitter);

            if (sitter.getFavoritadoPor() == null) {
                sitter.setFavoritadoPor(new ArrayList<>());
            }
            sitter.getFavoritadoPor().add(this);
        }
    }

    public void removeFavorito(PetSitter sitter) {
        if (favoritos != null) {
            favoritos.remove(sitter);

            if (sitter.getFavoritadoPor() != null) {
                sitter.getFavoritadoPor().remove(this);
            }
        }
    }

  @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PetOwner)) return false;
        PetOwner other = (PetOwner) obj;
        return id != null && id.equals(other.id);
    }

    @Override
    public String toString() {
        return "PetOwner [id=" + id + "]";
    }

}
