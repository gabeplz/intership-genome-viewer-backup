package com.mycompany.minorigv;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


/**
 * JPanel voor de visualisatie van de verschillende reading frames passend bij het referentie genoom.
 * @author kahuub
 * Date: 20/11/18
 */
public class CodonPanel extends JPanel{

	public void init() {
		this.setBackground(Color.ORANGE);
		
	}
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		DrawingTools.drawCenteredChar(g, 'r', 20, 20);
		
	}

}