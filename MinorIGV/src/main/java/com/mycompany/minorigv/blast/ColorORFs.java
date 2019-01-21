package com.mycompany.minorigv.blast;

import com.mycompany.minorigv.gffparser.BlastedORF;
import com.mycompany.minorigv.gffparser.Chromosome;
import com.mycompany.minorigv.gffparser.ORF;
import com.mycompany.minorigv.gui.Context;
import com.mycompany.minorigv.gui.Paths;
import com.mycompany.minorigv.sequence.Strand;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class ColorORFs extends JPanel {



    public static Choices colorSetting = Choices.IDENTITY;

    public ColorORFs() {

    }

    public ColorORFs(Context context) {
        super();
        cont = context;
        init();
    }

    public Choices getColorSetting() {
        return colorSetting;
    }

    public static void setColorSetting(Choices colorSetting) {
        colorSetting = colorSetting;
    }

    Context cont;

    public static Color[] colors = new Color[]{
            new Color(0,40,0),
            new Color(0,66,0),
            new Color(0,92,0),
            new Color(0,118,0),
            new Color(0,144,0),
            new Color(0,170,0),
            new Color(0,196,0),
            new Color(0,222,0),
            new Color(0,255,0),
            new Color(255,255,0)
    };

    public Color getColor(ORF orf){

        if (!(orf instanceof BlastedORF)){
            return colors[colors.length-1];
        }

        BlastedORF blastedOrf = (BlastedORF) orf;

        switch (colorSetting) {
            case IDENTITY:
                return pickColorIdentity(blastedOrf);
            case EVALUE:
                return pickColorEvalue(blastedOrf);
            case SCORE:
                return pickColorScore(blastedOrf);
            case BITSCORE:
                return pickColorBitScore(blastedOrf);
            default:
                return colors[colors.length-1];

        }
    }

    private Color pickColorIdentity(BlastedORF orf) {

        if (!orf.hasHit()){
            return colors[colors.length-1]; //geen hit dus geel
        }

        int identityScore = Integer.parseInt(orf.getBestHsp().getHspIdentity());

        if (identityScore >= 0 && identityScore < 50) {
            return colors[0];
        } else if (identityScore >= 50 && identityScore < 100) {
            return colors[1];
        } else if (identityScore >= 100 && identityScore < 200) {
            return colors[2];
        } else if (identityScore >= 200 && identityScore < 500) {
            return colors[3];
        } else if (identityScore >= 500 && identityScore < 1000) {
            return colors[4];
        } else if (identityScore >= 1000 && identityScore < 2000) {
            return colors[5];
        } else if (identityScore >= 2000 && identityScore < 4000) {
            return colors[6];
        } else if (identityScore >= 4000 && identityScore < 8000) {
            return colors[7];
        } else if (identityScore >= 8000) {
            return colors[8];
        }
        return colors[colors.length-1];

    }

    public Color pickColorEvalue(BlastedORF orf){

        if (!orf.hasHit()){
            return colors[colors.length-1]; //geen hit dus geel
        }

        Double evalue = Double.parseDouble(orf.getBestHsp().getHspEvalue());
        //Checken waartussen de e-value valt
        if (evalue <= 1.0E-4 && evalue > 1.0E-10) {
            return colors[0];
        } else if (evalue <= 1.0E-10 && evalue > 1.0E-20) {
            return colors[1];
        } else if (evalue <= 1.0E-20 && evalue > 1.0E-30) {
            return colors[2];
        } else if (evalue <= 1.0E-30 && evalue > 1.0E-50) {
            return colors[3];
        } else if (evalue <= 1.0E-50 && evalue > 1.0E-80) {
            return colors[4];
        } else if (evalue <= 1.0E-80 && evalue > 1.0E-100) {
            return colors[5];
        } else if (evalue <= 1.0E-100 && evalue > 1.0E-150) {
            return colors[6];
        } else if (evalue <= 1.0E-150 && evalue > 1.0E-200) {
            return colors[7];
        } else if (evalue <= 1.0E-200) {
            return colors[8];
        }
        return colors[colors.length-1];

    }

    public Color pickColorScore(BlastedORF orf){


        if (!orf.hasHit()){
            return colors[colors.length-1]; //geen hit dus geel
        }

        int score = Integer.parseInt(orf.getBestHsp().getHspScore());

        //Checken waartussen de score valt
        if (score >= 0 && score < 100) {
           return colors[0];
        } else if (score >= 100 && score < 200) {
            return colors[1];
        } else if (score >= 200 && score < 400) {
            return colors[2];
        } else if (score >= 400 && score < 800) {
            return colors[3];
        } else if (score >= 800 && score < 1000) {
            return colors[4];
        } else if (score >= 1000 && score < 2000) {
            return colors[5];
        } else if (score >= 2000 && score < 4000) {
            return colors[6];
        } else if (score >= 4000 && score < 8000) {
            return colors[7];
        } else if (score >= 8000) {
            return colors[8];
        }
        return colors[colors.length-1];

    }

    public Color pickColorBitScore(BlastedORF orf){

        if (!orf.hasHit()){
            return colors[colors.length-1]; //geen hit dus geel
        }

        int bitScore = Integer.parseInt(orf.getBestHsp().getHspBitScore());

        //Checken waartussen de bitScore valt
        if (bitScore >= 0 && bitScore < 200) {
            return colors[0];
        } else if (bitScore >= 200 && bitScore < 500) {
            return colors[1];
        } else if (bitScore >= 500 && bitScore < 1000) {
            return colors[2];
        } else if (bitScore >= 1000 && bitScore < 2000) {
            return colors[3];
        } else if (bitScore >= 2000 && bitScore < 3000) {
            return colors[4];
        } else if (bitScore >= 3000 && bitScore < 4000) {
            return colors[5];
        } else if (bitScore >= 4000 && bitScore < 5000) {
            return colors[6];
        } else if (bitScore >= 5000 && bitScore < 6000) {
            return colors[7];
        } else if (bitScore >= 6000) {
            return colors[8];
        }

        return colors[colors.length-1];
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("Identity", this.getWidth() / 2, 10);

        g.setColor(new Color(0, 40, 0));
        g.fillRect(10, 20, 25, 25);
        g.setColor(Color.BLACK);
        g.drawString("1-100", 50, 35);

        g.setColor(new Color(0, 66, 0));
        g.fillRect(10, 45, 25, 25);
        g.setColor(Color.BLACK);
        g.drawString("1-100", 50, 60);

        g.setColor(new Color(0, 92, 0));
        g.fillRect(10, 70, 25, 25);
        g.setColor(Color.BLACK);
        g.drawString("1-100", 50, 85);

        g.setColor(new Color(0, 118, 0));
        g.fillRect(10, 95, 25, 25);
        g.setColor(Color.BLACK);
        g.drawString("1-100", 50, 110);

        g.setColor(new Color(0, 144, 0));
        g.fillRect(10, 120, 25, 25);
        g.setColor(Color.BLACK);
        g.drawString("1-100", 50, 135);

        g.setColor(new Color(0, 170, 0));
        g.fillRect(10, 145, 25, 25);
        g.setColor(Color.BLACK);
        g.drawString("1-100", 50, 160);

        g.setColor(new Color(0, 196, 0));
        g.fillRect(10, 170, 25, 25);
        g.setColor(Color.BLACK);
        g.drawString("1-100", 50, 185);

        g.setColor(new Color(0, 222, 0));
        g.fillRect(10, 195, 25, 25);
        g.setColor(Color.BLACK);
        g.drawString("1-100", 50, 210);

        g.setColor(new Color(0, 255, 0));
        g.fillRect(10, 220, 25, 25);
        g.setColor(Color.BLACK);
        g.drawString("1-100", 50, 235);

        g.setColor(new Color(255, 255, 0));
        g.fillRect(10, 245, 25, 25);
        g.setColor(Color.BLACK);
        g.drawString("Geen hit", 50, 260);

    }

    /**
     * initiatie van het paneel waarin de sequenties worden getekent
     */
    public void init() {
        setPreferredSize(new Dimension(20, 300));
        setMaximumSize(new Dimension(20, 300));
        setMinimumSize(new Dimension(20, 30));
        setBackground(Color.WHITE);
    }


    public HashMap<ORF, Iteration> mapOrfBlast(BlastOutput bo) {

        HashMap<ORF, Iteration> mapje = new HashMap<>();

        String chromName = bo.getBlastOutputIterations().getIteration().get(0).getIterationQueryDef().split("\\|")[5].split(":")[1];
        Chromosome chrom = cont.getOrganism().getChromosome(chromName);

        for (Iteration iter : bo.getBlastOutputIterations().getIteration()) {

            ORF o = makeORF(iter);
            mapje.put(o, iter);

        }
        chrom.getListORF().clear();
        chrom.getListORF().addAll(mapje.keySet());
        return mapje;
    }

    public ORF makeORF(Iteration iter) {

        ORF orf = null;

        String header = iter.iterationQueryDef;
        String[] informationHeader = header.split("\\|");

        String id = (informationHeader[0]);
        int RF = Integer.parseInt(informationHeader[1].split(":")[1]);
        int startORF = Integer.parseInt(informationHeader[2].split(":")[1]);
        int stopORF = Integer.parseInt(informationHeader[3].split(":")[1]);
        String strandORF = informationHeader[4].split(":")[1];
        String chromName = informationHeader[5].split(":")[1];
        Strand strandORFenum = null;
        if (strandORF.equals("POSITIVE")) {
            strandORFenum = Strand.POSITIVE;
        } else if (strandORF.equals("NEGATIVE")) {
            strandORFenum = Strand.NEGATIVE;
        }

        orf = new ORF(startORF, stopORF, RF, id, strandORFenum, stopORF - startORF, chromName);

        return orf;
    }


}
