package com.mycompany.minorigv.gffparser;
import java.util.ArrayList;

public class InformationUser {


    public void getInfo(Organisms organisme){

        String org_id = "559292";
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

        ArrayList<Feature> featureList = chromosoom.getFeaturesBetween(start,stop);
        ArrayList<Feature> featureFilteredList = chromosoom.filterFeatures(featureList, keuze_gebruiker);
        System.out.println(featureFilteredList);
    }
}
