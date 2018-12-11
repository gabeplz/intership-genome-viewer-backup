package com.mycompany.minorigv.gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.LineMetrics;

public class DrawingTools {

	// 12 is the fond size used while drawing letters
	private static final int OFFSET = 12;

	public static int[] calculateLetterPosition(int width, int SeqLength, double i) {

		//detracting 12 pixels from the width so the application wont draw nucleotides partially of screen
		width = width - OFFSET;

		//the # of pixels left on the right of the screen that are not used. this depends on the panel size
		double rest = width % SeqLength;

		// the x position of a nucleotide is the OFFSET + the width of a nucleotide * the position of that nucleotide in the sequence
		int x_position = (int) (OFFSET + (width/SeqLength) * i);

		//miniOffset is the amount of pixels that are left on the right of
		// the screen divided by the sequence length
		int restOffset = (int) ((rest / SeqLength) * i);

		//add the mini offset to the x position of a nucleotide so it will utilise the entire panel without leaving empty spots on the right
		x_position += restOffset;

		//the width in pixels of a nucleotide = the number of pixels on the x ass devided by the length of the sequence that the application is trying to drawn
		int letterWidth = width/SeqLength;

		return new int[]{letterWidth,x_position};

	}

	/**
	 * Hulp functie voor het tekenen van een Character gezien vanuit het midden in niet linksonder.
	 * @param g Graphics tekenPanel waar getekent gaat worden.
	 * @param charr character dat getekent moet gaan worden.
	 * @param x horizontale positie waarop getekent moet worden.
	 * @param y verticale positie waarop getekent moet worden.
	 */
	public static void drawCenteredChar(Graphics g, char charr, int x, int y) {
		g.setFont(new Font ("Monospaced", Font.BOLD, 14));
		float font_width = g.getFontMetrics().stringWidth(String.valueOf(charr));
		LineMetrics fm = g.getFontMetrics().getLineMetrics(String.valueOf(charr), g);
		float font_height = fm.getHeight();

		g.drawString(String.valueOf(charr), (int) (x-(font_width/2)),(int) (y+(font_height/2)));
	}

	//Draw a rectangle around an element drawn in the application
	public static void drawCenteredRect(Graphics g, int x, int y, double width, int height) {


		int x1 = (int) (x - (int) width/2);
		int y1 = y - height/2;

		g.drawRect(x1, y1, (int) width, height);

	}

	//fill the rectangle drawn in the function drawCenterRect
	public static void drawFilledRect(Graphics g, int x, int y, double width, int height) {


		int x1 = (int) (x - (int) width/2);
		int y1 = y - height/2;

		g.fillRect(x1, y1, (int) width, height);

	}
}