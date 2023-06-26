package br.edu.femass.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Copia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Livro livro;
    private boolean isDisponivel;

    public Copia(Livro livro){
        this.livro = livro;
        this.isDisponivel = true;
    }

    public void setDisponivel(boolean f){
        this.isDisponivel = f;
    }

    @Override
    public String toString() {
        return ""+livro+": "+id;
    }

}
