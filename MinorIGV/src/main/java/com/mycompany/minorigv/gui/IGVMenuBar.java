package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.FastaFileChooser;
import com.mycompany.minorigv.sequence.CodonTable;
import com.mycompany.minorigv.sequence.TranslationManager;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
		openData = new JMenuItem("Load Data");
		saveORF = new JMenuItem("Save ORF's");

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
		saveORF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveOrfAction();
			}
		});

	//add the sub items to "File"
		files.add(openRef);
		files.add(openData);
		files.add(saveORF);

	//add the files to the menu bar
		add(files);

	//second menu item "tools"
		tools = new JMenu("tools");

	//sub items for menu itm 2 "tools"
		findORF = new JMenuItem("Find ORF");
		blast = new JMenuItem("blast");
		featureList = new JMenuItem("Show features");

	//action listeners for the sub item of tools
		findORF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				findOrfAction();
			}
		});
		blast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				blastAction();
			}
		});
		featureList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				featureListAction();
			}
		});

	//add the sub items to "tools"
		tools.add(findORF);
		tools.add(blast);
		tools.add(featureList);

	//add tools to the menu bar
		add(tools);
	}

	/**
	 * function creating the third menu item for the menu bar "features"
	 */
	public void featureMenu(){
	// create the feature menu for the menu bar
		features = new JMenu("Features");

	// Checkboxes the contain the features that can be visualized
		Genes = new JCheckBoxMenuItem("Genes");
		mRNA = new JCheckBoxMenuItem("mRNA");
		Exon = new JCheckBoxMenuItem("Exon");
		Region = new JCheckBoxMenuItem("Region");
		CDS = new JCheckBoxMenuItem("CDS");

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
	private void saveOrfAction() {

	}
	private void findOrfAction() {

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
