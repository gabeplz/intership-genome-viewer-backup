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
	Boolean forward;


	public void init(Boolean strand) {
		this.forward = strand;
		this.setBackground(Color.ORANGE);
		setPreferredSize(new Dimension(500,75));
		setMaximumSize(new Dimension(2000,40));
		setMinimumSize(new Dimension(100,30));

	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		String seq = cont.getSubSequentie();
		int start = cont.getStart();
		int stop = cont.getStop();
		seq = forward ? seq : ReferencePanel.getReverseComplement(seq);

		for (int f = 1; f < 4; f++) {
			for (int i = f; i < seq.length()-1; i+=3) {
				int j = forward ? i : seq.length()-i;
				int[] info = DrawingTools.calculateLetterPosition((int) this.getSize().getWidth(),seq.length(), j);
				int x_pos = info[1];
				int width = DrawingTools.calculateLetterPosition((int) this.getSize().getWidth(), seq.length(), j+1.5)[1];
				width -= DrawingTools.calculateLetterPosition((int) this.getSize().getWidth(), seq.length(),j-1.5)[1];
				if(j%2 > 0) {
					g.setColor(new Color(42, 112, 150));
				}
				else{
					g.setColor(new Color(127, 191, 226));
				}

				int y = (start + j) % 3;

				DrawingTools.drawFilledRect(g, x_pos, 20+20*(y),width+1, 20);
				g.setColor(Color.BLACK);

				DrawingTools.drawCenteredChar(g,seq.charAt(j),x_pos,20+20*(y));

			}

		}
	}


	public void setContext(Context cont) {
		this.cont = cont;
	}

	public void setForward(Boolean forward) {
		this.forward = forward;
	}
}