package exemplo.jpa;

import exemplo.jpa.Enums.TipoServico;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_PERFIL_PETSITTER")
@PrimaryKeyJoinColumn(name = "ID")
@DiscriminatorValue("PS")
public class PerfilPetSitter extends Perfil {

    @Column(name = "EXPERIENCIA", length = 300)
    private String experiencia;

    @Column(name = "CERTIFICACOES", length = 300)
    private String certificacoes;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_SERVICO", length = 50)
    private TipoServico tipoServico;

    // Getters e setters
    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getCertificacoes() {
        return certificacoes;
    }

    public void setCertificacoes(String certificacoes) {
        this.certificacoes = certificacoes;
    }

    public TipoServico getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }
}
