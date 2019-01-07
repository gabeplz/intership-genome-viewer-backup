package com.mycompany.minorigv.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.print.DocFlavor;
import javax.swing.JPanel;

/**
 * bouwt en tekend de panel met de liniaal
 */
public class RulerPanel extends JPanel implements PropertyChangeListener{

	Context conti;			//bevat de start stop and lengte van de sequentie waarop de ruler uitgelijnd word
    int x;                  //linkerkant selectie rechthoek
    int x2;                 //rechterkant selectie rechthoek



	/**
	 * initializes the panel
	 */
	public void init() {
		this.setBackground(Color.lightGray);
		setPreferredSize(new Dimension(500,75));
		setMaximumSize(new Dimension(2000,40));
		setMinimumSize(new Dimension(100,30));

		x = x2 = 0; //selectie rechthoek
        MyMouseListener listener = new MyMouseListener(); //luisteren naar de muis
        addMouseListener(listener);
        addMouseMotionListener(listener);

	}

	/**
	 * tekend componenten in de panel
	 * roept helper methods aan om de components te genereren
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int length = conti.getLength();
		int start = conti.getStart();
		int stop = conti.getStop();

		g.drawString(String.valueOf(length)+"bp",(int)(this.getSize().getWidth()/2),15); // tekend de lengte in baseparen in het midden van de panel
		g.fillRect(5,40,(int)(this.getSize().getWidth()-10),5);

		int stepSize = calculateStepSize(length);
		int first = (start - (start % stepSize)) + stepSize - 1;	// de waarde van first is gelijk aan de positie in de sequentie van de
																	// eerste nucleotide waarboven de eerste ruler lijn getekend word

		for (int j = first; j < stop; j+= stepSize){


			int pos = (int) DrawingTools.calculateLetterPosition(this.getWidth(), length,Double.valueOf(j-start)); //schaald de posities in sequentie naar de breedte van de panel
			g.drawLine(pos,40,pos,30);								// draws line on the ruler
			g.drawString(String.valueOf(j + 1) + "bp", pos, 30);		// draws the nucleotide position(in sequence) above the line


		}


        int alpha = 127; // 50% transparent
        Color myColour = new Color(255, 0, 0, alpha);
		g.setColor(myColour);
		g.fillRect(x,0,x2-x,getHeight());  //selectie rechthoek

	}



	/**
	 * calculates the stepSize
	 * @param length int
	 * @return stepSize int
	 */
	public static int calculateStepSize(int length){
		double lengte = Double.valueOf(length);
		int multiplier = 1;
		int[] options = {1,2,5};			// de basis waardes van stepSize

		while((lengte / 10) > 10){			// bepaald de vermenigvuldiger voor stepSize
			multiplier = multiplier * 10;
			lengte = lengte / 10;
		}
		for(int baseValue : options){				// loops door options
			if(lengte / baseValue <= 20){			// bepaald welke basis waarde als baseValue gebruikt word
				return multiplier * baseValue;		// returns de StepSize
			}
		}

		//1-2-5-10-20-50-100-200-500-1000-2000-5000-10000
		return -1;
	}

	/**
	 * set de context object voor RulerPanel en voegt een PropertyChangeListener toe aan context
	 * @param conti
	 */
	public void setContext(Context conti) {
		this.conti = conti;
		conti.addPropertyChangeListener("range", this); // luisterd of in context de functie firePropertyChange met als topic: "range", word uitgevoerd.
	}

	/**
	 * overrides propery change
	 * hertekend het paneel
	 * word geactiveerd door de PropertyChangeListener in RulerPanel.setContext()
	 * @param arg0
	 */
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		this.invalidate();
		this.repaint();
		
	}

    /**
     * Start van selectie rechthoek instellen.
     * @param x
     */
    public void setStartPoint(int x) {
        this.x = x;

    }

    /**
     * stop van selectie rechthoek instellen.
     * @param x
     */
    public void setEndPoint(int x) {
        x2 = (x);

    }

    /**
     * functie die het zoomen doet op mouse release.
     */
    private void dragZoom() {

	    int width = this.getWidth();

	    int start = conti.getStart();
	    int stop = conti.getStop();
        double amount = stop-start;

	    int newStart = (int) (((double)x / (double)width) * amount) + start;
        int newStop =  (int) (((double)x2 / (double)width) * amount) + start;
        x = x2 = 0;
        conti.changeSize(newStart,newStop);

    }

    /**
     * functie die repaint spamt tijdens het bewegen. //TODO bufferen.
     */
    private void dragPaint() {

	    //buffer die image ooit
	    repaint();
    }

    /**
     * Hulper class MouseListener voor de selectie
     */
    class MyMouseListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            setStartPoint(e.getX());
        }

        public void mouseDragged(MouseEvent e) {
            setEndPoint(e.getX());
            dragPaint();
        }

        public void mouseReleased(MouseEvent e) {
            setEndPoint(e.getX());
            dragZoom();

        }
    }

}