/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 *
 * @author elaine
 */
@Entity
@Table(name = "TB_AVALIACAO")
public class Avaliacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AVALIACAO")
    private Long id;

    @Column(name = "NUM_NOTA", nullable = false)
    private Integer nota; // escala 1 a 5

    @Column(name = "TXT_COMENTARIO", length = 500)
    private String comentario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_AGENDAMENTO")
    private Agendamento agendamento;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }
}
