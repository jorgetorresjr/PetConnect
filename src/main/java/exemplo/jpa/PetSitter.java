package exemplo.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="TB_PETSITTER") 
@DiscriminatorValue(value = "PS")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName = "ID")
public class PetSitter extends Usuario {
    @Column(name = "NUM_VALOR_HORA")
    private Double valorHora;
    @Column(name = "TXT_DISPONIBILIDADE")
    private String disponibilidade;
    @Column(name = "TXT_RESTRICOES")
    private String restricoes;

    public Double getValorHora() {
        return valorHora;
    }

    public void setValorHora(Double valorVendas) {
        this.valorHora = valorVendas;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public String getRestricoes() {
        return restricoes;
    }

    public void setRestricoes(String restricoes) {
        this.restricoes = restricoes;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("exemplo.jpa.PetSitter[");
        sb.append(super.toString());
        sb.append(", R$ ");
        sb.append(valorHora);
        sb.append(" por hora, ");
        sb.append(disponibilidade);  
        sb.append(", ");
        sb.append(restricoes);
        sb.append("]");
        return sb.toString();
    }    
}
