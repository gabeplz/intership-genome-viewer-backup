package com.mycompany.minorigv;
import java.util.ArrayList;

public class Chromosome {

    private String id;

    private ArrayList<Feature> features;

    public Chromosome(String id, ArrayList<Feature> features) {
        this.id = id;
        this.features = features;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }

    @Override
    public String toString() {
        String result = "";
        for(int i = 0; i< features.size(); i++){
            result += (" " + features.get(i).toString());
        }
        return result;
    }

    public ArrayList<Feature> getFeaturesBetween(int start, int stop){

        ArrayList<Feature> featureList = new ArrayList<Feature>();
        for(Feature f : features){


            if (f.getStop_gene() > start && f.getStop_gene() < stop){
                featureList.add(f);
            }else if(f.getStart_gene() > start && f.getStart_gene() < stop){
                featureList.add(f);
            }else if(f.getStart_gene() < start && f.getStop_gene() > stop){
                featureList.add(f);
            }
        }

        return featureList;
    }
}
