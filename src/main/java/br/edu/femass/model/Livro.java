package br.edu.femass.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome; 
    private Integer ano;
    private String edicao;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "livro")
    private List<Copia> copias = new ArrayList<>();
    @ManyToMany
    private List<Autor> autores = new ArrayList<>();
    @ManyToOne
    private Genero genero;

    public Livro(String nome, Integer ano, String edicao, Genero genero, List<Autor> autores){
        this.nome = nome;
        this.ano = ano;
        this.edicao = edicao;
        this.genero = genero;
        this.autores = autores;
    }

    public void addAutor(Autor autor){
        autores.add(autor);
    }

    public void removeAutor(int index){
        autores.remove(index);
    }

    public void addCopia(Copia copia){
        copias.add(copia);
    }

    public void removeCopia(int index){
        copias.remove(index);
    }

    public int getAmountCopiasDisponiveis(){
        int copiasDisponiveis = 0;
        for(int i = 0; i < copias.size(); i++){
            if(copias.get(i).isDisponivel()){
                copiasDisponiveis++;
            }
        }
        return copiasDisponiveis;
    }

    public List<Copia> getCopiasDisponiveis() {
        List<Copia> copiasDisponiveis = new ArrayList<>();
    
        for (Copia copia : copias) {
            if (copia.isDisponivel()) {
                copiasDisponiveis.add(copia);
            }   
        }
        return copiasDisponiveis;
    }


    public boolean podeEmprestar(){
        return getAmountCopiasDisponiveis()>1;
    }

    @Override
    public String toString() {
        return ""+nome+": "+genero;
    }
    
}
