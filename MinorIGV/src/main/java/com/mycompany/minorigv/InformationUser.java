package com.mycompany.minorigv;
import java.util.ArrayList;

public class InformationUser {


    public void getInfo(ArrayList<String> allContigs, ArrayList<Chromosome> chromosomes){
        Gene gen = new Gene();





        for( Chromosome chromosome : chromosomes){
            String contigID = allContigs.get(1);
            if(chromosome.getId().equals(contigID)){
//                chromosome.getFeatures();
                //System.out.println(chromosome);

                //System.out.println(chromosome.getFeaturesBetween(2000,5000));

                ArrayList<Feature> lijst = chromosome.getFeaturesBetween(2000,5000);
                for(Feature s : lijst){
                    System.out.println(s);



                }
            }
        }

    }
}
