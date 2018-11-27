package com.mycompany.minorigv.gffparser;
import com.mycompany.minorigv.FastaFileReader;
import com.mycompany.minorigv.sequence.findORF;

import java.io.*;
import java.util.*;

/**
 *
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 */
public class main {
    public static String path_gff;
    public static String path_fasta;
    public static void main(String[] args) throws Exception{
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        path_gff = classLoader.getResource("voorbeeldgff.gff").getFile();
        path_fasta = classLoader.getResource("GCF_000146045.2_R64_genomic.fna").getFile();

        gffReader lees = new gffReader();
        Organisms org = lees.readData(path_gff);

        HashMap<String,String> fastaMap = FastaFileReader.getSequences(path_fasta);

        for(String id : fastaMap.keySet()){
            org.addSequence(id,fastaMap.get(id));
        }
        Chromosome chr = org.getChromosome("NC_001133.9");
        ArrayList orfs = findORF.searchORF("NC_001133.9",chr.getSeq());
        chr.setListORF(orfs);

        // Wegschrijven ORFs
        ArrayList<ORF> listORF = chr.getListORF();
        PrintWriter writer = new PrintWriter("orf.fasta", "UTF-8");
        for(ORF o: listORF){
            writer.println(">ORF" + o.getIdORF() + "|RF: " + o.getReadingframe() + "|start: " + o.getStart() + "|stop: " + o.getStop());
            writer.println(o.getDNA_ORF());
        }
        writer.close();

        InformationUser info = new InformationUser();
        info.getInfo(org);
    }
}