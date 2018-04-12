/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import java.io.IOException;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 *
 * @author Wero
 */
public class ScreenController extends StackPane {
    
    /**
     * Esta variable contiene todas las vistas de la Aplicación
     * 
     * Requiere dos parámetros:
     *  - El Nombre de la pantalla (Identificador)
     *  - El Archivo correspondiente a ese Nombre
     */
    private HashMap<String, Node> screens = new HashMap<>();
    
    /**
     * Constructor
     * 
     * Inicializa una nueva instancia de la clase ScreenController
     */
    public ScreenController(){
        super();
    }
    
    /**
     * Método para agregar pantallas a la Aplicación
     * @param name String Nombre de la Pantalla (Identificador)
     * @param screen Node Archivo correspondiente a ese Nombre
     */
    public void addScreen(String name, Node screen){
        
        screens.put(name, screen);
        
    }
    
    /**
     * Método para conseguir una pantalla de screens
     * @param name String Nombre de la Pantalla (Identificador)
     * @return Node Retornamos la pantalla si fue encontrada
     */
    public Node getScreen(String name){
        
        return screens.get(name);
        
    }
    
    /**
     * Método para precargar todas las pantallas de la Aplicación
     * @param name String Nombre de la Pantalla (Identificador)
     * @param resource String Archivo correspondiente a ese Nombre
     * @return 
     */
    public Boolean loadScreen(String name, String resource){
        
        try {
            
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            IContentSetter myScreenController = ((IContentSetter) myLoader.getController());
            myScreenController.setContentToScreen(this);
            addScreen(name, loadScreen);
            return true;
            
        } catch (IOException e) {
            
            System.out.println("Error al cargar Pantalla: " + e.getMessage());
            return false;
            
        }
        
    }
    
    /**
     * Método para colocar nueva Pantalla y eliminar la antigua Pantalla
     * @param name String Nombre de la Pantalla (Identificador)
     * @return Boolean Retornamos true si todo salió bien o false si hubo error
     */
    public Boolean setScreen(final String name){
        if (screens.get(name) != null) {   
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {  
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(800), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        getChildren().remove(0);                    
                        getChildren().add(0, screens.get(name));    
                        Timeline fadeIn = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                new KeyFrame(new Duration(600), new KeyValue(opacity, 1.0)));
                        fadeIn.play();
                    }
                }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name));       
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(2500), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.println("La pantalla no ha sido cargada. \n");
            return false;
        }
    }
    
    /**
     * Método para borrar pantallas de screens
     * @param name String Nombre de la Pantalla (Identificador)
     * @return Boolean Retornamos true si todo salió bien o false si hubo error
     */
    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("La pantalla no existe.");
            return false;
        } else {
            return true;
        }
    }
    
}
