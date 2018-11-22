package com.mycompany.minorigv;

import java.awt.Graphics;
import java.awt.font.LineMetrics;

public class DrawingTools {

	
	/**
	 * Hulp functie voor het tekenen van een Character gezien vanuit het midden in niet linksonder.
	 * @param g Graphics tekenPanel waar getekent gaat worden.
	 * @param charr character dat getekent moet gaan worden.
	 * @param x horizontale positie waarop getekent moet worden.
	 * @param y verticale positie waarop getekent moet worden.
	 */
	public static void drawCenteredChar(Graphics g, char charr, int x, int y) {
		
		
		float font_width = g.getFontMetrics().stringWidth(String.valueOf(charr));
		LineMetrics fm = g.getFontMetrics().getLineMetrics(String.valueOf(charr), g);
		float font_height = fm.getHeight();
		
		g.drawString(String.valueOf(charr), (int) (x-(font_width/2)),(int) (y+(font_height/2)));
		
		g.drawRect(x-10, y-10, 20, 20);
			
		
	}
	
	
	
	
}
