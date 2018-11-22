package com.mycompany.minorigv;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;


/**
 * Class voor interactie met de gebruiker ten behoeve van het verduidelijken welk gedeelte van de sequentie
 * door de gebruiker aanschouwt wordt.
 * @author kahuub
 * Date: 20/11/18
 */
public class GenomePanel extends JPanel{
	
	private JTextArea Organism;
	private JTextArea Chromosome;
	private JTextArea Locus;
	private JButton ZoomIn;
	private JButton ZoomOut;
	
	public void init() {
		
		this.setBackground(Color.CYAN);
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(200,50));
		
		makeTextAreas();
		makeZoomButtons();
		addElements();
		
	}
	
	private void addElements() {
		this.add(Organism);
		this.add(Chromosome);
		this.add(Locus);
		this.add(ZoomIn);
		this.add(ZoomOut);
		
	}

	private void makeTextAreas() {
		
		Organism = new JTextArea(1,20);
		Organism.setText("Organism");
		Chromosome = new JTextArea(1,20);
		Chromosome.setText("Chromosome");
		Locus = new JTextArea(1,20);
		Locus.setText("Chr1:1024-2048");
	}
	
	private void makeZoomButtons() {
		
		ZoomIn = new JButton("+");
		ZoomOut = new JButton("-");
		
	}

}
