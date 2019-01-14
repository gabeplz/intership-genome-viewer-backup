package com.mycompany.minorigv.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;

/**
 * Wanneer er op de button Save ORF wordt geklikt, komt er een pop-up die hier wordt aangemaakt.
 * Deze pop-up bevat twee Radio Buttons (All en Between), een Label, een Textfield en een button (Save).
 * In het Textfield kan de lengte ingevoerd worden die het ORF minimaal moet hebben in nucleotide.
 * Bij de RadioButton kan er gekozen worden of gezocht wordt over het hele sequentie of tussen een bepaalde start en stop.
 */
public class SaveOrf {

    private Context cont;
    private JRadioButton buttonAll, buttonBetween;
    private JFormattedTextField textField;

    /**
     * declareren van de buttons en de textfields
     *
      */
    public void saveOrfAction() {
        System.out.println(cont.getStart());
        // Radio Button wordt aangemaakt.
        buttonAll = new JRadioButton("All", true);
        buttonBetween = new JRadioButton("Between");
        ButtonGroup groupRadioButton = new ButtonGroup();
        groupRadioButton.add(buttonAll);
        groupRadioButton.add(buttonBetween);

        // Label wordt aangemaakt.
        final JLabel labelLengthORFUser = new JLabel("Length ORF (nt): ", JLabel.LEFT);

        // Er kunnen geen letters ingevoerd worden, wanneer dit wel gebeurd wordt het vorige cijfer gebruikt.
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(true);
        textField = new JFormattedTextField(formatter);
        textField.setValue(100); // Strandaard lengte van het ORF.

        // Save button wordt aangemaakt.
        JButton saveButton = new JButton();
        saveButton.setText("Save");

        // Panel voor Radio Button, Label en Textfield wordt aangemaakt
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(buttonAll);
        panel.add(buttonBetween);
        panel.add(labelLengthORFUser);
        panel.add(textField);

        // Panel voor de Button wordt aangemaakt.
        JPanel panelForButton = new JPanel();
        panelForButton.add(saveButton);

        // Er wordt een Padding ingesteld.
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Er wordt een frame aangemaakt.
        JFrame f = new JFrame();
        f.getContentPane().setLayout(new BoxLayout(f.getContentPane(), BoxLayout.PAGE_AXIS));
        f.getContentPane().add(panel);
        f.getContentPane().add(panelForButton);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        // ActionListener voor de Save Button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveButtonAction();
                    f.dispose();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                } catch (NullPointerException el) {
                    final JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Er is geen ORF om op te slaan ");
                    frame.dispose();
                }
            }
        });
    }
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

    public void setContext(Context cont) {
        this.cont = cont;
    }
}
