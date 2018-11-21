package com.mycompany.minorigv;
import java.io.*;

/**
 *
 * @author amber/anne
 */
public class main {
    public static String path = "/Users/amber/Desktop/voorbeeld gff.gff";


    public static void main(String[] args) throws FileNotFoundException, IOException{
        gffReader lees = new gffReader();
        lees.readData(path);
    }
}