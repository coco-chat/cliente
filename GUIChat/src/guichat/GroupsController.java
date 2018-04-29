/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import java.io.IOException;
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
 * FXML Controller class
 *
 * @author Wero
 */
public class GroupsController implements Initializable {

    @FXML
    private Button closeWindowBtn;
    @FXML
    private Button minimizeWindowBtn;

    // Controles implementados en Interfaz
    @FXML private Button createGroupBtn, outBtn, messagesBtn;
    @FXML private ListView listUsers;
    @FXML
    private TextField txtGroup;
    @FXML
    private TextField txtOwner;
    
    // Variables locales
    private String username;
    @FXML
    private AnchorPane headerBar;
    
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
     * Método para pasar el nombre de usuario de una vista a otra
     * @param username String Nombre de Usuario
     */
    public void setUsername(String username){
        this.username = username;
        System.out.println(this.username);
        txtOwner.setText(this.username);
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
     * Métoda para crear grupo y agregar Usuarios
     * @param e 
     */
    @FXML
    public void createGroup(ActionEvent e){
        try {
            getUsers();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Stage stage = (Stage) createGroupBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            HomeController home = loader.getController();
            home.setUsername(this.username);
        }catch (IOException io){
            io.printStackTrace();
        }
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
    
    public void getUsers(){
        ObservableList users = listUsers.getSelectionModel().getSelectedItems();
        System.out.println("Dueño del Grupo " + txtGroup.getText() + ": " + txtOwner.getText());
        for(Object o : users)
            System.out.println("Usuario = " + o);
    }
    
    /**
     * Método para insertar contenido de pruebas
     */
    public void insertContent(){
        listUsers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listUsers.getItems().add("Kevin Alan");
        listUsers.getItems().add("Arturo Carrillo");
        listUsers.getItems().add("Juan Antonio");
        listUsers.getItems().add("Emiliano Moreno");
        listUsers.getItems().add("Gerardo Richaud");
        listUsers.getItems().add("Eduardo Fuentes");
        listUsers.getItems().add("Vanya Martínez");
        listUsers.getItems().add("Jimena Zaragoza");
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
