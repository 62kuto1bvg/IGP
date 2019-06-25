package Client;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Koordinatengitter extends JPanel {
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////Ist Leider immer noch etwas verbuggt, verstehe aber nicht warum, meine vermutung /////////////////////////////////////////////
//////////////////////////////////////////liegt entweder am verzerrungsfaktor von Mercator, oder aber bei dem verhältniss für den Kartenzoom/////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	double maxEast;
	double maxNorth;
	double minEast;
	double minNorth;
	double verhaeltnis;
	String crs;
	double DeltaX, DeltaY;
	int width;
	int strichdicke;
	ArrayList<Integer> Laengengradetransformiert = new ArrayList<>();
	ArrayList<Double> Laengengrade = new ArrayList<>();

	ArrayList<Double> Breitengradetransformiert = new ArrayList<>();
	ArrayList<Double> Breitengrade = new ArrayList<>();

	public void erzeugeKoordinatengitter(String crs, double minE, double minN, double maxE, double maxN,
			double verhaeltnis, int width, int strichdicke) {

		this.crs = crs;
		this.minEast = minE;
		// this.minNorth = minNorth;
		this.maxEast = maxE;
		// this.maxNorth = maxNorth;
		this.verhaeltnis = verhaeltnis;
		this.width = (int) width;
		this.strichdicke=strichdicke;
		// Behelf, um voerst den Kartenausschnitt auf DIN Format zu bekommen, da der
		// Kartenauschnitt beim Zoomen quadratisch ist:

		if (ActionListenerMap.auswaehlbarerBereichStatus.contains("Navigation")) {
			// Es wird das was von einer Quatratischen fläche oben und unten für ein
			// Dinformat zu viel ist abgschnitten
			DeltaX = maxEast - minEast;
			DeltaY = maxNorth - minNorth;
			double Verhal = DeltaX / Math.sqrt(2);
			double Abzug = (DeltaX - Verhal) / 2;
			this.minNorth = minN + Abzug;
			this.maxNorth = maxN - Abzug;
		}

		// Wenn der Auschnitt durch die Masstabseingabe oder das Aufziehbare Fenster
		// erfolgt ist, dann ist die BBox schon im richtigen Seitenverhältniss
		else {
			this.minNorth = minN;
			this.maxNorth = maxN;
		}

		// Geometrische Lösung mithilfe der erde als Kügel und Bogenmasse

		// Rechnen der Winkel:

		// Winkel Bogensegment:
		double beta = ((maxEast - minEast));
		// Winkel zwischen den Breitengraden:

		// Alpha ist der Winkel zwischen Äquator und dem Punkt auf der Kugel
		double alpha = (maxNorth - minNorth);

		// Iterationswerte, um Winkel welche kleiner sind als 1 zu vereinfachen:
		double betaVereinfacht = 0;
		double betaiteration = beta;
		int betaiterationcounter = 0;

		//////////////////////////////////////////////////////////////////////////////////////////////////

		double alphaVereinfacht = 0;
		double alphaiteration = alpha;
		int alphaiterationcounter = 0;
		///////////////////////////////////////////////////////////////////////////////////////////////////
		double KoordinatengitterStartxiteration = minEast;
		double KoordinatengitterStartxVereinfacht = 0;

		double KoordinatengitterStartyiteration = minNorth;
		double KoordinatengitterStartyVereinfacht = 0;
		///////////////////////////////////////////////////////////////////////////////////////////////////
		// der Winkel Beta muss ähnlich wie bei der Massstabsleiste vereinfacht werden.
		// Hierfür muss erst einmal überprüft werden, ob diese größer oder kleiner als 1
		/////////////////////////////////////////////////////////////////////////////////////////////////// Grad
		/////////////////////////////////////////////////////////////////////////////////////////////////// ist.

		// Für Beta muss ein Iterationshilfswert benutzt werden,
		// Zusätzlich wird ein Counter gestartet:
		///////////////////////////////////////////////////////////////////////////////////////////////////
		if (betaiteration < 1.00) {

			do {
				betaiteration = betaiteration * 10;
				betaiterationcounter = betaiterationcounter + 1;
				//für jede Nachkommastelle geht der iterationscounter eins hoch
				// Zur Vereinfachung wird die Zahl gerundet:
				double betaiterationVereinfacht = Math.round(betaiteration);
				// ...und nun wieder in die ursprüngliche Dimension umgewandelt:
				
			
				betaVereinfacht = betaiterationVereinfacht / Math.pow(10, betaiterationcounter);
				
				// ...somit wurden zum Beispiel aus 0,337854 Grad jetzt 0.3 Grad generiert

			} while (betaiteration <1);
	
			//vereinfacht bedeutet, das die Zahlen durch rundungen zu Vollen zahlen gemacht worden sind also 0,425 zu 0,4 oder 1,546 zu 2
			
			//Also erste Iterationkoordinate nehmen wir den kleinsten Eastewert
			//Dieser wird jetzt so oft mit 10 multiplitziert, bis er keine Nachkommastellen merh hat und dann wieder solange mit 10 multipliziert bis er die ursprungliche diemsion hat
			//der iterationswert von oben kann(betaiterationcounter) übernommen werden
			//der Prozess ist notwendig um eine Zahl die kleiner ist als 0 zu runden
			
			KoordinatengitterStartxiteration = KoordinatengitterStartxiteration * Math.pow(10, betaiterationcounter);

			double iterationKoordinatengitterStartxVereinfacht = Math.round(KoordinatengitterStartxiteration);

			KoordinatengitterStartxVereinfacht = iterationKoordinatengitterStartxVereinfacht/ Math.pow(10, betaiterationcounter);
				
			
			////////////////////////////////////////////////////////////////////////////////////////////////////////
			//ist der Bogenabschnitt größer als 0 ist dies einfacher, da nur der Bogenabshnitt gerundet werden muss und es gibt einen Startwert für die iteration
		} else if (betaiteration > 1) {
			betaVereinfacht = Math.round(betaiteration);
			KoordinatengitterStartxVereinfacht = Math.round(KoordinatengitterStartxiteration);
			Laengengrade.add(KoordinatengitterStartxVereinfacht);
		}
	

		//////////////////Selbe Schritte erfolgen jetzt in Nord-Südrichtung////////////////////////////////////////////////////////////////////////////////////////
		
		if (alphaiteration < 1.00) {
		//////////////////////////////////////////////
			
			do {
				alphaiteration = alphaiteration * 10;
				alphaiterationcounter = alphaiterationcounter + 1;

				// Zur Vereinfachung wird die Zahl gerundet:
				double alphaiterationVereinfacht = Math.round(alphaiteration);

				// ...und nun wieder in die ursprungliche Dimension umgewandelt:
			
			
					alphaVereinfacht = alphaiterationVereinfacht / Math.pow(10, alphaiterationcounter);
				
			
			} while (alphaiteration < 1);

			//Startwert 
			KoordinatengitterStartyiteration = KoordinatengitterStartyiteration * Math.pow(10, alphaiterationcounter);
			double iterationKoordinatengitterStartyVereinfacht = Math.round(KoordinatengitterStartyiteration);
			KoordinatengitterStartyVereinfacht = iterationKoordinatengitterStartyVereinfacht/ Math.pow(10, alphaiterationcounter);
			Breitengrade.add(KoordinatengitterStartyVereinfacht);//Koordinatengitterstart wird in die Koordinatengitterliste aufgenommen
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		} else if (alphaiteration > 1) {
			
			alphaVereinfacht = Math.round(alphaiteration);
			KoordinatengitterStartyVereinfacht = Math.round(KoordinatengitterStartyiteration);
			Breitengrade.add(KoordinatengitterStartyVereinfacht);//Koordinatengitterstart wird in die Koordinatengitterliste aufgenommen
		}

		// Wieder manuell Breitengrad davorschieben:
		//Um sicherzugehen, das durch das Kürzen der Winkel nicht Zuviel Platz entstanden ist wird von unserere Startlinie parallel ein Meridian in die falsche Richtung gesetzt.
		double ersterKoordinatengitterwertx = KoordinatengitterStartxVereinfacht - (betaVereinfacht / 6);
		Laengengrade.add(ersterKoordinatengitterwertx);

		double ersterKoordinatengitterwerty = KoordinatengitterStartyVereinfacht - (alphaVereinfacht / 4);
		Breitengrade.add(KoordinatengitterStartyVereinfacht);

	
		///////////////////////////////////////////////////////////////////////////////////////////////////

		
		// nun werden die anderen Linien erzeugt:
		for (int i = 1; i < 5; i++) {

			double Koordinatengitterwerty = KoordinatengitterStartyVereinfacht + ((alphaVereinfacht / 4) * i);
			Breitengrade.add(Koordinatengitterwerty);

		
		}

		for (int i = 1; i < 7; i++) {
			double Koordinatengitterwertx = KoordinatengitterStartxVereinfacht + ((betaVereinfacht / 6) * i);
			Laengengrade.add(Koordinatengitterwertx);
		}

		// Jetzt muss allerdings noch die Lage auf dem Bildschirm berechnet werden:
		/////////// Transformieren //////////
		for (int j = 0; j < Laengengrade.size(); j++) {

			double Laenge = Laengengrade.get(j);
			int LaengeBildschirm = (int) (((Laenge - minEast) * (width)) / beta);

			Laengengradetransformiert.add(LaengeBildschirm);
		}

		for (int j = 0; j < Breitengrade.size(); j++) {

			double Breiten = Breitengrade.get(j);

			double deltaBreite = (Breiten - minNorth);
			double hight = (((width / Math.sqrt(2))));
			double breiteBildschirm = (((deltaBreite) * (hight)) / alpha);
			Breitengradetransformiert.add(breiteBildschirm);

		}

	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	// -------------------------------------------- Selbe Vorgehensweise für die
	// Breitengrade: -------------------------------------------------------
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Client.Druckuebersicht.ausgewaehlteFarbeKoordinatengitter);
		g2.setStroke(new BasicStroke(strichdicke));
		g2.setFont(new Font("ARIAL", Font.PLAIN,(int) 13*strichdicke));
		// Zeichnen der senkrechten Linien:
		for (int j = 0; j < Laengengradetransformiert.size(); j++) {

			int laenge = Laengengradetransformiert.get(j);
			if ((laenge < 20) || laenge > (width - (width/10))) {

			} else {
				g2.drawLine(laenge, 0, laenge, (int) (width / Math.sqrt(2)));

				// Beschriften der senkrechten Linien:
				String Breitetext = String.valueOf(Math.round(Laengengrade.get(j) * 100) / 100.0);
				g2.drawString(Breitetext, laenge + (int)(width / Math.sqrt(2)/40), (int) ((width / Math.sqrt(2)/30)));
			}
		}

		for (int j = 0; j < Breitengradetransformiert.size(); j++) {

			Double breite = Breitengradetransformiert.get(j);
			if (breite < 20 || breite > (width / Math.sqrt(2))) {

			} else {
				g2.drawLine(0, (int) ((((width / Math.sqrt(2)) - (breite) - 20))), width-(width/20)-1,
						(int) (((width / Math.sqrt(2)) - (breite)-20)));

				// Beschriften der senkrechten Linien:
				String Breitetext = String.valueOf(Math.round(Breitengrade.get(j) * 100) / 100.0);
				g2.drawString(Breitetext, (width/60), (int) ((width / Math.sqrt(2)) - (breite)) + (width/100));


			}
		}
	}
}
