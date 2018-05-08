/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import guichat.Modelos.Amigo;
import guichat.Modelos.Usuario;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import guichat.Modelos.Comunicacion;
import guichat.Modelos.Grupo;
import guichat.Modelos.InfoGrupo;
import guichat.Modelos.Integrante;
import guichat.Modelos.Mensaje;
import guichat.Modelos.MensajeGrupo;
import guichat.Modelos.NuevoGrupo;
import guichat.Modelos.PetGrupo;
import guichat.Process.Interfaz;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
/**
 *
 * @author usuario
 */
public class Procesos {
    
   public static String ip;
   public static int puerto;
   public static Socket soquet;
   
   public static Gson json = new Gson();
   
   public Procesos() { }
   
    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        Procesos.ip = ip;
    }

    public static int getPuerto() {
        return puerto;
    }

    public static void setPuerto(int puerto) {
        Procesos.puerto = puerto;
    }
    
   public static void CrearSocket(String ip, int puerto)
   {     
       try {
           soquet = new Socket(ip,puerto);
       } catch (IOException ex) {
           Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   public static double Login(String usuario, String contraseña, String servidor){
        Usuario user = new Usuario();
        Comunicacion modeloInput = new Comunicacion();
        Comunicacion modeloOutput = new Comunicacion();
        try {
            Procesos.CrearSocket(servidor, 4567);
            DataOutputStream dataOutput=new DataOutputStream(Procesos.soquet.getOutputStream());
            user.setUsername(usuario);
            user.setPassword(contraseña);
            modeloOutput.setTipo(Comunicacion.MTypes.RQCUENTA_LOGIN);
            modeloOutput.setContenido(user);
            dataOutput.writeUTF(json.toJson(modeloOutput));
            DataInputStream dataInput= new DataInputStream(Procesos.soquet.getInputStream());
            modeloInput= json.fromJson(dataInput.readUTF(), Comunicacion.class);
            if (modeloInput.getTipo()== Comunicacion.MTypes.ACK_LOGIN) {
                System.out.println(modeloInput.getContenido());
                if ((double)modeloInput.getContenido()==210.0) {
                    return (double)modeloInput.getContenido();
                }
            }
        } catch (IOException I) {
            I.getMessage();
            return 0;
        }
       return 0;
       
    }    
   
    public static double Register(String nick, String contraseña, String servidor){
        System.out.println("Hola");
        Usuario usuario = new Usuario();
        Comunicacion modeloRespuesta = new Comunicacion();
        Comunicacion modeloPeticion = new Comunicacion();
        
        try {
            System.out.println("Entre");
            Procesos.CrearSocket(servidor, 4567);
            DataOutputStream peticion = new DataOutputStream(Procesos.soquet.getOutputStream());
            usuario.setUsername(nick);
            usuario.setPassword(contraseña);
            modeloPeticion.setTipo(Comunicacion.MTypes.RQCUENTA_REG);
            modeloPeticion.setContenido(usuario);
            peticion.writeUTF(json.toJson(modeloPeticion));
            
            DataInputStream  respuesta = new DataInputStream(Procesos.soquet.getInputStream());
            modeloRespuesta= json.fromJson(respuesta.readUTF(), Comunicacion.class);
            
            if (modeloRespuesta.getTipo()== Comunicacion.MTypes.ACK) {
                System.out.println(modeloRespuesta.getContenido());
                if ((double)modeloRespuesta.getContenido()==220.0) {
                    return (double)modeloRespuesta.getContenido();
                }
            }
            
        } catch (IOException I) {
            I.getMessage();
            return 0;
        }
       return 0;
    }
    
    public static double CrearGrupo(NuevoGrupo grupo){
        Comunicacion modeloRespuesta = new Comunicacion();
        Comunicacion modeloPeticion = new Comunicacion();
        try {
            System.out.println("Entre");
            DataOutputStream peticion = new DataOutputStream(Procesos.soquet.getOutputStream());
           
            modeloPeticion.setTipo(Comunicacion.MTypes.RQGRUPOS_CREATE);
            modeloPeticion.setContenido(grupo);
            peticion.writeUTF(json.toJson(modeloPeticion));
            
            DataInputStream  respuesta = new DataInputStream(Procesos.soquet.getInputStream());
            modeloRespuesta= json.fromJson(respuesta.readUTF(), Comunicacion.class);
            
            if (modeloRespuesta.getTipo()== Comunicacion.MTypes.ACK) {
                System.out.println(modeloRespuesta.getContenido());
                if ((double)modeloRespuesta.getContenido()==270.0) {
                    return (double)modeloRespuesta.getContenido();
                }
            }
            
        } catch (IOException I) {
            I.getMessage();
            return 0;
        }
       return 0;
    }
    
    public static void MostrarGruposA () {
        DataOutputStream peticion = null;
        try {

            Comunicacion modeloOutput = new Comunicacion();
            Comunicacion modeloInput = new Comunicacion();
            modeloOutput.setTipo(Comunicacion.MTypes.RQGRUPOS_GETALL);
            peticion = new DataOutputStream(soquet.getOutputStream());
            String data = json.toJson(modeloOutput);
            peticion.writeUTF(data);

            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            data = RecibirConfirmacion.readUTF();
            modeloInput = json.fromJson(data, Comunicacion.class);
            Type type = new TypeToken<List<Grupo>>() {}.getType();
            String JsonList = json.toJson(modeloInput.getContenido());
            List<Grupo> grupos = json.fromJson(JsonList, type);
            for(Grupo grupo : grupos){
                Interfaz.createGroups(grupo.getNombre(), grupo.getId());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void AgregarUsuarioGrupo (int gr, int id) {
        DataOutputStream EnviarCadena = null;
        PetGrupo grupo = new PetGrupo();
        grupo.setGrupo(gr);
        grupo.setUsuario(id);
        try {

            Comunicacion peticionAmigo = new Comunicacion();
            peticionAmigo.setTipo(Comunicacion.MTypes.RQGRUPOS_INVITE);
            peticionAmigo.setContenido(grupo);
            EnviarCadena = new DataOutputStream(soquet.getOutputStream());
            EnviarCadena.writeUTF(json.toJson(peticionAmigo));
            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            RecibirConfirmacion.readUTF();
            
        } catch (IOException ex) { 
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public static void MostrarInfoGrupo (int id) {
        DataOutputStream peticion = null;
        Grupo grupo = new Grupo();
        grupo.setId(id);
        try {

            Comunicacion modeloOutput = new Comunicacion();
            Comunicacion modeloInput = new Comunicacion();
            modeloOutput.setContenido(grupo);
            modeloOutput.setTipo(Comunicacion.MTypes.RQGRUPOS_GETINFO);
            peticion = new DataOutputStream(soquet.getOutputStream());
            String data = json.toJson(modeloOutput);
            peticion.writeUTF(data);

            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            data = RecibirConfirmacion.readUTF();
            modeloInput = json.fromJson(data, Comunicacion.class);
            
            String JsonList = json.toJson(modeloInput.getContenido());
            InfoGrupo info = json.fromJson(JsonList, InfoGrupo.class);
            List<Usuario> miembros = info.getMiembros();
            for(Usuario miembro : miembros) {
                Interfaz.createIntegrant(miembro.getUsername(), miembro.getId());
            }
            List<Usuario> nomiembros = info.getNoMiembros();
            for(Usuario nomiembro : nomiembros) {
                Interfaz.createUsers(nomiembro.getUsername(), nomiembro.getId());
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void MostrarUsuariosDesconectados(){
        DataOutputStream peticion = null;
        try {

            Comunicacion modeloOutput = new Comunicacion();
            Comunicacion modeloInput = new Comunicacion();
            modeloOutput.setTipo(Comunicacion.MTypes.RQUSUARIOS_DESCONECTADOS);
            peticion = new DataOutputStream(soquet.getOutputStream());
            String data = json.toJson(modeloOutput);
            peticion.writeUTF(data);

            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            data = RecibirConfirmacion.readUTF();
            modeloInput = json.fromJson(data, Comunicacion.class);
            Type type = new TypeToken<List<Usuario>>() {}.getType();
            String JsonList = json.toJson(modeloInput.getContenido());
            List<Usuario> amigos = json.fromJson(JsonList, type);
            for(Usuario usuario : amigos){
                Interfaz.createFriend(false, false, usuario.getUsername(), usuario.getId());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void MostrarListaAgregar(){
        DataOutputStream peticion = null;
        try {

            Comunicacion modeloOutput = new Comunicacion();
            Comunicacion modeloInput = new Comunicacion();
            modeloOutput.setTipo(Comunicacion.MTypes.RQAMIGOS_GETUSUARIOS);
            peticion = new DataOutputStream(soquet.getOutputStream());
            String data = json.toJson(modeloOutput);
            peticion.writeUTF(data);

            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            data = RecibirConfirmacion.readUTF();
            modeloInput = json.fromJson(data, Comunicacion.class);
            Type type = new TypeToken<List<Usuario>>() {}.getType();
            String JsonList = json.toJson(modeloInput.getContenido());
            List<Usuario> amigos = json.fromJson(JsonList, type);
            for(Usuario usuario : amigos){
                Interfaz.createRequest(true, usuario.getUsername(), usuario.getId());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void MostrarListaEliminar(){
        DataOutputStream peticion = null;
        try {

            Comunicacion modeloOutput = new Comunicacion();
            Comunicacion modeloInput = new Comunicacion();
            modeloOutput.setTipo(Comunicacion.MTypes.RQAMIGOS_GETALL);
            peticion = new DataOutputStream(soquet.getOutputStream());
            String data = json.toJson(modeloOutput);
            peticion.writeUTF(data);

            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            data = RecibirConfirmacion.readUTF();
            modeloInput = json.fromJson(data, Comunicacion.class);
            Type type = new TypeToken<List<Amigo>>() {}.getType();
            String JsonList = json.toJson(modeloInput.getContenido());
            List<Amigo> amigos = json.fromJson(JsonList, type);
            for(Amigo amigo : amigos){
                if(amigo.getAmigo1() != -1){
                    Interfaz.createRequest(false, amigo.getApodo1(), amigo.getId());
                }else{
                    Interfaz.createRequest(false, amigo.getApodo2(), amigo.getId());
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static double ActualizarApodoAmigo(String apodo, int id){
        Amigo amigo = new Amigo();
        Comunicacion modeloRespuesta = new Comunicacion();
        Comunicacion modeloPeticion = new Comunicacion();
        
        try {
            DataOutputStream peticion = new DataOutputStream(Procesos.soquet.getOutputStream());
            modeloPeticion.setTipo(Comunicacion.MTypes.RQAMIGOS_UPDATE);
            amigo.setApodo1(apodo);
            amigo.setAmigo1(id);
            modeloPeticion.setContenido(amigo);
            peticion.writeUTF(json.toJson(modeloPeticion));
            
            DataInputStream  respuesta = new DataInputStream(Procesos.soquet.getInputStream());
            modeloRespuesta= json.fromJson(respuesta.readUTF(), Comunicacion.class);
            
            if (modeloRespuesta.getTipo()== Comunicacion.MTypes.ACK) {
                System.out.println(modeloRespuesta.getContenido());
                if ((double)modeloRespuesta.getContenido()==243.0) {
                    return (double)modeloRespuesta.getContenido();
                }
            }
            
        } catch (IOException I) {
            I.getMessage();
            return 0;
        }
       return 0;
    }
    
    public static void MostrarNotificacionesGrupos () {
        DataOutputStream peticion = null;
        try {

            Comunicacion modeloOutput = new Comunicacion();
            Comunicacion modeloInput = new Comunicacion();
            modeloOutput.setTipo(Comunicacion.MTypes.RQGRUPOS_GETPET);
            peticion = new DataOutputStream(soquet.getOutputStream());
            String data = json.toJson(modeloOutput);
            peticion.writeUTF(data);

            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            data = RecibirConfirmacion.readUTF();
            modeloInput = json.fromJson(data, Comunicacion.class);
            Type type = new TypeToken<List<Grupo>>() {}.getType();
            String JsonList = json.toJson(modeloInput.getContenido());
            List<Grupo> grupos = json.fromJson(JsonList, type);
            for(Grupo grupo : grupos){
                Interfaz.createNotification(false, grupo.getNombre() + " quiere que seas un integrante del grupo!", grupo.getId());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
        
    public static void MostrarNotificacionesUsuarios () {
        DataOutputStream peticion = null;
        try {

            Comunicacion modeloOutput = new Comunicacion();
            Comunicacion modeloInput = new Comunicacion();
            modeloOutput.setTipo(Comunicacion.MTypes.RQAMIGOS_GETPET);
            peticion = new DataOutputStream(soquet.getOutputStream());
            String data = json.toJson(modeloOutput);
            peticion.writeUTF(data);

            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            data = RecibirConfirmacion.readUTF();
            modeloInput = json.fromJson(data, Comunicacion.class);
            Type type = new TypeToken<List<Usuario>>() {}.getType();
            String JsonList = json.toJson(modeloInput.getContenido());
            List<Usuario> usuarios = json.fromJson(JsonList, type);
            for(Usuario usuario : usuarios){
                Interfaz.createNotification(true, usuario.getUsername() + " quiere ser tu amigo!", usuario.getId());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void MostrarUsuariosConectados(){
        DataOutputStream peticion = null;
        try {

            Comunicacion modeloOutput = new Comunicacion();
            Comunicacion modeloInput = new Comunicacion();
            modeloOutput.setTipo(Comunicacion.MTypes.RQUSUARIOS_CONECTADOS);
            peticion = new DataOutputStream(soquet.getOutputStream());
            String data = json.toJson(modeloOutput);
            peticion.writeUTF(data);

            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            data = RecibirConfirmacion.readUTF();
            modeloInput = json.fromJson(data, Comunicacion.class);
            Type type = new TypeToken<List<Usuario>>() {}.getType();
            String JsonList = json.toJson(modeloInput.getContenido());
            List<Usuario> amigos = json.fromJson(JsonList, type);
            for(Usuario usuario : amigos){
                Interfaz.createFriend(true, false, usuario.getUsername(), usuario.getId());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void MostrarGrupos() {
        DataOutputStream peticion = null;
        try {

            Comunicacion modeloOutput = new Comunicacion();
            Comunicacion modeloInput = new Comunicacion();
            modeloOutput.setTipo(Comunicacion.MTypes.RQGRUPOS_GETALL);
            peticion = new DataOutputStream(soquet.getOutputStream());
            String data = json.toJson(modeloOutput);
            peticion.writeUTF(data);

            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            data = RecibirConfirmacion.readUTF();
            modeloInput = json.fromJson(data, Comunicacion.class);
            Type type = new TypeToken<List<Grupo>>() {}.getType();
            String JsonList = json.toJson(modeloInput.getContenido());
            List<Grupo> grupos = json.fromJson(JsonList, type);
            for(Grupo grupo : grupos){
                Interfaz.createGroup(grupo.getNombre(), grupo.getId());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void MostrarAmigos(){
        DataOutputStream peticion = null;
        try {

            Comunicacion modeloOutput = new Comunicacion();
            Comunicacion modeloInput = new Comunicacion();
            modeloOutput.setTipo(Comunicacion.MTypes.RQAMIGOS_CONECTADOS);
            peticion = new DataOutputStream(soquet.getOutputStream());
            String data = json.toJson(modeloOutput);
            peticion.writeUTF(data);

            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            data = RecibirConfirmacion.readUTF();
            modeloInput = json.fromJson(data, Comunicacion.class);
            Type type = new TypeToken<List<Amigo>>() {}.getType();
            String JsonList = json.toJson(modeloInput.getContenido());
            List<Amigo> amigos = json.fromJson(JsonList, type);
            for(Amigo amigo : amigos){
                if(amigo.getAmigo1() != -1){
                    Interfaz.createFriend(true, true, amigo.getApodo1(), amigo.getAmigo1());
                }else if(amigo.getAmigo2() != -1){
                    Interfaz.createFriend(true, true, amigo.getApodo2(), amigo.getAmigo2());
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void MostrarAmigosDesconectados(){
        DataOutputStream peticion = null;
        try {

            Comunicacion modeloOutput = new Comunicacion();
            Comunicacion modeloInput = new Comunicacion();
            modeloOutput.setTipo(Comunicacion.MTypes.RQAMIGOS_DESCONECTADOS);
            peticion = new DataOutputStream(soquet.getOutputStream());
            String data = json.toJson(modeloOutput);
            peticion.writeUTF(data);

            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            data = RecibirConfirmacion.readUTF();
            modeloInput = json.fromJson(data, Comunicacion.class);
            Type type = new TypeToken<List<Amigo>>() {}.getType();
            String JsonList = json.toJson(modeloInput.getContenido());
            List<Amigo> amigos = json.fromJson(JsonList, type);
            for(Amigo amigo : amigos){
                if(amigo.getAmigo1() != -1){
                    Interfaz.createFriend(false, true, amigo.getApodo1(), amigo.getAmigo1());
                }else if(amigo.getAmigo2() != -1){
                    Interfaz.createFriend(false, true, amigo.getApodo2(), amigo.getAmigo2());
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void ListaUsuarios() {
        DataOutputStream peticion = null;
        try {

            Comunicacion modeloOutput = new Comunicacion();
            Comunicacion modeloInput = new Comunicacion();
            modeloOutput.setTipo(Comunicacion.MTypes.RQUSUARIOS_GETALL);
            peticion = new DataOutputStream(soquet.getOutputStream());
            String data = json.toJson(modeloOutput);
            peticion.writeUTF(data);

            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            data = RecibirConfirmacion.readUTF();
            modeloInput = json.fromJson(data, Comunicacion.class);
            Type type = new TypeToken<List<Usuario>>() {}.getType();
            String JsonList = json.toJson(modeloInput.getContenido());
            List<Usuario> usuarios = json.fromJson(JsonList, type);
            for(Usuario usuario : usuarios){
                Interfaz.createUser(usuario.getUsername(), usuario.getId());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void EnviarMensajes(String txtMessage, int id)
    {
        
        DataOutputStream EnviarCadena = null;
        Usuario origen = new Usuario();
        System.out.println("Enviando mensaje");
        try {

            Comunicacion modeloOutput = new Comunicacion();
            System.out.println(txtMessage);
            Mensaje mensaje_enviar= new Mensaje();
            Usuario usuario_destino = new Usuario();
            usuario_destino.setId(id);
            mensaje_enviar.setDestino(usuario_destino);
            mensaje_enviar.setOrigen(origen);
            mensaje_enviar.setContenido(txtMessage);
            modeloOutput.setTipo(Comunicacion.MTypes.RQMENSAJES_SENDPERSONAL);
            modeloOutput.setContenido(mensaje_enviar);
            EnviarCadena = new DataOutputStream(soquet.getOutputStream());
            EnviarCadena.writeUTF(json.toJson(modeloOutput));
            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            RecibirConfirmacion.readUTF();
            mensaje_enviar.setOrigen(new Usuario());
            GuardarMensajePersonal(mensaje_enviar);
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
    }
    
    public static void RecibirPeticiones()
    {
        Gson jayson= new Gson();
        Comunicacion modelo = new Comunicacion();
        try {
            DataInputStream dataInput= new DataInputStream(soquet.getInputStream());
            modelo= jayson.fromJson(dataInput.readUTF(), Comunicacion.class);
            mensajeria(modelo);
        } catch (IOException e) {
            e.getMessage();
}
    }
    public static void mensajeria(Comunicacion modelo)
    {
        
        Gson json= new Gson(); 
        String data = json.toJson(modelo.getContenido());
        switch(modelo.getTipo())
            {
                case SEND_MENSAJE:
                    MensajeRecibido(json.fromJson(data, Mensaje.class));
                    break;
                case SEND_MENSAJE_GRUPO:
                    MensajeGrupoRecibido(json.fromJson(data, MensajeGrupo.class));
                    break;
                case SEND_CONECTADOS:
                    
                    break;
                case SEND_DESCONECTADOS:
                    
                    break;
            }
    }
    
    public static void EnviarMensajeGrupo(String txtMessage, int IdGrupo)
    {
        DataOutputStream EnviarCadena = null;
        try {
            Comunicacion modeloOutput = new Comunicacion();
            System.out.println(txtMessage);
            MensajeGrupo mensaje_enviar= new MensajeGrupo();
            mensaje_enviar.getGrupo().setId(IdGrupo);
            mensaje_enviar.setMensaje(txtMessage);
            modeloOutput.setTipo(Comunicacion.MTypes.RQMENSAJES_SENDGRUPO);
            modeloOutput.setContenido(mensaje_enviar);
            EnviarCadena = new DataOutputStream(soquet.getOutputStream());
            EnviarCadena.writeUTF(json.toJson(modeloOutput));
            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            RecibirConfirmacion.readUTF();
            
            GuardarMensajeGrupo(mensaje_enviar);
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void CambiarNombreGrupo(String txtNombre, int IdGrupo)
    {
        DataOutputStream EnviarCadena = null;
        Usuario origen = new Usuario();
        origen.setId(1);
        System.out.println("Enviando m");
        try {
            Comunicacion modeloOutput = new Comunicacion();
            System.out.println(txtNombre);
            Grupo CambiarGrupo= new Grupo();
            CambiarGrupo.setId(IdGrupo);
            CambiarGrupo.setNombre(txtNombre);
            modeloOutput.setTipo(Comunicacion.MTypes.RQGRUPOS_CREATE);
            EnviarCadena = new DataOutputStream(soquet.getOutputStream());
            EnviarCadena.writeUTF(json.toJson(modeloOutput));
            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            RecibirConfirmacion.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void EliminarIntegrante(int IdMiembro, int IdGrupo)
    {
        DataOutputStream EnviarCadena = null;
        Integrante EliminarIntegrante= new Integrante();
        System.out.println("Eliminando");
        try {
            Comunicacion modeloOutput = new Comunicacion();
            
            EliminarIntegrante.setUsuario(IdMiembro);
            EliminarIntegrante.setGrupo(IdGrupo);
            modeloOutput.setTipo(Comunicacion.MTypes.RQGRUPOS_DELETE);
            modeloOutput.setContenido(EliminarIntegrante);;
            EnviarCadena = new DataOutputStream(soquet.getOutputStream());
            EnviarCadena.writeUTF(json.toJson(modeloOutput));
            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            RecibirConfirmacion.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    public static void MensajeRecibido(Mensaje mensaje)
    {
        MostrarMensajeAmigo(mensaje);
        mensaje.setDestino(new Usuario());
        GuardarMensajePersonal(mensaje);
    }
    
    public static void MensajeGrupoRecibido(MensajeGrupo mensaje_grupo)
    {
        if(Interfaz.type == 2){
            if(mensaje_grupo.getGrupo().getId() == Interfaz.idElement){
                Platform.runLater(() -> {
                    Interfaz.createBubble(Boolean.TRUE, Boolean.TRUE, mensaje_grupo.getMensaje(), mensaje_grupo.getRemitente().getUsername());
                });
            } else {
                System.out.println("Estas en otro grupo");
            }
        } else {
            System.out.println("Estas platicando con alguien en persona");
        }
        GuardarMensajeGrupo(mensaje_grupo);
    }
    
    public static void PeticionAceptarAmigo (int id) {
        DataOutputStream EnviarCadena = null;
        Usuario aceptar = new Usuario();
        aceptar.setId(id);
        System.out.println("Aceptar petición Amigo");
        System.out.println("Amigo: " + id);
        try {

            Comunicacion peticionAmigo = new Comunicacion();
            peticionAmigo.setTipo(Comunicacion.MTypes.RQAMIGOS_ADD);
            peticionAmigo.setContenido(aceptar);
            EnviarCadena = new DataOutputStream(soquet.getOutputStream());
            EnviarCadena.writeUTF(json.toJson(peticionAmigo));
            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            RecibirConfirmacion.readUTF();
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public static void PeticionAceptarGrupo (int id) {
        DataOutputStream EnviarCadena = null;
        Grupo grupo = new Grupo();
        grupo.setId(id);
        System.out.println("Enviando peticion Grupo");
        try {

            Comunicacion peticionAmigo = new Comunicacion();
            peticionAmigo.setTipo(Comunicacion.MTypes.RQGRUPOS_ADD);
            peticionAmigo.setContenido(grupo);
            EnviarCadena = new DataOutputStream(soquet.getOutputStream());
            EnviarCadena.writeUTF(json.toJson(peticionAmigo));
            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            RecibirConfirmacion.readUTF();
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void PeticionAgregarAmigo (int id) {
        DataOutputStream EnviarCadena = null;
        Usuario invitacion = new Usuario();
        invitacion.setId(id);
        try {

            Comunicacion peticionAmigo = new Comunicacion();
            peticionAmigo.setTipo(Comunicacion.MTypes.RQAMIGOS_INVITE);
            peticionAmigo.setContenido(invitacion);
            EnviarCadena = new DataOutputStream(soquet.getOutputStream());
            EnviarCadena.writeUTF(json.toJson(peticionAmigo));
            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            RecibirConfirmacion.readUTF();
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void PeticionEliminarAmigo (int id)
    {
        DataOutputStream EnviarCadena = null;
        Amigo invitacion = new Amigo();
        invitacion.setAmigo1(id);
        System.out.println("Enviando peticion Amigo");
        try {

            Comunicacion peticionAmigo = new Comunicacion();
            peticionAmigo.setTipo(Comunicacion.MTypes.RQAMIGOS_DELETE);
            peticionAmigo.setContenido(invitacion);
            
            EnviarCadena = new DataOutputStream(soquet.getOutputStream());
            EnviarCadena.writeUTF(json.toJson(peticionAmigo));
            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            RecibirConfirmacion.readUTF();
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void CerrarSesion () {
        DataOutputStream EnviarCadena = null;
        try {

            Comunicacion cerrar = new Comunicacion();
            cerrar.setTipo(Comunicacion.MTypes.RQ_LOGOUT);
            
            EnviarCadena = new DataOutputStream(soquet.getOutputStream());
            EnviarCadena.writeUTF(json.toJson(cerrar));
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void MostrarMensajeAmigo(Mensaje mensaje) {
        if(Interfaz.type == 1){
            if(mensaje.getOrigen().getId() == Interfaz.idElement){
                Platform.runLater(
                    () -> Interfaz.createBubble(Boolean.TRUE, Boolean.FALSE, mensaje.getContenido(), null)
                );
            } else {
                System.out.println("Te llego un mensaje");  
            }
        } else {
            System.out.println("Estas platicando en un grupo");
        }
    }
    
     private static boolean GuardarMensajePersonal (Mensaje mensaje) {
        Gson gson = new Gson(); 
        boolean status;        
        ArchivosController mensajesPersonalesFile = new ArchivosController (
            System.getProperty("user.dir") + "\\mensajesPersonales.json"
        );
        String cadena = gson.toJson(mensaje);
        status = mensajesPersonalesFile.writeFile(cadena);
        return status;
    }
    
    public static void showLastMessages (Usuario usuario) {
        List<Mensaje> mensajes = getLastMessages(usuario);
        for(Mensaje mensaje : mensajes) {
            if(mensaje.getOrigen().getId()==-1){
                Platform.runLater(() -> Interfaz.createBubble(Boolean.FALSE, Boolean.FALSE, mensaje.getContenido(), null));
            }else{
                Platform.runLater(() -> Interfaz.createBubble(Boolean.TRUE, Boolean.FALSE, mensaje.getContenido(), null));
            }
        }
    }
     
    public static List<Mensaje> getLastMessages(Usuario usuario){
        Gson gson = new Gson();
        ArchivosController archivosController = new ArchivosController(
                System.getProperty("user.dir") + "\\mensajesPersonales.json"
        );
        List<String> mensajesJson = archivosController.readFile();
        List<Mensaje> mensajesUsr = new ArrayList<>();
        List<Mensaje> mensajes = new ArrayList<>();
        List<Mensaje> result = new ArrayList<>();
        Mensaje mensajeAux;
        
        for(String cadena:mensajesJson){
            mensajeAux = gson.fromJson(cadena, Mensaje.class);
            mensajesUsr.add(mensajeAux);
        }
        
        for(Mensaje mensaje:mensajesUsr){
            if(mensaje.getDestino().getId()==usuario.getId()){
                mensaje.setOrigen(new Usuario());
                mensajes.add(mensaje);
            }
            if(mensaje.getOrigen().getId()==usuario.getId()){
                mensaje.setDestino(new Usuario());
                mensajes.add(mensaje);
            }
        }
        
        int size = mensajes.size();
        if(size>3){
            for (int i = size-4;i<size ; i++) {
                result.add(mensajes.get(i));
            }
        }else result = mensajes;
        return result;
    }
    
    private static boolean GuardarMensajeGrupo (MensajeGrupo smsGrupo) {        
        Gson gson = new Gson();        
                
        ArchivosController mensajesGruposFile = new ArchivosController (
            System.getProperty("user.dir") + "\\smsGrupos.json"
        );
        
        boolean wsms = mensajesGruposFile.writeFile(gson.toJson(smsGrupo));
        
        if (wsms) {
            return true;
        } else {
            return false;
        }        
        
        //Para leer verificar cual de cuál grupo es del que se quiere obtener 
        //los mensajes.
    }
    
    
    public static ArrayList<MensajeGrupo> GetAllSmsOneGroup (Grupo group) {
        
        Gson gson = new Gson();
        
        int group_Id = group.getId();
        
        ArchivosController mensajesGruposFile = new ArchivosController (
            System.getProperty("user.dir") + "\\smsGrupos.json"
        );
        
        List<String> allGroupsallSmsStr = mensajesGruposFile.readFile();
        List <MensajeGrupo> allGroupsallSmsObj = new ArrayList<>();
        
        //Se obtienen pasan las Str de mensajes de grupo y se transforman a 
        //MensajeGrupo.class
        for (String groupSmsStr: allGroupsallSmsStr) {
            MensajeGrupo savedGroupSms = new MensajeGrupo();
            savedGroupSms = gson.fromJson(groupSmsStr, MensajeGrupo.class);
            allGroupsallSmsObj.add(savedGroupSms);
        }
        
        ArrayList<MensajeGrupo> oneGroupallSmsObj = new ArrayList<>();
        
        //Se obtienen los mensajes un solo grupo
        for (MensajeGrupo groupSmsObj : allGroupsallSmsObj) {
            int auxGroupId = groupSmsObj.getGrupo().getId();
            if (auxGroupId == group_Id) {
                oneGroupallSmsObj.add(groupSmsObj);
            }
        }
        
        return oneGroupallSmsObj;
        
    }
}
