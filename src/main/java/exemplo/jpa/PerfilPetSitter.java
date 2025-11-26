package exemplo.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_PERFIL_PETSITTER")
public class PerfilPetSitter extends Perfil {

    @Column(name = "EXPERIENCIA", length = 300)
    private String experiencia;

    @Column(name = "CERTIFICACOES", length = 300)
    private String certificacoes;

    // Getters e setters
    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { this.experiencia = experiencia; }

    public String getCertificacoes() { return certificacoes; }
    public void setCertificacoes(String certificacoes) { this.certificacoes = certificacoes; }
}