/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author usuario
 */
public class Cliente {

    Cliente()
    {
    
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int numero=1;
        byte cadenita[];
        cadenita= new byte[100];
        String arreglo;
        Scanner scanear;
        scanear = new Scanner(System.in);
        arreglo= scanear.nextLine();
        Socket cliente;
        try {
            cliente= new Socket("192.168.84.207",81);
            System.out.println("Conectado");
            cliente.getOutputStream().write(arreglo.getBytes("UTF-8"));
            cliente.getInputStream().read(cadenita);
            arreglo= new String(cadenita);
            System.out.println(arreglo);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
