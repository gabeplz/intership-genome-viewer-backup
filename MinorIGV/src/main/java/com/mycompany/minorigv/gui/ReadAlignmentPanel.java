package com.mycompany.minorigv.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    public static Color ambiguousColor = new Color(60, 60, 60);
    //public static Color ambiguousColor = new Color(0, 20, 200);
    public static Color deletionColour = new Color(250, 250, 250);
    public static Color insertColour = new Color(250, 150, 0);
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
        if (width >= 1) {
            ding(g, cont.getCurrentSamReads());
        }
      //  else if (width > 1000) {

      //  }
        if (width < 1) {
            ding2(g, cont.getCurrentSamReads());
        }
        //ding(g);
    }

    private void ding2(Graphics g, ArrayList<SamRead> readList) {
        // private void ding(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        System.out.println(this.getSize());
        Dimension dim = this.getSize();
        cont.resetInsertTooltipsMap();
        cont.resetCurrentAreaReadMap();
        //SamRead tempSam = new SamRead();

        int length = cont.getLength();


        int realWidth = (int) DrawingTools.calculateLetterWidth((int) dim.getWidth() + 17, length);
        for (int i = 0; i < readList.size(); i++) {
            SamRead tempSam = readList.get(i);
            int refPositionStart = tempSam.getStart() - cont.getStart();
            int refPositionStop = refPositionStart + tempSam.getTotalLength();
            int drawingPositionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStart - 0.5);    // the + 17 is for the scrollbar. it should draw behind the bar
            int drawingPositionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStop - 0.5);
            g.setColor(matchColor);
            g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * (cont.getPixelHeightReads() + cont.getPixelSpaceBetweenReads()) + cont.getPixelSpaceBetweenReads(), drawingPositionStop - drawingPositionStart, cont.getPixelHeightReads());
        }}

    private void ding(Graphics g, ArrayList<SamRead> readList) {
        // private void ding(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        System.out.println(this.getSize());
        Dimension dim = this.getSize();
        cont.resetInsertTooltipsMap();
        cont.resetCurrentAreaReadMap();

        //SamRead tempSam = new SamRead();

        int length = cont.getLength();


        int realWidth = (int) DrawingTools.calculateLetterWidth((int) dim.getWidth() + 17, length);
        int xPos = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, -0.5); //linkerkant van rechthoekje, positie op de x-as.
        int oldXPos;


                /**
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

          **/
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
            boolean drawAllColous = tempSam.getDrawAllColoursBoolean();




            boolean isLast = false;
            for (int x = 0; x < cigarArraysLength; x++) {
                if (x == cigarArraysLength - 1) {
                    isLast = true;
                }
                if (tempCigarChar[x] == '=' && drawAllColous == false) {
                    int refPositionStart = start + referencePostionMod;
                    int refPositionStop = refPositionStart + tempCigarNumbers[x];

                    int drawingPositionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStart - 0.5);    // the + 17 is for the scrollbar. it should draw behind the bar
                    int drawingPositionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStop - 0.5);
                    if (isLast == true) {
                        drawingPositionStop -= 1;
                    }
                    g.setColor(matchColor);
                   // g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * 8, drawingPositionStop - drawingPositionStart, 2);
                    g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * (cont.getPixelHeightReads() + cont.getPixelSpaceBetweenReads())  + cont.getPixelSpaceBetweenReads(), drawingPositionStop - drawingPositionStart, cont.getPixelHeightReads());
                    // tempSam.getHeightLayer() * (height + space)
                    referencePostionMod += tempCigarNumbers[x];
                    positionInTempSeq += tempCigarNumbers[x];


                } else if (tempCigarChar[x] == '=' && drawAllColous == true) {
                    for (int n = 0; n < tempCigarNumbers[x]; n++) {
                        int refPositionStart = start + referencePostionMod;
                        int refPositionStop = refPositionStart + 1;

                        g.setColor(getColor( tempString.charAt(positionInTempSeq)));


                        int drawingPositionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStart - 0.5);    // the + 17 is for the scrollbar. it should draw behind the bar
                        int drawingPositionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStop - 0.5);
                        if (isLast == true && n == tempCigarNumbers[x] - 1) {
                            drawingPositionStop -= 1;
                        }
                        //  g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * 8, drawingPositionStop - drawingPositionStart, 3);
                        g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * (cont.getPixelHeightReads() + cont.getPixelSpaceBetweenReads())  + cont.getPixelSpaceBetweenReads(), drawingPositionStop - drawingPositionStart, cont.getPixelHeightReads());
                        referencePostionMod += 1;
                        positionInTempSeq += 1;


                    }

                } else if (tempCigarChar[x] == 'X') {
                    for (int n = 0; n < tempCigarNumbers[x]; n++) {
                        int refPositionStart = start + referencePostionMod;
                        int refPositionStop = refPositionStart + 1;

                        g.setColor(getColor( tempString.charAt(positionInTempSeq)));

                        int drawingPositionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStart - 0.5);    // the + 17 is for the scrollbar. it should draw behind the bar
                        int drawingPositionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStop - 0.5);
                        if (isLast == true && n == tempCigarNumbers[x] - 1) {
                            drawingPositionStop -= 1;
                        }
                      //  g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * 8, drawingPositionStop - drawingPositionStart, 3);
                        g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * (cont.getPixelHeightReads() + cont.getPixelSpaceBetweenReads())  + cont.getPixelSpaceBetweenReads(), drawingPositionStop - drawingPositionStart, cont.getPixelHeightReads());
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
                        System.out.println("D is last");

                    }
                    g.setColor(deletionColour);
                   // g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * 8, drawingPositionStop - drawingPositionStart, 2);
                    g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * (cont.getPixelHeightReads() + cont.getPixelSpaceBetweenReads())  + cont.getPixelSpaceBetweenReads(), drawingPositionStop - drawingPositionStart, cont.getPixelHeightReads());
                    referencePostionMod += tempCigarNumbers[x];

                } else if (tempCigarChar[x] == 'I') {
                    //     for (int n = 0; x < tempCigarNumbers[x]; n++) {
                    //     }
                    int refPositionStart = start + referencePostionMod ;
                    int refPositionStop = refPositionStart + 1;
                    int drawingPositionStart = 0;
                    int drawingPositionStop = 0;

                    if (realWidth != 1) {
                        drawingPositionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStart - 1);    // the + 17 is for the scrollbar. it should draw behind the bar
                        drawingPositionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStop - 1.5);

                        if (isLast == true) {
                            drawingPositionStop -= 1;
                            System.out.println("I is last");
                        }

                        g.setColor(insertColour);
                        //     g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * 8, drawingPositionStop - drawingPositionStart, 3);
                        g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * (cont.getPixelHeightReads() + cont.getPixelSpaceBetweenReads())  + cont.getPixelSpaceBetweenReads(), drawingPositionStop - drawingPositionStart, cont.getPixelHeightReads());
                        String insertString = tempString.substring(positionInTempSeq, positionInTempSeq + tempCigarNumbers[x]);
                        Rectangle inrect = new Rectangle(drawingPositionStart, tempSam.getHeightLayer() * (cont.getPixelHeightReads() + cont.getPixelSpaceBetweenReads())  + cont.getPixelSpaceBetweenReads(), drawingPositionStop - drawingPositionStart, cont.getPixelHeightReads());


                        // g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * (cont.getPixelHeightReads() + cont.getPixelSpaceBetweenReads())  + cont.getPixelSpaceBetweenReads(), drawingPositionStop - drawingPositionStart, cont.getPixelHeightReads());
                        cont.putIntoInsertTooltipsMap(inrect,insertString);
                        positionInTempSeq += tempCigarNumbers[x];


                    }else {
                        drawingPositionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStart - 1.5);    // the + 17 is for the scrollbar. it should draw behind the bar
                        drawingPositionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStop - 1.5);

                    if (isLast == true) {
                        drawingPositionStop -= 1;
                        System.out.println("I is last");
                    }

                    g.setColor(insertColour);
                    //     g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * 8, drawingPositionStop - drawingPositionStart, 3);
                    g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * (cont.getPixelHeightReads()  + cont.getPixelSpaceBetweenReads()) + cont.getPixelHeightReads() + cont.getPixelSpaceBetweenReads(), drawingPositionStop - drawingPositionStart, cont.getPixelSpaceBetweenReads());
                    String insertString = tempString.substring(positionInTempSeq, positionInTempSeq + tempCigarNumbers[x]);
                    Rectangle inrect = new Rectangle(drawingPositionStart, tempSam.getHeightLayer() * (cont.getPixelHeightReads() + cont.getPixelSpaceBetweenReads()) + cont.getPixelHeightReads()  + cont.getPixelSpaceBetweenReads(), drawingPositionStop - drawingPositionStart, cont.getPixelSpaceBetweenReads());


                    // g2.fillRect(drawingPositionStart, tempSam.getHeightLayer() * (cont.getPixelHeightReads() + cont.getPixelSpaceBetweenReads())  + cont.getPixelSpaceBetweenReads(), drawingPositionStop - drawingPositionStart, cont.getPixelHeightReads());
                    cont.putIntoInsertTooltipsMap(inrect,insertString);
                    positionInTempSeq += tempCigarNumbers[x];
                }




                } else if (tempCigarChar[x] == 'S'){
                    positionInTempSeq += tempCigarNumbers[x];
                } else if (tempCigarChar[x] == 'H'){
                // do nothing
            }


            }

            int refPositionStart = tempSam.getStart() - cont.getStart();
            int refPositionStop = refPositionStart + tempSam.getTotalLength();
            int drawingPositionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStart - 0.5);    // the + 17 is for the scrollbar. it should draw behind the bar
            int drawingPositionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 17, length, refPositionStop - 0.5);
            drawingPositionStop -= 1;
            Rectangle readRect = new Rectangle(drawingPositionStart, tempSam.getHeightLayer() * (cont.getPixelHeightReads() + cont.getPixelSpaceBetweenReads())  + cont.getPixelSpaceBetweenReads(), drawingPositionStop - drawingPositionStart, cont.getPixelHeightReads());
            cont.putIntoCurrentAreaReadMap(readRect, tempSam);
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
    /**    if (Character.toUpperCase(tempString.charAt(positionInTempSeq)) == 'A') {
            g.setColor(adenineColor);
        } else if (Character.toUpperCase(tempString.charAt(positionInTempSeq)) == 'T') {
            g.setColor(thymineColor);
        } else if (Character.toUpperCase(tempString.charAt(positionInTempSeq)) == 'C') {
            g.setColor(cytosineColor);
        } else if (Character.toUpperCase(tempString.charAt(positionInTempSeq)) == 'G') {
            g.setColor(guanineColor);
        }else if (Character.toUpperCase(tempString.charAt(positionInTempSeq)) == 'N'){
            g.setColor(ambiguousColor);
        } else {
            System.out.println("vreemd karakter");
            System.out.println(tempString.charAt(positionInTempSeq));
        }**/
    }
    public Color getColor(char ch) {
        switch (ch) {
            case 'a': return adenineColor;
            case 'A': return adenineColor;
            case 't': return thymineColor;
            case 'T': return thymineColor;
            case 'c': return cytosineColor;
            case 'C': return cytosineColor;
            case 'g': return guanineColor;
            case 'G': return guanineColor;
            case 'n': return ambiguousColor;
            case 'N': return ambiguousColor;

            default:
                System.out.println(ch);
                throw new IllegalArgumentException();
        }
    }

    // @Override
    // public String getToolTipText(MouseEvent event) {
    //     Point p = new Point(event.getX(), event.getY());

    public void init() {
        setPreferredSize(new Dimension(500, 50));
        setMaximumSize(new Dimension(2000, 40));
        setMinimumSize(new Dimension(100, 30));
        setBackground(Color.lightGray);


        //Create the popup menu.
        JPopupMenu popup = createPopupMenu();

        MyMouse myMouse = new MyMouse(popup);


        addMouseListener(myMouse);
        addMouseMotionListener(myMouse);

    }
    public JPopupMenu createPopupMenu() {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem menuItem1;
        JMenuItem menuItem2;
        JMenuItem menuItem3;
        JMenuItem menuItem4;

        menuItem1 = new JMenuItem("show colours");

        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cont.switchAllColors();
                cont.readAlignmentPanelPropertyChangeLauncer();



            }
        });
        popup.add(menuItem1);
        menuItem2 = new JMenuItem("set as select point 1");
        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cont.setSelectionPosition1();
                menuItem2.setText("set as select point 1 " + "[" +( cont.getSelectionPosition1()+1) + "]");

            }
        });
        popup.add(menuItem2);
        menuItem3 = new JMenuItem("set as select point 2");
        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cont.setSelectionPosition2();
                menuItem3.setText("set as select point 2 " + "[" + (cont.getSelectionPosition2() +1) + "]");

            }
        });
        popup.add(menuItem3);

        menuItem4 = new JMenuItem("add ref sequence between selection points to selected sequences");
        menuItem4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cont.addToSelectedSequncesFastaMap(cont.getSelectionPosition1(), cont.getSelectionPosition2());


            }
        });
        popup.add(menuItem4);

        return popup;

    }

    @Override
    public void setListeners() {
        //cont.addPropertyChangeListener("coverageReadsPerPixel", this);
        cont.addPropertyChangeListener("range", this);
        cont.addPropertyChangeListener("area", this);
        cont.addPropertyChangeListener("chromosome", this);
        cont.addPropertyChangeListener("pixelHeightReads", this);
        cont.addPropertyChangeListener("PixelSpaceBetweenReads", this);
        cont.addPropertyChangeListener("showReadColours",this);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // PixelSpaceBetweenReads pixelHeightReads
        if (evt.getPropertyName().equals("chromosome")) {
            cont.resetSelectionPositions();
        }

        if (evt.getPropertyName().equals("range") ||
                evt.getPropertyName().equals("chromosome") ||
                evt.getPropertyName().equals("area") ||
                evt.getPropertyName().equals("PixelSpaceBetweenReads") ||
                evt.getPropertyName().equals("pixelHeightReads") ||
                evt.getPropertyName().equals("showReadColours") ) {

        //    System.out.println("prop panel setcurrent reads start");
            cont.setCurrentSamReads();
       //     System.out.println("prop panel setcurrent reads stop");
     //       System.out.println("prop panel GETcurrent reads start");
         //   Dimension area = new Dimension(100, (8 * cont.getCurrentSamReadMaxHeight() + 8));
            Dimension area = new Dimension(100,   ((cont.getPixelHeightReads() + cont.getPixelSpaceBetweenReads()) * cont.getCurrentSamReadMaxHeight()) + cont.getPixelSpaceBetweenReads() * 2 + cont.getPixelHeightReads());
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

    //TODO 22 width is rand voor normale positie bepaling 21 zie je de volgende.
    class MyMouse extends MouseAdapter {
        JPopupMenu popup;
        Point p1 = null;
        Point p2 = null;
        MyMouse (JPopupMenu popupMenu){
            popup = popupMenu;
        }
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1){
                System.out.println("Left button clicked");
                p2 = e.getPoint();
                SamRead clickedread = cont.getSamReadForPoint(p2);
                if (clickedread != null){
                    clickedread.inverseDrawAllColoursBoolean();

                    cont.readAlignmentPanelPropertyChangeLauncer();
                }

            } else if (e.getButton() == MouseEvent.BUTTON2){
                System.out.println("Middle button clicked");
                p2 = e.getPoint();
                System.out.println(p2.x + "xpoint");
                System.out.println(p2.y + "ypoint");
                Object source = e.getSource();
                if(source instanceof ReadAlignmentPanel){
                    ReadAlignmentPanel panelPressed = (ReadAlignmentPanel) source;
                    Dimension dim = panelPressed.getSize();
                    System.out.println( (DrawingTools.calculatePixelPosition(p2.x, dim.getWidth() + 17, cont.getLength(), cont.getStart()) + 0.5 )  );
                    System.out.println((int) (DrawingTools.calculatePixelPosition(p2.x + 1, dim.getWidth() + 17, cont.getLength(), cont.getStart()) + 0.5 )  );
                }
                //

                System.out.println();
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                System.out.println("Right button clicked");
            //    cont.switchAllColors();
           //     cont.readAlignmentPanelPropertyChangeLauncer();
                p2 = e.getPoint();
                System.out.println(p2 + "point");
                System.out.println(p2.x + "xpoint");
                System.out.println(p2.y + "ypoint");
                checkpopup(e);


            }

            //pto(p1.toString());


        }
        private void checkpopup (MouseEvent e){
            if (e.isPopupTrigger())
            {
                Object source = e.getSource();
                if(source instanceof ReadAlignmentPanel){
                    ReadAlignmentPanel panelPressed = (ReadAlignmentPanel) source;
                    Dimension dim = panelPressed.getSize();
            //        System.out.println( (DrawingTools.calculatePixelPosition(p2.x, dim.getWidth() + 17, cont.getLength(), cont.getStart()) + 0.5 )  );
                    cont.setClickedRefPosition  ((int) (DrawingTools.calculatePixelPosition(p2.x + 1, dim.getWidth() + 17, cont.getLength(), cont.getStart()) + 0.5 )  ) ;



                System.out.println("checkPOPUPtrue");
                System.out.println(popup.getSubElements()[0]);

                JMenuItem a = new JMenuItem("A popup menu item"+ cont.getClickedRefPosition()+" 9");
               // popup.setLabel("A popup label"+ cont.getClickedRefPosition()+" 9");

                System.out.println( popup.getComponentCount());
                //popup.add("A popup menu item"+ cont.getClickedRefPosition()+" 9");
               // popup.remove(a);
                int realWidth = (int) DrawingTools.calculateLetterWidth((int) dim.getWidth() + 17, cont.getLength());
                    System.out.println("A"+realWidth);
                    System.out.println("A"+mathf(realWidth));
                    System.out.println("A"+p2.x);

                if (p2.x < mathf(realWidth)  ){
                    JLabel label = new JLabel("Position -");
                    if (popup.getComponentCount() == 4){
                        popup.getComponent(1).setEnabled(false);
                        popup.getComponent(2).setEnabled(false);
                        popup.insert(label, 0);
                    } else if(popup.getComponentCount() == 5){
                        popup.getComponent(2).setEnabled(false);
                        popup.getComponent(3).setEnabled(false);
                        popup.remove(0);
                        popup.insert(label, 0);
                    }
                    popup.show(e.getComponent(),
                            e.getX(), e.getY());
                } else {
                    JLabel label = new JLabel("Position "+ cont.getClickedRefPosition());
                    if (popup.getComponentCount() == 4){
                        popup.getComponent(1).setEnabled(true);
                        popup.getComponent(2).setEnabled(true);
                        popup.insert(label, 0);
                    } else if(popup.getComponentCount() == 5){
                        popup.getComponent(2).setEnabled(true);
                        popup.getComponent(3).setEnabled(true);
                        popup.remove(0);
                        popup.insert(label, 0);
                    }
                    popup.show(e.getComponent(),
                            e.getX(), e.getY());
                }
                  //  popup.show(e.getComponent(),
                  //          e.getX(), e.getY());
            }}
        }

        private int mathf(int x){
            int b = 11;

            int a = (int) b - (int)(0.5 * x);
            return a;
        }

        @Override
        public void mouseMoved (MouseEvent e) {
            p1 = e.getPoint();
            pto(p1, e);

        }
    }
}

