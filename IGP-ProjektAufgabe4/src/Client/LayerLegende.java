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
	int BreiteKartenausschnitt;

	int xuntenKoordinate;
	int yuntenKoordinate;
	int xobenKoordinate;
	int yobenKoordinate;

	public void fuelleLegende(String crs, double minEast, double minNorth, double maxEast, double maxNorth,
			double verhaeltnis, int width, int Legendenbreite, int BreiteKartenausschnitt) throws IOException {
		this.setLayout(null);

		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.black));

		this.crs = crs;
		this.minEast = minEast;
		this.minNorth = minNorth;
		this.maxEast = maxEast;
		this.maxNorth = maxNorth;
		this.verhaeltnis = verhaeltnis;
		this.Legendenbreite = Legendenbreite;
		this.BreiteKartenausschnitt = BreiteKartenausschnitt;

		// Die legendenKoordinaten sind kleinermasstäblich. damit es übersichtlicher
		// wird.
		double Deltax = maxEast - minEast;
		double Deltay = maxNorth - minNorth;

		double minEastLegende = minEast - Deltax;
		double maxEastLegende = maxEast + Deltax;

		double minNorthLegende = minNorth - Deltay;
		double maxNorthLegende = maxNorth + Deltay;

		JPanel Kartenbild = new JPanel();
		// int GroesseBild=(int)(Legendenbreite/5)*3;
		LoadKartenLegende newMap = new LoadKartenLegende(crs, minEastLegende, minNorthLegende, maxEastLegende,
				maxNorthLegende, verhaeltnis, BreiteKartenausschnitt);
		JLabel actualMap = (JLabel) newMap.showMap();
		actualMap.setBackground(Color.WHITE);
		Kartenbild.add(actualMap);
		Kartenbild.setBackground(Color.WHITE);
		Kartenbild.setBounds((Legendenbreite / 5), (Legendenbreite / 5), (Legendenbreite / 5) * 3,
				(Legendenbreite / 5) * 3);
		Kartenbild.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		Kartenbild.setVisible(true);

		RotesViereck rv = new RotesViereck(Legendenbreite);
	
		rv.setVisible(true);

		this.add(rv);
		this.add(Kartenbild);
		this.repaint();
//
//		xuntenKoordinate = (int) (Legendenbreite / 5);
//		yuntenKoordinate = (int) (Legendenbreite / 5) + ((Legendenbreite / 5) * 3);
//
//		xobenKoordinate = (int) (Legendenbreite / 5) * 4;
//		yobenKoordinate = (int) (Legendenbreite / 5);
//
//	}
//
//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//
//		Graphics2D g2de = (Graphics2D) g;
//
//		g2de.drawLine(xobenKoordinate + 15, yobenKoordinate + 15, xuntenKoordinate - 15, yuntenKoordinate - 15);
//		this.repaint();

	}

}
