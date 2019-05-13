package Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Druckuebersicht  {
	double maxEast;
	double maxNorth;
	double minEast;
	double minNorth;
	double verhaeltnis;
	String crs;
	int X,Y;
	static Color ausgewaehlteFarbeNordstern;
	static Color ausgewaehlteFarbeMassstabsleiste;
	static Color ausgewaehlteFarbeKoordinatengitter;
	
	// Bildschirmgr��e herrausfinden (f�r dynamisches Fenster):
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	double breite = dim.getWidth();
	double hoehe = dim.getHeight();
	int dpi=java.awt.Toolkit.getDefaultToolkit().getScreenResolution(); 
	static JLayeredPane Kartenblatt = new JLayeredPane();
	static JLayeredPane KartenblattDruck = new JLayeredPane();
	public void Oeffne�bersicht(String crs, double minEast, double minNorth, double maxEast, double maxNorth, double verhaeltnis)
			throws IOException {

		this.maxEast = maxEast;
		this.maxNorth = maxNorth;
		this.minEast = minEast;
		this.minNorth = minNorth;
		this.crs = crs;
		this.verhaeltnis = verhaeltnis;
		int width=1000;
		
		////////////////////////umwandel in A4Format
		
//		double A4inch= 42.0/2.54;
//		int widthFormat=(int) A4inch*dpi;
//		System.out.println("width: "+widthFormat);	
		
		
        ///////////////////////////////////////
		
		
		
		
		// Hier kommt das Kartenformat, muss noch �bergeben werden
		// Die Wurzel, weil das das Seitenver�ltnis von Din A ist,
		// bei Hochkanten pl�nen muss dies getauscht werden
		int KarteBreite=1000;
		int KarteHoehe = (int) (KarteBreite / (Math.sqrt(2)));

//		int KarteBreiteDruck=(int)widthFormat;
//		int KarteHoeheDruck = (int) (KarteBreiteDruck / (Math.sqrt(2)));
//		
		// Fensterbreite soll nicht den Ganzen Bildschirm bedecken:
		int FensterBreite = (int) ((breite / 5) * 4);
		int FensterHoehe = (int) ((hoehe / 5) * 4);

		JFrame FensterDruckuebersicht = new JFrame("Druck�bersicht");

		// Gr��e dynamisch, sollte in der Bildschirmmitte sein [NOCH ZU PR�FEN]:
		FensterDruckuebersicht.setBounds((int) (breite / (10)), (int) (hoehe / (10)), FensterBreite, FensterHoehe);
	

		// JPanel hat irgendein Layoutged�ns, welches ausgeschalten werden muss, weil
		// sonst die Gr��e der Karten nicht gescheid eingestellt werden kann:
		FensterDruckuebersicht.setLayout(null);

		// Kartenblatt soll das druckbare Papier werden:
		Kartenblatt.setBorder(BorderFactory.createLineBorder(Color.black));
		Kartenblatt.setBounds((int) (KarteBreite / 20), (int) (KarteHoehe / 20), KarteBreite, KarteHoehe);
		Kartenblatt.setBackground(Color.WHITE); 
		Kartenblatt.setLayout(null);

//		KartenblattDruck.setBorder(BorderFactory.createLineBorder(Color.black));
//		KartenblattDruck.setBounds((int) (KarteBreiteDruck / 20), (int) (KarteHoeheDruck / 20), KarteBreiteDruck, KarteHoeheDruck);
//		KartenblattDruck.setBackground(Color.WHITE); 
//		KartenblattDruck.setLayout(null);

		
		JPanel Kartenbild = new JPanel();
//
//		JPanel KartenbildDruck = new JPanel();
		
		LoadKartenBild newMap = new LoadKartenBild(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis,width);
		JLabel actualMap = (JLabel) newMap.showMap();
		Kartenbild.add(actualMap);
		
//		LoadKartenBild newMapDruck = new LoadKartenBild(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis,widthFormat);
//		JLabel actualMapDruck = (JLabel) newMapDruck.showMap();
//		KartenbildDruck.add(actualMapDruck);

		// Kartenbild.setBorder(BorderFactory.createLineBorder(Color.black));
		Kartenbild.setBounds(20, 20, KarteBreite - 40, KarteHoehe - 40);
		Kartenbild.setVisible(true);

//		KartenbildDruck.setBounds(20, 20, KarteBreiteDruck - 40, KarteHoeheDruck - 40);
//		KartenbildDruck.setVisible(true);
//		
		
		
		JButton ButtonDrucken = new JButton("Drucken");
		ButtonDrucken.setBounds((int) ((FensterBreite / 5) * 4), (int) ((FensterHoehe / 10) * 8), 200, 50);
		ButtonDrucken.setActionCommand("Druckmenue");
		
		JButton ButtonSpeichern = new JButton("Speichern");
		ButtonSpeichern.setBounds((int) ((FensterBreite / 5) * 4), (int) ((FensterHoehe / 10) * 7), 200, 50);
		ButtonSpeichern.setActionCommand("Speicher");
		
		JButton ButtonausgewaehlteFarbeNordstern = new JButton("Farbe Nordstern");
		ButtonausgewaehlteFarbeNordstern.setBounds((int) ((FensterBreite / 5) * 4), (int) ((FensterHoehe / 10) * 5), 200, 50);
		ButtonausgewaehlteFarbeNordstern.setActionCommand("ausw�hlen Farbe Nordstern");
		

		JButton ButtonausgewaehlteFarbeMassstab = new JButton("Farbe Massstab");
		ButtonausgewaehlteFarbeMassstab.setBounds((int) ((FensterBreite / 5) * 4), (int) ((FensterHoehe / 10) * 3), 200, 50);
		ButtonausgewaehlteFarbeMassstab.setActionCommand("ausw�hlen Farbe Massstab");
		
		
		JButton ButtonausgewaehlteFarbeGitter = new JButton("Farbe Gitter");
		ButtonausgewaehlteFarbeGitter.setBounds((int) ((FensterBreite / 5) * 4), (int) ((FensterHoehe / 10) * 1), 200, 50);
		ButtonausgewaehlteFarbeGitter.setActionCommand("ausw�hlen Farbe Gitter");

		ActionListenerDruckuebersicht ActionListenerDruckuebersicht = new ActionListenerDruckuebersicht();
		ButtonDrucken.addActionListener(ActionListenerDruckuebersicht);
		ButtonausgewaehlteFarbeNordstern.addActionListener(ActionListenerDruckuebersicht);
		ButtonausgewaehlteFarbeMassstab.addActionListener(ActionListenerDruckuebersicht);
		ButtonausgewaehlteFarbeGitter.addActionListener(ActionListenerDruckuebersicht);
		ButtonSpeichern.addActionListener(ActionListenerDruckuebersicht);
		
		// Massstabsleiste implementieren:
		Massstabsleiste Ml = new Massstabsleiste();
		Ml.erstelleMassstabsleiste(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis,width);
		Ml.setBounds(((int) ((KarteBreite / 10)*6)), (int) (KarteHoehe / 10)*9, ((KarteHoehe /10)*7), (KarteHoehe /10));
		
		
		
		// Nordpfeil einf�gen:
		Nordpfeil nordpfeil = new Nordpfeil();
		nordpfeil.setBounds(((int) (KarteBreite / 10) * 8), (int) (KarteHoehe / 10), 300, 300);
	
		// Drag and Drop:		
		Verschieben vsNordpfeil = new Verschieben(nordpfeil);
		Verschieben vsMassstab = new Verschieben(Ml);
		
		
	
		
	    // Koordinatengitter:
		Koordinatengitter Koordgitter = new Koordinatengitter();
		Koordgitter.erzeugeKoordinatengitter(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis, width);
		Koordgitter.setBounds(0, 0, KarteBreite, KarteHoehe);
		
		nordpfeil.setVisible(true);
		Ml.setVisible(true);
		Koordgitter.setVisible(true);
		
		
		FensterDruckuebersicht.add(ButtonSpeichern);
		FensterDruckuebersicht.add(ButtonDrucken);
		FensterDruckuebersicht.add(ButtonausgewaehlteFarbeMassstab);
		FensterDruckuebersicht.add(ButtonausgewaehlteFarbeNordstern);
		FensterDruckuebersicht.add(ButtonausgewaehlteFarbeGitter);

		Kartenblatt.add(Koordgitter);
		Kartenblatt.add(Ml);
		Kartenblatt.add(nordpfeil);
		Kartenblatt.add(Kartenbild);
		
		
		
		Kartenblatt.setLayer(Koordgitter,400);
		Kartenblatt.setLayer(nordpfeil, 400);
		Kartenblatt.setLayer(nordpfeil, 400);
		Kartenblatt.setLayer(Ml, 400);
		
	FensterDruckuebersicht.add(Kartenblatt);
		FensterDruckuebersicht.setVisible(true);
//		
//		Koordgitter.setWidth(widthFormat);
//		Ml.setWidth(widthFormat);
//		
//		KartenblattDruck.add(Koordgitter);
//		KartenblattDruck.add(Ml);
//		KartenblattDruck.add(nordpfeil);
//		KartenblattDruck.add(Kartenbild);
//		
//		
//		
//		KartenblattDruck.setLayer(Koordgitter,400);
//		KartenblattDruck.setLayer(nordpfeil, 400);
//		KartenblattDruck.setLayer(nordpfeil, 400);
//		KartenblattDruck.setLayer(Ml, 400);
		// Kartenblatt.add(nordpfeil);
		
		
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