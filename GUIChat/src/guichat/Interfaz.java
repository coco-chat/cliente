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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author usuario
 */
public class Interfaz {
    
    public static VBox mensajes;
    public static VBox friends;
    public static VBox groups;
    public static VBox lista;
    public static VBox agregar;
    public static VBox eliminar;
    public static VBox aceptarAmigos;
    public static VBox aceptarGrupos;
    public static Button editar;
    public static TextField current;
    public static int idElement;
    public static int type;
    public static String nombre;
    public static int amistad;
    
    public Interfaz(){
        
    }
    
    public static void createNotification(Boolean status, String name, int id){
        HBox container = new HBox();
        container.getStyleClass().add("friend-container");
        CButton request = new CButton();
        request.setIdElement(id);
        Label user = new Label();
        user.setText(name);
        user.getStyleClass().add("name");
        request.getStyleClass().add("btn-green");
        request.setText("Agregar");
        request.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                if(e.getSource() == request){
                    System.out.println("Id del Element: " + request.getIdElement());
                   if(status){
                       Procesos.PeticionAceptarAmigo(request.getIdElement());
                       deleteRequest(aceptarAmigos, request.getIdElement());
                   }else{
                       Procesos.PeticionAceptarGrupo(request.getIdElement());
                       deleteRequest(aceptarGrupos, request.getIdElement());
                   }
                }
            }
        });
        container.getChildren().add(user);
        container.getChildren().add(request);
        if(status){
            aceptarAmigos.getChildren().add(container);
        }else{
            aceptarGrupos.getChildren().add(container);
        }
    }
    
    public static void createRequest(Boolean status, String name, int id){
        HBox container = new HBox();
        container.getStyleClass().add("friend-container");
        CButton request = new CButton();
        request.setIdElement(id);
        Label user = new Label();
        user.setText(name);
        user.getStyleClass().add("name");
        if(status){
            request.getStyleClass().add("btn-green");
            request.setText("Agregar");
        }else{
            request.getStyleClass().add("btn-gray");
            request.setText("Eliminar");
        }
        request.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                if(e.getSource() == request){
                    System.out.println("Id del Element: " + request.getIdElement());
                   if(status){
                       Procesos.PeticionAgregarAmigo(request.getIdElement());
                       deleteRequest(agregar, request.getIdElement());
                   }else{
                       Procesos.PeticionEliminarAmigo(request.getIdElement());
                       deleteRequest(eliminar, request.getIdElement());
                   }
                }
            }
        });
        container.getChildren().add(user);
        container.getChildren().add(request);
        if(status){
            agregar.getChildren().add(container);
        }else{
            eliminar.getChildren().add(container);
        }
    }
    
    public static void deleteRequest(VBox container, int id){
        if(container instanceof VBox){
            for(Node hbox : ((VBox)container).getChildren()){
                if(hbox instanceof HBox){
                    for(Node cbutton : ((HBox)hbox).getChildren()){
                        if(cbutton instanceof CButton){
                            if(((CButton) cbutton).getIdElement() == id){
                                container.getChildren().remove(hbox);
                                return;
                            }
                        }
                    }
                }
            }
        }
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
    public static void createBubble(Boolean position, Boolean type, String message, String user){
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
        mensajes.getChildren().add(main);
    }
    
    /**
     * Método para crear el boton del amigo
     * @param name String Nombre o apodo del amigo
     * @param id int Identificador en la base de datos del amigo
     */
    public static void createFriend(Boolean state, Boolean friend, String name, int id){
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
                    idElement = user.getIdElement();
                    current.setText(user.getNameElement());
                    nombre = user.getNameElement();
                    type = 1;
                    editar.setDisable(false);
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
    public static void createGroup(String name, int id){
        HBox container = new HBox();
        container.getStyleClass().add("contact");
        CButton user = new CButton(name);
        user.setIdElement(id);
        user.setNameElement(name);
        user.getStyleClass().add("btn-group");
        user.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(event.getSource() == user){
                    mensajes.getChildren().clear();
                    System.out.println("Id del Usuario: " + user.getIdElement());
                    idElement = user.getIdElement();
                    current.setText(user.getNameElement());
                    nombre = user.getNameElement();
                    type = 2;
                    editar.setDisable(false);
                }
            }
        });
        groups.getChildren().add(container);
    }
    
    public static void createUser(String name, int id){
        CCheckBox usuario = new CCheckBox(name);
        usuario.getStyleClass().add("check");
        usuario.setIdUser(id);
        lista.getChildren().add(usuario);
    }
}