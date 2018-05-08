/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Wero
 */
public class RegisterController implements Initializable {

    // Controles implementados en Interfaz
    @FXML private Button closeWindowBtn, minimizeWindowBtn, signinBtn, loginBtn;
    @FXML private TextField txtNewuser, txtnewPassword, txtServer;
    
    /**
     * Método para llevar a cabo el registro del usuario
     * @param e 
     */
    @FXML
    public void handleRegister(ActionEvent e){
        
        if(!txtNewuser.getText().isEmpty() && !txtnewPassword.getText().isEmpty() && !txtServer.getText().isEmpty()){
            double res = Procesos.Register(txtNewuser.getText(), txtnewPassword.getText(), txtServer.getText());
            System.out.println(res);
            if(res == 220.0){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
                    Stage stage = (Stage) signinBtn.getScene().getWindow();
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);
                    HomeController home = loader.getController();
                    Hilo hilo = new Hilo();
                    hilo.start();
                    home.setHilo(hilo);
                }catch (IOException io){
                    io.printStackTrace();
                }
            }
        }
        else{
            System.out.println("Error en el registro"); 
        }
    }
    
    /**
     * Método para pasar a la Pantalla de Login
     * @param e 
     */
    @FXML
    public void goToLogin(ActionEvent e){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    
     /**
     * Método para minimizar la pestaña
     * @param e 
     */
    @FXML
    public void handleMinimizeWindow(ActionEvent e){
        Stage stage = (Stage) minimizeWindowBtn.getScene().getWindow();
        stage.setIconified(true);
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }    
    
}
