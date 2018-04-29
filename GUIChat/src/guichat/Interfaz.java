/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import guichat.Components.CButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author usuario
 */
public class Interfaz {
    
    public Interfaz(){
        
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
    public static void createBubble(VBox container, Boolean position, Boolean type, String message, String user){
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
        container.getChildren().add(main);
    }
    
    /**
     * Método para crear el boton del amigo
     * @param name String Nombre o apodo del amigo
     * @param id int Identificador en la base de datos del amigo
     */
    public static void createFriend(VBox friends, VBox mensajes , String name, int id){
        CButton user = new CButton(name);
        user.setIdElement(id);
        user.setNameElement(name);
        user.getStyleClass().add("chat-btn");
        user.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(event.getSource() == user){
                    mensajes.getChildren().clear();
                    System.out.println("Id del usuario: " + user.getIdElement());
                }
            }
        });
        friends.getChildren().add(user);
    }
}
