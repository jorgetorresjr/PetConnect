/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author elaine
 */
@Entity
@Table(name = "TB_SERVICO")
public class Servico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SERVICO")
    private Long id;

    @NotBlank(message = "Nome do serviço é obrigatório")
    @Size(min = 4, max = 10, message = "Nome deve ter entre 4 e 10 caracteres")
    @Column(name = "TXT_NOME", nullable = false)
    private String nome;

    @NotNull(message = "Preço por hora é obrigatório")
    @DecimalMin(value = "0.01", inclusive = true, message = "Preço deve conter valor válido")
    @Column(
            name = "NUM_PRECO_HORA",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal precoHora;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_PETSITTER")
    private PetSitter petSitter;

    @OneToMany(mappedBy = "servico", cascade = CascadeType.REMOVE)
    private List<Agendamento> agendamentos;

    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL)
    private java.util.List<Pagamento> pagamentos;

    // =====================
    // GETTERS E SETTERS
    // =====================
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPrecoHora() {
        return precoHora;
    }

    public void setPrecoHora(BigDecimal precoHora) {
        this.precoHora = precoHora;
    }

    public PetSitter getPetSitter() {
        return petSitter;
    }

    public void setPetSitter(PetSitter petSitter) {
        this.petSitter = petSitter;
    }

    public List<Agendamento> getAgendamento() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }
}
