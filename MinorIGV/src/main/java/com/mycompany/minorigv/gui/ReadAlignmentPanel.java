package com.mycompany.minorigv.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


public class ReadAlignmentPanel extends IGVPanel implements PropertyChangeListener {
    public static Color thymineColor = new Color(128, 0, 255);
    public static Color adenineColor = new Color(255, 0, 0);
    public static Color cytosineColor = new Color(123, 215, 0);
    public static Color guanineColor = new Color(0, 215, 215);
    public static Color matchColor = new Color(100, 100, 100);
    public int YSpaceBetweenReads;
    public int hightOfReads;


    public ReadAlignmentPanel(Context context) {
        super();
        this.setContext(context);
        ToolTipManager.sharedInstance().registerComponent(this);
        ToolTipManager.sharedInstance().setInitialDelay(250);
        this.setListeners();
        this.init();
       // this.setToolTipText("");


    }

    private void pto(Point p1, MouseEvent e){
    //    this.setToolTipText(s);

        this.getToolTipText(p1, e);
    }


    public String getToolTipText(Point p1, MouseEvent event) {
        String tooltiptext = cont.getTooltipForPoint(p1);
        if (tooltiptext != null) {

            setToolTipText(tooltiptext);
        } else {
            setToolTipText(null);
        }
        return super.getToolTipText(event);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension dim = this.getSize();

        int width = (int) DrawingTools.calculateLetterWidth((int) dim.getWidth(), cont.getLength());
        if (width >= 2) {
            ding(g, cont.getCurrentSamReads());
        }
        //ding(g);
    }

    private void ding(Graphics g, ArrayList<SamRead> readList) {
        // private void ding(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        System.out.println(this.getSize());
        Dimension dim = this.getSize();
        cont.resetInsertTooltipsMap();
        //SamRead tempSam = new SamRead();

        int length = cont.getLength();


        int realWidth = (int) DrawingTools.calculateLetterWidth((int) dim.getWidth() + 17, length);
        int xPos = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, -0.5); //linkerkant van rechthoekje, positie op de x-as.
        int oldXPos;
        for (int i = 0; i < length; i++) {

            oldXPos = xPos;
            xPos = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, i + 0.5); //nieuwe xPos bepalen.

            int width = xPos - oldXPos;  //breedte vierkantje

            if (realWidth > 2) {
                width = xPos - oldXPos - 1; //pixelbrede streep wit rechts.
            }
            g.setColor(Color.gray);

            g2.fillRect(oldXPos, 0, width, 1500);
        }


        /**
         g.setColor(Color.BLACK); //Zwarte kleur resetten.

         g2.drawLine(0,0,0,139);

         g2.drawLine(897,0,897,139);
         g2.drawLine(900,0,900,139);

         g.setColor(Color.pink);
         int start = 0 - cont.getStart();
         int stop =  11- cont.getStart();
         int positionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length , start-0.5);    // the + 17 is for the scrollbar. it should draw behind the bar
         int positionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() +17 , length, stop-0.5);
         g2.fillRect(positionStart, dim.height - (3*20), (positionStop-1) - positionStart, 300);

         g.setColor(Color.blue);
         start = 11 - cont.getStart();
         stop =  20 - cont.getStart();
         positionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length , start-0.5);    // the + 17 is for the scrollbar. it should draw behind the bar
         positionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() +17 , length, stop-0.5);
         g2.fillRect(positionStart, dim.height - (4*20), (positionStop-1) - positionStart, 300);

         g.setColor(Color.blue);
         start = 20 - cont.getStart();
         stop =  21 - cont.getStart();
         positionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length , start-0.5);    // the + 17 is for the scrollbar. it should draw behind the bar
         positionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() +17 , length, stop-0.5);
         if(realWidth > 2){
         positionStop -= 1;

         }
         g2.fillRect(positionStart, 0, positionStop - positionStart, 300);


         g.setColor(Color.blue);
         start = 37 - cont.getStart();
         stop =  40 - cont.getStart();
         positionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length , start-0.5);    // the + 17 is for the scrollbar. it should draw behind the bar
         positionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() +17 , length, stop-0.5);
         if(realWidth > 2){
         positionStop -= 1;

         }
         g2.fillRect(positionStart, 0, positionStop - positionStart, 300);

         g.setColor(Color.blue);
         start = 34 - cont.getStart();
         stop =  37 - cont.getStart();
         positionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length , start-0.5);    // the + 17 is for the scrollbar. it should draw behind the bar
         positionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() +17 , length, stop-0.5);
         if(realWidth > 2){
         positionStop -= 1;

         }
         g2.fillRect(positionStart, 0, positionStop - positionStart, 300);
         */
        System.out.println(realWidth);
/**
 g.setColor(Color.darkGray);

 for (int i = 0; i < readList.size(); i++) {
 SamRead tempSam = readList.get(i);
 char[] tempCigarChar = tempSam.getcigarChars();
 int[] tempCigarNumbers = tempSam.getcigarNumbers();
 int start = tempSam.getStart() - cont.getStart();

 int stop = tempSam.getStart() + tempSam.getTotalLength() - cont.getStart();

 int positionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, start - 0.5);    // the + 17 is for the scrollbar. it should draw behind the bar
 int positionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, stop - 0.5);
 if (realWidth > 2) {
 positionStop -= 1;
 }
 g2.fillRect(positionStart, tempSam.getHeightLayer() * 8, positionStop - positionStart, 5);
 }
 **/
        for (int i = 0; i < readList.size(); i++) {
            SamRead tempSam = readList.get(i);

            String tempString = tempSam.getSequence();
            // System.out.println(tempString);
            char[] tempCigarChar = tempSam.getcigarChars();
            int[] tempCigarNumbers = tempSam.getcigarNumbers();
            int cigarArraysLength = tempCigarChar.length;

            int start = tempSam.getStart() - cont.getStart();
            int referencePostionMod = 0;
            int positionInTempSeq = 0;

            boolean isLast = false;
            for (int x = 0; x < cigarArraysLength; x++) {
                if (x == cigarArraysLength - 1) {
                    isLast = true;
                }
                if (tempCigarChar[x] == '=') {
                    int refPositionStart = start + referencePostionMod;
                    int refPositionStop = refPositionStart + tempCigarNumbers[x];

                    int drawingPositionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStart - 0.5);    // the + 17 is for the scrollbar. it should draw behind the bar
                    int drawingPositionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStop - 0.5);
                    if (isLast == true) {
                        drawingPositionStop -= 1;
                    }
                    g.setColor(matchColor);
                    g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * 8, drawingPositionStop - drawingPositionStart, 2);
// tempSam.getHeightLayer() * (height + space)
                    referencePostionMod += tempCigarNumbers[x];
                    positionInTempSeq += tempCigarNumbers[x];
                } else if (tempCigarChar[x] == 'X') {
                    for (int n = 0; n < tempCigarNumbers[x]; n++) {
                        int refPositionStart = start + referencePostionMod;
                        int refPositionStop = refPositionStart + 1;

                        if (tempString.charAt(positionInTempSeq) == 'A') {
                            g.setColor(adenineColor);
                        } else if (tempString.charAt(positionInTempSeq) == 'T') {
                            g.setColor(thymineColor);
                        } else if (tempString.charAt(positionInTempSeq) == 'C') {
                            g.setColor(cytosineColor);
                        } else if (tempString.charAt(positionInTempSeq) == 'G') {
                            g.setColor(guanineColor);
                        } else {
                            System.out.println("vreemd karakter");
                        }

                        int drawingPositionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStart - 0.5);    // the + 17 is for the scrollbar. it should draw behind the bar
                        int drawingPositionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStop - 0.5);
                        if (isLast == true && n == tempCigarNumbers[x] - 1) {
                            drawingPositionStop -= 1;
                        }
                        g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * 8, drawingPositionStop - drawingPositionStart, 3);

                        referencePostionMod += 1;
                        positionInTempSeq += 1;


                    }
                } else if (tempCigarChar[x] == 'D') {
                    int refPositionStart = start + referencePostionMod;
                    int refPositionStop = refPositionStart + tempCigarNumbers[x];

                    int drawingPositionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStart - 0.5);    // the + 17 is for the scrollbar. it should draw behind the bar
                    int drawingPositionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStop - 0.5);
                    if (isLast == true) {
                        drawingPositionStop -= 1;
                    }
                    g.setColor(Color.black);
                    g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * 8, drawingPositionStop - drawingPositionStart, 2);

                    referencePostionMod += tempCigarNumbers[x];

                } else if (tempCigarChar[x] == 'I') {
                    //     for (int n = 0; x < tempCigarNumbers[x]; n++) {
                    //     }
                    int refPositionStart = start + referencePostionMod;
                    int refPositionStop = refPositionStart + 1;
                    int drawingPositionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStart - 1);    // the + 17 is for the scrollbar. it should draw behind the bar
                    int drawingPositionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStop - 1.5);

                    if (isLast == true) {
                        drawingPositionStop -= 1;
                    }

                    g.setColor(Color.orange);
                    g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * 8, drawingPositionStop - drawingPositionStart, 3);
                    String insertString = tempString.substring(positionInTempSeq, positionInTempSeq + tempCigarNumbers[x]);
                    Rectangle inrect = new Rectangle(drawingPositionStart, tempSam.getHeightLayer() * 8, drawingPositionStop - drawingPositionStart, 3);

                    cont.putIntoInsertTooltipsMap(inrect,insertString);
                    positionInTempSeq += tempCigarNumbers[x];
                }
            }

/**
 stop = tempSam.getStart() + tempSam.getTotalLength() - cont.getStart();

 positionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, start - 0.5);    // the + 17 is for the scrollbar. it should draw behind the bar
 positionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, stop - 0.5);
 if (realWidth > 2) {
 positionStop -= 1;
 }
 g2.fillRect(positionStart, tempSam.getHeightLayer() * 8, positionStop - positionStart, 5);
 **/
        }


        /**
         **/
        /**
         int positionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 15, length , start-0.5);    // the + 15 is for the scrollbar. it should draw behind the bar
         int positionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() +15 , length, stop-0.5);
         //g2.fillRect(positionStart, dim.height - (1*20), positionStop - positionStart, 15);
         // g2.fillRect(positionStart, dim.height - (2*20), positionStop - positionStart, 15);
         g2.fillRect(positionStart, dim.height - (3*20), positionStop - positionStart, 300);
         g2.fillRect(positionStart, dim.height - (4*20), positionStop - positionStart, 300);

         g.setColor(Color.blue);

         start = 2215 - cont.getStart();
         stop =  2216 - cont.getStart();
         length = cont.getLength();
         dim = this.getSize();
         positionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 15, length, start-0.5);    // the + 15 is for the scrollbar. it should draw behind the bar
         positionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() +15 , length, stop-0.5);


         //  g2.fillRect(positionStart, dim.height - (1*20), positionStop - positionStart, 15);
         g2.fillRect(positionStart, dim.height - (2*20), positionStop - positionStart, 1500);
         **/
    }

    public void change() {
    }

    // @Override
    // public String getToolTipText(MouseEvent event) {
    //     Point p = new Point(event.getX(), event.getY());

    public void init() {
        setPreferredSize(new Dimension(500, 50));
        setMaximumSize(new Dimension(2000, 40));
        setMinimumSize(new Dimension(100, 30));
        setBackground(Color.WHITE);

        MyMouse myMouse = new MyMouse();
        addMouseListener(myMouse);
        addMouseMotionListener(myMouse);

    }

    @Override
    public void setListeners() {
        //cont.addPropertyChangeListener("coverageReadsPerPixel", this);
        cont.addPropertyChangeListener("range", this);
        cont.addPropertyChangeListener("area", this);
        cont.addPropertyChangeListener("chromosome", this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("range") || evt.getPropertyName().equals("chromosome") || evt.getPropertyName().equals("area")) {
            System.out.println("prop panel setcurrent reads start");
            cont.setCurrentSamReads();
            System.out.println("prop panel setcurrent reads stop");
            System.out.println("prop panel GETcurrent reads start");
            Dimension area = new Dimension(100, (8 * cont.getCurrentSamReadMaxHeight() + 8));
            this.setPreferredSize(area);
            //this.invalidate();
            this.revalidate();
            this.repaint();


        } else if (evt.getPropertyName().equals("area")) { //andere view (start/stop)

            Dimension area = new Dimension(100, 200);
            this.setPreferredSize(area);
            //this.invalidate();
            this.revalidate();
            this.repaint();
        }
    }

    class MyMouse extends MouseAdapter {
        Point p1 = null;
        Point p2 = null;

        @Override
        public void mousePressed(MouseEvent e) {
            p2 = e.getPoint();
            System.out.println(p2 + "point");
            System.out.println(p2.x + "xpoint");
            System.out.println(p2.y + "ypoint");
            //pto(p1.toString());


        }
        @Override
        public void mouseMoved (MouseEvent e) {
            p1 = e.getPoint();
           // System.out.println(p1 + "point");
          //  System.out.println(p1.x + "xpoint");
          //  System.out.println(p1.y + "ypoint");
            pto(p1, e);

        }
    }
}

