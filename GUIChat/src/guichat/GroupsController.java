/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import guichat.Process.Interfaz;
import guichat.Components.CCheckBox;
import guichat.Modelos.NuevoGrupo;
import guichat.Modelos.PetGrupo;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Wero
 */
public class GroupsController implements Initializable {

    // Controles implementados en Interfaz
    @FXML private Button closeWindowBtn;
    @FXML private Button minimizeWindowBtn;
    @FXML private Button createGroupBtn, outBtn, messagesBtn, friendsBtn, notificationBtn, modifyBtn;
    @FXML private TextField txtGroup;
    @FXML private TextField txtOwner;
    @FXML private VBox usersVBox;
    
    // Variables locales
    private String username;
    private Hilo hilo;
        
    public void setHilo(Hilo hilo) {
        this.hilo = hilo;
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
    
    @FXML
    public void goToModifyGroup(ActionEvent e){
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyGroups.fxml"));
            Stage stage = (Stage) modifyBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            ModifyGroupsController modify = loader.getController();
            modify.setHilo(this.hilo);
        }catch (IOException io){
            io.printStackTrace();
        }
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
        getUsers();        
    }
    
    /**
     * Método para cerrar sesión del Usuario
     * @param e 
     */
    @FXML
    public void signOut(ActionEvent e){
        try {
            Procesos.CerrarSesion();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) outBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            this.hilo.stopThread();
        }catch (IOException io){
            io.printStackTrace();
        }
    }
    
    public void getUsers(){
        List<PetGrupo> usuarios = new ArrayList<PetGrupo>();
        int cont = 0;
        if(usersVBox instanceof VBox){
            for(Node node : ((VBox)usersVBox).getChildren()){
                if(node instanceof CCheckBox){
                    if(((CCheckBox) node).isSelected()){
                        cont++;
                        PetGrupo usuario = new PetGrupo();
                        usuario.setUsuario(((CCheckBox) node).getIdUser());
                        usuarios.add(usuario);
                        System.out.println("Id del usuario: " + ((CCheckBox) node).getIdUser());
                    }
                }
            }
        }
        if(cont > 0){
            NuevoGrupo grupo = new NuevoGrupo();
            grupo.setIntegrantes(usuarios);
            grupo.getGrupo().setNombre(txtGroup.getText());
            if(Procesos.CrearGrupo(grupo) == 270.0){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
                    Stage stage = (Stage) outBtn.getScene().getWindow();
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);
                }catch (IOException io){
                    io.printStackTrace();
                }
            }
        }else{
            System.out.println("No tienes usuarios seleccionados.");
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Interfaz.lista = usersVBox;
        Procesos.ListaUsuarios();
    }    
    
}
