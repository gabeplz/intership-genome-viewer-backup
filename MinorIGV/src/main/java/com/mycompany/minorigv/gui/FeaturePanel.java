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

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		ArrayList<Feature> featureFilteredList = new ArrayList<>();
		int start, stop;

		try {
			featureFilteredList = cont.getCurrentFeatureList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Dimension dim = this.getSize();
		int length = cont.getLength();

		for (Feature feat : featureFilteredList){
			if(feat.toString().contains("Gene")){
				String tag = (String) feat.getAttributes().get("locus_tag");

				start = feat.getStart() - cont.getStart();
				stop = feat.getStop() - cont.getStart();
				String strand = feat.getStrand();
				System.out.println(strand);
				int[] info = DrawingTools.calculateLetterPosition((int)dim.getWidth(), length, start);
				int[] info_stop = DrawingTools.calculateLetterPosition((int)dim.getWidth(), length, stop);
				if(strand.equals("+") && forward == true){
					// Start is waar op de x-as, lengthGene is hoe lang de balk moet zijn, height is de dikte van de balk.
					g.setColor(Color.ORANGE);
					g.fillRect(info[1], 30, info_stop[1]-info[1], 15);
					g.setColor(Color.BLACK);

					// locus tag in midden van gen visualiseren.
					int centerTag = g.getFontMetrics().stringWidth(tag)/2;
					g.drawString(tag, ((info_stop[1]+info[1])/2)-centerTag, 43);
				}else if(strand.equals("-") && forward == false){
					// Start is waar op de x-as, lengthGene is hoe lang de balk moet zijn, height is de dikte van de balk.
					g.setColor(Color.ORANGE);
					g.fillRect(info[1], 4, info_stop[1]-info[1], 15);
					g.setColor(Color.BLACK);

					// locus tag in midden van gen visualiseren.
					int centerTag = g.getFontMetrics().stringWidth(tag)/2;
					g.drawString(tag, ((info_stop[1]+info[1])/2)-centerTag, 15);
				}

			}
		}
	}
	public void init(boolean forward) {
		this.forward = forward;
		this.setBackground(Color.lightGray);
	}

	public void setContext(Context context) {
		this.cont = context;
	}
}