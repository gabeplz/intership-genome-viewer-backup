package com.mycompany.minorigv.gffparser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Deze class maakt objecten voor chromosomen/contigs. Ook worden hier de bijbehorende features in object gezet.
 *
 * @author Anne van Ewijk en Amber Janssen Groesbeek
 */
public class Chromosome {

    private String id;
    private ArrayList<Feature> features;
    private String seq;
    private ArrayList<ORF> listORF;
    private String comp;
    private HashMap<String, Object> readingframe;

    /**
     * De constructor.
     */
    public Chromosome() {
        features = new ArrayList<Feature>();
    }

    /**
     * De constructor.
     * @param id        Het id van het chromosoom/contig.
     * @param features  Features zijn het CDS, Exon, Gene, mRNA en Region.
     */
    public Chromosome(String id, ArrayList<Feature> features) {
        this.id = id;
        this.features = features;
    }

//    public Chromosome(String comp, HashMap<String, Object> readingframe){
//        this.comp = comp;
//        this.readingframe = readingframe;
//    }

    /**
     * De constructor.
     * @param id        Het id van het chromosoom/contig.
     * @param seq
     */
    public Chromosome(String id, String seq, ArrayList<ORF> listORF){
        this.id = id;
        this.seq = seq;
        this.listORF = listORF;
    }

    /**
     * Het toevoegen van features aan het chromosoom.
     * @param feat      Features zijn het CDS, Exon, Gene, mRNA en Region.
     */
    public void addFeature(Feature feat){
        if (feat instanceof Feature){
            features.add(feat);
        }
    }

    /**
     * Het returned het id van het chromosoom/contig.
     * @return  id is een String. Het is het id van het chromosoom/contig.
     */
    public String getId() {
        return id;
    }

    /**
     * Het genereerd het id van het chromosoom/contig.
     * @param id is een String. Het is het id van het chromosoom/contig.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Het returned de sequentie van het chromosoom/contig.
     * @return  seq is een String. Het is de DNA sequentie van het chromosoom.
     */
    public String getSeq() {
        return seq;
    }

    /**
     * Het genereerd de sequentie van het chromosoom/contig.
     * @param seq
     */
    public void setSeq(String seq) {
        this.seq = seq.toUpperCase();
    }

    /**
     * Het returned een ArrayList met de features erin.
     * @return  features is een ArrayList met daarin de features
     */
    public ArrayList<Feature> getFeatures() {
        return features;
    }

    /**
     * Het genereerd de ArrayList met daarin de features.
     * @param features is een Arraylist met daarin de features
     */
    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }

    /**
     * Het returned een ArrayList met daarin de ORFs.
     * @return  listORF is een ArrayList met daarin de ORFs.
     */
    public ArrayList<ORF> getListORF() {
        return listORF;
    }

    /**
     * Het genereerd de ArrayList met daarin de ORFs
     * @param listORF is een ArrayList met daarin de ORFs.
     */
    public void setListORF(ArrayList<ORF> listORF) {
        this.listORF = listORF;
    }

    public String getComp() {
        return comp;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }

    public HashMap<String, Object> getReadingframe() {
        return readingframe;
    }

    public void setReadingframe(HashMap<String, Object> readingframe) {
        this.readingframe = readingframe;
    }

    /**
     * Alle features die tussen de start en stop positie voorkomen wordem om eem ArrayList gezet.
     * @param start     Start positie van de feature op het chromosoom.
     * @param stop      Stop positie van de feature op het chromosoom
     * @return          Een lijst met de features die voldoen aan de start en stop
     */
    public ArrayList<Feature> getFeaturesBetween(int start, int stop){

        ArrayList<Feature> featureList = new ArrayList<Feature>();
        for(Feature f : features){
            if (f.getStop() > start && f.getStop() < stop){
                featureList.add(f);
            }else if(f.getStart() > start && f.getStart() < stop){
                featureList.add(f);
            }else if(f.getStart() < start && f.getStop() > stop){
                featureList.add(f);
            }
        }
        return featureList;
    }

    /**
     * De Features worden opgehaald na keuze van de gebruiker.
     * Stel de gebruiker wilt alleen Gene en CDS zien, dan worden deze features opgehaald die behoren tot CDS en Gene.
     * @param featureList           is de lijst met alle features uit het chromosoom/contig.
     * @param SelectedFeatures      is de lijst met de gekozen features (dus bijv. Gene en CDS) die de gebruiker heeft gekozen.
     * @return                      Een lijst met de gekozen features wordt gereturned.
     */
    public static ArrayList<Feature> filterFeatures(ArrayList<Feature> featureList, ArrayList<String> SelectedFeatures){
        ArrayList<Feature> featureFilteredList = new ArrayList<Feature>();
        for (Feature feat : featureList){
            String klas = feat.getClass().toString();
            for (String optie : SelectedFeatures){
                if (klas.contains(optie)){
                    featureFilteredList.add(feat);
                }
            }
        }
        return featureFilteredList;
    }


    @Override
    public String toString() {
        return "Chromosome{" +
                "id='" + id + '\'' +
                ", features=" + features +
                '}';
    }
}