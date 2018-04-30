/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import guichat.Components.CButton;
import guichat.Components.CCheckBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
    public static void createFriend(VBox friends, VBox mensajes, Boolean state, Boolean friend, String name, int id){
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
                    mensajes.getChildren().clear();
                    System.out.println("Id del Usuario: " + user.getIdElement());
                }
            }
        });
        Label con = new Label();
        con.getStyleClass().add("circle");
        if(state){
            con.getStyleClass().add("online");
        }else{
            con.getStyleClass().add("offline");
        }
        container.getChildren().add(user);
        container.getChildren().add(con);
        if(friend){
            Label f = new Label();
            f.getStyleClass().add("icon");
            container.getChildren().add(f);
        }
        friends.getChildren().add(container);
    }
    
    /**
     * Método para crear el boton del grupo
     * @param name String Nombre del grupo
     * @param id int Identificador en la base de datos del grupo
     */
    public static void createGroup(VBox groups, VBox mensajes, String name, int id){
        CButton group = new CButton(name);
        group.setIdElement(id);
        group.setNameElement(name);
        group.getStyleClass().add("chat-btn");
        group.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(event.getSource() == group){
                    mensajes.getChildren().clear();
                    System.out.println("Id del grupo: " + group.getIdElement());
                }
            }
        });
        groups.getChildren().add(group);
    }
    
    public static void createUser(VBox users, String name, int id){
        CCheckBox usuario = new CCheckBox(name);
        usuario.getStyleClass().add("check");
        usuario.setIdUser(id);
        users.getChildren().add(usuario);
    }
}
