package com.mycompany.minorigv.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


/**
 * JPanel voor de visualisatie van de verschillende reading frames passend bij het referentie genoom.
 * @author kahuub
 * Date: 20/11/18
 */
public class CodonPanel extends JPanel{

	Context cont;


	public void init() {
		this.setBackground(Color.ORANGE);
		setPreferredSize(new Dimension(500,75));
		setMaximumSize(new Dimension(2000,40));
		setMinimumSize(new Dimension(100,30));

	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		String seq = cont.getSubSequentie();

		for (int f = 0; f < 3; f++) {
			for (int i = f+1; i < seq.length()-1; i+=3) {
				int[] info = DrawingTools.calculateLetterPosition((int) this.getSize().getWidth(),seq.length(), i);
				int x_pos = info[1];
				int width = DrawingTools.calculateLetterPosition((int) this.getSize().getWidth(), seq.length(), i+1.5)[1];
				width -= DrawingTools.calculateLetterPosition((int) this.getSize().getWidth(), seq.length(),i-1.5)[1];
				if(i%2 > 0) {
					g.setColor(new Color(42, 112, 150));
				}
				else{
					g.setColor(new Color(127, 191, 226));
				}


				DrawingTools.drawFilledRect(g, x_pos, 20*(f+1),width+1, 20);
				g.setColor(Color.BLACK);

				DrawingTools.drawCenteredChar(g,seq.charAt(i),x_pos,20*(f+1));




			}

		}
	}


	public void setContext(Context cont) {
		this.cont = cont;
	}
}