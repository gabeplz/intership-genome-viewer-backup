package com.mycompany.minorigv.gui;

import java.awt.*;
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
 *
 * @author kahuub
 * Date: 20/11/18
 */

public class GenomePanel extends JPanel implements PropertyChangeListener {
    private Context cont;
    private JTextArea Organism;
    private JComboBox Chromosome;
    private JTextField Locus;
    private JButton ZoomIn;
    private JButton ZoomOut;
    private JButton Search;
    private int start;
    private int stop;

    /**
     * function building the Genomepanel in the application
     */
    public void init() {

        this.setBackground(Color.CYAN);
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(200, 50));
        makeTextAreas();
        makeZoomButtons();
        addElements();

    }

    /**
     * Function that adds all the elements to the Genomepanel
     */
    private void addElements() {
        this.add(Organism);
        this.add(Chromosome);
        this.add(Locus);
        this.add(ZoomIn);
        this.add(ZoomOut);
        this.add(Search);

    }

    /**
     * Convection user input to pc input by subtracting 1 so a user input of 1 will be seen by the pc as index 0.
     */

    private void parseInput() {
        String positions = Locus.getText();              // input van de user ophalen en spliten op "-" naar een start en stop
        String[] parts = positions.split("-");
        start = Integer.parseInt(parts[0]) - 1;         // converteren van user input naar index 0
        stop = Integer.parseInt(parts[1]) - 1;
    }

    /**
     * function creating the text area that will display the name of an organism who's dna sequence is shown on screen and the position of the DNA sequence the user is looking at
     */
    private void makeTextAreas() {

        Organism = new JTextArea(1, 20);
        Organism.setText("Organism");
        Chromosome = new JComboBox();
        Locus = new JTextField(20);
        Locus.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        Locus.setText("1-100");

        ActionListener chromosomeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeChromosome();
            }
        };

        Chromosome.addActionListener(chromosomeListener);

    }

    /**
     * Functie verantwoordelijk voor het veranderen van het huidige chromosoom.
     */
    private void changeChromosome() {
        try {
            cont.changeChromosome((String) Chromosome.getSelectedItem());

        } catch (Exception e) {
            System.out.println("Idk what went wrong");

        }

    }

    /**
     * Function creating the +, - and search zoom buttons
     */
    private void makeZoomButtons() {

        ZoomIn = new JButton("+");
        ZoomIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZoomInAction();
            }
        });

        ZoomOut = new JButton("-");
        ZoomOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZoomOutAction();
            }
        });

        Search = new JButton("Search");
        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchAction();
            }
        });

    }

    /**
     * Function changing the start and stop used in context with a start and stop chosen by the user
     */
    private void SearchAction() {
        parseInput();
        cont.changeSize(start, stop);
    }

    /**
     * ZoomInAction contains the logic behind the zoom in button
     */
    private void ZoomInAction() {
        parseInput();
        int length = stop - start;
        if (length > 10) {
            int scale = (int) Math.round(length * 0.1);
            start = start + scale +1;
            stop = stop - scale +1;

            cont.changeSize(start, stop);
        }
    }

    /**
     * ZoomOutAction contains the logic behind the zoom out button
     */
    private void ZoomOutAction() {
        parseInput();
        int length = stop - start;
        // TODO: 11/12/2018
        //if (length*0.1 <= cont.getLength()
        int scale = (int) Math.round(length * 0.1);
        start = start - scale +1;
        stop = stop + scale +1;
        if (start <= 0) {
            start = 0;
            }
        cont.changeSize(start, stop);
    }

    /**
     * change the context when a new chromosome is chosen when loading a new fasta file or a new range is selected
     * @param cont
     */

    public void setContext(Context cont) {
        this.cont = cont;
        cont.addPropertyChangeListener("chromosomeNameArray", this);
        cont.addPropertyChangeListener("range",this);
        

    }

    /**
     * set the correct start "-" stop for Locus
     */
    private void syncSize() {
    	this.Locus.setText((cont.getStart()+1)+"-"+(cont.getStop()+1));
    }

    /**
     * changeContext changes the chromosomes in the dropdown menu with new model containing chromosomes from set in context.java
     */
    public void changedContext() {
        DefaultComboBoxModel model = new DefaultComboBoxModel(cont.getChromosomeNames());
        Chromosome.setModel(model);

    }

    /**
     * function telling the application what to do when 1 of the 2 scenarios in setContext is met
     * @param evt
     */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if (evt.getPropertyName().equals("chromosomeNameArray")) {
			changedContext();
			syncSize();
		}
		else if(evt.getPropertyName().equals("range")) {
			syncSize();
		}	
	}
}
