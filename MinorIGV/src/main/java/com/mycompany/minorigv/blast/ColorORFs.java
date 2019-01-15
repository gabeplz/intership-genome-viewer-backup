package com.mycompany.minorigv.blast;

import com.mycompany.minorigv.gui.CodonPanel;
import com.mycompany.minorigv.sequence.Strand;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ColorORFs {

    public ColorORFs(){

    }

    public HashMap<String, Color> headerColor = new HashMap<>();

    //TODO verander naam functie
    public void evalue(HashMap<String, Double> headerEvalue, ArrayList<String> headerORFsNoHits){
        List<Double> evalues = new ArrayList<>(headerEvalue.values());


        Collections.sort(evalues);

        int rangeGreenCol = 215/evalues.size();
        int count = 0;

        for (Double p : evalues) {
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
        setHeaderColor(headerColor);
    }

    public Color setColor(int index){
        Color col = new Color(0,255-index , 0);
        return col;
    }

    public void setHeaderColor(HashMap<String, Color> headerColor){
        this.headerColor = headerColor;
    }

    public HashMap<String, Color> getHeaderColor(){
        return headerColor;
    }
}
