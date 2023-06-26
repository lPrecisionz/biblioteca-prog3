package br.edu.femass.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Aluno extends Leitor {
    @Getter
    private String matricula;

    public Aluno(String nome, String email, String telefone,Usuario usuario, String matricula){
        super(nome, email, telefone, usuario);
        this.matricula = matricula;
    }
}
