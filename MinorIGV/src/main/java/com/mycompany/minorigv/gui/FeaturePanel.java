package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.gffparser.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

/**
 * Class voor de visualisatie van verschillende features zoals MRNA/Genen/Telomeren etc die te vinden zijn
 * in GFF(Generic feature format) Files.
 * @author kahuub en Amber Janssen Groesbeek
 * Date: 20/11/18
 *
 */
public class FeaturePanel extends JPanel implements PropertyChangeListener {
	Context cont;
	boolean forward;
	private ArrayList<Integer> y_coord_forw = new ArrayList<>(Arrays.asList(20,60));
	private ArrayList<Integer> y_text_forw = new ArrayList<>(Arrays.asList(8,48));
	private ArrayList<Integer> y_coord_rev = new ArrayList<>(Arrays.asList(4,45));
	private ArrayList<Integer> y_text_rev = new ArrayList<>(Arrays.asList(15,57));

	/**
	 * Alle features binnen een gewenst start-stop positie ophalen en deze weergeven in de GUI.
	 *
	 * @param g		Graphics waarin getekend wordt.
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		if (cont.getOrganism() == null)return;

		ArrayList<Feature> featureFilteredList = null;

		try {
			// Alle features die gekozen zijn door de gebruiker tussen een start en stop positie.
			featureFilteredList = cont.getCurrentFeatureList();
		} catch (Exception e) {
			e.printStackTrace();
		}


        int latest_stop_genes = 0;
		int latest_stop_mrna = 0;
		int overlap_coord_genes = 0;
		int overlap_coord_mrna = 0;
		ArrayList<String> choices_user = cont.getChoicesUser();
		for (Feature feat : featureFilteredList){
			// Als in het object naam Gene voorkomt dan worden de genen gemapt.
			if(feat instanceof Gene){

			    if(feat.getStart() < latest_stop_genes && overlap_coord_genes == 0){
                    overlap_coord_genes = 18;
                }else{
                    overlap_coord_genes = 0;
                }
				int index = choices_user.indexOf("Gene");
				latest_stop_genes = drawFeatures(feat,g, (String) feat.getAttributes().get("locus_tag"), index, Color.ORANGE, Color.black, overlap_coord_genes);

			// Als in het object naam mRNA voorkomt dan word mRNA gemapt
			}else if(feat instanceof mRNA){
			    if(feat.getStart() < latest_stop_mrna && overlap_coord_mrna == 0){
                    overlap_coord_mrna = 22;
                }else{
                    overlap_coord_mrna = 0;
                }
				// Ophalen welke index van de coordinaten er gepakt moet worden.
				int index = choices_user.indexOf("mRNA");
				latest_stop_mrna = drawFeatures(feat,g, (String) feat.getAttributes().get("Name"), index, Color.BLUE, Color.white, overlap_coord_mrna);
			}
		}
	}

	/**
	 * Het tekenen van Features in de forward of reverse panel. Dit hangt af van de input van de gebruiker in de toolbar Features.
	 *
	 * @param feat			Het object van een Feature.
	 * @param g				Graphics waarin getekend wordt
	 * @param tag			Wat er in de balkjes van de Feature komt te staan (locus tag, naam, etc)
	 * @param index			Index voor de coordinaten waar de Feature op het panel moet komen te staan
	 * @param colorFeature	Kleur van het balkje voor een Feature
	 * @param colorText		Kleur van de tekst in een balkje.
	 */
	public int drawFeatures(Feature feat, Graphics g, String tag, int index, Color colorFeature, Color colorText, int overlap_coord){
		int start, stop;
		Dimension dim = this.getSize();
		int length = cont.getLength();

//		if(feat.getStart() < latest_stop && overlap_coord == 0){
//		    overlap_coord = 18;
//        }else{
//            overlap_coord = 0;
//        }


        start = feat.getStart() - cont.getStart();
		stop = feat.getStop() - cont.getStart();
		String strand = feat.getStrand();
		int[] info_start = DrawingTools.calculateLetterPosition((int)dim.getWidth(), length, start);
		int[] info_stop = DrawingTools.calculateLetterPosition((int)dim.getWidth(), length, stop);

		// Als de strand van het gen + is, dan wordt er op de forward panel getekend.
		if(strand.equals("+") && forward == true){
			g.setColor(colorFeature);
			// Het op de goede positie zetten van de genen, gelet op schaalbaarheid.
			g.fillRect(info_start[1], (dim.height-y_coord_forw.get(index))+overlap_coord, info_stop[1]-info_start[1], 15);
			g.setColor(colorText);

			// Kleiner lettertype bij een kleiner balkje
			if(g.getFontMetrics().stringWidth(tag) > info_stop[1]-info_start[1]){
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			}else{
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			}

			// locus tag in midden van gen visualiseren.
			int centerTag = g.getFontMetrics().stringWidth(tag)/2;
			g.drawString(tag, ((info_stop[1]+info_start[1])/2)-centerTag, (dim.height - y_text_forw.get(index))+overlap_coord);

			// Als de strand van het gen - is, wordt er op de reverse panel getekend.
		}else if(strand.equals("-") && forward == false){
			g.setColor(colorFeature);

			// Het op de goede positie zetten van de genen.
			g.fillRect(info_start[1], y_coord_rev.get(index)+overlap_coord, info_stop[1]-info_start[1], 15);
			g.setColor(colorText);

			// Kleiner lettertype bij een kleiner balkje
			if(g.getFontMetrics().stringWidth(tag) > info_stop[1]-info_start[1]){
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			}else{
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			}

			// locus tag in midden van gen visualiseren.
			int centerTag = g.getFontMetrics().stringWidth(tag)/2;
			g.drawString(tag, ((info_stop[1]+info_start[1])/2)-centerTag, y_text_rev.get(index) + overlap_coord);
		}

		return feat.getStop();
	}

	/**
	 *
	 * @param forward	boolean voor welke panel: True voor forward panel, False voor reverse panel.
	 */
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



    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.invalidate();
        this.repaint();
    }
}