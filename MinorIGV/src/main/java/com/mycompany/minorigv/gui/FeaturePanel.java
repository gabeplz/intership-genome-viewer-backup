package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.gffparser.Feature;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

/**
 * Class voor de visualisatie van verschillende features zoals MRNA/Genen/Telomeren etc die te vinden zijn
 * in GFF(Generic feature format) Files.
 * @author kahuub
 * Date: 20/11/18
 *
 */
public class FeaturePanel extends JPanel implements PropertyChangeListener {
	Context cont;
	boolean forward;
	private ArrayList<Integer> y_coord_forw = new ArrayList<>(Arrays.asList(20,40));
	private ArrayList<Integer> y_text_forw = new ArrayList<>(Arrays.asList(8,28));
	private ArrayList<Integer> y_coord_rev = new ArrayList<>(Arrays.asList(4,25));
	private ArrayList<Integer> y_text_rev = new ArrayList<>(Arrays.asList(15,37));

	/**
	 * Alle features binnen een gewenst start-stop positie ophalen en deze weergeven in de GUI.
	 *
	 * @param g		Graphics waarin getekend wordt.
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		if (cont.getOrganism() == null)return;

		Feature[] featureFilteredList = null;

		try {
			// Alle features die gekozen zijn door de gebruiker tussen een start en stop positie.
			featureFilteredList = cont.getCurrentFeatureList();
		} catch (Exception e) {
			e.printStackTrace();
		}



        System.out.println(featureFilteredList.length + "featurepanel java");

		for (Feature feat : featureFilteredList){
			// Als in het object naam Gene voorkomt dan worden de genen gemapt.
			if(feat.toString().contains("Gene")){
				int index = cont.getChoicesUser().indexOf("Gene");
				drawFeatures(feat,g, (String) feat.getAttributes().get("locus_tag"), index);

			// Als in het object naam mRNA voorkomt dan word mRNA gemapt
			}else if(feat.toString().contains("mRNA")){
				// Ophalen welke index van de coordinaten er gepakt moet worden.
				int index = cont.getChoicesUser().indexOf("mRNA");
				drawFeatures(feat,g, (String) feat.getAttributes().get("Name"), index);
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
		context.addPropertyChangeListener("currentFeatureList",this);
	}

	/**
	 * Het tekenen van de genen op de forward en reverse panel.
	 *
	 * @param feat	De features waarin een gen object staat
	 * @param g		Graphics waarin getekend word.
	 */
	public void drawFeatures(Feature feat, Graphics g, String tag, int index){
		int start, stop;
		Dimension dim = this.getSize();
		int length = cont.getLength();

		start = feat.getStart() - cont.getStart();
		stop = feat.getStop() - cont.getStart();
		String strand = feat.getStrand();

		int[] info_start = DrawingTools.calculateLetterPosition((int)dim.getWidth(), length, start);
		int[] info_stop = DrawingTools.calculateLetterPosition((int)dim.getWidth(), length, stop);

		// Als de strand van het gen + is, dan wordt er op de forward panel getekend.
		if(strand.equals("+") && forward == true){
			g.setColor(Color.ORANGE);
			// Het op de goede positie zetten van de genen, gelet op schaalbaarheid.
			g.fillRect(info_start[1], dim.height-y_coord_forw.get(index), info_stop[1]-info_start[1], 15);
			g.setColor(Color.BLACK);

			// Kleiner lettertype bij een kleiner balkje
			if(g.getFontMetrics().stringWidth(tag) > info_stop[1]-info_start[1]){
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			}else{
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			}

			// locus tag in midden van gen visualiseren.
			int centerTag = g.getFontMetrics().stringWidth(tag)/2;
			g.drawString(tag, ((info_stop[1]+info_start[1])/2)-centerTag, dim.height - y_text_forw.get(index));

			// Als de strand van het gen - is, wordt er op de reverse panel getekend.
		}else if(strand.equals("-") && forward == false){
			g.setColor(Color.ORANGE);

			// Het op de goede positie zetten van de genen.
			g.fillRect(info_start[1], y_coord_rev.get(index), info_stop[1]-info_start[1], 15);
			g.setColor(Color.BLACK);

			// Kleiner lettertype bij een kleiner balkje
			if(g.getFontMetrics().stringWidth(tag) > info_stop[1]-info_start[1]){
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			}else{
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			}

			// locus tag in midden van gen visualiseren.
			int centerTag = g.getFontMetrics().stringWidth(tag)/2;
			g.drawString(tag, ((info_stop[1]+info_start[1])/2)-centerTag, y_text_rev.get(index));
		}
	}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.invalidate();
        this.repaint();
    }
}