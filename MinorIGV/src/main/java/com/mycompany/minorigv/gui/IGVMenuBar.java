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
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

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
	JMenu Files, Tools, features;

	// Define all the sub items for the menu bar that will not have their own sub items in the menu
	JMenuItem  Open_ref,Open_data, Save_orf, Find_orf, Blast,Genes, mRNA ,Exon, Region, CDS;

	// List containing the features that the user wants to have visualized
	ArrayList<String> featureArray = new ArrayList<String>();

    JRadioButton buttonAll ,buttonBetween;
    JFormattedTextField textField;

	/**
	 * init
	 */
	public void init() {
		this.setPreferredSize(new Dimension(200, 25));
		this.setMinimumSize(new Dimension(100, 25));
		Menus();
		featureMenu();
        condonTableMenu();
	}

	/**
	 * function creating the first 2 menus on the menu bar "Files" and "Tools"
	 */
	public void Menus(){
	//first menu item "File"
		Files = new JMenu("Files");

	//sub items for menu item 1 "File"
		Open_ref = new JMenuItem("Load reference");
		Open_data = new JMenuItem("Load GFF");

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


	//add the sub items to "File"
		Files.add(Open_ref);
		Files.add(Open_data);

	//add the Files to the menu bar
		add(Files);

	//second menu item "Tools"
		Tools = new JMenu("ORF");

	//sub items for menu itm 2 "Tools"
		Find_orf = new JMenuItem("Find ORF");
        Save_orf = new JMenuItem("Save ORF's");
		Blast = new JMenuItem("Blast");

	//action listeners for the sub item of Tools
		Find_orf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FindorfAction();
			}
		});
        Save_orf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveorfAction();
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
		Tools.add(Save_orf);
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

		}catch (Exception e){}
	}
	private void OpenDataAction() {

		try{
			FastaFileChooser fasta = new FastaFileChooser();
			String path = fasta.fastafile();
			cont.addGFF(path);

		}catch (Exception e){}


	}
	private void SaveorfAction() {
		System.out.println("hoi");
		buttonAll = new JRadioButton("All",true);
		buttonBetween = new JRadioButton("Between");
		ButtonGroup groupRadioButton = new ButtonGroup();
		groupRadioButton.add(buttonAll); groupRadioButton.add(buttonBetween);
		final JLabel labelLengthORFUser = new JLabel("Length ORF : ", JLabel.LEFT);

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(true);
		textField = new JFormattedTextField(formatter);
		textField.setValue(100);
		JButton saveButton = new JButton();
		saveButton.setText("Save");

		JPanel panel = new JPanel(new GridLayout(2,2));
        panel.add(buttonAll);panel.add(buttonBetween);panel.add(labelLengthORFUser);panel.add(textField);

        JPanel panel2 = new JPanel();
        panel2.add(saveButton);

        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		JFrame f = new JFrame();

		f.getContentPane().setLayout(new BoxLayout(f.getContentPane(), BoxLayout.PAGE_AXIS));
		f.getContentPane().add(panel);

		f.getContentPane().add(panel2);

		f.pack();
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveButtonAction();
            }
        });



	}

    private void saveButtonAction() {
        int lengthORFUser = (int) Integer.parseInt(textField.getValue().toString());
        Boolean m = buttonAll.isSelected();
        if(m == true){
            System.out.println("ALL");
            cont.setCurORFListALL(lengthORFUser);
        }else{
            System.out.println("BETWEEN");
        }
    }

    private void FindorfAction() {
		int lenghtORF = Integer.parseInt(JOptionPane.showInputDialog("Lenght ORF"));
		cont.setCurORFListALL(lenghtORF);
	}
	private void BlastAction() {

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
	 * @return
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
