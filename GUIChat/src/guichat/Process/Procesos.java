/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat.Process;

import com.google.gson.Gson;

/**
 *
 * @author Wero
 */
public class Procesos {
    
    private Gson json;
    private RQAmigos amigos;
    private RQCuenta cuenta;
    private RQGrupos grupos;
    private RQMensajes mensajes;
    private RQUsuarios usuarios;
    
    public Procesos() {
        json = new Gson();
        amigos = new RQAmigos();
        cuenta = new RQCuenta();
        grupos = new RQGrupos();
        mensajes = new RQMensajes();
        usuarios = new RQUsuarios();
    }
    
    /* =======================
     * TODO Cuenta
     ======================= */
    public void cuentaLogin() {

    }
    
    public void cuentaRegister() {
        
    }
    
    public void cuentaLogout() {
        
    }
    
    /* =======================
     * TODO Amigos
     ======================= */
    public void amigosAdd() {
        
    }
    
    public void amigosDelete() {
        
    }
    
    public void amigosInvite() {
        
    }
    
    public void amigosUpdate() {
        
    }
    
    public void amigosGetAll() {
        
    }
    
    public void amigosGetConectados() {
        
    }
    
    public void amigosGetDesconectados() {
        
    }
    
    public void amigosGetPet() {
        
    }
    
    public void amigosGetUsuarios() {
        
    }
    
    /* =======================
     * TODO Grupos
     ======================= */
    public void gruposCreate() {
        
    }
    
    public void gruposInvite() {
        
    }
    
    public void gruposAdd() {
        
    }
    
    public void gruposDelete() {
        
    }
    
    public void gruposGetInfo() {
        
    }
    
    public void gruposGetPet() {
        
    }
    
    public void gruposGetAll() {
        
    }
    
    public void gruposUpdate() {
        
    }
    
    /* =======================
     * TODO Usuarios
     ======================= */
    public void usuariosGetAll() {
        
    }
    
    public void usuariosGetConectados() {
        
    }
    
    public void usuariosGetDesconectados() {
        
    }
    
    /* =======================
     * TODO Mensajes
     ======================= */
    public void mensajesSendPersonal() {
        
    }
    
    public void mensajesSendGrupo() {
        
    }
    
}
