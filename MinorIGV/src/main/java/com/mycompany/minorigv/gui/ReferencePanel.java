package com.mycompany.minorigv.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class ReferencePanel extends JPanel {

	//Hardcoded String ff want moeten nog Context objecten hebben
	Context cont;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if(cont == null) return;
		System.out.println("yeet");

		Graphics2D g2 = (Graphics2D) g;

		Dimension dim = this.getSize();
		String seq = cont.getSubSequentie();

		int length = cont.getStop()-cont.getStart();

		double disBetween = dim.getWidth() / length;
		String revComp = getReverseComplement(seq);
		for(int i = 0; i < length; i++  ) {
			int[] info = DrawingTools.calculateLetterPosition((int)dim.getWidth(), length, i);
			int x_pos = info[1];

			this.chooseLetterColor(g,seq.charAt(i));

			DrawingTools.drawCenteredChar(g2, seq.charAt(i), x_pos, 20);

			this.chooseLetterColor(g,revComp.charAt(i));
			DrawingTools.drawCenteredChar(g2, revComp.charAt(i), x_pos, 40);
			g.setColor(Color.BLACK);
		}

	}

	private void chooseLetterColor(Graphics g, char c) {

		switch (c) {
		case 'T':
			g.setColor(Color.BLUE);
			break;
		case 'A':
			g.setColor(Color.RED);
			break;
		case 'C':
			g.setColor(new Color(0 ,153, 0));
			break;
		case 'G':
			g.setColor(new Color(255 ,85, 0));
			break;
		case 't':
			g.setColor(Color.BLUE);
			break;
		case 'a':
			g.setColor(Color.RED);
			break;
		case 'c':
			g.setColor(new Color(0 ,153, 0));
			break;
		case 'g':
			g.setColor(new Color(255 ,85, 0));
			break;
		default:
			g.setColor(Color.BLACK);
		}

	}

	/**
	 * ripperino IGV
	 * @param sequence
	 * @return
	 */
	public static String getReverseComplement(String sequence) {
		char[] complement = new char[sequence.length()];
		int jj = 0;
		for (int ii = 0; ii < sequence.length(); ii++) {
			char c = sequence.charAt(ii);

			switch (c) {
			case 'T':
				complement[jj] = 'A';
				break;
			case 'A':
				complement[jj] = 'T';
				break;
			case 'C':
				complement[jj] = 'G';
				break;
			case 'G':
				complement[jj] = 'C';
				break;
			case 't':
				complement[jj] = 'a';
				break;
			case 'a':
				complement[jj] = 't';
				break;
			case 'c':
				complement[jj] = 'g';
				break;
			case 'g':
				complement[jj] = 'c';
				break;
			default:
				complement[jj] = c;
			}
			jj++;
		}
		return new String(complement);
	}
	// initiatie van het paneel waarin de sequenties worden getekent
	public void init() {

		setPreferredSize(new Dimension(500,55));
		setMaximumSize(new Dimension(2000,40));
		setMinimumSize(new Dimension(100,30));
		setBackground(Color.WHITE);
	}

	public void setContext(Context cont) {
		this.cont = cont;
	}
}