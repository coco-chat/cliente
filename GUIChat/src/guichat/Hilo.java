/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import com.google.gson.Gson;
import guichat.Modelos.Comunicacion;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Wero
 */
public class Hilo extends Thread {
    
    private volatile Boolean flagThread = true;
    private ServerSocket response;
    
    public Hilo() {
        
    }
    
    @Override
    public void run(){
        System.out.println("Corriendo");
        try {
            response = new ServerSocket(7654);  
            while(this.flagThread) {
                System.out.println("Entre al while");
                Gson json = new Gson();
                Socket peticion = response.accept();
                DataInputStream datos = new DataInputStream(peticion.getInputStream());
                String da = datos.readUTF();
                Comunicacion modelo = json.fromJson(da, Comunicacion.class);
                Procesos.mensajeria(modelo);
                peticion.close();
            }
            System.out.println("Finalizado.");
        } catch (IOException ex) {
            System.out.println("Hilo Terminado.");
        }
    }
    
    public void stopThread() {
        System.out.println("Entre para detener el Hilo.");
        this.flagThread = false;
        try {
            response.close();
        } catch (IOException ex) {
            System.out.println("Hilo terminado por stopThread");
        }
    }
    
}
