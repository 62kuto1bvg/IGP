package Client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Nordpfeil extends JPanel {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {

		int[] x1Points = new int[] { 50, 0, 50 };
		int[] y1Points = new int[] { 50, 100, 0 };

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);

		g2d.fillPolygon(x1Points, y1Points, 3);

		int[] x2Points = new int[] { 50, 100, 50 };
		int[] y2Points = new int[] { 50, 100, 0 };

		g2d.drawPolygon(x2Points, y2Points, 3);
		g2d.drawString("NORD", 35, 110);

	}

}
