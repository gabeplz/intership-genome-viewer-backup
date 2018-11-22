package com.mycompany.minorigv;
import java.io.*;
import java.util.*;

/**
 * Aanroepen van gffReader.
 *
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 *
 */
public class main {
    public static String path = "D:\\0000 HAN\\00 Minor\\Project\\voorbeeld gff.gff";

    public static void main(String[] args) throws FileNotFoundException, IOException{

        gffReader lees = new gffReader();
        Organisms org = lees.readData(path);
        InformationUser info = new InformationUser();
        info.getInfo(org);
    }
}