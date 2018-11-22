package com.mycompany.minorigv.gffparser;
import java.io.*;
import java.util.*;

/**
 * Aanroepen van gffReader.
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 */
public class main {
    public static String path = "/home/kahuub/Documents/github-minor/Minor-IGV/MinorIGV/src/main/resources/voorbeeldgff.gff";

    public static void main(String[] args) throws FileNotFoundException, IOException{
    	
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    	path = classLoader.getResource("voorbeeldgff.gff").getFile();

        gffReader lees = new gffReader();
        Organisms org = lees.readData(path);
        InformationUser info = new InformationUser();
        info.getInfo(org);
    }
}