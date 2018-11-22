package com.mycompany.minorigv;

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
		//System.out.println(this.getSize());

		int n_x= 0;
		int n_y = 20;
		int r_n_y = 40;

		for (int i = 0; i<=seq.length()-1;i++) {
			//System.out.println(i);

			char seq_n = seq.charAt(i);
			char rev_seq_n = rev_seq.charAt(i);


			g2.drawString(String.valueOf(seq_n)		, n_x	, n_y);
			g2.drawString(String.valueOf(rev_seq_n)	, n_x	, r_n_y);

			n_x += 12;



		}
		
	}

	public void init() {
		
		setPreferredSize(new Dimension(500,25));
		setMinimumSize(new Dimension(100,30));
		setBackground(new Color(180,180,180));
		
		
	}

}