/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import guichat.Process.Interfaz;
import com.google.gson.Gson;
import guichat.Modelos.Comunicacion;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Wero
 */
public class HomeController implements Initializable, Runnable {
    
    // Controles implementados en Interfaz
    @FXML private Button closeWindowBtn, minimizeWindowBtn, outBtn, groupBtn, editBtn, notificationBtn, friendsBtn, modifyBtn;
    @FXML private TextArea txtMessage;
    @FXML private VBox messagesVBox, groupsVBox, friendsVBox;
    @FXML private TextField txtCurrentContact;
    @FXML private ScrollPane scrollMain;
    
    // Variables de control internas
    private Boolean type = false;
    private String username;
    private Boolean flagEdit = false;
    private Boolean typeEdit = true;
    private String contact;
    private ServerSocket response;
    private volatile Boolean flagThread = true;
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
     * Métodos FXML
     * =========================================================================
     */
    
    @FXML
    public void goToFriends(ActionEvent e){
         try {
            stopThread();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Friends.fxml"));
            Stage stage = (Stage) friendsBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    
    @FXML
    public void goToModifyGroup(ActionEvent e){
         try {
            stopThread();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyGroups.fxml"));
            Stage stage = (Stage) modifyBtn.getScene().getWindow();
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
            if(!txtCurrentContact.getText().equals(Interfaz.nombre)){
                if(Procesos.ActualizarApodoAmigo(txtCurrentContact.getText(), Interfaz.idElement) == 243.0){
                    System.out.println("Campo actualizado correctamente");
                }else{
                    System.out.println("Hubo un problema con la actualizacion");
                }
                System.out.println("Nuevo nombre del elemento: " + txtCurrentContact.getText());
                Interfaz.nombre = txtCurrentContact.getText();
            }
            editBtn.setText("Editar");
            txtCurrentContact.setDisable(true);
            flagEdit = false;
        }
    }
    
    /**
     * Método para poder cerrar la pestaña
     * @param e ?
     */
    @FXML
    public void handleCloseWindow(ActionEvent e){
        stopThread();
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
    
    public void enter(){
        txtMessage.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent keyEvent){
                if(keyEvent.getCode() == KeyCode.ENTER){
                    keyEvent.consume();
                    if(Interfaz.type == 1){
                        if(!txtMessage.getText().isEmpty()){sendToFriend();};
                    }else if(Interfaz.type == 2){
                        if(!txtMessage.getText().isEmpty()){sendToGroup();};
                    }
                }
            }
        });
        messagesVBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollMain.setVvalue( 1.0d );
            }
        });
    }
    
    public void sendToFriend(){
        Interfaz.createBubble(Boolean.FALSE, type, txtMessage.getText(), null);
        Procesos.EnviarMensajes(txtMessage.getText(), Interfaz.idElement);
        txtMessage.setText("");
    }
    
    public void sendToGroup() {
        Interfaz.createBubble(Boolean.FALSE, type, txtMessage.getText(), null);
        Procesos.EnviarMensajeGrupo(txtMessage.getText(), Interfaz.idElement);
        txtMessage.setText("");
    }
    
    /**
     * Método para cerrar sesión del Usuario
     * @param e 
     */
    @FXML
    public void signOut(ActionEvent e){
        try {
            Procesos.CerrarSesion();
            stopThread();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) outBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    
    /**s
     * Método pare redirigir a la pestaña de Creación de Grupos
     * @param e 
     */
    @FXML
    public void goToCreateGroup(ActionEvent e){
        try {
            stopThread();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Groups.fxml"));
            Stage stage = (Stage) groupBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    
    @FXML
    public void goToNotifications(ActionEvent e){
        try {
            stopThread();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Notifications.fxml"));
            Stage stage = (Stage) notificationBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
        
    }
    
    @FXML
    public void sendMessage(ActionEvent e){
        if(Interfaz.type == 1){
            if(!txtMessage.getText().isEmpty()){sendToFriend();};
        }else if(Interfaz.type == 2){
            if(!txtMessage.getText().isEmpty()){sendToGroup();};
        }
    }
    
    /**
     * Método para inicializar los componentes de la Interfaz
     * @param url ?
     * @param rb ?
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        enter();
        Interfaz.mensajes = messagesVBox;
        Interfaz.friends = friendsVBox;
        Interfaz.groups = groupsVBox;
        Interfaz.current = txtCurrentContact;
        Interfaz.editar = editBtn;
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
            response = new ServerSocket(7654);
            while(this.flagThread) {
                System.out.println("Entre al while");
                Gson json = new Gson();
                Socket peticion = response.accept();
                DataInputStream datos = new DataInputStream(peticion.getInputStream());
                String da = datos.readUTF();
                Comunicacion modelo = json.fromJson(da, Comunicacion.class);
                Procesos.mensajeria(modelo);
                peticion.close();
            }
            System.out.println("Sali");
        } catch (IOException ex) {
            System.out.println("Hilo Terminado");
        }
    }
    
    public void stopThread() {
        System.out.println("Entre para detener la ");
        this.flagThread = false;
        try {
            response.close();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
