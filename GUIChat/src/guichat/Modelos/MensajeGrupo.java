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
public class MensajeGrupo implements Serializable{
    private Grupo grupo;
    private Usuario usuario;
    private String contenido;
    
    public MensajeGrupo() {
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }   
}
