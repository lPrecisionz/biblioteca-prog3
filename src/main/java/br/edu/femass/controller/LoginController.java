package br.edu.femass.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.edu.femass.Auxiliares.JavaFx;
import br.edu.femass.dao.UsuarioDao;
import br.edu.femass.model.Usuario;
import jakarta.persistence.NoResultException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {
    JavaFx javafx = new JavaFx();
    UsuarioDao usuarioDao = new UsuarioDao(Usuario.class);
    
    @FXML
    private TextField txt_login;

    @FXML
    private PasswordField txt_senha;
    
    @FXML
    private void btn_entrar(ActionEvent event) {
        if(txt_login.getText().equals("adm") && txt_senha.getText().equals("adm")){
            javafx.criarTela("/fxml/bibliotecario.fxml","Bibliotecario");
            return;
        }

        try{
            Usuario usuario = usuarioDao.findByName(txt_login.getText());
            boolean usuarioAprovado = comparaSenha(usuario, txt_senha.getText());
            if(usuarioAprovado){
                javafx.criarTela("/fxml/leitor.fxml", "Leitor");
            }
            else{
                javafx.exibirErro("Não foi possível se conectar", "Senha incorreta!");
            }
        } catch(NoResultException e){
            javafx.exibirErro("Não foi possível se conectar", "Usuário incorreto!");
        }

    }

    private boolean comparaSenha(Usuario usuario, String senha){
        return (usuario.getSenha().equals(senha));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
}
