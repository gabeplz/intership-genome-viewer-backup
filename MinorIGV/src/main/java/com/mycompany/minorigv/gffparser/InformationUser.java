package com.mycompany.minorigv.gffparser;
import com.mycompany.minorigv.sequence.AminoAcidSequence;
import com.mycompany.minorigv.sequence.TranslationManeger;
import com.mycompany.minorigv.sequence.findORF;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mycompany.minorigv.sequence.TranslationManeger.getReverseComplement;

/**
 * Ophalen van de informatie doormiddel van de keuzes die de gebruiker maakt in de gui.
 *
 * @author Anne van Ewijk en Huub Goltstein
 */
public class InformationUser {

    /**
     * Ophalen informatie doormiddel van de keuzes van de gebruiker in de gui.
     *
     * @param organisme     object van het organismen met alle bijbehorende chromosomen/contigs en
     *                      daarvan de informatie.
     */
    public void getInfo(Organisms organisme) throws Exception {

        String chromosoom_id = "NC_001133.9";
        int start = 2000;
        int stop = 5000;

        ArrayList<String> keuze_gebruiker = new ArrayList<String>(){{add("Gene"); add("CDS"); add("Region");}};

        Chromosome chromosoom = null;
        try {
            chromosoom = organisme.getChromosome(chromosoom_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ophalen van alle features tussen een bepaalde start en stop codon.
        ArrayList<Feature> featureList = chromosoom.getFeaturesBetween(start,stop);

        // Filteren van de features die tussen een bepaalde start en stop codon zitten.
        ArrayList<Feature> featureFilteredList = chromosoom.filterFeatures(featureList, keuze_gebruiker);

//        for(Feature feat: featureFilteredList){
//            HashMap attributes = feat.getAttributes();
//            // Lijst met alle Keys
//            attributes.keySet();
//            // Ophalen values
//            attributes.get("locus_tag");
//            //System.out.println(attributes.get("locus_tag"));
//        }
//
//        for(ORF o: chromosoom.getListORF()){
//            //System.out.println(o.getAaStart());
//        }
//
//        ArrayList<ORF> hi = chromosoom.getListORF();
//        //System.out.println(hi);







        Chromosome chr = organisme.getChromosome("NC_001133.9");
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


        TranslationManeger translator = new TranslationManeger();
        HashMap<String, Object> readingframes = translator.start(chr.getSeq().toUpperCase());

        String complement = getReverseComplement(chr.getSeq().toUpperCase());
        String comp = new StringBuilder(complement).reverse().toString();
        chr.setComp(comp);
        chr.setReadingframe(readingframes);
        
        AminoAcidSequence RF = (AminoAcidSequence) chr.getReadingframe().get("RF5");
        System.out.println(RF.getSequence());

    }
}
