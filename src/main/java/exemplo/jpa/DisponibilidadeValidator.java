/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thayn
 */
public class DisponibilidadeValidator
        implements ConstraintValidator<ValidaDisponibilidade, String> {

    private List<String> disponibilidades;

    @Override
    public void initialize(ValidaDisponibilidade constraintAnnotation) {
        this.disponibilidades = new ArrayList<>();

        this.disponibilidades.add("manhã");
        this.disponibilidades.add("tarde");
        this.disponibilidades.add("noite");
        this.disponibilidades.add("integral");
        this.disponibilidades.add("manhã e tarde");
        this.disponibilidades.add("manhã e noite");
        this.disponibilidades.add("seg-sex");
        this.disponibilidades.add("segunda");
        this.disponibilidades.add("terça");
        this.disponibilidades.add("quarta");
        this.disponibilidades.add("quinta");
        this.disponibilidades.add("sexta");
        this.disponibilidades.add("sábado");
        this.disponibilidades.add("domingo");
    }

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {

        if (valor == null) {
            return false;
        }

        return disponibilidades.contains(valor.toLowerCase().trim());
    }
}
