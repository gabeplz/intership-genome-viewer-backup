package com.mycompany.minorigv.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import com.mycompany.minorigv.sequence.CodonTabel;
import com.mycompany.minorigv.sequence.Strand;
import com.mycompany.minorigv.sequence.TranslationManager;
import com.mycompany.minorigv.sequence.makeCompStrand;


/**
 * JPanel voor de visualisatie van de verschillende reading frames passend bij het referentie genoom.
 * @author kahuub
 * Date: 20/11/18
 */
public class CodonPanel extends JPanel implements PropertyChangeListener{

	Context cont;
	Strand strand;


	public void init(Strand strand) {
		this.strand = strand;
		//this.setBackground(Color.ORANGE);
		setPreferredSize(new Dimension(500,75));
		setMaximumSize(new Dimension(2000,40));
		setMinimumSize(new Dimension(100,30));

	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		String seq;
		try { 
			seq = cont.getSequentie(); 
		}
		catch (Exception e){
			return;
		}

		if(strand == Strand.POSITIVE) {
			drawPositive(g,seq);
		}
		else {
			drawNegative(g,seq);
		}

	}

	private void drawNegative(Graphics g, String seq) {

		int start = cont.getStart(); 					//start van het beeld.
		int stop = cont.getStop(); 						//stop van het beeld.
		int length = cont.getLength();					//lengte subsequentie.
		int PanelWidth = (int) getSize().getWidth(); 	//breedte paneel

		CodonTabel huidigeTabel = TranslationManager.buildDefault();

		for (int f = 0; f < 3; f++) { //TODO Berekenen van het frame adhv de stop.
			int frameStart = cont.getFullLenght() -stop -f;
			int frame = frameStart % 3; //TODO eigen functie.
			

			String aaSeq = TranslationManager.getAminoAcids(strand,seq.substring(start,stop-f),huidigeTabel);
			

			int aa = 0; // aa teller
			for (int indexRef = stop-f; indexRef > start+2; indexRef-=3) {

				int x_pos = (int) DrawingTools.calculateLetterPosition(PanelWidth,length, indexRef-start-2);
				System.out.println(String.valueOf(PanelWidth)+":"+String.valueOf(length)+":"+String.valueOf(indexRef-start)+":"+String.valueOf(x_pos));

				int width = (int) Math.ceil( (DrawingTools.calculateLetterWidth(PanelWidth, length)*3));

				if(indexRef%2 > 0) {
					g.setColor(new Color(42, 112, 150));
				}
				else{
					g.setColor(new Color(127, 191, 226));
				}

				int height = 20+20*(2-frame);
				DrawingTools.drawFilledRect(g, x_pos, height,width+1, 20);
				g.setColor(Color.BLACK);

				DrawingTools.drawCenteredChar(g,aaSeq.charAt(aa),x_pos,height);
				aa++;
			}

		}

	}



	private void drawPositive(Graphics g, String seq) {

		int start = cont.getStart(); 					//start van het beeld.
		int stop = cont.getStop(); 						//stop van het beeld.
		int length = cont.getLength();						//lengte subsequentie.
		int PanelWidth = (int) getSize().getWidth(); 	//breedte paneel

		CodonTabel huidigeTabel = TranslationManager.buildDefault();

		for (int f = 0; f < 3; f++) {
			int frameStart = start+f;
			int frame = frameStart % 3; //TODO eigen functie.

			String aaSeq = TranslationManager.getAminoAcids(strand,seq.substring(start+f,stop),huidigeTabel);

			int aa = 0; // aa teller
			for (int indexRef = frameStart; indexRef < stop-2; indexRef+=3) {

				int x_pos = (int) DrawingTools.calculateLetterPosition(PanelWidth,length, indexRef-start+1);
				System.out.println(String.valueOf(PanelWidth)+":"+String.valueOf(length)+":"+String.valueOf(indexRef-start)+":"+String.valueOf(x_pos));

				int width = (int) Math.ceil( (DrawingTools.calculateLetterWidth(PanelWidth, length)*3));

				if(indexRef%2 > 0) {
					g.setColor(new Color(42, 112, 150));
				}
				else{
					g.setColor(new Color(127, 191, 226));
				}

				int height = 20+20*(2-frame);
				DrawingTools.drawFilledRect(g, x_pos, height,width+1, 20);
				g.setColor(Color.BLACK);

				DrawingTools.drawCenteredChar(g,aaSeq.charAt(aa),x_pos,height);
				aa++;
			}

		}

	}
	public void setContext(Context cont) {
		this.cont = cont;
		cont.addPropertyChangeListener("range", this);
		cont.addPropertyChangeListener("chromosome",this);
	}

	public void setForward(Strand strand) {
		this.strand = strand;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		this.revalidate();
		this.repaint();

	}
}