
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.minorigv;

import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Gebruiker
 */
public class helloWorld {

    public static void main(String[] args) {
        chromosometest nummer1 = new chromosometest("1", "atgaaaccg"); //werk tot ongeveer 52000  //atgaaaccg
        chromosometest nummer2 = new chromosometest("1", "aattaaggg");
        
        
        
    
        System.out.println((TranslationManeger.start(nummer1)[3].getSequence()));
//        System.out.println((TranslationManeger.start(nummer2)[0].getSequence()));

    }
}
