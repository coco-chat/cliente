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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
<<<<<<< HEAD
    @FXML private Button closeWindowBtn, minimizeWindowBtn, outBtn, groupBtn, notificationBtn;
=======
    @FXML private Button closeWindowBtn, minimizeWindowBtn, outBtn, groupBtn, deleteBtn, editBtn;
>>>>>>> af9eb3598680a33eebbd64cb67565f8c0b1f02a5
    @FXML private TextArea txtMessage;
    @FXML private VBox messagesVBox, groupsVBox, friendsVBox;
    @FXML private TextField txtCurrentContact;
    
    // Variables de control internas
    private Boolean type = false;
    private String username;
    private Boolean flagEdit = false;
    private Boolean typeEdit = true;
    private String contact;
    private String[] users = {
        "Arturo Carrillo",
        "Kevin Alan",
        "Vanya Martínez",
        "Jimena Zaragoza",
        "Juan Antonio",
        "Emiliano Moreno",
        "Eduardo Fuentes"
    };
    
    /**
     * Método para hacer pruebas en la pantalla
     */
    public void insertContent(){
        Boolean flag = false;
        for(String user : users){
            createBubble(flag, type, user, null);
            if(flag) flag = false;
            else flag = true;
        }
        int contador = 0;
        for(String user : users){
            createFriend(user, contador);
            contador++;
        }
        for(String user : users){
            createGroup(user, contador);
            contador++;
        }
    }
    
    /**
     * Método para crear el boton del grupo
     * @param name String Nombre del grupo
     * @param id int Identificador en la base de datos del grupo
     */
    public void createGroup(String name, int id){
        CButton group = new CButton(name);
        group.setIdElement(id);
        group.setNameElement(name);
        group.getStyleClass().add("chat-btn");
        group.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(event.getSource() == group){
                    messagesVBox.getChildren().clear();
                    System.out.println("Id del Grupo: " + group.getIdElement());
                    txtCurrentContact.setText(group.getNameElement());
                    contact = group.getNameElement();
                    type = true;
                    typeEdit = true;
                    editBtn.setDisable(false);
                    deleteBtn.setDisable(false);
                }
            }
        });
        groupsVBox.getChildren().add(group);
    }
    
    /**
     * Método para crear el boton del amigo
     * @param name String Nombre o apodo del amigo
     * @param id int Identificador en la base de datos del amigo
     */
    public void createFriend(String name, int id){
        CButton user = new CButton(name);
        user.setIdElement(id);
        user.setNameElement(name);
        user.getStyleClass().add("chat-btn");
        user.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(event.getSource() == user){
                    messagesVBox.getChildren().clear();
                    System.out.println("Id del usuario: " + user.getIdElement());
                    txtCurrentContact.setText(user.getNameElement());
                    type = false;
                    contact = user.getNameElement();
                    editBtn.setDisable(false);
                    deleteBtn.setDisable(false);
                    typeEdit = false;
                }
            }
        });
        friendsVBox.getChildren().add(user);
    }
    
    /**
     * Método para crear el cuadro de mensaje de salida
     * @param position Boolean Determinamos la posición del Mensaje
     * true => Izquierda
     * false => Derecha
     * @param type Boolean Determinamos si es para un grupo o amigo
     * true => Grupo
     * false => Amigo
     * @param message String Mensaje a insertar
     * @param user String Nombre de Usuario que envío el mensaje
     */
    public void createBubble(Boolean position, Boolean type, String message, String user){
        StackPane main = new StackPane();
        main.getStyleClass().add("bubble-container");
        VBox div = new VBox();
        if(position){
            main.getStyleClass().add("left");
            div.getStyleClass().add("align-left");
        }else{
            main.getStyleClass().add("right");
            div.getStyleClass().add("align-right");
        }
        Label content = new Label();
        content.setText(message);
        content.getStyleClass().add("bubble");
        div.getChildren().add(content);
        if(type){
            main.getStyleClass().add("group-message");
            Label by = new Label();
            by.setText(user);
            by.getStyleClass().add("by");
            div.getChildren().add(by);
        }
        main.getChildren().add(div);
        messagesVBox.getChildren().add(main);
    }
    
    @FXML
    public void editContact(ActionEvent e){
        if(!flagEdit){
            editBtn.setText("Aceptar");
            txtCurrentContact.setDisable(false);
            flagEdit = true;
            txtCurrentContact.requestFocus();
        }else{
            if(!txtCurrentContact.getText().equals(contact)){
                System.out.println("Nuevo nombre del elemento: " + txtCurrentContact.getText());
                contact = txtCurrentContact.getText();
            }
            editBtn.setText("Editar");
            txtCurrentContact.setDisable(true);
            flagEdit = false;
        }
    }
    
    @FXML
    public void deleteContact(ActionEvent e){
        System.out.println("Falta implementar funcionalidad del botón de eliminar");
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
            Stage stage = (Stage) groupBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            GroupsController group = loader.getController();
            group.setUsername(this.username);
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
            notifications.setUsername(this.username);
        }catch (IOException io){
            io.printStackTrace();
        }
        
    }
    
    @FXML
    public void sendMessage(ActionEvent e){
        createBubble(Boolean.FALSE, type, txtMessage.getText(), "werofuentes");
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
