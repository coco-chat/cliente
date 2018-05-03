/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import com.google.gson.Gson;
import guichat.Components.CButton;
import guichat.Modelos.Comunicacion;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Wero
 */
public class HomeController implements Initializable, Runnable {
    String ip;
    // Controles implementados en Interfaz
    @FXML private Button closeWindowBtn, minimizeWindowBtn, outBtn, groupBtn, deleteBtn, editBtn, notificationBtn, friendsBtn;
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
     * =========================================================================
     * Métodos Locales
     * =========================================================================
     */
    
    /**
     * Método para hacer pruebas en la pantalla
     */
    public void insertContent(){
        Boolean flag = false;
        for(String user : users){
            Interfaz.createBubble(messagesVBox, flag, type, user, null);
            if(flag) flag = false;
            else flag = true;
        }
        int contador = 0;
        for(String user : users){
            createFriend(flag, flag, user, contador);
            if(flag) flag = false;
            else flag = true;
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
     * @param state Boolean Determina si el usuario esta activo o no
     * true => online
     * false => offline
     * @param friend Boolean Determina si un usuario es amigo o no
     * true => Amigo
     * false => No es amigo
     * @param name String Nombre o apodo del amigo
     * @param id int Identificador en la base de datos del amigo
     */
    public void createFriend(Boolean state, Boolean friend, String name, int id){      
        HBox container = new HBox();
        container.getStyleClass().add("contact");
        CButton user = new CButton(name);
        user.setIdElement(id);
        user.setNameElement(name);
        user.getStyleClass().add("btn");
        user.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(event.getSource() == user){
                    messagesVBox.getChildren().clear();
                    System.out.println("Id del Usuario: " + user.getIdElement());
                    txtCurrentContact.setText(user.getNameElement());
                    contact = user.getNameElement();
                    type = false;
                    typeEdit = false;
                    editBtn.setDisable(false);
                    deleteBtn.setDisable(false);
                }
            }
        });
        Label con = new Label();
        con.getStyleClass().add("circle");
        if(state){
            con.getStyleClass().add("online");
        }else{
            con.getStyleClass().add("offline");
        }con.getStyleClass().add("circle");
        container.getChildren().add(user);
        container.getChildren().add(con);
        if(friend){
            Label f = new Label();
            f.getStyleClass().add("icon");
            container.getChildren().add(f);
        }
        friendsVBox.getChildren().add(container);
    }
    
    /**
     * Editar información de contacto
     * @param e 
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
    
    /**
     * =========================================================================
     * Métodos FXML
     * =========================================================================
     */
    
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
    public void editContact(ActionEvent e){
        if(!flagEdit){
            editBtn.setText("Aceptar");
            txtCurrentContact.setDisable(false);
            flagEdit = true;
            txtCurrentContact.requestFocus();
        }else{
            if(!txtCurrentContact.getText().equals(contact)){
                if(Procesos.ActualizarApodoAmigo(txtCurrentContact.getText()) == 243.0){
                    System.out.println("Campo actualizado correctamente");
                }else{
                    System.out.println("Hubo un problema con la actualizacion");
                }
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
    public void setip(String ip){
        this.ip = ip;
        System.out.println(this.ip);
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
        Interfaz.createBubble(messagesVBox, Boolean.FALSE, type, txtMessage.getText(), "werofuentes");
        Procesos.EnviarMensajes(txtMessage.getText(), Interfaz.idElement);
        
        System.out.println(Interfaz.idElement);
        System.out.println(Interfaz.type);
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
        Procesos.mensajes = messagesVBox;
        Procesos.friends = friendsVBox;
        Procesos.groups = groupsVBox;
        Thread hilo = new Thread(this);
        hilo.start();
        Procesos.MostrarAmigos();
        Procesos.MostrarAmigosDesconectados();
        Procesos.MostrarUsuariosConectados();
        Procesos.MostrarUsuariosDesconectados();
        //if(Procesos.ActualizarApodoAmigo("Pancho") == 243.0){
        //    System.out.println("El cambio se llevo a cabo correctamente");
        //}else{
        //    System.out.println("Hubo problemas con la actualización");
        //}
        Procesos.MostrarGrupos();
    }
    
    @Override
    public void run(){
        System.out.println("Corriendo");
        try {
            ServerSocket response = new ServerSocket(7654);
            while(true) {
                System.out.println("Entre al while");
                Gson json = new Gson();
                Socket peticion = response.accept();
                DataInputStream datos = new DataInputStream(peticion.getInputStream());
                String da = datos.readUTF();
                Comunicacion modelo = json.fromJson(da, Comunicacion.class);
                Procesos.mensajeria(modelo);
                peticion.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
