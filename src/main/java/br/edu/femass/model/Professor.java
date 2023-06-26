package br.edu.femass.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Professor extends Leitor{
    @Getter
    private String formacao;
    public Professor(String nome, String email, String telefone,Usuario usuario, String formacao){
        super(nome, email, telefone, usuario);
        this.formacao = formacao;
    }
}
