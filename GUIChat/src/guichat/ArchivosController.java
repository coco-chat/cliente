/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emiliano
 */
public class ArchivosController {
    private File archivo;
    
    public ArchivosController () {
        this.archivo = null;
    }
    
    public ArchivosController(String path) {
        archivo = new File(path);
    }
    
    public boolean writeFile(String cad) {
        synchronized(archivo) {
            try {
                FileWriter FWriter = new FileWriter(archivo,true);
                BufferedWriter writer = new BufferedWriter(FWriter);
                writer.append(cad+"\n");
                writer.close();
                return true;
            } catch(IOException ex) {
                return false;
            }
        }
    }
    
    public ArrayList<String> readFile() {
        synchronized(archivo) {
                String auxiliar;
                ArrayList<String> lista = new ArrayList<>();
                try {
                    FileReader FReader = new FileReader(archivo);
                    BufferedReader reader = new BufferedReader(FReader);
                    auxiliar = reader.readLine();
                    while(auxiliar!=null) {
                        lista.add(auxiliar);
                        auxiliar=reader.readLine();
                    }
                    reader.close();
                    return lista;
                } catch(IOException ex) {
                    return null;
                }
        }
    }
    
    public boolean overwriteFile(List<String> data) {
        synchronized(archivo) {
            try {
                FileWriter FWriter = new FileWriter(archivo);
                BufferedWriter writer = new BufferedWriter(FWriter);
                for(String cad : data) {
                    cad+="\n";
                    writer.write(cad);
                }
                writer.close();
                return true;
            } catch(Exception ex) {
                return false;
            }
        }
    }
}
