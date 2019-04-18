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
       // Graphics2D g2 = (Graphics2D) g;
        Dimension dim = this.getSize();       // grootte dit Paneel.
        int length = cont.getLength();        // lengte subsequentie.
        String seq = cont.getSubSequentie();  // huidige subSequentie
        int[][] currentBarCovarage = cont.getCurrentBarCovarage();
      //  System.out.println(cont.getLength());
       // System.out.println(currentBarCovarage[0].length);
        int width = (int) DrawingTools.calculateLetterWidth((int) dim.getWidth(), currentBarCovarage[0].length);

        if(width >= 1 ){
        drawBar(g,currentBarCovarage);  //Vierkantje teken functie
    }
        else {
        //Nog niks, gewoon vol negeren.
    }



    }
private void drawBar(Graphics g, int[][] currentBarCovarage ){
    Graphics2D g2 = (Graphics2D) g;
    int barLenght = currentBarCovarage[0].length;
    Dimension dim = this.getSize();
    int realWidth = (int) DrawingTools.calculateLetterWidth((int) dim.getWidth(), barLenght);  //bepaal de toegewezen breedte per pixel.

    int xPos = (int) DrawingTools.calculateLetterPosition( (int) dim.getWidth(), barLenght, -0.5); //linkerkant van rechthoekje, positie op de x-as.
    int oldXPos; //onthouden oude xPos.
    System.out.println("____________");
    System.out.println(currentBarCovarage[0][0]);
    System.out.println(currentBarCovarage[1][0]);
    System.out.println(currentBarCovarage[2][0]);
    System.out.println(currentBarCovarage[3][0]);
    System.out.println(currentBarCovarage[4][0]);
    System.out.println((int) (dim.getHeight()));
    for(int i = 0; i < barLenght; i++  ) {
        int heightModifier = 0;




            oldXPos = xPos;
            xPos = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth(), barLenght, i + 0.5); //nieuwe xPos bepalen.

            int width = xPos - oldXPos;  //breedte vierkantje

            if (realWidth > 2) {
                width = xPos - oldXPos - 1; //pixelbrede streep wit rechts.
            }

            int height = (int) (dim.getHeight());
          for (int row = currentBarCovarage.length -1; row > -1; row--) { // i want to draw the bar from the bottom up. so thats why the loop is inversed
            g.setColor(colourArray[row]);
            int val =currentBarCovarage[row][i];
            //cont.get
            heightModifier += val;
            g2.fillRect(oldXPos,height -heightModifier,width,val);

/**
            g.setColor(colourArray[4]);
            int val =currentBarCovarage[4][i];
            heightModifier += val;

        g2.fillRect(oldXPos,height -heightModifier,width,val);

            g.setColor(colourArray[3]);
        val =currentBarCovarage[3][i];
        heightModifier += val;
            g2.fillRect(oldXPos,height -heightModifier,width,val);

            g.setColor(colourArray[2]);
        val =currentBarCovarage[2][i];
        heightModifier += val;
            g2.fillRect(oldXPos,height -heightModifier,width,val);

            g.setColor(colourArray[1]);
        val =currentBarCovarage[1][i];
        heightModifier += val;
            g2.fillRect(oldXPos,height -heightModifier,width,val);

            g.setColor(colourArray[0]);
        val =currentBarCovarage[0][i];
        heightModifier += val;
            g2.fillRect(oldXPos,height -heightModifier,width,val);

**/
 //           g.setColor(colourArray[row]);
            //this.chooseLetterColor(g, 'A'); //letterkleur kiezen.

 //           heightModifier += currentBarCovarage[row][i]*10;

 //           g2.fillRect(oldXPos, height - heightModifier, width, currentBarCovarage[row][i]*10); //tekenen net rechthoekje.
            //g2.fillRect(oldXPos,height -20,width,20);

            //this.chooseLetterColor(g, 'T'); //letterkleur kiezen.

          //  g2.fillRect(oldXPos, height - 100, width, 20); //tekenen net rechthoekje.

            //this.chooseLetterColor(g, 'C'); //letterkleur kiezen.

           // g2.fillRect(oldXPos, height - 40, width, 20);
            //g2.fillRect(oldXPos,height -20,width,20);
            //           this.chooseLetterColor(g,revComp.charAt(j)); //letterkleur kiezen reverse.
            //         g2.fillRect(oldXPos,HEIGHT+HEIGHT/2,width,HEIGHT); //tekenen net rechthoekje.

            //g.setColor(Color.BLACK); //Zwarte kleur resetten.
        }
    }
}
    private void chooseLetterColor(Graphics g, char c) {
        //http://phrogz.net/css/distinct-colors.html
        switch (c) {
            case 'T':
                g.setColor(thymineColor);
                break;
            case 'A':
                g.setColor(adenineColor);
                break;
            case 'C':
                g.setColor(cytosineColor);
                break;
            case 'G':
                g.setColor(guanineColor);
                break;
            case 't':
                g.setColor(thymineColor);
                break;
            case 'a':
                g.setColor(adenineColor);
                break;
            case 'c':
                g.setColor(cytosineColor);
                break;
            case 'g':
                g.setColor(guanineColor);
                break;
            default:
                g.setColor(Color.BLACK);
        }

    }
    public void init() {
        this.setPreferredSize(new Dimension(100, 50));
        this.setMinimumSize(new Dimension(100, 50));
        this.setMaximumSize(new Dimension(100, 50));
        setBackground(Color.WHITE);
    }

    @Override
    public void setListeners() {
        cont.addPropertyChangeListener("nep", this);
        cont.addPropertyChangeListener("range", this);
        cont.addPropertyChangeListener("chromosome", this);
    }

}