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
//Diese Klasse ist ähnlich der Druckübersicht, wurde aber erstellt weil die Druckübersicht nur 1000 Pixel breit ist, wir aber eine groesser Pixeldichte der Karte wollen
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

			////////////////// Breiten der DinFormate/////////////////////////////

			double breitenDinFormate[] = { 29.7, 42.0, 59.4, 84.1, 118, 9 };

			//////////////////////// umwandel in DIN-Format anhand der opixeldichte wird
			//////////////////////// umgerechnet, wieviele Pixel das bild braucht, um beim
			//////////////////////// ausdrucken in dem jeweiligem Format gut auszusehen

			int index = ActionListenerMap.AuswahlFormatIndex;
			double A4inch = breitenDinFormate[index] / 2.54;
			int widthFormat = (int) A4inch * dpi;
			double verhaeltnissUebersichtDruck = (widthFormat / 1000.00);//Verhaelnis zwischen Druckuebersichtsbreite und tatsaechlicher Druckbreite

			/////////////////////////////////////// Erstellen des Panels

			int KarteBreiteDruck = (int) widthFormat;
			int KarteHoeheDruck = (int) (KarteBreiteDruck / (Math.sqrt(2)));

			JFrame FensterDruck = new JFrame("Druck");

			FensterDruck.setBounds(1, 1, 1, 1);

			// JPanel hat irgendein Layoutgedöns, welches ausgeschalten werden muss, weil
			// sonst die Größe der Karten nicht gescheid eingestellt werden kann:
			FensterDruck.setLayout(null);

			// Kartenblatt soll das druckbare Papier werden:
			KartenblattDruck.setBorder(BorderFactory.createLineBorder(Color.black));
			KartenblattDruck.setBounds(0, 0, KarteBreiteDruck, KarteHoeheDruck);
			KartenblattDruck.setBackground(Color.WHITE);
			KartenblattDruck.setLayout(null);

			// Laden der Karte, diesmal aber mit mehr pixeln
			JPanel KartenbildDruck = new JPanel();
			LoadKartenBild newMapDruck = new LoadKartenBild(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis,
					widthFormat);
			JLabel actualMapDruck = (JLabel) newMapDruck.showMap();
			KartenbildDruck.add(actualMapDruck);

			KartenbildDruck.setBorder(BorderFactory.createLineBorder(Color.black));
			KartenbildDruck.setBounds(20, 20, KarteBreiteDruck - 40, KarteHoeheDruck - 40);
			KartenbildDruck.setVisible(true);
			KartenblattDruck.setLayout(null);

			// Erzeugen des Koordinatengitters

			Koordinatengitter Koordgitter = new Koordinatengitter();
			Koordgitter.erzeugeKoordinatengitter(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis, widthFormat);
			Koordgitter.setBounds(0, 0, KarteBreiteDruck, KarteHoeheDruck);

			//////////////////////Problematisch ist jetzt die Drag and Drop funktion in der Druckansicht.////////////////////////////////////////////////////
			//Da die Kartenelemente verschoben worden sind, muessen dies auch in der druckzusammenstellung verschoben werden.
			int XNordpfeil = 0;
			int YNordpfeil = 0;
			int Xmleiste = 0;
			int Ymleiste = 0;
			int Xmlegende = 0;
			int Ymlegende = 0;
			
			Nordpfeil.x = (verhaeltnissUebersichtDruck);

			
			//die Kartenelemente aus der Druckuebersicht wurden in einen Statische Liste verschoben
			//diese Elemente ziehe ich jetzt raus, damit ich die verschiebung nachvollziehen kann.
			ArrayList<Component> Kartenelemente = Verschieben.Kartenelemente;

			for (int i = 0; i < Kartenelemente.size(); i++) {
				
				System.out.println(Kartenelemente.get(i).getClass().toString());
				if (Kartenelemente.get(i).getClass().toString().contains("Nordpfeil")) {

				//Mithilfe von "verhaeltnissUebersichtDruck" wird der Masstab der Transformation der Lagekorrdinaten der Elemente angegeben, da das Blatt nun größer wird
					XNordpfeil = (int) ((Kartenelemente.get(i).getX()) * verhaeltnissUebersichtDruck);
					YNordpfeil = (int) ((Kartenelemente.get(i).getY()) * verhaeltnissUebersichtDruck);

				} else if (Kartenelemente.get(i).getClass().toString().contains("leiste")) {

					Xmleiste = (int) ((Kartenelemente.get(i).getX()) * verhaeltnissUebersichtDruck);
					Ymleiste = (int) ((Kartenelemente.get(i).getY()) * verhaeltnissUebersichtDruck);
				}
				else if (Kartenelemente.get(i).getClass().toString().contains("Legende")) {

					Xmlegende = (int) ((Kartenelemente.get(i).getX()) * verhaeltnissUebersichtDruck);
					Ymlegende = (int) ((Kartenelemente.get(i).getY()) * verhaeltnissUebersichtDruck);
				}
				
				
				
				
				
			}

			Nordpfeil nordpeil = new Nordpfeil();
			LayerLegende ll=new LayerLegende();
			nordpeil.setVisible(true);
			nordpeil.setBounds(XNordpfeil, YNordpfeil, 800, 800);
			Nordpfeil.x=verhaeltnissUebersichtDruck;
			KartenblattDruck.add(nordpeil);

			Massstabsleiste Ml = new Massstabsleiste();
			Ml.erstelleMassstabsleiste(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis, widthFormat);
			nordpeil.setVisible(true);
			Ml.setBounds((Xmleiste), Ymleiste, ((KarteHoeheDruck / 10) * 7), (KarteHoeheDruck / 10));
			KartenblattDruck.add(Ml);

			ll.setBounds(Xmlegende,Ymlegende,(int) (KarteBreiteDruck / 10) * 2,KarteHoeheDruck);
			ll.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
		
			KartenblattDruck.add(ll);
			
			KartenblattDruck.add(KartenbildDruck);
			KartenbildDruck.setVisible(true);

			
			KartenblattDruck.add(Koordgitter);
			KartenblattDruck.setLayer(nordpfeil, 400);
			KartenblattDruck.setLayer(Koordgitter, 300);
			KartenblattDruck.setLayer(ll, 400);
			KartenblattDruck.setLayer(KartenbildDruck, 0);

			FensterDruck.add(KartenblattDruck);
			FensterDruck.setVisible(true);

		}
	}

}