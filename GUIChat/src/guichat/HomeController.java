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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Wero
 */
public class HomeController implements Initializable,Runnable {
    String ip;
    // Controles implementados en Interfaz
    @FXML private Button closeWindowBtn, minimizeWindowBtn, outBtn, groupBtn, deleteBtn, editBtn;
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
            Interfaz.createBubble(messagesVBox, flag, type, user, null);
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
     * Editar información de contacto
     * @param e 
     */
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
    
    public void sendMessage(ActionEvent e){
        Interfaz.createBubble(messagesVBox, Boolean.FALSE, type, txtMessage.getText(), "werofuentes");
        Procesos.EnviarMensajes(txtMessage.getText());
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
        //Procesos.MostrarAmigos();
        //Procesos.MostrarUsuariosConectados();
        //Procesos.MostrarUsuariosDesconectados();
        if(Procesos.ActualizarApodoAmigo("Pancho") == 243.0){
            System.out.println("El cambio se llevo a cabo correctamente");
        }else{
            System.out.println("Hubo problemas con la actualización");
        }
    }
    
    @Override
    public void run()
    {
        System.out.println("Corriendo");
        
        try {
            
            ServerSocket response = new ServerSocket(7654);
            
            System.out.println("Entre al try");
            
            while(true) {
                
                System.out.println("Entre al while");
                
                Gson json = new Gson();
                
                Socket peticion = response.accept();
                
                DataInputStream datos = new DataInputStream(peticion.getInputStream());
                
                String da = datos.readUTF();
                
                Comunicacion modelo = json.fromJson(da, Comunicacion.class);
                
                Procesos.mensajeria(modelo);
                
                peticion.close();
                
                response.close();
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
