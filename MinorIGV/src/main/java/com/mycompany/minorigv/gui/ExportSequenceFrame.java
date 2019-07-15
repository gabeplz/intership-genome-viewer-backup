package com.mycompany.minorigv.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExportSequenceFrame extends Frame implements PropertyChangeListener {
    private Context cont;
    private IGVMenuBar bar;
    JFrame frame3 = new JFrame();
    JTextField matrixNameField;
    JButton removeFromMemoryButton;
    JButton removeFromSaveListButton;
    JButton addToSaveListButton;
    JButton addAllToSaveListButton;
    JButton saveToFileButton;

    JLabel memoryListLabel;
    JLabel saveListLabel;

    JScrollPane memoryScollPane ;
    DefaultListModel modelMemory ;
    JList<String> memoryList;

    JScrollPane saveScollPane ;
    DefaultListModel modelSave ;
    JList<String> saveList;

    public ExportSequenceFrame (IGVMenuBar barr,Context contin){
      //  frame3 = new JFrame();
        this.cont = contin;
        bar = barr;
        setListeners();

        JLabel memoryListLabel = new JLabel("<html>List of sequences in memory</html>");
        memoryListLabel.setBounds(50, 20, 150, 30);
        JLabel saveListLabel = new JLabel("<html>List of sequences to be saved to a file</html>");
        saveListLabel.setBounds(450, 20, 150, 30);

        memoryScollPane = new JScrollPane();
        modelMemory = new DefaultListModel();
        memoryList = new JList(modelMemory);
        memoryScollPane.setViewportView(memoryList);

        memoryScollPane.setBounds(50, 50, 150, 380);
        ArrayList keyList =  cont.getSelectedSequncesFastaMapKeys();
        for (int i = 0; i < keyList.size(); i++) {
            modelMemory.addElement(keyList.get(i));
        }

        saveScollPane = new JScrollPane();
        modelSave = new DefaultListModel();
        saveList = new JList(modelSave);
        saveScollPane.setViewportView(saveList);
        saveScollPane.setBounds(450, 50, 150, 380);

        ArrayList<String> tempList = (ArrayList<String>) cont.getSelectedSequncesKeysForSaving().clone();
        for (int i = 0; i < tempList.size(); i++) {
            modelSave.addElement(tempList.get(i));
        }
        tempList.clear();



        addToSaveListButton = new JButton("<html>add to save list</html>");
        addToSaveListButton.setBounds(220,100,130,40);
        addToSaveListButton.addActionListener(new ActionListener() {       //neemt de input data. bouwd een matrix. voegt item toe aan de list(via maodel)
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder messageText = new StringBuilder();
                messageText.append("the following sequences were already present in the save list and not added again.");
                messageText.append(System.getProperty("line.separator"));
                Boolean found = false;
                int[] selectedItems = memoryList.getSelectedIndices();
                for(int i=0; i < selectedItems.length ;i++) {
                    String x = (String) modelMemory.getElementAt(selectedItems[i]);

                    if(cont.getSelectedSequncesKeysForSaving().contains(x)){
                        found = true;
                        messageText.append(x);
                        messageText.append(System.getProperty("line.separator"));

                    } else {
                        modelSave.addElement(x);
                        cont.addToSelectedSequncesKeysForSaving(x);
                        cont.checkdebug();
                    }
                }
                if (found == true){
                    JOptionPane.showMessageDialog(null, new JScrollPane( new JTextArea(messageText.toString())));

                }


            }
        });

        addAllToSaveListButton = new JButton("<html>add all to save list</html>");
        addAllToSaveListButton.setBounds(220,150,130,40);
        addAllToSaveListButton.addActionListener(new ActionListener() {       //neemt de input data. bouwd een matrix. voegt item toe aan de list(via maodel)
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder messageText = new StringBuilder();
                messageText.append("the following sequences were not added because they were already present in the save list.");
                messageText.append(System.getProperty("line.separator"));
                Boolean found = false;

                for (int i = 0; i < modelMemory.getSize(); i++) {
                    System.out.println("model" + i);


                    String x = (String) modelMemory.getElementAt(i);

                    if (cont.getSelectedSequncesKeysForSaving().contains(x)) {
                        found = true;
                        messageText.append(x);
                        messageText.append(System.getProperty("line.separator"));

                    } else {
                        modelSave.addElement(x);
                        cont.addToSelectedSequncesKeysForSaving(x);
                        cont.checkdebug();
                    }
                }
                    if (found == true) {
                        JOptionPane.showMessageDialog(null, new JScrollPane(new JTextArea(messageText.toString())));

                    }


            }
        });

        removeFromSaveListButton = new JButton("<html>remove from save list<html>");
        removeFromSaveListButton.setBounds(620,100,130,40);
        removeFromSaveListButton.addActionListener(new ActionListener() {       //neemt de input data. bouwd een matrix. voegt item toe aan de list(via maodel)
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedItems = saveList.getSelectedIndices();
                for(int counter=selectedItems.length - 1; counter >= 0;counter--) {
                    cont.removeFromSelectedSequncesKeysForSaving(selectedItems[counter]);
                    modelSave.remove(selectedItems[counter]);
                    cont.checkdebug();

                }
            }
        });

        removeFromMemoryButton = new JButton("<html>remove from memory and save list</html>");
        removeFromMemoryButton.setBounds(220,190,130,70);
        removeFromMemoryButton.addActionListener(new ActionListener() {       //neemt de input data. bouwd een matrix. voegt item toe aan de list(via maodel)
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedItems = memoryList.getSelectedIndices();
                for(int counter=selectedItems.length - 1; counter >= 0;counter--){
                    String x = (String)modelMemory.getElementAt(selectedItems[counter]);
                    // System.out.println(x.hashCode());
                    cont.removeFromSelectedSequncesFastaMap(x);

                    System.out.println(modelSave.indexOf(x)+"proooof");
                    modelSave.removeElement(x);
                    modelMemory.remove(selectedItems[counter]);
                    cont.checkdebug();


                }
            }
        });

        saveToFileButton = new JButton("<html>save to file<html>");
        saveToFileButton.setBounds(620,200,130,40);
        saveToFileButton.addActionListener(new ActionListener() {       //neemt de input data. bouwd een matrix. voegt item toe aan de list(via maodel)
            @Override
            public void actionPerformed(ActionEvent e) {
            cont.writeToFasta(cont.getSelectedSequncesKeysForSaving(), cont.getSelectedSequncesMap());
            }
        });










        frame3.add(memoryListLabel);
        frame3.add(saveListLabel);
        frame3.add(removeFromMemoryButton);
        frame3.add(memoryScollPane);
        frame3.add(addToSaveListButton);
        frame3.add(removeFromSaveListButton);
        frame3.add(saveScollPane);
        frame3.add(addAllToSaveListButton);
        frame3.add(saveToFileButton);
        frame3.setSize(800, 505);
        frame3.setLayout(null);
        frame3.setVisible(true);
        frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

}


    public void setname(){

    }
    /**
     * sets context
     * @param cont
     */
    public void setContext(Context cont) {
        this.cont = cont;
    }
    public void kys(){
        frame3.dispose();
    }
    public void setListeners() {
        cont.addPropertyChangeListener("addSequence", this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {

        this.frame3.getContentPane().remove(memoryScollPane);

        memoryScollPane = new JScrollPane();
        modelMemory = new DefaultListModel();
        memoryList = new JList(modelMemory);
        memoryScollPane.setViewportView(memoryList);

        memoryScollPane.setBounds(50, 50, 150, 380);
        ArrayList keyList = cont.getSelectedSequncesFastaMapKeys();
        for (int i = 0; i < keyList.size(); i++) {
            modelMemory.addElement(keyList.get(i));
        }
        frame3.getContentPane().add(memoryScollPane);

        this.frame3.revalidate();

        this.frame3.repaint();
        System.out.println("propertychance");


    }
}