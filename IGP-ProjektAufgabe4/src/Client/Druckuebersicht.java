package Client;

import java.awt.Color;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.TransferHandler;

public class Druckuebersicht  {
	double maxEast;
	double maxNorth;
	double minEast;
	double minNorth;
	double verhaeltnis;
	String crs;
	int X,Y;
	static Color ausgewaehlteFarbeNordstern;
	static Color ausgewaehlteFarbeMassstabsleiste=Color.BLACK;
	static Color ausgewaehlteFarbeKoordinatengitter;
	
	// Bildschirmgroesse herrausfinden für dynamisches Fenster

	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	double breite = dim.getWidth();
	double hoehe = dim.getHeight();
	static JLayeredPane Kartenblatt = new JLayeredPane();

	public void OeffneÜbersicht(String crs, double minEast, double minNorth, double maxEast, double maxNorth, double verhaeltnis)
			throws IOException {

		this.maxEast = maxEast;
		this.maxNorth = maxNorth;
		this.minEast = minEast;
		this.minNorth = minNorth;
		this.crs = crs;
		this.verhaeltnis = verhaeltnis;
		int width=1000;
		// Hier kommt das Kartenformat, muss noch übergeben werden
		// Die wurzel, weil das das seitenverältnis von Din A ist, bei Hochkanten plänen
		// muss dies getauscht werden

		int KarteBreite = 1000;
		int KarteHoehe = (int) (KarteBreite / (Math.sqrt(2)));

		// Fensterbreite soll nicht den Ganzen Bildschirm bedecken
		int FensterBreite = (int) ((breite / 5) * 4);
		int FensterHoehe = (int) ((hoehe / 5) * 4);

		JFrame FensterDruckuebersicht = new JFrame("Druckübersicht");

		// Groesse dynamisch, sollte in der Bildschirmmitte sein [NOCH ZU PRUEFEN]

		FensterDruckuebersicht.setBounds((int) (breite / (10)), (int) (hoehe / (10)), FensterBreite, FensterHoehe);
	

		// JPanel hat irgendein Layoutgedöns, welchesAusgeschalten werden muss, weil
		// sonst die Grosse der Karten nicht gescheit eingestellt werden Konnte
		FensterDruckuebersicht.setLayout(null);

		// Kartenblatt soll das druckbare Papier werden

		Kartenblatt.setBorder(BorderFactory.createLineBorder(Color.black));
		Kartenblatt.setBounds((int) (KarteBreite / 20), (int) (KarteHoehe / 20), KarteBreite, KarteHoehe);
		Kartenblatt.setBackground(Color.WHITE);
		Kartenblatt.setLayout(null);

		JPanel Kartenbild = new JPanel();

		LoadKartenBild newMap = new LoadKartenBild(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis,width);
		JLabel actualMap = (JLabel) newMap.showMap();
		Kartenbild.add(actualMap);

		// Kartenbild.setBorder(BorderFactory.createLineBorder(Color.black));
		Kartenbild.setBounds(20, 20, KarteBreite - 40, KarteHoehe - 40);
		Kartenbild.setVisible(true);

		JButton ButtonDrucken = new JButton("Drucken");
		ButtonDrucken.setBounds((int) ((FensterBreite / 5) * 4), (int) ((FensterHoehe / 5) * 4), 200, 50);
		ButtonDrucken.setActionCommand("Druckmenue");
		
		JButton ButtonausgewaehlteFarbeNordstern = new JButton("Farbe Nordstern");
		ButtonausgewaehlteFarbeNordstern.setBounds((int) ((FensterBreite / 5) * 4), (int) ((FensterHoehe / 5) * 3), 200, 50);
		ButtonausgewaehlteFarbeNordstern.setActionCommand("auswählen Farbe Nordstern");
		

		JButton ButtonausgewaehlteFarbeMassstab = new JButton("Farbe Massstab");
		ButtonausgewaehlteFarbeMassstab.setBounds((int) ((FensterBreite / 5) * 4), (int) ((FensterHoehe / 5) * 2), 200, 50);
		ButtonausgewaehlteFarbeMassstab.setActionCommand("auswählen Farbe Massstab");

		ActionListenerDruckuebersicht ActionListenerDruckuebersicht = new ActionListenerDruckuebersicht();
		ButtonDrucken.addActionListener(ActionListenerDruckuebersicht);
		ButtonausgewaehlteFarbeNordstern.addActionListener(ActionListenerDruckuebersicht);
		ButtonausgewaehlteFarbeMassstab.addActionListener(ActionListenerDruckuebersicht);

		//Massstabsleiste
		
		Massstabsleiste Ml = new Massstabsleiste();
		Ml.erstelleMassstabsleiste(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis,width);
		Ml.setBounds(((int) ((KarteBreite / 10)*6)), (int) (KarteHoehe / 10)*9, ((KarteHoehe /10)*7), (KarteHoehe /10));
		

		// Nordpfeil einfuegen

		Nordpfeil nordpfeil = new Nordpfeil();
		nordpfeil.setBounds(((int) (KarteBreite / 10) * 8), (int) (KarteHoehe / 10), 300, 300);
	
		// Drag and Drop
		
		Verschieben vsNordpfeil = new Verschieben(nordpfeil);
		Verschieben vsMassstab = new Verschieben(Ml);
		
		
		nordpfeil.setVisible(true);
		Ml.setVisible(true);
		
		
	    //Koordinatengitter
		Koordinatengitter Koordgitter = new Koordinatengitter();
		Koordgitter.erzeugeKoordinatengitter(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis, width);
		Koordgitter.setBounds(0, 0, KarteBreite, KarteHoehe);
		
		FensterDruckuebersicht.add(ButtonDrucken);
		FensterDruckuebersicht.add(ButtonausgewaehlteFarbeMassstab);
		FensterDruckuebersicht.add(ButtonausgewaehlteFarbeNordstern);
		
		Kartenblatt.add(Koordgitter);
		Kartenblatt.add(Ml);
		Kartenblatt.add(nordpfeil);
		Kartenblatt.add(Kartenbild);
		
		Kartenblatt.setLayer(Koordgitter,400);
		Kartenblatt.setLayer(nordpfeil, 400);
		Kartenblatt.setLayer(nordpfeil, 400);
		Kartenblatt.setLayer(Ml, 400);
		// Kartenblatt.add(nordpfeil);
		FensterDruckuebersicht.add(Kartenblatt);

		FensterDruckuebersicht.setVisible(true);

	}



	public static void paint(Graphics2D g2d) {
		// TODO Auto-generated method stub

	}



	public String getCrs() {
		return crs;
	}



	public void setCrs(String crs) {
		this.crs = crs;
	}



	public Color getAusgewaehlteFarbeNordstern() {
		return ausgewaehlteFarbeNordstern;
	}



	public void setAusgewaehlteFarbeNordstern(Color ausgewaehlteFarbeNordstern) {
		this.ausgewaehlteFarbeNordstern = ausgewaehlteFarbeNordstern;
	}



	public Color getAusgewaehlteFarbeMassstabsleiste() {
		return ausgewaehlteFarbeMassstabsleiste;
	}



	public void setAusgewaehlteFarbeMassstabsleiste(Color ausgewaehlteFarbeMassstabsleiste) {
		this.ausgewaehlteFarbeMassstabsleiste = ausgewaehlteFarbeMassstabsleiste;
	}



	public Color getAusgewaehlteFarbeKoordinatengitter() {
		return ausgewaehlteFarbeKoordinatengitter;
	}



	public void setAusgewaehlteFarbeKoordinatengitter(Color ausgewaehlteFarbeKoordinatengitter) {
		this.ausgewaehlteFarbeKoordinatengitter = ausgewaehlteFarbeKoordinatengitter;
	}



	public double getBreite() {
		return breite;
	}



	public void setBreite(double breite) {
		this.breite = breite;
	}

	
}
