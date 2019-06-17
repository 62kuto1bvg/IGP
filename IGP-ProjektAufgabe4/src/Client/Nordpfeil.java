package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class Nordpfeil extends JPanel {

	/**
	* 
	*/
	
	private static final long serialVersionUID = 1L;
	public static double x=1.00;
	
	
	
	


	public void setX(double x) {
		this.x = x;
	}




	public void paintComponent(Graphics g) {
//x ist zur veränderung der breite des Nordpfeiles
		//Polygon für linkes Dreieck
		int[] x1Points = new int[] { (int) (x*50),(int) (x*0), (int) (x*50) };
		int[] y1Points = new int[] { (int) (x*50), (int) (x*100), (int) (x*0) };

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Client.Druckuebersicht.ausgewaehlteFarbeNordstern);
		//ANTIALIASING hilft gegen Kantenflimmern
		//g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
	
		
		
		g2d.fillPolygon(x1Points, y1Points, 3);

		
		//Polygon für rechtes Dreieck
		int[] x2Points = new int[] { (int) (x*50), (int) (x*100),(int) (x*50) };
		int[] y2Points = new int[] { (int) (x*50), (int) (x*100), (int) (x*0) };

		
		Font Schrift = new Font("ARIAL",Font.BOLD,(int)(x*17));
		g2d.setFont(Schrift);
		g2d.drawPolygon(x2Points, y2Points, 3);
		g2d.drawString("NORD",(int)(x*27),(int)(x*110));

	}

}
