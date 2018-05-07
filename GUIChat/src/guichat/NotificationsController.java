/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import guichat.Process.Interfaz;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author start
 */
public class NotificationsController implements Initializable{
    @FXML private Button closeWindowBtn, minimizeWindowBtn, messagesBtn, outBtn, groupBtn, friendsBtn, modifyBtn;
    @FXML private VBox friendsNotificationsVBox, groupsNotificationsVBox;
    private String username;
    private String[] users = {
        "Arturo Carrillo te quiere agregar como amigo",
        "Kevin Alan te envío solicitud para el grupo Basura",
        "Vanya Martínez te quiere agregar como amigo",
        "Jimena Zaragoza",
        "Juan Antonio",
        "Emiliano Moreno",
        "Eduardo Fuentes"
    };
    private int currentRequest;

    public NotificationsController() {
        
    }
    
    
    @FXML
    public void handleCloseWindow(ActionEvent e){
        Stage stage = (Stage) closeWindowBtn.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void goToModifyGroup(ActionEvent e){
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyGroups.fxml"));
            Stage stage = (Stage) modifyBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    
    @FXML
    public void handleMinimizeWindow(ActionEvent e){
        Stage stage = (Stage) minimizeWindowBtn.getScene().getWindow();
        stage.setIconified(true);
    }

    public void setUsername(String username) {
        this.username = username;
        System.out.println(this.username);
    }
    
    @FXML
    public void goToMessages(ActionEvent e){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Stage stage = (Stage) messagesBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    
    @FXML
    public void goToCreateGroup(ActionEvent e){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Groups.fxml"));
            Stage stage = (Stage) groupBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    
    @FXML
    public void goToFriends(ActionEvent e){
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Friends.fxml"));
            Stage stage = (Stage) friendsBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    
    @FXML
    public void signOut(ActionEvent e){
        try {
            Procesos.CerrarSesion();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) outBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
        Interfaz.aceptarAmigos = friendsNotificationsVBox;
        Interfaz.aceptarGrupos = groupsNotificationsVBox;
        Procesos.MostrarNotificacionesUsuarios();
        Procesos.MostrarNotificacionesGrupos();
    }
    
}
