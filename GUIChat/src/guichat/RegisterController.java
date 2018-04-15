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
 * FXML Controller class
 *
 * @author Wero
 */
public class RegisterController implements Initializable, IContentSetter {
    
    ScreenController screen;
    // Controles implementados en Interfaz
    @FXML
    private Button closeWindowBtn;
    private TextField txtNewuser, txtnewPassword;
    private Button signin;

    public RegisterController() {
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
    
    @FXML
    public void handleRegister(ActionEvent e){
        
        if(txtNewuser.getText().isEmpty() && txtnewPassword.getText().isEmpty()){
            
        }
        else{
            //modelo = txtNewuser.getText();
            //modelo = txtnewPassword.getText();
            screen.setScreen(GUIChat.screenHome);
        }
    }
    
    /**
     * Initializes the controller class.
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
