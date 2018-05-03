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
import guichat.Modelos.Integrante;
import guichat.Modelos.Mensaje;
import guichat.Modelos.MensajeGrupo;
import guichat.Modelos.NuevoGrupo;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
/**
 *
 * @author usuario
 */
public class Procesos {
    
   public static String ip;
   public static int puerto;
   public static Socket soquet;
   public static VBox mensajes;
   public static VBox friends;
   public static VBox groups;
   public static VBox lista;
   
   public static Gson json = new Gson();
   
   public Procesos()
   {
       
   }
   
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
           soquet= new Socket(ip,puerto);
       } catch (IOException ex) {
           Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   public static double Login(String usuario, String contraseña, String servidor){
        String ip= servidor;
        Usuario user = new Usuario();
        Comunicacion modeloInput = new Comunicacion();
        Comunicacion modeloOutput = new Comunicacion();
        try {
            Procesos.CrearSocket(ip, 4567);
            DataOutputStream dataOutput=new DataOutputStream(Procesos.soquet.getOutputStream());
            user.setUsername(usuario);
            user.setPassword(contraseña);
            modeloOutput.setTipo(Comunicacion.MTypes.RQ_LOGIN);
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
            Procesos.CrearSocket("192.168.84.215", 4567);
            DataOutputStream peticion = new DataOutputStream(Procesos.soquet.getOutputStream());
            usuario.setUsername(nick);
            usuario.setPassword(contraseña);
            modeloPeticion.setTipo(Comunicacion.MTypes.RQ_REG);
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
           
            modeloPeticion.setTipo(Comunicacion.MTypes.RQ_CGRUPO);
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
    
    public static void MostrarUsuariosDesconectados(){
        DataOutputStream peticion = null;
        try {

            Comunicacion modeloOutput = new Comunicacion();
            Comunicacion modeloInput = new Comunicacion();
            modeloOutput.setTipo(Comunicacion.MTypes.RQ_DESCONECTADOS);
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
                Interfaz.createFriend(friends, mensajes,true, true,  usuario.getUsername(), usuario.getId());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static double ActualizarApodoAmigo(String apodo){
        Amigo amigo = new Amigo();
        Comunicacion modeloRespuesta = new Comunicacion();
        Comunicacion modeloPeticion = new Comunicacion();
        
        try {
            DataOutputStream peticion = new DataOutputStream(Procesos.soquet.getOutputStream());
            modeloPeticion.setTipo(Comunicacion.MTypes.RQ_APODO);
            amigo.setApodo2(apodo);
            amigo.setAmigo2(3);
            amigo.setId(1);
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
        
    public static void MostrarUsuariosConectados(){
        DataOutputStream peticion = null;
        try {

            Comunicacion modeloOutput = new Comunicacion();
            Comunicacion modeloInput = new Comunicacion();
            modeloOutput.setTipo(Comunicacion.MTypes.RQ_CONECTADOS);
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
                Interfaz.createFriend(friends, mensajes, true, true, usuario.getUsername(), usuario.getId());
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
            modeloOutput.setTipo(Comunicacion.MTypes.RQ_GRUPOS);
            peticion = new DataOutputStream(soquet.getOutputStream());
            String data = json.toJson(modeloOutput);
            peticion.writeUTF(data);

            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            data = RecibirConfirmacion.readUTF();
            modeloInput = json.fromJson(data, Comunicacion.class);
            Type type = new TypeToken<List<Grupo>>() {}.getType();
            String JsonList = json.toJson(modeloInput.getContenido());
            List<Grupo> grupos = json.fromJson(JsonList, type);
            for(Grupo grupo : grupos)
                Interfaz.createGroup(groups, mensajes, grupo.getNombre(), grupo.getId());
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void MostrarAmigos(){
        DataOutputStream peticion = null;
        try {

            Comunicacion modeloOutput = new Comunicacion();
            Comunicacion modeloInput = new Comunicacion();
            modeloOutput.setTipo(Comunicacion.MTypes.RQ_AMIGOSCON);
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
                    Interfaz.createFriend(friends, mensajes, true, true, amigo.getApodo1(), amigo.getAmigo1());
                }else if(amigo.getAmigo2() != -1){
                    Interfaz.createFriend(friends, mensajes, true, true, amigo.getApodo2(), amigo.getAmigo2());
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
            modeloOutput.setTipo(Comunicacion.MTypes.RQ_AMIGOSDES);
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
                    Interfaz.createFriend(friends, mensajes, true, true, amigo.getApodo1(), amigo.getAmigo1());
                }else if(amigo.getAmigo2() != -1){
                    Interfaz.createFriend(friends, mensajes, true, true, amigo.getApodo2(), amigo.getAmigo2());
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
            modeloOutput.setTipo(Comunicacion.MTypes.RQ_LUSUARIOS);
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
                Interfaz.createUser(lista, usuario.getUsername(), usuario.getId());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void EnviarMensajes(String txtMessage, int id)
    {
        
        DataOutputStream EnviarCadena = null;
        Usuario origen = new Usuario();
        origen.setId(1);
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
            modeloOutput.setTipo(Comunicacion.MTypes.RQ_MENSAJE);
            modeloOutput.setContenido(mensaje_enviar);
            EnviarCadena = new DataOutputStream(soquet.getOutputStream());
            EnviarCadena.writeUTF(json.toJson(modeloOutput));

            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            RecibirConfirmacion.readUTF();
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
                case SEND_GRUPO:
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
        Usuario origen = new Usuario();
        origen.setId(1);
        System.out.println("Enviando mensaje");
        try {
            Comunicacion modeloOutput = new Comunicacion();
            System.out.println(txtMessage);
            MensajeGrupo mensaje_enviar= new MensajeGrupo();
            mensaje_enviar.getGrupo().setId(IdGrupo);
            mensaje_enviar.setContenido(txtMessage);
            modeloOutput.setTipo(Comunicacion.MTypes.RQ_MENSAJE_GRUPO);
            modeloOutput.setContenido(mensaje_enviar);
            EnviarCadena = new DataOutputStream(soquet.getOutputStream());
            EnviarCadena.writeUTF(json.toJson(modeloOutput));
            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            RecibirConfirmacion.readUTF();
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
            modeloOutput.setTipo(Comunicacion.MTypes.RQ_CGRUPO);
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
        Usuario origen = new Usuario();
        origen.setId(1);
        System.out.println("Eliminando");
        try {
            Comunicacion modeloOutput = new Comunicacion();
            System.out.println(IdMiembro);
            Integrante EliminarIntegrante= new Integrante();
            EliminarIntegrante.setId(IdMiembro);
            EliminarIntegrante.setGrupo(IdGrupo);
            modeloOutput.setTipo(Comunicacion.MTypes.RQ_DMIEMBRO);
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
        
    }
    public static void MensajeGrupoRecibido(MensajeGrupo mensaje_grupo)
    {
        
    }
    
    public static void EnviarPeticionAmigo ()
    {
        DataOutputStream EnviarCadena = null;
        Usuario amigoSolicitado = new Usuario();
        amigoSolicitado.setId(1);
        System.out.println("Enviando peticion Amigo");
        try {

            Comunicacion peticionAmigo = new Comunicacion();
            peticionAmigo.setTipo(Comunicacion.MTypes.RQ_NAMIGO);
            peticionAmigo.setContenido(amigoSolicitado);
            
            EnviarCadena = new DataOutputStream(soquet.getOutputStream());
            EnviarCadena.writeUTF(json.toJson(peticionAmigo));
            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            RecibirConfirmacion.readUTF();
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
     public static void PeticionEliminarAmigo ()
    {
        DataOutputStream EnviarCadena = null;
        Usuario bannedFriend = new Usuario();
        bannedFriend.setId(1);
        System.out.println("Enviando peticion Amigo");
        try {

            Comunicacion peticionAmigo = new Comunicacion();
            peticionAmigo.setTipo(Comunicacion.MTypes.RQ_DAMIGO);
            peticionAmigo.setContenido(bannedFriend);
            
            EnviarCadena = new DataOutputStream(soquet.getOutputStream());
            EnviarCadena.writeUTF(json.toJson(peticionAmigo));
            DataInputStream RecibirConfirmacion= new DataInputStream(soquet.getInputStream());
            RecibirConfirmacion.readUTF();
            
        } catch (IOException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    private static void MostrarMensajeAmigo(Mensaje mensaje) {
        Platform.runLater(
            () -> Interfaz.createBubble(mensajes, Boolean.TRUE, Boolean.FALSE, mensaje.getContenido(), null)
        );
    }
    
    private static boolean GuardarMensajePersonal (Mensaje mensaje, int Id_Conversnate) {
        Gson gson = new Gson();
            
        //Id_Conversante es el ID de la otra persona con la que se mensajea
        
        ArchivosController mensajesPersonalesFile = new ArchivosController (
            System.getProperty("user.dir") + "\\" + Id_Conversnate + ".json"
        );
        
        List <Mensaje> smsPersonalesRecibidos = new ArrayList<>();
        List <String> smsStrFile = mensajesPersonalesFile.readFile();
        
        for (String personalSmsStr : smsStrFile) {
            Mensaje smsRecibido = new Mensaje();
            smsRecibido = gson.fromJson(personalSmsStr, Mensaje.class);
            smsPersonalesRecibidos.add(smsRecibido);
        }
        
        if (smsPersonalesRecibidos.size() < 3) {
            boolean wsms = mensajesPersonalesFile.writeFile(gson.toJson(mensaje));
            if (wsms) {
                return true;    //Mensaje almacenado
            } else {
                return false;
            }
        } else if (smsPersonalesRecibidos.size() > 2) {
          Iterator listIterator = smsPersonalesRecibidos.listIterator();
          List <Mensaje> newPersonalSms = new ArrayList<>();
          
          while(listIterator.hasNext()) {
              Mensaje aux = new Mensaje();
              aux = (Mensaje) listIterator.next();
              newPersonalSms.add(aux);
          }
          
          newPersonalSms.add(mensaje);
          smsStrFile = new ArrayList<>();
          
          for (Mensaje newSms : newPersonalSms) {
              smsStrFile.add(gson.toJson(newSms));
          }
          
          boolean owsms = mensajesPersonalesFile.overwriteFile(smsStrFile);
          
          if (owsms) {
              return true;
          } else {
            return false;
          }
        }
        return false;//Error por que no hay numero mensajes adecuado
    }    
}
