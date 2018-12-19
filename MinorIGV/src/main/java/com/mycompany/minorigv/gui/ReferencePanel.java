package com.mycompany.minorigv.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;

import com.mycompany.minorigv.sequence.MakeCompStrand;

/**
 * ReferencePanel tekent de nucleotiden van de referentie sequentie.
 */
public class ReferencePanel extends JPanel implements PropertyChangeListener {

	Context cont;
	public static final int HEIGHT = 20;

	public static Color thymineKleur = new Color(128,0,255);
    public static Color adenineKleur = new Color(255,0,0);
	public static Color cytosineKleur = new Color(123, 215,0);
	public static Color guanineKleur = new Color(0, 215, 215);


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		try {
			cont.getSubSequentie(); //sequentie opvragen.
		}
		catch (Exception e) {
			return; //geen sequentie.
		}

		Dimension dim = this.getSize();       // grootte dit Paneel
		String seq = cont.getSubSequentie();  // huidige subSequentie
		int length = cont.getLength();        // lengte subsequentie

		int width = (int) DrawingTools.calculateLetterWidth((int) dim.getWidth(), length);   //breedte per letter bepaald door drawingtools.

		if (width > 7){  // gebaseerd op arbitrair getal //TODO afhankelijk van letterbreedte.
		    drawLetters(g,seq); //letter teken Functie
        }
        else if(width >= 1 ){
            drawNuc(g,seq);  //Vierkantje teken functie
        }
        else {
            //Nog niks, gewoon vol negeren.
        }


	}

    /**
     * Functie voor het tekenen van Vierkante Nucleotide.
     * @param g  Graphics object om op te tekenen.
     * @param seq  De subsequentie.
     */
    private void drawNuc(Graphics g, String seq) {

        Graphics2D g2 = (Graphics2D) g;
        Dimension dim = this.getSize();       // grootte dit Paneel.
        int length = cont.getLength();        // lengte subsequentie.

        String revComp = MakeCompStrand.getReverseComplement(seq);  //Reverse Complement van de sub sequentie.

        int realWidth = (int) DrawingTools.calculateLetterWidth((int) dim.getWidth(), length);  //bepaal de toegewezen breedte per pixel.

        int xPos = (int) DrawingTools.calculateLetterPosition( (int) dim.getWidth(), length, -0.5); //linkerkant van rechthoekje, positie op de x-as.
        int oldXPos; //onthouden oude xPos.

        for(int i = 0; i < length; i++  ) {
            int j = length-i-1;

            oldXPos = xPos;
            xPos = (int) DrawingTools.calculateLetterPosition( (int) dim.getWidth(), length, i+0.5); //nieuwe xPos bepalen.

            int width = xPos-oldXPos;  //breedte vierkantje

            if(realWidth > 2){
                width = xPos-oldXPos-1; //pixelbrede streep wit rechts.
            }

            this.chooseLetterColor(g,seq.charAt(i)); //letterkleur kiezen.

            g2.fillRect(oldXPos,HEIGHT/2,width,HEIGHT); //tekenen net rechthoekje.

            this.chooseLetterColor(g,revComp.charAt(j)); //letterkleur kiezen reverse.
            g2.fillRect(oldXPos,HEIGHT+HEIGHT/2,width,HEIGHT); //tekenen net rechthoekje.

            g.setColor(Color.BLACK); //Zwarte kleur resetten.
        }

    }

    /**
     * Letterteken functie.
     * @param g graphics object G.
     * @param seq sequentie object.
     */
    private void drawLetters(Graphics g, String seq){

        Graphics2D g2 = (Graphics2D) g;       // graphics object.
        Dimension dim = this.getSize();       // grootte dit Paneel
        int length = cont.getLength();        // lengte subsequentie

        String revComp = MakeCompStrand.getReverseComplement(seq);  //reverse complement.
		for(int i = 0; i < length; i++  ) {
			int j = length-i-1;  //rechts naar links (reverse)

			int xPos = (int) DrawingTools.calculateLetterPosition( (int) dim.getWidth(), length, i);  //letter corresponderend bepalen.

			this.chooseLetterColor(g,seq.charAt(i));  //letterkleur kiezen.

			DrawingTools.drawCenteredChar(g2, seq.charAt(i), xPos, HEIGHT);  //gecentreerd character tekenen.

			this.chooseLetterColor(g,revComp.charAt(j));  //letterkeuze  <-, loop richting ->
			DrawingTools.drawCenteredChar(g2, revComp.charAt(j), xPos, 2*HEIGHT);  //tekenen reverse
			g.setColor(Color.BLACK);  //kleur terugzetten
		}


	}

    /**
     * Coole switch functie voor kleurtjes.
     * @param g graphics object.
     * @param c char voor selectie.
     */
	private void chooseLetterColor(Graphics g, char c) {
		//http://phrogz.net/css/distinct-colors.html
		switch (c) {
		case 'T':
			g.setColor(thymineKleur);
			break;
		case 'A':
			g.setColor(adenineKleur);
			break;
		case 'C':
			g.setColor(cytosineKleur);
			break;
		case 'G':
			g.setColor(guanineKleur);
			break;
		case 't':
			g.setColor(thymineKleur);
			break;
		case 'a':
			g.setColor(adenineKleur);
			break;
		case 'c':
			g.setColor(cytosineKleur);
			break;
		case 'g':
			g.setColor(guanineKleur);
			break;
		default:
			g.setColor(Color.BLACK);
		}

	}

	/**
	 * initiatie van het paneel waarin de sequenties worden getekent
 	 */
	public void init() {
		setPreferredSize(new Dimension(500,55));
		setMaximumSize(new Dimension(2000,40));
		setMinimumSize(new Dimension(100,30));
		setBackground(Color.WHITE);
	}

	// TODO: 11/12/2018
    /**
     * Context instellen.
     * @param cont context object.
     */
	public void setContext(Context cont) {
		this.cont = cont;
		cont.addPropertyChangeListener("range", this);
		cont.addPropertyChangeListener("chromosome",this);
		
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {  //als range/chromosome veranderen updaten.
		this.invalidate();
		this.repaint();
		
	}
}