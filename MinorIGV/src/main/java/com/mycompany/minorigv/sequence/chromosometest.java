/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.minorigv.sequence;
import java.util.ArrayList;
/**
 *
 * @author Gebruiker
 */
public class chromosometest {

    private String id;
    private String Refsequence;
            
    public chromosometest(String id, String Refsequence) {
        this.id = id;
        this.Refsequence = Refsequence;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefsequence() {
        return Refsequence;
    }
    
    public void setRefsequence(String Refsequence) {
        this.Refsequence = Refsequence;
    }
 
}

