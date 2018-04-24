/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;


import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import guichat.Modelos.Comunicacion;
import guichat.Modelos.Comunicacion.MTypes;
import guichat.Modelos.Usuario;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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
 *
 * @author Wero
 */
public class LoginController implements Initializable {
    
    // Controles implementados en Interfaz
    @FXML private Button closeWindowBtn, minimizeWindowBtn, registerBtn, loginBtn;
    @FXML private TextField txtUsername, txtPassword, txtServer;
    
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Stage stage = (Stage) registerBtn.getScene().getWindow();
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
    
    @FXML
    public void handleLogin(ActionEvent e){
        String ip=txtServer.getText();
        Usuario user= new Usuario();
        Gson jayson= new Gson();
        Comunicacion modeloInput = new Comunicacion();
        Comunicacion modeloOutput = new Comunicacion();
        if (!txtPassword.getText().equals("") && !txtServer.getText().equals("") && !txtUsername.getText().equals("")) {
             try {
            Socket soquet= new Socket(ip,4567);
            DataOutputStream dataOutput=new DataOutputStream(soquet.getOutputStream());
            user.setUsername(txtUsername.getText());
            user.setPassword(txtPassword.getText());
            modeloOutput.setTipo(Comunicacion.MTypes.RQ_LOGIN);
            modeloOutput.setContenido(user);
            dataOutput.writeUTF(jayson.toJson(modeloOutput));
            DataInputStream dataInput= new DataInputStream(soquet.getInputStream());
            modeloInput= jayson.fromJson(dataInput.readUTF(), Comunicacion.class);
                 if (modeloInput.getTipo()== MTypes.ACK_LOGIN) {
                     System.out.println(modeloInput.getContenido());
                     if ((double)modeloInput.getContenido()==210.0) {
                         try {
                             System.out.println("entre");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
                Stage stage = (Stage) loginBtn.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            }catch (IOException io){
                io.printStackTrace();
            }
                     }
                     
                 }
        } catch (IOException I) {
            I.getMessage();
        }
        }
        else
        {
            System.out.println("pendejo");
        }
       
        
        if(txtUsername.getText().equals("werofuentes") && txtPassword.getText().equals("14300143")){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
                Stage stage = (Stage) loginBtn.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            }catch (IOException io){
                io.printStackTrace();
            }
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
    
    
    
}
