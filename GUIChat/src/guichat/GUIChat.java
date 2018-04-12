/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Wero
 */
public class GUIChat extends Application {
    
    /**
     * Aquí se agregan todas las pantallas de nuestra aplicación, se separan en
     * dos, el nombre de la pantalla (Identificador) y el nombre del archivo que 
     * corresponde a esa pantalla.
     */
    public static String screenLogin = "Login";
    public static String screenLoginFile = "Login.fxml";
    public static String screenRegister = "Register";
    public static String screenRegisterFile = "Register.fxml";
    public static String screenHome = "Home";
    public static String screenHomeFile = "Home.fxml";
    
    @Override
    public void start(Stage stage) throws Exception {
        
        ScreenController container = new ScreenController();
        container.loadScreen(GUIChat.screenHome, GUIChat.screenHomeFile);
        container.loadScreen(GUIChat.screenLogin, GUIChat.screenLoginFile);
        container.loadScreen(GUIChat.screenRegister, GUIChat.screenRegisterFile);
        
        container.setScreen(GUIChat.screenLogin);
        
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image("https://cdn1.iconfinder.com/data/icons/ui-5/502/speech-512.png"));
        stage.setTitle("Coco Chat");
        Group root = new Group();
        root.getChildren().addAll(container);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
