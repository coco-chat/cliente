/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat.Components;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Wero
 */
public class CCheckBox extends CheckBox {
    
    private int idUser;
    
    public CCheckBox(String name){
        this.setText(name);
    }
    
    public void setIdUser(int idUser){
        this.idUser = idUser;
    }
    
    public int getIdUser(){
        return this.idUser;
    }
    
    
    
}
