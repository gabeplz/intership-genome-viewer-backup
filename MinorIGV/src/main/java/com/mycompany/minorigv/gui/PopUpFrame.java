package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.gffparser.Chromosome;
import com.mycompany.minorigv.gffparser.Feature;
import com.mycompany.minorigv.gffparser.Organisms;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PopUpFrame extends JFrame {
    Context cont;

    public PopUpFrame() {
        JFrame frame = new JFrame("Features");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JLabel label = new JLabel("label be labeling");
        panel.add(label);
        frame.add(panel);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        System.out.println("ddddddd");
        Feature[] featureFilteredList = null;
        featureFilteredList = cont.getCurrentFeatureList();
        System.out.println("superdedup");
        System.out.println(featureFilteredList);
            }
    /**
     * Context instellen
     * @param context
     */
    public void getContext(Context context) {
        this.cont = context;
    }
}
