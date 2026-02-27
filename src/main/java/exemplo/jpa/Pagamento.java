/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import exemplo.jpa.Enums.StatusPagamento;
import exemplo.jpa.Enums.TipoPagamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author jorge
 */
@Entity
@Table(name = "TB_PAGAMENTO")
public class Pagamento {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    @Column(name = "VLR_VALOR", precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    @NotNull
    @Future
    @Column(name = "DT_DATA")
    private LocalDate data;

    @NotNull
    @Column(name = "DT_HORA")
    private LocalTime hora;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ID_PETOWNER", nullable = false)
    private PetOwner petOwner;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ID_SERVICO", nullable = false)
    private Servico servico;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TXT_TIPO_PAGAMENTO", nullable = false)
    private TipoPagamento tipoPagamento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TXT_STATUS_PAGAMENTO", nullable = false)
    private StatusPagamento status;

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public PetSitter getPetSitter() {
        return servico.getPetSitter();
    }

    public PetOwner getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(PetOwner petOwner) {
        this.petOwner = petOwner;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

} 