package com.mycompany.minorigv.gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.LineMetrics;

public class DrawingTools {

	private static final int OFFSET = 12;

	public static int[] calculateLetterPosition(int width, int amount, double i) {


		width = width - OFFSET;
		double rest = width % amount;

		int pos = (int) (OFFSET + (width/amount) * i);
		int miniOffset = (int) ((rest / amount) * i);
		pos += miniOffset;

		int letterWidth = width/amount;

		return new int[]{letterWidth,pos};

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

	public static void drawCenteredRect(Graphics g, int x, int y, double width, int height) {


		int x1 = (int) (x - (int) width/2);
		int y1 = y - height/2;

		g.drawRect(x1, y1, (int) width, height);

	}
	
	public static void drawFilledRect(Graphics g, int x, int y, double width, int height) {


		int x1 = (int) (x - (int) width/2);
		int y1 = y - height/2;

		g.fillRect(x1, y1, (int) width, height);

	}
}