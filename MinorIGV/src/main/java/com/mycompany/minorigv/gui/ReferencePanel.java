package com.mycompany.minorigv.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.naming.ContextNotEmptyException;
import javax.swing.JPanel;

import com.mycompany.minorigv.sequence.makeCompStrand;

public class ReferencePanel extends JPanel implements PropertyChangeListener {

	//Hardcoded String ff want moeten nog Context objecten hebben
	Context cont;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		try {
			cont.getSubSequentie();
		}
		catch (Exception e) {
			return; //geen sequentie.
		}

		Graphics2D g2 = (Graphics2D) g;
		Dimension dim = this.getSize();
		String seq = cont.getSubSequentie();
		int length = cont.getLength();

		int width = (int) DrawingTools.calculateLetterWidth((int) dim.getWidth(), length);

		if (width > 7){
		    drawLetters(g,seq);
        }
        else if(width >= 1 ){
            drawNuc(g,seq);
        }


	}

    private void drawNuc(Graphics g, String seq) {

        Graphics2D g2 = (Graphics2D) g;
        Dimension dim = this.getSize();
        seq = cont.getSubSequentie();
        int length = cont.getLength();

        String revComp = makeCompStrand.getReverseComplement(seq);

        for(int i = 0; i < length; i++  ) {
            int j = length-i-1;
            
            int x_pos = (int) DrawingTools.calculateLetterPosition( (int) dim.getWidth(), length, i);

            this.chooseLetterColor(g,seq.charAt(i));
            //DrawingTools.drawCenteredFilledRect(g2, x_pos,x_width, 20);

            this.chooseLetterColor(g,revComp.charAt(j));
           // DrawingTools.drawCenteredFilledRect(g2, x_pos, 40);

            g.setColor(Color.BLACK);
        }

    }

    private void drawLetters(Graphics g, String seq){

		Graphics2D g2 = (Graphics2D) g;
		Dimension dim = this.getSize();
		seq = cont.getSubSequentie();
		int length = cont.getLength();

		String revComp = makeCompStrand.getReverseComplement(seq);
		for(int i = 0; i < length; i++  ) {
			int j = length-i-1;
			
			int x_pos = (int) DrawingTools.calculateLetterPosition( (int) dim.getWidth(), length, i);

			this.chooseLetterColor(g,seq.charAt(i));

			DrawingTools.drawCenteredChar(g2, seq.charAt(i), x_pos, 20);

			this.chooseLetterColor(g,revComp.charAt(j));
			DrawingTools.drawCenteredChar(g2, revComp.charAt(j), x_pos, 40);
			g.setColor(Color.BLACK);
		}


	}

	private void chooseLetterColor(Graphics g, char c) {

		switch (c) {
		case 'T':
			g.setColor(Color.BLUE);
			break;
		case 'A':
			g.setColor(Color.RED);
			break;
		case 'C':
			g.setColor(new Color(0 ,153, 0));
			break;
		case 'G':
			g.setColor(new Color(255 ,85, 0));
			break;
		case 't':
			g.setColor(Color.BLUE);
			break;
		case 'a':
			g.setColor(Color.RED);
			break;
		case 'c':
			g.setColor(new Color(0 ,153, 0));
			break;
		case 'g':
			g.setColor(new Color(255 ,85, 0));
			break;
		default:
			g.setColor(Color.BLACK);
		}

	}
	// initiatie van het paneel waarin de sequenties worden getekent
	public void init() {

		setPreferredSize(new Dimension(500,55));
		setMaximumSize(new Dimension(2000,40));
		setMinimumSize(new Dimension(100,30));
		setBackground(Color.WHITE);
	}

	public void setContext(Context cont) {
		this.cont = cont;
		cont.addPropertyChangeListener("range", this);
		cont.addPropertyChangeListener("chromosome",this);
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		this.invalidate();
		this.repaint();
		
	}
}