package com.mycompany.minorigv;
import java.util.ArrayList;

public class InformationUser {


    public void getInfo(ArrayList<String> allContigs, ArrayList<Chromosome> chromosomes){
        Gene gen = new Gene();
        for( Chromosome chromosome : chromosomes){
            // prokaryoten hebben meestal maar 1 chromosoom/contig, vandaar de index 0.
            String contigID = allContigs.get(0);
            if(chromosome.getId().equals(contigID)){
                ArrayList<Feature> lijst = chromosome.getFeaturesBetween(2000,5000);
                for(Feature s : lijst){
//                    System.out.println(s);
                }
            }
        }

    }
}
