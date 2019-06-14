package Client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Druckuebersicht extends JPanel  {
	static JFrame FensterDruckuebersicht = new JFrame("Druckübersicht");
	static double maxEast;
	static double maxNorth;
	static double minEast;
	static double minNorth;
	static double verhaeltnis;
	static String crs;
	int X,Y;
	static Color ausgewaehlteFarbeNordstern;
	static Color ausgewaehlteFarbeMassstabsleiste;
	static Color ausgewaehlteFarbeKoordinatengitter;
	static Nordpfeil nordpfeil = new Nordpfeil();
	static LayerLegende LayerLegende =new LayerLegende();
	// Bildschirmgröße herrausfinden (für dynamisches Fenster):
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	double breite = dim.getWidth();
	double hoehe = dim.getHeight();
	//dpi ist die Pixel pro zoll
	int dpi=java.awt.Toolkit.getDefaultToolkit().getScreenResolution(); 
	static JLayeredPane Kartenblatt = new JLayeredPane();
	static String LegendeSkalierungsmodus="Aus";
	static Color ausgewählteSkalierung=Color.LIGHT_GRAY;
	//Skalierung Skalierung=new Skalierung();	
	int KarteHoehe;
	int KarteBreite;
	
	public void OeffneÜbersicht(String crs, double minEast, double minNorth, double maxEast, double maxNorth, double verhaeltnis)
			throws IOException {
		
	
		this.maxEast = maxEast;
		this.maxNorth = maxNorth;
		this.minEast = minEast;
		this.minNorth = minNorth;
		this.crs = crs;
		this.verhaeltnis = verhaeltnis;
		int width=1000;
		
	
		
		
		// Die Wurzel(2), weil das das Seitenverältnis von Din A ist,
	
		 KarteBreite=1000;
		 KarteHoehe = (int) (KarteBreite / (Math.sqrt(2)));


		// Fensterbreite soll nicht den Ganzen Bildschirm bedecken:
		int FensterBreite = (int) ((breite / 5) * 4);
		int FensterHoehe = (int) ((hoehe / 5) * 4);

	
	
		
		// Größe dynamisch, sollte in der Bildschirmmitte sein [NOCH ZU PRÜFEN]:
		FensterDruckuebersicht.setBounds((int) (breite / (10)), (int) (hoehe / (10)), FensterBreite, FensterHoehe);
	

		// JPanel hat irgendein Layoutgedöns, welches ausgeschalten werden muss, weil
		// sonst die Größe der Karten nicht gescheid eingestellt werden kann:
		FensterDruckuebersicht.setLayout(null);

		// Kartenblatt soll das druckbare Papier werden:
		Kartenblatt.setLayout(null);
		Kartenblatt.setBounds((int) (KarteBreite / 20), (int) (KarteHoehe / 20), KarteBreite, KarteHoehe);
		Kartenblatt.setBackground(Color.RED);
	


		//Kartenbild ist das Bild, welches vom WmsServer gedownloadet wird
		JPanel Kartenbild = new JPanel();
		Kartenbild.setBackground(Color.WHITE);
		//Bild wird in ein JLabel gesteckt
		LoadKartenBild newMap = new LoadKartenBild(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis,width);
		JLabel actualMap = (JLabel) newMap.showMap();
		Kartenbild.add(actualMap);


		Kartenbild.setBorder(BorderFactory.createLineBorder(Color.black));
		Kartenbild.setBounds(KarteBreite/40, KarteHoehe/40,( KarteBreite - (KarteBreite/20)), KarteHoehe - (KarteHoehe/20));
		Kartenbild.setVisible(true);
	

		
		JButton ButtonDrucken = new JButton("Drucken");
		ButtonDrucken.setBounds((int) ((FensterBreite / 5) * 4), (int) ((FensterHoehe / 10) * 8.5), 200, 50);
		ButtonDrucken.setActionCommand("Druckmenue");
		
		JButton ButtonSpeichern = new JButton("Speichern");
		ButtonSpeichern.setBounds((int) ((FensterBreite / 5) * 4), (int) ((FensterHoehe / 10) * 7.5), 200, 50);
		ButtonSpeichern.setActionCommand("Speicher");
		
		
		
		JButton ButtonLegendeSkalieren = new JButton("Legende skalieren");
		ButtonLegendeSkalieren.setBounds((int) ((FensterBreite / 5) * 4), (int) ((FensterHoehe / 10) * 5), 200, 50);
		ButtonLegendeSkalieren.setBackground(ausgewählteSkalierung);
		ButtonLegendeSkalieren.setActionCommand("LegendeSkalieren");
		
		
		JButton ButtonausgewaehlteFarbeNordstern = new JButton("Farbe Nordstern");
		ButtonausgewaehlteFarbeNordstern.setBounds((int) ((FensterBreite / 5) * 4), (int) ((FensterHoehe / 10) * 3), 200, 50);
		ButtonausgewaehlteFarbeNordstern.setActionCommand("auswählen Farbe Nordstern");
		

		JButton ButtonausgewaehlteFarbeMassstab = new JButton("Farbe Massstab");
		ButtonausgewaehlteFarbeMassstab.setBounds((int) ((FensterBreite / 5) * 4), (int) ((FensterHoehe / 10) * 2), 200, 50);
		ButtonausgewaehlteFarbeMassstab.setActionCommand("auswählen Farbe Massstab");
		
		
		JButton ButtonausgewaehlteFarbeGitter = new JButton("Farbe Gitter");
		ButtonausgewaehlteFarbeGitter.setBounds((int) ((FensterBreite / 5) * 4), (int) ((FensterHoehe / 10) * 1), 200, 50);
		ButtonausgewaehlteFarbeGitter.setActionCommand("auswählen Farbe Gitter");

		ActionListenerDruckuebersicht ActionListenerDruckuebersicht = new ActionListenerDruckuebersicht();
		ButtonDrucken.addActionListener(ActionListenerDruckuebersicht);
		ButtonausgewaehlteFarbeNordstern.addActionListener(ActionListenerDruckuebersicht);
		ButtonausgewaehlteFarbeMassstab.addActionListener(ActionListenerDruckuebersicht);
		ButtonausgewaehlteFarbeGitter.addActionListener(ActionListenerDruckuebersicht);
		ButtonSpeichern.addActionListener(ActionListenerDruckuebersicht);
		ButtonLegendeSkalieren.addActionListener(ActionListenerDruckuebersicht);
		
		// Massstabsleiste implementieren:
		Massstabsleiste Ml = new Massstabsleiste();
		Ml.erstelleMassstabsleiste(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis,width,1);
		Ml.setBounds(((int) ((KarteBreite / 10)*1)), (int) (KarteHoehe / 10)*8, ((KarteHoehe /10)*7), (KarteHoehe /10));
		
		LayerLegende.setBounds((int) (KarteBreite / 10) * 8, KarteHoehe/40,(((int) (KarteBreite / 10) * 2)-(KarteBreite/40)),KarteHoehe - (KarteHoehe/20));
		int Legendenbreite=(int)(( (KarteBreite / 10) * 2)-(KarteBreite/40));
		
		int Legendenhoehe=KarteHoehe - (KarteHoehe/20);
		
		int LegendenKartenbreite=(int)(Legendenbreite/5)*3;
		
		
		//umrechnen der Bildschirmbreite in "echte" CM
		double breite = dim.getWidth();
		double hoehe = dim.getHeight();
		int dpi = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
		

		
		LayerLegende.fuelleLegende(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis, width,Legendenbreite,Legendenhoehe,LegendenKartenbreite,1,0);
		// Nordpfeil einfügen:
		nordpfeil.setBounds(((int) (KarteBreite / 10) * 1), (int) (KarteHoehe / 10), 300, 300);
	
		// Drag and Drop:		
		
		
		
		//Wenn die Legende im Skaliermodus ist, darf Diese nicht verschoben werden
			 
			
		Verschieben vsLayerLegende= new Verschieben(LayerLegende);
		Verschieben vsNordpfeil = new Verschieben(nordpfeil);
		Verschieben vsMassstab = new Verschieben(Ml);		
		
		
	    // Koordinatengitter:
		Koordinatengitter Koordgitter = new Koordinatengitter();
		Koordgitter.erzeugeKoordinatengitter(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis, width,1);
		Koordgitter.setBounds(KarteBreite/40, KarteHoehe/40,( KarteBreite - (KarteBreite/20)), KarteHoehe - (KarteHoehe/20));
		
		
		LayerLegende.setVisible(true);
		nordpfeil.setVisible(true);
		Ml.setVisible(true);
		Koordgitter.setVisible(true);
		
		
		//FensterDruckuebersicht.add(ButtonLegendeSkalieren);
		FensterDruckuebersicht.add(ButtonSpeichern);
		FensterDruckuebersicht.add(ButtonDrucken);
		FensterDruckuebersicht.add(ButtonausgewaehlteFarbeMassstab);
		FensterDruckuebersicht.add(ButtonausgewaehlteFarbeNordstern);
		FensterDruckuebersicht.add(ButtonausgewaehlteFarbeGitter);
		
		
		
		JPanel Hintergrund=new JPanel();
		Hintergrund.setLayout(null);
		Hintergrund.setBounds(0,0,KarteBreite, KarteHoehe);
		Hintergrund.setBorder(BorderFactory.createLineBorder(Color.black));
		Hintergrund.setBackground(Color.WHITE);
		Hintergrund.setVisible(true);
	
	
		
		Rahmen Rahmen=new Rahmen(KarteBreite,KarteHoehe,1);
		Rahmen.setBounds(0, 0,KarteBreite,KarteHoehe);
		Rahmen.setVisible(true);
		
		Kartenblatt.setLayout(null);
		Kartenblatt.add(Rahmen);
		Kartenblatt.add(LayerLegende);
		Kartenblatt.add(Koordgitter);
		Kartenblatt.add(Ml);
		Kartenblatt.add(nordpfeil);
		Kartenblatt.add(Kartenbild);
		Kartenblatt.add(Hintergrund);
		
		

		Kartenblatt.setBackground(Color.WHITE);
		//Kartenblatt.setLayer(Skalierung,500);
		Kartenblatt.setLayer(LayerLegende,900);
		Kartenblatt.setLayer(Rahmen,1000);
		Kartenblatt.setLayer(Koordgitter,400);
		Kartenblatt.setLayer(nordpfeil, 400);
		Kartenblatt.setLayer(Ml, 400);
		
		
		
		FensterDruckuebersicht.add(Kartenblatt);
		FensterDruckuebersicht.setVisible(true);
		
			
	}

		
		
	
	
	public static Color getAusgewählteSkalierung() {
		return ausgewählteSkalierung;
	}

	public static void setAusgewählteSkalierung(Color ausgewählteSkalierung) {
		Druckuebersicht.ausgewählteSkalierung = ausgewählteSkalierung;
	}

	public double getMaxEast() {
		return maxEast;
	}

	public void setMaxEast(double maxEast) {
		this.maxEast = maxEast;
	}

	public double getMaxNorth() {
		return maxNorth;
	}

	public void setMaxNorth(double maxNorth) {
		this.maxNorth = maxNorth;
	}

	public double getMinEast() {
		return minEast;
	}

	public void setMinEast(double minEast) {
		this.minEast = minEast;
	}

	public double getMinNorth() {
		return minNorth;
	}

	public void setMinNorth(double minNorth) {
		this.minNorth = minNorth;
	}

	public Nordpfeil getNordpfeil() {
		return nordpfeil;
	}

	public void setNordpfeil(Nordpfeil nordpfeil) {
		this.nordpfeil = nordpfeil;
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