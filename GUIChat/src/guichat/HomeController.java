/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import com.google.gson.Gson;
import guichat.Modelos.Comunicacion;
import guichat.Modelos.Mensaje;
import guichat.Modelos.MensajeGrupo;
import guichat.Modelos.Amigo;
import guichat.Modelos.Grupo;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Wero
 */
public class HomeController implements Initializable,Runnable {
    String ip;
    // Controles implementados en Interfaz
    @FXML private Button closeWindowBtn, minimizeWindowBtn, outBtn;
    @FXML private TextArea txtMessage;
    @FXML private ListView listFriends, listGroups, listConversation;
    
    // Variables de control internas
    private String username;
    private Thread hilo;
    
    /**
     * Conseguir elemento del formulario
     */
    public void insertContent(){
        listFriends.getItems().addAll("Kevin Alan", "Arturo Carrillo", "Juan Antonio", "Emiliano Moreno", "Gerardo", "Wero");
        listGroups.getItems().addAll("Régimen Perro", "8°B");
        listConversation.getItems().addAll("Hola Putita", "Que onda perro", "Como va todo?");
    }
    
    @FXML
    public void getGroup(MouseEvent event) {
        System.out.println(listGroups.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void getUser(MouseEvent event) {
        System.out.println(listFriends.getSelectionModel().getSelectedItem());
    }
    @Override
    public void run()
    {
        esperar();
    }
    
    public void esperar()
    {
        Gson jayson= new Gson();
        Comunicacion modelo = new Comunicacion();
        try {
            Socket soquet= new Socket(ip,81);
            DataInputStream dataInput= new DataInputStream(soquet.getInputStream());
            modelo= jayson.fromJson(dataInput.readUTF(), Comunicacion.class);
        } catch (IOException e) {
            e.getMessage();
        }
          
            
        
    }
    public void mensajeria(Comunicacion modelo)
    {
        
          Gson jayson= new Gson(); 
        switch(modelo.getTipo())
            {
                case SEND_MENSAJE:
                    Mensaje_recivido(jayson.fromJson(modelo.getContenido().toString(), Mensaje.class));
                    break;
                case SEND_GRUPO:
                    Mensaje_Grupo_recivido(jayson.fromJson(modelo.getContenido().toString(), MensajeGrupo.class));
                    break;
                case SEND_CONECTADOS:
                    
                    break;
                case SEND_DESCONECTADOS:
                    
                    break;
            }
    }
    public void Mensaje_recivido(Mensaje mensaje)
    {
        
    }
    public void Mensaje_Grupo_recivido(MensajeGrupo mensaje_grupo)
    {
        
    }
    public void Lista_Conectados(Amigo amigos_conectados)
    {
        
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
        insertContent();
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
    
    @FXML
    public void sendMessage(ActionEvent e){
        System.out.println(txtMessage.getText());
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

        hilo = new Thread();
        hilo.start(); 

    }   
    
}
