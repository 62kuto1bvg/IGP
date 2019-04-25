package Client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class Nordpfeil extends JPanel {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {

		//Polygon für linkes Dreieck
		int[] x1Points = new int[] { 50, 0, 50 };
		int[] y1Points = new int[] { 50, 100, 0 };

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Client.Druckuebersicht.ausgewaehlteFarbeNordstern);
		//ANTIALIASING hilft gegen Kantenflimmern
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
	
		
		g2d.fillPolygon(x1Points, y1Points, 3);

		
		//Polygon für rechtes Dreieck
		int[] x2Points = new int[] { 50, 100, 50 };
		int[] y2Points = new int[] { 50, 100, 0 };

		
		
		g2d.drawPolygon(x2Points, y2Points, 3);
		g2d.drawString("NORD", 35, 110);

	}

}
