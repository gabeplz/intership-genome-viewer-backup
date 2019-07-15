package com.mycompany.minorigv;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class HashmapToFastaWriter {

    public static void k (ArrayList<String> a, LinkedHashMap<String,String> b){
        String filename = JOptionPane.showInputDialog("Name this file");
        JFileChooser saveFile = new JFileChooser();
        saveFile.setSelectedFile(new File(filename));
        BufferedWriter writer;
        int sf = saveFile.showSaveDialog(null);

        if(sf == JFileChooser.APPROVE_OPTION){
            try {
                writer = new BufferedWriter(new FileWriter(saveFile.getSelectedFile()));
                for (int i = 0; i < a.size(); i++) {

                    writer.write(a.get(i));
                    writer.newLine();
                    writer.write(b.get(a.get(i)));
                    writer.newLine();

                }
                writer.close();
                JOptionPane.showMessageDialog(null, "File has been saved","File Saved",JOptionPane.INFORMATION_MESSAGE);
                // true for rewrite, false for override

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else if(sf == JFileChooser.CANCEL_OPTION){
            JOptionPane.showMessageDialog(null, "File save has been canceled");
        }
    }
}
