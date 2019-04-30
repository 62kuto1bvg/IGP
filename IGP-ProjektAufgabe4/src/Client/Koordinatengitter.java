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

		double betaVereinfacht = 0;
		double betaiteration = beta;
		int betaiterationcounter = 0;

		double KoordinatengitterStartxiteration = minx;
		double KoordinatengitterStartxVereinfacht = 0;
		System.out.println("beta " + beta);

		// der winkel beta muss aehnlich wie bei der Massstabsleiste vereinfacht werden.
		// Hierfuer muss ersteinmal ueberprueft werden, ob diese groesser oder kleiner
		// als 1Grad ist
		// fuer beta muss ein iterationshilfswert bbenutzt werden

		///////////////////////////////////////////////////////////////////////////////////////////////////
		if (betaiteration < 1.00) {

			do {
				betaiteration = betaiteration * 10;

				betaiterationcounter = betaiterationcounter + 1;

				System.out.println("betaiteration " + betaiteration);
				System.out.println("betaiterationcounter " + betaiterationcounter);

				// zur vereinfachung wird die zahl gerundet-

				double betaiterationVereinfacht = Math.round(betaiteration);
				System.out.println("KLEINERE  betaiterationVereinfacht " + betaiterationVereinfacht);
				// und nun wieder in die Ursprungliche dimension umgewandelt

				if (betaiterationcounter == 0) {

					betaVereinfacht = betaiterationVereinfacht;

				}

				else {
					betaVereinfacht = betaiterationVereinfacht / Math.pow(10, betaiterationcounter);
				}
				System.out.println("KLEINERE  betaVereinfacht " + betaVereinfacht);
				// somit wurden zumbeispiel aus 0,337854 Grad jetzt 0.3grad generiert
				System.out.println("betaVereinfacht " + betaVereinfacht);

			} while (betaiteration <= 1);

			// Ausserdem wird noch ein startwert benoetigt um von diesm die koordinatenwerte
			// abzutragen
			KoordinatengitterStartxiteration = KoordinatengitterStartxiteration * Math.pow(10, betaiterationcounter);

			double iterationKoordinatengitterStartxVereinfacht = Math.round(KoordinatengitterStartxiteration);

			KoordinatengitterStartxVereinfacht = iterationKoordinatengitterStartxVereinfacht
					/ Math.pow(10, betaiterationcounter);

			System.out.println("min x " + minx);
			System.out.println("KoordinatengitterStartxVereinfacht x " + KoordinatengitterStartxVereinfacht);

		} else if (betaiteration > 1) {

			betaVereinfacht = Math.round(betaiteration);

			KoordinatengitterStartxVereinfacht = Math.round(KoordinatengitterStartxiteration);

			System.out.println("minx " + minx);
			System.out.println("KoordinatengitterStartxVereinfachtx " + KoordinatengitterStartxVereinfacht);

		}

		// um sicherzugehen, das zwischen dem gewaehlten(und gerundetem wert und dem
		// startwert keine zu grosse luecke ist wird manuel einen Breitengrad
		// davorgeschoben

		double ersterKoordinatengitterwertx = KoordinatengitterStartxVereinfacht - (betaVereinfacht / 6);
		Laengengrade.add(ersterKoordinatengitterwertx);
		System.out.println("ersterKoordinatengitterwert " + ersterKoordinatengitterwertx);

		///////////////////////////////////////////////////////////////////////////////////////////////////
		// nun werden die anderen Linien erzeugt.
		for (int i = 1; i < 7; i++) {
			double Koordinatengitterwertx = KoordinatengitterStartxVereinfacht + ((betaVereinfacht / 6) * i);
			Laengengrade.add(Koordinatengitterwertx);

		}

		for (int j = 0; j < Laengengrade.size(); j++) {

			double Laenge = Laengengrade.get(j);
			int LaengeBildschirm = (int) (((Laenge - minx) * width) / beta);
			Laengengradetransformiert.add(LaengeBildschirm);
		}

		for (int j = 0; j < Laengengradetransformiert.size(); j++) {
			System.out.println(Laengengradetransformiert.get(j).toString());
		}

		// jetzt muss allerdings noch die lage auf dem Bildschirm berechnet werden.

	}

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

	}

}
