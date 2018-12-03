package com.mycompany.minorigv.gui;

import java.awt.*;
import javax.print.DocFlavor;
import javax.swing.JPanel;

public class RulerPanel extends JPanel {

	Context conti;

	public void init() {

		this.setBackground(Color.lightGray);
		setPreferredSize(new Dimension(500,75));
		setMaximumSize(new Dimension(2000,40));
		setMinimumSize(new Dimension(100,30));

	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int length = conti.getLength();
		int start = conti.getStart();
		int stop = conti.getStop();

		g.drawString(String.valueOf(length)+"bp",(int)(this.getSize().getWidth()/2),15);
		g.fillRect(5,20,(int)(this.getSize().getWidth()-10),5);

		int stepSize = calculateStepSize(length);
		int first = (start - (start % stepSize)) + stepSize - 1;

		for (int j = first; j < stop; j+= stepSize){

			int[] info = DrawingTools.calculateLetterPosition(this.getWidth(), length,Double.valueOf(j-start));
			int pos = info[1];
			g.drawLine(pos,20,pos,0);

		}
	}

	public static int calculateStepSize(int length){
		double lengte = Double.valueOf(length);
		int stepSize = 1;
		while((lengte / 10) > 10){
			stepSize = stepSize * 10;
			lengte = lengte / 10;
		}
		int[] options = {1,2,5};

		for(int i : options){
			if(lengte / i <= 20){
				return stepSize * i;
			}
		}

		return -1;
		//1-2-5-10-20-50-100-200-500-1000-2000-5000-10.000
	}


	public void setContext(Context conti) {
		this.conti = conti;
	}

}
