package br.edu.femass.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import br.edu.femass.Auxiliares.JavaFx;
import br.edu.femass.dao.Dao;
import br.edu.femass.dao.UsuarioDao;
import br.edu.femass.model.Aluno;
import br.edu.femass.model.Leitor;
import br.edu.femass.model.Livro;
import br.edu.femass.model.Professor;
import br.edu.femass.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class UsuarioController implements Initializable{
    JavaFx javafx = new JavaFx();

    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_nome;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_telefone;
    @FXML
    private TextField txt_infoLeitor;
    @FXML
    private TextField txt_usuario;
    @FXML
    private TextField txt_senha;
    @FXML
    private Label label_tipoLeitor;
    @FXML
    private ComboBox<String> comboBox_tipoLeitor;
    @FXML
    private ListView<Leitor> listaUsuarios;

    UsuarioDao usuarioDao = new UsuarioDao(Usuario.class);
    Dao<Leitor> leitorDao = new Dao<Leitor>(Leitor.class);
    Dao<Aluno> alunoDao = new Dao<Aluno>(Aluno.class);
    Dao<Professor> professorDao = new Dao<Professor>(Professor.class);

    @FXML
    private void btn_cadastrar(ActionEvent action){
        if(validaCampos()){
            List<Usuario> usuarioExistente = usuarioDao.findListByName(txt_usuario.getText());
            boolean existe = !usuarioExistente.isEmpty();
            if(existe){
                javafx.exibirErro("USUÁRIO EXISTENTE", "ESCOLHA OUTRO NOME DE USUÁRIO");
                return;
            }

            Usuario usuario = new Usuario(txt_usuario.getText(),
            txt_senha.getText());
            boolean isAluno = (comboBox_tipoLeitor.getValue().equals("Aluno"));
            
            if(isAluno){
                Aluno aluno = new Aluno(txt_nome.getText(),
                txt_email.getText(),
                txt_telefone.getText(),
                usuario,
                txt_infoLeitor.getText()
                );
                alunoDao.create(aluno);
            } else{
                Professor professor = new Professor(txt_nome.getText(),
                txt_email.getText(),
                txt_telefone.getText(),
                usuario,
                txt_infoLeitor.getText());
                professorDao.create(professor);
            }
            clearText(); 
            preencherListaUsuarios();

        } else{
        javafx.exibirErro("DADOS INVÁLIDOS", "Preencha todos os campos");
        }
    }

    @FXML
    private void btn_excluir(ActionEvent action){
        /*if(txt_id.getId()==null){
            javafx.exibirErro("Erro ao excluir usuario", "Nenhum usuário selecionado");
            return;
        }
        Long usuarioId = Long.parseLong(txt_id.getText());
        leitorDao.delete(usuarioId);*/
        System.out.println("Non implemented!");
    }

    @FXML
    private void click_lista(MouseEvent event){
        Leitor leitor = listaUsuarios.getSelectionModel().getSelectedItem();
        txt_id.setText(leitor.getId().toString());
    }

    private void preencherListaUsuarios(){
        ObservableList<Leitor> lista = FXCollections.observableArrayList(
            leitorDao.findAll()
        );
        listaUsuarios.setItems(lista);
    }

    private void clearText(){
        txt_nome.setText("");
        txt_email.setText("");
        txt_infoLeitor.setText("");
        txt_telefone.setText("");
        txt_usuario.setText("");
        txt_senha.setText("");
        label_tipoLeitor.setText("");
    }

    private boolean validaCampos(){
        if(txt_nome.getText().length() == 0
            || txt_email.getText().length() == 0
            || txt_telefone.getText().length() == 0
            || txt_usuario.getText().length() == 0
            || txt_senha.getText().length() == 0
            || comboBox_tipoLeitor.getValue() == null
            || txt_infoLeitor.getText().length() == 0){
                return false;
            }
        return true;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencherListaUsuarios();
        comboBox_tipoLeitor.getItems().addAll("Professor", "Aluno");
        //listener pra avaliar quando opção da combo é selecionada
        comboBox_tipoLeitor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Professor")) {
                label_tipoLeitor.setText("Formacao");
            } else if (newValue.equals("Aluno")) {
                label_tipoLeitor.setText("Matricula");
            }
        });
    }
}
