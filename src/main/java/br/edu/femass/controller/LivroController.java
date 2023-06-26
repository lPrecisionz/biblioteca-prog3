package br.edu.femass.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.edu.femass.Auxiliares.JavaFx;
import br.edu.femass.dao.Dao;
import br.edu.femass.model.Autor;
import br.edu.femass.model.Copia;
import br.edu.femass.model.Genero;
import br.edu.femass.model.Livro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LivroController implements Initializable{
    JavaFx javafx = new JavaFx();
    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_nome;
    @FXML
    private TextField txt_ano;
    @FXML
    private TextField txt_edicao;
    @FXML
    private TextField txt_copias;
    @FXML
    private ComboBox<Genero> comboBox_genero;
    @FXML
    private ComboBox<Autor> comboBox_autor;
    @FXML
    private ListView<Autor> listaAutores;
    @FXML
    private ListView<Livro> listaLivros;
    
    Dao<Genero> generoDao = new Dao<Genero>(Genero.class);
    Dao<Autor> autorDao = new Dao<Autor>(Autor.class);
    Dao<Livro> livroDao = new Dao<Livro>(Livro.class);
    
    @FXML
    private void btn_novoGenero(ActionEvent action){
        javafx.criarTela("/fxml/genero.fxml","Gênero");
    }

    @FXML
    private void btn_novoAutor(ActionEvent action){
        javafx.criarTela("/fxml/autor.fxml","Autor");
    }

    @FXML
    private void btn_excluir(ActionEvent action){
        if(txt_id.getId()==null){
            javafx.exibirErro("Erro ao excluir livro", "Nenhum livro selecionado");
            return;
        }
        Long livroId = Long.valueOf(txt_id.getText()); 
        livroDao.delete(livroId);
        preencheListaLivros();
    }

    @FXML
    private void btn_cadastrar(ActionEvent action){
        if(validaCampos()){
            List<Autor> autores = listaAutores.getItems();
            Genero genero = comboBox_genero.getSelectionModel().getSelectedItem();
            Livro livro = new Livro(
                txt_nome.getText(),
                Integer.parseInt(txt_ano.getText()),
                txt_edicao.getText(),
                genero,
                autores
            );
            livroDao.create(livro);
            clearText();
            preencheListaLivros();
        }
        else{
            javafx.exibirErro("DADOS INVÁLIDOS", "Não foi possível cadastrar o livro");
        }
        
    }

    @FXML
    private void btn_incluirCopias(ActionEvent action){
        if(txt_id.getText().length()==0){
            javafx.exibirErro("Erro ao incluir cópias", "Nenhum livro selecionado");
            return;
        }
        Livro livro = livroDao.findById(txt_id.getText());
        int numCopias = Integer.parseInt(txt_copias.getText());

        for(int i = 0; i < numCopias; i++){
            livro.addCopia(new Copia(livro));
        }
        livroDao.update(livro);
        txt_copias.setText(""); 
    }

    private boolean validaCampos() {
    if (txt_nome.getText().length()==0
            || txt_ano.getText().length()==0
            || txt_edicao.getText().length()==0
            || comboBox_genero.getValue() == null
            || listaAutores.getItems().isEmpty()){
        return false;
        }
    return true;
    }

    private void preencheComboGenero(){
        ObservableList<Genero> lista = FXCollections.observableArrayList(
            generoDao.findAll()
        );
        comboBox_genero.setItems(lista);
    }

    private void preencheComboAutor(){
        ObservableList<Autor> lista = FXCollections.observableArrayList(
            autorDao.findAll()
        );
        comboBox_autor.setItems(lista);
    }

    private void preencheListaLivros(){
        ObservableList<Livro> lista = FXCollections.observableArrayList(
            livroDao.findAll()
        );
        listaLivros.setItems(lista);
    }

    private void preencheListaAutores(){
        Autor autorSelecionado = comboBox_autor.getValue();
        if(autorSelecionado!= null && !listaAutores.getItems().contains(autorSelecionado)){
            listaAutores.getItems().add(autorSelecionado);
        }
    }
    
    private void clearText(){
        txt_nome.setText("");
        txt_ano.setText("");
        txt_edicao.setText("");
        listaAutores.getItems().clear();
    }

    @FXML
    private void click_lista(MouseEvent event){
        Livro livro = listaLivros.getSelectionModel().getSelectedItem();
        txt_id.setText(livro.getId().toString());
        txt_nome.setText(livro.getNome());
        txt_ano.setText(livro.getAno().toString());
        txt_edicao.setText(livro.getEdicao());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencheListaLivros();
        preencheComboAutor();
        preencheComboGenero();

        //listener - checa toda vez que a combo muda de valor
        comboBox_autor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            preencheListaAutores();
        });
    }
}
