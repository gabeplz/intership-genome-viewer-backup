package com.mycompany.minorigv.gffparser;
import com.mycompany.minorigv.FastaFileReader;

import java.io.*;
import java.util.*;

/**
 * Aanroepen van gffReader.
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 */
public class main {
    public static String path_gff;
    public static String path_fasta;
    public static void main(String[] args) throws FileNotFoundException, IOException{
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        path_gff = classLoader.getResource("voorbeeldgff.gff").getFile();
        path_fasta = classLoader.getResource("GCF_000146045.2_R64_genomic.fna").getFile();

        gffReader lees = new gffReader();
        Organisms org = lees.readData(path_gff);

        FastaFileReader file = new FastaFileReader();
        file.openFasta(path_fasta);

        InformationUser info = new InformationUser();
        info.getInfo(org);

    }
}