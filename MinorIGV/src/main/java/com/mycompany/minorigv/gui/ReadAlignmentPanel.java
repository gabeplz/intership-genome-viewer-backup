package com.mycompany.minorigv.gui;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ReadAlignmentPanel extends IGVPanel implements PropertyChangeListener {

   public ReadAlignmentPanel(Context context){
       super();
       this.setContext(context);
       this.setListeners();
       this.init();
   }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension dim = this.getSize();
        ding(g);
    }
    private void ding(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        System.out.println(this.getSize());

        g2.drawLine(10,49,20,49);
        System.out.println(this.getSize());
        g2.drawLine(10,49,2000,4900);
        System.out.println(this.getSize());
        g.setColor(Color.pink);
        g2.drawLine(882,49,882,490);
        int start = 0 - cont.getStart();
        int stop =  1- cont.getStart();
        int length = cont.getLength();
        Dimension dim = this.getSize();
        int positionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 15, length, start);    // the + 15 is for the scrollbar. it should draw behind the bar
        int positionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() +15 , length, stop);
        g2.fillRect(positionStart, dim.height - (1*20), positionStop - positionStart, 15);
        g2.fillRect(positionStart, dim.height - (2*20), positionStop - positionStart, 15);
        g2.fillRect(positionStart, dim.height - (3*20), positionStop - positionStart, 15);
        g2.fillRect(positionStart, dim.height - (4*20), positionStop - positionStart, 15);

        g.setColor(Color.blue);

         start = 1 - cont.getStart();
         stop =  2- cont.getStart();
         length = cont.getLength();
         dim = this.getSize();
         positionStart = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() + 15, length, start);    // the + 15 is for the scrollbar. it should draw behind the bar
         positionStop = (int) DrawingTools.calculateLetterPosition((int) dim.getWidth() +15 , length, stop);
        g2.fillRect(positionStart, dim.height - (1*20), positionStop - positionStart, 15);
        g2.fillRect(positionStart, dim.height - (2*20), positionStop - positionStart, 15);

    }
    public void change(){}
    public void init() {
        setPreferredSize(new Dimension(500,50));
        setMaximumSize(new Dimension(2000,40));
        setMinimumSize(new Dimension(100,30));
        setBackground(Color.WHITE);
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
       if (evt.getPropertyName().equals("range")||evt.getPropertyName().equals("chromosome")){
           System.out.println("range");
           //this.invalidate();
           this.revalidate();
           this.repaint();


        }
       else if(evt.getPropertyName().equals("area")) { //andere view (start/stop)
           System.out.println("area");
           Dimension area = new Dimension(100,100);
           this.setPreferredSize(area);
           //this.invalidate();
           this.revalidate();
           this.repaint();
       }
    }
}

