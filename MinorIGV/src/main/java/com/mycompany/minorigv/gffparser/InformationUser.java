package com.mycompany.minorigv.gffparser;
import com.mycompany.minorigv.sequence.TranslationManeger;
import com.mycompany.minorigv.sequence.makeCompStrand;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Ophalen van de informatie doormiddel van de keuzes die de gebruiker maakt in de gui.
 *
 * @author Anne van Ewijk, Huub Goltstein en Amber Janssen Groesbeek
 */
public class InformationUser {

    /**
     * Ophalen informatie doormiddel van de keuzes van de gebruiker in de gui.
     *
     * @param organisme     object van het organismen met alle bijbehorende chromosomen/contigs en
     *                      daarvan de informatie.
     */
    public void getInfo(Organisms organisme) throws Exception {
        // Keuzes van de gebruiker van de applicatie.
        String chromosoom_id = "NC_001133.9";
        int start = 2000;
        int stop = 5000;
        ArrayList<String> keuze_gebruiker = new ArrayList<String>(){{add("Gene"); add("CDS"); add("Region");}};

        Chromosome chromosoom = new Chromosome();
        try {
            chromosoom = organisme.getChromosome(chromosoom_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getFeaturesUser(chromosoom, start, stop, keuze_gebruiker);
        chromosoom.setListORF();
        getORFsUser(chromosoom, start, stop);
        makeCompStrand compStrand = new makeCompStrand();
        String seqComp = compStrand.getReverseComplement(chromosoom.getSeqTemp().toUpperCase());
        getAAuser(chromosoom, seqComp);
        writeORF(chromosoom, seqComp);
    }

    /**
     * Het verkrijgen van de objecten van de gewenste features en binnen een bepaalde range.
     *
     * @param chromosoom        Object met het gewenste chromosoom object
     * @param start             Start positie die de gebruiker wilt zien
     * @param stop              Stop positie tot waar de gebruiker het chromosoom wilt zien
     * @param keuze_gebruiker   De keuze die de gebruiker maakt voor het tonen van welke features
     */
    public void getFeaturesUser(Chromosome chromosoom, int start, int stop, ArrayList<String> keuze_gebruiker){
        // Ophalen van alle features tussen een bepaalde start en stop codon.
        ArrayList<Feature> featureList = chromosoom.getFeaturesBetween(start,stop);

        // Filteren van de features die tussen een bepaalde start en stop codon zitten.
        ArrayList<Feature> featureFilteredList = chromosoom.filterFeatures(featureList, keuze_gebruiker);

        for(Feature feat: featureFilteredList){
            HashMap attributes = feat.getAttributes();
            // Lijst met alle Keys
            attributes.keySet();
            // Ophalen values
            System.out.println(attributes.get("locus_tag"));

        }
    }

    /**
     * Het verkrijgen van de objecten van orfs die binnen een bepaalde range vallen.
     *
     * @param chromosoom        Object met het gewenste chromosoom object
     * @param start             Start positie vanaf waar de gebruiker wilt zoeken
     * @param stop              Stop positie tot waar de gebruiker wilt zoeken
     */
    public void getORFsUser(Chromosome chromosoom, int start, int stop){
        ArrayList<ORF> orfsFilteredList = chromosoom.getORFsBetween(start, stop);

        for(ORF o: orfsFilteredList){
            o.getStart();
        }
    }

    /**
     * Het vertalen van de sequentie uit het fasta bestand (chromosoom/contig sequentie) naar een aminozuursequentie
     * in drie verschillende reading frames.
     *
     * @param chr       Chromosoom object van het gekozen chromosoom van de gebruiker.
     */
    public HashMap<String, Object> getAAuser(Chromosome chr, String seqComp){
        TranslationManeger translator = new TranslationManeger();
        HashMap<String, Object> readingframes = translator.start(chr.getSeqTemp().toUpperCase(), seqComp.toUpperCase());

        return readingframes;
    }

    /**
     * Wegschrijven ORFs in een fasta bestand.
     *
     * @param chr       Object van het gewenste chromosoom.
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public void writeORF(Chromosome chr, String seqComp) throws FileNotFoundException, UnsupportedEncodingException {
        //Wegschrijven ORFs
        System.out.println(seqComp);
        System.out.println(chr.getSeqTemp());
        ArrayList<ORF> listORF = chr.getListORF();
        PrintWriter writer = new PrintWriter("orf.fasta", "UTF-8");
        for(ORF o: listORF){
            writer.println(">" + o.getIdORF() + "|RF: " + o.getReadingframe() + "|start: " + o.getStart() + "|stop: " + o.getStop());
            if(o.getReadingframe() > 0){
                writer.println(chr.getSeqTemp().substring(o.getStart(), o.getStop()));
            }else{
                writer.println(seqComp.substring(o.getStop(), o.getStart()));
            }
        }
        writer.close();
    }
}