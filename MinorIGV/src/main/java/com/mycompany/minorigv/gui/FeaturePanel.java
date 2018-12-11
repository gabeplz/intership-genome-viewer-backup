package com.mycompany.minorigv.gui;

import com.mycompany.minorigv.gffparser.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
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
    private int y_cood_reverse_max = 0;
    private int y_cood_forward_max = 0;


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

		int y_cood_reverse = 4;
        int y_cood_forward = 4;

        ArrayList<Feature> featureGene = new ArrayList<>();
        ArrayList<Feature> featuremRNA = new ArrayList<>();

        // Elke feature in een aparte lijst zetten
        for(Feature feat : featureFilteredList) {
            if (feat instanceof Gene) {
                featureGene.add(feat);
            } else if (feat instanceof mRNA) {
                featuremRNA.add(feat);
            }
        }

        ArrayList<Integer> newCoord;

        // Als de lijsten niet leeg zijn, dan worden de coordinaten bepaald voor het mappen.
        if(!featureGene.isEmpty()){
            newCoord = setCoordinates(featureGene, g, y_cood_reverse, y_cood_forward);
            y_cood_reverse = newCoord.get(0);
            y_cood_forward = newCoord.get(1);
        }if(!featuremRNA.isEmpty()){
            newCoord = setCoordinates(featuremRNA, g, y_cood_reverse, y_cood_forward);
            y_cood_reverse = newCoord.get(0);
            y_cood_forward = newCoord.get(1);
        }
	}

	public ArrayList<Integer> setCoordinates(ArrayList<Feature> OneFeature, Graphics g, int y_cood_reverse, int y_cood_forward){
	    int latest_stop_reverse = 0;
	    int latest_stop_forward = 0;
	    HashMap<Feature, Integer> list_ov_ft = new HashMap<>();
	    Feature feat = null;
	    ArrayList<Integer> newCoord = new ArrayList<>();

	    // Voor elke feature in een lijst met dezelfde features (bijv. alleen genen) wordt er gekeken op welke strand het voorkomt en wordt getekend.
        for(Feature feature: OneFeature){
            if (feature.getStrand().equals("-")){
                // Ophalen van de coordinaten op de reverse strand van de Feature.
                y_cood_reverse = getCoordinates(feature, latest_stop_reverse, list_ov_ft, y_cood_reverse, feat);

                // Ophalen tags
                String tag = getTag(feature);

                // Tekenen van de feature op de reverse strand.
                feat = drawFeatures(feature,g,tag, y_cood_reverse);

                // Opslaan van de stop positie van de laaste feature om overlap te achterhalen.
                latest_stop_reverse = feat.getStop();

                // Ophalen van de hoogste y-coordinaat voor het tekenen van andere Features (bijv. mRNA na genen)
                y_cood_reverse_max = getMaxCoordinates(y_cood_reverse, y_cood_reverse_max);
            }else if(feature.getStrand().equals("+")){

                // Ophalen van de coordinaten op de template strand van de Feature.
                y_cood_forward = getCoordinates(feature, latest_stop_forward, list_ov_ft, y_cood_forward, feat);

                String tag = getTag(feature);

                feat = drawFeatures(feature,g,tag, y_cood_forward);
                latest_stop_forward = feat.getStop();

                y_cood_forward_max = getMaxCoordinates(y_cood_forward, y_cood_forward_max);
            }
        }

        y_cood_forward = y_cood_forward_max + 20;
        y_cood_reverse = y_cood_reverse_max + 20;

        // Returnen van de coordinaten waarna verder getekent moet worden.
        newCoord.add(y_cood_reverse);
        newCoord.add(y_cood_forward);

        return newCoord;
    }

    private String getTag(Feature feature){
	    // Als de feature een gen is wordt de locus tag als tag opgeslagen, anders de naam van de feature.
	    if(feature instanceof Gene){
	        return (String) feature.getAttributes().get("locus_tag");
        }else{
	        return (String) feature.getAttributes().get("Name");
        }
	}

    private int getCoordinates(Feature curFeature, int latest_stop, HashMap<Feature, Integer> list_ov_ft, int y_cood, Feature lastFeature){
        // Bepalen of er overlap is van twee features.
	    if(curFeature.getStart() < latest_stop){
	        // Als er overlap is wordt de vorige feature (object) opgeslagen.
            list_ov_ft.put(lastFeature, y_cood);
            if(!list_ov_ft.isEmpty()){
                for(Feature f:list_ov_ft.keySet()){
                    if(curFeature.getStart() < f.getStop()){
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
	    // Bepalen van de hoogste waarde van de y-coordinaat, vanaf daar wordt de volgende Feature getekend.
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
     * */
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
	 * Context instellen
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