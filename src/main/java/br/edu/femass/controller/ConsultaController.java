package br.edu.femass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.edu.femass.dao.LivroDao;
import br.edu.femass.model.Livro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class ConsultaController implements Initializable {
    LivroDao livroDao = new LivroDao(Livro.class);

    @FXML
    private ListView listaLivros;

    private void preencheListaLivros(){
        ObservableList<Livro> lista = FXCollections.observableArrayList(
            livroDao.findAll()
        );
        listaLivros.setItems(lista);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencheListaLivros();
    }
}
