/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import guichat.Components.CCheckBox;
import guichat.Modelos.NuevoGrupo;
import guichat.Modelos.PetGrupo;
import guichat.Modelos.Usuario;
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
import javafx.scene.layout.AnchorPane;
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
    @FXML private Button createGroupBtn, outBtn, messagesBtn;
    @FXML private TextField txtGroup;
    @FXML private TextField txtOwner;
    @FXML private VBox usersVBox;
    @FXML private AnchorPane headerBar;
    
    // Variables locales
    private String username;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) outBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
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
     * Método para insertar contenido de pruebas
     */
    public void insertContent(){
        int count = 1;
        for(String user : users) {
            Interfaz.createUser(usersVBox, user, count);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Procesos.lista = usersVBox;
        Procesos.ListaUsuarios();
    }    
    
}
