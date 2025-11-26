package exemplo.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_PERFIL_PETOWNER")
public class PerfilPetOwner extends Perfil {

    @Column(name = "PREFERENCIAS_PET", length = 300)
    private String preferenciasPet;

    // Getters e setters
    public String getPreferenciasPet() { return preferenciasPet; }
    public void setPreferenciasPet(String preferenciasPet) { this.preferenciasPet = preferenciasPet; }
}