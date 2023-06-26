package br.edu.femass.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Emprestimo {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate data;
    private LocalDate dataPrevistaEntrega;
    private LocalDate dataEntrega;
    private boolean isGoing=true;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Copia copia;

    @ManyToOne
    private Leitor leitor;

    public Emprestimo(Copia copia, Leitor leitor, LocalDate data){
        this.copia = copia;
        this.leitor = leitor;
        this.data = data;
        if(leitor instanceof Professor){
            dataPrevistaEntrega = data.plusDays(30);
        } else{
            dataPrevistaEntrega = data.plusDays(5);
        }
        //copia.setDisponivel(false);
    }

    public void devolverLivro(LocalDate data){
        this.isGoing = false;
        this.copia.setDisponivel(true);
        this.dataEntrega = data;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataPrevistaEntrega.format(formatter);
        if(this.isGoing()){
            return ""+id+": "+copia.getLivro().getNome()+", cópia  "+copia.getId()+"; "+leitor.getNome()+"; Prazo: "+dataFormatada;
        } else{
            return ""+id+": "+copia.getLivro().getNome()+", cópia  "+copia.getId()+"; "+leitor.getNome()+"; status: Entregue";
        }
    }

    

}
