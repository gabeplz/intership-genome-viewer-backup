package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.FastaFileChooser;
import com.mycompany.minorigv.FastaFileReader;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Class for building the Menu bar of the application
 */
public class IGVMenuBar extends JMenuBar {

	private Context cont;
	// Define all the items for the menu bar that will have sub items
	JMenu Files, Tools, features;

	// Define all the sub items for the menu bar that will not have their own sub items in the menu
	JMenuItem  Open_ref,Open_data, Save_orf, Find_orf, Blast,Genes, mRNA ,Exon, Region, CDS;

	// List containing the features that the user wants to have visualized
	ArrayList<String> featureArray = new ArrayList<String>();

	/**
	 * init
	 */
	public void init() {
		this.setPreferredSize(new Dimension(200, 25));
		this.setMinimumSize(new Dimension(100, 25));
		Menus();
		featureMenu();
	}

	/**
	 * function creating the first 2 menus on the menu bar "Files" and "Tools"
	 */
	public void Menus(){
	//first menu item "File"
		Files = new JMenu("Files");

	//sub items for menu item 1 "File"
		Open_ref = new JMenuItem("Load reference");
		Open_data = new JMenuItem("Load Data");
		Save_orf = new JMenuItem("Save ORF's");

	//action listeners for the sub item of File
		Open_ref.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadReferenceAction();
			}
		});
		Open_data.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				OpenDataAction();
			}
		});
		Save_orf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SaveorfAction();
			}
		});

	//add the sub items to "File"
		Files.add(Open_ref);
		Files.add(Open_data);
		Files.add(Save_orf);

	//add the Files to the menu bar
		add(Files);

	//second menu item "Tools"
		Tools = new JMenu("Tools");

	//sub items for menu itm 2 "Tools"
		Find_orf = new JMenuItem("Find ORF");
		Blast = new JMenuItem("Blast");

	//action listeners for the sub item of Tools
		Find_orf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FindorfAction();
			}
		});
		Blast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BlastAction();
			}
		});

	//add the sub items to "Tools"
		Tools.add(Find_orf);
		Tools.add(Blast);

	//add Tools to the menu bar
		add(Tools);
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

	// action performed for Files and Tools
	private void loadReferenceAction() {
		try{
			FastaFileChooser fasta = new FastaFileChooser();
			String path = fasta.fastafile();
			cont.addFasta(path);

			System.out.println("load reference test");
		}catch (Exception e){}
	}
	private void OpenDataAction() {

		try{
			FastaFileChooser fasta = new FastaFileChooser();
			String path = fasta.fastafile();
			cont.addGFF(path);

			System.out.println("load reference test");
		}catch (Exception e){}


	}
	private void SaveorfAction() {
		System.out.println("Saving ORFs");
	}
	private void FindorfAction() {
		System.out.println("Finding ORFs");
	}
	private void BlastAction() {
		System.out.println("BLAST");
	}

	// action performed for the choose Feature menu
	// if check box is selected it will add the feature to a list that contains the features that should be visualized
	private void genesAction() {
		Boolean m = Genes.isSelected();
		if (m == true){
			featureArray.add("Genes");
		}else{
			featureArray.remove("Genes");
		}
		System.out.println(featureArray);
	}
	private void mrnaAction() {
		Boolean m = mRNA.isSelected();
		if (m == true){
			featureArray.add("mRNA");
		}else{
			featureArray.remove("mRNA");
		}
		System.out.println(featureArray);
	}
	private void regionAction() {
		Boolean m = Region.isSelected();
		if (m == true){
			featureArray.add("Region");
		}else{
			featureArray.remove("Region");
		}
		System.out.println(featureArray);
	}
	private void exonAction() {
		Boolean m = Exon.isSelected();
		if (m == true){
			featureArray.add("EXON");
		}else{
			featureArray.remove("EXON");
		}
		System.out.println(featureArray);
	}
	private void cdsAction() {
		Boolean m = CDS.isSelected();
		if (m == true){
			featureArray.add("CDS");
		}else{
			featureArray.remove("CDS");
		}
		System.out.println(featureArray);
	}

	/**
	 *
	 * @return
	 */
	public ArrayList<String> getFeatureArray() {
		return featureArray;
	}

	/**
	 *
	 * @param cont
	 */
	public void setContext(Context cont) {
		this.cont = cont;
	}

}
