package com.mycompany.minorigv.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class ReferencePanel extends JPanel {

	//Hardcoded String ff want moeten nog Context objecten hebben
	String seq = "ATCGAATCGATCGATTCGATCGATCGATTCATCGATCGATCGATTCGATCGATTCGGTCGATCGATCGATCGATTCGATCGATTCGATTCG";
	String rev_seq = "ATCGAATCGATCGATTCGATCGATCGATTCATCGATCGATCGATTCGATCGATTCGGTCGATCGATCGATCGATTCGATCGATTCGATTCG";

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.RED);
		Font font = new Font("Monospaced", Font.PLAIN, 12);
		g2.setFont(font);

		// X start coordinaten voor het tekenen van de referentie sequenties.
		int n_x= 0;

		// Y start coordinatie voor het tekenen van de nucleotide uit de forward(n_y) en reverse(r_n_y) van de referentie sequentie.
		int n_y = 20;
		int r_n_y = 40;

		// Loop die forward en reverse strand nucleotide 1 voor 1 tekent.
		for (int i = 0; i<=seq.length()-1;i++) {

		// ophalen van de volgende nucleotide uit de forward en reverse sequentie.
			char seq_n = seq.charAt(i);
			char rev_seq_n = rev_seq.charAt(i);

			//coordinaten voor het teken van de nucleotide
			g2.drawString(String.valueOf(seq_n)		, n_x	, n_y);
			g2.drawString(String.valueOf(rev_seq_n)	, n_x	, r_n_y);

			//verplaatsen van de x coordinaten naar rechts zodat de getekende nucleotide samen een sequentie vormen.
			n_x += 12;



		}
		
	}
// initiatie van het paneel waarin de sequenties worden getekent
	public void init() {
		
		setPreferredSize(new Dimension(500,25));
		setMinimumSize(new Dimension(100,30));
		setBackground(new Color(180,180,180));
		
		
	}

}