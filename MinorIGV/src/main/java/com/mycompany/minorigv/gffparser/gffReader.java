package com.mycompany.minorigv.gffparser;
import java.io.*;
import java.util.*;


/**
 * Deze class leest een bestand met genomische data in. Per regel wordt er gekeken of de data in die regel relevant is
 * aan de hand van de derde kolom. Vervolgens worden er van de relevante regels objecten gemaakt zodat de data wordt opgeslagen.
 *
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 */
public class gffReader {

    /**
     * Het inlezen van het bestand per regel en het opslaan van data in de regels wanneer er in de derde kolom gene, mRNA,
     * exon, CDS of region staat.
     *
     * @param path                      Het pad waar het bestand staat wordt vanuit de main meegegeven.
     * @throws FileNotFoundException    Afvangen van een error zodra het bestand niet gevonden kan worden.
     * @throws IOException              Als er een input of output exception plaatsvindt.
     */
    public static Organisms readData(Organisms org, String path) throws FileNotFoundException, IOException, Exception {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        ArrayList<String> allContigs = new ArrayList<String>();
        
        String line = null;
        String ID = null;
        String ID_organism = null;
        
        if (org == null) {
        	org = new Organisms();
        }
        
        Chromosome chrom = null;
        // inlezen van het bestand per regel.
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("##sequence-region")) {
                String[] splited = line.split("\\s+");
                ID = splited[1];
                allContigs.add(ID);
                chrom = new Chromosome();
                chrom.setId(ID);
                org.addChromosome(chrom);
            } else if (line.startsWith("##species ")) {
                String[] id_org = line.split("=");
                ID_organism = id_org[1];
                org.setId(ID_organism);
                // Als de regel met ID begint is het mogelijk relevante data om op te slaan.
            } else if (line.startsWith("#")) {
            } else if (line.startsWith(ID)) {
                String[] columns = line.split("\\t");

                // Class waarin de kolom met attributen wordt verwerkt.
                attributes att = new attributes();
                Feature feat = null;
                // Als er in de regel in kolom 3 gene, mRNA, exon, CDS of region staat, bevat de regel relevante data dat wordt opgeslagen.
                if (line.split("\\t")[2].equals("gene")) {
                    HashMap attribute = att.splitAtt(columns[8]);
                    feat = new Gene(columns[1], columns[3], columns[4], columns[5], columns[6], columns[7], attribute);
                } else if (line.split("\\t")[2].equals("mRNA")) {
                    HashMap attribute = att.splitAtt(columns[8]);
                    feat = new mRNA(columns[1], columns[3], columns[4], columns[5], columns[6], columns[7], attribute);
                } else if (line.split("\\t")[2].equals("exon")) {
                    HashMap attribute = att.splitAtt(columns[8]);
                    feat = new Exon(columns[1], columns[3], columns[4], columns[5], columns[6], columns[7], attribute);
                } else if (line.split("\\t")[2].equals("CDS")) {
                    HashMap attribute = att.splitAtt(columns[8]);
                    feat = new CDS(columns[1], columns[3], columns[4], columns[5], columns[6], columns[7], attribute);
                } else if (line.split("\\t")[2].equals("region")) {
                    HashMap attribute = att.splitAtt(columns[8]);
                    feat = new Region(columns[1], columns[3], columns[4], columns[5], columns[6], columns[7], attribute);
                }
                chrom.addFeature(feat);
            }
        }
        return org;
    }
}