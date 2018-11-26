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

    public String line;
    public String ID;
    public String ID_organism;

    /**
     * Het inlezen van het bestand per regel en het opslaan van data in de regels wanneer er in de derde kolom gene, mRNA,
     * exon, CDS of region staat.
     *
     * @param path                      Het pad waar het bestand staat wordt vanuit de main meegegeven.
     * @throws FileNotFoundException    Afvangen van een error zodra het bestand niet gevonden kan worden.
     * @throws IOException              Als er een input of output exception plaatsvindt.
     */
    public Organisms readData(String path) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        ArrayList<Chromosome> chromosomes = new ArrayList<>();
        ArrayList<String> allContigs = new ArrayList<String>();

        // inlezen van het bestand per regel.
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("##sequence-region")) {
                String[] splited = line.split("\\s+");
                ID = splited[1];
                allContigs.add(ID);
            } else if (line.startsWith("##species ")) {
                String[] id_org = line.split("=");
                ID_organism = id_org[1];
                // Als de regel met ID begint is het mogelijk relevante data om op te slaan.
            } else if (line.startsWith("#")) {
            } else if (line.startsWith(ID)) {
                String[] columns = line.split("\\t");

                // Class waarin de kolom met attributen wordt verwerkt.
                attributes att = new attributes();

                // Als er in de regel in kolom 3 gene, mRNA, exon, CDS of region staat, bevat de regel relevante data dat wordt opgeslagen.
                if (line.split("\\t")[2].equals("gene")) {
                    HashMap attribute = att.splitAtt(columns[8]);
                    Gene genObject = makeGene(columns[1], columns[3], columns[4], columns[5], columns[6], columns[7], attribute);
                    make(chromosomes, genObject);
                } else if (line.split("\\t")[2].equals("mRNA")) {
                    HashMap attribute = att.splitAtt(columns[8]);
                    mRNA mRNAObject = makemRNA(columns[1], columns[3], columns[4], columns[5], columns[6], columns[7], attribute);
                    make(chromosomes, mRNAObject);
                } else if (line.split("\\t")[2].equals("exon")) {
                    HashMap attribute = att.splitAtt(columns[8]);
                    Exon exonObject = makeExon(columns[1], columns[3], columns[4], columns[5], columns[6], columns[7], attribute);
                    make(chromosomes, exonObject);
                } else if (line.split("\\t")[2].equals("CDS")) {
                    HashMap attribute = att.splitAtt(columns[8]);
                    CDS cdsObject = makeCDS(columns[1], columns[3], columns[4], columns[5], columns[6], columns[7], attribute);
                    make(chromosomes, cdsObject);
                } else if (line.split("\\t")[2].equals("region")) {
                    HashMap attribute = att.splitAtt(columns[8]);
                    Region regionObject = makeRegion(columns[1], columns[3], columns[4], columns[5], columns[6], columns[7], attribute);
                    make(chromosomes, regionObject);
                }
            }
        }
        Organisms org = new Organisms(ID_organism, chromosomes);
        return org;
    }

    /**
     * De class Chromosome wordt gelinkt aan de features van het chromosoom. Zodra het ID (chromosoom 1 bijv)
     * al aanwezig is in de arraylist chromosomes, dan wordt het object van de feature (mRNA bijv) toegevoegd aan het ID.
     *
     * @param chromosomes   Een ArrayList met de objecten van chromosome.
     * @param feature       Het object van een feature (gene, mRNA, exon, CDS of region).
     */
    public void make(ArrayList<Chromosome> chromosomes, Feature feature) {
        // Ophalen of de ArrayList chromosome het ID al bevat (nee: -1).
        int index = containsID(chromosomes, ID);

        // ArrayList chromosome vullen met de objecten.
        if(index == -1){
            // Alle data van een feature (één regel met daarin gene, mRNA, exon, CDS of region) in initialList zetten.
            ArrayList<Feature> initialList = new ArrayList<>();
            initialList.add(feature);

            // Er wordt een nieuw object chromosoom gemaakt met bijbehorende chromosoom ID (ID).
            Chromosome chromosome = new Chromosome(ID, initialList);

            // Object chromosoom wordt toegevoegd aan ArrayList chromosomes.
            chromosomes.add(chromosome);
        }else{
            // Toevoegen van een feature (gene, mRNA, exon, CDS of region) aan een bestaande chromosoom ID.
            Chromosome currentChromosome = chromosomes.get(index);
            currentChromosome.getFeatures().add(feature);
        }
    }

    /**
     * Checken of er al een object is gemaakt met het chromosoom ID. Als er wel een object is gemaakt met het ID dan
     * wordt er een getal groter dan 0 gereturned. Als er nog geen object is aangemaakt dan wordt -1 gereturned.
     *
     * @param list  een ArrayList met daarin alle objecten van chromosomes.
     * @param id    het ID van het chromosoom.
     * @return      -1 geeft aan dat er nog geen Object is met het ID.
     */
    private int containsID(ArrayList<Chromosome> list, String id){
        // Loopen over de ArrayList met chromosoom objecten.
        for( int i=0;i< list.size();i++){
            if(list.get(i).getId().equals(id))
                return i;
        }
        return -1;
    }


    /**
     * Het opslaan van alle informatie van het gen in een object.
     *
     * @param seqid       Het ID van het contig/chromosoom waarin het gen aanwezig is.
     * @param start       Start positie van het gen op het genoom
     * @param end         Stop positie van het gen op het genoom
     * @param score       Score van het gen
     * @param strand      Of het gen aanwezig is in de strand (+) of complementaire strand (-)
     * @param phase       Het geeft het reading frame aan waarin het gen voorkomt (0,1,2 of ".")
     * @param attributes  Informatie over het gen, locus_tag aanwezig.
     * @return            Object waarin de meegegeven informatie van het gen wordt opgeslagen.
     */
    private static Gene makeGene(String seqid, String start, String end, String score, String strand, String phase, HashMap attributes) {
        Gene object;
        object = new Gene(seqid, start, end, score, strand, phase, attributes);
        return object;
    }

    /**
     * Het opslaan van alle informatie van het mRNA in een object.
     *
     * @param seqid         Het ID van het contig/chromosoom waarin het mRNA aanwezig is.
     * @param start         Start positie van het mRNA op het genoom
     * @param end           Stop positie van het mRNA op het genoom
     * @param score         Score van het mRNA
     * @param strand        Of het mRNA aanwezig is in de strand (+) of complementaire strand (-)
     * @param phase         Het geeft het reading frame aan waarin het mRNA voorkomt (0,1,2 of ".")
     * @param attributes    Informatie over het gen.
     * @return              Object waarin de meegegeven informatie van het gen wordt opgeslagen.
     */
    private static mRNA makemRNA(String seqid, String start, String end, String score, String strand, String phase, HashMap attributes) {
        mRNA object;
        object = new mRNA(seqid, start, end, score, strand, phase, attributes);
        return object;
    }

    /**
     * Het opslaan van alle informatie van het exon in een object.
     *
     * @param seqid         Het ID van het contig/chromosoom waarin het exon aanwezig is.
     * @param start         Start positie van het exon op het genoom
     * @param end           Stop positie van het exon op het genoom
     * @param score         Score van het exon
     * @param strand        Of het exon aanwezig is in de strand (+) of complementaire strand (-)
     * @param phase         Het geeft het reading frame aan waarin het exon voorkomt (0,1,2 of ".")
     * @param attributes    Informatie over het exon.
     * @return              Object waarin de meegegeven informatie van het exon wordt opgeslagen.
     */
    private static Exon makeExon(String seqid, String start, String end, String score, String strand, String phase, HashMap attributes) {
        Exon object;
        object = new Exon(seqid, start, end, score, strand, phase, attributes);
        return object;
    }

    /**
     * Het opslaan van alle informatie van het CDS (coding sequence) in een object.
     *
     * @param seqid         Het ID van het contig/chromosoom waarin het CDS aanwezig is.
     * @param start         Start positie van het CDS op het genoom
     * @param end           Stop positie van het CDS op het genoom
     * @param score         Score van het CDS
     * @param strand        Of het CDS aanwezig is in de strand (+) of complementaire strand (-)
     * @param phase         Het geeft het reading frame aan waarin het CDS voorkomt (0,1,2)
     * @param attributes    Informatie over het CDS.
     * @return              Object waarin de meegegeven informatie van het CDS wordt opgeslagen.
     */
    private static CDS makeCDS(String seqid, String start, String end, String score, String strand, String phase, HashMap attributes) {
        CDS object;
        object = new CDS(seqid, start, end, score, strand, phase, attributes);
        return object;
    }

    /**
     * Het opslaan van alle informatie van de regio in een object. De regio bevat informatie over het
     * chromosoom zelf.
     *
     * @param seqid         Het ID van het contig/chromosoom.
     * @param start         Start positie van het contig/chromosoom op het genoom
     * @param end           Stop positie van het contig/chromosoom op het genoom
     * @param score         Score van het Region
     * @param strand        Of het contig/chromosoom aanwezig is in de strand (+) of complementaire strand (-)
     * @param phase         Het geeft het reading frame aan waarin het Region voorkomt (0,1,2 of ".")
     * @param attributes    Informatie over het contig/chromosoom.
     * @return              Object waarin de meegegeven informatie van het CDS wordt opgeslagen.
     */
    private static Region makeRegion(String seqid, String start, String end, String score, String strand, String phase, HashMap attributes) {
        Region object;
        object = new Region(seqid, start, end, score, strand, phase, attributes);
        return object;
    }
}

