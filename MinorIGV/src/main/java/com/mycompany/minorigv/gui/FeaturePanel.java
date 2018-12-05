package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.gffparser.Feature;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Class voor de visualisatie van verschillende features zoals MRNA/Genen/Telomeren etc die te vinden zijn
 * in GFF(Generic feature format) Files.
 * @author kahuub
 * Date: 20/11/18
 *
 */
public class FeaturePanel extends JPanel {
	Context cont;
	boolean forward;

	/**
	 * Alle features binnen een gewenst start-stop positie ophalen en deze weergeven in de GUI.
	 *
	 * @param g		Graphics waarin getekend wordt.
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		ArrayList<Feature> featureFilteredList = new ArrayList<>();

		try {
			// Alle features die gekozen zijn door de gebruiker tussen een start en stop positie.
			featureFilteredList = cont.getCurrentFeatureList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Feature feat : featureFilteredList){
			// Als in het object naam Gene voorkomt dan worden de genen gemapt.
			if(feat.toString().contains("Gene")){
				drawGenes(feat,g);

			// Als in het object naam mRNA voorkomt dan word mRNA gemapt
			}else if(feat.toString().contains("mRNA")){
				drawmRNA(feat,g);
			}
		}
	}

	public void init(boolean forward) {
		this.forward = forward;
		this.setBackground(Color.lightGray);
	}

	/**
	 *
	 * @param context
	 */
	public void setContext(Context context) {
		this.cont = context;
	}

	/**
	 * Het tekenen van de genen op de forward en reverse panel.
	 *
	 * @param feat	De features waarin een gen object staat
	 * @param g		Graphics waarin getekend word.
	 */
	public void drawGenes(Feature feat, Graphics g){
		int start, stop;
		Dimension dim = this.getSize();
		int length = cont.getLength();

		String tag = (String) feat.getAttributes().get("locus_tag");

		start = feat.getStart() - cont.getStart();
		stop = feat.getStop() - cont.getStart();
		String strand = feat.getStrand();

		int[] info_start = DrawingTools.calculateLetterPosition((int)dim.getWidth(), length, start);
		int[] info_stop = DrawingTools.calculateLetterPosition((int)dim.getWidth(), length, stop);
		// Als de strand van het gen + is, dan wordt er op de forward panel getekend.
		if(strand.equals("+") && forward == true){
			g.setColor(Color.ORANGE);
			// Het op de goede positie zetten van de genen, gelet op schaalbaarheid.
			g.fillRect(info_start[1], dim.height-20, info_stop[1]-info_start[1], 15);
			g.setColor(Color.BLACK);

			// Kleiner lettertype bij een kleiner balkje
			if(g.getFontMetrics().stringWidth(tag) > info_stop[1]-info_start[1]){
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			}else{
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			}

			// locus tag in midden van gen visualiseren.
			int centerTag = g.getFontMetrics().stringWidth(tag)/2;
			g.drawString(tag, ((info_stop[1]+info_start[1])/2)-centerTag, dim.height - 8);

			// Als de strand van het gen - is, wordt er op de reverse panel getekend.
		}else if(strand.equals("-") && forward == false){
			g.setColor(Color.ORANGE);

			// Het op de goede positie zetten van de genen.
			g.fillRect(info_start[1], 4, info_stop[1]-info_start[1], 15);
			g.setColor(Color.BLACK);

			// Kleiner lettertype bij een kleiner balkje
			if(g.getFontMetrics().stringWidth(tag) > info_stop[1]-info_start[1]){
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			}else{
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			}

			// locus tag in midden van gen visualiseren.
			int centerTag = g.getFontMetrics().stringWidth(tag)/2;
			g.drawString(tag, ((info_stop[1]+info_start[1])/2)-centerTag, 15);
		}
	}

	public void drawmRNA(Feature feat, Graphics g){
		int start, stop;
		Dimension dim = this.getSize();
		int length = cont.getLength();

		start = feat.getStart() - cont.getStart();
		stop = feat.getStop() - cont.getStart();
		String strand = feat.getStrand();

		int[] info_start = DrawingTools.calculateLetterPosition((int)dim.getWidth(), length, start);
		int[] info_stop = DrawingTools.calculateLetterPosition((int)dim.getWidth(), length, stop);
		String name = (String) feat.getAttributes().get("Name");

		// Als de strand van het mRNA + is, dan wordt er op de forward panel getekend.
		if(strand.equals("+") && forward == true){
			g.setColor(Color.BLUE);
			// Het op de goede positie zetten van de mRNAs, gelet op schaalbaarheid.
			g.fillRect(info_start[1], dim.height-40, info_stop[1]-info_start[1], 15);

			g.setColor(Color.WHITE);

			// Kleiner lettertype bij een kleiner balkje
			if(g.getFontMetrics().stringWidth(name) > info_stop[1]-info_start[1]){
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			}else{
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			}

			// locus tag in midden van gen visualiseren.
			int centerTag = g.getFontMetrics().stringWidth(name)/2;
			g.drawString(name, ((info_stop[1]+info_start[1])/2)-centerTag, dim.height-28);

			// Als de strand van het gen - is, wordt er op de reverse panel getekend.
		}else if(strand.equals("-") && forward == false){
			g.setColor(Color.BLUE);

			// Het op de goede positie zetten van de mRNAs.
			g.fillRect(info_start[1], 25, info_stop[1]-info_start[1], 15);

			g.setColor(Color.WHITE);

			// Kleiner lettertype bij een kleiner balkje
			if(g.getFontMetrics().stringWidth(name) > info_stop[1]-info_start[1]){
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			}else{
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			}

			// locus tag in midden van gen visualiseren.
			int centerTag = g.getFontMetrics().stringWidth(name)/2;
			g.drawString(name, ((info_stop[1]+info_start[1])/2)-centerTag, 37);
		}
	}
}