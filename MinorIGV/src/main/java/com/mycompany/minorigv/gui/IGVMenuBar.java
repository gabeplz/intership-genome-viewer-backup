package com.mycompany.minorigv.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class IGVMenuBar extends JMenuBar {

	public void init()

	{
		
		this.setPreferredSize(new Dimension(200,25));
		this.setMinimumSize(new Dimension(100,25));

		// Define all the items for the menu bar that will have sub items
		JMenu Files, Tools;

		// Define all the sub items for the menu bar that will not have their own sub items in the
		JMenuItem  Open_ref,Open_data, Save_orf, Find_orf, Blast;


		// Create new item on the toolbar named Files
		Files = new JMenu("File");
		add(Files);

		// Create a new sub item under Files that will run an opening reference genomes function
		Open_ref = new JMenuItem("Load reference");
		Open_ref.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadReferenceAction();
			}
		});
		Files.add(Open_ref);

		// Create a new sub item under Files that will run a loading your data function
		Open_data = new JMenuItem("Load Data");
		Open_data.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				OpenDataAction();
			}
		});
		Files.add(Open_data);

		// Create a new sub item under Files that will run a saving your ORF data function
		Save_orf = new JMenuItem("Save ORF's");
		Save_orf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SaveorfAction();
			}
		});
		Files.add(Save_orf);

		// Create new item on the toolbar named Tools
		Tools = new JMenu("Tools");
		add(Tools);

		// create new sub item under Tools that will run the find orf function
		Find_orf = new JMenuItem("Find ORF");
		Find_orf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FindorfAction();
			}
		});
		Tools.add(Find_orf);

		// create new sub item under Tools that will run the BLAST function
		Blast = new JMenuItem("Blast");
		Blast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BlastAction();
			}
		});
		Tools.add(Blast);

	}
	// test button action listener
	private void loadReferenceAction() {

		System.out.println("load reference test");
	}
	private void OpenDataAction() {
		System.out.println("load data test");
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

}
