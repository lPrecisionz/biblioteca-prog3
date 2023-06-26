package br.edu.femass.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.edu.femass.Auxiliares.JavaFx;
import br.edu.femass.dao.Dao;
import br.edu.femass.dao.EmprestimoDao;
import br.edu.femass.dao.LivroDao;
import br.edu.femass.model.Copia;
import br.edu.femass.model.Emprestimo;
import br.edu.femass.model.Leitor;
import br.edu.femass.model.Livro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

public class EmprestimoController implements Initializable{
    JavaFx javafx = new JavaFx();

    EmprestimoDao emprestimoDao = new EmprestimoDao(Emprestimo.class);
    LivroDao livroDao = new LivroDao(Livro.class);
    Dao<Leitor> leitorDao = new Dao<Leitor>(Leitor.class);
    Dao<Copia> copiaDao = new Dao<Copia>(Copia.class);

    @FXML
    private ComboBox<Livro> comboBox_livro;
    @FXML
    private ComboBox<Copia> comboBox_copia;
    @FXML
    private ComboBox<Leitor> comboBox_leitor;
    @FXML
    private DatePicker data_emprestimo;
    @FXML
    private ListView<Emprestimo> listaEmprestimos;
    @FXML
    private ComboBox<String> comboBox_tipoEmprestimo;

    @FXML  
    private void btn_emprestar(ActionEvent action){
        if(!validaCampos()){
            javafx.exibirErro("Erro ao cadastrar empréstimo","Preencha todos os campos");
            return;
        }
        List<Emprestimo> emprestimos = emprestimoDao.findByLeitor(comboBox_leitor.getValue().getNome());
        boolean limite = emprestimos.size() == 5;

        if(limite){
            javafx.exibirErro("Erro ao cadastrar empréstimo","O usuário já possui 5 empréstimos em andamento");
            return;
        }

        boolean podeEmprestar = comboBox_livro.getValue().podeEmprestar();
        boolean copiaDisponivel = comboBox_copia.getValue().isDisponivel();
        if(!podeEmprestar || !copiaDisponivel){
            javafx.exibirErro("Erro ao emprestar livro", "O livro não pode ser emprestado");
            return;
        }
        Copia copia = comboBox_copia.getValue();
        copia.setDisponivel(false); //não deu certo setar no construtor; fiz essa cachorrice
        Emprestimo emprestimo = new Emprestimo(
            copia,
            comboBox_leitor.getValue(),
            data_emprestimo.getValue()
        );
        copiaDao.update(comboBox_copia.getValue());
        emprestimoDao.create(emprestimo);
        preencherListaEmprestimo();
        preencherComboCopia();
        clearCampos();
    }
    
    private void preencherComboTipoEmprestimo(){
        ObservableList<String> lista = FXCollections.observableArrayList("Todos", "Em Andamento", "Concluídos");
        comboBox_tipoEmprestimo.setItems(lista);
    }

    private void preencherComboLivro(){
        ObservableList<Livro> lista = FXCollections.observableArrayList(
            livroDao.findLivrosWithCopias()
        );
        comboBox_livro.setItems(lista);
    }

    private void preencherComboLeitor(){
        ObservableList<Leitor> lista = FXCollections.observableArrayList(
            leitorDao.findAll()
        );
        comboBox_leitor.setItems(lista);
    }

    private void preencherComboCopia(){
        Livro livroSelecionado = comboBox_livro.getValue();
        if(livroSelecionado != null){
            ObservableList<Copia> lista = FXCollections.observableArrayList(
                livroSelecionado.getCopiasDisponiveis()
            );
            comboBox_copia.setItems(lista);
        }
    }

    private void preencheListaTodos(){
            ObservableList<Emprestimo> lista = FXCollections.observableArrayList(
            emprestimoDao.findAll()
        );
        listaEmprestimos.setItems(lista);
    }

    private void preencheListaOnGoing(){
        ObservableList<Emprestimo> lista = FXCollections.observableArrayList(
            emprestimoDao.findByOnGoing()
        );
        listaEmprestimos.setItems(lista);
    }

    private void preencheListaConcluded(){
            ObservableList<Emprestimo> lista = FXCollections.observableArrayList(
            emprestimoDao.findByConcluded()
        );
        listaEmprestimos.setItems(lista);
    }

    private void preencherListaEmprestimo(){
        String tipoEmprestimo = comboBox_tipoEmprestimo.getValue();
        if (tipoEmprestimo==null) return;

        switch(tipoEmprestimo){
            case "Todos":
                preencheListaTodos();
                break;
            case "Em Andamento":
                preencheListaOnGoing();
                break;
            case "Concluídos":
                preencheListaConcluded();
                break;
        }
    }

    private boolean validaCampos(){
        if(comboBox_livro.getValue()==null
        || comboBox_leitor.getValue()==null
        || comboBox_copia.getValue()==null
        || data_emprestimo.getValue()==null){
            return false;
        }
        return true;
    }

    private void clearCampos(){
        comboBox_livro.setValue(null);
        comboBox_copia.setValue(null);
        comboBox_leitor.setValue(null);
        data_emprestimo.setValue(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencherComboLivro();
        preencherComboLeitor();
        preencherComboTipoEmprestimo();

        //listeners
        comboBox_tipoEmprestimo.setOnAction(event -> {
            preencherListaEmprestimo();
        });

        comboBox_livro.setOnAction(event -> {
        preencherComboCopia();
        });
    }
    
}
