package Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Druckuebersicht {
	double maxx;
	double maxy;
	double minx;
	double miny;
	double verhaeltnis;
	String crs;

	// Bildschirmgroesse herrausfinden für dynamisches Fenster

	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	double breite = dim.getWidth();
	double hoehe = dim.getHeight();

	public void OeffneÜbersicht(String crs, double minx, double miny, double maxx, double maxy, double verhaeltnis) throws IOException {

		this.maxx = maxx;
		this.maxy = maxy;
		this.minx = minx;
		this.miny = miny;
		this.crs = crs;
		this.verhaeltnis = verhaeltnis;

		// Hier kommt das Kartenformat, muss noch übergeben werden
		// Die wurzel, weil das das seitenverältnis von Din A ist, bei Hochkanten plänen
		// mus dies getauscht werden

		int KarteBreite = 1000;
		int KarteHoehe = (int) (KarteBreite / (Math.sqrt(2)));

		// Fensterbreite soll nicht den Ganzen Bildschirm bedecken
		int FensterBreite = (int) ((breite / 5) * 4);
		int Fensterhoehe = (int) ((hoehe / 5) * 4);

		JFrame FensterDruckuebersicht = new JFrame("Druckübersicht");

		// Groesse dynamisch, sollte in der Bildschirmmitte sein [NOCH ZU PRUEFEN]

		FensterDruckuebersicht.setBounds((int) (breite / (10)), (int) (hoehe / (10)), FensterBreite, Fensterhoehe);
		// Programm wird geschlossen beim schliesen
		FensterDruckuebersicht.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// JPanel hat irgendein Layoutgedöns, welchesAusgeschalten werden muss, weil
		// sonst die Grosse der Karten nicht gescheit eingestellt werden Konnte
		FensterDruckuebersicht.setLayout(null);

		//Kartenblatt soll das druckbare Papier werden
		JPanel Kartenblatt = new JPanel();
		Kartenblatt.setBorder(BorderFactory.createLineBorder(Color.black));
		Kartenblatt.setBounds((int) (KarteBreite / 10), (int) (KarteHoehe / 10), KarteBreite, KarteHoehe);
		Kartenblatt.setBackground(Color.WHITE);
		

		 JPanel Kartenbild = new JPanel();
		 LoadMap newMap = new LoadMap(crs,minx, miny, maxx, maxy, verhaeltnis); 				
		 JLabel actualMap = (JLabel) newMap.showMap();
		 Kartenbild.add(actualMap);
		 
		 Kartenbild.setBorder(BorderFactory.createLineBorder(Color.black));
		 Kartenbild.setBounds((int) (KarteBreite / 10)+10, (int) (KarteHoehe / 10)+10, ((KarteBreite/3)*2)-20, KarteHoehe-20);
		
		 
			  
		FensterDruckuebersicht.add(Kartenbild);
		FensterDruckuebersicht.add(Kartenblatt);
		FensterDruckuebersicht.setVisible(true);
	
	
	}

}
