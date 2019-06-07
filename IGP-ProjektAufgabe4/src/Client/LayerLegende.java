package Client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LayerLegende extends JPanel {

	
	String crs;
	double minEast, minNorth, maxEast, maxNorth, verhaeltnis;
	static int width;
	static int height;
	double DeltaX, DeltaY;
	int Legendenbreite;

	

	public void	fuelleLegende(String crs, double minEast, double minNorth, double maxEast,double maxNorth, double verhaeltnis, int width,int Legendenbreite) throws IOException {
		this.setLayout(null);
	
	this.setBackground(Color.WHITE);
	this.setBorder(BorderFactory.createLineBorder(Color.black));
	

	
	this.crs = crs;
	this.minEast = minEast;
	this.minNorth = minNorth;
	this.maxEast = maxEast;
    this.maxNorth = maxNorth;
	this.verhaeltnis = verhaeltnis;	
	this.Legendenbreite=Legendenbreite;

	
	
	JPanel Kartenbild=new JPanel();
	int GroesseBild=(int)(Legendenbreite/5)*3;
	System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDD"+GroesseBild);
	LoadKartenLegende newMap = new LoadKartenLegende(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis,150);
	JLabel actualMap = (JLabel) newMap.showMap();
	Kartenbild.add(actualMap);
	
	Kartenbild.setBounds((Legendenbreite/5),(Legendenbreite/5),(Legendenbreite/5)*3,(Legendenbreite/5)*3);
	Kartenbild.setVisible(true);
	this.add(Kartenbild);
	this.repaint();
	
	
	}
	



	public void paintComponent(Graphics g) {
	
	super.paintComponent(g);

	Graphics2D g2de = (Graphics2D) g;
	

	
	
}
}
