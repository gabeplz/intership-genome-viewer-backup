package com.mycompany.minorigv.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private Context cont;
	private JTextArea Organism;
	private JTextArea Chromosome;
	private JTextArea Locus;
	private JButton ZoomIn;
	private JButton ZoomOut;
	private JButton Search;
	
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
		this.add(Search);
		
	}

	private void makeTextAreas() {
		
		Organism = new JTextArea(1,20);
		Organism.setText("Organism");
		Chromosome = new JTextArea(1,20);
		Chromosome.setText("Chromosome");
		Locus = new JTextArea(1,20);
		Locus.setText("15-30");
	}
	
	private void makeZoomButtons() {
		
		ZoomIn = new JButton("+");

		ZoomOut = new JButton("-");
		Search = new JButton("Search");
		Search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SearchAction();
			}
		});
		
	}
	private void SearchAction(){
		String positions = Locus.getText();
		String[] parts = positions.split("-");
		int start = Integer.parseInt(parts[0]);
		int stop = Integer.parseInt(parts[1]);
		cont.changeSize(start,stop);
	}
	public void setContext(Context cont) {
		this.cont = cont;

	}

}
