/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
/**
 *
 * @author elaine
 */

@Entity
@Table(name = "TB_AGENDAMENTO")
public class Agendamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AGENDAMENTO")
    private Long id;

    @Column(name = "DT_INICIO", nullable = false)
    private Timestamp dataInicio;

    @Column(name = "NUM_HORAS", nullable = false)
    private Integer horas;

    @Column(name = "BOOL_CONFIRMADO", nullable = false)
    private Boolean confirmado = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_PETOWNER", referencedColumnName = "ID_USUARIO")
    private PetOwner petOwner;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_SERVICO", referencedColumnName = "ID_SERVICO")
    private Servico servico;
    
    @OneToOne(mappedBy = "agendamento")
    private Avaliacao avaliacao;

    // ===== Getters e Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDataInicio() {
        return dataInicio;
}

    public void setDataInicio(Timestamp dataInicio) {
        this.dataInicio = dataInicio;
}

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Boolean getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(Boolean confirmado) {
        this.confirmado = confirmado;
    }

    public PetOwner getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(PetOwner petOwner) {
        this.petOwner = petOwner;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }
    
    public Avaliacao getAvaliacao() {
    return avaliacao;
}

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
}
}
