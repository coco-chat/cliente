/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichat.Components;

import javafx.scene.control.Button;

/**
 *
 * @author Wero
 */
public class CButton extends Button {
    
    private int idElement;
    private String nameElement;

    public String getNameElement() {
        return nameElement;
    }

    public void setNameElement(String name_element) {
        this.nameElement = name_element;
    }
    
    public CButton(String text) {
        super(text);
    }
    
    public CButton(){
        
    }

    public int getIdElement() {
        return idElement;
    }

    public void setIdElement(int id_element) {
        this.idElement = id_element;
    }
    
}
