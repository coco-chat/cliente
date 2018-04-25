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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Wero
 */
public class HomeController implements Initializable {

    // Controles implementados en Interfaz
    @FXML private Button closeWindowBtn, minimizeWindowBtn, outBtn, groupBtn;
    @FXML private TextArea txtMessage;
    @FXML private ListView listFriends, listGroups;
    @FXML private VBox messagesVBox;
    
    // Variables de control internas
    private String username;
    private String[] users = {
        "Arturo Carrillo",
        "Kevin Alan",
        "Emiliano Moreno",
        "Juan Castillo",
        "Vanya Martínez",
        "Jimena Zaragoza",
    };
    
    /**
     * Conseguir elemento del formulario
     */
    public void insertContent(){
        listFriends.getItems().addAll("Kevin Alan", "Arturo Carrillo", "Juan Antonio", "Emiliano Moreno", "Gerardo", "Wero");
        listGroups.getItems().addAll("Régimen Perro", "8°B");
        Boolean flag = false;
        for(String user : users){
            createBubble(flag, user);
            if(flag) flag = false;
            else flag = true;
        }
    }
    
    @FXML
    public void getGroup(MouseEvent event) {
        System.out.println(listGroups.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void getUser(MouseEvent event) {
        System.out.println(listFriends.getSelectionModel().getSelectedItem());
    }
    
    /**
     * Método para crear el cuadro de mensaje de salida
     * @param position Boolean Determinamos la posición del Mensaje
     * true => Izquierda
     * false => Derecha
     * @param message String Mensaje a insertar
     */
    public void createBubble(Boolean position, String message){
        StackPane main = new StackPane();
        main.getStyleClass().add("bubble-container");
        if(position){
            main.getStyleClass().add("left");
        }else{
            main.getStyleClass().add("right");
        }
        Label content = new Label();
        content.setText(message);
        content.getStyleClass().add("bubble");
        main.getChildren().add(content);
        messagesVBox.getChildren().add(main);
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
     * Método para minimizar la pestaña
     * @param e 
     */
    @FXML
    public void handleMinimizeWindow(ActionEvent e){
        Stage stage = (Stage) minimizeWindowBtn.getScene().getWindow();
        stage.setIconified(true);
    }
    
    /**
     * Método para pasar el nombre de usuario de una vista a otra
     * @param username String Nombre de Usuario
     */
    public void setUsername(String username){
        this.username = username;
        System.out.println(this.username);
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
     * Método pare redirigir a la pestaña de Creación de Grupos
     * @param e 
     */
    @FXML
    public void goToCreateGroup(ActionEvent e){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Groups.fxml"));
            Stage stage = (Stage) outBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            GroupsController group = loader.getController();
            group.setUsername(this.username);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    
    @FXML
    public void sendMessage(ActionEvent e){
        createBubble(Boolean.TRUE, txtMessage.getText());
        txtMessage.setText("");
    }
    
    /**
     * Método para inicializar los componentes de la Interfaz
     * @param url ?
     * @param rb ?
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        insertContent();
    }   
    
}
