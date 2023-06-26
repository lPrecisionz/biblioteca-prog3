package br.edu.femass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.edu.femass.model.Autor;
import br.edu.femass.model.Genero;
import br.edu.femass.Auxiliares.JavaFx;
import br.edu.femass.dao.Dao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class GeneroController implements Initializable{
    JavaFx javafx = new JavaFx();

    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_genero;
    @FXML
    private ListView<Genero> listaGeneros;

    private Dao<Genero> generoDao = new Dao<Genero>(Genero.class);

    @FXML
    private void btn_adicionar(ActionEvent action){
        Genero genero = new Genero(txt_genero.getText().trim());
        generoDao.create(genero);
        preencheListaGeneros();
        clearText();
    }

    @FXML
    private void btn_excluir(ActionEvent action){
        if(txt_id.getId()==null){
            javafx.exibirErro("Erro ao excluir gênero", "Nenhum gênero selecionado");
            return;
        }
        Long generoId = Long.valueOf(txt_id.getText()); 
        generoDao.delete(generoId);
        preencheListaGeneros();
    }

    @FXML
    private void click_lista(MouseEvent event){
        Genero genero = listaGeneros.getSelectionModel().getSelectedItem();
        txt_id.setText(genero.getId().toString());
    }

    private void preencheListaGeneros(){
        ObservableList<Genero> lista = FXCollections.observableArrayList(
            generoDao.findAll()
        );
        listaGeneros.setItems(lista);
    }

    private void clearText(){
        txt_genero.setText("");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencheListaGeneros();
    }
    
}
