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
 *
 * @author kahuub
 * Date: 20/11/18
 */

public class GenomePanel extends JPanel implements PropertyChangeListener {
    private Context cont;
    private JTextArea Organism;
    private JComboBox Chromosome;
    private JTextArea Locus;
    private JButton ZoomIn;
    private JButton ZoomOut;
    private JButton Search;
    private int start;
    private int stop;


    public void init() {

        this.setBackground(Color.CYAN);
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(200, 50));

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

    //Converteren user input naar pc input door middel van -1 zodat een user input can 1 door de pc word gezien als index0
    private void parseInput() {
        String positions = Locus.getText();              // input van de user ophalen en spliten op "-" naar een start en stop
        String[] parts = positions.split("-");
        start = Integer.parseInt(parts[0]) - 1;         // converteren van user input naar index 0
        stop = Integer.parseInt(parts[1]) - 1;
    }

    private void makeTextAreas() {

        Organism = new JTextArea(1, 20);
        Organism.setText("Organism");
        Chromosome = new JComboBox();
        Locus = new JTextArea(1, 20);
        Locus.setText("1-100");

        ActionListener chromosomeListener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                changeChromosome();
            }
        };

        Chromosome.addActionListener(chromosomeListener);

    }

    private void changeChromosome() {
        try {
            //cont.changeSize(0,2);
            cont.changeChromosome((String) Chromosome.getSelectedItem());

        } catch (Exception e) {
            System.out.println("Idk what went wrong");

        }

    }

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

    private void SearchAction() {
        parseInput();
        cont.changeSize(start, stop);
    }

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

    private void ZoomOutAction() {
        parseInput();
        int length = stop - start;
        //if (length*0.1 <= cont.getLength()
        int scale = (int) Math.round(length * 0.1);
        start = start - scale +1;
        stop = stop + scale +1;
        if (start <= 0) {
            start = 0;
            }
        cont.changeSize(start, stop);
    }

    public void setContext(Context cont) {
        this.cont = cont;
        cont.addPropertyChangeListener("chromosomeNameArray", this);
        cont.addPropertyChangeListener("range",this);
        

    }
    
    private void syncSize() {
    	this.Locus.setText((cont.getStart()+1)+"-"+(cont.getStop()+1));
    }

    public void changedContext() {
        DefaultComboBoxModel model = new DefaultComboBoxModel(cont.getChromosomeNames());
        Chromosome.setModel(model);

    }

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
