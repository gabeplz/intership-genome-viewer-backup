package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.blast.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * Table output voor een blastXML
 */
public class BlastTable extends JFrame {

    BlastOutput blastOutput;

    /**
     * Constructor voor de Table
     * @param blastOutput een blastoutput die gevisualiseerd moet worden.
     */
    public BlastTable(BlastOutput blastOutput) {
        super("blast tabel");
        this.blastOutput = blastOutput;
        setMinimumSize(new Dimension(500, 500));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new JScrollPane(makeTable()));
        this.pack();
        this.setVisible(true);

    }

    /**
     * Functie die de JTable maakt uit de data.
     * @return een JTable.
     */
    public JTable makeTable() {

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int Column) {
                return false;
            }
        };

        JTable table = new JTable(model);

        String[] Columns = {
                "queryDef",
                "queryLen",
                "hitAccession",
                "hitLength",
                "hspEvalue",
                "hspScore",
                "hspBitScore",
                "hspPositives",
                "hspIdentity"
        };

        for (String colHeader : Columns) {
            model.addColumn(colHeader);
        }


        for (Iteration iter : blastOutput.getBlastOutputIterations().getIteration()) {
            ArrayList<String> row = new ArrayList<String>();

            row.add(iter.getIterationQueryDef());
            row.add(iter.getIterationQueryLen());

            if (!iter.getIterationHits().getHit().isEmpty()){

                Hit hit = iter.getIterationHits().getHit().get(0);
                row.add(hit.getHitAccession());
                row.add(hit.getHitLen());

                Hsp hsp = hit.getHitHsps().getHsp().get(0);
                row.add(hsp.getHspEvalue());
                row.add(hsp.getHspScore());
                row.add(hsp.getHspBitScore());
                row.add(hsp.getHspPositive());
                row.add(hsp.getHspIdentity());

            }

            else{

            }

            model.addRow(row.toArray());
        }

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {

                if(!event.getValueIsAdjusting()){
                    newInfoPanel(table.getSelectedRow());
                }

            }
        });

        return table;
    }

    /**
     * Functie voor het tonen van de hit behorende bij de huidige selectie.
     * @param index
     */
    private void newInfoPanel(int index) {

        JFrame iterationFrame = new JFrame();

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int Column) {
                return false;
            }
        };

        JTable table = new JTable(model);

        iterationFrame.add(table);



        System.out.println(blastOutput.getBlastOutputIterations().getIteration().get(index));

    }


    /**
     * Loze main voor testing
     * @param args args
     */
    public static void main(String[] args) {

        BLAST blast = new BLAST();

        BlastOutput bo = null;
        try {
            bo = blast.parseXML("/home/kahuub/Documents/github-minor/app/hoi.xml");
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BlastTable tabel = new BlastTable(bo);
    }

}