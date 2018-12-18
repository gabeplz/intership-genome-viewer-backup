package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.FastaFileChooser;
import com.mycompany.minorigv.FastaFileReader;
import com.mycompany.minorigv.gffparser.ORF;
import com.mycompany.minorigv.sequence.CodonTable;
import com.mycompany.minorigv.sequence.Strand;
import com.mycompany.minorigv.sequence.TranslationManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.*;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

/**
 * Class for building the Menu bar of the application
 */
public class IGVMenuBar extends JMenuBar {

	private Context cont;
	// Define all the items for the menu bar that will have sub items
	JMenu files, tools, features;

	// Define all the sub items for the menu bar that will not have their own sub items in the menu
	JMenuItem openRef, openData, saveORF, findORF, blast,Genes, mRNA ,Exon, Region, CDS, featureList;

	// List containing the features that the user wants to have visualized
	ArrayList<String> featureArray = new ArrayList<String>();

    JRadioButton buttonAll ,buttonBetween;
    JFormattedTextField textField;

	/**
	 * Initialize the de different menu items.
	 */
	public void init() {
		this.setPreferredSize(new Dimension(200, 25));
		this.setMinimumSize(new Dimension(100, 25));
		menus();
		featureMenu();
        condonTableMenu();
	}

	/**
	 * function creating the first 2 menus on the menu bar "files" and "tools"
	 */
	public void menus(){
	//first menu item "File"
		files = new JMenu("files");

	//sub items for menu item 1 "File"
		openRef = new JMenuItem("Load reference");
		openData = new JMenuItem("Load GFF");

	//action listeners for the sub item of File
		openRef.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadReferenceAction();
			}
		});
		openData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openDataAction();
			}
		});


	//add the sub items to "File"
		files.add(openRef);
		files.add(openData);

	//add the files to the menu bar
		add(files);

	//second menu item "tools"
		tools = new JMenu("ORF");

	//sub items for menu itm 2 "tools"
		findORF = new JMenuItem("Find ORFs");
		saveORF = new JMenuItem("Save ORFs");
		blast = new JMenuItem("Blast");

	//action listeners for the sub item of tools
		findORF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				findOrfAction();
			}
		});
		saveORF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveOrfAction();
			}
		});
		blast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				blastAction();
			}
		});


	//add the sub items to "tools"
		tools.add(findORF);
		tools.add(saveORF);
		tools.add(blast);

	//add tools to the menu bar
		add(tools);
	}

	/**
	 * function creating the third menu item for the menu bar "features"
	 */
	public void featureMenu(){
	// create the feature menu for the menu bar
		features = new JMenu("Features");

		featureList = new JMenuItem("Show features");
	// Checkboxes the contain the features that can be visualized
		Genes = new JCheckBoxMenuItem("Genes");
		mRNA = new JCheckBoxMenuItem("mRNA");
		Exon = new JCheckBoxMenuItem("Exon");
		Region = new JCheckBoxMenuItem("Region");
		CDS = new JCheckBoxMenuItem("CDS");

		featureList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				featureListAction();
			}
		});
	// add action listeners to the check boxes
		Genes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				genesAction();
			}
		});
		mRNA.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mrnaAction();
			}
		});
		Region.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				regionAction();
			}
		});
		Exon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exonAction();
			}
		});
		CDS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cdsAction();
			}
		});

	// add the checkboxes to the features menu

		features.add(featureList);
		features.add(Genes);
		features.add(mRNA);
		features.add(Exon);
		features.add(Region);
		features.add(CDS);

	// add the features menu to the menu bar
		add(features);

	// create booleans that will be true if check box is selected and false if not selected
		boolean m = mRNA.isSelected();
		boolean e = Exon.isSelected();
		boolean r = Region.isSelected();
		boolean c =CDS.isSelected();

	}

	// action performed for files and tools
	private void loadReferenceAction() {
		try{
			FastaFileChooser fasta = new FastaFileChooser();
			String path = fasta.fastafile();
			cont.addFasta(path);

		}catch (Exception e){}
	}
	private void openDataAction() {

		try{
			FastaFileChooser fasta = new FastaFileChooser();
			String path = fasta.fastafile();
			cont.addGFF(path);

		}catch (Exception e){}


	}

	/**
	 * Wanneer er op de button Save ORF wordt geklikt, komt er een pop-up die hier wordt aangemaakt.
	 * Deze pop-up bevat twee Radio Buttons (All en Between), een Label, een Textfield en een button (Save).
	 * In het Textfield kan de lengte ingevoerd worden die het ORF minimaal moet hebben in nucleotide.
	 * Bij de RadioButton kan er gekozen worden of gezocht wordt over het hele sequentie of tussen een bepaalde start en stop.
	 */
	private void saveOrfAction() {
		// Radio Button wordt aangemaakt.
		buttonAll = new JRadioButton("All",true);
		buttonBetween = new JRadioButton("Between");
		ButtonGroup groupRadioButton = new ButtonGroup();
		groupRadioButton.add(buttonAll); groupRadioButton.add(buttonBetween);
		// Label wordt aangemaakt.
		final JLabel labelLengthORFUser = new JLabel("Length ORF (nt): ", JLabel.LEFT);
		// Er kunnen geen letters ingevoerd worden, wanneer dit wel gebeurd wordt het vorige cijfer gebruikt.
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(true);
		textField = new JFormattedTextField(formatter);
		textField.setValue(100); // Strandaard lengte van het ORF.
		// Save button wordt aangemaakt.
		JButton saveButton = new JButton();
		saveButton.setText("Save");
		// Panel voor Radio Button, Label en Textfield wordt aangemaakt
		JPanel panel = new JPanel(new GridLayout(2,2));
        panel.add(buttonAll);panel.add(buttonBetween);panel.add(labelLengthORFUser);panel.add(textField);
		// Panel voor de Button wordt aangemaakt.
        JPanel panelForButton = new JPanel();
        panelForButton.add(saveButton);
		// Er wordt een Padding ingesteld.
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        // Er wordt een frame aangemaakt.
		JFrame f = new JFrame();
		f.getContentPane().setLayout(new BoxLayout(f.getContentPane(), BoxLayout.PAGE_AXIS));
		f.getContentPane().add(panel);
		f.getContentPane().add(panelForButton);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		// ActionListener voor de Save Button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				try {
					saveButtonAction();
					f.dispose();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
			}
        });
	}

	/**
	 * Wanneer er op de Save Button in het panel wordt geklikt, worden de gegevens opgehaald die op dat moment zijn ingevoerd.
	 * En wordt de functie saveORFs aangeroepen, om de ORFs in een file op te slaan.
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
    private void saveButtonAction() throws FileNotFoundException, UnsupportedEncodingException {
    	// haalt de ingevoerde lengte op van het ORF.
        int lengthORFUser = Integer.parseInt(textField.getValue().toString());
		// Set de ORFs
        cont.setCurORFListALL(lengthORFUser);
		// Kijkt welke Radio Button is aangeklikt.
        Boolean m = buttonAll.isSelected();
        if(m == true){
            cont.saveORFs(cont.getCurORFListALL());
        }else{
            cont.saveORFs(cont.getCurORFListBetween());
        }
    }

	/**
	 * Wanneer er op de button Find ORF gedrukt wordt, komt er een pop-up waarin de lengte van het ORF ingevuld kan worden (nt).
	 * Vervolgens worden de ORFs gezocht en gevisualiseerd.
	 */
    private void findOrfAction() {
		int lenghtORF = Integer.parseInt(JOptionPane.showInputDialog("Length ORF (nt)"));
		cont.setCurORFListALL(lenghtORF);
	}

	private void blastAction() {


	}

	private void featureListAction() {
    	PopUpFrame popup = new PopUpFrame(cont);
    }

	// action performed for the choose Feature menu
	// if check box is selected it will add the feature to a list that contains the features that should be visualized
	private void genesAction() {
		Boolean m = Genes.isSelected();
		if (m == true){
			featureArray.add("Gene");
		}else{
			featureArray.remove("Gene");
		}
        tellContext();
	}
	private void mrnaAction() {
		Boolean m = mRNA.isSelected();
		if (m == true){
			featureArray.add("mRNA");
		}else{
			featureArray.remove("mRNA");
		}

        tellContext();
	}
	private void regionAction() {
		Boolean m = Region.isSelected();
		if (m == true){
			featureArray.add("Region");
		}else{
			featureArray.remove("Region");
		}
        tellContext();
	}
	private void exonAction() {
		Boolean m = Exon.isSelected();
		if (m == true){
			featureArray.add("Exon");
		}else{
			featureArray.remove("Exon");
		}

        tellContext();
	}
	private void cdsAction() {
		Boolean m = CDS.isSelected();
		if (m == true){
			featureArray.add("CDS");
		}else{
			featureArray.remove("CDS");
		}

        tellContext();
	}

	/**
	 *
	 * @return Arraylist<Strings> bevat geslecteerde features.
	 */
	public ArrayList<String> getFeatureArray() {
		return featureArray;
	}

	public void tellContext(){
	    cont.setKeuze_gebruiker(this.featureArray);
    }

	public void setContext(Context cont) {
		this.cont = cont;
	}

    /**
     * maakt en voegt de translatie tabel menu aan de menubalk toe
     * gebruikt getCodonTableMenuItem om de items te genereren
     */
	public void condonTableMenu(){
	    JMenu transTableMenu = new JMenu("Translation Table");
		ButtonGroup group = new ButtonGroup();
        for (CodonTable codonTable : TranslationManager.getInstance().getAllCodonTables()) {
		    JMenuItem item = getCodonTableMenuItem(codonTable);
		    transTableMenu.add(item);
		    group.add(item);
	    }
        add(transTableMenu);
    }

    /**
     * maakt de menu items voor codonTableMenu
     * @param codonTable
     * @return
     */
	private JRadioButtonMenuItem getCodonTableMenuItem(CodonTable codonTable) {
		JRadioButtonMenuItem item = new JRadioButtonMenuItem();
		String fullName = codonTable.getNames()[0];
		String shortName = fullName;
		if (fullName.length() > 40) {
			shortName = fullName.substring(0, 37) + "...";
			item.setToolTipText(fullName);
		}
		item.setText(shortName);
		final Integer curKey = codonTable.getKey();
        item.setSelected(item.getText().contentEquals("Standard"));
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				cont.setCurrentCodonTable(curKey);


			}
		});
		return item;
	}


}
