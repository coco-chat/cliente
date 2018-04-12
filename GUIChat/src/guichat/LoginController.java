/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Wero
 */
public class LoginController implements Initializable, IContentSetter {
    
    // Controlador para mostrar contenido
    ScreenController screen;
    
    // Controles implementados en Interfaz
    @FXML private Button closeWindowBtn;
    @FXML private TextField txtUsername, txtPassword;
    
    /**
     * Constructor de LoginController
     * 
     * Inicializa una nueva instancia de la clase LoginController
     */
    public LoginController(){
        
    }
    
    /**
     * Método para poder cerrar la pestaña
     * @param e ?
     */
    @FXML
    public void handleCloseWindow(ActionEvent e){
        Stage stage = (Stage) closeWindowBtn.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método para pasar a la Pantalla de Register
     * @param e 
     */
    @FXML
    public void goToRegister(ActionEvent e){
        screen.setScreen(GUIChat.screenRegister);
    }
    
    @FXML
    public void handleLogin(ActionEvent e){
        if(txtUsername.getText().equals("werofuentes") && txtPassword.getText().equals("14300143")){
            screen.setScreen(GUIChat.screenHome);
        }
    }
    
    /**
     * Método para inicializar los componentes de la Interfaz
     * @param url ?
     * @param rb ?
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /**
     * Método para obtener que pantalla es la que se encuentra en ejecución
     * @param screenPage
     */
    @Override
    public void setContentToScreen(ScreenController screenPage) {
        this.screen = screenPage;
    }
    
}
