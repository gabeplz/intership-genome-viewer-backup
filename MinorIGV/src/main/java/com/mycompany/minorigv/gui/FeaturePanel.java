package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.gffparser.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import javax.swing.JPanel;

import static java.util.Collections.max;
import static java.util.Collections.sort;

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

    private int latest_stop_reverse;
    private int latest_stop_forward;
    private HashMap<Feature, Integer> list_ov_ft = new HashMap<>();
    private Feature feat = null;
    private int y_cood_reverse, y_cood_forward;
    private int y_cood_reverse_max, y_cood_forward_max;

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

		y_cood_reverse = 4;
		y_cood_forward = 4;

		ArrayList<Feature> featureGene = new ArrayList<>();
        ArrayList<Feature> featuremRNA = new ArrayList<>();

        for(Feature feat : featureFilteredList) {
            if (feat instanceof Gene) {
                featureGene.add(feat);
            } else if (feat instanceof mRNA) {
                featuremRNA.add(feat);
            }
        }

        if(!featureGene.isEmpty()){
            setCoordinates(featureGene, g);
        }if(!featuremRNA.isEmpty()){
            setCoordinates(featuremRNA, g);
        }


	}

	public void setCoordinates(ArrayList<Feature> features, Graphics g){
        String tag = "hoi";

        for(Feature gene: features){
            if (gene.getStrand().equals("-")){
                y_cood_reverse = getCoordinates(gene, latest_stop_reverse, list_ov_ft, y_cood_reverse, feat);
                feat = drawFeatures(gene,g,tag, y_cood_reverse);
                latest_stop_reverse = feat.getStop();

                y_cood_reverse_max = getMaxCoordinates(y_cood_reverse, y_cood_reverse_max);

            }else if(gene.getStrand().equals("+")){
                y_cood_forward = getCoordinates(gene, latest_stop_forward, list_ov_ft, y_cood_forward, feat);
                feat = drawFeatures(gene,g,tag, y_cood_forward);
                latest_stop_forward = feat.getStop();

                y_cood_forward_max = getMaxCoordinates(y_cood_forward, y_cood_forward_max);
            }
        }

        y_cood_forward = y_cood_forward_max + 10;
        y_cood_reverse = y_cood_reverse_max + 20;
    }

    private int getCoordinates(Feature gene, int latest_stop, HashMap<Feature, Integer> list_ov_ft, int y_cood, Feature feat){
        if(gene.getStart() < latest_stop){
            list_ov_ft.put(feat, y_cood);
            if(!list_ov_ft.isEmpty()){
                for(Feature f:list_ov_ft.keySet()){
                    if(gene.getStart() < f.getStop()){
                        y_cood += (20/ list_ov_ft.size());
                    }else{
                        y_cood = list_ov_ft.get(f);
                    }
                }
            }else{
                y_cood += 20;
            }
        }else{
            y_cood += 0;
            list_ov_ft.clear();
        }

        return y_cood;
    }

    private int getMaxCoordinates(int y_cood, int y_cood_max){
        if(y_cood > y_cood_max){
            return y_cood;
        }else{
            return y_cood_max;
        }
    }

	/**
	 * Het tekenen van Features in de forward of reverse panel. Dit hangt af van de input van de gebruiker in de toolbar Features.
	 *
	 * @param feat			Het object van een Feature.
	 * @param g				Graphics waarin getekend wordt
	 * @param tag			Wat er in de balkjes van de Feature komt te staan (locus tag, naam, etc)
//	 * @param index			Index voor de coordinaten waar de Feature op het panel moet komen te staan
	 */
	public Feature drawFeatures(Feature feat, Graphics g, String tag, int overlap_coord){
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
			g.fillRect(info_start[1], dim.height-(overlap_coord+20), info_stop[1]-info_start[1], 15);
			g.setColor(Color.BLACK);

			// Kleiner lettertype bij een kleiner balkje
			if(g.getFontMetrics().stringWidth(tag) > info_stop[1]-info_start[1]){
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			}else{
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			}

			// locus tag in midden van gen visualiseren.
			int centerTag = g.getFontMetrics().stringWidth(tag)/2;
			g.drawString(tag, ((info_stop[1]+info_start[1])/2)-centerTag, (dim.height - (7 +overlap_coord)));

			// Als de strand van het gen - is, wordt er op de reverse panel getekend.
		}else if(strand.equals("-") && forward == false){
			g.setColor(Color.ORANGE);

			// Het op de goede positie zetten van de genen.
			g.fillRect(info_start[1], overlap_coord, info_stop[1]-info_start[1], 15);
			g.setColor(Color.BLACK);

			// Kleiner lettertype bij een kleiner balkje
			if(g.getFontMetrics().stringWidth(tag) > info_stop[1]-info_start[1]){
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			}else{
				g.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			}

			// locus tag in midden van gen visualiseren.
			int centerTag = g.getFontMetrics().stringWidth(tag)/2;
			g.drawString(tag, ((info_stop[1]+info_start[1])/2)-centerTag, overlap_coord+12);
		}

		return feat;
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