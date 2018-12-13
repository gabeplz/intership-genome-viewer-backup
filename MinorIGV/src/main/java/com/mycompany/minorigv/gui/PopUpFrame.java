package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.gffparser.Chromosome;
import com.mycompany.minorigv.gffparser.Feature;
import com.mycompany.minorigv.gffparser.Organisms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PopUpFrame extends JFrame {
    Context cont;

    /**
     *
     * @param cont
     */
    public PopUpFrame(Context cont) {

        super();
        this.cont = cont;
        JFrame frame = new JFrame("Features");
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Feature[] featureFilteredList = cont.getCurrentFeatureList();

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Phase");
        model.addColumn("Strand");
        model.addColumn("Start");
        model.addColumn("stop");
        model.addRow(new Object[]{"ID","Phase","Strand","Start","Stop"});

        for (Feature feat:featureFilteredList) {
            model.addRow(new Object[]{feat.getId(), feat.getPhase(),feat.getStrand(),feat.getStart(),feat.getStop()});
        }

        frame.add(table);
        frame.setVisible(true);
            }
    /**
     * Context instellen
     * @param context
     */
    public void setContext(Context context) {
        this.cont = context;
    }
}
