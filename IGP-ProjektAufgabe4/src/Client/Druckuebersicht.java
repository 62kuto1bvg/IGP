package Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

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
	static JLayeredPane Kartenblatt = new JLayeredPane();
	
	
	public void OeffneÜbersicht(String crs, double minx, double miny, double maxx, double maxy, double verhaeltnis) throws IOException {

		this.maxx = maxx;
		this.maxy = maxy;
		this.minx = minx;
		this.miny = miny;
		this.crs = crs;
		this.verhaeltnis = verhaeltnis;

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
		// Programm wird geschlossen beim schliesen
		FensterDruckuebersicht.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// JPanel hat irgendein Layoutgedöns, welchesAusgeschalten werden muss, weil
		// sonst die Grosse der Karten nicht gescheit eingestellt werden Konnte
		FensterDruckuebersicht.setLayout(null);

		//Kartenblatt soll das druckbare Papier werden
	
		
		Kartenblatt.setBorder(BorderFactory.createLineBorder(Color.black));
		Kartenblatt.setBounds((int) (KarteBreite / 20), (int) (KarteHoehe / 20), KarteBreite, KarteHoehe);
		Kartenblatt.setBackground(Color.WHITE);
		Kartenblatt.setLayout(null);
		
		 JPanel Kartenbild = new JPanel();
		 
		 LoadKartenBild newMap = new LoadKartenBild(crs,minx, miny, maxx, maxy, verhaeltnis); 				
		 JLabel actualMap = (JLabel) newMap.showMap();
		 Kartenbild.add(actualMap);
		 
		// Kartenbild.setBorder(BorderFactory.createLineBorder(Color.black));
		 Kartenbild.setBounds(20,20,KarteBreite-40, KarteHoehe-40);
		 Kartenbild.setVisible(true);
		 
		 JButton ButtonDrucken = new JButton("Drucken");
		 ButtonDrucken.setBounds((int)((FensterBreite/5)*4), (int)((FensterHoehe/5)*4), 200, 50);
		 ButtonDrucken.setActionCommand("Druckmenue");
		
		 
		 ActionListenerDruckuebersicht ActionListenerDruckuebersicht = new ActionListenerDruckuebersicht();
		 ButtonDrucken.addActionListener(ActionListenerDruckuebersicht);
		
		 
		 
		FensterDruckuebersicht.add(ButtonDrucken);	  	
	
		Kartenblatt.add(Kartenbild);
		Kartenblatt.setLayer(Kartenblatt, 10);
		
		FensterDruckuebersicht.add(Kartenblatt);

		 
			
	
	
		 
		FensterDruckuebersicht.setVisible(true);
	
	
	}


	public static void paint(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

}
