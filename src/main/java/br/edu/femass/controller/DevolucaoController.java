package br.edu.femass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.edu.femass.Auxiliares.JavaFx;
import br.edu.femass.dao.EmprestimoDao;
import br.edu.femass.model.Emprestimo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DevolucaoController implements Initializable{
    EmprestimoDao emprestimoDao = new EmprestimoDao(Emprestimo.class);
    JavaFx javafx = new JavaFx();

    @FXML
    private ComboBox<Emprestimo> comboBox_emprestimos;
    @FXML
    private TextField txt_livro;
    @FXML
    private TextField txt_copia;
    @FXML
    private TextField txt_leitor;
    @FXML
    private TextField txt_dataEntrega;
    @FXML
    private DatePicker dataEntrega;
    @FXML
    private ListView<Emprestimo> emprestimosConcluidos;

    @FXML
    private void btn_devolver(ActionEvent action){
        if(!validaCampos()){
        javafx.exibirErro("Erro ao devolver livro", "Selecione opções válidas");
        return;
        }
        Emprestimo emprestimoDevolvido = comboBox_emprestimos.getValue();
        emprestimoDevolvido.devolverLivro(dataEntrega.getValue());
        emprestimoDao.update(emprestimoDevolvido);
        preencherListaEmprestimo();
        clearText();
    }

    private void preencherComboEmprestimo(){
        ObservableList<Emprestimo> lista = FXCollections.observableArrayList(
            emprestimoDao.findByOnGoing());
            comboBox_emprestimos.setItems(lista);
    }

    private void preencherListaEmprestimo(){
        ObservableList<Emprestimo> lista = FXCollections.observableArrayList(
            emprestimoDao.findByConcluded());
            emprestimosConcluidos.setItems(lista);
    }

    private void preencheCampos(Emprestimo e){
        txt_livro.setText(e.getCopia().getLivro().getNome());
        txt_copia.setText(e.getCopia().getId().toString());
        txt_leitor.setText(e.getLeitor().getUsuario().getLogin());
        txt_dataEntrega.setText(e.getDataPrevistaEntrega().toString());
    }

    private void clearText(){
        comboBox_emprestimos.setValue(null);
        dataEntrega.setValue(null);
        txt_livro.setText("");
        txt_copia.setText("");
        txt_leitor.setText("");
        txt_dataEntrega.setText("");
        dataEntrega.setValue(null);
    }
    private boolean validaCampos(){
        if(dataEntrega.getValue()==null
        || comboBox_emprestimos.getValue()==null){
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencherComboEmprestimo();
        preencherListaEmprestimo();

        comboBox_emprestimos.setOnAction(event -> {
        Emprestimo selectedEmprestimo = comboBox_emprestimos.getValue();
        if (selectedEmprestimo != null) {
            preencheCampos(selectedEmprestimo);
        }
        });
    }
    
}
