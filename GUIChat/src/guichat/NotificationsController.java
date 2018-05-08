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
    private Hilo hilo;
        
    public void setHilo(Hilo hilo) {
        this.hilo = hilo;
    }

    public NotificationsController() {
        
    }
    
    
    @FXML
    public void handleCloseWindow(ActionEvent e){
        this.hilo.stopThread();
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
            ModifyGroupsController modify = loader.getController();
            modify.setHilo(this.hilo);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    
    @FXML
    public void handleMinimizeWindow(ActionEvent e){
        Stage stage = (Stage) minimizeWindowBtn.getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    public void goToMessages(ActionEvent e){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Stage stage = (Stage) messagesBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            HomeController home = loader.getController();
            home.setHilo(this.hilo);
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
            GroupsController groups = loader.getController();
            groups.setHilo(this.hilo);
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
            FriendsController friends = loader.getController();
            friends.setHilo(this.hilo);
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
            this.hilo.stopThread();
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
