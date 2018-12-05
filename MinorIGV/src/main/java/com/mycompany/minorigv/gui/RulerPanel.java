package com.mycompany.minorigv.gui;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.print.DocFlavor;
import javax.swing.JPanel;

/**
 * builds and draws the panel with the ruler.
 */
public class RulerPanel extends JPanel implements PropertyChangeListener{

	Context conti;			//contains the start stop and length of the sequence on which the ruler will be fitted

	/**
	 * initializes the panel
	 */
	public void init() {

		this.setBackground(Color.lightGray);
		setPreferredSize(new Dimension(500,75));
		setMaximumSize(new Dimension(2000,40));
		setMinimumSize(new Dimension(100,30));

	}

	/**
	 * draws components in the panel
	 * calls on helper methods to generate the components
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int length = conti.getLength();
		int start = conti.getStart();
		int stop = conti.getStop();

		g.drawString(String.valueOf(length)+"bp",(int)(this.getSize().getWidth()/2),15); // draws the length in bp the middle of the panel
		g.fillRect(5,40,(int)(this.getSize().getWidth()-10),5);

		int stepSize = calculateStepSize(length);
		int first = (start - (start % stepSize)) + stepSize - 1;	//the value of first is equal to the position (in the sequence) of
																	//the first nucleotide above which a ruler line will be
																	// drawn

		for (int j = first; j < stop; j+= stepSize){

			int[] info = DrawingTools.calculateLetterPosition(this.getWidth(), length,Double.valueOf(j-start)); //scales the positions (in sequence) to the width of the panel
			int pos = info[1];
			g.drawLine(pos,40,pos,30);								// draws line on the ruler
			g.drawString(String.valueOf(j + 1) + "bp", pos, 30);		// draws the nucleotide position(in sequence) above the line


		}
	}

	/**
	 * calculates the stepSize
	 * @param length int
	 * @return stepSize int
	 */
	public static int calculateStepSize(int length){
		double lengte = Double.valueOf(length);
		int multiplier = 1;
		int[] options = {1,2,5};			// the base values of stepSize

		while((lengte / 10) > 10){			// determent's the multiplier for stepSize
			multiplier = multiplier * 10;
			lengte = lengte / 10;
		}
		for(int baseValue : options){				// loops trough options
			if(lengte / baseValue <= 20){			// determent's the baseValue
				return multiplier * baseValue;		// returns the StepSize
			}
		}

		return -1;
		//1-2-5-10-20-50-100-200-500-1000-2000-5000-10.000
	}

	/**
	 *
	 * @param conti set the context object for RulerPanel
	 */
	public void setContext(Context conti) {
		this.conti = conti;
		conti.addPropertyChangeListener("range", this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		this.invalidate();
		this.repaint();
		
	}

}
