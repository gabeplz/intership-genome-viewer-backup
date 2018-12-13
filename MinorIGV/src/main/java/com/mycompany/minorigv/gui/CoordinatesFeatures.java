package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.gffparser.*;
import com.mycompany.minorigv.sequence.Strand;
import com.sun.xml.internal.fastinfoset.sax.Features;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Coordinaten bepalen van elke feature, rekening gehouden met overlappende features.
 *
 * @author Huub en Amber Janssen Groesbeek
 */
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
    public void seperateFeatures(Feature[] featureFilteredList, Graphics g){
        // De coordinaten van de y as op de forward en reverse panel.
        // index 0: de y as coordinaat op reverse panel
        // index 1: de y as coordinaat op forward panel
        // index 2: de hoogste y as coordinaat op de forward panel
        // index 3: de hoogste y as coordinaat op de reverse panel
        ArrayList<Integer> newCoord = new ArrayList<>(Arrays.asList(4,4,0,0));

        // TODO : In het aanmaken van de featureFilteredList al een hashmap genereren.
        ArrayList<Feature> featureGene = new ArrayList<>();
        ArrayList<Feature> featuremRNA = new ArrayList<>();
        ArrayList<Feature> featureExon = new ArrayList<>();
        ArrayList<Feature> featureCDS = new ArrayList<>();
        ArrayList<Feature> featureRegion = new ArrayList<>();

        // Elke feature in een aparte lijst zetten
        for(Feature feat : featureFilteredList) {
            if (feat instanceof Gene) {
                featureGene.add(feat);
            } else if (feat instanceof mRNA) {
                featuremRNA.add(feat);
            } else if (feat instanceof Exon){
                featureExon.add(feat);
            } else if (feat instanceof CDS){
                featureCDS.add(feat);
            } else if (feat instanceof Region){
                featureRegion.add(feat);
            } else {
                // voor volledigheid
            }
        }

        // TODO : Als er een hashmap is gemaakt, dan de if statements aanpassen.
        // Als de lijsten niet leeg zijn, dan worden de coordinaten bepaald voor het mappen.
        if(!featureGene.isEmpty()){
            newCoord = setCoordinates(featureGene, g, newCoord.get(0), newCoord.get(1),newCoord.get(3),newCoord.get(2), new Color(255, 135, 12));
        }if(!featuremRNA.isEmpty()){
            newCoord = setCoordinates(featuremRNA, g, newCoord.get(0), newCoord.get(1), newCoord.get(3), newCoord.get(2), new Color(255, 229, 57));
        }if(!featureExon.isEmpty()){
            newCoord = setCoordinates(featureExon, g, newCoord.get(0), newCoord.get(1), newCoord.get(3), newCoord.get(2), new Color(255, 79, 35));
        }if(!featureCDS.isEmpty()){
            newCoord = setCoordinates(featureCDS, g, newCoord.get(0), newCoord.get(1), newCoord.get(3), newCoord.get(2),  new Color(140, 183, 255));
        }if(!featureRegion.isEmpty()){
            newCoord = setCoordinates(featureRegion, g, newCoord.get(0), newCoord.get(1), newCoord.get(3), newCoord.get(2),  new Color(150, 238, 75));

        }
    }

    /**
     * Coordinaten van elke feature worden bepaald en meegegeven aan een methode die de features tekent.
     * @param OneFeature            Een lijst met daarin alle objecten van één feature, bijvoorbeeld alle objecten van de feature Genes.
     * @param g                     Graphics
     * @param yCoodReverse          De y as coordinaat in de reverse panel
     * @param yCoodForward          De y as coordinaat in de forward panel
     * @param yCoodReverseMax       De hoogste y coordinaat van een getekende feature in de reverse panel
     * @param yCoodForwardMax       De hoogste y coordinaat van een getekende feature in de forward panel
     * @param col                   Kleur van een soort feature. Genes: Orange, mRNA: Blue.
     * @return  ArrayList met op index 1: yCoodReverse, index 2: yCoodForward, 3: yCoodForwardMax, 4: yCoodReverseMax.
     */
    public ArrayList<Integer> setCoordinates(ArrayList<Feature> OneFeature, Graphics g, int yCoodReverse, int yCoodForward, int yCoodReverseMax, int yCoodForwardMax, Color col){
        int latestStopReverse = 0;
        int latestStopForward = 0;
        HashMap<Feature, Integer> listOvFt = new HashMap<>();
        Feature feat = null;
        ArrayList<Integer> newCoord = new ArrayList<>();

        // Voor elke feature in een lijst met dezelfde features (bijv. alleen genen) wordt er gekeken op welke strand het voorkomt en wordt getekend.
        for(Feature feature: OneFeature){
            if (feature.getStrand().equals(Strand.NEGATIVE)){
                // Ophalen van de coordinaten op de reverse strand van de Feature.
                yCoodReverse = getCoordinates(feature, latestStopReverse, listOvFt, yCoodReverse, feat);

                // Ophalen tags
                String tag = getTag(feature);

                // Tekenen van de feature op de reverse strand.
                feat = draw.drawFeatures(feature,g,tag, yCoodReverse, col);

                // Opslaan van de stop positie van de laaste feature om overlap te achterhalen.
                latestStopReverse = feat.getStop();

                // Ophalen van de hoogste y-coordinaat voor het tekenen van andere Features (bijv. mRNA na genen)
                yCoodReverseMax = getMaxCoordinates(yCoodReverse, yCoodReverseMax);
            }else if(feature.getStrand().equals(Strand.POSITIVE)){
                // Ophalen van de coordinaten op de template strand van de Feature.
                yCoodForward = getCoordinates(feature, latestStopForward, listOvFt, yCoodForward, feat);

                String tag = getTag(feature);

                feat = draw.drawFeatures(feature,g,tag, yCoodForward, col);

                latestStopForward = feat.getStop();

                yCoodForwardMax = getMaxCoordinates(yCoodForward, yCoodForwardMax);
            }
        }

        yCoodForward = yCoodForwardMax + 20;
        yCoodReverse = yCoodReverseMax + 20;

        // Returnen van de coordinaten waarna verder getekent moet worden.
        newCoord.add(yCoodReverse);
        newCoord.add(yCoodForward);
        newCoord.add(yCoodForwardMax);
        newCoord.add(yCoodReverseMax);

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
        }else if (feature instanceof Exon){
            return (String) feature.getAttributes().get("gene");
        }else {
            return (String) feature.getAttributes().get("Name");
        }
    }

    /**
     * Bepalen of er overlap is met features. y as coordinaten bepalen.
     *
     * @param curFeature    Huidige feature
     * @param latestStop   De stop positie van de laatste feature
     * @param listOvFt    Lijst met features die mogelijk overlap veroorzaken
     * @param yCood        y coordinaat waarna verder gegaan  moet worden
     * @param lastFeature   Vorige feature.
     * @return              Integer van de y as coordinaat.
     */
    private int getCoordinates(Feature curFeature, int latestStop, HashMap<Feature, Integer> listOvFt, int yCood, Feature lastFeature){
        // Bepalen of er overlap is van twee features: dan is de start van de huidige feature kleiner dan de stop van de laatste feature.
        if(curFeature.getStart() < latestStop){
            // Als er overlap is wordt de vorige feature (object) opgeslagen in een hashmap met als value de y as coordinaat.
            listOvFt.put(lastFeature, yCood);
            // loopen over de overlappende features en achterhalen of er nog steeds overlapping plaatsvindt over die vorige features.
            for(Feature f:listOvFt.keySet()){
                if(curFeature.getStart() < f.getStop()){
                    yCood += (20/ listOvFt.size());
                }else{
                    yCood = listOvFt.get(f);
                }
            }
        }else{
            yCood += 0;
            listOvFt.clear();
        }
        return yCood;
    }

    /**
     * Bepalen van de hoogste waarde op de y as.
     *
     * @param yCood        Huidige y coordinaat
     * @param yCoodMax    Maximale y coordinaat die is getekent
     * @return              De hoogste y coordinaat
     */
    private int getMaxCoordinates(int yCood, int yCoodMax){
        // Bepalen van de hoogste waarde van de y-coordinaat, vanaf daar wordt de volgende Feature getekend.
        if(yCood > yCoodMax){
            return yCood;
        }else{
            return yCoodMax;
        }
    }
}
