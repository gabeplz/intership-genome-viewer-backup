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
	Boolean forward;


	public void init(Boolean strand) {
		this.forward = strand;
		//this.setBackground(Color.ORANGE);
		setPreferredSize(new Dimension(500,75));
		setMaximumSize(new Dimension(2000,40));
		setMinimumSize(new Dimension(100,30));

	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		try {
			cont.getSubSequentie();
		}
		catch (Exception e) {
			return; //geen sequentie.
		}

		String seq = cont.getSubSequentie();
		int start = cont.getStart();
		int stop = cont.getStop();

		seq = forward ? seq : makeCompStrand.getReverseComplement(seq);

		Strand direction = forward ? Strand.POSITIVE : Strand.NEGATIVE;

		CodonTabel huidigeTabel = TranslationManager.buildDefault();

		int k = forward ? +1 : -1;


		for (int f = 0; f < 3; f++) {
			String aaSeq = TranslationManager.getAminoAcids(Strand.POSITIVE,seq.substring(f),huidigeTabel);
			int aa = 0;
			for (int i = f; i < seq.length()-2; i+=3) {

				int j = forward ? i : seq.length()-1-i;

				int[] info = DrawingTools.calculateLetterPosition((int) this.getSize().getWidth(),seq.length(), j+k);
				int x_pos = info[1];

				int width = DrawingTools.calculateLetterPosition((int) this.getSize().getWidth(), seq.length(), j+1.5)[1];
				width -= DrawingTools.calculateLetterPosition((int) this.getSize().getWidth(), seq.length(),j-1.5)[1];

				if(j%2 > 0) {
					g.setColor(new Color(42, 112, 150));
				}
				else{
					g.setColor(new Color(127, 191, 226));
				}

				int y = (start + j) % 3;

				DrawingTools.drawFilledRect(g, x_pos, 20+20*(y),width+1, 20);
				g.setColor(Color.BLACK);

				DrawingTools.drawCenteredChar(g,aaSeq.charAt(aa),x_pos,20+20*(y));
				aa++;
			}

		}
	}


	public void setContext(Context cont) {
		this.cont = cont;
		cont.addPropertyChangeListener("range", this);
		cont.addPropertyChangeListener("chromosome",this);
	}

	public void setForward(Boolean forward) {
		this.forward = forward;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		this.revalidate();
		this.repaint();
		
	}
}