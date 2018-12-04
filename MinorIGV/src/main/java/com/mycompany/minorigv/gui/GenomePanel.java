package com.mycompany.minorigv.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;


/**
 * Class voor interactie met de gebruiker ten behoeve van het verduidelijken welk gedeelte van de sequentie
 * door de gebruiker aanschouwt wordt.
 * @author kahuub
 * Date: 20/11/18
 */
public class GenomePanel extends JPanel implements PropertyChangeListener{
	private Context cont;
	private JTextArea Organism;
	private JComboBox Chromosome;
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
		Chromosome = new JComboBox();
		Locus = new JTextArea(1,20);
		//Locus.setText("0-100");

		ActionListener chromosomeListener = new ActionListener() {//add actionlistner to listen for change
			@Override
			public void actionPerformed(ActionEvent e) {
				changeChromosome();
			}
		};

		Chromosome.addActionListener(chromosomeListener);

	}
	
	private void changeText() {
		Locus.setText(cont.getStart() + "-" + cont.getStop());
	}

	private void changeChromosome()  {
		try {
			//cont.changeSize(0,2);
			cont.changeChromosome((String) Chromosome.getSelectedItem());
		}
		catch(Exception e){
			System.out.println("Idk what went wrong");

		}

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
		cont.addPropertyChangeListener("chromosomeNameArray",this);
		

	}

	public void changedContext(){
		DefaultComboBoxModel model = new DefaultComboBoxModel( cont.getChromosomeNames() );
		Chromosome.setModel( model );
		

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if (evt.getPropertyName().equals("chromosomeNameArray"))changedContext();
		if (evt.getPropertyName().equals("startStop")) changeText();
		
	}

}
