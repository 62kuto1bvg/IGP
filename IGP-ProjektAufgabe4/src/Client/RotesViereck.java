package Client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JPanel;
import org.omg.CORBA.Bounds;

public class RotesViereck extends JPanel{
	
	int xuntenKoordinate;
	 int yuntenKoordinate;
	 int xobenKoordinate;
	 int yobenKoordinate;
	
	
	public RotesViereck(int Legendenbreite) {
	this.setLayout(null);
		
	 xuntenKoordinate = (int) (Legendenbreite / 5);
	 yuntenKoordinate = (int) (Legendenbreite / 5) + ((Legendenbreite / 5) * 3);

	 xobenKoordinate = (int) (Legendenbreite / 5) * 4;
	 yobenKoordinate = (int) (Legendenbreite / 5);
	System.out.println("RRRRRRRRRRRRRRROOOOOOOOOOOOOOOOOTTTTTTTTTT"+yuntenKoordinate);
	
	this.setVisible(true);
	}	
		
		public void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2de = (Graphics2D) g;	
	
	g2de.drawLine(xobenKoordinate, yobenKoordinate, xuntenKoordinate, yuntenKoordinate);
	g2de.drawString("TEST", 10, 10);
	this.repaint();

}}
