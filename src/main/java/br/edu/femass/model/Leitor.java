package br.edu.femass.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Leitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id; 
    protected String nome;
    protected String email;
    protected String telefone;
    @OneToOne(cascade = CascadeType.REMOVE)
    protected Usuario usuario;

    public Leitor(String nome, String email, String telefone, Usuario usuario){
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.usuario = usuario;
    }

    @Override
    public String toString() {
            return ""+nome+"; User: "+usuario.getLogin();
    }
}
