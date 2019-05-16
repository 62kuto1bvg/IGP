package Client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Druckausgabe {
	double maxEast;
	double maxNorth;
	double minEast;
	double minNorth;
	double verhaeltnis;
	String crs;
	static JLayeredPane KartenblattDruck = new JLayeredPane();
	public void erstelleDruckausgabe(String crs, double minEast, double minNorth, double maxEast, double maxNorth,
			double verhaeltnis, Nordpfeil nordpfeil) throws IOException {
			
		{

			JFrame FensterDruckausgabe = new JFrame();

			this.maxEast = maxEast;
			this.maxNorth = maxNorth;
			this.minEast = minEast;
			this.minNorth = minNorth;
			this.crs = crs;
			this.verhaeltnis = verhaeltnis;

			//////////////////////////////////////////// Berechnungen für das Kartenformat

			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			double breite = dim.getWidth();
			double hoehe = dim.getHeight();
			int dpi = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
		

			//////////////////////// umwandel in A4Format

			double A4inch = 42.0 / 2.54;
			int widthFormat = (int) A4inch * dpi;
			System.out.println("width: " + widthFormat);
			double verhaeltnissUebersichtDruck = (widthFormat / 1000.00);
			///////////////////////////////////////
			int KarteBreiteDruck = (int) widthFormat;
			int KarteHoeheDruck = (int) (KarteBreiteDruck / (Math.sqrt(2)));

			JFrame FensterDruck = new JFrame("Druck");

			FensterDruck.setBounds(0, 0, KarteBreiteDruck, KarteHoeheDruck);

			// JPanel hat irgendein Layoutgedöns, welches ausgeschalten werden muss, weil
			// sonst die Größe der Karten nicht gescheid eingestellt werden kann:
			FensterDruck.setLayout(null);

			// Kartenblatt soll das druckbare Papier werden:
			KartenblattDruck.setBorder(BorderFactory.createLineBorder(Color.black));
			KartenblattDruck.setBounds(0, 0, KarteBreiteDruck, KarteHoeheDruck);
			KartenblattDruck.setBackground(Color.WHITE);
			KartenblattDruck.setLayout(null);

			JPanel KartenbildDruck = new JPanel();

			LoadKartenBild newMapDruck = new LoadKartenBild(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis,
					widthFormat);
			JLabel actualMapDruck = (JLabel) newMapDruck.showMap();
			KartenbildDruck.add(actualMapDruck);

			KartenbildDruck.setBorder(BorderFactory.createLineBorder(Color.black));
			KartenbildDruck.setBounds(20, 20, KarteBreiteDruck - 40, KarteHoeheDruck - 40);
			KartenbildDruck.setVisible(true);
			KartenblattDruck.setLayout(null);
			
			Koordinatengitter Koordgitter = new Koordinatengitter();
			Koordgitter.erzeugeKoordinatengitter(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis, widthFormat);
			Koordgitter.setBounds(0, 0, KarteBreiteDruck, KarteHoeheDruck);

			
			
			
			

//////////////////////////////////////////////////////////////////////////
			ArrayList<Component> Kartenelemente = Verschieben.Kartenelemente;

			for (int i = 0; i < Kartenelemente.size(); i++) {
				System.out.println(Kartenelemente.get(i).getClass().toString());
				if (Kartenelemente.get(i).getClass().toString().contains("Nordpfeil")) {
					System.out.println("verhaeltnissUebersichtDruck "+verhaeltnissUebersichtDruck);
				
					Nordpfeil.x=(verhaeltnissUebersichtDruck);
					int Width = (int) ((Kartenelemente.get(i).getX()) * verhaeltnissUebersichtDruck);
					System.out.println("Width=" + Width);
					int Height = (int) ((Kartenelemente.get(i).getY()) * verhaeltnissUebersichtDruck);
					System.out.println("Height=" + Height);

					Nordpfeil nordpeil = new Nordpfeil();
					nordpeil.setVisible(true);
					nordpeil.setBounds(Width, Height, 300, 300);
					KartenblattDruck.add(nordpeil);
					KartenblattDruck.setLayer(nordpfeil, 400);
				
				
				
				
				}  else if(Kartenelemente.get(i).getClass().toString().contains("leiste")) {
					System.out.println("NEEEEEEEEEEEEEEEIIIIIIIIIIIIIIIIIINNNNNNNNNN");
				}
			}
			KartenblattDruck.add(KartenbildDruck);
			KartenbildDruck.setVisible(true);
			KartenblattDruck.add(Koordgitter);
			KartenblattDruck.setLayer(Koordgitter,400);
			KartenblattDruck.setLayer(Koordgitter,400);
			KartenblattDruck.setLayer(KartenbildDruck,0);
			
			
			
			
		

			FensterDruck.add(KartenblattDruck);
			FensterDruck.setVisible(true);

		}
	}

}