package com.mycompany.minorigv.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.mycompany.minorigv.gffparser.ORF;
import com.mycompany.minorigv.sequence.CodonTabel;
import com.mycompany.minorigv.sequence.Strand;
import com.mycompany.minorigv.sequence.TranslationManager;



/**
 * JPanel voor de visualisatie van de verschillende reading frames passend bij het referentie genoom.
 * @author Huub en Anne
 * Date: 20/11/18
 */
public class CodonPanel extends JPanel implements PropertyChangeListener{

	Context cont;
	Strand strand;

	private final int ZOOM_SIZE_1 = 20; //Hoeveelheid pixels waarna maar één kleur blauw gebruikt wordt.
	private final int ZOOM_SIZE_2 = 14; //Hoeveelheid pixels waarna de aminozuur letters verdwijnen. (ORF (geel) + start (groen) + stop (rood))
	private final int ZOOM_SIZE_3 = 3;  //Hoeveelheid pixels waarna alleen de ORFs (geel) worden weergegeven.

    /**
     * Initaliseerd
     * @param strand
     */
	public void init(Strand strand) {
		this.strand = strand;
		setPreferredSize(new Dimension(500,75));
		setMaximumSize(new Dimension(2000,40));
		setMinimumSize(new Dimension(100,30));
	}

    /**
     * Override de paintComponent uit JPanel.
     * @param g     Graphics context
     */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Doet niks wanneer er geen sequentie aanwezig is.
		String seq;
		try { 
			seq = cont.getSequentie(); 
		}
		catch (Exception e){
			return;
		}

		// Breedte van een aminozuurblokje.
        double letterWidth = DrawingTools.calculateLetterWidth((int) this.getSize().getWidth(), cont.getLength())*3;
		// Beïnvloed de tekenmethode van de positive (forward) strand.
        if(strand == Strand.POSITIVE) {
            if(letterWidth > ZOOM_SIZE_1){
                drawPositive(g,seq, letterWidth);
            }else if(letterWidth > ZOOM_SIZE_2){
                drawPositive(g,seq, letterWidth);
            }else if(letterWidth > ZOOM_SIZE_3){
                drawPositive(g,seq, letterWidth);
            }else{
                drawZoomedOut(g, seq);
            }
		}
		// Beïnvloed de tekenmethode van de negative (reverse) strand.
		else {
            if(letterWidth > ZOOM_SIZE_1){
                drawNegative(g,seq, letterWidth);
            }else if(letterWidth > ZOOM_SIZE_2){
                drawNegative(g,seq, letterWidth);
            }else if(letterWidth > ZOOM_SIZE_3){
                drawNegative(g,seq, letterWidth);
            }else{
                drawZoomedOut(g, seq);
            }
		}
	}

    /**
     * Tekent de negative CodonPanel.
     * @param g     Graphics context
     * @param seq   Sequentie van het chromosoom/contig.
     * @param letterWidth
     */
	private void drawNegative(Graphics g, String seq, double letterWidth) {

		int start = cont.getStart(); 					//start van het beeld.
		int stop = cont.getStop(); 						//stop van het beeld.
		int length = cont.getLength();					//lengte subsequentie.
		int panelWidth = (int) getSize().getWidth(); 	//breedte paneel

		CodonTabel huidigeTabel = TranslationManager.buildDefault(); //TODO gebruiker keuze tabel

		for (int f = 0; f < 3; f++) { //loopen over de drie mogelijke frames.
            int frame = ORF.calcFrame(stop-1-f, Strand.NEGATIVE, cont.getFullLenght());  //bepalen frame van de subsequentie.

            ArrayList<ORF> strandORFs = getORFs(Strand.NEGATIVE, frame); //ORF's ophalen behorend bij dit frame.
			String aaSeq = TranslationManager.getAminoAcids(strand,seq.substring(start,stop-f),huidigeTabel); //sequentie behorend bij dit frame.

            int x_pos_right; //linkerkant van rechthoekje, positie op de x-as.
            int x_pos_left = (int) DrawingTools.calculateLetterPosition( panelWidth, length, stop-f-start-2+1.5); //onthouden oude x_pos.

			int aa = 0; // aa teller
			for (int indexRef = stop-f; indexRef > start+2; indexRef-=3) { //loopen van rechts naar links op de referentie <-> links naar rechts complement
				char letter = aaSeq.charAt(aa); //character op de positie bijbehorend aminozuur.

				int indexSubSeq = indexRef-start-2; //index op referentie ten opzichte subsequentie
                x_pos_right = x_pos_left; //oude links -> rechts.
                x_pos_left = (int) DrawingTools.calculateLetterPosition( panelWidth, length, indexSubSeq-1.5); //nieuwe x_pos bepalen.
				int x_pos = (int) DrawingTools.calculateLetterPosition(panelWidth,length, indexSubSeq); //positie van de letter.

                int width = x_pos_right-x_pos_left; //breedte van het vierkantje |<-->|
				int height = calcHeight(strand,frame);//20+20*(frame);

                AA(strandORFs, g, indexRef, letter,letterWidth); //instellen juiste kleur.
                g.fillRect(x_pos_left,height-10,width,20);

				g.setColor(Color.BLACK);

                if(letterWidth > ZOOM_SIZE_2){ //teken de letters alleen als ze nog leesbaar zijn,
                    DrawingTools.drawCenteredChar(g,aaSeq.charAt(aa),x_pos,height);
                }
				aa++; // aa teller ophogen.
			}
		}
	}

    /**
     *Tekent het positive CodonPanel.
     * @param g     Graphics context
     * @param seq   Sequentie van het chromosoom/contig.
     * @param letterWidth
     */
	private void drawPositive(Graphics g, String seq, double letterWidth) {

		int start = cont.getStart(); 					//start van het beeld.
		int stop = cont.getStop(); 						//stop van het beeld.
		int length = cont.getLength();					//lengte subsequentie.
		int panelWidth = (int) getSize().getWidth(); 	//breedte paneel

		CodonTabel huidigeTabel = TranslationManager.buildDefault(); //TODO gebruiker keuze tabel

		for (int f = 0; f < 3; f++) { // f teller voor de frames.
			int frameStart = start+f;
            int frame = ORF.calcFrame(frameStart, Strand.POSITIVE, cont.getFullLenght());

            ArrayList<ORF> strandORFs = getORFs(Strand.POSITIVE, frame);
			String aaSeq = TranslationManager.getAminoAcids(strand,seq.substring(start+f,stop),huidigeTabel);

			int aa = 0; // aa teller
			for (int indexRef = frameStart; indexRef < stop-2; indexRef+=3) {
				char letter = aaSeq.charAt(aa);

				int x_pos = (int) DrawingTools.calculateLetterPosition(panelWidth,length, indexRef-start+1);
				int width = (int) (DrawingTools.calculateLetterWidth(panelWidth, length)*3);
                AA(strandORFs, g, indexRef, letter, letterWidth);

				int height = 20+20*(2-frame);
				DrawingTools.drawFilledRect(g, x_pos, height,width+1, 20);
				g.setColor(Color.BLACK);

				if(letterWidth > ZOOM_SIZE_2){
                    DrawingTools.drawCenteredChar(g,letter,x_pos,height);
                }
				aa++;
			}
		}
	}

    /**
     * instellen context
     * @param cont de huidige context
     */
	public void setContext(Context cont) {
		this.cont = cont;
		cont.addPropertyChangeListener("range", this);
		cont.addPropertyChangeListener("chromosome",this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.revalidate();
		this.repaint();

	}

    /**
     * @param strandORFs  een ArrayList met van 1 frame op 1 strand alle ORFs tussen de start en stop van de gebruiker
     * @param g           graphics object g.
     * @param indexRef    de index gemapt naar de referentie.
     * @param letter      de letter van het aminozuur.
     * @param width
     */
    public void AA(ArrayList<ORF> strandORFs, Graphics g, int indexRef, char letter, double width){
        if(strandORFs.size() > 0){ //als uberhaubt ORF's.
            for(ORF o: strandORFs) {
                int startORF = o.getStart();
                int stopORF = o.getStop();
                if (indexRef < stopORF && indexRef > startORF) {
                    colorFrames(g, indexRef, letter, "ORF",width);
                    return;
                }
            }
        }
        colorFrames(g, indexRef, letter, "-", width);
    }

    /**
     * @param g Graphics Context Object.
     * @param indexRef Index op de referentie.
     * @param letter de letter van het aminozuur
     * @param bevest bevestigings String.
     * @param letterWidth breedte van de letter
     */
	public void colorFrames(Graphics g, int indexRef, char letter, String bevest, double letterWidth){
	    if('M' == letter){
            g.setColor(new Color(0, 153, 0));
		}else if('*' == letter){
            g.setColor(new Color(250, 0, 0));
        }else if(bevest.equals("ORF")){
            g.setColor(new Color(255, 255, 0));
        }else if(indexRef%2 > 0 || letterWidth < ZOOM_SIZE_1 ) {  //als te smalle vakjes voor 2 kleuren blauw.
            g.setColor(new Color(127, 191, 226));
        }else{
            g.setColor(new Color(42, 112, 150));
		}
	}

    /**
     * Haalt de ORFs op tussen start en stop
     * Haalt de ORFs op, op één van de strands en op één van de readingframes van de strands
     * @param strand Strand waarop de ORF's moeten zijn gevonden.
     * @param frame het Frame waarin de ORF's moeten liggen.
     * @return een ArrayList van ORF's
     */
	public ArrayList<ORF> getORFs(Strand strand, int frame){
		ArrayList<ORF> listORF = cont.getCurORFListBetween();

        ArrayList<ORF> strandORFs = new ArrayList<>();
        for(ORF o : listORF){
            if(o.getStrand().equals(strand) && o.getReadingframe() == frame){
                strandORFs.add(o);
            }
        }
        return strandORFs;
	}

    /**
     * Haalt de ORFs op tussen start en stop
     * Haalt de ORFs op, op één van de strands en op één van de readingframes van de strands
     * @param strand Strand waarop de ORF's moeten zijn gevonden.
     * @return een ArrayList van ORF's
     */
    public ArrayList<ORF> getORFs(Strand strand){
        ArrayList<ORF> listORF = cont.getCurORFListBetween();

        ArrayList<ORF> strandORFs = new ArrayList<>();
        for(ORF o : listORF){
            if(o.getStrand().equals(strand)){
                strandORFs.add(o);
            }
        }
        return strandORFs;
    }

    /**
     * Functie voor het bepalen van de hoogte waarop getekent moet worden.
     * @param strand Strand die getekent wordt.
     * @param frame het frame wat momenteel wordt getekent.
     * @return de hoogte in pixels waarop getekent moet worden.
     */
    private static int calcHeight(Strand strand, int frame){

	    if(strand == Strand.NEGATIVE){
	        return 20+20*(frame)-10;
        }else if(strand == Strand.POSITIVE){
	        return 20+20*(2-frame)-10;
        }
        else return -1;

    }

    /**
     * Functie voor het tekenen als er ver is uitgezoomt.
     * @param g Graphics context object.
     * @param seq Sequentie als String.
     */
    private void drawZoomedOut(Graphics g, String seq){

        int start = cont.getStart(); 					//start van het beeld.
        int stop = cont.getStop(); 					//stop van het beeld.
        int length = cont.getLength();					//lengte subsequentie.
        int panelWidth = (int) getSize().getWidth(); 	//breedte paneel

        // Link positie van het grote blauwe blok.
        int xPosLeft = (int) DrawingTools.calculateLetterPosition(panelWidth, length,start-start);
        // Rechter positie van het grote blauw blok.
        int xPosRight = (int) DrawingTools.calculateLetterPosition(panelWidth, length, stop-start);

        g.setColor(new Color(127, 191, 226));
        // Tekent het grote blauwe blok
        g.fillRect(xPosLeft,10,xPosRight-xPosLeft,10+3*20-10);

	    ArrayList<ORF> listORF = getORFs(strand);
	    for(ORF o : listORF){
	        // Linker positie in pixel van het ORF (geel)
	        xPosLeft = (int) DrawingTools.calculateLetterPosition(panelWidth, length,o.getStart()-start);
	        // Rechter positie in pixel van het ORF (geel).
	        xPosRight = (int) DrawingTools.calculateLetterPosition(panelWidth, length, o.getStop()-start);
	        int height = calcHeight(strand, o.getReadingframe());
            g.setColor(new Color(255, 255, 0));
	        g.fillRect(xPosLeft, height, xPosRight-xPosLeft, 20);
	        g.setColor(Color.BLACK);
        }
    }
}