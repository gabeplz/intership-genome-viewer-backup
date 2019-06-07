package com.mycompany.minorigv.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReadScaleFrame extends Frame {
    private Context cont;
    private IGVMenuBar bar;
    JFrame frame4;

    public ReadScaleFrame(int pixelHeightReads, int pixelSpaceBetweenReads, IGVMenuBar barr) {
        frame4 = new JFrame();
        bar = barr;

        JLabel newValueLabel = new JLabel("new value for the height of each read");
        JTextField newValueField = new JTextField(pixelHeightReads);

        //        int l = cont.getCoverageReadsPerPixel();
        newValueLabel.setBounds(20, 20, 100, 30);
        newValueField.setBounds(20, 50, 120, 30);

        JTextArea infoField = new JTextArea( "reads will be " + pixelHeightReads +" pixels high");
        infoField.setBounds(200, 50, 190, 60);
        infoField.setLineWrap(true);

        JButton setpixelHeightButton = new JButton("set height");
        setpixelHeightButton.setBounds(20, 55, 120, 25);



        JTextArea infoField2 = new JTextArea("the graph shows the coverage up to "+50 * pixelSpaceBetweenReads + " Reads");
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
                        infoField.setText(cont.getCoverageReadsPerPixel()+" read(s) will heighten \n the bar with 1 pixel");
                        infoField2.setText("the graph shows the coverage up to "+50 * cont.getCoverageReadsPerPixel()+ " Reads");

                    } else{
                        ExceptionDialogs.ErrorDialog("Please enter a rounded number above zero","foutieve invoer");
                    }
                }catch (NumberFormatException numEx){
                    ExceptionDialogs.ErrorDialog("Please enter a rounded number above zero","foutieve invoer");
                }
                int newCoverageReadsPerPixel = 1; //newValueField.getText();
            }
        });



        frame4.add(newValueLabel);
        frame4.add(newValueField);
        frame4.add(infoField);
        frame4.add(infoField2);
        frame4.add(setButton);
        frame4.setSize(400, 300);
        frame4.setLayout(null);
        frame4.setVisible(true);
        frame4.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void kys () {
        frame4.dispose();
    }

    /**
     * sets context
     * @param cont
     */
    public void setContext (Context cont){
        this.cont = cont;
    }
}
