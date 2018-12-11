package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.gffparser.Feature;
import com.mycompany.minorigv.gffparser.Gene;
import com.mycompany.minorigv.gffparser.mRNA;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CoordinatesFeatures {
    FeaturePanel draw;

    /**
     * Aangeven dat draw het feature panel is in FeaturePanel bestand.
     * @param featurePanel      De panel waarop getekent gaat worden.
     */
    public CoordinatesFeatures(FeaturePanel featurePanel) {
        draw = featurePanel;
    }

    /**
     * Aparte lijsten maken voor elke Feature.
     * @param featureFilteredList      Lijst met alle features door elkaar op basis van de keuze van de gebruiker.
     * @param g                        Graphic
     */
    public void seperateFeatures(ArrayList<Feature> featureFilteredList, Graphics g){
        // De coordinaten van de y as op de forward en reverse panel
        int y_cood_reverse = 4;
        int y_cood_forward = 4;

        // Hoogste y as die is getekend zodat de volgende feature vanaf dat coordinaat pas gaat tekenen
        int y_cood_reverse_max = 0;
        int y_cood_forward_max = 0;

        ArrayList<Feature> featureGene = new ArrayList<>();
        ArrayList<Feature> featuremRNA = new ArrayList<>();

        // Elke feature in een aparte lijst zetten
        for(Feature feat : featureFilteredList) {
            if (feat instanceof Gene) {
                featureGene.add(feat);
            } else if (feat instanceof mRNA) {
                featuremRNA.add(feat);
            }
        }

        ArrayList<Integer> newCoord;

        // Als de lijsten niet leeg zijn, dan worden de coordinaten bepaald voor het mappen.
        if(!featureGene.isEmpty()){
            newCoord = setCoordinates(featureGene, g, y_cood_reverse, y_cood_forward,y_cood_reverse_max,y_cood_forward_max);
            y_cood_reverse = newCoord.get(0);
            y_cood_forward = newCoord.get(1);
            y_cood_forward_max = newCoord.get(2);
            y_cood_reverse_max = newCoord.get(3);
        }if(!featuremRNA.isEmpty()){
            newCoord = setCoordinates(featuremRNA, g, y_cood_reverse, y_cood_forward, y_cood_reverse_max, y_cood_forward_max);
        }
    }

    /**
     * Coordinaten van elke feature worden bepaald en meegegeven aan een methode die de features tekent.
     * @param OneFeature            Een lijst met daarin alle objecten van één feature, bijvoorbeeld alle objecten van de feature Genes.
     * @param g                     Graphics
     * @param y_cood_reverse        De y as coordinaat in de reverse panel
     * @param y_cood_forward        De y as coordinaat in de forward panel
     * @param y_cood_reverse_max    De hoogste y coordinaat van een getekende feature in de reverse panel
     * @param y_cood_forward_max    De hoogste y coordinaat van een getekende feature in de forward panel
     * @return  ArrayList met op index 1: y_cood_reverse, index 2: y_cood_forward, 3: y_cood_forward_max, 4: y_cood_reverse_max.
     */
    public ArrayList<Integer> setCoordinates(ArrayList<Feature> OneFeature, Graphics g, int y_cood_reverse, int y_cood_forward, int y_cood_reverse_max, int y_cood_forward_max){
        int latest_stop_reverse = 0;
        int latest_stop_forward = 0;
        HashMap<Feature, Integer> list_ov_ft = new HashMap<>();
        Feature feat = null;
        ArrayList<Integer> newCoord = new ArrayList<>();

        // Voor elke feature in een lijst met dezelfde features (bijv. alleen genen) wordt er gekeken op welke strand het voorkomt en wordt getekend.
        for(Feature feature: OneFeature){
            if (feature.getStrand().equals("-")){
                // Ophalen van de coordinaten op de reverse strand van de Feature.
                y_cood_reverse = getCoordinates(feature, latest_stop_reverse, list_ov_ft, y_cood_reverse, feat);

                // Ophalen tags
                String tag = getTag(feature);

                // Tekenen van de feature op de reverse strand.
                feat = draw.drawFeatures(feature,g,tag, y_cood_reverse);

                // Opslaan van de stop positie van de laaste feature om overlap te achterhalen.
                latest_stop_reverse = feat.getStop();

                // Ophalen van de hoogste y-coordinaat voor het tekenen van andere Features (bijv. mRNA na genen)
                y_cood_reverse_max = getMaxCoordinates(y_cood_reverse, y_cood_reverse_max);
            }else if(feature.getStrand().equals("+")){

                // Ophalen van de coordinaten op de template strand van de Feature.
                y_cood_forward = getCoordinates(feature, latest_stop_forward, list_ov_ft, y_cood_forward, feat);

                String tag = getTag(feature);

                feat = draw.drawFeatures(feature,g,tag, y_cood_forward);

                latest_stop_forward = feat.getStop();

                y_cood_forward_max = getMaxCoordinates(y_cood_forward, y_cood_forward_max);
            }
        }

        y_cood_forward = y_cood_forward_max + 20;
        y_cood_reverse = y_cood_reverse_max + 20;

        // Returnen van de coordinaten waarna verder getekent moet worden.
        newCoord.add(y_cood_reverse);
        newCoord.add(y_cood_forward);
        newCoord.add(y_cood_forward_max);
        newCoord.add(y_cood_reverse_max);

        return newCoord;
    }

    /**
     * Ophalen van de tags die in de features moeten komen te staan. Als de feature een gen is, wordt de locus_tag gevisualiseerd. Anders
     * wordt de naam van de feature uit de attributes opgehaald.
     * @param feature   De feature waarvan de tag opgehaald moet worden
     * @return          Een string met daarin de tag voor in de feature.
     */
    private String getTag(Feature feature){
        // Als de feature een gen is wordt de locus tag als tag opgeslagen, anders de naam van de feature.
        if(feature instanceof Gene){
            return (String) feature.getAttributes().get("locus_tag");
        }else{
            return (String) feature.getAttributes().get("Name");
        }
    }

    /**
     * Bepalen of er overlap is met features. y as coordinaten bepalen.
     *
     * @param curFeature    Huidige feature
     * @param latest_stop   De stop positie van de laatste feature
     * @param list_ov_ft    Lijst met features die mogelijk overlap veroorzaken
     * @param y_cood        y coordinaat waarna verder gegaan  moet worden
     * @param lastFeature   Vorige feature.
     * @return              Integer van de y as coordinaat.
     */
    private int getCoordinates(Feature curFeature, int latest_stop, HashMap<Feature, Integer> list_ov_ft, int y_cood, Feature lastFeature){
        // Bepalen of er overlap is van twee features: dan is de start van de huidige feature kleiner dan de stop van de laatste feature.
        if(curFeature.getStart() < latest_stop){
            // Als er overlap is wordt de vorige feature (object) opgeslagen in een hashmap met als value de y as coordinaat.
            list_ov_ft.put(lastFeature, y_cood);
            if(!list_ov_ft.isEmpty()){
                // Als de overlap lijst niet leeg is, wordt er gekeken met welke feature de huidige feature overlap heeft.
                for(Feature f:list_ov_ft.keySet()){
                    if(curFeature.getStart() < f.getStop()){
                        y_cood += (20/ list_ov_ft.size());
                    }else{
                        y_cood = list_ov_ft.get(f);
                    }
                }
            }else{
                y_cood += 20;
            }
        }else{
            y_cood += 0;
            list_ov_ft.clear();
        }

        return y_cood;
    }

    /**
     * Bepalen van de hoogste waarde op de y as.
     *
     * @param y_cood        Huidige y coordinaat
     * @param y_cood_max    Maximale y coordinaat die is getekent
     * @return              De hoogste y coordinaat
     */
    private int getMaxCoordinates(int y_cood, int y_cood_max){
        // Bepalen van de hoogste waarde van de y-coordinaat, vanaf daar wordt de volgende Feature getekend.
        if(y_cood > y_cood_max){
            return y_cood;
        }else{
            return y_cood_max;
        }
    }


}
