package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.FastaFileChooser;
import com.mycompany.minorigv.sequence.CodonTable;
import com.mycompany.minorigv.sequence.TranslationManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
 * Deze class maakt een menubar voor de applicatie die verschillende menus bevat zoals menus voor het laden van files tools etc.
 *
 * @Auteur: Tim Kuijpers
 */
public class IGVMenuBar extends JMenuBar {

    private Context cont;
    // Defineer de menu items die zelf sub items zullen bevatten.
    JMenu files, tools, features;

    // Defineer de menu items die zelf niet sub items zullen bevatten.
    JMenuItem openRef, openData, saveORF, findORF, blast, genes, mRNA, exon, region, CDS, featureList;


    // Een lijst die de features bevat die de gebruiker op dat moment wil zien.
    ArrayList<String> featureArray = new ArrayList<String>();

    private JRadioButton buttonAll, buttonBetween;
    private JFormattedTextField textField;

    /**
     * Initializeer de verschillende menus.
     */
    public void init() {
        this.setPreferredSize(new Dimension(200, 25));
        this.setMinimumSize(new Dimension(100, 25));
        menus();
        featureMenu();
        condonTableMenu();
    }

    /**
     * Het maken van de eerste 2 menus "File" en "Tools"
     */
    public void menus() {
        //Eerst menu item Files
        files = new JMenu("files");

        //Sub items voor Files
        openRef = new JMenuItem("Load reference");
        openData = new JMenuItem("Load GFF");

        //Action listeners voor de sub items van Files
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


        //Voeg de subitems toe aan  "File"
        files.add(openRef);
        files.add(openData);

        //Voeg Files toe aan de menubar.
        add(files);

        //Tweede menu item "tools"
        tools = new JMenu("ORF");

        //Sub items voor menu item 2 "tools"
        findORF = new JMenuItem("Find ORFs");
        saveORF = new JMenuItem("Save ORFs");
        blast = new JMenuItem("Blast");

        //Action listeners voor de sub item van tools
        findORF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    findOrfAction();

                } catch (NumberFormatException el) {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "can not find orf");
                }
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


        //Voeg de sub items toe aan "tools"
        tools.add(findORF);
        tools.add(saveORF);
        tools.add(blast);

        //Voeg tools toe aan de menu bar.
        add(tools);
    }

    /**
     * Het aan maken van het 3de menu  "features".
     */
    public void featureMenu() {
        // Aanmaken van een feature menu voor op de menu bar
        features = new JMenu("Features");

        featureList = new JMenuItem("Show features");// Checkboxes die de verschillende features bevatten die de gebruiker kan kiezen.


        // Checkboxes die de verschillende features bevatten die de gebruiker kan kiezen.
        genes = new JCheckBoxMenuItem("Genes");
        mRNA = new JCheckBoxMenuItem("mRNA");
        exon = new JCheckBoxMenuItem("Exon");
        region = new JCheckBoxMenuItem("Region");
        CDS = new JCheckBoxMenuItem("CDS");


        featureList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                featureListAction();
            }
        });

        // Voeg action listeners toe aan de check boxes.
        genes.addActionListener(new ActionListener() {
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
        region.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regionAction();
            }
        });
        exon.addActionListener(new ActionListener() {
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

        // Voeg de checkboxes toe aan features menu
        features.add(featureList);
        features.add(genes);
        features.add(mRNA);
        features.add(exon);
        features.add(region);
        features.add(CDS);


        // Aanmaken van booleans. Een boolean is true als een checkbock is aan geselecteerd en false als dit niet het geval is
        boolean m = mRNA.isSelected();
        boolean e = exon.isSelected();
        boolean r = region.isSelected();
        boolean c = CDS.isSelected();

        // Voeg het features menu aan de menu bar
        add(features);

    }

    // Action performed voor files en tools.
    private void loadReferenceAction() {
        try {

            FastaFileChooser fasta = new FastaFileChooser();
            String path = fasta.fastafile();

            cont.addFasta(path);

        } catch (Exception e) {

        }
    }

    private void openDataAction() {
        try {
            FastaFileChooser fasta = new FastaFileChooser();
            String path = fasta.fastafile();

            cont.addGFF(path);

        } catch (Exception e) {
        }
    }

    /**
     * Wanneer er op de button Save ORF wordt geklikt, komt er een pop-up die hier wordt aangemaakt.
     * Deze pop-up bevat twee Radio Buttons (All en Between), een Label, een Textfield en een button (Save).
     * In het Textfield kan de lengte ingevoerd worden die het ORF minimaal moet hebben in nucleotide.
     * Bij de RadioButton kan er gekozen worden of gezocht wordt over het hele sequentie of tussen een bepaalde start en stop.
     */
    private void saveOrfAction() {
        SaveOrf orf = new SaveOrf();
        orf.setContext(cont);
        orf.saveOrfAction();
    }
//        // Radio Button wordt aangemaakt.
//        buttonAll = new JRadioButton("All", true);
//        buttonBetween = new JRadioButton("Between");
//        ButtonGroup groupRadioButton = new ButtonGroup();
//        groupRadioButton.add(buttonAll);
//        groupRadioButton.add(buttonBetween);
//
//        // Label wordt aangemaakt.
//        final JLabel labelLengthORFUser = new JLabel("Length ORF (nt): ", JLabel.LEFT);
//
//        // Er kunnen geen letters ingevoerd worden, wanneer dit wel gebeurd wordt het vorige cijfer gebruikt.
//        NumberFormat format = NumberFormat.getInstance();
//        NumberFormatter formatter = new NumberFormatter(format);
//        formatter.setValueClass(Integer.class);
//        formatter.setMinimum(0);
//        formatter.setMaximum(Integer.MAX_VALUE);
//        formatter.setAllowsInvalid(true);
//        textField = new JFormattedTextField(formatter);
//        textField.setValue(100); // Strandaard lengte van het ORF.
//
//        // Save button wordt aangemaakt.
//        JButton saveButton = new JButton();
//        saveButton.setText("Save");
//
//        // Panel voor Radio Button, Label en Textfield wordt aangemaakt
//        JPanel panel = new JPanel(new GridLayout(2, 2));
//        panel.add(buttonAll);
//        panel.add(buttonBetween);
//        panel.add(labelLengthORFUser);
//        panel.add(textField);
//
//        // Panel voor de Button wordt aangemaakt.
//        JPanel panelForButton = new JPanel();
//        panelForButton.add(saveButton);
//
//        // Er wordt een Padding ingesteld.
//        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
//
//        // Er wordt een frame aangemaakt.
//        JFrame f = new JFrame();
//        f.getContentPane().setLayout(new BoxLayout(f.getContentPane(), BoxLayout.PAGE_AXIS));
//        f.getContentPane().add(panel);
//        f.getContentPane().add(panelForButton);
//        f.pack();
//        f.setLocationRelativeTo(null);
//        f.setVisible(true);
//
//        // ActionListener voor de Save Button
//        saveButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    saveButtonAction();
//                    f.dispose();
//                } catch (FileNotFoundException e1) {
//                    e1.printStackTrace();
//                } catch (UnsupportedEncodingException e1) {
//                    e1.printStackTrace();
//                } catch (NullPointerException el) {
//                    final JFrame frame = new JFrame();
//                    JOptionPane.showMessageDialog(frame, "Er is geen ORF om op te slaan ");
//                    frame.dispose();
//                }
//            }
//        });
//    }

    /**
     * Wanneer er op de Save Button in het panel wordt geklikt, worden de gegevens opgehaald die op dat moment zijn ingevoerd.
     * En wordt de functie saveORFs aangeroepen, om de ORFs in een file op te slaan.
     *
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    private void saveButtonAction() throws FileNotFoundException, UnsupportedEncodingException, NullPointerException {
        // haalt de ingevoerde lengte op van het ORF.
        int lengthORFUser = Integer.parseInt(textField.getValue().toString());
        // Set de ORFs
        cont.setCurORFListALL(lengthORFUser);
        // Kijkt welke Radio Button is aangeklikt.
        Boolean m = buttonAll.isSelected();
        if (m == true) {
            cont.saveORFs(cont.getCurORFListALL());
        } else {
            cont.saveORFs(cont.getCurORFListBetween());
        }
    }

    /**
     * Wanneer er op de button Find ORF gedrukt wordt, komt er een pop-up waarin de lengte van het ORF ingevuld kan worden (nt).
     * Vervolgens worden de ORFs gezocht en gevisualiseerd.
     */
    private void findOrfAction() throws NumberFormatException {
        int lenghtORF = Integer.parseInt(JOptionPane.showInputDialog("Length ORF (nt)"));
        cont.setCurORFListALL(lenghtORF);
    }

    private void blastAction() {


    }

    private void featureListAction() {
        PopUpFrame popup = new PopUpFrame(cont);
    }

    // Action performed voor het choose Feature menu
    // Als een checkbox geslecteerd word zal de bijhorende feature worden toegevoegd aan de featureArray.
    private void genesAction() {
        Boolean m = genes.isSelected();
        if (m == true) {
            featureArray.add("Gene");
        } else {
            featureArray.remove("Gene");
        }
        tellContext();
    }

    private void mrnaAction() {
        Boolean m = mRNA.isSelected();
        if (m == true) {
            featureArray.add("mRNA");
        } else {
            featureArray.remove("mRNA");
        }

        tellContext();
    }

    private void regionAction() {
        Boolean m = region.isSelected();
        if (m == true) {
            featureArray.add("Region");
        } else {
            featureArray.remove("Region");
        }
        tellContext();
    }

    private void exonAction() {
        Boolean m = exon.isSelected();
        if (m == true) {
            featureArray.add("Exon");
        } else {
            featureArray.remove("Exon");
        }

        tellContext();
    }

    private void cdsAction() {
        Boolean m = CDS.isSelected();
        if (m == true) {
            featureArray.add("CDS");
        } else {
            featureArray.remove("CDS");
        }

        tellContext();
    }

    /**
     * @return Arraylist<Strings> bevat geslecteerde features.
     */
    public ArrayList<String> getFeatureArray() {
        return featureArray;
    }

    public void tellContext() {
        cont.setChoiceUser(this.featureArray);
    }

    public void setContext(Context cont) {
        this.cont = cont;
    }

    /**
     * maakt en voegt de translatie tabel menu aan de menubalk toe
     * gebruikt getCodonTableMenuItem om de items te genereren
     */
    public void condonTableMenu() {
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
     *
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
