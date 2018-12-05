package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.gffparser.ORF;

import javax.swing.*;
import java.util.ArrayList;

public class markORF extends JPanel {

    Context cont;


    public void getORF(){
        ArrayList<ORF> listORF = new ArrayList<>();


        try {
            listORF = cont.getCurORFList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (ORF orf : listORF){
//            System.out.println(orf.getStart() + "  " + orf.getStop() + " " + orf.getReadingframe());
        }

    }


    public void init() {
        getORF();
    }

    public void setContext(Context context) {
        this.cont = context;
    }
}
