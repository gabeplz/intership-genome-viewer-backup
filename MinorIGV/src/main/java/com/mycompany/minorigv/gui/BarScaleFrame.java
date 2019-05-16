package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.motif.PositionScoreMatrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BarScaleFrame extends Frame {
    private Context cont;
    private IGVMenuBar bar;
    JFrame frame3;

    public BarScaleFrame(int coverageReadsPerPixel, IGVMenuBar barr) {
        frame3 = new JFrame();
        bar = barr;

        JLabel newValueLabel = new JLabel("new value");
        JTextField newValueField = new JTextField(coverageReadsPerPixel);
        int k = 3;
//        int l = cont.getCoverageReadsPerPixel();
        newValueLabel.setBounds(20, 20, 100, 30);
        newValueField.setBounds(20, 50, 120, 30);

        JTextArea infoField = new JTextArea(coverageReadsPerPixel +" read(s) will heighten \n the bar with 1 pixel");
        infoField.setBounds(200, 50, 190, 60);
        infoField.setLineWrap(true);

        JTextArea infoField2 = new JTextArea("the graph shows a scale of 0 to 50");
        infoField2.setBounds(200, 120, 190, 60);
        infoField2.setLineWrap(true);

        JButton setButton = new JButton("set scale");
        setButton.setBounds(20, 120, 170, 60);

        setButton.addActionListener(new ActionListener() {       //neemt de input data. bouwd een matrix. voegt item toe aan de list(via maodel)
            @Override
            public void actionPerformed(ActionEvent e) {
            try{
                int newCoverageReadsPerPixel = Integer.parseInt(newValueField.getText());
                if (newCoverageReadsPerPixel > 0){
                    cont.setCoverageReadsPerPixel(newCoverageReadsPerPixel);


                } else{
                    ExceptionDialogs.ErrorDialog("Please enter a rounded number above zero","foutieve invoer");
                }
            }catch (NumberFormatException numEx){
                ExceptionDialogs.ErrorDialog("Please enter a rounded number above zero","foutieve invoer");
                }
            int newCoverageReadsPerPixel = 1; //newValueField.getText();
            }
        });



        frame3.add(newValueLabel);
        frame3.add(newValueField);
        frame3.add(infoField);
        frame3.add(infoField2);
        frame3.add(setButton);
        frame3.setSize(400, 300);
        frame3.setLayout(null);
        frame3.setVisible(true);
        frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
        public void kys () {
            frame3.dispose();
        }

        /**
         * sets context
         * @param cont
         */
        public void setContext (Context cont){
            this.cont = cont;
        }
    }