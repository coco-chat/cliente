/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat.Modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin Alan Martinez Virgen 14300260 8B1
 */
public class MensajeGrupo implements Serializable{
    private Grupo grupo;
    private Usuario remitente;
    private String mensaje;
    private List<Usuario> integrantes;

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
    
    public Usuario getRemitente() {
        return remitente;
    }

    public void setRemitente(Usuario remitente) {
        this.remitente = remitente;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Usuario> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<Usuario> integrantes) {
        this.integrantes = integrantes;
    }
    
    public MensajeGrupo() {
        this.grupo = new Grupo();
        this.remitente = new Usuario();
        this.mensaje = " ";
        this.integrantes = new ArrayList<>();
    }
 
}
