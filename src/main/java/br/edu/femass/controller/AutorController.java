package br.edu.femass.controller;

import br.edu.femass.Auxiliares.JavaFx;
import br.edu.femass.dao.Dao;
import br.edu.femass.model.Autor;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AutorController implements Initializable{
    JavaFx javafx = new JavaFx();
    
    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_nome;
    @FXML
    private TextField txt_sobrenome;
    @FXML
    private ListView<Autor> listaAutores;

    Dao<Autor> autorDao = new Dao<Autor>(Autor.class);

    @FXML
    private void btn_adicionar(ActionEvent action){
        Autor autor = new Autor(txt_nome.getText().toString(),
        txt_sobrenome.getText().toString());
        autorDao.create(autor);
        clearText();
        preencheListaAutores();
    }

    @FXML
    private void btn_excluir(ActionEvent action){
        if(txt_id.getId()==null){
            javafx.exibirErro("Erro ao excluir autor", "Nenhum autor selecionado");
            return;
        }
        Long autorId = Long.valueOf(txt_id.getText()); 
        autorDao.delete(autorId);
        preencheListaAutores();
    }

    @FXML
    private void click_lista(MouseEvent event){
        Autor autor = listaAutores.getSelectionModel().getSelectedItem();
        txt_id.setText(autor.getId().toString());
    }

    private void preencheListaAutores(){
        ObservableList<Autor> lista = FXCollections.observableArrayList(
            autorDao.findAll()
        );
        listaAutores.setItems(lista);
    }

    private void clearText(){
        txt_nome.setText("");
        txt_sobrenome.setText("");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencheListaAutores();
    }
}
