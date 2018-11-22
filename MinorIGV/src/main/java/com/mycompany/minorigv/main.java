package com.mycompany.minorigv;
import java.io.*;

/**
 * Aanroepen van gffReader.
 *
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 *
 */
public class main {
    public static String path = "/Users/amber/Desktop/voorbeeld gff.gff";

    public static void main(String[] args) throws FileNotFoundException, IOException{
        gffReader lees = new gffReader();
        lees.readData(path);
    }
}