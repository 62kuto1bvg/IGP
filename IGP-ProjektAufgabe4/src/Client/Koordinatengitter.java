package Client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Koordinatengitter extends JPanel {

	double maxx;
	double maxy;
	double minx;
	double miny;
	double verhaeltnis;
	String crs;
	double DeltaX, DeltaY;
	int width;
	ArrayList<Integer> Laengengradetransformiert = new ArrayList<>();
	ArrayList<Double> Laengengrade = new ArrayList<>();

	ArrayList<Integer> Breitengradetransformiert = new ArrayList<>();
	ArrayList<Double> Breitengrade = new ArrayList<>();

	public void erzeugeKoordinatengitter(String crs, double minx, double miny, double maxx, double maxy,
			double verhaeltnis, int width) {

		this.crs = crs;
		this.minx = minx;
		// this.miny = miny;
		this.maxx = maxx;
		// this.maxy = maxy;
		this.verhaeltnis = verhaeltnis;
		this.width = (int) width;

		// Behelf um Voerst den Kartenausschnitt auf DIN Format zu bekommen

		DeltaX = maxx - minx;
		DeltaY = maxy - miny;

		double Verhal = DeltaX / Math.sqrt(2);
		double Abzug = (DeltaX - Verhal) / 2;

		this.miny = miny + Abzug;
		this.maxy = maxy - Abzug;

		// rechnen der winkel
		// Alpha ist der winkel zwischen Aeuquator und den punkt auf der Kugel

		// Umkreis auf Breitengrad

		// Winkel Bogensegment
		double beta = ((maxx - minx));
		// winkelzwischen den breitengrade
		double alpha = (maxy - miny);
		// iterationsverte, um winkel, welche kleiner sind als 1 zu verinfachen
		double betaVereinfacht = 0;
		double betaiteration = beta;
		int betaiterationcounter = 0;

		double alphaVereinfacht = 0;
		double alphaiteration = alpha;
		int alphaiterationcounter = 0;

		double KoordinatengitterStartxiteration = minx;
		double KoordinatengitterStartxVereinfacht = 0;

		double KoordinatengitterStartyiteration = miny;
		double KoordinatengitterStartyVereinfacht = 0;

		// der winkel beta muss aehnlich wie bei der Massstabsleiste vereinfacht werden.
		// Hierfuer muss ersteinmal ueberprueft werden, ob diese groesser oder kleiner
		// als 1Grad ist
		// fuer beta muss ein iterationshilfswert benutzt werden
		// zusätzlich wird ein Counter

		///////////////////////////////////////////////////////////////////////////////////////////////////

		if (betaiteration < 1.00) {

			do {
				betaiteration = betaiteration * 10;

				betaiterationcounter = betaiterationcounter + 1;

				// zur vereinfachung wird die zahl gerundet-

				double betaiterationVereinfacht = Math.round(betaiteration);

				// und nun wieder in die Ursprungliche dimension umgewandelt

				if (betaiterationcounter == 0) {
					betaVereinfacht = betaiterationVereinfacht;
				}

				else {
					betaVereinfacht = betaiterationVereinfacht / Math.pow(10, betaiterationcounter);
				}

				// somit wurden zumbeispiel aus 0,337854 Grad jetzt 0.3grad generiert

			} while (betaiteration <= 1);

			// Ausserdem wird noch ein startwert benoetigt um von diesem die
			// koordinatenwerte
			// abzutragen

			KoordinatengitterStartxiteration = KoordinatengitterStartxiteration * Math.pow(10, betaiterationcounter);

			double iterationKoordinatengitterStartxVereinfacht = Math.round(KoordinatengitterStartxiteration);

			KoordinatengitterStartxVereinfacht = iterationKoordinatengitterStartxVereinfacht
					/ Math.pow(10, betaiterationcounter);

		} else if (betaiteration > 1) {

			betaVereinfacht = Math.round(betaiteration);
			KoordinatengitterStartxVereinfacht = Math.round(KoordinatengitterStartxiteration);

		}

		// um sicherzugehen, das zwischen dem gewaehlten(und gerundetem wert und dem
		// startwert keine zu grosse luecke ist wird manuel einen Breitengrad
		// davorgeschoben

		if (alphaiteration < 1.00) {

			do {
				alphaiteration = alphaiteration * 10;
				alphaiterationcounter = alphaiterationcounter + 1;

				// zur vereinfachung wird die zahl gerundet
				double alphaiterationVereinfacht = Math.round(alphaiteration);

				// und nun wieder in die Ursprungliche dimension umgewandelt

				if (alphaiterationcounter == 0) {
					alphaVereinfacht = alphaiterationVereinfacht;
				}

				else {
					alphaVereinfacht = alphaiterationVereinfacht / Math.pow(10, alphaiterationcounter);
				}

				// somit wurden zumbeispiel aus 0,337854 Grad jetzt 0.3grad generiert
				
			} while (alphaiteration <= 1);

			// Ausserdem wird noch ein startwert benoetigt um von diesem die
			// koordinatenwerte
			// abzutragen

			KoordinatengitterStartyiteration = KoordinatengitterStartyiteration * Math.pow(10, alphaiterationcounter);

			double iterationKoordinatengitterStartyVereinfacht = Math.round(KoordinatengitterStartyiteration);

			KoordinatengitterStartyVereinfacht = iterationKoordinatengitterStartyVereinfacht/ Math.pow(10, alphaiterationcounter);
			
			System.out.println("KoordinatengitterStartyVereinfacht"+KoordinatengitterStartyVereinfacht);
	
			
		} else if (alphaiteration > 1) {
			System.out.println("ALPHA  "+alpha);
			alphaVereinfacht = Math.round(alphaiteration);
			
			KoordinatengitterStartyVereinfacht = Math.round(KoordinatengitterStartyiteration);
			System.out.println("KoordinatengitterStartyVereinfachtGross"+KoordinatengitterStartyVereinfacht);
		
			
		}

		// um sicherzugehen, das zwischen dem gewaehlten(und gerundetem wert und dem
		// startwert keine zu grosse luecke ist wird manuel einen Breitengrad
		// davorgeschoben

		double ersterKoordinatengitterwertx = KoordinatengitterStartxVereinfacht - (betaVereinfacht / 6);
		Laengengrade.add(ersterKoordinatengitterwertx);

		double ersterKoordinatengitterwerty = KoordinatengitterStartyVereinfacht - (alphaVereinfacht / 4);
		Breitengrade.add(ersterKoordinatengitterwerty);
		
		System.out.println("ersterKoordinatengitterwerty"+ ersterKoordinatengitterwerty);

		///////////////////////////////////////////////////////////////////////////////////////////////////
		// nun werden die anderen Linien erzeugt.

		for (int i = 1; i < 5; i++) {
			
			double Koordinatengitterwerty = KoordinatengitterStartyVereinfacht + ((alphaVereinfacht / 4) * i);
			Breitengrade.add(Koordinatengitterwerty);
			//System.out.println("BreitengradeAAAAAAA+  "+ Koordinatengitterwerty);
		}

		for (int i = 1; i < 7; i++) {
			double Koordinatengitterwertx = KoordinatengitterStartxVereinfacht + ((betaVereinfacht / 6) * i);
			Laengengrade.add(Koordinatengitterwertx);

		}

		// jetzt muss allerdings noch die Lage auf dem Bildschirm berechnet werden.
		/////////// Transformieren //////////
		for (int j = 0; j < Laengengrade.size(); j++) {

			double Laenge = Laengengrade.get(j);
			int LaengeBildschirm = (int) (((Laenge - minx) * width) / beta);
			Laengengradetransformiert.add(LaengeBildschirm);
		
		}

		for (int j = 0; j < Breitengrade.size(); j++) {

			double Breiten = Breitengrade.get(j);
		
			
			
			int breiteBildschirm=(int) ((Breiten*width*Math.sqrt(2))/alpha);
			Breitengradetransformiert.add(breiteBildschirm);
			
			System.out.println("BREITEEEEEE+  "+ breiteBildschirm);

		}
	}

	//////////////////////// SelbeVorgehensweisefür
	//////////////////////// die///////////////////////////////
	////////////////////////////// Breitengrade//////////////////////////////////////

	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Client.Druckuebersicht.ausgewaehlteFarbeMassstabsleiste);

		// Zeichnen der Senkrechten Linien
		for (int j = 0; j < Laengengradetransformiert.size(); j++) {

			int laenge = Laengengradetransformiert.get(j);
			if (laenge < 0 || laenge > width) {

			} else {
				g2.drawLine(laenge, 0, laenge, (int) (width / Math.sqrt(2)));

				// Beschrieften der Senkrechten Linien

				String Breitetext = String.valueOf(Math.round(Laengengrade.get(j) * 100) / 100.0);
				g2.drawString(Breitetext, laenge + 10, 10);

			}

		}

	
	for (int j = 0; j < Breitengradetransformiert.size(); j++) {
		//System.out.println("HHHHHHHHHHHHHHHHH+  "+Breitengradetransformiert.get(j).toString());
		
		int breite = Breitengradetransformiert.get(j);
		if (breite < 0 || breite > (width/Math.sqrt(2))) {

		} else {
			g2.drawLine(0, breite, 0, (width));

			// Beschriften der Senkrechten Linien

			String Breitetext = String.valueOf(Math.round(Breitengrade.get(j) * 100) / 100.0);
			g2.drawString(Breitetext, breite + 10, 10);

		}

	}
	}

	
	
	
	
	
	
	

}
