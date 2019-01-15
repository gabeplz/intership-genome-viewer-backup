package com.mycompany.minorigv.motif;

import com.mycompany.minorigv.gui.Context;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
public class TextAreaExample extends Frame {
    private Context cont;
    private String areaText;

    public TextAreaExample() {
        JFrame frame2 = new JFrame();
        TextArea area = new TextArea("Welcome to javatpoint");
        area.setBounds(10, 50, 300, 300);

        JButton matrixButton = new JButton("build matrix");
        matrixButton.setBounds(10,10,190,40);
        matrixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cont.setRegexPattern(area.getText());

                PositionScoreMatrix.buildMatrix(cont.getRegexPattern());
            }
        });

        JButton searchButton = new JButton ("search motives");
        searchButton.setBounds(200,10,190,40);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        frame2.add(area);
        frame2.add(matrixButton);
        frame2.add(searchButton);

        frame2.setSize(400, 400);
        frame2.setLayout(null);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    }

    public String returnInput(String areaText) {
        return areaText;
    }

    public void setContext(Context cont) {
        this.cont = cont;
    }
}