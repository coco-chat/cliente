/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat.Modelos;

import java.io.Serializable;

/**
 *
 * @author Kevin Alan Martinez Virgen 14300260 8B1
 */
public class Modelo_Mensaje_Grupo implements Serializable{
    private Modelo_grupos grupo;
    private Modelo_usuarios usuario;
    private String contenido;
    
    public Modelo_Mensaje_Grupo() {
    }

    public Modelo_grupos getGrupo() {
        return grupo;
    }

    public void setGrupo(Modelo_grupos grupo) {
        this.grupo = grupo;
    }

    public Modelo_usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Modelo_usuarios usuario) {
        this.usuario = usuario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }   
}
