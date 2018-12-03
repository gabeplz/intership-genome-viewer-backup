package com.mycompany.minorigv.gffparser;

import com.mycompany.minorigv.sequence.findORF;
import com.mycompany.minorigv.sequence.makeCompStrand;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
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
    private String seqTemp;
    private ArrayList<ORF> listORF;
    private HashMap<String, Object> readingframe;
    //private String seqComp;

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


    /**
     * De constructor.
     * @param id        Het id van het chromosoom/contig
     * @param seqTemp   Sequentie van de template strand
     * @param listORF   Lijst met alle ORFs gevonden
     * //@param seqComp   Sequentie van de complementaire strand
     */
    public Chromosome(String id, String seqTemp, ArrayList<ORF> listORF){
        this.id = id;
        this.seqTemp = seqTemp;
        this.listORF = listORF;
        //this.seqComp = seqComp;
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
     * @return  seqTemp is een String. Het is de DNA sequentie van het chromosoom.
     */
    public String getSeqTemp() {
        return seqTemp;
    }

    /**
     * Het genereerd de sequentie van het chromosoom/contig.
     * @param seqTemp
     */
    public void setSeqTemp(String seqTemp) {
        this.seqTemp = seqTemp;
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
     */
    public void setListORF() throws FileNotFoundException, UnsupportedEncodingException {
        // ORFs zoeken in de template en complementaire strand.
        String seqComp = makeCompStrand.getReverseComplement(getSeqTemp());
        listORF = findORF.searchORF(getId(), getSeqTemp());
        ArrayList orfs_comp = findORF.searchORF(getId(), seqComp, "comp");
        listORF.addAll(orfs_comp);
        this.listORF = listORF;
    }

//    /**
//     * Het ophalen van de complementaire strand sequentie (DNA)
//     * @return      De DNA sequentie van de complementaire strand
//     */
//    public String getSeqComp() {
//        return seqComp;
//    }
//
//    /**
//     * Het opslaan van de complementaire strand (DNA sequentie)
//     * @param seqComp   De complementaire DNA sequentie
//     */
//    public void setSeqComp(String seqComp) {
//        //this.seqComp = seqComp;
//        // Complementaire strand maken
//        makeCompStrand compStrand = new makeCompStrand();
//        String comp = compStrand.getReverseComplement(getSeqTemp().toUpperCase());
//
//        String seqComp = new StringBuilder(comp).reverse().toString();
//        //setSeqComp(compSeq);
//
//    }

    /**
     * Hashmap met als key de readingframes (RF1, RF2, ..., RF6) en als value de aminozuursequentie
     * @return      Een hashmap. key: readingframes, value: aminozuursequentie
     */
    public HashMap<String, Object> getReadingframe() {
        return readingframe;
    }

    /**
     * Het opslaan van de hashmap met daarin voor elk readingframe (key), de aminozuursequentie (value)
     * @param readingframe  Hashmap. Key: readingframes, value: aminozuursequentie
     */
    public void setReadingframe(HashMap<String, Object> readingframe) {
        this.readingframe = readingframe;
    }

    /**
     * Alle features die tussen de start en stop positie voorkomen wordenom eem ArrayList gezet.
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
            System.out.println(klas);
            for (String optie : SelectedFeatures){
                if (klas.contains(optie)){
                    featureFilteredList.add(feat);
                }
            }
        }
        return featureFilteredList;
    }

    /**
     * Alle ORFs die tussen de start en stop positie voorkomen worden om eem ArrayList gezet.
     * @param start     Startpositie waar vandaan de orfs gezocht moeten worden
     * @param stop      Stoppositie tot waar de orfs gezocht moeten worden
     * @return          Een lijst met de orfs die voldoen aan de start en stop
     */
    public ArrayList<ORF> getORFsBetween(int start, int stop){
        ArrayList<ORF> orfsFilteredList = new ArrayList<>();

        for(ORF o : listORF){
            if (o.getStop() > start && o.getStop() < stop){
                orfsFilteredList.add(o);
            }else if(o.getStart() > start && o.getStart() < stop){
                orfsFilteredList.add(o);
            }else if(o.getStart() < start && o.getStop() > stop){
                orfsFilteredList.add(o);
            }
        }

        return orfsFilteredList;
    }



    @Override
    public String toString() {
        return "Chromosome{" +
                "id='" + id + '\'' +
                ", features=" + features +
                '}';
    }
}