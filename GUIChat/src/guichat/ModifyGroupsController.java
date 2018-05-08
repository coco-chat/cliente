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
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Wero
 */
public class ModifyGroupsController implements Initializable {

    // Controles implementados en Interfaz
    @FXML private Button closeWindowBtn, minimizeWindowBtn, outBtn, groupBtn, notificationBtn, friendsBtn, messagesBtn;
    @FXML private VBox groupsVBox, intVBox, usersVBox;
    @FXML private Label txtNameGroup;
    
    // Variables Globales
    private Hilo hilo;
        
    public void setHilo(Hilo hilo) {
        this.hilo = hilo;
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
            this.hilo.stopThread();
            Procesos.CerrarSesion();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) outBtn.getScene().getWindow();
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
            GroupsController groups = loader.getController();
            groups.setHilo(this.hilo);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    
    @FXML
    public void goToNotifications(ActionEvent e){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Notifications.fxml"));
            Stage stage = (Stage) notificationBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            NotificationsController notifications = loader.getController();
            notifications.setHilo(this.hilo);
        }catch (IOException io){
            io.printStackTrace();
        }
        
    }
    
    /**
     * Método para poder cerrar la pestaña
     * @param e ?
     */
    @FXML
    public void handleCloseWindow(ActionEvent e){
        this.hilo.stopThread();
        Stage stage = (Stage) closeWindowBtn.getScene().getWindow();
        stage.close();
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Interfaz.nombreGrupo = txtNameGroup;
        Interfaz.grupos = groupsVBox;
        Interfaz.integrantes = intVBox;
        Interfaz.posiblesIntegrantes = usersVBox;
        Procesos.MostrarGruposA();
    }    
    
}
