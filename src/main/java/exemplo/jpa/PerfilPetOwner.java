package exemplo.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "TB_PERFIL_PETOWNER")
@PrimaryKeyJoinColumn(name = "ID")
@DiscriminatorValue("PO")
public class PerfilPetOwner extends Perfil {

    @Size(max = 300)
    @Column(name = "PREFERENCIAS_PET", length = 300)
    private String preferenciasPet;

    // Getters e setters
    public String getPreferenciasPet() {
        return preferenciasPet;
    }

    public void setPreferenciasPet(String preferenciasPet) {
        this.preferenciasPet = preferenciasPet;
    }
}
