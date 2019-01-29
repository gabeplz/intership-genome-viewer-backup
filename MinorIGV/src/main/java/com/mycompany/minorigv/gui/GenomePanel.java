package com.mycompany.minorigv.gui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import com.mycompany.minorigv.gffparser.OrganismFiles;

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
//  private JTextArea organism;
    private JComboBox chromosome, organism;
    private JTextField locus;
    private JButton zoomIn;
    private JButton zoomOut;
    private JButton search;
    private int start;
    private int stop;

    public GenomePanel(Context context) {
        super();
        this.setContext(context);
        this.setListeners();
        this.init();
    }

    /**
     * function building the Genomepanel in the application
     */
    public void init() {
        this.setBackground(Color.GRAY);
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
        this.add(organism);
        this.add(chromosome);
        this.add(locus);
        this.add(zoomIn);
        this.add(zoomOut);
        this.add(search);

    }

    /**
     * Convection user input to pc input by subtracting 1 so a user input of 1 will be seen by the pc as index 0.
     */
    private void parseInput() {

        try {
            String positions = locus.getText();              // input van de user ophalen en spliten op "-" naar een start en stop

            if(!positions.matches("^([+-]?[0-9][0-9]*)-([+-]?[0-9][0-9]*)$")){
                ExceptionDialogs.ErrorDialog("Verkeerde invoer start-stop veld","foutieve invoer");
                return;
            }
            String[] parts = positions.split("-");
            int newStart = Integer.parseInt(parts[0]) - 1;         // converteren van user input naar index 0
            int newStop = Integer.parseInt(parts[1]) - 1;
            start = newStart;
            stop = newStop;


        } catch (Exception e) {
            ExceptionDialogs.ErrorDialog("Verkeerde invoer start-stop veld","foutieve invoer");
        }

    }

    /**
     * function creating the text area that will display the name of an organism who's dna sequence is shown on screen and the position of the DNA sequence the user is looking at
     */
    private void makeTextAreas() {
        // Maakt de ComboBox voor de organismen.
        organism = new JComboBox();
        // Het path waar de bestanden staan
        String pathNAS = cont.getPath(Paths.GENOMES);

        File f = new File(pathNAS);
        // Gecontroleerd of het path bestaat en of het een map is.
        if (f.exists() && f.isDirectory()) {
            String[] directories = f.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return new File(dir, name).isDirectory();
                }
            });
            // Alfabetische volgorde organisme namen en toevoegen aan ComboBox
            Arrays.sort(directories);
            organism.setModel(new DefaultComboBoxModel(directories));
            // Zorgen dat er geen organisme is geselecteerd in het begin.
            organism.setSelectedIndex(-1);

        } else {
            // Wanneer het path niet bestaat kan de gebruiker zelf nog files selecteren.
            organism.addItem("klik op files");
        }

        // Actionlistener om te kijken of er op een ander organisme wordt gedrukt.
        ActionListener organismListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeOrganism(pathNAS);
            }
        };
        organism.addActionListener(organismListener);


        chromosome = new JComboBox();
        locus = new JTextField(20);
        locus.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        locus.setText("1-101");


        locus.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){

                searchAction();

            }});

        ActionListener chromosomeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeChromosome();
            }
        };

        chromosome.addActionListener(chromosomeListener);
    }

    /**
     * Functie verantwoordelijk voor het veranderen van het huidige chromosoom.
     */
    private void changeChromosome() {
        try {
            cont.changeChromosome((String) chromosome.getSelectedItem());

        } catch (Exception e) {
            System.err.println("Error changing chromosome");

        }
    }

    /**
     * Kijken of het gekozen organisme is veranderd.
     * @param pathNAS
     */
    private void changeOrganism(String pathNAS) {
        try {
            // Kijkt welk organisme is gekozen.
            String name = (String) organism.getSelectedItem();
            OrganismFiles file = new OrganismFiles();
            file.getFiles(pathNAS, name);
            // Leest het fasta bestand in en het gff bestand.
            cont.addFasta(file.getFNAPath());
            cont.addGFF(file.getGFFPath());
        } catch (Exception e) {
            System.err.println("Error changing organism");
            e.printStackTrace();
        }

    }

    /**
     * Function creating the +, - and search zoom buttons
     */
    private void makeZoomButtons() {
        // TODO: 20/12/2018 disable zoom out while fasta is not loaded
        zoomIn = new JButton("+");
        zoomIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomInAction();
            }
        });

        zoomOut = new JButton("-");
        zoomOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomOutAction();
            }
        });

        search = new JButton("Search");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchAction();
            }
        });

    }

    /**
     * Function changing the start and stop used in context with a start and stop chosen by the user
     */
    private void searchAction() {
        parseInput();
        try {
            cont.changeSize(start, stop);
        }
        catch(Exception e){
            ExceptionDialogs.ErrorDialog(e.getMessage(),"error");
        }
    }

    /**
     * ZoomInAction contains the logic behind the zoom in button
     */
    private void zoomInAction() {
        parseInput();

        try {
            int length = stop - start;
            if (length > 10) {
                int scale = (int) Math.round(length * 0.1);
                start = start + scale;
                stop = stop - scale;
                cont.getFullLenght();
                cont.changeSize(start, stop);
            }
        } catch (IndexOutOfBoundsException e) {
            //e.printStackTrace();
            ExceptionDialogs.ErrorDialog(e.getMessage(),"error");


        } catch (NullPointerException e) {
            ExceptionDialogs.ErrorDialog("Geen sequentie ingelezen","error");

        }
    }

    /**
     * ZoomOutAction contains the logic behind the zoom out button
     */
    private void zoomOutAction() {
        parseInput();
        try {
            int length = stop - start;
            int scale = (int) Math.round(length * 0.1);
            start = start - scale;
            stop = stop + scale;
            if (start <= 0) {
                start = 0;
                }
            if (stop >= cont.getFullLenght()){
                stop = cont.getFullLenght();
            }
            cont.changeSize(start, stop);
        } catch (IndexOutOfBoundsException e) {
            //e.printStackTrace();
            ExceptionDialogs.ErrorDialog(e.getMessage(),"error");
        } catch (NullPointerException e) {
            ExceptionDialogs.ErrorDialog("Geen sequentie ingelezen","error");
        }
    }

    /**
     * change the context when a new chromosome is chosen when loading a new fasta file or a new range is selected
     * @param cont
     */
    public void setContext(Context cont) {
        this.cont = cont;

    }


    public void setListeners() {
        cont.addPropertyChangeListener("chromosomeNameArray", this);
        cont.addPropertyChangeListener("range",this);
    }

    /**
     * set the correct start "-" stop for Locus
     */
    private void syncSize() {

        if(cont == null || cont.getCurChromosome() == null || cont.getSequentie() == null){
            locus.setText("1-101");
        } else {
            this.locus.setText((cont.getStart() + 1) + "-" + (cont.getStop() + 1));
        }
    }

    /**
     * changeContext functie die het model update van het dropdown menu.
     */
    public void changedContext() {
        DefaultComboBoxModel model = new DefaultComboBoxModel(cont.getChromosomeNames());
        chromosome.setModel(model);

    }

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if (evt.getPropertyName().equals("chromosomeNameArray")) { //andere chromosomen
			changedContext();
			syncSize();
		}
		else if(evt.getPropertyName().equals("range")) { //andere view (start/stop)
			syncSize();
		}	
	}

	public String getOrganismBox(){
        return organism.getSelectedItem().toString();
    }
}
