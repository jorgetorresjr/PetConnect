package exemplo.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="TB_PETOWNER") 
@DiscriminatorValue(value = "C")
@PrimaryKeyJoinColumn(name="ID_USER", referencedColumnName = "ID")
public class PetOwner extends User {
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
   

    @Override
    public String toString() {
        return "exemplo.jpa.PetOwner[ id=" + id + " ]";
    }

}
