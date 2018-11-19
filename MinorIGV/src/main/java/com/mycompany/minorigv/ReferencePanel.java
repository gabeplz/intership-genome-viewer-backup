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
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.RED);
		System.out.println(this.getSize());
		g2.drawString(seq, 0, 20);
		
	}

	public void init() {
		
		setPreferredSize(new Dimension(500,25));
		setMinimumSize(new Dimension(100,30));
		setBackground(new Color(180,180,180));
		
		
	}

}