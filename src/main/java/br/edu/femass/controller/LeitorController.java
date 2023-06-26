package br.edu.femass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.edu.femass.Auxiliares.JavaFx;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class LeitorController implements Initializable{
    JavaFx javafx = new JavaFx();

    @FXML
    private void btn_consultarLivros(ActionEvent action){
        javafx.criarTela("/fxml/consulta.fxml","Consulta - livros");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
}
