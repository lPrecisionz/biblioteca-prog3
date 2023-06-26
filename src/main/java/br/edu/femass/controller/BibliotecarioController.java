package br.edu.femass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.edu.femass.Auxiliares.JavaFx;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class BibliotecarioController implements Initializable {
    JavaFx javafx = new JavaFx();

    @FXML
    private void btn_cadastrarUsuario(ActionEvent action){
        javafx.criarTela("/fxml/usuario.fxml","Usuário");
    }

    @FXML
    private void btn_cadastrarLivro(ActionEvent action){
        javafx.criarTela("/fxml/livro.fxml", "Livro");
    }

    @FXML
    private void btn_cadastrarAutor(ActionEvent action){
        javafx.criarTela("/fxml/autor.fxml", "Autor");
    }

    @FXML
    private void btn_cadastrarGenero(ActionEvent action){
        javafx.criarTela("/fxml/genero.fxml", "Genero");
    }

    @FXML
    private void btn_devolverCopia(ActionEvent action){
        javafx.criarTela("/fxml/devolucao.fxml","Devolucao");
    }

    @FXML
    private void btn_emprestarCopia(ActionEvent action){
        javafx.criarTela("/fxml/emprestimo.fxml", "Empréstimo");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
}
