package com.mycompany.minorigv.blast;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ColorORFs {

    //TODO verander naam functie
    public void evalue(HashMap<String, Double> headerEvalue, ArrayList<String> headerORFsNoHits){
        List<Double> evalues = new ArrayList<>(headerEvalue.values());
        HashMap<String, Color> headerColor = new HashMap<>();

        Collections.sort(evalues);

        int rangeGreenCol = 215/evalues.size();
        int count = 0;

        for (Double p : evalues) {
            System.out.println(p);
            for(String entry : headerEvalue.keySet()){
                if(p.equals(headerEvalue.get(entry))){
                    Color col = setColor(rangeGreenCol * count);
                    headerColor.put(entry, col);
                }
            }
            count++;
        }

        for(String header: headerORFsNoHits){
            headerColor.put(header, new Color(255, 255, 0));
        }

        System.out.println(headerColor);


    }

    public Color setColor(int index){
        Color col = new Color(0,255-index , 0);
        return col;
    }
}
