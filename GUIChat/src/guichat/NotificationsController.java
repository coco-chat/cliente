/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import guichat.Components.CButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author start
 */
public class NotificationsController implements Initializable{
    @FXML private Button closeWindowBtn, minimizeWindowBtn, messagesBtn, outBtn;
    @FXML private VBox notificationsVBox;
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

    public NotificationsController() {
    }
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insertContent();
    }
    
    @FXML
    public void handleCloseWindow(ActionEvent e){
        Stage stage = (Stage) closeWindowBtn.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Método para hacer pruebas en la pantalla
     */
    public void createNotification(String name, int id){
        CButton notification = new CButton(name);
        notification.setIdElement(id);
        notification.setNameElement(name);
        notification.getStyleClass().add("chat-btn");
        notification.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(event.getSource() == notification){
                    System.out.println("Id del usuario: " + notification.getIdElement());
                    //txtUser.setText(notification.getNameElement());
                }
            }
        });
        notificationsVBox.getChildren().add(notification);
    }
    
    public void insertContent(){
        int contador = 0;
        for(String user : users){
            createNotification(user, contador);
            contador++;
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
            HomeController home = loader.getController();
            home.setUsername(this.username);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    
    @FXML
    public void signOut(ActionEvent e){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) outBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    
    
    
    
    
    
}
