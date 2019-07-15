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
        infoField.setBounds(200, 30, 190, 100);
        infoField.setLineWrap(true);

        JButton setpixelHeightButton = new JButton("set height");
        setpixelHeightButton.setBounds(20, 85, 120, 30);



        JLabel newSpaceLabel = new JLabel("new value for the space between reads");
        newSpaceLabel.setBounds(20, 140, 100, 30);

        JTextField newSpaceField = new JTextField(pixelSpaceBetweenReads);
        newSpaceField.setBounds(20, 180, 120, 30);

        JTextArea infoField2 = new JTextArea("the space between read will \n be " + pixelSpaceBetweenReads + " pixels");
        infoField2.setBounds(200, 150, 190, 100);
        infoField2.setLineWrap(true);

        JButton setSpaceButton = new JButton("set space");
        setSpaceButton.setBounds(20, 220, 120, 30);




        setpixelHeightButton.addActionListener(new ActionListener() {       //neemt de input data. bouwd een matrix. voegt item toe aan de list(via maodel)
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int newpixelHeightPerRead = Integer.parseInt(newValueField.getText());
                    if (newpixelHeightPerRead > 0){
                        cont.setpixelHeightReads(newpixelHeightPerRead);
                        infoField.setText("reads will be " + cont.getPixelHeightReads() +" pixels high");

                    } else{
                        ExceptionDialogs.ErrorDialog("Please enter a rounded number above zero","foutieve invoer");
                    }
                }catch (NumberFormatException numEx){
                    ExceptionDialogs.ErrorDialog("Please enter a rounded number above zero","foutieve invoer");
                }
                int newCoverageReadsPerPixel = 1; //newValueField.getText();
            }
        });

        setSpaceButton.addActionListener(new ActionListener() {       //neemt de input data. bouwd een matrix. voegt item toe aan de list(via maodel)
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int newSpaceBetweenReads = Integer.parseInt(newSpaceField.getText());
                    if (newSpaceBetweenReads > 0){
                        cont.setPixelSpaceBetweenReads(newSpaceBetweenReads);

                        infoField2.setText("the space between read will \n be " + cont.getPixelSpaceBetweenReads() + " pixels");

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
        frame4.add(newSpaceLabel);
        frame4.add(newValueField);
        frame4.add(newSpaceField);
        frame4.add(infoField);
        frame4.add(infoField2);
        frame4.add(setpixelHeightButton);
        frame4.add(setSpaceButton);
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
