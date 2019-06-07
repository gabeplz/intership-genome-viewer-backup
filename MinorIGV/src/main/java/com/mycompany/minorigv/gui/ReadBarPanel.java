package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.sequence.MakeCompStrand;

import java.awt.*;
import java.beans.PropertyChangeListener;

public class ReadBarPanel extends IGVPanel implements PropertyChangeListener {
    public static Color thymineColor = new Color(128,0,255);
    public static Color adenineColor = new Color(255,0,0);
    public static Color cytosineColor = new Color(123, 215,0);
    public static Color guanineColor = new Color(0, 215, 215);
    public static Color matchColor = new Color(100, 100, 100);
    public static Color[] colourArray = { matchColor,adenineColor,thymineColor,cytosineColor,guanineColor};

    /**
     * Constructor
     *
     * @param context huidige context
     */
    public ReadBarPanel(Context context) {
        super();
        this.setContext(context);
        this.setListeners();
        this.init();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension dim = this.getSize();       // grootte dit Paneel.

        int[][] currentBarCovarage = cont.getCurrentBarCovarage();

        int width = (int) DrawingTools.calculateLetterWidth((int) dim.getWidth(), currentBarCovarage[0].length);

        if(width >= 1 ){
            drawBar(g,currentBarCovarage);  //Vierkantje teken functie
        }
        else {
            //Nog niks, gewoon vol negeren.
        }



    }

    /**
     *draws a bar graph
     *
     * @param g
     * @param currentBarCovarage
     */
    private void drawBar(Graphics g, int[][] currentBarCovarage ){
        Graphics2D g2 = (Graphics2D) g;
        int barLenght = currentBarCovarage[0].length;
        Dimension dim = this.getSize();
        System.out.println(this.getSize() + "readbar");
        int realWidth = (int) DrawingTools.calculateLetterWidth((int) dim.getWidth(), barLenght);  //bepaal de toegewezen breedte per pixel.

        int xPos = (int) DrawingTools.calculateLetterPosition( (int) dim.getWidth(), barLenght, -0.5); //linkerkant van rechthoekje, positie op de x-as.
        int oldXPos; //onthouden oude xPos.

 /**       System.out.println("____________");
        System.out.println(currentBarCovarage[0][0]);
        System.out.println(currentBarCovarage[1][0]);
        System.out.println(currentBarCovarage[2][0]);
        System.out.println(currentBarCovarage[3][0]);
        System.out.println(currentBarCovarage[4][0]);
        System.out.println((int) (dim.getHeightLayer()));**/
        for(int i = 0; i < barLenght; i++  ) {
            int heightModifier = 0; //

            oldXPos = xPos;
            xPos = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth(), barLenght, i + 0.5); //nieuwe xPos bepalen.

          //  System.out.println(oldXPos);
            //System.out.println(xPos);

            int width = xPos - oldXPos;  //breedte vierkantje

            if (realWidth > 2) {
                width = xPos - oldXPos - 1; //pixelbrede streep wit rechts.
            }

            int height = (int) (dim.getHeight());
            for (int row = currentBarCovarage.length -1; row > -1; row--) { // i want to draw the bar from the bottom up. so thats why the loop is inversed
                g.setColor(colourArray[row]);
                int val = currentBarCovarage[row][i];
                int scaledVal = (int)(val/cont.getCoverageReadsPerPixel());//cont.get
                heightModifier += scaledVal;
                g2.fillRect(oldXPos,height -heightModifier,width,scaledVal);



            }
        }
        g.setColor(Color.BLACK); //Zwarte kleur resetten.
       // g2.drawLine(0,0,0,20);
        String s = Integer.toString(50*cont.getCoverageReadsPerPixel());
        g2.drawString(s,3,10);
        g2.drawLine(0,0,0,50);
        g2.drawLine(0,0,2,0);
        g2.drawLine(0,49,2,49);

       // g2.drawLine(900,0,900,49);
    }

    public void init() {
        setPreferredSize(new Dimension(500,50));
        setMaximumSize(new Dimension(2000,40));
        setMinimumSize(new Dimension(100,30));
        setBackground(Color.WHITE);
    }

    @Override
    public void setListeners() {
        cont.addPropertyChangeListener("coverageReadsPerPixel", this);
        cont.addPropertyChangeListener("range", this);
        cont.addPropertyChangeListener("chromosome", this);
    }

}