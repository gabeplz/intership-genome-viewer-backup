package com.mycompany.minorigv.gffparser;
import java.util.ArrayList;
import java.util.HashMap;

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
    public void getInfo(Organisms organisme){

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

        for(Feature feat: featureFilteredList){
            HashMap attributes = feat.getAttributes();
            // Lijst met alle Keys
            attributes.keySet();
            // Ophalen values
            attributes.get("locus_tag");

            System.out.println(feat.getStart() + " " + feat.getStop());
        }
    }
}
