/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import guichat.Components.CButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class FriendsController implements Initializable {

    // Controles implementados en Interfaz
    @FXML private Button closeWindowBtn, minimizeWindowBtn, outBtn, messagesBtn;
    @FXML private VBox addFriendsVBox, deleteFriendsVBox;
    
    private String[] users = {
        "Arturo Carrillo",
        "Kevin Alan",
        "Vanya Martínez",
        "Jimena Zaragoza",
        "Juan Antonio",
        "Emiliano Moreno",
        "Eduardo Fuentes"
    };
    
    
    public void insertContent(){
        int count = 1;
        Boolean flag = true;
        for(String user : users) {
            createRequest(flag, user, count);
            if(flag) flag = false;
            else flag = true;
            count++;
        }
    }
    
    public void createRequest(Boolean status, String name, int id){
        HBox container = new HBox();
        container.getStyleClass().add("friend-container");
        CButton request = new CButton();
        request.setIdElement(id);
        Label user = new Label();
        user.setText(name);
        user.getStyleClass().add("name");
        if(status){
            request.getStyleClass().add("btn-green");
            request.setText("Agregar");
        }else{
            request.getStyleClass().add("btn-gray");
            request.setText("Eliminar");
        }
        request.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                if(e.getSource() == request){
                    System.out.println("Id del Element: " + request.getIdElement());
                }
            }
        });
        container.getChildren().add(user);
        container.getChildren().add(request);
        if(status){
            addFriendsVBox.getChildren().add(container);
        }else{
            deleteFriendsVBox.getChildren().add(container);
        }
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
     * Método para cerrar sesión del Usuario
     * @param e 
     */
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        insertContent();
    }    
    
}
