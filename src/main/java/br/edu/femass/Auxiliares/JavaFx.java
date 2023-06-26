package br.edu.femass.Auxiliares;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class JavaFx {
    public void criarTela(String fxml, String titulo){
        try{
        Parent root;
        root = FXMLLoader.load(getClass().getResource(fxml));
            
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
                
        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void exibirErro(String titulo, String mensagem){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensagem);
        alerta.show();
    }
}
