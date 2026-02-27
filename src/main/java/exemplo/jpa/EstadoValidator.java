/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;


public class EstadoValidator implements ConstraintValidator<ValidaEstado, String> {
    private List<String> estados;
    
    @Override
    public void initialize(ValidaEstado validaEstado) {
        this.estados = new ArrayList<>();
        this.estados.add("AC");
        this.estados.add("AL");
        this.estados.add("AP");
        this.estados.add("AM");
        this.estados.add("BA");
        this.estados.add("CE");
        this.estados.add("DF");
        this.estados.add("ES");
        this.estados.add("GO");
        this.estados.add("MA");
        this.estados.add("MT");
        this.estados.add("MS");
        this.estados.add("MG");
        this.estados.add("PA");
        this.estados.add("PB");
        this.estados.add("PR");
        this.estados.add("PE");
        this.estados.add("PI");
        this.estados.add("RJ");
        this.estados.add("RN");
        this.estados.add("RS");
        this.estados.add("RO");
        this.estados.add("RR");
        this.estados.add("SC");
        this.estados.add("SP");
        this.estados.add("SE");
        this.estados.add("TO");
    }

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        return valor == null ? false : estados.contains(valor);
    }
    
}
